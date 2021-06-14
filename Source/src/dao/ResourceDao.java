/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import db.Resource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 *
 * @author Radu
 */
public class ResourceDao {
    
    private final Connection con;
    
    public ResourceDao(Connection con) {
        this.con = con;
    }
    
    //Metoda folosita pentru a selecta toate resursele folosite in aplicatie.
    public List<Resource> getResources() {
        List<Resource> resources = new ArrayList<>();
        String sql = "SELECT * FROM resources";
        
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Resource resource = new Resource();
                
                resource.setResourceID(rs.getInt("ResourceID"));
                resource.setName(rs.getString("ResourceName"));
                resource.setResource(rs.getBytes("Resource"));
               
                resources.add(resource);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
            
        return resources;
    }
}
    
