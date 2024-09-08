/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adt;

/**
 *
 * @author leezhihua
 */
public interface MapInterface<K, V> {

    void put(K key, V value);

    V get(K key);

    V get(int key);

    boolean remove(K key);

    V removeValue(K key);

    int size();

    ArrayList<V> values();

    boolean containsKey(K key);

    boolean containsValue(V value);

    void clear();

    Set<K> keySet();
    
    V getOrDefault(K key, V defaultValue);
}