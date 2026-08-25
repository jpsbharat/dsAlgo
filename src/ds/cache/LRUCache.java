package ds.cache;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class LRUCache<K, V> {
    private final int size;
    private final Map<K, V> cache;
    private final LinkedList<K> lruKeys;

    public LRUCache(int size) {
        super();
        this.size = size;
        this.cache = new HashMap<>();
        this.lruKeys = new LinkedList<>();
    }

    public void put(K k, V v) {
        if (cache.size() >= size) {
            K key = lruKeys.removeFirst();
            cache.remove(key);
        }

        cache.put(k, v);
        lruKeys.addLast(k);
    }

    public V get(K k) {
        if (cache.containsKey(k)) {
            V v = cache.get(k);
            cache.remove(k);
            lruKeys.remove(k);

            cache.put(k, v);
            lruKeys.addLast(k);
            return v;
        }

        return null;
    }

    public void print() {
        System.out.println(cache);
        System.out.println(lruKeys);
    }

    public static void main(String[] args) {
        LRUCache<String, Float> cache = new LRUCache<>(4);
        cache.put("1", 1.1f);
        cache.put("2", 1.2f);
        cache.put("3", 1.3f);
        cache.print();
        cache.put("4", 1.4f);
        cache.print();
        cache.put("5", 1.5f);
        cache.print();
        cache.put("6", 1.6f);
        cache.print();
        System.out.println(cache.get("4"));
        cache.print();
        System.out.println(cache.get("6"));
        cache.print();
        System.out.println(cache.get("3"));
        cache.print();
    }

}
