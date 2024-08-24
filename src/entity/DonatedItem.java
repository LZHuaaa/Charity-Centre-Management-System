/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.io.Serializable;

/**
 *
 * @author leezh
 */
public class DonatedItem implements Serializable {

    private String name;
    private int quantity;

    public DonatedItem(String name, int quantity) {
      
        this.name = name;
        this.quantity = quantity;
    }

 

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "DonatedItem{"
                + ", name='" + name + '\''
                + ", quantity=" + quantity
                + '}';
    }
}
