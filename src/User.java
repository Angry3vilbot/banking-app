import java.math.BigDecimal;

public class User {
    private BigDecimal cardnumber;
    private String name;
    private double balance;
    private BigDecimal[] jars;
    private BigDecimal[] transactionIds;
    private DepositRequest[] requests;
    private Transaction[] transactions;

    public User(BigDecimal cardnumber, String name, double balance, BigDecimal[] jars, BigDecimal[] transactionIds, DepositRequest[] requests, Transaction[] transactions) {
        this.cardnumber = cardnumber;
        this.name = name;
        this.balance = balance;
        this.jars = jars;
        this.transactionIds = transactionIds;
        this.requests = requests;
        this.transactions = transactions;
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

    public BigDecimal[] getJars() {
        return jars;
    }

    public Transaction[] getTransactions() {
        return transactions;
    }

    public DepositRequest[] getRequests() {
        return requests;
    }
}
