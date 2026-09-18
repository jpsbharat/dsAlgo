package lld.atm;

public class ATM {
    public enum State {
        IDLE
    }

    private boolean running;
    private State currentState;
    private CardProcessor cardProcessor;
    private CashInventory cashInventory;

    public ATM(CardProcessor cardProcessor, CashInventory cashInventory) {
        this.running = false;
        this.currentState = State.IDLE;
        ;
        this.cardProcessor = cardProcessor;
        this.cashInventory = cashInventory;
    }

    private void init() {
        this.cashInventory.init();
        this.cashInventory.init();
    }

    public void start() {
        this.init();
        this.running = true;
        while (running) {

        }
    }

    public void stop() {
        this.running = false;
    }

}
