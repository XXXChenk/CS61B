/**
 * A deque implementation backed by a doubly linked list.
 *
 * @param <T> the type of item stored in the deque
 */
public class LinkedListDeque<T> {

    private class Node {
        T item;
        Node next;
        Node prev;

        private Node() {
            item = null;
            next = null;
            prev = null;
        }

        private Node(T i, Node n, Node p) {
            item = i;
            next = n;
            prev = p;
        }
    }

    private int size;
    private Node sentinel;

    /**
     * Creates an empty linked list deque.
     */
    public LinkedListDeque() {
        sentinel = new Node();
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    /**
     * Adds an item to the front of the deque.
     *
     * @param item the item to add
     */
    public void addFirst(T item) {
        Node first = new Node(item, sentinel.next, sentinel);
        sentinel.next.prev = first;
        sentinel.next = first;

        size += 1;
    }

    /**
     * Adds an item to the back of the deque.
     *
     * @param item the item to add
     */
    public void addLast(T item) {
        Node last = new Node(item, sentinel, sentinel.prev);
        sentinel.prev.next = last;
        sentinel.prev = last;
        size += 1;
    }

    /**
     * Returns {@code true} if the deque is empty, otherwise {@code false}.
     *
     * @return whether the deque contains no items
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the number of items in the deque.
     *
     * @return the number of items in the deque
     */
    public int size() {
        return this.size;
    }

    /**
     * Prints the items in the deque from first to last, separated by a space.
     */
    public void printDeque() {
        for (Node p = sentinel.next; p != sentinel; p = p.next) {
            System.out.print(p.item + " ");
        }
        System.out.println();
    }

    /**
     * Removes and returns the item at the front of the deque.
     * If no such item exists, returns {@code null}.
     *
     * @return the removed front item, or {@code null} if the deque is empty
     */
    public T removeFirst() {
        if (sentinel.next != sentinel) {
            Node first = sentinel.next;
            sentinel.next = first.next;
            first.next.prev = sentinel;
            size -= 1;
            return first.item;
        }
        return null;
    }

    /**
     * Removes and returns the item at the back of the deque.
     * If no such item exists, returns {@code null}.
     *
     * @return the removed back item, or {@code null} if the deque is empty
     */
    public T removeLast() {
        if (sentinel.prev != sentinel) {
            Node last = sentinel.prev;
            sentinel.prev = last.prev;
            last.prev.next = sentinel;
            size -= 1;
            return last.item;
        }
        return null;
    }

    /**
     * Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
     * If no such item exists, returns {@code null}. Must not alter the deque.
     *
     * @param index the index of the item to retrieve
     * @return the item at the requested index, or {@code null} if the index is invalid
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }

        Node p;
        if (index < size / 2) {
            p = sentinel.next;
            for (int i = 0; i < index; i += 1) {
                p = p.next;
            }
        } else {
            p = sentinel.prev;
            for (int i = size - 1; i > index; i -= 1) {
                p = p.prev;
            }
        }
        return p.item;
    }

    private T getRecursiveHelper(Node node, int index) {
        if (index == 0) {
            return node.item;
        }
        return getRecursiveHelper(node.next, index - 1);
    }

    /**
     * Same as {@link #get(int)}, but uses recursion.
     *
     * @param index the index of the item to retrieve
     * @return the item at the requested index, or {@code null} if the index is invalid
    */
    public T getRecursive(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        return getRecursiveHelper(sentinel.next, index);
    }
}
