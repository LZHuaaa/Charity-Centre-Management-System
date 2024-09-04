/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adt;



public interface ListInterface<T>  {
    boolean add(T element);
    boolean add(int newPosition, T newEntry);
    boolean remove(T element);
    T remove(int givenPosition);
    T getEntry(int givenPosition);    
    T replace(int givenPosition, T newEntry);
    boolean isEmpty();
    boolean isFull(); 
    int size();
    boolean contains(T anEntry);
    boolean clear();
    int indexOf(T element); //retrieve or update item
    int lastIndexOf(T element);//remove or update the most recent item associated with the donee
    void reverse(); //display the most recently added donees first
           
   
}

