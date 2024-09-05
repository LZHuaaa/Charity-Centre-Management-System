/*
Created by Chia Yuxuan,Darren Tan Ke Yu,Kevin Er Yong Xian, Lee Zhi Hua
 */
package adt;

public class ArrayList<T> implements ListInterface<T> {

    private T[] array;
    private int size = 0;
    private static final int CAPACITY = 50;

    public ArrayList() {
        array = (T[]) new Object[CAPACITY];
    }

    @Override
    public boolean add(T element) {

        if (validateItem(element)) {
            checkCapacity();
            array[size++] = element;
            return true;
        }

        return false;
    }

    @Override
    public boolean add(int index, T newEntry) {
        if (validateIndex(index) && validateItem(newEntry)) {

            checkCapacity();
            for (int i = size; i > index; i--) {
                array[i] = array[i - 1];
            }

            array[index] = newEntry;
            size++;
            return true;

        }
        return false;
    }

    @Override
    public boolean remove(T element) {

        if (validateItem(element)) {
            for (int i = 0; i < size; i++) {
                if (array[i].equals(element)) {
                    remove(i);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public T remove(int index) {
        validateIndex(index);
        T removeItem = array[index];
        for (int i = index; i < size - 1; i++) {
            array[i] = array[i + 1];
        }

        array[--size] = null;
        return removeItem;
    }

    @Override
    public T getEntry(int index) {
        validateIndex(index);
        return array[index];
    }

    @Override
    public T replace(int index, T newEntry) {

        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("The position should within " + size);
        }

        if (validateItem(newEntry)) {
            T old = array[index];
            array[index] = newEntry;
            return old;
        }
        return null;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean isFull() {
        return size == array.length;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean contains(T element) {

        if (validateItem(element)) {
            for (int i = 0; i < size; i++) {
                if (array[i].equals(element)) {
                    return true;
                }
            }
        }
        return false;

    }

    @Override
    public boolean clear() {
        for (int i = 0; i < size; i++) {
            array[i] = null;
        }
        size = 0;
        return true;
    }

    @Override
    public int indexOf(T item) {
        if (validateItem(item)) {
            for (int i = 0; i < size; i++) {
                if (array[i].equals(item)) {
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(T item) {
        if (validateItem(item)) {
            for (int i = size - 1; i >= 0; i--) {
                if (array[i].equals(item)) {
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    public void reverse() {
        for (int i = 0; i < size / 2; i++) {
            T temp = array[i];
            array[i] = array[size - i - 1];
            array[size - i - 1] = temp;
        }
    }

    @Override
    public void checkCapacity() {
        if (size == array.length) {
            int newCapacity = array.length * 2;
            T[] newArray = (T[]) new Object[newCapacity];
            for (int i = 0; i < size; i++) {
                newArray[i] = array[i];
            }

            array = newArray;

        }

    }

    @Override
    public boolean validateIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("The position should within" + size);

        }

        return true;
    }

    @Override
    public boolean validateItem(T element) {
        if (element == null) {
            throw new IllegalArgumentException("cannot be null.");

        }

        return true;
    }

}
