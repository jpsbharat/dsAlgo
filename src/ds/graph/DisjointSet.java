package ds.graph;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class DisjointSet<T> {
    private final Map<T, Node<T>> elements = new HashMap<>();

    public DisjointSet(Collection<? extends T> elems){
        for (T elem : elems) {
            elements.put(elem, new Node<>(elem, elem));
        }
    }

    public void add(T elem) {
        if (!elements.containsKey(elem)) {
            elements.put(elem, new Node<>(elem, elem));
        }
    }

    public T find(T elem) {
        Node<T> pNode = findParentNode(elem);
        return (pNode != null) ? pNode.pKey : null;
    }

    private Node<T> findParentNode(T elem) {
        if (!elements.containsKey(elem)) {
            return null;
        }
        return find0(elem);
    }

    private Node<T> find0(T elem) {
        Node<T> info = elements.get(elem);

        if (info.pKey.equals(elem))
            return info;

        info = find0(info.pKey);
        return info;
    }

    public void union(T one, T two) {
        Node<T> nodeOne = findParentNode(one);
        Node<T> nodeTwo = findParentNode(two);

        if (nodeOne == null || nodeTwo == null || nodeOne == nodeTwo)
            return;

        if (nodeOne.rank > nodeTwo.rank) {
            nodeTwo.pKey = nodeOne.pKey;
            nodeOne.size += nodeTwo.size;
        } else if (nodeOne.rank < nodeTwo.rank) {
            nodeOne.pKey = nodeTwo.pKey;
            nodeTwo.size += nodeOne.size;
        } else {
            nodeTwo.pKey = nodeOne.pKey;
            nodeOne.size += nodeTwo.size;
            nodeOne.rank++;
        }
    }

    public static final class Node<T> {
        public T keys;
        public T pKey;
        public int size = 0;
        public int rank = 0;

        Node(T keys, T pKey) {
            this.keys = keys;
            this.pKey = pKey;
            this.size = 1;
        }
    }
}
