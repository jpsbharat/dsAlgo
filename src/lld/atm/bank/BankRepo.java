package lld.atm.bank;

import lld.atm.model.Account;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BankRepo {

    private static final Map<String, Bank> bankMap = new ConcurrentHashMap<>();

    static {
        Bank bank = new Bank("HDFC");
        Account ac = new Account(123, "ABC", bank.getName());
        ac.addBalance(1000000);
        bank.addAccount(ac);

        ac = new Account(124, "XYZ", bank.getName());
        ac.addBalance(1000000);
        bank.addAccount(ac);
        bankMap.put(bank.getName(), bank);


        bank = new Bank("ICICI");
        ac = new Account(123, "ABC", bank.getName());
        ac.addBalance(1000000);
        bank.addAccount(ac);

        ac = new Account(124, "XYZ", bank.getName());
        ac.addBalance(1000000);
        bank.addAccount(ac);
        bankMap.put(bank.getName(), bank);
    }

    public static long getBalance(String bankName, long acNumber) {
        Bank bank = bankMap.get(bankName);
        if (bank != null) {
            Account ac = bank.getAccount(acNumber);
            if (ac != null) {
                return ac.getBalance();
            } else {
                System.out.println("Bank doesnt exist...");
                return 0;
            }
        } else {
            System.out.println("Bank doesnt exist...");
            return 0;
        }
    }

    public static boolean withdraw(String bankName, long acNumber, long amt) {
        Bank bank = bankMap.get(bankName);
        if (bank != null) {
            Account ac = bank.getAccount(acNumber);
            if (ac != null) {
                ac.withdraw(amt);
            } else {
                System.out.println("Bank doesnt exist...");
                return false;
            }
        } else {
            System.out.println("Bank doesnt exist...");
            return false;
        }
        return true;
    }
}
