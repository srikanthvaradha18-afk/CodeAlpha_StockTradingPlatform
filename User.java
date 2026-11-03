import java.util.ArrayList;

public class User {

    private String name;
    private double cashBalance;
    private Portfolio portfolio;

    private ArrayList<Transaction> history =
            new ArrayList<>();

    public User(String name, double balance) {
        this.name = name;
        this.cashBalance = balance;
        this.portfolio = new Portfolio();
    }

    public double getCashBalance() {
        return cashBalance;
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }

    public ArrayList<Transaction> getHistory() {
        return history;
    }

    public void addCash(double amount) {
        cashBalance += amount;
    }

    public void deductCash(double amount) {
        cashBalance -= amount;
    }
}