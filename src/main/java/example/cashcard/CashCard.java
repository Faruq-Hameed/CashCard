package example.cashcard;

public class CashCard {
    private static long counter = 0; // shared across all objects
    private Long id;
    private int amount;

    public CashCard() {
        this.id = this.counter++;
        this.amount = 0;
    }

    public int getAmount() {
        return amount;
    }

    public void addMoney(int amount) {
        this.amount += amount;
    }

    public void removeMoney(int amount) {
        int finalAmount = this.amount - amount;
        if (finalAmount < 0) {
            return;
        }
        this.amount -= amount;
    }

    public Long getId() {
        return id;
    }
}
