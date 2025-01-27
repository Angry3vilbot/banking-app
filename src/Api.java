import javax.swing.*;
import java.io.CharArrayReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

public class Api extends JPanel {
    private final String DATABASE_URL = "jdbc:postgresql://localhost:5432/postgres";
    private final String dbpassword = System.getenv("PASSWORD");
    private Connection connection = null;
    private PreparedStatement pstat = null;
    void authenticate(String login, char[] password) throws SQLException {
        // Establish a connection
        connection = DriverManager.getConnection(DATABASE_URL, "postgres", dbpassword);
        // Get the user with the matching password and card number (who doesn't love saving passwords as plain text)
        pstat = connection.prepareStatement("SELECT * FROM \"user\" WHERE cardnumber=? AND \"password\"=?");
        pstat.setBigDecimal(1, new BigDecimal(login));
        // Reader used to not keep a string in memory
        Reader reader = new CharArrayReader(password);
        pstat.setCharacterStream(2, reader);
        ResultSet resSet = pstat.executeQuery();
        resSet.next();
        pstat.clearParameters();
        // Override the password array for safety
        Arrays.fill(password, ' ');
        // Check if there are no transactions/deposit requests. If there are, get them from the database and store them
        BigDecimal[] transactionIds = {};
        BigDecimal[] requestIds = {};
        DepositRequest[] requests = {};
        Transaction[] transactions = {};
        if(resSet.getArray(6) != null) {
            transactionIds = (BigDecimal[])resSet.getArray(6).getArray();
            // Order the transaction ids from newest to oldest
            for (int i = 0; i < transactionIds.length / 2; i++) {
                BigDecimal temp = transactionIds[i];
                transactionIds[i] = transactionIds[transactionIds.length - 1 - i];
                transactionIds[transactionIds.length - 1 - i] = temp;
            }
            transactions = new Transaction[transactionIds.length];
            for (int i = 0; i < transactionIds.length; i++) {
                transactions[i] = getTransactionData(transactionIds[i].intValue());
            }
        }
        if(resSet.getArray(7) != null) {
            requestIds = (BigDecimal[])resSet.getArray(7).getArray();
            requests = new DepositRequest[requestIds.length];
            for (int i = 0; i < requests.length; i++) {
                requests[i] = getDepositRequestData(requestIds[i].intValue());
            }
        }

        // Create a new User instance to be stored in the UserSession
        User user = new User(new BigDecimal(resSet.getString(1)), resSet.getString(2),
                resSet.getDouble(3), (BigDecimal[])resSet.getArray(4).getArray(), transactionIds, requests, transactions);
        // Store the user in the UserSession
        UserSession.getInstance().setUser(user);
        pstat.close();
        connection.close();
    }
    // Deposit via card number
    void deposit(BigDecimal payInfo, Double amount) throws SQLException{
        // Establish a connection
        connection = DriverManager.getConnection(DATABASE_URL, "postgres", dbpassword);
        // Create a new deposit request
        pstat = connection.prepareStatement("INSERT INTO request (name, amount, target) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        pstat.setString(1, UserSession.getInstance().getUser().getName());
        pstat.setDouble(2, amount);
        pstat.setBigDecimal(3, UserSession.getInstance().getUser().getCardnumber());
        pstat.execute();
        ResultSet request = pstat.getGeneratedKeys();
        request.next();
        // Save the deposit request in the user with the payInfo card number
        pstat = connection.prepareStatement
                ("UPDATE \"user\" SET requests=array_append(COALESCE(requests, '{}'), ?) WHERE cardnumber=?");
        pstat.setBigDecimal(1, request.getBigDecimal(1));
        pstat.setBigDecimal(2, payInfo);
        pstat.executeUpdate();

        pstat.close();
        connection.close();
    }
    // "Deposit" via Free Money
    void deposit(Double amount) throws SQLException{
        // Establish a connection
        connection = DriverManager.getConnection(DATABASE_URL, "postgres", dbpassword);
        // Add the money to the user's account
        pstat = connection.prepareStatement("UPDATE \"user\" SET balance=balance+? WHERE cardnumber=?");
        pstat.setDouble(1, amount);
        pstat.setBigDecimal(2, UserSession.getInstance().getUser().getCardnumber());
        pstat.executeUpdate();

        // Create a transaction for the operation
        pstat = connection.prepareStatement("INSERT INTO transaction (value, title, sender) VALUES (?, 'Deposit Via Free Money', 'Free Money')", Statement.RETURN_GENERATED_KEYS);
        pstat.setDouble(1, amount);
        pstat.execute();
        ResultSet transaction = pstat.getGeneratedKeys();
        transaction.next();

        // Save the transaction in the user's account
        pstat = connection.prepareStatement("UPDATE \"user\" SET transactions=array_append(COALESCE(transactions, '{}'), ?) WHERE cardnumber=?");
        pstat.setBigDecimal(1, transaction.getBigDecimal(1));
        pstat.setBigDecimal(2, UserSession.getInstance().getUser().getCardnumber());
        pstat.executeUpdate();

        pstat.close();
        connection.close();
    }
    Transaction getTransactionData(int id) throws SQLException {
        Transaction transaction;
        // Establish a connection
        connection = DriverManager.getConnection(DATABASE_URL, "postgres", dbpassword);
        // Get the transaction with the specified ID
        pstat = connection.prepareStatement("SELECT * FROM \"transaction\" WHERE id=?");
        pstat.setInt(1, id);
        ResultSet resSet = pstat.executeQuery();
        resSet.next();
        // Create the Transaction object and return it
        transaction = new Transaction(resSet.getInt(1), resSet.getDouble(2), resSet.getString(3),
                resSet.getString(4), resSet.getString(5), resSet.getString(6));
        return transaction;
    }

    DepositRequest getDepositRequestData(int id) throws SQLException {
        DepositRequest request;
        // Establish a connection
        connection = DriverManager.getConnection(DATABASE_URL, "postgres", dbpassword);
        // Get the deposit request with the specified ID
        pstat = connection.prepareStatement("SELECT * FROM \"request\" WHERE id=?");
        pstat.setInt(1, id);
        ResultSet resSet = pstat.executeQuery();
        resSet.next();
        // Create the DepositRequest object and return it
        request = new DepositRequest(id, resSet.getString(2), resSet.getBigDecimal(3), resSet.getBigDecimal(4));
        return request;
    }

    void fulfillDepositRequest(int id) throws SQLException {
        // Establish a connection
        connection = DriverManager.getConnection(DATABASE_URL, "postgres", dbpassword);
        // Get the data about the request
        DepositRequest request = getDepositRequestData(id);
        // Subtract the money from the user's account if they have enough
        if (UserSession.getInstance().getUser().getBalance() >= request.getAmount().doubleValue()) {
            pstat = connection.prepareStatement("UPDATE \"user\" SET balance=balance-? WHERE cardnumber=?");
            pstat.setDouble(1, request.getAmount().doubleValue());
            pstat.setBigDecimal(2, UserSession.getInstance().getUser().getCardnumber());
            pstat.executeUpdate();
        }
        else {
            throw new SQLException("Not enough money on the user's balance");
        }
        // Add the money to request target's account
        pstat = connection.prepareStatement("UPDATE \"user\" SET balance=balance+? WHERE cardnumber=?");
        pstat.setDouble(1, request.getAmount().doubleValue());
        pstat.setBigDecimal(2, request.getTarget());
        pstat.executeUpdate();

        // Save transactions for the operation
        pstat = connection.prepareStatement("INSERT INTO transaction (value, title, type, destination) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        pstat.setDouble(1, -request.getAmount().doubleValue());
        pstat.setString(2, "Deposit request fulfillment for " + request.getName());
        pstat.setString(3, "Deposit Request");
        pstat.setString(4, "" + request.getTarget());
        pstat.execute();
        ResultSet transaction = pstat.getGeneratedKeys();
        transaction.next();

        // Save the transaction in the user's account
        pstat = connection.prepareStatement("UPDATE \"user\" SET transactions=array_append(COALESCE(transactions, '{}'), ?) WHERE cardnumber=?");
        pstat.setBigDecimal(1, transaction.getBigDecimal(1));
        pstat.setBigDecimal(2, UserSession.getInstance().getUser().getCardnumber());
        pstat.executeUpdate();

        pstat = connection.prepareStatement("INSERT INTO transaction (value, title, sender) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        pstat.setDouble(1, request.getAmount().doubleValue());
        pstat.setString(2, "Deposit request fulfilled by " + UserSession.getInstance().getUser().getName());
        pstat.setString(3, UserSession.getInstance().getUser().getName());
        pstat.execute();
        transaction = pstat.getGeneratedKeys();
        transaction.next();

        // Save the transaction in the target's account
        pstat = connection.prepareStatement("UPDATE \"user\" SET transactions=array_append(COALESCE(transactions, '{}'), ?) WHERE cardnumber=?");
        pstat.setBigDecimal(1, transaction.getBigDecimal(1));
        pstat.setBigDecimal(2, request.getTarget());
        pstat.executeUpdate();
        // Remove the request from the database
        deleteDepositRequest(id);
        // Refresh user data
        update();
        pstat.close();
        connection.close();
    };

    void deleteDepositRequest(int id) throws SQLException {
        // Establish a connection
        connection = DriverManager.getConnection(DATABASE_URL, "postgres", dbpassword);
        // Delete the request from the database
        pstat = connection.prepareStatement("DELETE FROM \"request\" WHERE id=?");
        pstat.setInt(1, id);
        pstat.execute();
        // Remove the request from the user
        pstat = connection.prepareStatement("UPDATE \"user\" SET requests=array_remove(requests, ?) WHERE cardnumber=?");
        pstat.setInt(1, id);
        pstat.setBigDecimal(2, UserSession.getInstance().getUser().getCardnumber());
        pstat.executeUpdate();
    }

    void send(double amount, BigDecimal cardNumber, String name) throws SQLException{
        // Establish a connection
        connection = DriverManager.getConnection(DATABASE_URL, "postgres", dbpassword);
        // Get current user data for verification
        pstat = connection.prepareStatement("SELECT * FROM \"user\" WHERE cardnumber=?");
        pstat.setBigDecimal(1, UserSession.getInstance().getUser().getCardnumber());
        ResultSet user = pstat.executeQuery();
        user.next();
        // Check if the user's account has enough money to send
        if(user.getDouble(3) < amount) {
            pstat.close();
            connection.close();
            throw new SQLException("Not enough money on the user's balance");
        }
        // Subtract the amount from the user's balance
        pstat = connection.prepareStatement("UPDATE \"user\" SET balance=balance-? WHERE cardnumber=?");
        pstat.setDouble(1, amount);
        pstat.setBigDecimal(2, user.getBigDecimal(1));
        pstat.executeUpdate();
        // Add the amount to the target's balance
        pstat = connection.prepareStatement("UPDATE \"user\" SET balance=balance+? WHERE cardnumber=?");
        pstat.setDouble(1, amount);
        pstat.setBigDecimal(2, cardNumber);
        pstat.executeUpdate();

        // Save transactions for the operation
        pstat = connection.prepareStatement("INSERT INTO transaction (value, title, type, destination) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        pstat.setDouble(1, -amount);
        pstat.setString(2, "Transfer to " + name);
        pstat.setString(3, "Transfer");
        pstat.setString(4, "" + cardNumber);
        pstat.execute();
        ResultSet transaction = pstat.getGeneratedKeys();
        transaction.next();

        // Save the transaction in the user's account
        pstat = connection.prepareStatement("UPDATE \"user\" SET transactions=array_append(COALESCE(transactions, '{}'), ?) WHERE cardnumber=?");
        pstat.setBigDecimal(1, transaction.getBigDecimal(1));
        pstat.setBigDecimal(2, UserSession.getInstance().getUser().getCardnumber());
        pstat.executeUpdate();

        pstat = connection.prepareStatement("INSERT INTO transaction (value, title, sender) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        pstat.setDouble(1, amount);
        pstat.setString(2, "Transfer from " + UserSession.getInstance().getUser().getName());
        pstat.setString(3, UserSession.getInstance().getUser().getCardnumber().toString());
        pstat.execute();
        transaction = pstat.getGeneratedKeys();
        transaction.next();

        // Save the transaction in the target's account
        pstat = connection.prepareStatement("UPDATE \"user\" SET transactions=array_append(COALESCE(transactions, '{}'), ?) WHERE cardnumber=?");
        pstat.setBigDecimal(1, transaction.getBigDecimal(1));
        pstat.setBigDecimal(2, cardNumber);
        pstat.executeUpdate();

        // Refresh user data
        update();
        pstat.close();
        connection.close();
    }

    // Update data about the user
    void update() throws SQLException{
        // Establish a connection
        connection = DriverManager.getConnection(DATABASE_URL, "postgres", dbpassword);
        // Get the current user
        pstat = connection.prepareStatement("SELECT * FROM \"user\" WHERE cardnumber=?");
        pstat.setBigDecimal(1, UserSession.getInstance().getUser().getCardnumber());
        ResultSet resSet = pstat.executeQuery();
        resSet.next();
        // Check if there are no transactions/deposit requests. If there are, get them from the database and store them
        BigDecimal[] transactionIds = {};
        BigDecimal[] requestIds = {};
        DepositRequest[] requests = {};
        Transaction[] transactions = {};
        if(resSet.getArray(6) != null) {
            transactionIds = (BigDecimal[])resSet.getArray(6).getArray();
            // Order the transaction ids from newest to oldest
            for (int i = 0; i < transactionIds.length / 2; i++) {
                BigDecimal temp = transactionIds[i];
                transactionIds[i] = transactionIds[transactionIds.length - 1 - i];
                transactionIds[transactionIds.length - 1 - i] = temp;
            }
            transactions = new Transaction[transactionIds.length];
            for (int i = 0; i < transactionIds.length; i++) {
                transactions[i] = getTransactionData(transactionIds[i].intValue());
            }
        }
        if(resSet.getArray(7) != null) {
            requestIds = (BigDecimal[])resSet.getArray(7).getArray();
            requests = new DepositRequest[requestIds.length];
            for (int i = 0; i < requests.length; i++) {
                requests[i] = getDepositRequestData(requestIds[i].intValue());
            }
        }
        // Create a new User instance to be stored in the UserSession
        User user = new User(new BigDecimal(resSet.getString(1)), resSet.getString(2),
                resSet.getDouble(3), (BigDecimal[])resSet.getArray(4).getArray(),
                transactionIds, requests, transactions);
        // Store the user in the UserSession
        UserSession.getInstance().setUser(user);
        pstat.close();
        connection.close();
    }
}
