/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package adt;

/**
 *
 * @author eyong
 */

public interface TreeMapInterface<K, V> {  
    boolean put(K key, V value);  
    V get(K key);  
    V remove(K key);  
    boolean containsKey(K key);  
    boolean isEmpty();  
    int size();  
    void clear();  
} 