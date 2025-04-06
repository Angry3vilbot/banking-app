package angry3vilbot.bankingapp;

/**
 * Jar class represents a savings jar with an ID, title, goal amount, and current balance.
 * It provides constructors to initialize the jar with or without a goal amount.
 * It also provides getter methods to access the jar's properties.
 */
public class Jar {
    /**
     * ID of the jar
     */
    private int id;
    /**
     * Title of the jar
     */
    private String title;
    /**
     * Goal amount for the jar (optional)
     */
    private double goal;
    /**
     * Current balance of the jar
     */
    private double balance;

    /**
     * Constructs a Jar object with the specified ID, title, goal, and balance.
     * @param id the ID of the jar
     * @param title the title of the jar
     * @param goal the goal amount for the jar
     * @param balance the current balance of the jar
     */
    public Jar(int id, String title, double goal, double balance) {
        this.id = id;
        this.title = title;
        this.goal = goal;
        this.balance = balance;
    }
    /**
     * Constructs a Jar object with the specified ID, title, and balance but without a goal.
     * @param id the ID of the jar
     * @param title the title of the jar
     * @param balance the current balance of the jar
     */
    public Jar(int id, String title, double balance) {
        this.id = id;
        this.title = title;
        this.balance = balance;
    }

    /**
     * Gets the ID of the jar.
     * @return the ID of the jar
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the title of the jar.
     * @return the title of the jar
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets the goal amount of the jar.
     * @return the goal amount of the jar
     */
    public double getGoal() {
        return goal;
    }

    /**
     * Gets the current balance of the jar.
     * @return the current balance of the jar
     */
    public double getBalance() {
        return balance;
    }
}
