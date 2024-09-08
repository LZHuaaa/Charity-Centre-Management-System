/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adt;
import java.util.Iterator;

/**
 *
 * @author leezh
 */
public class LinkedList<T> implements ListInterface<T> , Iterable<T> { 

    private Node<T> head;
    private int size;


    @Override
    public boolean isFull() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean validateIndex(int index) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean validateItem(T element) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void checkCapacity() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

  

    @Override
    public boolean clear() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private static class Node<T> {
        T data;
        Node<T> next;

        public Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    public LinkedList() {
        this.head = null;
        this.size = 0;
    }

    @Override
    public boolean add(T element) {
        if (element == null) return false;

        Node<T> newNode = new Node<>(element);

        if (head == null) {
            head = newNode;
        } else {
            Node<T> current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        size++;
        return true;
    }

    @Override
    public boolean add(int index, T newEntry) {
        if (index < 0 || index > size || newEntry == null) return false;

        Node<T> newNode = new Node<>(newEntry);

        if (index == 0) {
            newNode.next = head;
            head = newNode;
        } else {
            Node<T> previous = head;
            for (int i = 0; i < index - 1; i++) {
                previous = previous.next;
            }
            newNode.next = previous.next;
            previous.next = newNode;
        }

        size++;
        return true;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) return null;

        Node<T> current = head;
        if (index == 0) {
            head = head.next;
        } else {
            Node<T> previous = null;
            for (int i = 0; i < index; i++) {
                previous = current;
                current = current.next;
            }
            previous.next = current.next;
        }

        size--;
        return current.data;
    }
    
     @Override
    public boolean remove(T element) {
        if (head == null || element == null) {
            return false;
        }
        Node<T> current = head;
        Node<T> previous = null;
        while (current != null) {
            if (current.data.equals(element)) {
                if (previous == null) {
                    head = current.next;  // Remove head
                } else {
                    previous.next = current.next;  // Bypass the node to remove
                }
                size--;
                return true;
            }
            previous = current;
            current = current.next;
        }
        return false;  // Element not found
    }
    

    @Override
    public T getEntry(int index) {
        if (index < 0 || index >= size) return null;

        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.data;
    }

    @Override
    public T replace(int index, T newEntry) {
        if (index < 0 || index >= size || newEntry == null) return null;

        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }

        T oldData = current.data;
        current.data = newEntry;
        return oldData;
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
    public boolean contains(T anEntry) {
        Node<T> current = head;
        while (current != null) {
            if (current.data.equals(anEntry)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public int indexOf(T element) {
        if (element == null) return -1;

        Node<T> current = head;
        int index = 0;

        while (current != null) {
            if (current.data.equals(element)) {
                return index;
            }
            current = current.next;
            index++;
        }

        return -1;
    }

    @Override
    public int lastIndexOf(T element) {
        if (element == null) return -1;

        Node<T> current = head;
        int lastIndex = -1;
        int index = 0;

        while (current != null) {
            if (current.data.equals(element)) {
                lastIndex = index;
            }
            current = current.next;
            index++;
        }

        return lastIndex;
    }

    @Override
    public void reverse() {
        Node<T> previous = null;
        Node<T> current = head;
        Node<T> next = null;

        while (current != null) {
            next = current.next;
            current.next = previous;
            previous = current;
            current = next;
        }

        head = previous;
    }
    @Override  
    public Iterator<T> iterator() {  
        return new Iterator<T>() {  
            private Node<T> current = head;  

            @Override  
            public boolean hasNext() {  
                return current != null;  
            }  

            @Override  
            public T next() {  
                if (!hasNext()) {  
                    throw new IllegalStateException("No more elements");  
                }  
                T data = current.data;  
                current = current.next;  
                return data;  
            }  
        };  
    }  
}
