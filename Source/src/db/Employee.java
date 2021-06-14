/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.util.Date;

/**
 *
 * @author Radu
 */
//Toate implementarile din pachetul db sunt clase necesare pentru implementarea claselor din pachetul dao ce permite comunicarea cu baza de date.
//Pentru un MVC, aceste clase trebuie sa aiba doar setter si getter pentru fiecarare atribut, precum si un constructor unde este cazul.
public class Employee {
    
    private int employeeID;
    private int areaID; 
    private String name;
    private String country;
    private String city;
    private String region;
    private String adress;
    private String phoneNumber;
    private Date birthDate;
    private int sex;
    private String PIN;
    private String function;
    private boolean atWork;
    private double salary;
    private byte [] photo;

    public Employee(){
        
    }
    
    public Employee(int areaID, String name, String country, String city, String region, String adress, String phoneNumber, Date birthDate, int sex, String PIN, String function, double salary, byte [] photo) {
        this.areaID = areaID;
        this.name = name;
        this.country = country;
        this.city = city;
        this.region = region;
        this.adress = adress;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.sex = sex;
        this.PIN = PIN;
        this.function = function;
        this.atWork = false;
        this.salary = salary;
        this.photo = photo;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public void setAreaID(int areaID) {
        this.areaID = areaID;
    }
    
    public void setName(String name) {
        this.name = name;
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

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
   
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }
    
    public void setSex(int sex) {
        this.sex = sex;
    }
    public void setPIN(String PIN) {
        this.PIN = PIN;
    }

    public void setFunction(String function) {
        this.function = function;
    }
    
    public void setAtWork(boolean atWork) {
        this.atWork = atWork;
    }
    
    public void setSalary(double salary) {
        this.salary = salary;
    }

    public void setPhoto(byte [] photo) {
        this.photo = photo;
    }
    
    public int getEmployeeID() {
        return employeeID;
    }

    public int getAreaID() {
        return areaID;
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

    public Date getBirthDate() {
        return birthDate;
    }
    
    public int getSex() {
        return sex;
    }
    
    public String getPIN() {
        return PIN;
    }

    public String getFunction() {
        return function;
    }

    public boolean isAtWork() {
        return atWork;
    }
    
    public double getSalary() {
        return salary;
    }
    
    public byte [] getPhoto() {
        return photo;
    }
}
