/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package adt;

/**
 *
 * @author eyong
 */
public interface SetInterface<T>extends Iterable<T> {  
    boolean add(T element);  
    boolean remove(T element);  
    boolean contains(T element);  
    boolean isEmpty();  
    int size();  
    void clear();  
}  