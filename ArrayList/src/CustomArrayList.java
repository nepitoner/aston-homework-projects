import java.util.Arrays;
import java.util.Comparator;

public class CustomArrayList<T extends Comparable<T>> {
    private T[] data;
    private final static int DEFAULT_CAPACITY = 10;
    private int dataFillSize;

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

    public void add(T newElem) {
        if (isFull()) {
            resize();
        }
        data[dataFillSize++] = newElem;
    }

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

    public T get(int index) {
        checkIndex(index);
        return data[index];
    }

    public void removeByIndex(int index) {
        checkIndex(index);
        int numMoved = dataFillSize - index - 1;
        System.arraycopy(data, index + 1, data, index, numMoved);
        data[--dataFillSize] = null;
    }

    public void removeByValue(T value) {
        for (int i = 0; i < dataFillSize; i++) {
            if (data[i].equals(value)) {
                removeByIndex(i);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public void clear() {
        data = (T[]) new Comparable[DEFAULT_CAPACITY];
        dataFillSize = 0;
    }

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

    @Override
    public String toString() {
        return Arrays.toString(data);
    }
}
