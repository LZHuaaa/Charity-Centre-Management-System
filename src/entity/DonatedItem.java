/*
 * @author Lee Zhi Hua
 */
package entity;

import java.util.Objects;



public class DonatedItem {

   
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
    
    @Override
public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    DonatedItem item = (DonatedItem) obj;
    return name.equals(item.name);
}

@Override
public int hashCode() {
    return Objects.hash(name);
}

}
