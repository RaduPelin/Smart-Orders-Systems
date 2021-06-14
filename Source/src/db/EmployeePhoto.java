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
public class EmployeePhoto {
    
    private int photoID;
    private int restaurantID;
    private String PIN;
    private byte[] photo;
    
    public EmployeePhoto(){
        
    }
    
    public EmployeePhoto(String PIN, byte[] photo){
        this.PIN = PIN;
        this.photo = photo;
    }
    
    public void setPhotoID(int photoID) {
        this.photoID = photoID;
    }

    public void setRestaurantID(int restaurantID) {
        this.restaurantID = restaurantID;
    }
    
    public void setPIN(String PIN) {
        this.PIN = PIN;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public int getPhotoID() {
        return photoID;
    }

    public int getRestaurantID() {
        return restaurantID;
    }

    public String getPIN() {
        return PIN;
    }

    public byte[] getPhoto() {
        return photo;
    }
}
