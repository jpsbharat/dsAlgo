package lld.atm.model;

public class Account {
    private final long id;
    private final String name;
    private final String bankName;

    long balance;

    public Account(long id, String name, String bankName) {
        this.id = id;
        this.name = name;
        this.bankName = bankName;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBankName() {
        return bankName;
    }

    public long getBalance() {
        return balance;
    }

    public void addBalance(long amt) {
        this.balance += amt;
    }

    public void withdraw(long amt) {
        this.balance -= amt;
    }
}
