/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import db.Resource;
import db.Restaurant;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import service.MainServiceMySQL;

/**
 *
 * @author Radu
 */
public class RestaurantDao{
    private final Connection con;
    private final List<Resource> resources = MainServiceMySQL.getInstance().getResources();
    
    public RestaurantDao(Connection con) {
        this.con = con;
    }
    
    //Metoda folosita pentru selectarea tuturor restaurantelor inscrise in aplicatie.
    public List<Restaurant> getRestaurants() {
        List<Restaurant> restaurants = new ArrayList<>();
        String sql = "SELECT * FROM restaurant";
        
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Restaurant restaurant = new Restaurant();
                
                restaurant.setRestaurantID(rs.getInt("RestaurantID"));
                restaurant.setName(rs.getString("RestaurantName"));
                restaurant.setCountry(rs.getString("RCountry"));
                restaurant.setRegion(rs.getString("RRegion"));
                restaurant.setCity(rs.getString("RCity"));
                restaurant.setAdress(rs.getString("RAdress"));
                restaurant.setPhoneNumber(rs.getString("RPhoneNumber"));
                restaurant.setUsername(rs.getString("Username"));
                restaurant.setUPass(rs.getString("UPassword"));  
                restaurant.setAPass(rs.getString("APassword"));
                restaurant.setLogo(rs.getBytes("Logo"));
                
                restaurants.add(restaurant);
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
            
        return restaurants;
    }
    
    //Metoda folosita pentru adaugarea unui nou restaurant in aplicatie.
    public void addRestaurant(Restaurant restaurant) {
       String sql = "INSERT INTO restaurant Values(NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
       try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, restaurant.getName());
            stmt.setString(2, restaurant.getCountry());
            stmt.setString(3, restaurant.getRegion());
            stmt.setString(4, restaurant.getCity());
            stmt.setString(5, restaurant.getAdress());
            stmt.setString(6, restaurant.getPhoneNumber());
            stmt.setString(7, restaurant.getUsername());
            stmt.setString(8, restaurant.getUPass());
            stmt.setString(9, restaurant.getAPass());
            
            InputStream is;
            if (restaurant.getLogo() != null) {
                is = new ByteArrayInputStream(restaurant.getLogo());
                stmt.setBlob(10, is);
            } else {
                is = new ByteArrayInputStream(resources.get(136).getResource());
                stmt.setBlob(10, is);
            }

            stmt.executeUpdate();
            
        } catch (SQLException e) {
           System.out.println(e.getMessage());
        } 
    }
}
    

