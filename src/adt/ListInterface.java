/*
Created by Chia Yuxuan,Darren Tan Ke Yu,Kevin Er Yong Xian, Lee Zhi Hua
 */
package adt;


public interface ListInterface<T> {

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
