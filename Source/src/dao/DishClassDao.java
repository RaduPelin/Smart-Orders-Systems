/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import db.DishClass;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Radu
 */
public class DishClassDao{
    private final Connection con;
    
    public DishClassDao(Connection con) {
       this.con = con;
    }
    
    //Metoda ce va fi folosita ulterior in aplicatie pentru a selecta toate clasele de produse din meniul restaurantului corespunzator interogarii.
    public List<DishClass> getDishClasses(String username) {
        List<DishClass> dishClasses = new ArrayList<>();
        String sql = "SELECT * FROM dishclass DC INNER JOIN Restaurant R ON DC.RestaurantID = R.RestaurantID WHERE R.Username = ?";
        
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1,username);
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                DishClass dishClass = new DishClass();
                
                dishClass.setClassID(rs.getInt("DishClassID"));
                dishClass.setRestaurantID(rs.getInt("RestaurantID"));
                dishClass.setName(rs.getString("DishClassName"));
                
                dishClasses.add(dishClass);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return dishClasses;
    }
            
    //Metoda ce va fi folosita ulterior in aplicatie ce adauga o clasa de produse in meniul restaurantului corespunzator interogarii.
    public void addDishClass(String className, int restaurantID) {
       String sql = "INSERT INTO dishclass VALUES (NULL, ?, ?)";
       
        try (PreparedStatement stmt = con.prepareStatement(sql)) {    
            stmt.setInt(1, restaurantID);
            stmt.setString(2, className);
           
            stmt.executeUpdate();
           
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    //Metoda ce va fi folosita ulterior in aplicatie pentru a sterge o anumita clase de produse, impreuna cu toate produsele clasei aferente(impune constrangerea), din meniul restaurantului corespunzator interogarii. 
    public void removeDishClass(int classID) {
        String sql = "DELETE FROM dishclass WHERE ClassID = ?";
        
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1,classID);
            
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    } 
    
    //Metoda ce va fi folosita pentru a face modificari asupra numelui clasei de produse din restaurantul corespunzator interogarii.
    public void updateDishClass(String className, int restaurantID) {
        String sql = "UPDATE dishclass DC SET DC.Name = ? Where DC.RestaurantID = ?";
        
        try (PreparedStatement stmt = con.prepareStatement(sql)) {     
            stmt.setString(1, className);
            stmt.setInt(2, restaurantID);
           
            stmt.executeUpdate();
           
        } catch (SQLException e) {
            System.out.println(e.getMessage());
       }
    }
}
