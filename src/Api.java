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
        // Create a new User instance to be stored in the UserSession
        User user = new User(new BigDecimal(resSet.getString(1)), resSet.getString(2),
                resSet.getDouble(3), (BigDecimal[])resSet.getArray(4).getArray());
        // Store the user in the UserSession
        UserSession.getInstance().setUser(user);
        pstat.close();
        connection.close();
    }
}
