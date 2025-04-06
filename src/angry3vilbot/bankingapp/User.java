package angry3vilbot.bankingapp;

import java.math.BigDecimal;

/**
 * User class represents a user in the system.
 * It contains user information such as card number, name, balance,
 * jar IDs, transaction IDs, deposit requests, transactions, and jars.
 * It also includes a flag to indicate if the user has acknowledged a message about their jar(s) reaching their goal(s).
 */
public class User {
    /**
     * The card number of the user.
     */
    private BigDecimal cardnumber;
    /**
     * The name of the user.
     */
    private String name;
    /**
     * The balance of the user.
     */
    private double balance;
    /**
     * Array of jar IDs for jars associated with the user.
     */
    private BigDecimal[] jarIds;
    /**
     * Array of {@link Jar} objects for jars associated with the user.
     */
    private Jar[] jars;
    /**
     * Array of transaction IDs of transactions associated with the user.
     */
    private BigDecimal[] transactionIds;
    /**
     * Array of {@link DepositRequest} objects for deposit requests associated with the user.
     */
    private DepositRequest[] requests;
    /**
     * Array of {@link Transaction} objects for transactions associated with the user.
     */
    private Transaction[] transactions;
    /**
     * Flag indicating if the user has acknowledged the message about their jar(s) reaching their goal(s).
     */
    private boolean acknowledgedJarGoalMsg = false;

    /**
     * Constructs a User object.
     * @param cardnumber the card number of the user
     * @param name the name of the user
     * @param balance the balance of the user
     * @param jarIds the IDs of the jars associated with the user
     * @param transactionIds the IDs of the transactions associated with the user
     * @param requests the deposit requests associated with the user
     * @param transactions the transactions associated with the user
     * @param jars the jars associated with the user
     * @param acknowledgedJarGoalMsg a flag indicating if the user has acknowledged the jar goal message
     */
    public User(BigDecimal cardnumber, String name, double balance, BigDecimal[] jarIds, BigDecimal[] transactionIds, DepositRequest[] requests, Transaction[] transactions, Jar[] jars, boolean acknowledgedJarGoalMsg) {
        this.cardnumber = cardnumber;
        this.name = name;
        this.balance = balance;
        this.jarIds = jarIds;
        this.transactionIds = transactionIds;
        this.requests = requests;
        this.transactions = transactions;
        this.jars = jars;
        this.acknowledgedJarGoalMsg = acknowledgedJarGoalMsg;
    }

    /**
     * Gets the user's card number.
     * @return the card number of the user
     */
    public BigDecimal getCardnumber() {
        return cardnumber;
    }

    /**
     * Gets the name of the user.
     * @return the name of the user
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the user's balance.
     * @return the balance of the user
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Gets the jars associated with the user.
     * @return the array of {@link Jar} objects for jars associated with the user
     */
    public Jar[] getJars() {
        return jars;
    }

    /**
     * Gets the jar IDs associated with the user.
     * @return the array of jar IDs associated with the user
     */
    public BigDecimal[] getJarIds() { return jarIds; }

    /**
     * Gets the transactions associated with the user.
     * @return the array of {@link Transaction} objects for transactions associated with the user
     */
    public Transaction[] getTransactions() {
        return transactions;
    }

    /**
     * Gets the deposit requests associated with the user.
     * @return the array of {@link DepositRequest} objects for deposit requests associated with the user
     */
    public DepositRequest[] getRequests() {
        return requests;
    }

    /**
     * Gets a boolean value that shows whether the user has acknowledged the jar goal message or not.
     * @return <code>true</code> if the user has acknowledged the jar goal message, <code>false</code> otherwise.
     */
    public boolean getAcknowledgedJarGoalMsg() {
        return acknowledgedJarGoalMsg;
    }

    /**
     * Sets if the user has acknowledged the jar goal message or not.
     * @param confirmed <code>true</code> if the user has acknowledged the jar goal message, <code>false</code> otherwise.
     */
    public void setAcknowledgedJarGoalMsg(boolean confirmed) {
        acknowledgedJarGoalMsg = confirmed;
    }
}
