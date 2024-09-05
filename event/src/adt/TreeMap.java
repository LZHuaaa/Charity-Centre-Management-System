/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adt;

/**
 *
 * @author eyong
 */

import java.util.Comparator;  

public class TreeMap<K, V> implements TreeMapInterface<K, V> {  
    private Node<K, V> root;  
    private int size;  
    private Comparator<? super K> comparator;  

    public TreeMap() {  
        this.size = 0;  
        this.root = null;  
        this.comparator = null;  
    }  

    public TreeMap(Comparator<? super K> comparator) {  
        this.size = 0;  
        this.root = null;  
        this.comparator = comparator;  
    }  

    private static class Node<K, V> {  
        K key;  
        V value;  
        Node<K, V> left;  
        Node<K, V> right;  

        Node(K key, V value) {  
            this.key = key;  
            this.value = value;  
            this.left = null;  
            this.right = null;  
        }  
    }  

    @Override  
    public boolean put(K key, V value) {  
        if (key == null) {  
            throw new IllegalArgumentException("Key cannot be null.");  
        }  
        root = put(root, key, value);  
        return true;  
    }  

    private Node<K, V> put(Node<K, V> node, K key, V value) {  
        if (node == null) {  
            size++;  
            return new Node<>(key, value);  
        }  

        int cmp = compare(key, node.key);  
        if (cmp < 0) {  
            node.left = put(node.left, key, value);  
        } else if (cmp > 0) {  
            node.right = put(node.right, key, value);  
        } else {  
            node.value = value; // Update value if key already exists  
        }  
        return node;  
    }  

    @Override  
    public V get(K key) {  
        if (key == null) {  
            throw new IllegalArgumentException("Key cannot be null.");  
        }  
        return get(root, key);  
    }  

    private V get(Node<K, V> node, K key) {  
        if (node == null) {  
            return null;  
        }  

        int cmp = compare(key, node.key);  
        if (cmp < 0) {  
            return get(node.left, key);  
        } else if (cmp > 0) {  
            return get(node.right, key);  
        } else {  
            return node.value; // Key found  
        }  
    }  

    @Override  
    public V remove(K key) {  
        if (key == null) {  
            throw new IllegalArgumentException("Key cannot be null.");  
        }  
        V value = get(key);  
        if (value != null) {  
            root = remove(root, key);  
            size--;  
        }  
        return value;  
    }  

    private Node<K, V> remove(Node<K, V> node, K key) {  
        if (node == null) {  
            return null;  
        }  

        int cmp = compare(key, node.key);  
        if (cmp < 0) {  
            node.left = remove(node.left, key);  
        } else if (cmp > 0) {  
            node.right = remove(node.right, key);  
        } else {  
            // Node with only one child or no child  
            if (node.left == null) {  
                return node.right;  
            } else if (node.right == null) {  
                return node.left;  
            }  

            // Node with two children: Get the inorder successor (smallest in the right subtree)  
            node.key = minValue(node.right);  
            node.value = get(node.right, node.key);  
            node.right = remove(node.right, node.key);  
        }  
        return node;  
    }  

    private K minValue(Node<K, V> node) {  
        K minValue = node.key;  
        while (node.left != null) {  
            minValue = node.left.key;  
            node = node.left;  
        }  
        return minValue;  
    }  

    @Override  
    public boolean containsKey(K key) {  
        return get(key) != null;  
    }  

    @Override  
    public boolean isEmpty() {  
        return size == 0;  
    }  

    @Override  
    public int size() {  
        return size;  
    }  

    @Override  
    public void clear() {  
        root = null;  
        size = 0;  
    }  

    private int compare(K key1, K key2) {  
        if (comparator != null) {  
            return comparator.compare(key1, key2);  
        }  
        return ((Comparable<? super K>) key1).compareTo(key2);  
    }  
}