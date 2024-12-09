import java.math.BigDecimal;

public class User {
    private BigDecimal cardnumber;
    private String name;
    private double balance;
    private BigDecimal[] jars;

    public User(BigDecimal cardnumber, String name, double balance, BigDecimal[] jars) {
        this.cardnumber = cardnumber;
        this.name = name;
        this.balance = balance;
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

    public BigDecimal[] getJars() {
        return jars;
    }
}
