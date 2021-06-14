/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import db.Employee;
import db.Resource;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import service.MainServiceMySQL;

/**
 *
 * @author Radu
 */
public class EmployeeDao {
    private final Connection con;
    private final List<Resource> resources = MainServiceMySQL.getInstance().getResources();
    
    public EmployeeDao(Connection con) {
       this.con = con;
    }
    
    //Folosim aceasta metoda pentru a selecta angajatii din restaurantul corespunzator interogarii.
    public List<Employee> getEmployees(String username) {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM employee E INNER JOIN area A ON E.AreaID = A.AreaID INNER JOIN restaurant R ON A.RestaurantID = R.RestaurantID WHERE R.Username = ? GROUP BY E.EmployeeName";
        
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, username);
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Employee employee = new Employee();
                
                employee.setEmployeeID(rs.getInt("EmployeeID"));
                employee.setAreaID(rs.getInt("AreaID"));
                employee.setName(rs.getString("EmployeeName"));
                employee.setCountry(rs.getString("ECountry"));
                employee.setRegion(rs.getString("ERegion"));
                employee.setCity(rs.getString("ECity"));
                employee.setAdress(rs.getString("EAdress"));
                employee.setPhoneNumber(rs.getString("EPhoneNumber"));
                employee.setBirthDate((java.util.Date) rs.getDate("BirthDate"));
                employee.setSex(rs.getInt("Sex"));
                employee.setPIN(rs.getString("PIN"));
                employee.setFunction(rs.getString("Function"));
                employee.setAtWork(rs.getBoolean("AtWork"));
                employee.setSalary(rs.getDouble("Salary"));
                employee.setPhoto(rs.getBytes("Photo"));
                
                employees.add(employee);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
            
        return employees;
    }
    
    //Folosim aceasta metoda pentru a selecta angajatii din zona ce aparține restaurantului corespunzator interogarii.
    public List<Employee> getEmployees(String areaName, String username) {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM employee E INNER JOIN area A ON E.AreaID = A.AreaID INNER JOIN restaurant R ON A.RestaurantID = R.RestaurantID WHERE R.Username = ? AND A.AreaName = ?";
        
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, areaName);
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Employee employee = new Employee();
                
                employee.setEmployeeID(rs.getInt("EmployeeID"));
                employee.setAreaID(rs.getInt("AreaID"));
                employee.setName(rs.getString("EmployeeName"));
                employee.setCountry(rs.getString("ECountry"));
                employee.setRegion(rs.getString("ERegion"));
                employee.setCity(rs.getString("ECity"));
                employee.setAdress(rs.getString("EAdress"));
                employee.setPhoneNumber(rs.getString("EPhoneNumber"));
                employee.setBirthDate((java.util.Date) rs.getDate("BirthDate"));
                employee.setSex(rs.getInt("Sex"));
                employee.setPIN(rs.getString("PIN"));
                employee.setFunction(rs.getString("Function"));
                employee.setAtWork(rs.getBoolean("AtWork"));
                employee.setSalary(rs.getDouble("Salary"));
                employee.setPhoto(rs.getBytes("Photo"));
                
                employees.add(employee);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
            
        return employees;
    }
    
    //Folosim aceasta metoda pentru a adauga un angajat in restaurantul corespunator interogarii.
    public void addEmployee(Employee employee, int restaurantID) {
       String sql = "INSERT INTO employee VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
       
        try (PreparedStatement stmt = con.prepareStatement(sql)) {    

            stmt.setInt(1, employee.getAreaID());
            stmt.setString(2, employee.getName());
            stmt.setString(3, employee.getCountry());
            stmt.setString(4, employee.getRegion());
            stmt.setString(5, employee.getCity());
            stmt.setString(6, employee.getAdress());
            stmt.setString(7, employee.getPhoneNumber());
            
            Date birthDate = new Date(employee.getBirthDate().getTime());
            
            stmt.setDate(8, birthDate);
            stmt.setInt(9, employee.getSex());
            stmt.setString(10, employee.getPIN());
            stmt.setString(11, employee.getFunction());
            stmt.setBoolean(12, employee.isAtWork());
            stmt.setDouble(13, employee.getSalary());
            stmt.setDate(14,new java.sql.Date(Calendar.getInstance().getTime().getTime()));
            
            InputStream is;
            
            if (employee.getPhoto() != null) {
                is = new ByteArrayInputStream(employee.getPhoto());
                stmt.setBlob(15, is);
            } else {
                if (employee.getSex() == 0) {
                    is = new ByteArrayInputStream(resources.get(126).getResource());
                } else {
                    is = new ByteArrayInputStream(resources.get(127).getResource());
                }
                stmt.setBlob(15, is);
            }
    
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    //Folosim aceasta metoda pentru a sterge un angajat ce are un anumit cod de identificare.
    public void removeEmployee(String PIN) {
        String sql = "DELETE FROM employee WHERE PIN = ?";
        
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1,PIN);
            
            stmt.executeUpdate();
            
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    //Folosim aceasta metoda pentru a modifica un angajat din restaurantul corespunzator interogarii.
    public void updateEmployee(Employee employee, String PIN, int restaurantID) {
        String sql;
        if(employee.getPhoto() != null) {
            sql = "UPDATE employee E INNER JOIN area A ON E.AreaID = A.AreaID INNER JOIN restaurant R ON A.RestaurantID = R.RestaurantID SET E.AreaID = ?, EmployeeName = ?, ECountry = ?, ERegion = ?, ECity = ?, EAdress = ?, EPhoneNumber = ?,  BirthDate = ?, Sex = ?, Pin = ?, Function = ?, Salary = ?, Photo = ? Where A.RestaurantID = ? AND E.PIN = ?";
        } else {
            sql = "UPDATE employee E INNER JOIN area A ON E.AreaID = A.AreaID INNER JOIN restaurant R ON A.RestaurantID = R.RestaurantID SET E.AreaID = ?, EmployeeName = ?, ECountry = ?, ERegion = ?, ECity = ?, EAdress = ?, EPhoneNumber = ?,  BirthDate = ?, Sex = ?, Pin = ?, Function = ?, Salary = ? Where A.RestaurantID = ? AND E.PIN = ?";
        }

        try (PreparedStatement stmt = con.prepareStatement(sql)) {     
            stmt.setInt(1, employee.getAreaID());
            stmt.setString(2, employee.getName());
            stmt.setString(3, employee.getCountry());
            stmt.setString(4, employee.getRegion());
            stmt.setString(5, employee.getCity());
            stmt.setString(6, employee.getAdress());
            stmt.setString(7, employee.getPhoneNumber());
            
            Date birthDate = new Date(employee.getBirthDate().getTime());
            
            stmt.setDate(8, birthDate);
            stmt.setInt(9, employee.getSex());
            stmt.setString(10, employee.getPIN());
            stmt.setString(11, employee.getFunction());
            stmt.setDouble(12, employee.getSalary());
            
            
            InputStream is;
            
            if (employee.getPhoto() != null) {
                is = new ByteArrayInputStream(employee.getPhoto());
                stmt.setBlob(13, is);
                stmt.setInt(14, restaurantID);
                stmt.setString(15, PIN);
            } else {
                stmt.setInt(13, restaurantID);
                stmt.setString(14, PIN);
            }

            stmt.executeUpdate();
           
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }       
}

