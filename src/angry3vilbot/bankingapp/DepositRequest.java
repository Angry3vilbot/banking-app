package angry3vilbot.bankingapp;

import java.math.BigDecimal;

/**
 * This class represents a deposit request.
 * It contains the ID, name of the user that made the request, the amount to be deposited, and the account number to deposit into.
 * It provides a constructor to initialize these fields and getter methods to access them.
 */
public class DepositRequest {
    /**
     * ID of the deposit request
     */
    private int id;
    /**
     * Name of the user that made the request
     */
    private String name;
    /**
     * Amount to be deposited
     */
    private BigDecimal amount;
    /**
     * Account number to deposit into
     */
    private BigDecimal target;

    /**
     * Constructs a DepositRequest object with the specified ID, name, amount, and target.
     * @param id ID of the deposit request
     * @param name Name of the user that made the request
     * @param amount Amount to be deposited
     * @param target Account number to deposit into
     */
    public DepositRequest(int id, String name, BigDecimal amount, BigDecimal target) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.target = target;
    }

    /**
     * Gets the ID of the deposit request.
     * @return ID of the deposit request
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the name of the user that made the request.
     * @return Name of the user that made the request
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the amount to be deposited.
     * @return Amount to be deposited
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * Gets the account number to deposit into.
     * @return Account number to deposit into
     */
    public BigDecimal getTarget() {
        return target;
    }
}
