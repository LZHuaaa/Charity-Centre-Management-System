/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author eyong
 */
import java.util.Objects;  

public class Volunteer implements Comparable<Volunteer> {  
    private String id;  
    private String name;  
    private String phoneNumber;  
    private String email;  

    public Volunteer(String id, String name, String phoneNumber, String email) {  
        this.id = id;  
        this.name = name;  
        this.phoneNumber = phoneNumber;  
        this.email = email;  
    }  

    public String getId() {  
        return id;  
    }  

    public String getName() {  
        return name;  
    }  

    public String getPhoneNumber() {  
        return phoneNumber;  
    }  

    public String getEmail() {  
        return email;  
    }  

    @Override  
    public String toString() {  
        return "Volunteer{" +  
                "id='" + id + '\'' +  
                ", name='" + name + '\'' +  
                ", phoneNumber='" + phoneNumber + '\'' +  
                ", email='" + email + '\'' +  
                '}';  
    }  

    @Override  
    public boolean equals(Object obj) {  
        if (this == obj) return true;  
        if (obj == null || getClass() != obj.getClass()) return false;  
        Volunteer volunteer = (Volunteer) obj;  
        return id.equals(volunteer.id);  
    }  

    @Override  
    public int hashCode() {  
        return Objects.hash(id);  
    }  

    @Override  
    public int compareTo(Volunteer o) {  
        return this.name.compareTo(o.name);  
    }  
}  