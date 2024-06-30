import java.util.Arrays;
import java.util.Comparator;

/**
 * A custom implementation of an ArrayList data structure that can store elements of type T
 * <p> Opportunities:
 * <p> - insertion
 * <p> - removing
 * <p> - obtaining the element by index
 * <p> - sorting elements
 * @param <T> the type of elements in the list
 * @author Oksana Shik
 */

public class CustomArrayList<T extends Comparable<T>> {
    private T[] data;
    private final static int DEFAULT_CAPACITY = 10;
    private int dataFillSize;

    /**
     * Constructs a new CustomArrayList with the default capacity of 10
     */
    @SuppressWarnings("unchecked")
    public CustomArrayList() {
        data = (T[]) new Comparable[DEFAULT_CAPACITY];
        dataFillSize = 0;
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        int newSize = (int) (data.length * 1.5 + 1);
        T[] tempArray = (T[]) new Comparable[newSize];
        System.arraycopy(data, 0, tempArray, 0, data.length);
        data = tempArray;
    }

    private Boolean isFull() {
        return data.length == dataFillSize;
    }

    private void checkIndex(int index) {
        if (index >= dataFillSize || index < 0) {
            throw new ArrayIndexOutOfBoundsException("Index " + index +
                    " is out of bounds for array of length " + dataFillSize);
        }
    }

    /**
     * Adds a new element to the end of the list
     * @param newElem the element to be added
     */
    public void add(T newElem) {
        if (isFull()) {
            resize();
        }
        data[dataFillSize++] = newElem;
    }

    /**
     * Inserts a new element at the specified index, shifting the remaining elements to the right
     *
     * @param index    the index at which the new element should be inserted
     * @param newElem  the element to be inserted
     * @throws ArrayIndexOutOfBoundsException if the index is out of bounds
     */
    @SuppressWarnings("unchecked")
    public void add(int index, T newElem) {
        if (index != dataFillSize) {
            checkIndex(index);
        }

        if (isFull()) {
            resize();
        }

        T[] tempArray = (T[])new Comparable[data.length];
        System.arraycopy(data, 0, tempArray, 0, index);
        tempArray[index] = newElem;
        dataFillSize++;
        System.arraycopy(data, index, tempArray, index + 1, dataFillSize - index - 1);
        data = tempArray;
    }

    /**
     * Returns the element from the list by its index
     * @param index the index of the element in the list
     * @return the element at the required index
     * @throws ArrayIndexOutOfBoundsException if the index is out of bounds
     */
    public T get(int index) {
        checkIndex(index);
        return data[index];
    }

    /**
     * Removes the element at the specified index, shifting the remaining elements to the left
     *
     * @param index the index of the element to be removed
     * @throws ArrayIndexOutOfBoundsException if the index is out of bounds
     */
    public void removeByIndex(int index) {
        checkIndex(index);
        int numMoved = dataFillSize - index - 1;
        System.arraycopy(data, index + 1, data, index, numMoved);
        data[--dataFillSize] = null;
    }

    /**
     * Removes the first occurrence of the specified element from the list
     * @param value the value of the element to be removed
     */
    public void removeByValue(T value) {
        for (int i = 0; i < dataFillSize; i++) {
            if (data[i].equals(value)) {
                removeByIndex(i);
            }
        }
    }

    /**
     * Clears the list, resetting the internal array to the default capacity
     */
    @SuppressWarnings("unchecked")
    public void clear() {
        data = (T[]) new Comparable[DEFAULT_CAPACITY];
        dataFillSize = 0;
    }

    /**
     * Sorts the elements in the list in descending order or ascending order
     * @param reverse marker of the sort order to be reversed
     */
    public void sort(boolean reverse) {
        Comparator<T> comparator;
        if (reverse) {
             comparator = Comparator.reverseOrder();
        } else { comparator = Comparator.naturalOrder();}
        for (int i = 0; i < dataFillSize; i++) {
            T val = data[i];
            int j = i - 1;
            for (; j >= 0; j--) {
                if (comparator.compare(val, data[j]) < 0) {
                    data[j + 1] = data[j];
                } else {
                    break;
                }
            }
            data[j + 1] = val;
        }
    }

    /**
     * Returns a string representation of the list
     *
     * @return a string representation of the list
     */
    @Override
    public String toString() {
        return Arrays.toString(data);
    }
}
