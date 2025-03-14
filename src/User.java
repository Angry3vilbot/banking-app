import java.math.BigDecimal;

public class User {
    private BigDecimal cardnumber;
    private String name;
    private double balance;
    private BigDecimal[] jarIds;
    private Jar[] jars;
    private BigDecimal[] transactionIds;
    private DepositRequest[] requests;
    private Transaction[] transactions;

    public User(BigDecimal cardnumber, String name, double balance, BigDecimal[] jarIds, BigDecimal[] transactionIds, DepositRequest[] requests, Transaction[] transactions, Jar[] jars) {
        this.cardnumber = cardnumber;
        this.name = name;
        this.balance = balance;
        this.jarIds = jarIds;
        this.transactionIds = transactionIds;
        this.requests = requests;
        this.transactions = transactions;
        this.jars = jars;
    }

    public BigDecimal getCardnumber() {
        return cardnumber;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public Jar[] getJars() {
        return jars;
    }

    public BigDecimal[] getJarIds() { return jarIds; }

    public Transaction[] getTransactions() {
        return transactions;
    }

    public DepositRequest[] getRequests() {
        return requests;
    }
}
