package ds.conc;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.LockSupport;


public class ConcStack {
    private static class StackNode<T> {
        T value;
        StackNode<T> next;

        StackNode(T value) {
            this.value = value;
        }

        public T getValue() {
            return this.value;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int stackPopThreads = 2;
        int stackPushThreads = 2;
        Random randomIntegerGenerator = new Random();
        List<Thread> threads = new ArrayList<>();
        LockFreeStack<Integer> operStack = new LockFreeStack<>();

        for (int j = 0; j < 10; j++) {
            operStack.push(randomIntegerGenerator.nextInt());
        }

        for (int k = 0; k < stackPushThreads; k++) {
            Thread pushThread = new Thread(() -> {
                System.out.println("Pushing into stack...");
                while (true) {
                    operStack.push(randomIntegerGenerator.nextInt());
                }
            });

            pushThread.setDaemon(true);
            threads.add(pushThread);
        }

        for (int k = 0; k < stackPopThreads; k++) {
            Thread popThread = new Thread(() -> {
                System.out.println(
                        "Popping from stack ...");
                while (true) {
                    operStack.pop();
                }
            });

            popThread.setDaemon(true);
            threads.add(popThread);
        }

        for (Thread thread : threads) {
            thread.start();
        }
        Thread.sleep(500);
        System.out.println("The number of stack operations performed in 1/2 a second-->" + operStack.getNoOfOperations());
    }

    private static class LockFreeStack<T> {
        private final AtomicInteger noOfOperations = new AtomicInteger(0);
        private final AtomicReference<StackNode<T>> headNode = new AtomicReference<>();

        public int getNoOfOperations() {
            return noOfOperations.get();
        }

        public void push(T value) {
            StackNode<T> newHead = new StackNode<T>(value);
            while (true) {
                StackNode<T> currentHeadNode = headNode.get();
                newHead.next = currentHeadNode;
                if (headNode.compareAndSet(currentHeadNode, newHead)) {
                    break;
                } else {
                    LockSupport.parkNanos(1);
                }
            }
            noOfOperations.incrementAndGet();
        }

        public T pop() {
            StackNode<T> currentHeadNode = headNode.get();
            while (currentHeadNode != null) {
                StackNode<T> newHead = currentHeadNode.next;
                if (headNode.compareAndSet(currentHeadNode,
                        newHead)) {
                    break;
                } else {
                    LockSupport.parkNanos(1);
                    currentHeadNode = headNode.get();
                }
            }
            noOfOperations.incrementAndGet();
            return currentHeadNode != null ? currentHeadNode.value : null;
        }
    }

    private static class ClassicStack<T> {
        private StackNode<T> headNode;
        private int noOfOperations;

        public synchronized int getNoOfOperations() {
            return noOfOperations;
        }

        public synchronized void push(T number) {
            StackNode<T> newNode = new StackNode<T>(number);
            newNode.next = headNode;
            headNode = newNode;
            noOfOperations++;
        }

        public synchronized T pop() {
            if (headNode == null)
                return null;
            else {
                T val = headNode.getValue();
                StackNode<T> newHead = headNode.next;
                headNode.next = newHead;
                noOfOperations++;
                return val;
            }
        }
    }
}
