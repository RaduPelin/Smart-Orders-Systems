/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.AreaDao;
import dao.DishClassDao;
import dao.DishDao;
import dao.DishOrderDao;
import dao.EmployeeDao;
import dao.OrderDao;
import dao.ResourceDao;
import dao.RestaurantDao;
import dao.TableDao;
import db.Area;
import db.Dish;
import db.DishClass;
import db.Employee;
import db.Order;
import db.Resource;
import db.Restaurant;
import db.Table;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Radu
 */
//Implementam Serviciul de comunicare cu baza de date.
//Ne conectam la baza de date business a host-ului local după pornirea modulelor Apashe si MySQL din XAMPP
public class MainServiceMySQL {
    
     private Connection con;
    
    private MainServiceMySQL() {
        try {
            String url ="jdbc:mysql://localhost/business";
            String user = "root";
            String pass = "";
            
            con = DriverManager.getConnection(url, user, pass);
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    private static final class SingleTonHolder {
        private static final MainServiceMySQL SINGLETON = new MainServiceMySQL();
    }

    public static MainServiceMySQL getInstance() {
        return SingleTonHolder.SINGLETON;
    }
    
    public List<Area> getAreas(String username) {
        AreaDao areaDao = new AreaDao(con);
        return areaDao.getAreas(username);
    }
    
    public void addArea(Area area, int restaurantID, int areaID) {
        AreaDao areaDao = new AreaDao(con);
        areaDao.addArea(area, restaurantID, areaID);
    }
    
    public void removeArea(int areaID) {
        AreaDao areaDao = new AreaDao(con);
        areaDao.removeArea(areaID);
    }
    
    public List<DishClass> getDishClasses(String username) {
        DishClassDao dishClassDao = new DishClassDao(con);
        return dishClassDao.getDishClasses(username);
    }
    
    public void addDishClass(String className, int restaurantID) {
        DishClassDao dishClassDao = new DishClassDao(con);
        dishClassDao.addDishClass(className, restaurantID);
    }
    
    public void removeDishClass(int classID) {
        DishClassDao dishClassDao = new DishClassDao(con);
        dishClassDao.removeDishClass(classID);
    }
    
    public Map<String, Integer> getDishList(String username) { 
        DishDao dishDao = new DishDao(con);
        return dishDao.getDishList(username);
    }
    
    public void addDish(Dish dish, int classID) {
        DishDao dishDao = new DishDao(con);
        dishDao.addDish(dish, classID);
    }
    
    public void removeDish(int dishID) {
        DishDao dishDao = new DishDao(con);
        dishDao.removeDish(dishID);
    }
    
    public Map<Dish, Integer> getDishListOnOrder(Time processingTime, String username) {
        DishOrderDao dishOrderDao = new DishOrderDao(con);
        return dishOrderDao.getDishListOnOrder(processingTime, username);
    }
    
    public void addDishOnOrder(int orderID, int dishID, int number) {
        DishOrderDao dishOrderDao = new DishOrderDao(con);
        dishOrderDao.addDishOnOrder(orderID, dishID, number);
    }
    
    public void removeDishOnOrder(int orderID) {
        DishOrderDao dishOrderDao = new DishOrderDao(con);
        dishOrderDao.removeDishOnOrder(orderID);
    }
    
    public void updateDishOrder(int value, int orderID, int dishID, Time processingTime, int restaurantID, int index) {
        DishOrderDao dishOrderDao = new DishOrderDao(con);
        dishOrderDao.updateDishOrder(value, orderID, dishID, processingTime, restaurantID, index);
    }
    
    public double getTableTotal(Time processingTime, String username) {
        DishOrderDao dishOrderDao = new DishOrderDao(con);
        return dishOrderDao.getTableTotal(processingTime, username);
    }
    
    public double getTodayTotal(String username) { 
        DishOrderDao dishOrderDao = new DishOrderDao(con);
        return dishOrderDao.getTodayTotal(username);
    }
    
    public List<Employee> getEmployees(String username){
        EmployeeDao employeeDao = new EmployeeDao(con);
        return employeeDao.getEmployees(username);
    }
    
    public List<Employee> getEmployees(String areaName, String username){
        EmployeeDao employeeDao = new EmployeeDao(con);
        return employeeDao.getEmployees(areaName, username);
    }
    
    public void addEmployee(Employee employee, int restaurantID){
        EmployeeDao employeeDao = new EmployeeDao(con);
        employeeDao.addEmployee(employee, restaurantID);
    }
    
    public void removeEmployee(String PIN){
        EmployeeDao employeeDao = new EmployeeDao(con);
        employeeDao.removeEmployee(PIN);
    }
    
    public void updateEmployee(Employee employee, String PIN, int restaurantID){
         EmployeeDao employeeDao = new EmployeeDao(con);
         employeeDao.updateEmployee(employee, PIN, restaurantID);
    }
    
    public List<Order> getOrders(String username, boolean done) {
        OrderDao orderDao = new OrderDao(con);
        return orderDao.getOrders(username, done);
    }
    
    public void addOrder(Time processingTime, int tableID) {
        OrderDao orderDao = new OrderDao(con);
        orderDao.addOrder(processingTime, tableID);
    }
    
    public void removeOrder(int orderID) {
        OrderDao orderDao = new OrderDao(con);
        orderDao.removeOrder(orderID);
    }
    
    public void updateOrder(Time preparingTime, int orderID) {
        OrderDao orderDao = new OrderDao(con);
        orderDao.updateOrder(preparingTime, orderID);
    }
   
    public List<Integer> getHappyAreas(String username) {
        OrderDao orderDao = new OrderDao(con);
        return orderDao.getHappyAreas(username);
    }
    
    public List<Resource> getResources() {
        ResourceDao resourceDao = new ResourceDao(con);
        return resourceDao.getResources();
    }
    
    public List<Restaurant> getRestaurants() {
        RestaurantDao restaurantDao = new RestaurantDao(con);
        return restaurantDao.getRestaurants();
    }
    
    public void addRestaurant(Restaurant restaurant) {
         RestaurantDao restaurantDao = new RestaurantDao(con);
         restaurantDao.addRestaurant(restaurant);
    }
    
    public List<Table> getTables(String username) {
        TableDao tableDao = new TableDao(con);
        return tableDao.getTables(username);
    }
    
    public List<Table> getTables(String areaName ,String username) {
         TableDao tableDao = new TableDao(con);
         return tableDao.getTables(areaName, username);
    }
    
    public void addTables(String areaName, int areaID, int number, int type) {
        TableDao tableDao = new TableDao(con);
        tableDao.addTables(areaName, areaID, number, type);
    }
    
    public void updateTable(int number, boolean state, int tableID) {
        TableDao tableDao = new TableDao(con);
        tableDao.updateTable(number, state, tableID);
    }
    
    public int getTablesNumber(String areaName, String username, int type) {
        TableDao tableDao = new TableDao(con);
        return tableDao.getTablesNumber(areaName, username, type);
    }
    
    public int getTotal(String username) {
        TableDao tableDao = new TableDao(con);
        return tableDao.getTotal(username);
    }

}
 