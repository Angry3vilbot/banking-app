public class Jar {
    private int id;
    private String title;
    private double goal;
    private double balance;

    public Jar(int id, String title, double goal, double balance) {
        this.id = id;
        this.title = title;
        this.goal = goal;
        this.balance = balance;
    }

    public Jar(int id, String title, double balance) {
        this.id = id;
        this.title = title;
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public double getGoal() {
        return goal;
    }

    public double getBalance() {
        return balance;
    }
}
