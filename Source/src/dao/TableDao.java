/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import db.Table;
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
public class TableDao {
    
    private final Connection con;
    
    public TableDao(Connection con) {
        this.con = con;
    }
    
    //Metoda folosita pentru a selecta mesele dintr-o anumita zona a restaurantului corespunzator interogarii.
    public List<Table> getTables(String areaName, String username) {
        List<Table> tables = new ArrayList<>();
        String sql = "SELECT * FROM rtable T INNER JOIN area A ON T.AreaID = A.AreaID INNER JOIN restaurant R ON A.RestaurantID = R.RestaurantID WHERE A.AreaName = ? AND R.Username = ? ORDER BY T.TableID";
        
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, areaName);
            stmt.setString(2, username);
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Table table = new Table();
                
                table.setTableID(rs.getInt("TableID"));
                table.setAreaID(rs.getInt("AreaID"));
                table.setName(rs.getString("TableName"));
                table.setType(rs.getInt("Type"));
                table.setState(rs.getBoolean("State"));
                table.setNumber(rs.getInt("Number"));
                
                tables.add(table);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
            
        return tables;
    }
    
     //Metoda folosita pentru a selecta mesele restaurantului corespunzator interogarii.
    public List<Table> getTables(String username) {
        List<Table> tables = new ArrayList<>();
        String sql = "SELECT T.TableID, T.TableName FROM rtable T INNER JOIN area A ON T.AreaID = A.AreaID INNER JOIN restaurant R ON A.RestaurantID = R.RestaurantID WHERE R.Username = ? Order By T.TableID";
        
        try (PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, username);
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Table table = new Table();
                
                table.setTableID(rs.getInt("TableID"));
                table.setName(rs.getString("TableName"));
                
                tables.add(table);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
            
        return tables;
    }
    
    //Metoda de modificare a starii si numarului de oameni de la masa.
    public void updateTable(int number, boolean state, int tableID) {
        String sql = "UPDATE rtable T  SET T.State = ?, T.Number = ? Where T.TableID = ?";
        
        try (PreparedStatement stmt = con.prepareStatement(sql)) {     
           stmt.setBoolean(1, state);
           stmt.setInt(2, number);
           stmt.setInt(3, tableID);
           
           stmt.executeUpdate();
           
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    
    //Metoda folosita pentru a adaugarea meselor in functie de tip si numar.
    public void addTables(String areaName, int areaID, int number, int type) {

        for (int i = 0; i < number; i++) {
           String sql = "INSERT INTO rtable VALUES (NULL, ?, ?, ?, ?, ?)";
           try (PreparedStatement stmt = con.prepareStatement(sql)) {    
                stmt.setInt(1, areaID);
                
                if (type == 1) {
                   stmt.setString(2, areaName + ": Small " + (i+1)); 
                } else {
                    if (type == 2) {
                        stmt.setString(2, areaName + ": Big " + (i+1)); 
                    } else {
                        if (type == 3) {
                            stmt.setString(2, areaName + ": Large " + (i+1)); 
                        } else {
                            if (type == 4) {
                                stmt.setString(2, areaName + ": Extra Large " + (i+1)); 
                            }
                        }
                    }
                }
                stmt.setInt(3, type );
                stmt.setBoolean(4, false);
                stmt.setInt(5, 0);
                
                stmt.executeUpdate();
                
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    //Metoda folosita pentru a selecta numarul de mese corespunzator fiecarui tip, din fiecare zona a restaurantului corespunzator interogarii.
    public int getTablesNumber(String areaName, String username, int type) {
        int total = 0;
        String sql = "SELECT COUNT(*) AS Total FROM rtable T INNER JOIN area A " +
                    "ON T.AreaID = A.AreaID INNER JOIN restaurant R ON A.RestaurantID = R.RestaurantID " +
                    "WHERE A.AreaName = ? AND T.Type = ? AND R.Username = ?";
        
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1,areaName);
            stmt.setInt(2,type);
            stmt.setString(3,username);
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                total = rs.getInt("Total");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
            
        return total;
    }
    
    //Metoda folosita pentru a selecta numarul total de mese din restaurantul corespunzator interogarii.
    public int getTotal(String username){
        int total = 0;
        String sql = "SELECT SUM(result.Number) AS Total FROM(SELECT COUNT(T.TableID) " + 
                    "AS NUMBER FROM rtable T INNER JOIN area A ON T.AreaID = A.AreaID " +
                    "INNER JOIN restaurant R ON A.RestaurantID = R.RestaurantID WHERE R.Username = ?) result";
        
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1,username);
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                total = rs.getInt("Total");
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
            
        return total;
    }
}
