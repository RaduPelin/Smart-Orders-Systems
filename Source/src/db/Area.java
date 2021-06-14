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
public class Area {
    
    private int areaID;
    private int restaurantID;
    private String name;
    private int order;

    public Area(){
        this(null, 0);
    }

    public Area(String name, int order){
        this.name = name;
        this.order = order;
    }    
    
    public void setAreaID(int areaID) {
        this.areaID = areaID;
    }

    public void setRestaurantID(int restaurantID) {
        this.restaurantID = restaurantID;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public void setOrder(int order) {
        this.order = order;
    }
    
    public int getAreaID() {
        return areaID;
    }

    public int getRestaurantID() {
        return restaurantID;
    }

    public String getName() {
        return name;
    }
    
    public int getOrder() {
        return order;
    }
}
