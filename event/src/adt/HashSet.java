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

public class HashSet<T> implements HashSetInterface<T>, Iterable<T> { // Implement Iterable
    private Object[] table;
    private int size;
    private static final int INITIAL_CAPACITY = 16;

    public HashSet() {
        table = new Object[INITIAL_CAPACITY];
        size = 0;
    }

    @Override
    public boolean add(T element) {
        if (element == null) {
            throw new IllegalArgumentException("Element cannot be null.");
        }
        if (contains(element)) {
            return false; // Element already exists
        }
        ensureCapacity();
        int index = getIndex(element);
        table[index] = element;
        size++;
        return true;
    }

    @Override
    public boolean remove(T element) {
        if (element == null) {
            throw new IllegalArgumentException("Element cannot be null.");
        }
        int index = getIndex(element);
        if (table[index] != null && table[index].equals(element)) {
            table[index] = null; // Remove the element
            size--;
            return true;
        }
        return false; // Element not found
    }

    @Override
    public boolean contains(T element) {
        if (element == null) {
            throw new IllegalArgumentException("Element cannot be null.");
        }
        int index = getIndex(element);
        return table[index] != null && table[index].equals(element);
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        for (int i = 0; i < table.length; i++) {
            table[i] = null; // Clear each element
        }
        size = 0;
    }

    private int getIndex(T element) {
        return Math.abs(element.hashCode()) % table.length;
    }

    private void ensureCapacity() {
        if (size >= table.length * 0.75) {
            int newCapacity = table.length * 2;
            Object[] newTable = new Object[newCapacity];
            for (int i = 0; i < table.length; i++) {
                if (table[i] != null) {
                    int newIndex = Math.abs(table[i].hashCode()) % newCapacity;
                    newTable[newIndex] = table[i];
                }
            }
            table = newTable;
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new HashSetIterator(); // Return iterator
    }

    // Inner class to implement Iterator
    private class HashSetIterator implements Iterator<T> {
        private int currentIndex = 0;
        private int elementsReturned = 0;

        @Override
        public boolean hasNext() {
            return elementsReturned < size; // Check if there are more elements
        }

        @Override
        public T next() {
            while (currentIndex < table.length) {
                if (table[currentIndex] != null) {
                    elementsReturned++;
                    return (T) table[currentIndex++]; // Return the current element and move to the next
                }
                currentIndex++;
            }
            throw new java.util.NoSuchElementException("No more elements in the HashSet.");
        }
    }
}