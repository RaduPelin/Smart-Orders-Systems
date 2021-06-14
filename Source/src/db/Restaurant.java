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
public class Restaurant {
     
    private int restaurantID;
    private String name;
    private String country;
    private String city;
    private String region;
    private String adress;
    private String phoneNumber;
    private String username;
    private String uPass;
    private String aPass;
    private byte [] logo;

    public Restaurant() {
        
    }

    public Restaurant(String name, String country, String city, String region, String adress, String phoneNumber, String username, String uPass, String aPass, byte[] logo) {
        this.name = name;
        this.country = country;
        this.city = city;
        this.region = region;
        this.adress = adress;
        this.phoneNumber = phoneNumber;
        this.username = username;
        this.uPass = uPass;
        this.aPass = aPass;
        this.logo = logo;
    }
   
    public void setName(String name) {
        this.name = name;
    }

    public void setRestaurantID(int restaurantID) {
        this.restaurantID = restaurantID;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public void setCountry(String country) {
        this.country = country;
    }
    
    public void setRegion(String region) {
        this.region = region;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public void setUsername(String username){
        this.username = username;
    }

    public void setUPass(String uPass) {
        this.uPass = uPass;
    }

    public void setAPass(String aPass) {
        this.aPass = aPass;
    }
    
    public void setLogo(byte [] logo){
        this.logo = logo;
    }
    
    public int getRestaurantID() {
        return restaurantID;
    }   
    
    public String getName() {
        return name;
    }
    
    public String getCountry() {
        return country;
    }
    
    public String getRegion() {
        return region;
    }

   
    public String getCity() {
        return city;
    }
    
    public String getAdress() {
        return adress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getUsername() {
        return username;
    }

    public String getUPass() {
        return uPass;
    }

    public String getAPass() {
        return aPass;
    }
    
    public byte [] getLogo() {
        return logo;
    }
}
