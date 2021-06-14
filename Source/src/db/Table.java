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
public class Table {
    
    private int tableID;
    private int areaID;
    private String name;
    private int type;
    private boolean state;
    private int number;

    public void setTableID(int tableID) {
        this.tableID = tableID;
    }

    public void setAreaID(int areaID) {
        this.areaID = areaID;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public void setType(int type) {
        this.type = type;
    }
    
    public void setState(boolean state) {
        this.state = state;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getTableID() {
        return tableID;
    }

    public int getAreaID() {
        return areaID;
    }

    public String getName() {
        return name;
    }
    
    public int getType() {
        return type;
    }
    
    public boolean isState() {
        return state;
    }  
    
    public int getNumber() {
        return number;
    }
}
