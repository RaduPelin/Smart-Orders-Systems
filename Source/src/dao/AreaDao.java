/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import db.Area;
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
public class AreaDao {
    
    private final Connection con;
    
    public AreaDao(Connection con) {
        this.con = con;
    }
    
    //Folosim aceasta metoda pentru a selecta toate zonele existente din restaurantul a carui username corespunde interogarii.
    public List<Area> getAreas(String username) {
        List<Area> areas = new ArrayList<>();
        String sql = "SELECT A.AreaID, A.RestaurantID, A.AreaName, A.AreaOrder FROM Area A INNER JOIN Restaurant R ON A.RestaurantID = R.RestaurantID WHERE R.Username = ? ORDER BY A.AreaOrder";
        
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1,username);
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Area area = new Area();
                
                area.setAreaID(rs.getInt("AreaID"));
                area.setRestaurantID(rs.getInt("RestaurantID"));
                area.setName(rs.getString("AreaName"));
                area.setOrder(rs.getInt("AreaOrder"));
                
                areas.add(area);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return areas;
    }
    
    //Folosim acesta metoda pentru a adauga o noua zona in restaurant, conform interogarii.
    public void addArea(Area area, int restaurantID, int areaID) {
        String sql;
        
        if (areaID == 0) {
            sql = "INSERT INTO area VALUES (NULL, ?, ?, ?)";
        } else {
            sql = "INSERT INTO area VALUES (?, ?, ?, ?)";
        }
        
        try (PreparedStatement stmt = con.prepareStatement(sql)) {    
            
            if (areaID == 0) {
                stmt.setInt(1, restaurantID);
                stmt.setString(2, area.getName());
                stmt.setInt(3, area.getOrder());
            } else {
                stmt.setInt(1, areaID);
                stmt.setInt(2, restaurantID);
                stmt.setString(3, area.getName());
                stmt.setInt(4, area.getOrder());
            }
            
            stmt.executeUpdate();
            
       } catch (SQLException e) {
            System.out.println(e.getMessage());
       }
    }
    
    //Folosim aceasta motoda pentru a sterge o zona adaugata in restaurant conform interogarii.
    public void removeArea(int areaID) {
        String sql = "DELETE FROM area WHERE AreaID = ?";
        
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1,areaID);
            
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    } 
}

