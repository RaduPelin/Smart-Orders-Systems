/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.sql.Time;
import java.util.Date;

/**
 *
 * @author Radu
 */
//Toate implementarile din pachetul db sunt clase necesare pentru implementarea claselor din pachetul dao ce permite comunicarea cu baza de date.
//Pentru un MVC, aceste clase trebuie sa aiba doar setter si getter pentru fiecarare atribut, precum si un constructor unde este cazul.
public class Order {
    
    private int orderID;
    private int tableID;
    private Time processingTime;
    private Time preparingTime;
    private Date date;

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public void setTableID(int tableID) {
        this.tableID = tableID;
    }

    public void setProcessingTime(Time processingTime) {
        this.processingTime = processingTime;
    }

    public void setPreparingTime(Time preparingTime) {
        this.preparingTime = preparingTime;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getOrderID() {
        return orderID;
    }

    public int getTableID() {
        return tableID;
    }

    public Time getProcessingTime() {
        return processingTime;
    }

    public Time getPreparingTime() {
        return preparingTime;
    }

    public Date getDate() {
        return date;
    }   
}
