/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import db.Order;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author Radu
 */
public class OrderDao {
    private final Connection con;
    
    public OrderDao(Connection con) {
       this.con = con;
    }
    
    //Folosim aceasta metoda pentru a selecta toate comenzile care sunt gata sau care nu, din restaurantul
    public List<Order> getOrders(String username, boolean done) {
        List<Order> orders = new ArrayList<>();
        String sql;
        
        if (done) {
            sql = "SELECT * FROM rorder O INNER JOIN rtable T ON O.TableID = T.TableID " +
                "INNER JOIN area A ON T.AreaID = A.AreaID INNER JOIN restaurant R " +
                "ON A.RestaurantID = R.RestaurantID WHERE O.PreparingTime IS NOT NULL AND O.OrderDate = ? AND R.Username = ? ORDER BY O.PreparingTime";
        } else {
            sql = "SELECT * FROM rorder O INNER JOIN rtable T ON O.TableID = T.TableID " +
                "INNER JOIN area A ON T.AreaID = A.AreaID INNER JOIN restaurant R " +
                "ON A.RestaurantID = R.RestaurantID WHERE O.PreparingTime IS NULL AND O.OrderDate = ? AND R.Username = ? ORDER BY O.ProcessingTime";
        }
        
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setDate(1,new java.sql.Date(Calendar.getInstance().getTime().getTime()));
            stmt.setString(2, username);
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Order order = new Order();
                
                order.setOrderID(rs.getInt("OrderID"));
                order.setTableID(rs.getInt("TableID"));
                order.setProcessingTime(rs.getTime("ProcessingTime"));
                order.setPreparingTime(rs.getTime("PreparingTime"));
                order.setDate(new java.sql.Date(Calendar.getInstance().getTime().getTime()));
                
                orders.add(order);
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
            
        return orders;
    }
    
    //Folosim aceasta metoda pentru a plasa o comanda la una din mesele restaurantului.
    public void addOrder(Time processingTime, int tableID) {
       String sql = "INSERT INTO rorder VALUES (NULL, ?, ?, NULL, ?)";
       
       try (PreparedStatement stmt = con.prepareStatement(sql)) {    
           stmt.setInt(1, tableID);
           stmt.setTime(2, processingTime);
           stmt.setDate(3,new java.sql.Date(Calendar.getInstance().getTime().getTime()));
           
           stmt.executeUpdate();
           
        } catch(SQLException e) {
           System.out.println(e.getMessage());
        }
    }
    
    //Metoda folosita ulterior in aplicatie pentru a sterge o comanda, apelata la fiecare zi, luna sau an in functie de cum doreste utilizatorul.
    public void removeOrder(int orderID) {
        String sql = "DELETE FROM rorder WHERE OrderID = ?";
        
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1,orderID);
            
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }  
    
    //Folosim aceasta metoda pentru a seta timpul la care a fost servita intreaga comanda.
    public void updateOrder(Time preparingTime, int orderID) {
        String sql = "UPDATE rorder O SET O.PreparingTime = ? Where O.OrderID = ?";
        
        try(PreparedStatement stmt = con.prepareStatement(sql)){     
            stmt.setTime(1, preparingTime);
            stmt.setInt(2, orderID);
           
            stmt.executeUpdate();
            
        } catch(SQLException e) {
            System.out.println(e.getMessage());
       }
    }
     
    //Folosim aceasta metoda pentru a selecta zonele care au avut cele mai multe comenzi in ziua curenta.
    public List<Integer> getHappyAreas(String username) {
        List<Integer> areas = new ArrayList<>();
        String sql = "SELECT A.AreaID AS HappyArea FROM rtable T INNER JOIN rorder O ON T.TableID = O.TableID INNER JOIN "+
                    "area A ON A.AreaID = T.AreaID INNER JOIN restaurant R ON A.RestaurantID = R.RestaurantID WHERE " + 
                    "R.Username = ? AND O.OrderDate = ? AND O.PreparingTime IS NOT NULL GROUP BY A.AreaID HAVING COUNT(O.OrderID) = (SELECT MAX(result.Number) FROM " +
                    "(SELECT COUNT(O.OrderID) AS Number FROM rtable T INNER JOIN rorder O ON T.TableID = O.TableID " +
                    "INNER JOIN area A  ON T.AreaID = A.AreaID INNER JOIN restaurant R ON A.RestaurantID = R.RestaurantID " +
                    "WHERE R.Username = ? AND O.OrderDate = ? AND O.PreparingTime IS NOT NULL GROUP BY A.AreaID) result)";
        
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1,username);
            stmt.setDate(2,new java.sql.Date(Calendar.getInstance().getTime().getTime()));
            stmt.setString(3, username);
            stmt.setDate(4,new java.sql.Date(Calendar.getInstance().getTime().getTime()));
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                areas.add(rs.getInt("HappyArea"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return areas;
    }  
    
}
