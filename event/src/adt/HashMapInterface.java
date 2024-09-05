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

    void put(K key, V value);
    V get(K key);
    V get(int key);
    boolean remove(K key);
    V removeValue(K key);
    int size();

    
}
