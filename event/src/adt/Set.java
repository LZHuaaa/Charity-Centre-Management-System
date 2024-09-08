/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adt;
import java.util.Iterator;  

/**
 *
 * @author eyong
 */
public class Set<T> implements SetInterface<T>, Iterable<T> {  

    private LinkedList<T> list;

    public Set() {
        list = new LinkedList<>();
    }

    @Override
    public boolean add(T element) {
        if (contains(element)) {
            return false; // Element already exists
        }
        list.add(element);
        return true;
    }

    @Override
    public boolean remove(T element) {
        int index = list.indexOf(element);
        if (index == -1) {
            return false; // Element not found
        }
        list.remove(index);
        return true;
    }

    @Override
    public boolean contains(T element) {
        return list.indexOf(element) != -1;
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public void clear() {
        list.clear();
    }
    @Override  
    public Iterator<T> iterator() {  
        return list.iterator(); // Delegate to LinkedList's iterator  
    }  
}