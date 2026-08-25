package ds.stack;

import java.util.Iterator;
import java.util.Stack;

public class MinStack {
    private final Stack<StackValue> s = new Stack<>();

    public void push(Integer v) {
        s.push(new StackValue(v, Math.min(v, this.min())));
    }

    public Integer pop() {
        StackValue v = s.pop();
        System.out.println(v);
        return v.v;
    }

    public boolean isEmpty() {
        return s.isEmpty();
    }

    public Integer peek() {
        if (!s.isEmpty()) {
            StackValue v = s.peek();
            return v.v;
        }
        return null;
    }

    public Integer min() {
        if (!s.isEmpty()) {
            StackValue v = s.peek();
            return v.min;
        }
        return Integer.MAX_VALUE;
    }

    public Iterator<StackValue> iterator() {
        return s.iterator();
    }

    public static class StackValue {
        private final int v;
        private final int min;

        public StackValue(int value, int min) {
            this.v = value;
            this.min = min;
        }

        @Override
        public String toString() {
            return "StackValue{" +
                    "v=" + v +
                    ", min=" + min +
                    '}';
        }
    }

    public static void main(String[] s) {
        MinStack ms = new MinStack();
        ms.push(9);
        ms.push(10);
        ms.push(11);
        ms.push(8);
        ms.push(7);
        System.out.println(ms.s);
        ms.push(18);
        ms.push(17);
        System.out.println(ms.s);
        ms.push(5);
        ms.push(13);
        System.out.println(ms.s);
        Iterator<MinStack.StackValue> it = ms.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }

        System.out.println("##############");

        while (!ms.isEmpty()) {
            System.out.println(ms.pop());
        }
    }
}
