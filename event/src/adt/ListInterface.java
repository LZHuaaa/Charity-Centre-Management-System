/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adt;


/**
 *
 * @author leezh
 */

public interface ListInterface<T> extends Iterable<T>{

    boolean add(T element);

    boolean add(int newPosition, T newEntry);

    boolean remove(T element);

    T remove(int givenPosition);

    T getEntry(int givenPosition);

    T replace(int givenPosition, T newEntry);

    boolean isEmpty();

    boolean isFull();

    int size();

    boolean contains(T anEntry);

    boolean clear();

    int indexOf(T element);

    int lastIndexOf(T element);

    void reverse();
    
    boolean validateIndex(int index);

    boolean validateItem(T element);
    
    void checkCapacity();

}