package cs;

public class Stack<E> {
    private E[] array;
    private int topIndex;
    private int length;
    private static final int DEFAULT_CAPACITY = 10;

    // Constructor
    @SuppressWarnings("unchecked")
    public Stack() {
        array = (E[]) new Object[DEFAULT_CAPACITY];
        topIndex = -1;  // Empty stack
        length = 0;
    }

    // Push an item onto the stack
    public void push(E item) {
        if (item == null) {
            return;
        }
        if (length == array.length) {
            resize();
        }
        array[++topIndex] = item;
        length++;
    }

    // Pop an item from the stack
    public E pop() {
        if (length == 0) {
            return null;
        }
        E result = array[topIndex];
        array[topIndex] = null;  // Help garbage collection
        topIndex--;
        length--;
        return result;
    }

    // Push an Object onto the stack
    public void pushObject(Object item) {
        if (item == null) {
            return;
        }
        if (length == array.length) {
            resize();
        }
        array[++topIndex] = (E) item;  // Cast Object to the generic type E
        length++;
    }
    
    // Get the current size of the stack
    public int getLength() {
        return length;
    }

    // Resize the array when the stack is full
    private void resize() {
        int newSize = array.length * 2;
        E[] newArray = (E[]) new Object[newSize];
        System.arraycopy(array, 0, newArray, 0, length);
        array = newArray;
    }

    // Peek at the top item of the stack without removing it
    public E getTop() {
        if (length == 0) {
            return null;
        }
        return array[topIndex];
    }
}
