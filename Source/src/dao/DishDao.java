/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import db.Dish;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Radu
 */
public class DishDao{
    
    private final Connection con;
    
    public DishDao(Connection con) {
       this.con = con;
    }
    
    //Metoda ce va fi folosita ulterior in aplicatie pentru a lua toate produsele din clasa de produse a meniului restaurantului corespunzator interogarii.
    public Map<String, Integer> getDishList(String username) {
        Map<String, Integer> dishList = new IdentityHashMap<>();
        String sql = "SELECT D.DishName, D.DishID FROM dish D INNER JOIN dishClass DC ON  D.DishClassID = DC.DishClassID INNER JOIN restaurant R ON DC.RestaurantID = R.RestaurantID WHERE R.Username = ?";
        
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, username);
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                dishList.put(rs.getString("DishName"), rs.getInt("DishID"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return dishList;
    }
    
    //Metoda ce va fi folosita ulterior in aplicatie pentru a adauga un singur produs in clasa de produse din meniul restaurantului corespunzator interogarii.
    public void addDish(Dish dish, int classID) {
        String sql = "INSERT INTO dish VALUES (NULL, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = con.prepareStatement(sql)) {    
            stmt.setInt(1, classID);
            stmt.setString(2, dish.getName());
            stmt.setDouble(3, dish.getQuantity());
            stmt.setDouble(4, dish.getPreparationTime());
            stmt.setDouble(5, dish.getPrice());
            stmt.setBoolean(6, dish.getState());
            
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    //Metoda ce va fi folosita ulterior in aplicatie pentru a sterge un singur produs din meniul restaurantului corespunzator interogarii.
    public void removeDish(int dishID) {
        String sql = "DELETE FROM dish WHERE DishID = ?";
        
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, dishID);
            
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    //Metoda ce va fi folosita ulterior in aplicatie pentru a modifica un anumit produs dintr-o clasa a meniului restaurantului corespunzator interogarii.
    public void updateDish(Dish dish, String dishName, int classID) {
        String sql = "UPDATE dish D SET D.DishName = ?, D.Quantity = ?, D.PreparationTime = ?, D.Price = ?, D.State = ? Where D.DishName = ? AND D.DishClassID = ?";
        
        try (PreparedStatement stmt = con.prepareStatement(sql)) {     
           stmt.setString(1, dish.getName());
           stmt.setDouble(2, dish.getQuantity());
           stmt.setDouble(3, dish.getPreparationTime());
           stmt.setDouble(4, dish.getPrice());
           stmt.setBoolean(5, dish.getState());
           stmt.setString(6, dishName);
           stmt.setInt(7, classID);
           
           stmt.executeUpdate();
           
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
