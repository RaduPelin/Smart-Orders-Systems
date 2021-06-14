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
public class Resource {
    
    private int resourceID;
    private String name;
    private byte [] resource;

    public void setResourceID(int resourceID) {
        this.resourceID = resourceID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setResource(byte[] resource) {
        this.resource = resource;
    }

    public int getResourceID() {
        return resourceID;
    }

    public String getName() {
        return name;
    }

    public byte[] getResource() {
        return resource;
    }
}
