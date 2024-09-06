/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adt;

import entity.Donee;

/**
 *
 * @author leezh
 */
public class HashMap<K, V> implements HashMapInterface<K, V> {

    private K key;
    private V value;
    private HashMap<K, V> next;
    private HashMap<K, V>[] bucketArray;
    private int capacity = 10; // Default capacity
    private int size = 0;

    public HashMap() {
        bucketArray = new HashMap[capacity];
    }

    private int hashFunction(K key) {
        return Math.abs(key.hashCode()) % bucketArray.length;
    }

    @Override
    public void put(K key, V value) {
        int index = hashFunction(key);
        HashMap<K, V> newNode = new HashMap<>();
        newNode.key = key;
        newNode.value = value;

        if (bucketArray[index] == null) {
            bucketArray[index] = newNode;
        } else {
            // Collision handling using chaining
            HashMap<K, V> current = bucketArray[index];
            while (current.next != null) {
                if (current.key.equals(key)) {
                    current.value = value; // Update the value if key already exists
                    return;
                }
                current = current.next;
            }
            current.next = newNode; // Add the new node to the end of the chain
        }
        size++;
    }

    @Override
    public V get(K key) {
        int index = hashFunction(key);
        HashMap<K, V> current = bucketArray[index];
        while (current != null) {
            if (current.key.equals(key)) {
                return current.value;
            }
            current = current.next;
        }
        return null; // Return null if the key is not found
    }

    public V get(int key) {
        // Convert integer key to generic key type K
        K genericKey = (K) Integer.valueOf(key);
        return get(genericKey);
    }

    @Override
    public boolean remove(K key) {
        int index = hashFunction(key);
        HashMap<K, V> current = bucketArray[index];
        HashMap<K, V> previous = null;

        while (current != null) {
            if (current.key.equals(key)) {
                if (previous == null) {
                    // Removing the first node in the bucket
                    bucketArray[index] = current.next;
                } else {
                    // Removing a node in the middle or end
                    previous.next = current.next;
                }

                return true; // Node removed successfully
            }
            previous = current;
            current = current.next;
        }

        return false; // Key not found
    }

    public V removeValue(K key) {
        int index = hashFunction(key);
        HashMap<K, V> current = bucketArray[index];
        HashMap<K, V> previous = null;

        while (current != null) {
            if (current.key.equals(key)) {
                if (previous == null) {

                    bucketArray[index] = current.next;
                } else {

                    previous.next = current.next;
                }

                size--;
                return current.value;

            }
            previous = current;
            current = current.next;
        }

        return null;
    }

    @Override
    public int size() {
        int size = 0;
        for (HashMap<K, V> bucket : bucketArray) {
            HashMap<K, V> current = bucket;
            while (current != null) {
                size++;
                current = current.next;
            }
        }
        return size;
    }

    @Override
    public void clear() {
        // Set all elements in the bucket array to null, effectively clearing the map
        for (int i = 0; i < bucketArray.length; i++) {
            bucketArray[i] = null;
        }
        size = 0; // Reset the size to 0
    }

    @Override
    public boolean containsKey(K key) {
        int index = hashFunction(key);
        HashMap<K, V> current = bucketArray[index];

        while (current != null) {
            if (current.key.equals(key)) {
                return true; // Key found
            }
            current = current.next;
        }
        return false; // Key not found
    }
}
