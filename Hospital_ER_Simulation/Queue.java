package cs;

public class Queue<E> {
    private E[] array;
    private int front;
    private int rear;
    private int length;
    private static final int DEFAULT_CAPACITY = 10;
    
    public Queue() {
        array = (E[]) new Object[DEFAULT_CAPACITY];
        front = 0;
        rear = -1;
        length = 0;
    }

    // Add an element to the queue
    public void add(E item) {
        if (item == null) {
            return;
        }
        if (length == array.length) {
            resize();
        }
        rear = (rear + 1) % array.length;  // Circular increment
        array[rear] = item;
        length++;
    }

    // Remove an element from the queue
    public E remove() {
        if (length == 0) {
            return null;
        }
        E result = array[front];
        front = (front + 1) % array.length;  // Circular increment
        length--;
        return result;
    }

    // Get the current size of the queue
    public int getLength() {
        return length;
    }

    // Resize the array when it's full
    private void resize() {
        int newSize = array.length * 2;
        E[] newArray = (E[]) new Object[newSize];
        for (int i = 0; i < length; i++) {
            newArray[i] = array[(front + i) % array.length];  // Copy elements in order
        }
        array = newArray;
        front = 0;
        rear = length - 1;
    }

    // Get the element at the front of the queue without removing it
    public E getFront() {
        if (length == 0) {
            return null;
        }
        return array[front];
    }
    
    public void addObject(Object item) {
        if (item == null) {
            return;
        }
        // Cast the object to the generic type and call the existing add method
        try {
            E castedItem = (E) item;
            add(castedItem);
        } catch (ClassCastException e) {
            System.err.println("Error: Cannot cast object to the queue's generic type.");
        }
    }
}

