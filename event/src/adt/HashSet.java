/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adt;

/**
 *
 * @author eyong
 */
import java.util.Iterator;

public class HashSet<T> implements SetInterface<T>, Iterable<T> {

    private static final int INITIAL_CAPACITY = 16;
    private LinkedList<T>[] table;
    private int size;

    @SuppressWarnings("unchecked")
    public HashSet() {
        table = (LinkedList<T>[]) new LinkedList[INITIAL_CAPACITY];
        size = 0;
    }

    @Override
    public boolean add(T element) {
        if (element == null) {
            throw new NullPointerException("Element cannot be null");
        }

        int index = getIndex(element);
        if (table[index] == null) {
            table[index] = new LinkedList<>();
        }

        if (!table[index].contains(element)) {
            table[index].add(element);
            size++;
            return true;
        }
        return false;
    }

    @Override
    public boolean remove(T element) {
        if (element == null) {
            throw new NullPointerException("Element cannot be null");
        }

        int index = getIndex(element);
        if (table[index] != null) {
            // Find the index of the element in the linked list  
            int elementIndex = table[index].indexOf(element);
            if (elementIndex != -1) {
                // Remove the element by index  
                table[index].remove(elementIndex);
                size--;
                // Check if the linked list is empty and set it to null if so  
                if (table[index].isEmpty()) {
                    table[index] = null;
                }
                return true; // Element was removed  
            }
        }
        return false; // Element not found  
    }

    @Override
    public boolean contains(T element) {
        if (element == null) {
            throw new NullPointerException("Element cannot be null");
        }

        int index = getIndex(element);
        return table[index] != null && table[index].contains(element);
    }

    @Override
    public void clear() {
        table = (LinkedList<T>[]) new LinkedList[INITIAL_CAPACITY];
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private int getIndex(T element) {
        return Math.abs(element.hashCode()) % table.length;
    }
    
    public boolean removeIf(java.util.function.Predicate<? super T> filter) {  
    boolean removed = false;  
    for (int i = 0; i < table.length; i++) {  
        if (table[i] != null) {  
            Iterator<T> iterator = table[i].iterator();  
            while (iterator.hasNext()) {  
                T element = iterator.next();  
                if (filter.test(element)) {  
                    iterator.remove(); // Remove element using the iterator  
                    removed = true;  
                }  
            }  
            // If the bucket becomes empty after removals, set it to null  
            if (table[i].isEmpty()) {  
                table[i] = null;  
            }  
        }  
    }  
    return removed;  
}  

    // Implementing the Iterable interface  
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int currentBucket = 0;
            private Iterator<T> currentIterator = getNextIterator();

            private Iterator<T> getNextIterator() {
                while (currentBucket < table.length) {
                    if (table[currentBucket] != null) {
                        return table[currentBucket].iterator();
                    }
                    currentBucket++;
                }
                return null; // No more elements  
            }

            @Override
            public boolean hasNext() {
                if (currentIterator == null) {
                    return false;
                }
                while (!currentIterator.hasNext()) {
                    currentBucket++;
                    currentIterator = getNextIterator();
                    if (currentIterator == null) {
                        return false;
                    }
                }
                return true;
            }

            @Override
            public T next() {
                return currentIterator.next();
            }
        };
    }
}
