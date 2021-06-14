/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

/**
 *
 * @author Radu
 */
//Toate implementarile din pachetul db sunt clase necesare pentru implementarea claselor din pachetul dao ce permite comunicarea cu baza de date.
//Pentru un MVC, aceste clase trebuie sa aiba doar setter si getter pentru fiecarare atribut, precum si un constructor unde este cazul.
public class DishClass {
    
    private int classID;
    private int restaurantID;
    private String name;

    public void setClassID(int classID) {
        this.classID = classID;
    }

    public void setRestaurantID(int restID) {
        this.restaurantID = restID;
    }
     
    public void setName(String name) {
        this.name = name;
    }

    public int getClassID() {
        return classID;
    }
    
    public int getRestaurantID() {
        return restaurantID;
    }

    public String getName() {
        return name;
    }
}