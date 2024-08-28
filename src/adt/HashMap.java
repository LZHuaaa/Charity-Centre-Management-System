/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adt;

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
}
