package lld.atm.bank;

import lld.atm.model.Account;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class Bank {
    private final String name;
    private final Map<Long, Account> accountMap;

    public Bank(String name) {
        this.name = name;
        this.accountMap = new ConcurrentHashMap<>();
    }

    public String getName() {
        return name;
    }

    public void addAccount(Account account){
        if(!accountMap.containsKey(account.getId())){
            accountMap.put(account.getId(), account);
        }
    }

    public Account getAccount(long id){
        return accountMap.get(id);
    }
}
