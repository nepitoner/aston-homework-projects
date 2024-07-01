import java.util.Comparator;

/**
 * A custom implementation of Linked List with references to both the next and previous element
 * <p> Opportunities:
 * <p> - insertion and removal of elements at both the beginning and end
 * <p> - insertion at any index
 * <p> - obtaining the element by index
 * <p> - sorting
 *
 * @param <T> the type of elements in the list
 * @author Oksana Shik
 */

public class CustomLinkedList<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size = 0;

    /**
     * Represents a node in the Linked List
     * Each node contains an element of type T and references to both nest and previous node.
     * @param <T> the type of elements in the node
     */
    private static class Node<T> {
        T element;
        Node<T> next;
        Node<T> prev;

        private Node(T element) {
            this.element = element;
        }
    }

    /**
     *  Removes all element from the list
     */
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new ArrayIndexOutOfBoundsException("Index " + index +
                    " is out of bounds for list of length " + size);
        }
    }

    /**
     * Returns the element from the list by its index
     * @param index the index of the element in the list
     * @return the element at the required index
     * @throws ArrayIndexOutOfBoundsException if the index is out of bounds
     */
    public T get(int index) {
        checkIndex(index);
        Node<T> temp = head;
        int i = 0;

        while (i != index) {
            temp = temp.next;
            i++;
        }
        return temp.element;
    }

    /**
     * Adds the specified element to the beginning of the list
     * @param elem the element to be added
     */
    public void addFirst(T elem) {
        Node<T> newNode = new Node<>(elem);
        newNode.next = head;
        head = newNode;

        if (tail == null) {
            tail = newNode;
        }
        size++;
    }

    /**
     * Adds the specified element to the end of the list
     * @param elem the element to be added
     */
    public void addLast(T elem) {
        if (tail == null) {
            addFirst(elem);
            return;
        }

        Node<T> newNode = new Node<>(elem);
        newNode.prev = tail;
        tail.next = newNode;

        tail = newNode;
        size++;
    }

    /**
     * Adds the specified element at the specified index in the list
     * @param elem the element to be added
     * @param index the index at which the element should be added
     * @throws ArrayIndexOutOfBoundsException if the index is out of bounds
     */
    public void add(T elem, int index) {
        checkIndex(index);
        if (index == 0) {
            addFirst(elem);
            return;
        }
        if (index == size - 1) {
            addLast(elem);
            return;
        }

        Node<T> temp = head;
        Node<T> futureNextNode = null;
        Node<T> futurePrevNode = null;
        int i = 0;

        while(temp != null){
            if (i == index) {
                futureNextNode = temp;
                break;
            }
            futurePrevNode = temp;
            temp = temp.next;
            i++;
        }
        Node<T> newNode = new Node<>(elem);
        newNode.next = futureNextNode;
        newNode.prev = futurePrevNode;

        futurePrevNode.next = newNode;
        futureNextNode.prev = newNode;
        size++;
    }

    /**
     * Adds the specified element to the end of the list
     * @param elem the element to be added
     */
    public void add(T elem) {
        addLast(elem);
    }

    /**
     * Removes the last element from the list
     */
    public void removeLast() {
        Node<T> temp = tail.prev;
        temp.next = null;
        tail = temp;
        size--;
    }

    /**
     * Removes the first element from the list
     */
    public void removeFirst() {
        Node<T> temp = head.next;
        temp.prev = null;
        head = temp;
        size--;
    }

    /**
     * Removes the first occurrence of the specified element from the list
     * @param value the value of the element to be removed
     */
    public void removeByValue(T value) {
        Node<T> temp = head;

        while(temp != null){
            if (temp.element == value) {
                if (temp.element == head.element) {
                    temp.next.prev = null;
                    head = temp.next;
                    break;
                }

                if (temp.element == tail.element) {
                    temp.prev.next = null;
                    tail = temp.prev;
                    break;
                }

                temp.prev.next = temp.next;
                temp.next.prev = temp.prev;
            }
            temp = temp.next;
        }
        size--;
    }

    /**
     * Returns the number of the elements in the list
     * @return the number of the elements in the list
     */
    public int getSize() {
        return size;
    }

    /**
     * Sorts the elements in the list in descending order or ascending order
     * @param reverse marker of the sort order to be reversed
     */
    @SuppressWarnings("unchecked")
    public void sort(boolean reverse) {
        if (head == null || head.next == null) {
            return;
        }
        Comparator<T> comparator;
        if (reverse) {
            comparator = (Comparator<T>) Comparator.reverseOrder();
        } else { comparator = (Comparator<T>) Comparator.naturalOrder();}
        Node<T> current = head;

        while (current != null) {
            Node<T> index = current.next;
            Node<T> min = current;

            while (index != null) {
                if (comparator.compare(index.element, min.element) < 0) {
                    min = index;
                }
                index = index.next;
            }
            T tempVal = current.element;
            current.element = min.element;
            min.element = tempVal;

            current = current.next;
        }
    }

    /**
     * Returns a string representation of the list, where the elements are
     * separated by an arrow
     *
     * @return a string representation of the list
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        Node<T> temp = head;
        while(temp != null){
            if (temp == tail) {
                builder.append(temp.element);
            } else {
                builder.append(temp.element).append(" -> ");
            }
            temp = temp.next;
        }
        return builder.toString();
    }
}
