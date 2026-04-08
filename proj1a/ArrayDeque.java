/**
 * An array-backed deque that uses a circular, resizing array.
 *
 * @param <T> the type of item stored in the deque
 */
public class ArrayDeque<T> {
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;
    private static final int INIT_CAP = 8;

    /**
     * Creates an empty array deque.
     */
    public ArrayDeque() {
        items = (T[]) new Object[INIT_CAP];
        size = 0;
        nextFirst = 0;
        nextLast = 1;
    }

    private int plusOne(int index) {
        return (index + 1) % items.length;
    }

    private int minusOne(int index) {
        return (index - 1 + items.length) % items.length;
    }

    private void resize(int newCap) {
        T[] newItems = (T[]) new Object[newCap];
        int cur = plusOne(nextFirst);
        for (int i = 0; i < size; i++) {
            newItems[i] = items[cur];
            cur = plusOne(cur);
        }
        items = newItems;
        nextFirst = newCap - 1;
        nextLast = size;
    }

    /**
     * Adds an item to the front of the deque.
     *
     * @param item the item to add
     */
    public void addFirst(T item) {
        if (size == items.length) {
            resize(items.length * 2);
        }
        items[nextFirst] = item;
        nextFirst = minusOne(nextFirst);
        size++;
    }

    /**
     * Adds an item to the back of the deque.
     *
     * @param item the item to add
     */
    public void addLast(T item) {
        if (size == items.length) {
            resize(items.length * 2);
        }
        items[nextLast] = item;
        nextLast = plusOne(nextLast);
        size++;
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
     * @return the deque size
     */
    public int size() {
        return size;
    }

    /**
     * Prints the items in the deque from first to last, separated by a space.
     */
    public void printDeque() {
        int cur = plusOne(nextFirst);
        for (int i = 0; i < size; i++) {
            System.out.print(items[cur]);
            if (i < size - 1) {
                System.out.print(" ");
            }
            cur = plusOne(cur);
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
        if (isEmpty()) {
            return null;
        }
        int firstIndex = plusOne(nextFirst);
        T value = items[firstIndex];
        items[firstIndex] = null;
        nextFirst = firstIndex;
        size--;
        checkUsageAndShrink();
        return value;
    }

    /**
     * Removes and returns the item at the back of the deque.
     * If no such item exists, returns {@code null}.
     *
     * @return the removed back item, or {@code null} if the deque is empty
     */
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        int lastIndex = minusOne(nextLast);
        T value = items[lastIndex];
        items[lastIndex] = null;
        nextLast = lastIndex;
        size--;
        checkUsageAndShrink();
        return value;
    }

    /**
     * Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
     * If no such item exists, returns {@code null}. Must not modify the deque.
     *
     * @param index the position of the item to retrieve
     * @return the item at the requested index, or {@code null} if the index is invalid
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        int start = plusOne(nextFirst);
        int actualIndex = (start + index) % items.length;
        return items[actualIndex];
    }

    private void checkUsageAndShrink() {
        int len = items.length;
        if (len >= 16 && size < len / 4) {
            resize(Math.max(INIT_CAP, len / 2));
        }
    }
}

