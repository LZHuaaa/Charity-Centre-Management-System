/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adt;

/**
 *
 * @author leezh
 */

    public interface HashMapInterface<K, V> {

    // Adds a key-value pair to the map
    void put(K key, V value);

    // Removes a key-value pair by key
    //void remove(K key);

    // Retrieves a value by key
    V get(K key);

    // Checks if a key is present in the map
    //boolean containsKey(K key);

    // Returns the number of key-value pairs in the map
    //int size();

    // Checks if the map is empty
    //boolean isEmpty();
}

