/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package adt;

/**
 *
 * @author leezh
 */
public interface LinkedListInterface<T> {

    boolean add(T element);

    boolean add(int index, T newEntry);

    T remove(int index);

    T getEntry(int index);

    T replace(int index, T newEntry);

    boolean isEmpty();

    int size();

    void clear();

    boolean contains(T anEntry);

    int indexOf(T element);

    int lastIndexOf(T element);

    void reverse();
}
