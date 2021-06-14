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
public class Dish {
    
    private int dishID;
    private int classID;
    private String name;
    private double quantity;
    private double price;
    private double preparationTime;
    private boolean state;
    
    public void setDishID(int dishID) {
        this.dishID = dishID;
    }

    public void setClassID(int classID) {
        this.classID = classID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setPreparationTime(double preparationTime) {
        this.preparationTime = preparationTime;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public int getDishID() {
        return dishID;
    }

    public int getClassID() {
        return classID;
    }

    public String getName() {
        return name;
    }

    public double getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public double getPreparationTime() {
        return preparationTime;
    }

    public boolean getState() {
        return state;
    }
}