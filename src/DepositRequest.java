import java.math.BigDecimal;

public class DepositRequest {
    private int id;
    private String name;
    private BigDecimal amount;
    private BigDecimal target;

    public DepositRequest(int id, String name, BigDecimal amount, BigDecimal target) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.target = target;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getTarget() {
        return target;
    }
}
