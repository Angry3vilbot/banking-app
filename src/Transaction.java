public class Transaction {
    private int id;
    private double value;
    private String title;
    private String type;
    private String destination;
    private String sender;

    public Transaction(int id, double value, String title, String type, String destination, String sender) {
        this.id = id;
        this.value = value;
        this.title = title;
        this.type = type;
        this.destination = destination;
        this.sender = sender;
    }

    public double getValue() {
        return value;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public String getDestination() {
        return destination;
    }

    public String getSender() {
        return sender;
    }
}
