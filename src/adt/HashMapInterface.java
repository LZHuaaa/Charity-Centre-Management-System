/**
 *
 * @author Lee Zhi Hua
 */
package adt;


public interface HashMapInterface<K, V> {

    void put(K key, V value);
    V get(K key);
    V get(int key);
    boolean remove(K key);
    V removeValue(K key);
    int size();
    
}
