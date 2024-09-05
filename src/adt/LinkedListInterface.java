/*
author Lee Zhi Hua
 */
package adt;


public interface LinkedListInterface<T> {

    boolean add(T element);

    boolean add(int index, T newEntry);

    T remove(int index);

    boolean remove(T element);

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
