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
import java.sql.Time;
import java.util.Calendar;
import java.util.IdentityHashMap;
import java.util.Map;

/**
 *
 * @author Radu
 */
public class DishOrderDao {
    
    private final Connection con;
    
    public DishOrderDao(Connection con) {
        this.con = con;
    }
    
    //Folosim aceasta metoda pentru a lua toate produsele din comanda, precum si numarul acestora.
    public Map<Dish, Integer> getDishListOnOrder(Time processingTime, String username) {
        Map<Dish, Integer> dishList = new IdentityHashMap<>();
        String sql = "SELECT D.DishName, D.PreparationTime, DIO.Number FROM dish D INNER JOIN dishorder DIO ON D.DishID = DIO.DishID " +
                    "INNER JOIN rorder O ON DIO.OrderID = O.OrderID INNER JOIN rtable T ON O.TableID = T.TableID " +
                    "INNER JOIN area A ON T.AreaID = A.AreaID INNER JOIN restaurant R ON A.RestaurantID = R.RestaurantID " +
                    "WHERE O.ProcessingTime = ? AND O.OrderDate = ? AND R.Username = ? ORDER BY D.DishName";
        
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setTime(1, processingTime);
            stmt.setDate(2, new java.sql.Date(Calendar.getInstance().getTime().getTime()));
            stmt.setString(3, username);
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Dish dish = new Dish();
                
                dish.setName(rs.getString("DishName"));
                dish.setPreparationTime(rs.getDouble("PreparationTime"));
                Integer number = rs.getInt("Number");
                dishList.put(dish, number);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return dishList;
    }
    
    //Folosim aceasta metoda pentru a adauga produsele la comanda plasata, rand pe rand.
    public void addDishOnOrder(int orderID, int dishID, int number) {
        String sql = "INSERT INTO dishorder Values (?, ?, ?)";
        
        try (PreparedStatement stmt = con.prepareStatement(sql)) {    
            stmt.setInt(1, orderID);
            stmt.setInt(2, dishID);
            stmt.setInt(3, number);
            
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    //Folosim aceasta metoda pentru a putea sterge un produs gresit din comanda corespunzător interogarii.
    public void removeDishOnOrder(int orderID) {
        String sql = "DELETE FROM dishorder WHERE OrderID = ?";
        
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, orderID);
            
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    //Folosim aceasta metoda pentru a modifica numarul de produse sau tipul de produs pentru o anumita comanda in parte.
    public void updateDishOrder(int value, int orderID, int dishID, Time processingTime, int restaurantID, int index) {
        String sql = null;
        
        if(index == 0) {
            sql = "UPDATE dishorder DIO INNER JOIN rorder O ON Dio.OrderID = O.OrderID INNER JOIN rtable T " +
                "ON O.TableID = T.TableID INNER JOIN area A ON T.AreaID = A.AreaID INNER JOIN restaurant R " +
                "ON A.RestaurantID = R.RestaurantID SET DIO.DishID = ? WHERE DIO.OrderID = ? AND DIO.DishID = ? AND O.ProcessingTime = ? AND O.OrderDate = ? AND R.RestaurantID = ?"; 
        } else {
            if (index == 1) {
            sql = "UPDATE dishorder DIO INNER JOIN rorder O ON Dio.OrderID = O.OrderID INNER JOIN rtable T " +
                "ON O.TableID = T.TableID INNER JOIN area A ON T.AreaID = A.AreaID INNER JOIN restaurant R " +
                "ON A.RestaurantID = R.RestaurantID SET DIO.Number = ? WHERE DIO.OrderID = ? AND DIO.DishID = ? AND O.ProcessingTime = ? AND O.OrderDate = ? AND R.RestaurantID = ?"; 
            }    
        }
        
        try (PreparedStatement stmt = con.prepareStatement(sql)) {     
            stmt.setInt(1, value);
            stmt.setDouble(2, orderID);
            stmt.setDouble(3, dishID);
            stmt.setTime(4, processingTime);
            stmt.setDate(5, new java.sql.Date(Calendar.getInstance().getTime().getTime()));
            stmt.setDouble(6, restaurantID);
           
            stmt.executeUpdate();
           
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    //Folosim aceasta metoda pentru a selecta totalul incasarilor in ziua curenta.
    public double getTodayTotal(String username) {
        double total = 0;
        String sql = "SELECT SUM(result.TotalPrice) AS Total FROM (SELECT DIO.Number*D.Price AS TotalPrice " +
                    "FROM dish D INNER JOIN dishorder DIO ON D.DishID = Dio.DishID INNER JOIN " + 
                    "rorder O ON DIO.OrderID = O.OrderID INNER JOIN rtable T ON O.TableID = T.TableID " +
                    "INNER JOIN area A ON T.AreaID = A.AreaID INNER JOIN restaurant R ON A.RestaurantID " +
                    "= R.RestaurantID WHERE O.OrderDate = ? AND O.PreparingTime IS NOT NULL AND R.Username = ?) result";
        
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setDate(1,new java.sql.Date(Calendar.getInstance().getTime().getTime()));
            stmt.setString(2, username);

            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                total = rs.getDouble("Total");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return total;
    }
    
    //Folosim aceasta metoda pentru a selecta totalul de la fiecare masa in parte in ziua curenta, la o anumita comanda.
    public double getTableTotal(Time processingTime, String username) {
        double total = 0;
        String sql = "SELECT SUM(result.TotalPrice) AS Total FROM (SELECT DIO.Number*D.Price AS TotalPrice FROM " +
                    "dish D INNER JOIN dishorder DIO ON D.DishID = Dio.DishID INNER JOIN rorder O ON " +
                    "DIO.OrderID = O.OrderID INNER JOIN rtable T ON O.TableID = T.TableID INNER JOIN area " +
                    "A ON T.AreaID = A.AreaID INNER JOIN restaurant R ON A.RestaurantID = R.RestaurantID " +
                    "WHERE O.OrderDate = ? AND O.PreparingTime IS NOT NULL AND R.Username = ? AND O.ProcessingTime = ?) result";
        
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setDate(1,new java.sql.Date(Calendar.getInstance().getTime().getTime()));
            stmt.setString(2, username);
            stmt.setTime(3, processingTime);
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                total = rs.getDouble("Total");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return total;
    } 
}
