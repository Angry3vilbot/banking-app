package angry3vilbot.bankingapp;

/**
 * Transaction class representing a financial transaction.
 * It contains the ID, value, title, type, and destination or sender.
 * This class is used to create and manage transactions in the system.
 * <br><br>
 * If the transaction has a destination, it is considered to be an outgoing transaction.
 * If the transaction has a sender, it is considered to be an incoming transaction.
 */
public class Transaction {
    /**
     * The ID of the transaction.
     */
    private int id;
    /**
     * The value of the transaction.
     */
    private double value;
    /**
     * The title of the transaction.
     */
    private String title;
    /**
     * The type of the transaction.
     */
    private String type;
    /**
     * The destination of the transaction. Indicates that the transaction is outgoing.
     */
    private String destination;
    /**
     * The sender of the transaction. Indicates that the transaction is incoming.
     */
    private String sender;

    /**
     * Constructs a Transaction object.
     * @param id id of the transaction
     * @param value value of the transaction
     * @param title title of the transaction
     * @param type type of the transaction
     * @param destination destination of the transaction (if the transaction is outgoing)
     * @param sender sender of the transaction (if the transaction is incoming)
     */
    public Transaction(int id, double value, String title, String type, String destination, String sender) {
        this.id = id;
        this.value = value;
        this.title = title;
        this.type = type;
        this.destination = destination;
        this.sender = sender;
    }

    /**
     * Returns the ID of the transaction.
     * @return the ID of the transaction
     */
    public double getValue() {
        return value;
    }

    /**
     * Returns the title of the transaction.
     * @return the title of the transaction
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the type of the transaction.
     * @return the type of the transaction
     */
    public String getType() {
        return type;
    }

    /**
     * Returns the destination of the transaction. Indicates that the transaction is outgoing.
     * @return the destination of the transaction
     */
    public String getDestination() {
        return destination;
    }

    /**
     * Returns the sender of the transaction. Indicates that the transaction is incoming.
     * @return the sender of the transaction
     */
    public String getSender() {
        return sender;
    }
}
