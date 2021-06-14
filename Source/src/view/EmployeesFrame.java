/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import db.Area;
import db.Employee;
import db.EmployeePhoto;
import db.Resource;
import db.Restaurant;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import service.MainServiceMySQL;
/**
 *
 * @author Radu
 */
public class EmployeesFrame extends javax.swing.JFrame {
    
    private final List<Resource> resources = MainServiceMySQL.getInstance().getResources();
    private final List<Restaurant> restaurants = MainServiceMySQL.getInstance().getRestaurants();
    private List<EmployeePhoto> employeePhotos;
    private List<Area> areas;
    private List<Employee> employees;
    private JLabel [] employeeInfoLabels;
    private JLabel [] employeePhotoLabels;
    private JLabel [] isAtWorkLabels;
    private JTextField [] employeeNameFields;
    private JTextField [] employeeFunctionFields; 
    private JButton [] editButtons;
    private JButton [] removeButtons;
    private String PINReminder;
    private String phoneReminder;
    private double salary = 800.0;
    private int index;
    private int restaurantID;
    private int sex = 0;
    private int page = 0;
    private int day = 0;
    private int mounth = 1;
    private int year = 0;
    private int area = 0;
    private int numberOfAreas = 0;
    private byte[] photoNow = null;
    private byte[] photoBefore = null;
    private boolean isSet = false;
    private boolean isPick = false;
    private boolean isSex = false;
    private boolean isDate = false;
    private boolean isIcon = false;
    private boolean isAdd = false;
    private boolean isChanged = false;
    private boolean isEmpty = false;
    private boolean isSettable = true;
    private boolean isEditable = false;
    /**
     * Se creaza pagina Employees
     */
    public EmployeesFrame() {
        initComponents();
        
        showDateComponents(false);
        
        employeeViewLabel.setBackground(new Color(0,0,0,100));
        allEmployeesLabel.setBackground(new Color(0,0,0,100));
        nameShadow.setBackground(new Color(0,0,0,100)); 
        countryShadow.setBackground(new Color(0,0,0,100));
        regionShadow.setBackground(new Color(0,0,0,100));
        cityShadow.setBackground(new Color(0,0,0,100));
        adressShadow.setBackground(new Color(0,0,0,100));
        adressPane.setBackground(new Color(0,0,0,0));
        adressPane.getViewport().setOpaque(false);
        adressField.setBackground(new Color(0,0,0,0));
        phoneNumberShadow.setBackground(new Color(0,0,0,100));
        PINShadow.setBackground(new Color(0,0,0,100));
        functionShadow.setBackground(new Color(0,0,0,100));
        functionPane.setBackground(new Color(0,0,0,0));
        functionPane.getViewport().setOpaque(false);
        functionField.setBackground(new Color(0,0,0,0));
        salaryShadow.setBackground(new Color(0,0,0,100));
        mounthShadow.setBackground(new Color(0,0,0,0));
        yearShadow.setBackground(new Color(0,0,0,0));
        dateShadow.setBackground(new Color(0,0,0,100));
        inactiveBackground.setBackground(new Color(0,0,0,150));
        inactiveBackground.setVisible(false);
        allertMessagePane.setBackground(new Color(0,0,0,0));
        allertMessagePane.setVisible(false);
        allertMessagePane.getViewport().setOpaque(false);
        allertMessageLabel.setIcon(getIcon(430, 240, resources.get(14).getResource()));
        allertMessageLabel.setVisible(false);
        allertMessageField.setBackground(new Color(0,0,0,0));
        text1Spot.setVisible(false);
        text2Spot.setVisible(false);
        text3Spot.setVisible(false);
        photoLabel.setVisible(false);
        pickButton.setIcon(getIcon(70,40,resources.get(10).getResource()));
        pickButton.setPressedIcon(getIcon(70,40,resources.get(11).getResource()));
        pickButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                pick();                 
            }
        });
        area1Button.setVisible(false);
        area2Button.setVisible(false);
        area3Button.setVisible(false);
        area4Button.setVisible(false);
        area5Button.setVisible(false);
        area6Button.setVisible(false);
        area7Button.setVisible(false);
        setButton.setIcon(getIcon(70,40,resources.get(10).getResource()));
        setButton.setPressedIcon(getIcon(70,40,resources.get(11).getResource()));
        setButton.addActionListener(e-> {
            activateAreas();
            isSet = true;
        });
        maleSexLabel.setIcon(getIcon(50,50,resources.get(86).getResource()));
        maleSexLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                setSex(maleSexLabel);
            }
        }); 
        femaleSexLabel.setIcon(getIcon(50,50,resources.get(88).getResource()));
        femaleSexLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                setSex(femaleSexLabel);
            }
        }); 
        showEmployees.setIcon(getIcon(250,250,resources.get(128).getResource()));
        showEmployees.setPressedIcon(getIcon(250,250,resources.get(129).getResource()));
        showEmployees.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                setEmployeesFrame();               
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                text3Spot.setVisible(true);
                text3Spot.setText("Show Employees");
            }
            @Override
            public void mouseExited(MouseEvent e) {
                text3Spot.setVisible(false);
                text3Spot.setText("");
            }
        });
        backButton.setIcon(getIcon(120,50,resources.get(4).getResource()));
        backButton.setPressedIcon(getIcon(120,50,resources.get(5).getResource()));
        backButton.addActionListener(e->back());
        addPhotoButton.setIcon(getIcon(150,170,resources.get(82).getResource()));
        addPhotoButton.setPressedIcon(getIcon(150,170,resources.get(83).getResource()));
        addPhotoButton.addActionListener(e->selectPhoto());
        changeButton.setVisible(false);
        changeButton.setIcon(getIcon(120,70,resources.get(10).getResource()));
        changeButton.setPressedIcon(getIcon(120,70,resources.get(11).getResource()));
        changeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                changeButton.setText("");
                changePhoto();
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                changeButton.setText("Change");
            }
        });
        addButton.setIcon(getIcon(80,80,resources.get(84).getResource()));
        addButton.setPressedIcon(getIcon(80,80,resources.get(85).getResource()));
        addButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                  add();
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                text1Spot.setVisible(true);
                text1Spot.setText("Add");
            } 
            @Override    
            public void mouseExited(MouseEvent e) {
                text1Spot.setVisible(false);
                text1Spot.setText("");
            }
        });
        goAddButton.setVisible(false);
        goAddButton.setIcon(getIcon(80,80,resources.get(130).getResource()));
        goAddButton.setPressedIcon(getIcon(80,80,resources.get(131).getResource()));
        goAddButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                resetComponents();               
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                text1Spot.setVisible(true);
                text1Spot.setText("Go Back");
            }
            @Override
            public void mouseExited(MouseEvent e) {
                text1Spot.setVisible(false);
                text1Spot.setText("");
            }
        });
        updateButton.setVisible(false);
        updateButton.setIcon(getIcon(80,80,resources.get(132).getResource()));
        updateButton.setPressedIcon(getIcon(80,80,resources.get(133).getResource()));
        updateButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                update();              
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                text2Spot.setVisible(true);
                text2Spot.setText("Update");
            }
            @Override
            public void mouseExited(MouseEvent e) {
                text2Spot.setVisible(false);
                text2Spot.setText("");
            }
        });
        okButton.setIcon(getIcon(45,45,resources.get(84).getResource()));
        okButton.setPressedIcon(getIcon(45,45,resources.get(85).getResource()));   
        okButton.addActionListener(e-> { 
            if (checkDate()) {
                setDate();
            } else {
                    day1Label.setIcon(getIcon(30,30,resources.get(97).getResource()));
                    day2Label.setIcon(getIcon(30,30,resources.get(97).getResource()));
                    mounth1Label.setIcon(getIcon(30,30,resources.get(86).getResource()));
                    mounth2Label.setIcon(getIcon(30,30,resources.get(86).getResource()));
                    year1Label.setIcon(getIcon(30,30,resources.get(99).getResource()));
                    year2Label.setIcon(getIcon(30,30,resources.get(99).getResource()));
                    year3Label.setIcon(getIcon(30,30,resources.get(99).getResource()));
                    year4Label.setIcon(getIcon(30,30,resources.get(99).getResource()));
                }
                
                resetDays();
            
  
            showDateComponents(false); });
        retryButton.setIcon(getIcon(180, 70, resources.get(4).getResource()));
        retryButton.setPressedIcon(getIcon(180, 70, resources.get(5).getResource()));
        retryButton.setVisible(false);
        retryButton.addActionListener(e->retry()); 
        prevButton.setIcon(getIcon(130, 130, resources.get(93).getResource()));
        prevButton.setPressedIcon(getIcon(130, 130, resources.get(94).getResource()));
        prevButton.setVisible(false);
        prevButton.addActionListener(e->showPreviousPageOfEmployees());
        nextButton.setIcon(getIcon(130, 130, resources.get(95).getResource()));
        nextButton.setPressedIcon(getIcon(130, 130, resources.get(96).getResource()));
        nextButton.setVisible(false);
        nextButton.addActionListener(e->showNextPageOfEmployees());
        prevMounthButton.setIcon(getIcon(40,40,resources.get(93).getResource()));
        prevMounthButton.setPressedIcon(getIcon(40,40,resources.get(94).getResource()));
        prevMounthButton.addActionListener(e->{
            setMounth(prevMounthButton);
            showDateComponents(true);
        }); 
        nextMounthButton.setIcon(getIcon(40,40,resources.get(95).getResource()));
        nextMounthButton.setPressedIcon(getIcon(40,40,resources.get(96).getResource()));
        nextMounthButton.addActionListener(e->{
            setMounth(nextMounthButton);
            showDateComponents(true);
        });
        day1Button.setIcon(getIcon(40,40,resources.get(90).getResource()));
        day2Button.setIcon(getIcon(40,40,resources.get(90).getResource()));
        day3Button.setIcon(getIcon(40,40,resources.get(90).getResource()));
        day4Button.setIcon(getIcon(40,40,resources.get(90).getResource()));
        day5Button.setIcon(getIcon(40,40,resources.get(90).getResource()));
        day6Button.setIcon(getIcon(40,40,resources.get(90).getResource()));
        day7Button.setIcon(getIcon(40,40,resources.get(90).getResource()));
        day8Button.setIcon(getIcon(40,40,resources.get(90).getResource()));
        day9Button.setIcon(getIcon(40,40,resources.get(90).getResource()));
        day10Button.setIcon(getIcon(40,40,resources.get(90).getResource()));
        day11Button.setIcon(getIcon(40,40,resources.get(90).getResource()));
        day12Button.setIcon(getIcon(40,40,resources.get(90).getResource()));
        day13Button.setIcon(getIcon(40,40,resources.get(90).getResource()));
        day14Button.setIcon(getIcon(40,40,resources.get(90).getResource()));
        day15Button.setIcon(getIcon(40,40,resources.get(90).getResource()));
        day16Button.setIcon(getIcon(40,40,resources.get(90).getResource()));
        day17Button.setIcon(getIcon(40,40,resources.get(90).getResource()));
        day18Button.setIcon(getIcon(40,40,resources.get(90).getResource()));
        day19Button.setIcon(getIcon(40,40,resources.get(90).getResource()));
        day20Button.setIcon(getIcon(40,40,resources.get(90).getResource()));
        day21Button.setIcon(getIcon(40,40,resources.get(90).getResource()));
        day29Button.setIcon(getIcon(40,40,resources.get(90).getResource()));
        day23Button.setIcon(getIcon(40,40,resources.get(90).getResource()));
        day24Button.setIcon(getIcon(40,40,resources.get(90).getResource()));
        day25Button.setIcon(getIcon(40,40,resources.get(90).getResource()));
        day26Button.setIcon(getIcon(40,40,resources.get(90).getResource()));
        day27Button.setIcon(getIcon(40,40,resources.get(90).getResource()));
        day28Button.setIcon(getIcon(40,40,resources.get(90).getResource()));
        day22Button.setIcon(getIcon(40,40,resources.get(90).getResource()));
        day30Button.setIcon(getIcon(40,40,resources.get(90).getResource()));
        day31Button.setIcon(getIcon(40,40,resources.get(90).getResource()));
        day1Button.setPressedIcon(getIcon(40,40,resources.get(91).getResource()));
        day2Button.setPressedIcon(getIcon(40,40,resources.get(91).getResource()));
        day3Button.setPressedIcon(getIcon(40,40,resources.get(91).getResource()));
        day4Button.setPressedIcon(getIcon(40,40,resources.get(91).getResource()));
        day5Button.setPressedIcon(getIcon(40,40,resources.get(91).getResource()));
        day6Button.setPressedIcon(getIcon(40,40,resources.get(91).getResource()));
        day7Button.setPressedIcon(getIcon(40,40,resources.get(91).getResource()));
        day8Button.setPressedIcon(getIcon(40,40,resources.get(91).getResource()));
        day9Button.setPressedIcon(getIcon(40,40,resources.get(91).getResource()));
        day10Button.setPressedIcon(getIcon(40,40,resources.get(91).getResource()));
        day11Button.setPressedIcon(getIcon(40,40,resources.get(91).getResource()));
        day12Button.setPressedIcon(getIcon(40,40,resources.get(91).getResource()));
        day13Button.setPressedIcon(getIcon(40,40,resources.get(91).getResource()));
        day14Button.setPressedIcon(getIcon(40,40,resources.get(91).getResource()));
        day15Button.setPressedIcon(getIcon(40,40,resources.get(91).getResource()));
        day16Button.setPressedIcon(getIcon(40,40,resources.get(91).getResource()));
        day17Button.setPressedIcon(getIcon(40,40,resources.get(91).getResource()));
        day18Button.setPressedIcon(getIcon(40,40,resources.get(91).getResource()));
        day19Button.setPressedIcon(getIcon(40,40,resources.get(91).getResource()));
        day20Button.setPressedIcon(getIcon(40,40,resources.get(91).getResource()));
        day21Button.setPressedIcon(getIcon(40,40,resources.get(91).getResource()));
        day29Button.setPressedIcon(getIcon(40,40,resources.get(91).getResource()));
        day23Button.setPressedIcon(getIcon(40,40,resources.get(91).getResource()));
        day24Button.setPressedIcon(getIcon(40,40,resources.get(91).getResource()));
        day25Button.setPressedIcon(getIcon(40,40,resources.get(91).getResource()));
        day26Button.setPressedIcon(getIcon(40,40,resources.get(91).getResource()));
        day27Button.setPressedIcon(getIcon(40,40,resources.get(91).getResource()));
        day28Button.setPressedIcon(getIcon(40,40,resources.get(91).getResource()));
        day22Button.setPressedIcon(getIcon(40,40,resources.get(91).getResource()));
        day30Button.setPressedIcon(getIcon(40,40,resources.get(91).getResource()));
        day31Button.setPressedIcon(getIcon(40,40,resources.get(91).getResource()));
        day1Label.setIcon(getIcon(30,30,resources.get(97).getResource()));
        day2Label.setIcon(getIcon(30,30,resources.get(97).getResource()));
        mounth1Label.setIcon(getIcon(30,30,resources.get(86).getResource()));
        mounth2Label.setIcon(getIcon(30,30,resources.get(86).getResource()));
        year1Label.setIcon(getIcon(30,30,resources.get(99).getResource()));
        year2Label.setIcon(getIcon(30,30,resources.get(99).getResource()));
        year3Label.setIcon(getIcon(30,30,resources.get(99).getResource()));
        year4Label.setIcon(getIcon(30,30,resources.get(99).getResource()));
       
        setSex(maleSexLabel);
        setSex(femaleSexLabel);
        setContent(nameField,"Name");
        setContent(countryField,"Country");
        setContent(regionField,"Region");
        setContent(cityField,"City");
        setContent(phoneNumberField,"Phone Number");
        setContent(PINField, "PIN");
        setContent(salaryField,"Salary");
        setContent(yearField,"Year");
        setDay(day1Button);
        setDay(day2Button);
        setDay(day3Button);
        setDay(day4Button);
        setDay(day5Button);
        setDay(day6Button);
        setDay(day7Button);
        setDay(day8Button);
        setDay(day9Button);
        setDay(day10Button);
        setDay(day11Button);
        setDay(day12Button);
        setDay(day13Button);
        setDay(day14Button);
        setDay(day15Button);
        setDay(day16Button);
        setDay(day17Button);
        setDay(day18Button);
        setDay(day19Button);
        setDay(day20Button);
        setDay(day21Button);
        setDay(day22Button);
        setDay(day23Button);
        setDay(day24Button);
        setDay(day25Button);
        setDay(day26Button);
        setDay(day27Button);
        setDay(day28Button);
        setDay(day29Button);
        setDay(day30Button);
        setDay(day31Button);     
    }
    
    //Setarea si afisarea cadrului pentru angajatii existenti (partea dreapta a ecranului).
    private void setEmployeesFrame(){
        showEmployees.setVisible(false);
        
        String username = restaurants.stream()
                .filter(r -> r.getRestaurantID() == restaurantID)
                .map(r -> r.getUsername())
                .findFirst()
                .orElse(null);
        employees = MainServiceMySQL.getInstance().getEmployees(username);
        employeeInfoLabels  = new JLabel[employees.size()];
        employeePhotoLabels = new JLabel[employees.size()];
        isAtWorkLabels = new JLabel[employees.size()];
        employeeNameFields = new JTextField[employees.size()];
        employeeFunctionFields = new JTextField[employees.size()]; 
        editButtons = new  JButton[employees.size()];
        removeButtons = new JButton[employees.size()];
        page = 1;
        int contor = 0;
        
        for (int i = 0; i<employees.size(); i++) {
            if (i%3 == 0) {
                contor = 0;
            } else contor++;
            
            allEmployeesPanel.setLayout(null); 
            allEmployeesPanel.show(true);
           
            employeeInfoLabels[i] = new JLabel();
            employeeInfoLabels[i].setVisible(false);
            employeeInfoLabels[i].setBounds(20,220*contor + 20,410, 200);
            employeeInfoLabels[i].setIcon(getIcon(410,200,resources.get(8).getResource()));
            isAtWorkLabels[i] = new JLabel();
            isAtWorkLabels[i].setVisible(false);
            isAtWorkLabels[i].setOpaque(false);
            isAtWorkLabels[i].setBounds(170,220*contor+150,50,50);
            
            if (employees.get(i).isAtWork()) {
                isAtWorkLabels[i].setIcon(getIcon(50,50,resources.get(81).getResource()));
            } else {
                isAtWorkLabels[i].setIcon(getIcon(50,50,resources.get(80).getResource()));
            } 
            
            employeeNameFields[i] = new JTextField();
            employeeNameFields[i].setVisible(false);
            employeeNameFields[i].setOpaque(false);
            employeeNameFields[i].setEditable(false);
            employeeNameFields[i].setBorder(null);
            employeeNameFields[i].setBounds(170, 220*contor+50, 230,40);
            employeeNameFields[i].setText(employees.get(i).getName());
            employeeNameFields[i].setForeground(new Color(255,255,255));
            employeeNameFields[i].setFont(new Font("Yu Gothic UI Semibold",Font.PLAIN, 24));
            employeeFunctionFields[i] = new JTextField();
            employeeFunctionFields[i].setVisible(false);
            employeeFunctionFields[i].setOpaque(false);
            employeeFunctionFields[i].setEditable(false);
            employeeFunctionFields[i].setBorder(null);
            employeeFunctionFields[i].setBounds(170, 220*contor+100, 230,40);
            employeeFunctionFields[i].setText(employees.get(i).getFunction());
            employeeFunctionFields[i].setForeground(new Color(255,255,255));
            employeeFunctionFields[i].setFont(new Font("Yu Gothic UI Semibold",Font.PLAIN, 24));
           
            int cursor = i;
            
            editButtons[i] = new JButton();
            editButtons[i].setForeground(new Color(255,255,255));
            editButtons[i].setText("Edit");
            editButtons[i].setFont(new Font("Yu Gothic UI Semibold",Font.PLAIN, 24));
            editButtons[i].setHorizontalAlignment(JButton.CENTER);
            editButtons[i].setHorizontalTextPosition(JButton.CENTER);
            editButtons[i].setContentAreaFilled(false);
            editButtons[i].setOpaque(false);
            editButtons[i].setBorder(null);
            editButtons[i].setBounds(430, 220*contor + 30, 130, 80);
            editButtons[i].setIcon(getIcon(130,80,resources.get(10).getResource()));
            editButtons[i].setPressedIcon(getIcon(130,80,resources.get(11).getResource()));
            editButtons[i].setVisible(false);
            editButtons[i].addActionListener(e->editInfo(cursor));
            removeButtons[i] = new JButton();
            removeButtons[i].setForeground(new Color(255,255,255));
            removeButtons[i].setText("Remove");
            removeButtons[i].setFont(new Font("Yu Gothic UI Semibold",Font.PLAIN, 24));
            removeButtons[i].setHorizontalAlignment(JButton.CENTER);
            removeButtons[i].setHorizontalTextPosition(JButton.CENTER);
            removeButtons[i].setContentAreaFilled(false);
            removeButtons[i].setBorder(null);
            removeButtons[i].setBounds(430, 220*contor+130, 130, 80);
            removeButtons[i].setIcon(getIcon(130,80,resources.get(10).getResource()));
            removeButtons[i].setPressedIcon(getIcon(130,80,resources.get(11).getResource())); 
            removeButtons[i].setVisible(false);
            removeButtons[i].addActionListener(e->removeEmployee(cursor)); 
            employeePhotoLabels[i] = new JLabel(); 
            employeePhotoLabels[i].setVisible(false);
            employeePhotoLabels[i].setBounds(80,220*contor + 50,90, 100);
            boolean isPhoto = false;
            
            if (!Arrays.equals(employees.get(i).getPhoto(), resources.get(126).getResource()) || !Arrays.equals(employees.get(i).getPhoto(), resources.get(127).getResource())) {
                isPhoto = true;
            }
            if (isPhoto) {
                employeePhotoLabels[i].setIcon(getIcon(90, 100, employees.get(i).getPhoto()));
            } else {
                if (employees.get(i).getSex()==0) {
                    employeePhotoLabels[i].setIcon(getIcon(90,100,resources.get(126).getResource())); 
                } else {
                    employeePhotoLabels[i].setIcon(getIcon(90,100,resources.get(127).getResource())); 
                } 
            }
            employeePhotoLabels[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                     showInfo(cursor);           
                }
            });        
            allEmployeesPanel.add(employeeInfoLabels[i]); 
            allEmployeesPanel.add(employeePhotoLabels[i]);
            allEmployeesPanel.add(isAtWorkLabels[i]);
            allEmployeesPanel.add(employeeNameFields[i]);
            allEmployeesPanel.add(employeeFunctionFields[i]);
            allEmployeesPanel.add(editButtons[i]);
            allEmployeesPanel.add(removeButtons[i]);

            if(i < 3){
                employeeInfoLabels[i].setVisible(true);
                employeePhotoLabels[i].setVisible(true);
                isAtWorkLabels[i].setVisible(true);
                employeeNameFields[i].setVisible(true);
                employeeFunctionFields[i].setVisible(true);
                editButtons[i].setVisible(true);
                removeButtons[i].setVisible(true); 
                if(employees.size() > 3){
                    nextButton.setVisible(true);
                }
            }
        }
    }

    //Metoda de modificare a unui anumit angajat.
    private void update(){
        isChanged = true;
        if (checkData()) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date birthDate = null;

            try {
                year = Integer.parseInt(year1Label.getName())*1000 + Integer.parseInt(year2Label.getName())*100 + Integer.parseInt(year3Label.getName())*10 + Integer.parseInt(year4Label.getName());
                mounth = Integer.parseInt(mounth1Label.getName())*10 + Integer.parseInt(mounth2Label.getName());
                day = Integer.parseInt(day1Label.getName())*10 + Integer.parseInt(day2Label.getName());
                
                birthDate = dateFormat.parse(dateToString());
            } catch (ParseException e) {
                System.out.println(e.getMessage());
            }
            String username = restaurants.stream()
            .filter(r -> r.getRestaurantID() == restaurantID)
            .map(r -> r.getUsername())
            .findFirst()
            .orElse(null);
            
            employees = MainServiceMySQL.getInstance().getEmployees(username);
        
            employees.stream()
                    .filter((employee) -> (employee.getPIN()
                    .equals(PINReminder) && photoNow == null))
                    .filter((employee) -> (Arrays.equals(employee.getPhoto(), resources.get(126).getResource()) || Arrays.equals(employee.getPhoto(), resources.get(127).getResource())))
                    .forEachOrdered((_item) -> {
                        if (sex == 1) {
                            photoNow = resources.get(127).getResource();
                        } else {
                            photoNow  = resources.get(126).getResource();
                        }
                    });
            
            int realAreaID = areas.get(area-1).getAreaID();
             
            Employee emp = new Employee(realAreaID, nameField.getText(), countryField.getText(), regionField.getText(), cityField.getText(), adressField.getText(), phoneNumberField.getText(), birthDate, sex, PINField.getText(), functionField.getText(), salary, photoNow);
           
            MainServiceMySQL.getInstance().updateEmployee(emp,PINReminder,restaurantID);
           
            
            allEmployeesPanel.removeAll();
            setEmployeesFrame();
            resetComponents();
            prevButton.setVisible(false);
        }
    }
    
    //Metoda de afisare informatii editabile despre un anumit angajat.
    private void editInfo(int contor){
        showInfo(contor);
        
        isSet = true;
        photoNow = null;
        photoBefore = null;
        isDate = true;
        isSettable = true;
        isEditable = true;
        
        resetDays();
        
        PINReminder = PINField.getText();
        updateButton.setVisible(true);
        pickButton.setVisible(true);
        photoLabel.setVisible(true);
        changeButton.setVisible(true);
        employees.stream().filter((employee) -> (employee.getPIN().equals(PINReminder)))
                    .forEachOrdered((employee) -> { photoLabel.setIcon(getIcon(130, 150, employee.getPhoto()));});
        
        nameField.setEditable(true);
        countryField.setEditable(true);
        regionField.setEditable(true);
        cityField.setEditable(true);
        adressField.setEditable(true);
        phoneNumberField.setEditable(true);
        PINField.setEditable(true);
        functionField.setEditable(true);
        salaryField.setEditable(true);
    }
    
    //Metoda de afisare informatii despre un anumit angajat.
    private void showInfo(int contor){
        resetComponents();
        goAddButton.setVisible(true);
        isSettable = false;
        resetSex();
        pickButton.setVisible(false);
        nameField.setText(employees.get(contor).getName());
        nameField.setEditable(false);
        countryField.setText(employees.get(contor).getCountry());
        countryField.setEditable(false);
        regionField.setText(employees.get(contor).getRegion());
        regionField.setEditable(false);
        cityField.setText(employees.get(contor).getCity());
        cityField.setEditable(false);
        adressField.setText(employees.get(contor).getAdress());
        adressField.setEditable(false);
        phoneNumberField.setText(employees.get(contor).getPhoneNumber());
        phoneReminder = phoneNumberField.getText();
        phoneNumberField.setEditable(false);
        PINField.setText(employees.get(contor).getPIN());
        PINField.setEditable(false);
        functionField.setText(employees.get(contor).getFunction());
        functionField.setEditable(false);
        salaryField.setText(""+employees.get(contor).getSalary());
        salaryField.setEditable(false);
        
        if (setButton.isVisible()) {
            activateAreas();
        } else {
            resetAreas();
        }
        
        if (areas.size()== 1) {
            area1Button.setVisible(true);
        } else {
            if (areas.size()== 2) {
                area1Button.setVisible(true);
                area2Button.setVisible(true);
            } else { 
                if (areas.size()==3) {
                    area1Button.setVisible(true);
                    area2Button.setVisible(true);
                    area3Button.setVisible(true);
                } else {
                    if (areas.size() == 4) {
                        area1Button.setVisible(true);
                        area2Button.setVisible(true);
                        area3Button.setVisible(true);
                        area4Button.setVisible(true);
                    } else {
                        if (areas.size() == 5) {
                            area1Button.setVisible(true);
                            area2Button.setVisible(true);
                            area3Button.setVisible(true);
                            area4Button.setVisible(true);
                            area5Button.setVisible(true);
                        } else {
                            if (areas.size() == 6) {
                                area1Button.setVisible(true);
                                area2Button.setVisible(true);
                                area3Button.setVisible(true);
                                area4Button.setVisible(true);
                                area5Button.setVisible(true);
                                area6Button.setVisible(true);
                            } else {
                                if (areas.size() == 6) {
                                    area1Button.setVisible(true);
                                    area2Button.setVisible(true);
                                    area3Button.setVisible(true);
                                    area4Button.setVisible(true);
                                    area5Button.setVisible(true);
                                    area6Button.setVisible(true);
                                    area7Button.setVisible(true);
                                }
                            }
                        }
                    }
                }
            }
        }

        for (int i = 0; i < areas.size(); i++) {
            if (areas.get(i).getAreaID() == employees.get(contor).getAreaID()) {
                area = areas.get(i).getOrder();
            }
        }
        
        if (area == 1) {
            area1Button.setVisible(true);
            area1Button.setIcon(getIcon(40,40,resources.get(100+2*area).getResource()));
        } else {
            if (area == 2) {
                area2Button.setVisible(true);
                area2Button.setIcon(getIcon(40,40,resources.get(100+2*area).getResource()));
            } else {
                if(area == 3){
                    area3Button.setVisible(true);
                    area3Button.setIcon(getIcon(40,40,resources.get(100+2*area).getResource()));
                } else {
                    if (area == 4) {
                        area4Button.setVisible(true);
                        area4Button.setIcon(getIcon(40,40,resources.get(100+2*area).getResource()));
                    } else {
                        if (area == 5) {
                            area5Button.setVisible(true);
                            area5Button.setIcon(getIcon(40,40,resources.get(100+2*area).getResource()));
                        } else {
                            if (area == 6) {
                                area6Button.setVisible(true);
                                area6Button.setIcon(getIcon(40,40,resources.get(100+2*area).getResource()));
                            } else {
                                if (area == 7) {
                                    area7Button.setVisible(true);
                                    area7Button.setIcon(getIcon(40,40,resources.get(100+2*area).getResource()));
                                }
                            }
                        }
                    }      
                }
            }
        }
        
        Calendar cal = Calendar.getInstance();
        
        cal.setTime(employees.get(contor).getBirthDate());
        
        year = cal.get(Calendar.YEAR);
        mounth = cal.get(Calendar.MONTH)+1;
        day = cal.get(Calendar.DAY_OF_MONTH);
        
        isDate = true;
  
        setVisibleInputDate(day1Label, day/10);
        setVisibleInputDate(day2Label, day%10);
        setVisibleInputDate(mounth1Label, mounth/10);
        setVisibleInputDate(mounth2Label, mounth%10);
        setVisibleInputDate(year1Label, year/1000);
        setVisibleInputDate(year2Label, year/100%10);
        setVisibleInputDate(year3Label, year%100/10);
        setVisibleInputDate(year4Label, year%10);
        sex = employees.get(contor).getSex();
        isSex = true;
        
        if (sex == 0) {
            maleSexLabel.setIcon(getIcon(50,50,resources.get(87).getResource()));         
        } else {
            femaleSexLabel.setIcon(getIcon(50,50,resources.get(89).getResource()));               
        }
        
        if (photoLabel.isVisible()) {
            photoLabel.setVisible(false);
            changeButton.setVisible(false);
        } else {
            addPhotoButton.setVisible(false);
        }
        addButton.setVisible(false);
    }
    
    //Metoda de stergere a unui angajat.
    private void removeEmployee(int cursor){
        MainServiceMySQL.getInstance().removeEmployee(employees.get(cursor).getPIN()); 
        
        String username = restaurants.stream()
            .filter(r -> r.getRestaurantID() == restaurantID)
            .map(r -> r.getUsername())
            .findFirst()
            .orElse(null);
        employees = MainServiceMySQL.getInstance().getEmployees(username);
        
        allEmployeesPanel.show(false);
        allEmployeesPanel.removeAll();
        showEmployees.setVisible(true);
        prevButton.setVisible(false);
        nextButton.setVisible(false);
        resetComponents();
    }
    
    //Metoda de afisare a paginii urmatoare de angajati.
    private void showNextPageOfEmployees(){
        int posibleSize;
        
        for (int i = page*3-3; i < page*3; i++) {
            employeeNameFields[i].setVisible(false);
            employeeFunctionFields[i].setVisible(false);
            employeeInfoLabels[i].setVisible(false);
            employeePhotoLabels[i].setVisible(false);
            isAtWorkLabels[i].setVisible(false);
            editButtons[i].setVisible(false);
            removeButtons[i].setVisible(false);
        }
        
        if (page*3+3 <= employees.size()) {
            posibleSize = page*3+3;
        } else {
            posibleSize = employees.size();
        }
        
        for (int i = page*3; i < posibleSize; i++) {
            employeeNameFields[i].setVisible(true);
            employeeFunctionFields[i].setVisible(true);
            employeeInfoLabels[i].setVisible(true);
            employeePhotoLabels[i].setVisible(true);
            isAtWorkLabels[i].setVisible(true);
            editButtons[i].setVisible(true);
            removeButtons[i].setVisible(true);
        }
        if (!prevButton.isVisible()) {
            prevButton.setVisible(true); 
        }
        
        page++;
        
        if (page == (employees.size()-1)/3+1) {
            nextButton.setVisible(false);
        }
    }
    
    //Metoda de afisare a paginii anterioare de angajati.
    private void showPreviousPageOfEmployees(){
        int posibleSize;
        
        if (page*3 <= employees.size()) {
            posibleSize = page*3;
        } else{
            posibleSize = employees.size();
        }
        
        for (int i = posibleSize-1; i >page*3-4; i--) {
            employeeNameFields[i].setVisible(false);
            employeeFunctionFields[i].setVisible(false);
            employeeInfoLabels[i].setVisible(false);
            employeePhotoLabels[i].setVisible(false);
            isAtWorkLabels[i].setVisible(false);
            editButtons[i].setVisible(false);
            removeButtons[i].setVisible(false);
        }
        
        for (int i = page*3-4; i > page*3-7; i--) {
            employeeNameFields[i].setVisible(true);
            employeeFunctionFields[i].setVisible(true);
            employeeInfoLabels[i].setVisible(true);
            employeePhotoLabels[i].setVisible(true);
            isAtWorkLabels[i].setVisible(true);
            editButtons[i].setVisible(true);
            removeButtons[i].setVisible(true);
        }
        
        page--;
        
        if (page == 1) {
            prevButton.setVisible(false);
        }
        
        nextButton.setVisible(true);
    }
     
    //Metoda de setare a continutului field-urilor.
    private void setContent(JTextField field, String fieldText){
        field.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (field.getText().equals(fieldText)) {
                    field.setText("");
                }   
            }
            @Override
            public void mouseExited(MouseEvent e) {
                if (field.getText().equals("")){
                    field.setText(fieldText);
                }
            }
        }); 
        
        field.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (field.getText().equals("")) {
                    field.setText(fieldText);
                }
            }
            @Override
            public void keyPressed(KeyEvent e) {
                if (field.getText().equals(fieldText)) {
                    field.setText("");
                }
            }
        });
    }
    
    //Metoda de setare a lunii.
    private void setMounth(JButton button) {
        String mounths[] ={"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"}; 
        
        if (mounthSpot.getText().equals(mounths[0])) {
            if (button.equals(nextMounthButton)) {
                mounth = 2;

                mounthSpot.setText("February");
            } else {
                mounth = 12;
                
                mounthSpot.setText("December");
            }
        } else {
            if (mounthSpot.getText().equals(mounths[11])) {
                if(button.equals(nextMounthButton)){
                    mounth = 1;
                                        
                    mounthSpot.setText("January");
                } else {
                    mounth = 11;
                    
                    mounthSpot.setText("November");    
                }
            } else {
                for (int i = 1; i < 11; i++) {
                    if (mounthSpot.getText().equals(mounths[i])) {
                        if (button.equals(nextMounthButton)) {
                            mounth = i+2;
                            
                            mounthSpot.setText(mounths[i+1]);
                                
                        } else {
                            mounth = i;
                                
                            mounthSpot.setText(mounths[i-1]);    
                        }
                            return;
                    } 
                }
            }
        }
    }
    
    //Metoda de setare a zilei.
    private void setDay(JButton button) {
        button.addActionListener(e -> {
            resetDays();
            
            String name = button.getText();
            day = Integer.parseInt(name);
            
            button.setIcon(getIcon(40,40,resources.get(92).getResource()));
        });
    }
    
    //Metoda de setare a datei DD MM YYYY.
    private void setVisibleInputDate(JLabel label, int value) {
        if(value == 0) label.setIcon(getIcon(30,30,resources.get(116).getResource()));
        if(value == 1) label.setIcon(getIcon(30,30,resources.get(117).getResource()));
        if(value == 2) label.setIcon(getIcon(30,30,resources.get(118).getResource()));
        if(value == 3) label.setIcon(getIcon(30,30,resources.get(119).getResource()));
        if(value == 4) label.setIcon(getIcon(30,30,resources.get(120).getResource()));
        if(value == 5) label.setIcon(getIcon(30,30,resources.get(121).getResource()));
        if(value == 6) label.setIcon(getIcon(30,30,resources.get(122).getResource()));
        if(value == 7) label.setIcon(getIcon(30,30,resources.get(123).getResource()));
        if(value == 8) label.setIcon(getIcon(30,30,resources.get(124).getResource()));
        if(value == 9) label.setIcon(getIcon(30,30,resources.get(125).getResource()));
        label.setName(""+value);
    }
    
    //Metoda de setare a datei de nastere.
    private void setDate() {
        setVisibleInputDate(day1Label, day/10);
        setVisibleInputDate(day2Label, day%10);
        setVisibleInputDate(mounth1Label, mounth/10);
        setVisibleInputDate(mounth2Label, mounth%10);
        setVisibleInputDate(year1Label, year/1000);
        setVisibleInputDate(year2Label, year/100%10);
        setVisibleInputDate(year3Label, year%100/10);
        setVisibleInputDate(year4Label, year%10);    
    }
  
    //Metoda de setare a sexului.
    private void setSex(JLabel sexLabel) {
        sexLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (isSettable) {
                    if (sexLabel.equals(maleSexLabel)) {
                        sex = 0;
                        maleSexLabel.setIcon(getIcon(50,50,resources.get(87).getResource()));
                        femaleSexLabel.setIcon(getIcon(50,50,resources.get(88).getResource()));
                    } else {
                        sex = 1;
                        femaleSexLabel.setIcon(getIcon(50,50,resources.get(89).getResource()));
                        maleSexLabel.setIcon(getIcon(50,50,resources.get(86).getResource()));

                    }
                isSex = true;
            }
            }          
        });  
    }
    
    //Metoda de setare a zonei.
    private void setArea(JButton button) {
        button.addActionListener(e -> {
            if (isSettable) {
                resetAreas();
                 
                String name = button.getName();
                area = Integer.parseInt(name);
                
                button.setIcon(getIcon(40,40,resources.get(100+2*area).getResource()));
            }
        });
    }
    
    //Metoda de resetare a campurilor de selectie.
    private void resetComponents() {
        photoNow = null;
        photoBefore = null;
        sex = 0;
        day = 0;
        mounth = 1;
        salary = 800.0;
        area = 0;
        year = 0;
        isSet = false;
        isSex = false;
        isDate = false;
        isPick = false;
        isChanged = false;
        isEmpty = false;
        isIcon = false;
        isSettable = true;
        isEditable = false;
        
        nameShadow.setBackground(new Color(0, 0, 0, 100));
        countryShadow.setBackground(new Color(0, 0, 0, 100));
        regionShadow.setBackground(new Color(0, 0, 0, 100));
        cityShadow.setBackground(new Color(0, 0, 0, 100));
        adressSpot.setForeground(new Color(255, 255, 255));
        adressShadow.setBackground(new Color(0, 0, 0, 100));
        phoneNumberShadow.setBackground(new Color(0, 0, 0, 100));
        birthDateSpot.setForeground(new Color(255,255,255));
        sexSpot.setForeground(new Color(255,255,255));
        PINShadow.setBackground(new Color(0, 0, 0, 100));
        functionSpot.setForeground(new Color(255,255,255));
        functionShadow.setBackground(new Color(0, 0, 0, 100));
        salaryShadow.setBackground(new Color(0, 0, 0, 100));
        areaSpot.setForeground(new Color(255,255,255));
        addPhotoButton.setVisible(true);        
        photoLabel.setVisible(false);
        changeButton.setVisible(false);
        nameField.setEditable(true);
        countryField.setEditable(true);
        regionField.setEditable(true);
        cityField.setEditable(true);
        adressField.setEditable(true);
        phoneNumberField.setEditable(true);
        PINField.setEditable(true);
        functionField.setEditable(true);
        salaryField.setEditable(true);
        setButton.setVisible(true);
        pickButton.setVisible(true);
        addButton.setVisible(true);
        updateButton.setVisible(false);
        goAddButton.setVisible(false);
        day1Label.setVisible(true);
        day2Label.setVisible(true);
        mounth1Label.setVisible(true);
        mounth2Label.setVisible(true);
        year1Label.setVisible(true);
        year2Label.setVisible(true);
        year3Label.setVisible(true);
        year4Label.setVisible(true);
        area1Button.setVisible(false);
        area2Button.setVisible(false);
        area3Button.setVisible(false);
        area4Button.setVisible(false);
        area5Button.setVisible(false);
        area6Button.setVisible(false);
        area7Button.setVisible(false);
        nameField.setText("Name");
        countryField.setText("Country");
        regionField.setText("Region");
        cityField.setText("City");
        adressField.setText("");
        phoneNumberField.setText("Phone Number");
        yearField.setText("Year");
        PINField.setText("PIN");
        functionField.setText("");
        salaryField.setText("Salary");
        mounthSpot.setText("January");
        yearField.setText("Year");
        day1Label.setIcon(getIcon(30,30,resources.get(97).getResource()));
        day2Label.setIcon(getIcon(30,30,resources.get(97).getResource()));
        mounth1Label.setIcon(getIcon(30,30,resources.get(86).getResource()));
        mounth2Label.setIcon(getIcon(30,30,resources.get(86).getResource()));
        year1Label.setIcon(getIcon(30,30,resources.get(99).getResource()));
        year2Label.setIcon(getIcon(30,30,resources.get(99).getResource()));
        year3Label.setIcon(getIcon(30,30,resources.get(99).getResource()));
        year4Label.setIcon(getIcon(30,30,resources.get(99).getResource()));
        resetDays();  
        resetSex();
        resetAreas();
    }
    
    //Metoda de resetare a zilei selectate.
    private void resetDays() {
        if (day == 1) {
            day1Button.setIcon(getIcon(40,40,resources.get(90).getResource()));
        } else {
            if (day == 2) {
                day2Button.setIcon(getIcon(40,40,resources.get(90).getResource()));
            } else {
                if (day == 3) {
                    day3Button.setIcon(getIcon(40,40,resources.get(90).getResource()));
                } else {
                    if (day == 4) {
                        day4Button.setIcon(getIcon(40,40,resources.get(90).getResource()));
                    } else {
                        if (day == 5) {
                            day5Button.setIcon(getIcon(40,40,resources.get(90).getResource()));
                        } else {
                            if (day == 6) {
                                day6Button.setIcon(getIcon(40,40,resources.get(90).getResource()));
                            } else {
                                if (day == 7) { 
                                    day7Button.setIcon(getIcon(40,40,resources.get(90).getResource()));
                                } else {
                                    if (day == 8) {
                                        day8Button.setIcon(getIcon(40,40,resources.get(90).getResource()));
                                    } else {
                                        if (day == 9) {
                                            day9Button.setIcon(getIcon(40,40,resources.get(90).getResource()));
                                        } else {
                                            if (day == 10) {
                                                day10Button.setIcon(getIcon(40,40,resources.get(90).getResource()));
                                            } else {
                                                if (day == 11){
                                                    day11Button.setIcon(getIcon(40,40,resources.get(90).getResource()));
                                                } else {
                                                    if (day == 12){
                                                        day12Button.setIcon(getIcon(40,40,resources.get(90).getResource()));
                                                    } else {
                                                        if (day == 13) {
                                                            day13Button.setIcon(getIcon(40,40,resources.get(90).getResource()));
                                                        } else {
                                                            if (day == 14) {
                                                                day14Button.setIcon(getIcon(40,40,resources.get(90).getResource()));
                                                            } else {
                                                                if (day == 15) { 
                                                                    day15Button.setIcon(getIcon(40,40,resources.get(90).getResource()));
                                                                } else {
                                                                    if (day == 16) {
                                                                        day16Button.setIcon(getIcon(40,40,resources.get(90).getResource()));
                                                                    } else {
                                                                        if (day == 17) {
                                                                            day17Button.setIcon(getIcon(40,40,resources.get(90).getResource()));
                                                                        } else {
                                                                            if (day == 18) {
                                                                                day18Button.setIcon(getIcon(40,40,resources.get(90).getResource()));
                                                                            } else {
                                                                                if (day == 19) {
                                                                                    day19Button.setIcon(getIcon(40,40,resources.get(90).getResource()));
                                                                                } else {
                                                                                    if (day == 20) {
                                                                                        day20Button.setIcon(getIcon(40,40,resources.get(90).getResource()));
                                                                                    } else {
                                                                                        if (day == 21) {
                                                                                            day21Button.setIcon(getIcon(40,40,resources.get(90).getResource()));
                                                                                        }else{
                                                                                            if(day == 22){
                                                                                                day22Button.setIcon(getIcon(40,40,resources.get(90).getResource()));
                                                                                            } else {
                                                                                                if (day == 23) {
                                                                                                    day23Button.setIcon(getIcon(40,40,resources.get(90).getResource()));
                                                                                                } else {
                                                                                                    if (day == 24) {
                                                                                                        day24Button.setIcon(getIcon(40,40,resources.get(90).getResource()));
                                                                                                    } else {
                                                                                                        if (day == 25) {
                                                                                                            day25Button.setIcon(getIcon(40,40,resources.get(90).getResource()));
                                                                                                        } else {
                                                                                                            if (day == 26) {
                                                                                                                day26Button.setIcon(getIcon(40,40,resources.get(90).getResource()));
                                                                                                            } else {
                                                                                                                if (day == 27) {
                                                                                                                    day27Button.setIcon(getIcon(40,40,resources.get(90).getResource()));
                                                                                                                } else {
                                                                                                                    if (day == 28) {
                                                                                                                        day28Button.setIcon(getIcon(40,40,resources.get(90).getResource()));
                                                                                                                    } else {
                                                                                                                        if (day == 29) {
                                                                                                                            day29Button.setIcon(getIcon(40,40,resources.get(90).getResource()));
                                                                                                                        } else {
                                                                                                                            if (day == 30) { 
                                                                                                                                day30Button.setIcon(getIcon(40,40,resources.get(90).getResource()));
                                                                                                                            } else {
                                                                                                                                if (day == 31) {
                                                                                                                                    day31Button.setIcon(getIcon(40,40,resources.get(90).getResource()));
                                                                                                                                }   
                                                                                                                            }   
                                                                                                                        }  
                                                                                                                    }   
                                                                                                                }   
                                                                                                            }   
                                                                                                        }   
                                                                                                    }  
                                                                                                } 
                                                                                            }   
                                                                                        }   
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    //Metoda de resetare a zonei selectate.
    private void resetAreas() {
        String username = restaurants.stream()
            .filter(r -> r.getRestaurantID() == restaurantID)
            .map(r -> r.getUsername())
            .findFirst()
            .orElse(null);

        areas = MainServiceMySQL.getInstance().getAreas(username);
        if (areas.size() == 1) {
            area1Button.setIcon(getIcon(40,40,resources.get(99+areas.get(0).getOrder()*2).getResource()));
        } else {
            if (areas.size() == 2) {
                area1Button.setIcon(getIcon(40,40,resources.get(99+areas.get(0).getOrder()*2).getResource()));
                area2Button.setIcon(getIcon(40,40,resources.get(99+areas.get(1).getOrder()*2).getResource()));
            } else {
                if (areas.size() == 3) {
                    area1Button.setIcon(getIcon(40,40,resources.get(99+areas.get(0).getOrder()*2).getResource()));
                    area2Button.setIcon(getIcon(40,40,resources.get(99+areas.get(1).getOrder()*2).getResource()));
                    area3Button.setIcon(getIcon(40,40,resources.get(99+areas.get(2).getOrder()*2).getResource()));
                } else {
                    if (areas.size() == 4) {
                        area1Button.setIcon(getIcon(40,40,resources.get(99+areas.get(0).getOrder()*2).getResource()));
                        area2Button.setIcon(getIcon(40,40,resources.get(99+areas.get(1).getOrder()*2).getResource()));
                        area3Button.setIcon(getIcon(40,40,resources.get(99+areas.get(2).getOrder()*2).getResource()));
                        area4Button.setIcon(getIcon(40,40,resources.get(99+areas.get(3).getOrder()*2).getResource()));
                    } else {
                        if (areas.size() == 5) {
                            area1Button.setIcon(getIcon(40,40,resources.get(99+areas.get(0).getOrder()*2).getResource()));
                            area2Button.setIcon(getIcon(40,40,resources.get(99+areas.get(1).getOrder()*2).getResource()));
                            area3Button.setIcon(getIcon(40,40,resources.get(99+areas.get(2).getOrder()*2).getResource()));
                            area4Button.setIcon(getIcon(40,40,resources.get(99+areas.get(3).getOrder()*2).getResource()));
                            area5Button.setIcon(getIcon(40,40,resources.get(99+areas.get(4).getOrder()*2).getResource()));
                        } else {
                            if (areas.size() == 5) {
                                area1Button.setIcon(getIcon(40,40,resources.get(99+areas.get(0).getOrder()*2).getResource()));
                                area2Button.setIcon(getIcon(40,40,resources.get(99+areas.get(1).getOrder()*2).getResource()));
                                area3Button.setIcon(getIcon(40,40,resources.get(99+areas.get(2).getOrder()*2).getResource()));
                                area4Button.setIcon(getIcon(40,40,resources.get(99+areas.get(3).getOrder()*2).getResource()));
                                area5Button.setIcon(getIcon(40,40,resources.get(99+areas.get(4).getOrder()*2).getResource()));
                                area6Button.setIcon(getIcon(40,40,resources.get(99+areas.get(5).getOrder()*2).getResource()));
                            } else {
                                area1Button.setIcon(getIcon(40,40,resources.get(99+areas.get(0).getOrder()*2).getResource()));
                                area2Button.setIcon(getIcon(40,40,resources.get(99+areas.get(1).getOrder()*2).getResource()));
                                area3Button.setIcon(getIcon(40,40,resources.get(99+areas.get(2).getOrder()*2).getResource()));
                                area4Button.setIcon(getIcon(40,40,resources.get(99+areas.get(3).getOrder()*2).getResource()));
                                area5Button.setIcon(getIcon(40,40,resources.get(99+areas.get(4).getOrder()*2).getResource()));
                                area6Button.setIcon(getIcon(40,40,resources.get(99+areas.get(5).getOrder()*2).getResource()));
                                area7Button.setIcon(getIcon(40,40,resources.get(99+areas.get(6).getOrder()*2).getResource()));
                            }
                        }
                    }
                }
            }
        }               
    }
    
    //Metode de resetare a campului Sex.
    private void resetSex() {
        sex = 0;
        isSex = false;
        
        maleSexLabel.setIcon(getIcon(50,50,resources.get(86).getResource()));
        femaleSexLabel.setIcon(getIcon(50,50,resources.get(88).getResource()));    
    }
    
    //Metoda de afisare a date picker-ului.
    private void showDateComponents(boolean state) {
        okButton.setVisible(state);
        dateShadow.setVisible(state);
        nextMounthButton.setVisible(state);
        prevMounthButton.setVisible(state);
        mounthSpot.setVisible(state);
        mounthShadow.setVisible(state);
        yearField.setVisible(state);
        yearShadow.setVisible(state);
        day1Button.setVisible(state);
        day2Button.setVisible(state);
        day3Button.setVisible(state);
        day4Button.setVisible(state);
        day5Button.setVisible(state);
        day6Button.setVisible(state);
        day7Button.setVisible(state);
        day8Button.setVisible(state);
        day9Button.setVisible(state);
        day10Button.setVisible(state);
        day11Button.setVisible(state);
        day12Button.setVisible(state);
        day13Button.setVisible(state);
        day14Button.setVisible(state);
        day15Button.setVisible(state);
        day16Button.setVisible(state);
        day17Button.setVisible(state);
        day18Button.setVisible(state);
        day19Button.setVisible(state);
        day20Button.setVisible(state);
        day21Button.setVisible(state);
        day29Button.setVisible(state);
        day23Button.setVisible(state);
        day24Button.setVisible(state);
        day25Button.setVisible(state);
        day26Button.setVisible(state);
        day27Button.setVisible(state);
        day28Button.setVisible(state);
        day22Button.setVisible(state);
        day30Button.setVisible(state);
        day31Button.setVisible(state); 
        pickButton.setVisible(!state);
        day1Label.setVisible(!state);
        day2Label.setVisible(!state);
        mounth1Label.setVisible(!state);
        mounth2Label.setVisible(!state);
        year1Label.setVisible(!state);
        year2Label.setVisible(!state);
        year3Label.setVisible(!state);
        year4Label.setVisible(!state);
        maleSexLabel.setVisible(!state);
        femaleSexLabel.setVisible(!state);
        if(isSet) {
            if (numberOfAreas == 1) {
                area1Button.setVisible(!state);
            } else {
                if (numberOfAreas == 2) {
                    area1Button.setVisible(!state);
                    area2Button.setVisible(!state);
                } else {
                    if (numberOfAreas == 3) {
                        area1Button.setVisible(!state);
                        area2Button.setVisible(!state);
                        area3Button.setVisible(!state);
                    } else {
                        if (numberOfAreas == 4) {
                            area1Button.setVisible(!state);
                            area2Button.setVisible(!state);
                            area3Button.setVisible(!state);
                            area4Button.setVisible(!state);
                        } else {
                            if (numberOfAreas == 5) {
                                area1Button.setVisible(!state);
                                area2Button.setVisible(!state);
                                area3Button.setVisible(!state);
                                area4Button.setVisible(!state);
                                area5Button.setVisible(!state);
                            } else {
                                if (numberOfAreas == 5) {
                                    area1Button.setVisible(!state);
                                    area2Button.setVisible(!state);
                                    area3Button.setVisible(!state);
                                    area4Button.setVisible(!state);
                                    area5Button.setVisible(!state);
                                    area6Button.setVisible(!state);
                                } else {
                                    area1Button.setVisible(!state);
                                    area2Button.setVisible(!state);
                                    area3Button.setVisible(!state);
                                    area4Button.setVisible(!state);
                                    area5Button.setVisible(!state);
                                    area6Button.setVisible(!state);
                                    area7Button.setVisible(!state);
                                }
                            }
                        }
                    }
                }
            }
        }
            
        if (!isSet) {
                setButton.setVisible(!state);
        }

        if (mounthSpot.getText().equals("February") && state == true) {
            day30Button.setVisible(false);
            day31Button.setVisible(false);
        }
        
        if ((mounthSpot.getText().equals("April")|| mounthSpot.getText().equals("June")|| mounthSpot.getText().equals("September") ||mounthSpot.getText().equals("November")) && state == true) {
            day30Button.setVisible(true);
            day31Button.setVisible(false);
        }
    }
    
    //Metoda de activare a butoanelor ce seteaza zona in care lucreaza angajatul.
    private void activateAreas() {
        setButton.setVisible(false);
        isSet = true;
        String username = restaurants.stream()
                .filter(r -> r.getRestaurantID() == restaurantID)
                .map(r -> r.getUsername())
                .findFirst()
                .orElse(null);
        areas = MainServiceMySQL.getInstance().getAreas(username);
        
        if (areas.isEmpty()) {
            isEmpty = true;
            return;
         }  
        if (areas.size() == 1) {
            numberOfAreas = 1;
            area1Button.setVisible(true);
            area1Button.setIcon(getIcon(40,40,resources.get(99 + 2*areas.get(0).getOrder()).getResource()));
            area1Button.setPressedIcon(getIcon(40,40,resources.get(100 + 2*areas.get(0).getOrder()).getResource()));
            area1Button.setName(""+ areas.get(0).getOrder());
            setArea(area1Button); //Pune event pe butoan
        }else {
            if (areas.size() == 2) {
                numberOfAreas = 2;
                area1Button.setVisible(true);
                area2Button.setVisible(true);
                area1Button.setIcon(getIcon(40,40,resources.get(99 + 2*areas.get(0).getOrder()).getResource()));
                area1Button.setPressedIcon(getIcon(40,40,resources.get(100 + 2*areas.get(0).getOrder()).getResource()));
                area2Button.setIcon(getIcon(40,40,resources.get(99 + 2*areas.get(1).getOrder()).getResource()));
                area2Button.setPressedIcon(getIcon(40,40,resources.get(100 + 2*areas.get(1).getOrder()).getResource()));
                area1Button.setName(""+ areas.get(0).getOrder());
                area2Button.setName(""+ areas.get(1).getOrder());
                setArea(area1Button); //Pune event pe butoan
                setArea(area2Button);
            }
            else {
                if (areas.size() == 3) {
                    numberOfAreas = 3;
                    area1Button.setVisible(true);
                    area2Button.setVisible(true);
                    area3Button.setVisible(true);
                    area1Button.setIcon(getIcon(40,40,resources.get(99 + 2*areas.get(0).getOrder()).getResource()));
                    area1Button.setPressedIcon(getIcon(40,40,resources.get(100 + 2*areas.get(0).getOrder()).getResource()));
                    area2Button.setIcon(getIcon(40,40,resources.get(99 + 2*areas.get(1).getOrder()).getResource()));
                    area2Button.setPressedIcon(getIcon(40,40,resources.get(100 + 2*areas.get(1).getOrder()).getResource()));
                    area3Button.setIcon(getIcon(40,40,resources.get(99 + 2*areas.get(2).getOrder()).getResource()));
                    area3Button.setPressedIcon(getIcon(40,40,resources.get(100 + 2*areas.get(2).getOrder()).getResource()));
                    area1Button.setName(""+ areas.get(0).getOrder());
                    area2Button.setName(""+ areas.get(1).getOrder());
                    area3Button.setName(""+ areas.get(2).getOrder());
                    setArea(area1Button); //Pune event pe butoan
                    setArea(area2Button);
                    setArea(area3Button);
                }else {
                    if(areas.size() == 4) {
                        numberOfAreas = 4;
                        area1Button.setVisible(true);
                        area2Button.setVisible(true);
                        area3Button.setVisible(true);
                        area4Button.setVisible(true);
                        area1Button.setIcon(getIcon(40,40,resources.get(99 + 2*areas.get(0).getOrder()).getResource()));
                        area1Button.setPressedIcon(getIcon(40,40,resources.get(100 + 2*areas.get(0).getOrder()).getResource()));
                        area2Button.setIcon(getIcon(40,40,resources.get(99 + 2*areas.get(1).getOrder()).getResource()));
                        area2Button.setPressedIcon(getIcon(40,40,resources.get(100 + 2*areas.get(1).getOrder()).getResource()));
                        area3Button.setIcon(getIcon(40,40,resources.get(99 + 2*areas.get(2).getOrder()).getResource()));
                        area3Button.setPressedIcon(getIcon(40,40,resources.get(100 + 2*areas.get(2).getOrder()).getResource()));
                        area4Button.setIcon(getIcon(40,40,resources.get(99 + 2*areas.get(3).getOrder()).getResource()));
                        area4Button.setPressedIcon(getIcon(40,40,resources.get(100 + 2*areas.get(3).getOrder()).getResource()));
                        area1Button.setName(""+ areas.get(0).getOrder());
                        area2Button.setName(""+ areas.get(1).getOrder());
                        area3Button.setName(""+ areas.get(2).getOrder());
                        area4Button.setName(""+ areas.get(3).getOrder());
                        setArea(area1Button); //Pune event pe butoan
                        setArea(area2Button);
                        setArea(area3Button);
                        setArea(area4Button);
                    }else {
                        if (areas.size() == 5) {
                            numberOfAreas = 5;
                            area1Button.setVisible(true);
                            area2Button.setVisible(true);
                            area3Button.setVisible(true);
                            area4Button.setVisible(true);
                            area5Button.setVisible(true);
                            area1Button.setIcon(getIcon(40,40,resources.get(99 + 2*areas.get(0).getOrder()).getResource()));
                            area1Button.setPressedIcon(getIcon(40,40,resources.get(100 + 2*areas.get(0).getOrder()).getResource()));
                            area2Button.setIcon(getIcon(40,40,resources.get(99 + 2*areas.get(1).getOrder()).getResource()));
                            area2Button.setPressedIcon(getIcon(40,40,resources.get(100 + 2*areas.get(1).getOrder()).getResource()));
                            area3Button.setIcon(getIcon(40,40,resources.get(99 + 2*areas.get(2).getOrder()).getResource()));
                            area3Button.setPressedIcon(getIcon(40,40,resources.get(100 + 2*areas.get(2).getOrder()).getResource()));
                            area4Button.setIcon(getIcon(40,40,resources.get(99 + 2*areas.get(3).getOrder()).getResource()));
                            area4Button.setPressedIcon(getIcon(40,40,resources.get(100 + 2*areas.get(3).getOrder()).getResource()));
                            area5Button.setIcon(getIcon(40,40,resources.get(99 + 2*areas.get(4).getOrder()).getResource()));
                            area5Button.setPressedIcon(getIcon(40,40,resources.get(100 + 2*areas.get(4).getOrder()).getResource()));
                            area1Button.setName(""+ areas.get(0).getOrder());
                            area2Button.setName(""+ areas.get(1).getOrder());
                            area3Button.setName(""+ areas.get(2).getOrder());
                            area4Button.setName(""+ areas.get(3).getOrder());
                            area5Button.setName(""+ areas.get(4).getOrder());
                            setArea(area1Button); //Pune event pe butoan
                            setArea(area2Button);
                            setArea(area3Button);
                            setArea(area4Button);
                            setArea(area5Button);
                        }else {
                            if (areas.size() == 6) {
                                numberOfAreas = 6;
                                area1Button.setVisible(true);
                                area2Button.setVisible(true);
                                area3Button.setVisible(true);
                                area4Button.setVisible(true);
                                area5Button.setVisible(true);
                                area6Button.setVisible(true);
                                area1Button.setIcon(getIcon(40,40,resources.get(99 + 2*areas.get(0).getOrder()).getResource()));
                                area1Button.setPressedIcon(getIcon(40,40,resources.get(100 + 2*areas.get(0).getOrder()).getResource()));
                                area2Button.setIcon(getIcon(40,40,resources.get(99 + 2*areas.get(1).getOrder()).getResource()));
                                area2Button.setPressedIcon(getIcon(40,40,resources.get(100 + 2*areas.get(1).getOrder()).getResource()));
                                area3Button.setIcon(getIcon(40,40,resources.get(99 + 2*areas.get(2).getOrder()).getResource()));
                                area3Button.setPressedIcon(getIcon(40,40,resources.get(100 + 2*areas.get(2).getOrder()).getResource()));
                                area4Button.setIcon(getIcon(40,40,resources.get(99 + 2*areas.get(3).getOrder()).getResource()));
                                area4Button.setPressedIcon(getIcon(40,40,resources.get(100 + 2*areas.get(3).getOrder()).getResource()));
                                area5Button.setIcon(getIcon(40,40,resources.get(99 + 2*areas.get(4).getOrder()).getResource()));
                                area5Button.setPressedIcon(getIcon(40,40,resources.get(100 + 2*areas.get(4).getOrder()).getResource()));
                                area6Button.setIcon(getIcon(40,40,resources.get(99 + 2*areas.get(5).getOrder()).getResource()));
                                area6Button.setPressedIcon(getIcon(40,40,resources.get(100 + 2*areas.get(5).getOrder()).getResource()));
                                area1Button.setName(""+ areas.get(0).getOrder());
                                area2Button.setName(""+ areas.get(1).getOrder());
                                area3Button.setName(""+ areas.get(2).getOrder());
                                area4Button.setName(""+ areas.get(3).getOrder());
                                area5Button.setName(""+ areas.get(4).getOrder());
                                area6Button.setName(""+ areas.get(5).getOrder());
                                setArea(area1Button); //Pune event pe butoan
                                setArea(area2Button);
                                setArea(area3Button);
                                setArea(area4Button);
                                setArea(area5Button);
                                setArea(area6Button);
                            }else {
                                numberOfAreas = 7;
                                area1Button.setVisible(true);
                                area2Button.setVisible(true);
                                area3Button.setVisible(true);
                                area4Button.setVisible(true);
                                area5Button.setVisible(true);
                                area6Button.setVisible(true);
                                area7Button.setVisible(true);
                                area1Button.setIcon(getIcon(40,40,resources.get(99 + 2*areas.get(0).getOrder()).getResource()));
                                area1Button.setPressedIcon(getIcon(40,40,resources.get(100 + 2*areas.get(0).getOrder()).getResource()));
                                area2Button.setIcon(getIcon(40,40,resources.get(99 + 2*areas.get(1).getOrder()).getResource()));
                                area2Button.setPressedIcon(getIcon(40,40,resources.get(100 + 2*areas.get(1).getOrder()).getResource()));
                                area3Button.setIcon(getIcon(40,40,resources.get(99 + 2*areas.get(2).getOrder()).getResource()));
                                area3Button.setPressedIcon(getIcon(40,40,resources.get(100 + 2*areas.get(2).getOrder()).getResource()));
                                area4Button.setIcon(getIcon(40,40,resources.get(99 + 2*areas.get(3).getOrder()).getResource()));
                                area4Button.setPressedIcon(getIcon(40,40,resources.get(100 + 2*areas.get(3).getOrder()).getResource()));
                                area5Button.setIcon(getIcon(40,40,resources.get(99 + 2*areas.get(4).getOrder()).getResource()));
                                area5Button.setPressedIcon(getIcon(40,40,resources.get(100 + 2*areas.get(4).getOrder()).getResource()));
                                area6Button.setIcon(getIcon(40,40,resources.get(99 + 2*areas.get(5).getOrder()).getResource()));
                                area6Button.setPressedIcon(getIcon(40,40,resources.get(100 + 2*areas.get(5).getOrder()).getResource()));
                                area7Button.setIcon(getIcon(40,40,resources.get(99 + 2*areas.get(6).getOrder()).getResource()));
                                area7Button.setPressedIcon(getIcon(40,40,resources.get(100 + 2*areas.get(6).getOrder()).getResource()));
                                area1Button.setName("" + areas.get(0).getOrder());
                                area2Button.setName("" + areas.get(1).getOrder());
                                area3Button.setName("" + areas.get(2).getOrder());
                                area4Button.setName("" + areas.get(3).getOrder());
                                area5Button.setName("" + areas.get(4).getOrder());
                                area6Button.setName("" + areas.get(5).getOrder());
                                area6Button.setName("" + areas.get(6).getOrder());
                                setArea(area1Button);
                                setArea(area2Button);
                                setArea(area3Button);
                                setArea(area4Button);
                                setArea(area5Button);
                                setArea(area6Button);
                                setArea(area7Button);
                            }
                        }
                    }  
                }
            }
        }  
    }
    
    //Metoda de afisare a erorii.
    private void showError(String message) { 
        allertMessageLabel.setVisible(true);
        allertMessagePane.setVisible(true);
        allertMessageField.setText("<htm><center><b><font face =yu-gothic size=32 color=rgb(255,255,255)>"+message+"</b></center></html>");
        retryButton.setVisible(true);
        inactiveBackground.setVisible(true);
        addPhotoButton.setVisible(false);
        photoLabel.setVisible(false);
        addButton.setVisible(false);
        backButton.setVisible(false);
        changeButton.setVisible(false);
    }
    
    //Metoda de verificare a datei de nasterii.
    private boolean checkDate() {        
        Calendar now = Calendar.getInstance();
        int y = now.get(Calendar.YEAR);
        
        if(isPick) {
            if (yearField.getText().equals("Year") || (!yearField.getText().matches("[0-9]+"))) {
                showError("You must to select a year!");
                if (isChanged) {
                    year = Integer.parseInt(year1Label.getName())*1000 + Integer.parseInt(year2Label.getName())*100 + Integer.parseInt(year3Label.getName())*10 + Integer.parseInt(year4Label.getName());
                    mounth = Integer.parseInt(mounth1Label.getName())*10 + Integer.parseInt(mounth2Label.getName());
                    day = Integer.parseInt(day1Label.getName())*10 + Integer.parseInt(day2Label.getName());
                }
                
                return false;
            } else {
                year = Integer.parseInt(yearField.getText());
            
                if (year > y - 18 || year <= 1950) {
                    showError("\nThe year is invalid!");
                    
                    if (isChanged) {
                        year = Integer.parseInt(year1Label.getName())*1000 + Integer.parseInt(year2Label.getName())*100 + Integer.parseInt(year3Label.getName())*10 + Integer.parseInt(year4Label.getName());
                        mounth = Integer.parseInt(mounth1Label.getName())*10 + Integer.parseInt(mounth2Label.getName());
                        day = Integer.parseInt(day1Label.getName())*10 + Integer.parseInt(day2Label.getName());
                    }
                    
                    return false;
                }  else {
                    if (day == 0) {
                        showError("You must to select a day!");
                        
                        if (isChanged) {
                            year = Integer.parseInt(year1Label.getName())*1000 + Integer.parseInt(year2Label.getName())*100 + Integer.parseInt(year3Label.getName())*10 + Integer.parseInt(year4Label.getName());
                            mounth = Integer.parseInt(mounth1Label.getName())*10 + Integer.parseInt(mounth2Label.getName());
                            day = Integer.parseInt(day1Label.getName())*10 + Integer.parseInt(day2Label.getName());
                        }
                        
                        return false;
                    } else {
                        isDate = true;
                        return true;
                    }
                } 
            }
        }

        return true;
    }
    
    //Metoda de verificare a tuturor datelor adaugate.
    private boolean checkData() {
        boolean ok = true;
        
        nameShadow.setBackground(new Color(0, 0, 0, 100));
        countryShadow.setBackground(new Color(0, 0, 0, 100));
        regionShadow.setBackground(new Color(0, 0, 0, 100));
        cityShadow.setBackground(new Color(0, 0, 0, 100));
        adressSpot.setForeground(new Color(255, 255, 255));
        adressShadow.setBackground(new Color(0, 0, 0, 100));
        phoneNumberShadow.setBackground(new Color(0, 0, 0, 100));
        birthDateSpot.setForeground(new Color(255,255,255));
        sexSpot.setForeground(new Color(255,255,255));
        PINShadow.setBackground(new Color(0, 0, 0, 100));
        functionSpot.setForeground(new Color(255,255,255));
        functionShadow.setBackground(new Color(0, 0, 0, 100));
        salaryShadow.setBackground(new Color(0, 0, 0, 100));
        areaSpot.setForeground(new Color(255,255,255));
        
        String username = restaurants.stream()
                .filter(r -> r.getRestaurantID() == restaurantID)
                .map(r -> r.getUsername())
                .findFirst()
                .orElse(null);
        employees = MainServiceMySQL.getInstance().getEmployees(username);
        
        if (nameField.getText().equals("Name") || nameField.getText().equals("")) {            
            ok = false;
            
            showError("Invalide! Check the name field!");
            nameShadow.setBackground(new Color(200, 30, 40, 100));
        }
        
        if (countryField.getText().equals("Country") || countryField.getText().equals("")) {
            ok = false;
            
            showError("Invalide! Check the country field!");
            countryShadow.setBackground(new Color(200, 30, 40, 100)); 
        }
        
        if (regionField.getText().equals("Region") || regionField.getText().equals("")) {
            ok = false;
            
            showError("Invalide! Check the region field!");
            regionShadow.setBackground(new Color(200, 30, 40, 100));
        }
        
        if (cityField.getText().equals("City") || cityField.getText().equals("")) {
            ok = false;
            
            showError("Invalide! Check the city field!");
            cityShadow.setBackground(new Color(200, 30, 40, 100));  
        }

        if (adressField.getText().equals("")) {
            ok = false;
            
            showError("Invalide! Check the adress field!");
            adressSpot.setForeground(new Color(200, 30, 40));  
            adressShadow.setBackground(new Color(200, 30, 40, 100));   
        }
        
        if (!phoneNumberField.getText().matches("[0-9]+")) {
            ok = false;
            
            showError("Invalide! The number contains characters!");
            phoneNumberShadow.setBackground(new Color(200, 30, 40, 100));  
        } else {
            if(!isEditable){
                for (Employee employee:employees) {
                    if (phoneNumberField.getText().equals(employee.getPhoneNumber())) {
                        ok = false;
                        
                        showError("Employee with the same phone number!");
                        phoneNumberShadow.setBackground(new Color(200, 30, 40, 100));
                        break;
                    } 
                } 
            } else {
                if(!phoneNumberField.getText().equals(phoneReminder)) {
                    for (Employee employee:employees) {
                        if (phoneNumberField.getText().equals(employee.getPhoneNumber())) {
                            ok = false;
                        
                            showError("Employee with the same phone number!");
                            phoneNumberShadow.setBackground(new Color(200, 30, 40, 100));
                            break;
                        } 
                    } 
                }
            }
        }
        
        isPick = true;
        
        if(!isChanged) {
            checkDate();
        }
        
        if (!isDate) {
            ok = false;
            
            showError("Invalide! Choose a birthDate!");
            birthDateSpot.setForeground(new Color(200, 30, 40)); 
        }
        
        if (!isSex) {
            ok = false;
            
            showError("Invalide! Choose a gender!");
            sexSpot.setForeground(new Color(200, 30, 40)); 
        } 
         
        if (!PINField.getText().matches("[0-9]+") || PINField.getText().length() != 13) {
            ok = false;
            
            showError("The PIN must have 13 digits and no character!");
            PINShadow.setBackground(new Color(200, 30, 40, 100));
        }else { //Daca continutul nu e editabil, verificarea nu e necesara
            
            if (!isEditable) {
                for (Employee employee:employees) {
                    if (PINField.getText().equals(employee.getPIN())) {
                        ok = false;
                        
                        showError("Employee with the same PIN!");
                        PINShadow.setBackground(new Color(200, 30, 40, 100));
                        break;
                    } 
                }
            }  else {
                if(!PINField.getText().equals(PINReminder)) {
                    for (Employee employee:employees) {
                        if (PINField.getText().equals(employee.getPIN())) {
                            ok = false;
                        
                            showError("Employee with the same PIN!");
                            PINShadow.setBackground(new Color(200, 30, 40, 100));
                            break;
                        } 
                    } 
                }
            } 
        }
        
        if (functionField.getText().equals("")) {
            ok = false;
            
            showError("Invalide! Check the function field!");
            functionSpot.setForeground(new Color(200, 30, 40));  
            functionShadow.setBackground(new Color(200, 30, 40, 100));
        }
        
        if (!salaryField.getText().matches("^(\\d+\\.)?\\d+$") || salaryField.getText().startsWith("0")) {
            ok = false;
            
            showError("Invalide! Check the salary field!");                                  
            salaryShadow.setBackground(new Color(200, 30, 40, 100));
        } else {
            salary = Double.parseDouble(salaryField.getText());
            
            if (salary <= 799) {
                ok = false;
                
                showError("Invalide! Salary must be greater than 800!");                                  
                salaryShadow.setBackground(new Color(200, 30, 40, 100));
            }
        }
        
        if (area == 0) {
            ok = false;
            
            showError("Invalide! Select area for employee!");
            areaSpot.setForeground(new Color(200,30,40));
        } 
        
        if (isEmpty) {
            ok = false;
            
            showError("Error! No area in the restaurant!");
            areaSpot.setForeground(new Color(200,30,40));
        }
            
        return ok;
    }
    //Metoda de selectare a datei de nastere.
    private void pick() {
        dateShadow.setVisible(true);
        mounthSpot.setVisible(true);
        mounthShadow.setVisible(true);        
        prevMounthButton.setVisible(true);
        nextMounthButton.setVisible(true);
        showDateComponents(true);
        yearField.setVisible(true);
        yearShadow.setVisible(true);
        okButton.setVisible(true);
        pickButton.setVisible(false);
        day1Label.setVisible(false);
        day2Label.setVisible(false);
        mounth1Label.setVisible(false);
        mounth2Label.setVisible(false);
        year1Label.setVisible(false);
        year2Label.setVisible(false);
        year3Label.setVisible(false);
        year4Label.setVisible(false);
        
        isPick = true;
    }
    
    //Metoda de revenire la pagina de baza, dupa aparitia unei erori.
    private void retry() {
        allertMessageLabel.setVisible(false);
        allertMessagePane.setVisible(false);
        retryButton.setVisible(false);
        inactiveBackground.setVisible(false);
        backButton.setVisible(true);
        
        if (!isEditable) { //Daca nu ne aflam in zona de editare angajat 
            addButton.setVisible(true);
            
            if (!isIcon) {
                addPhotoButton.setVisible(true);
            } else {
                photoLabel.setVisible(true);
                changeButton.setVisible(true);
            }
        } else {
            photoLabel.setVisible(true);
            changeButton.setVisible(true);
        }      
    }
    
    //Metoda de selectare a pozei. 
    private void selectPhoto() {
        photoBefore = photoNow;
        JFileChooser fileChooser = new JFileChooser();
        
        fileChooser.setCurrentDirectory(new File("E:\\Limbaje de programare - editare - design\\SQL\\Aplicatii\\Tema - Gestiunea comenzilor in restaurante"));
        FileNameExtensionFilter filter = new FileNameExtensionFilter("*.IMAGE", "jpg","gif","png");
        
        fileChooser.setFileFilter(filter);
        
        int result = fileChooser.showSaveDialog(null);

        if (result != JFileChooser.CANCEL_OPTION) {
            long size = fileChooser.getSelectedFile().length();
            
            if (size/1024 >= 200) {
                showError("Choose a photo smaller then 200 KB");
            } else {
                if(result == JFileChooser.APPROVE_OPTION){
                    try {
                        photoNow = Files.readAllBytes(fileChooser.getSelectedFile().toPath());
                        isIcon = true;
                        changeButton.setVisible(true);
                    } catch(IOException e) {
                        System.out.println(e.getMessage());
                    }
                    
                    photoLabel.setVisible(true);
                    photoLabel.setIcon(getIcon(130, 150, photoNow));
                    addPhotoButton.setVisible(false);
                }  
            } 
        }
    }
    
    //Metoda de modificare a pozei alese.
    private void changePhoto() {
        if (photoLabel.isVisible()) {
            selectPhoto();
            changeButton.setText("Change");     
        }
    }
    
    //Metoda de citire ca sir de caractere pentru data de nastere a angajatului.
    private String dateToString() {  
        return year+"-"+mounth+"-"+day;
    }
    
    //Metoda de adaugare angajat în baza de date.
    private void add() {
        isAdd = true;
        if (checkData()) {

            Date birthDate = new GregorianCalendar(year, mounth, day).getTime();
            
            int realAreaID = areas.get(area-1).getAreaID();

            Employee emp = new Employee(realAreaID, nameField.getText(), countryField.getText(), regionField.getText(), cityField.getText(), adressField.getText(), phoneNumberField.getText(), birthDate, sex, PINField.getText(), functionField.getText(), salary, photoNow);
            isAdd = false;
             
            MainServiceMySQL.getInstance().addEmployee(emp, restaurantID);

            allEmployeesPanel.removeAll();
            setEmployeesFrame();
            resetComponents();
        }
    }

    //Metoda de revenire la pagina Edit.
    private void back() {
        EditFrame edit = new EditFrame();
        
        edit.setLocationRelativeTo(null);
        edit.setVisible(true);
        edit.settingBackground(background.getIcon(), index, restaurantID);
        this.setVisible(false);
    }
    
    //Metoda de setare fundal si id-ul restaurantului, apelata din pagina Edit.
    public void settingBackground(Icon resource, int index, int restaurantID) {
        background.setIcon(resource);
        
        this.index = index;
        this.restaurantID = restaurantID;
    }

    //Metoda de setare si conversie binara a iconitelor preluate din baza de date, a tuturor componentelor(butoane, cadre, fundaluri).
    private ImageIcon getIcon (int width, int height, byte[] image) {
        BufferedImage img = null; 

        try {  
            img = ImageIO.read(new ByteArrayInputStream(image));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }  

        ImageIcon icon = new ImageIcon(img); 
        Image scaleImage = icon.getImage().getScaledInstance(width, height,Image.SCALE_DEFAULT);
        icon = new ImageIcon(scaleImage);
             
        return icon;  
    }
    /**
     * Nu modifica!
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        retryButton = new javax.swing.JButton();
        allertMessagePane = new javax.swing.JScrollPane();
        allertMessageField = new javax.swing.JTextPane();
        allertMessageLabel = new javax.swing.JLabel();
        inactiveBackground = new javax.swing.JLabel();
        addPhotoButton = new javax.swing.JButton();
        changeButton = new javax.swing.JButton();
        addButton = new javax.swing.JButton();
        updateButton = new javax.swing.JButton();
        goAddButton = new javax.swing.JButton();
        text3Spot = new javax.swing.JTextField();
        adressSpot = new javax.swing.JTextField();
        sexSpot = new javax.swing.JTextField();
        functionSpot = new javax.swing.JTextField();
        setButton = new javax.swing.JButton();
        nameField = new javax.swing.JTextField();
        countryField = new javax.swing.JTextField();
        regionField = new javax.swing.JTextField();
        cityField = new javax.swing.JTextField();
        adressPane = new javax.swing.JScrollPane();
        adressField = new javax.swing.JTextPane();
        phoneNumberField = new javax.swing.JTextField();
        birthDateSpot = new javax.swing.JTextField();
        photoLabel = new javax.swing.JLabel();
        nameShadow = new javax.swing.JLabel();
        countryShadow = new javax.swing.JLabel();
        regionShadow = new javax.swing.JLabel();
        cityShadow = new javax.swing.JLabel();
        adressShadow = new javax.swing.JLabel();
        phoneNumberShadow = new javax.swing.JLabel();
        prevMounthButton = new javax.swing.JButton();
        nextMounthButton = new javax.swing.JButton();
        pickButton = new javax.swing.JButton();
        day1Label = new javax.swing.JLabel();
        day2Label = new javax.swing.JLabel();
        mounth1Label = new javax.swing.JLabel();
        mounth2Label = new javax.swing.JLabel();
        year1Label = new javax.swing.JLabel();
        year2Label = new javax.swing.JLabel();
        year3Label = new javax.swing.JLabel();
        year4Label = new javax.swing.JLabel();
        mounthSpot = new javax.swing.JTextField();
        mounthShadow = new javax.swing.JLabel();
        day1Button = new javax.swing.JButton();
        day2Button = new javax.swing.JButton();
        day3Button = new javax.swing.JButton();
        day4Button = new javax.swing.JButton();
        day5Button = new javax.swing.JButton();
        day6Button = new javax.swing.JButton();
        day7Button = new javax.swing.JButton();
        day8Button = new javax.swing.JButton();
        day9Button = new javax.swing.JButton();
        day10Button = new javax.swing.JButton();
        day11Button = new javax.swing.JButton();
        day12Button = new javax.swing.JButton();
        day13Button = new javax.swing.JButton();
        day14Button = new javax.swing.JButton();
        day15Button = new javax.swing.JButton();
        day16Button = new javax.swing.JButton();
        day17Button = new javax.swing.JButton();
        day18Button = new javax.swing.JButton();
        day19Button = new javax.swing.JButton();
        day20Button = new javax.swing.JButton();
        day21Button = new javax.swing.JButton();
        day22Button = new javax.swing.JButton();
        day23Button = new javax.swing.JButton();
        day24Button = new javax.swing.JButton();
        day25Button = new javax.swing.JButton();
        day26Button = new javax.swing.JButton();
        day27Button = new javax.swing.JButton();
        day28Button = new javax.swing.JButton();
        day29Button = new javax.swing.JButton();
        day30Button = new javax.swing.JButton();
        day31Button = new javax.swing.JButton();
        yearField = new javax.swing.JTextField();
        yearShadow = new javax.swing.JLabel();
        okButton = new javax.swing.JButton();
        dateShadow = new javax.swing.JLabel();
        areaSpot = new javax.swing.JTextField();
        area1Button = new javax.swing.JButton();
        area2Button = new javax.swing.JButton();
        area3Button = new javax.swing.JButton();
        area4Button = new javax.swing.JButton();
        area5Button = new javax.swing.JButton();
        area6Button = new javax.swing.JButton();
        area7Button = new javax.swing.JButton();
        PINField = new javax.swing.JTextField();
        functionPane = new javax.swing.JScrollPane();
        functionField = new javax.swing.JTextPane();
        salaryField = new javax.swing.JTextField();
        functionShadow = new javax.swing.JLabel();
        salaryShadow = new javax.swing.JLabel();
        maleSexLabel = new javax.swing.JLabel();
        femaleSexLabel = new javax.swing.JLabel();
        PINShadow = new javax.swing.JLabel();
        showEmployees = new javax.swing.JButton();
        nextButton = new javax.swing.JButton();
        prevButton = new javax.swing.JButton();
        allEmployeesPanel = new javax.swing.JPanel();
        allEmployeesLabel = new javax.swing.JLabel();
        employeeViewLabel = new javax.swing.JLabel();
        text2Spot = new javax.swing.JTextField();
        text1Spot = new javax.swing.JTextField();
        backButton = new javax.swing.JButton();
        background = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(1400, 780));
        setName("Edit"); // NOI18N
        setPreferredSize(new java.awt.Dimension(1400, 780));
        setSize(new java.awt.Dimension(1400, 780));
        getContentPane().setLayout(null);

        retryButton.setBackground(new java.awt.Color(255, 255, 255));
        retryButton.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 36)); // NOI18N
        retryButton.setForeground(new java.awt.Color(255, 255, 255));
        retryButton.setText("Retry");
        retryButton.setBorder(null);
        retryButton.setBorderPainted(false);
        retryButton.setContentAreaFilled(false);
        retryButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        retryButton.setInheritsPopupMenu(true);
        retryButton.setMaximumSize(new java.awt.Dimension(160, 70));
        retryButton.setMinimumSize(new java.awt.Dimension(0, 0));
        retryButton.setPreferredSize(new java.awt.Dimension(160, 70));
        retryButton.setRequestFocusEnabled(false);
        getContentPane().add(retryButton);
        retryButton.setBounds(710, 340, 160, 70);

        allertMessagePane.setBackground(new java.awt.Color(255, 255, 255));
        allertMessagePane.setBorder(null);
        allertMessagePane.setForeground(new java.awt.Color(255, 255, 255));
        allertMessagePane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        allertMessagePane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        allertMessagePane.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        allertMessagePane.setMaximumSize(new java.awt.Dimension(250, 80));
        allertMessagePane.setMinimumSize(new java.awt.Dimension(0, 0));
        allertMessagePane.setOpaque(false);

        allertMessageField.setEditable(false);
        allertMessageField.setBorder(null);
        allertMessageField.setContentType("text/html"); // NOI18N
        allertMessageField.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 36)); // NOI18N
        allertMessageField.setForeground(new java.awt.Color(255, 255, 255));
        allertMessageField.setCaretColor(new java.awt.Color(255, 255, 255));
        allertMessageField.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        allertMessageField.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        allertMessageField.setMaximumSize(new java.awt.Dimension(250, 80));
        allertMessageField.setOpaque(false);
        allertMessageField.setPreferredSize(new java.awt.Dimension(250, 80));
        allertMessageField.setSelectionColor(new java.awt.Color(255, 255, 255));
        allertMessagePane.setViewportView(allertMessageField);

        getContentPane().add(allertMessagePane);
        allertMessagePane.setBounds(545, 220, 310, 130);

        allertMessageLabel.setBackground(new java.awt.Color(255, 255, 255));
        allertMessageLabel.setForeground(new java.awt.Color(255, 255, 255));
        allertMessageLabel.setMaximumSize(new java.awt.Dimension(430, 180));
        allertMessageLabel.setPreferredSize(new java.awt.Dimension(430, 240));
        getContentPane().add(allertMessageLabel);
        allertMessageLabel.setBounds(490, 180, 430, 240);

        inactiveBackground.setBackground(new java.awt.Color(255, 255, 255));
        inactiveBackground.setForeground(new java.awt.Color(255, 255, 255));
        inactiveBackground.setMaximumSize(new java.awt.Dimension(1400, 780));
        inactiveBackground.setOpaque(true);
        inactiveBackground.setPreferredSize(new java.awt.Dimension(1400, 780));
        getContentPane().add(inactiveBackground);
        inactiveBackground.setBounds(0, 0, 1400, 780);

        addPhotoButton.setBackground(new java.awt.Color(255, 255, 255));
        addPhotoButton.setForeground(new java.awt.Color(255, 255, 255));
        addPhotoButton.setBorder(null);
        addPhotoButton.setContentAreaFilled(false);
        addPhotoButton.setMaximumSize(new java.awt.Dimension(130, 150));
        addPhotoButton.setMinimumSize(new java.awt.Dimension(0, 0));
        addPhotoButton.setPreferredSize(new java.awt.Dimension(130, 150));
        getContentPane().add(addPhotoButton);
        addPhotoButton.setBounds(440, 65, 130, 150);

        changeButton.setBackground(new java.awt.Color(255, 255, 255));
        changeButton.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 24)); // NOI18N
        changeButton.setForeground(new java.awt.Color(255, 255, 255));
        changeButton.setText("Change");
        changeButton.setBorder(null);
        changeButton.setContentAreaFilled(false);
        changeButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        changeButton.setMaximumSize(new java.awt.Dimension(120, 70));
        changeButton.setMinimumSize(new java.awt.Dimension(0, 0));
        changeButton.setPreferredSize(new java.awt.Dimension(120, 70));
        getContentPane().add(changeButton);
        changeButton.setBounds(450, 230, 120, 70);

        addButton.setBackground(new java.awt.Color(255, 255, 255));
        addButton.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 24)); // NOI18N
        addButton.setForeground(new java.awt.Color(255, 255, 255));
        addButton.setBorder(null);
        addButton.setContentAreaFilled(false);
        addButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        addButton.setMaximumSize(new java.awt.Dimension(80, 80));
        addButton.setMinimumSize(new java.awt.Dimension(0, 0));
        addButton.setPreferredSize(new java.awt.Dimension(80, 80));
        getContentPane().add(addButton);
        addButton.setBounds(510, 580, 80, 80);

        updateButton.setBackground(new java.awt.Color(255, 255, 255));
        updateButton.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 24)); // NOI18N
        updateButton.setForeground(new java.awt.Color(255, 255, 255));
        updateButton.setBorder(null);
        updateButton.setContentAreaFilled(false);
        updateButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        updateButton.setMaximumSize(new java.awt.Dimension(80, 80));
        updateButton.setMinimumSize(new java.awt.Dimension(0, 0));
        updateButton.setPreferredSize(new java.awt.Dimension(80, 80));
        getContentPane().add(updateButton);
        updateButton.setBounds(430, 580, 80, 80);

        goAddButton.setBackground(new java.awt.Color(255, 255, 255));
        goAddButton.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 24)); // NOI18N
        goAddButton.setForeground(new java.awt.Color(255, 255, 255));
        goAddButton.setBorder(null);
        goAddButton.setContentAreaFilled(false);
        goAddButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        goAddButton.setMaximumSize(new java.awt.Dimension(80, 80));
        goAddButton.setMinimumSize(new java.awt.Dimension(0, 0));
        goAddButton.setPreferredSize(new java.awt.Dimension(80, 80));
        getContentPane().add(goAddButton);
        goAddButton.setBounds(510, 580, 80, 80);

        text3Spot.setEditable(false);
        text3Spot.setBackground(new java.awt.Color(255, 255, 255));
        text3Spot.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 48)); // NOI18N
        text3Spot.setForeground(new java.awt.Color(255, 255, 255));
        text3Spot.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        text3Spot.setToolTipText("");
        text3Spot.setBorder(null);
        text3Spot.setCaretColor(new java.awt.Color(255, 255, 255));
        text3Spot.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        text3Spot.setMaximumSize(new java.awt.Dimension(110, 40));
        text3Spot.setMinimumSize(new java.awt.Dimension(0, 0));
        text3Spot.setOpaque(false);
        text3Spot.setPreferredSize(new java.awt.Dimension(330, 80));
        text3Spot.setSelectionColor(new java.awt.Color(255, 255, 255));
        getContentPane().add(text3Spot);
        text3Spot.setBounds(860, 130, 390, 70);

        adressSpot.setEditable(false);
        adressSpot.setBackground(new java.awt.Color(255, 255, 255));
        adressSpot.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 24)); // NOI18N
        adressSpot.setForeground(new java.awt.Color(255, 255, 255));
        adressSpot.setText("Adress");
        adressSpot.setToolTipText("");
        adressSpot.setBorder(null);
        adressSpot.setCaretColor(new java.awt.Color(255, 255, 255));
        adressSpot.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        adressSpot.setMaximumSize(new java.awt.Dimension(80, 40));
        adressSpot.setMinimumSize(new java.awt.Dimension(0, 0));
        adressSpot.setOpaque(false);
        adressSpot.setPreferredSize(new java.awt.Dimension(80, 40));
        adressSpot.setSelectionColor(new java.awt.Color(255, 255, 255));
        getContentPane().add(adressSpot);
        adressSpot.setBounds(50, 245, 80, 40);

        sexSpot.setEditable(false);
        sexSpot.setBackground(new java.awt.Color(255, 255, 255));
        sexSpot.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 24)); // NOI18N
        sexSpot.setForeground(new java.awt.Color(255, 255, 255));
        sexSpot.setText("Sex");
        sexSpot.setToolTipText("");
        sexSpot.setBorder(null);
        sexSpot.setCaretColor(new java.awt.Color(255, 255, 255));
        sexSpot.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        sexSpot.setMaximumSize(new java.awt.Dimension(40, 40));
        sexSpot.setMinimumSize(new java.awt.Dimension(0, 0));
        sexSpot.setOpaque(false);
        sexSpot.setPreferredSize(new java.awt.Dimension(40, 40));
        sexSpot.setSelectionColor(new java.awt.Color(255, 255, 255));
        getContentPane().add(sexSpot);
        sexSpot.setBounds(50, 425, 40, 40);

        functionSpot.setEditable(false);
        functionSpot.setBackground(new java.awt.Color(255, 255, 255));
        functionSpot.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 24)); // NOI18N
        functionSpot.setForeground(new java.awt.Color(255, 255, 255));
        functionSpot.setText("Function");
        functionSpot.setToolTipText("");
        functionSpot.setBorder(null);
        functionSpot.setCaretColor(new java.awt.Color(255, 255, 255));
        functionSpot.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        functionSpot.setMaximumSize(new java.awt.Dimension(100, 40));
        functionSpot.setMinimumSize(new java.awt.Dimension(0, 0));
        functionSpot.setOpaque(false);
        functionSpot.setPreferredSize(new java.awt.Dimension(100, 40));
        functionSpot.setSelectionColor(new java.awt.Color(255, 255, 255));
        getContentPane().add(functionSpot);
        functionSpot.setBounds(50, 515, 100, 40);

        setButton.setBackground(new java.awt.Color(255, 255, 255));
        setButton.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 24)); // NOI18N
        setButton.setForeground(new java.awt.Color(255, 255, 255));
        setButton.setText("Set");
        setButton.setBorder(null);
        setButton.setContentAreaFilled(false);
        setButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        setButton.setMaximumSize(new java.awt.Dimension(120, 70));
        setButton.setMinimumSize(new java.awt.Dimension(0, 0));
        setButton.setPreferredSize(new java.awt.Dimension(120, 70));
        getContentPane().add(setButton);
        setButton.setBounds(130, 650, 70, 40);

        nameField.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 24)); // NOI18N
        nameField.setForeground(new java.awt.Color(255, 255, 255));
        nameField.setText("Name");
        nameField.setBorder(null);
        nameField.setCaretColor(new java.awt.Color(255, 255, 255));
        nameField.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        nameField.setMaximumSize(new java.awt.Dimension(350, 40));
        nameField.setMinimumSize(new java.awt.Dimension(0, 0));
        nameField.setOpaque(false);
        nameField.setPreferredSize(new java.awt.Dimension(350, 40));
        nameField.setSelectionColor(new java.awt.Color(255, 255, 255));
        getContentPane().add(nameField);
        nameField.setBounds(50, 65, 350, 40);

        countryField.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 24)); // NOI18N
        countryField.setForeground(new java.awt.Color(255, 255, 255));
        countryField.setText("Country");
        countryField.setBorder(null);
        countryField.setCaretColor(new java.awt.Color(255, 255, 255));
        countryField.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        countryField.setMaximumSize(new java.awt.Dimension(350, 40));
        countryField.setMinimumSize(new java.awt.Dimension(0, 0));
        countryField.setOpaque(false);
        countryField.setPreferredSize(new java.awt.Dimension(350, 40));
        countryField.setSelectionColor(new java.awt.Color(255, 255, 255));
        getContentPane().add(countryField);
        countryField.setBounds(50, 110, 350, 40);

        regionField.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 24)); // NOI18N
        regionField.setForeground(new java.awt.Color(255, 255, 255));
        regionField.setText("Region");
        regionField.setBorder(null);
        regionField.setCaretColor(new java.awt.Color(255, 255, 255));
        regionField.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        regionField.setMaximumSize(new java.awt.Dimension(350, 40));
        regionField.setMinimumSize(new java.awt.Dimension(0, 0));
        regionField.setOpaque(false);
        regionField.setPreferredSize(new java.awt.Dimension(350, 40));
        regionField.setSelectionColor(new java.awt.Color(255, 255, 255));
        getContentPane().add(regionField);
        regionField.setBounds(50, 155, 350, 40);

        cityField.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 24)); // NOI18N
        cityField.setForeground(new java.awt.Color(255, 255, 255));
        cityField.setText("City");
        cityField.setBorder(null);
        cityField.setCaretColor(new java.awt.Color(255, 255, 255));
        cityField.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        cityField.setMaximumSize(new java.awt.Dimension(350, 40));
        cityField.setMinimumSize(new java.awt.Dimension(0, 0));
        cityField.setOpaque(false);
        cityField.setPreferredSize(new java.awt.Dimension(350, 40));
        cityField.setSelectionColor(new java.awt.Color(255, 255, 255));
        getContentPane().add(cityField);
        cityField.setBounds(50, 200, 350, 40);

        adressPane.setBackground(new java.awt.Color(255, 255, 255));
        adressPane.setBorder(null);
        adressPane.setForeground(new java.awt.Color(255, 255, 255));
        adressPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        adressPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        adressPane.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 24)); // NOI18N
        adressPane.setMaximumSize(new java.awt.Dimension(270, 80));
        adressPane.setMinimumSize(new java.awt.Dimension(0, 0));
        adressPane.setOpaque(false);
        adressPane.setPreferredSize(new java.awt.Dimension(270, 80));

        adressField.setBorder(null);
        adressField.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 24)); // NOI18N
        adressField.setForeground(new java.awt.Color(255, 255, 255));
        adressField.setCaretColor(new java.awt.Color(255, 255, 255));
        adressField.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        adressField.setMaximumSize(new java.awt.Dimension(270, 80));
        adressField.setOpaque(false);
        adressField.setPreferredSize(new java.awt.Dimension(270, 80));
        adressField.setSelectionColor(new java.awt.Color(255, 255, 255));
        adressPane.setViewportView(adressField);

        getContentPane().add(adressPane);
        adressPane.setBounds(130, 245, 270, 80);

        phoneNumberField.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 24)); // NOI18N
        phoneNumberField.setForeground(new java.awt.Color(255, 255, 255));
        phoneNumberField.setText("Phone Number");
        phoneNumberField.setBorder(null);
        phoneNumberField.setCaretColor(new java.awt.Color(255, 255, 255));
        phoneNumberField.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        phoneNumberField.setMaximumSize(new java.awt.Dimension(350, 40));
        phoneNumberField.setMinimumSize(new java.awt.Dimension(0, 0));
        phoneNumberField.setOpaque(false);
        phoneNumberField.setPreferredSize(new java.awt.Dimension(350, 40));
        phoneNumberField.setSelectionColor(new java.awt.Color(255, 255, 255));
        getContentPane().add(phoneNumberField);
        phoneNumberField.setBounds(50, 335, 350, 40);

        birthDateSpot.setEditable(false);
        birthDateSpot.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 24)); // NOI18N
        birthDateSpot.setForeground(new java.awt.Color(255, 255, 255));
        birthDateSpot.setText("Birthdate");
        birthDateSpot.setToolTipText("");
        birthDateSpot.setBorder(null);
        birthDateSpot.setCaretColor(new java.awt.Color(255, 255, 255));
        birthDateSpot.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        birthDateSpot.setMaximumSize(new java.awt.Dimension(350, 40));
        birthDateSpot.setMinimumSize(new java.awt.Dimension(0, 0));
        birthDateSpot.setOpaque(false);
        birthDateSpot.setPreferredSize(new java.awt.Dimension(350, 40));
        birthDateSpot.setSelectionColor(new java.awt.Color(255, 255, 255));
        getContentPane().add(birthDateSpot);
        birthDateSpot.setBounds(50, 380, 120, 40);

        photoLabel.setBackground(new java.awt.Color(255, 255, 255));
        photoLabel.setForeground(new java.awt.Color(255, 255, 255));
        photoLabel.setMaximumSize(new java.awt.Dimension(130, 150));
        photoLabel.setOpaque(true);
        photoLabel.setPreferredSize(new java.awt.Dimension(130, 150));
        getContentPane().add(photoLabel);
        photoLabel.setBounds(440, 65, 130, 150);

        nameShadow.setBackground(new java.awt.Color(255, 255, 255));
        nameShadow.setForeground(new java.awt.Color(255, 255, 255));
        nameShadow.setMaximumSize(new java.awt.Dimension(350, 40));
        nameShadow.setOpaque(true);
        nameShadow.setPreferredSize(new java.awt.Dimension(350, 40));
        getContentPane().add(nameShadow);
        nameShadow.setBounds(50, 65, 350, 40);

        countryShadow.setBackground(new java.awt.Color(255, 255, 255));
        countryShadow.setForeground(new java.awt.Color(255, 255, 255));
        countryShadow.setMaximumSize(new java.awt.Dimension(350, 40));
        countryShadow.setOpaque(true);
        countryShadow.setPreferredSize(new java.awt.Dimension(350, 40));
        getContentPane().add(countryShadow);
        countryShadow.setBounds(50, 110, 350, 40);

        regionShadow.setBackground(new java.awt.Color(255, 255, 255));
        regionShadow.setForeground(new java.awt.Color(255, 255, 255));
        regionShadow.setMaximumSize(new java.awt.Dimension(350, 40));
        regionShadow.setOpaque(true);
        regionShadow.setPreferredSize(new java.awt.Dimension(350, 40));
        getContentPane().add(regionShadow);
        regionShadow.setBounds(50, 155, 350, 40);

        cityShadow.setBackground(new java.awt.Color(255, 255, 255));
        cityShadow.setForeground(new java.awt.Color(255, 255, 255));
        cityShadow.setMaximumSize(new java.awt.Dimension(350, 40));
        cityShadow.setOpaque(true);
        cityShadow.setPreferredSize(new java.awt.Dimension(350, 40));
        getContentPane().add(cityShadow);
        cityShadow.setBounds(50, 200, 350, 40);

        adressShadow.setBackground(new java.awt.Color(255, 255, 255));
        adressShadow.setForeground(new java.awt.Color(255, 255, 255));
        adressShadow.setMaximumSize(new java.awt.Dimension(270, 80));
        adressShadow.setOpaque(true);
        adressShadow.setPreferredSize(new java.awt.Dimension(270, 80));
        getContentPane().add(adressShadow);
        adressShadow.setBounds(130, 245, 270, 80);

        phoneNumberShadow.setBackground(new java.awt.Color(255, 255, 255));
        phoneNumberShadow.setForeground(new java.awt.Color(255, 255, 255));
        phoneNumberShadow.setMaximumSize(new java.awt.Dimension(350, 40));
        phoneNumberShadow.setOpaque(true);
        phoneNumberShadow.setPreferredSize(new java.awt.Dimension(350, 40));
        getContentPane().add(phoneNumberShadow);
        phoneNumberShadow.setBounds(50, 335, 350, 40);

        prevMounthButton.setBackground(new java.awt.Color(255, 255, 255));
        prevMounthButton.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 24)); // NOI18N
        prevMounthButton.setForeground(new java.awt.Color(255, 255, 255));
        prevMounthButton.setBorder(null);
        prevMounthButton.setContentAreaFilled(false);
        prevMounthButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        prevMounthButton.setMaximumSize(new java.awt.Dimension(120, 70));
        prevMounthButton.setMinimumSize(new java.awt.Dimension(0, 0));
        prevMounthButton.setPreferredSize(new java.awt.Dimension(120, 70));
        getContentPane().add(prevMounthButton);
        prevMounthButton.setBounds(155, 380, 40, 40);

        nextMounthButton.setBackground(new java.awt.Color(255, 255, 255));
        nextMounthButton.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 24)); // NOI18N
        nextMounthButton.setForeground(new java.awt.Color(255, 255, 255));
        nextMounthButton.setBorder(null);
        nextMounthButton.setContentAreaFilled(false);
        nextMounthButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        nextMounthButton.setMaximumSize(new java.awt.Dimension(120, 70));
        nextMounthButton.setMinimumSize(new java.awt.Dimension(0, 0));
        nextMounthButton.setPreferredSize(new java.awt.Dimension(120, 70));
        getContentPane().add(nextMounthButton);
        nextMounthButton.setBounds(400, 380, 40, 40);

        pickButton.setBackground(new java.awt.Color(255, 255, 255));
        pickButton.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 24)); // NOI18N
        pickButton.setForeground(new java.awt.Color(255, 255, 255));
        pickButton.setText("Pick");
        pickButton.setBorder(null);
        pickButton.setContentAreaFilled(false);
        pickButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        pickButton.setMaximumSize(new java.awt.Dimension(120, 70));
        pickButton.setMinimumSize(new java.awt.Dimension(0, 0));
        pickButton.setPreferredSize(new java.awt.Dimension(120, 70));
        getContentPane().add(pickButton);
        pickButton.setBounds(150, 380, 70, 40);

        day1Label.setBackground(new java.awt.Color(255, 255, 255));
        day1Label.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        day1Label.setForeground(new java.awt.Color(255, 255, 255));
        day1Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        day1Label.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        day1Label.setMaximumSize(new java.awt.Dimension(50, 50));
        day1Label.setPreferredSize(new java.awt.Dimension(30, 30));
        getContentPane().add(day1Label);
        day1Label.setBounds(215, 380, 30, 30);

        day2Label.setBackground(new java.awt.Color(255, 255, 255));
        day2Label.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        day2Label.setForeground(new java.awt.Color(255, 255, 255));
        day2Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        day2Label.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        day2Label.setMaximumSize(new java.awt.Dimension(50, 50));
        day2Label.setPreferredSize(new java.awt.Dimension(30, 30));
        getContentPane().add(day2Label);
        day2Label.setBounds(235, 380, 30, 30);

        mounth1Label.setBackground(new java.awt.Color(255, 255, 255));
        mounth1Label.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        mounth1Label.setForeground(new java.awt.Color(255, 255, 255));
        mounth1Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        mounth1Label.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        mounth1Label.setMaximumSize(new java.awt.Dimension(50, 50));
        mounth1Label.setPreferredSize(new java.awt.Dimension(30, 30));
        getContentPane().add(mounth1Label);
        mounth1Label.setBounds(265, 380, 30, 30);

        mounth2Label.setBackground(new java.awt.Color(255, 255, 255));
        mounth2Label.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        mounth2Label.setForeground(new java.awt.Color(255, 255, 255));
        mounth2Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        mounth2Label.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        mounth2Label.setMaximumSize(new java.awt.Dimension(50, 50));
        mounth2Label.setPreferredSize(new java.awt.Dimension(30, 30));
        getContentPane().add(mounth2Label);
        mounth2Label.setBounds(285, 380, 30, 30);

        year1Label.setBackground(new java.awt.Color(255, 255, 255));
        year1Label.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        year1Label.setForeground(new java.awt.Color(255, 255, 255));
        year1Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        year1Label.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        year1Label.setMaximumSize(new java.awt.Dimension(50, 50));
        year1Label.setPreferredSize(new java.awt.Dimension(30, 30));
        getContentPane().add(year1Label);
        year1Label.setBounds(315, 380, 30, 30);

        year2Label.setBackground(new java.awt.Color(255, 255, 255));
        year2Label.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        year2Label.setForeground(new java.awt.Color(255, 255, 255));
        year2Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        year2Label.setFocusable(false);
        year2Label.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        year2Label.setMaximumSize(new java.awt.Dimension(50, 50));
        year2Label.setPreferredSize(new java.awt.Dimension(30, 30));
        getContentPane().add(year2Label);
        year2Label.setBounds(335, 380, 30, 30);

        year3Label.setBackground(new java.awt.Color(255, 255, 255));
        year3Label.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        year3Label.setForeground(new java.awt.Color(255, 255, 255));
        year3Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        year3Label.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        year3Label.setMaximumSize(new java.awt.Dimension(50, 50));
        year3Label.setPreferredSize(new java.awt.Dimension(30, 30));
        getContentPane().add(year3Label);
        year3Label.setBounds(355, 380, 30, 30);

        year4Label.setBackground(new java.awt.Color(255, 255, 255));
        year4Label.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        year4Label.setForeground(new java.awt.Color(255, 255, 255));
        year4Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        year4Label.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        year4Label.setMaximumSize(new java.awt.Dimension(50, 50));
        year4Label.setPreferredSize(new java.awt.Dimension(30, 30));
        getContentPane().add(year4Label);
        year4Label.setBounds(375, 380, 30, 30);

        mounthSpot.setEditable(false);
        mounthSpot.setBackground(new java.awt.Color(255, 255, 255));
        mounthSpot.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 24)); // NOI18N
        mounthSpot.setForeground(new java.awt.Color(255, 255, 255));
        mounthSpot.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        mounthSpot.setText("January");
        mounthSpot.setToolTipText("");
        mounthSpot.setBorder(null);
        mounthSpot.setCaretColor(new java.awt.Color(255, 255, 255));
        mounthSpot.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        mounthSpot.setMaximumSize(new java.awt.Dimension(120, 40));
        mounthSpot.setMinimumSize(new java.awt.Dimension(0, 0));
        mounthSpot.setOpaque(false);
        mounthSpot.setPreferredSize(new java.awt.Dimension(120, 40));
        mounthSpot.setSelectionColor(new java.awt.Color(255, 255, 255));
        getContentPane().add(mounthSpot);
        mounthSpot.setBounds(240, 380, 120, 40);

        mounthShadow.setBackground(new java.awt.Color(255, 255, 255));
        mounthShadow.setForeground(new java.awt.Color(255, 255, 255));
        mounthShadow.setMaximumSize(new java.awt.Dimension(120, 40));
        mounthShadow.setOpaque(true);
        mounthShadow.setPreferredSize(new java.awt.Dimension(120, 40));
        getContentPane().add(mounthShadow);
        mounthShadow.setBounds(240, 380, 120, 40);

        day1Button.setBackground(new java.awt.Color(255, 255, 255));
        day1Button.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 24)); // NOI18N
        day1Button.setForeground(new java.awt.Color(255, 255, 255));
        day1Button.setText("1");
        day1Button.setBorder(null);
        day1Button.setContentAreaFilled(false);
        day1Button.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        day1Button.setMaximumSize(new java.awt.Dimension(120, 70));
        day1Button.setMinimumSize(new java.awt.Dimension(0, 0));
        day1Button.setPreferredSize(new java.awt.Dimension(120, 70));
        getContentPane().add(day1Button);
        day1Button.setBounds(180, 430, 30, 30);

        day2Button.setBackground(new java.awt.Color(255, 255, 255));
        day2Button.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 24)); // NOI18N
        day2Button.setForeground(new java.awt.Color(255, 255, 255));
        day2Button.setText("2");
        day2Button.setBorder(null);
        day2Button.setContentAreaFilled(false);
        day2Button.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        day2Button.setMaximumSize(new java.awt.Dimension(120, 70));
        day2Button.setMinimumSize(new java.awt.Dimension(0, 0));
        day2Button.setPreferredSize(new java.awt.Dimension(120, 70));
        getContentPane().add(day2Button);
        day2Button.setBounds(215, 430, 30, 30);

        day3Button.setBackground(new java.awt.Color(255, 255, 255));
        day3Button.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 24)); // NOI18N
        day3Button.setForeground(new java.awt.Color(255, 255, 255));
        day3Button.setText("3");
        day3Button.setBorder(null);
        day3Button.setContentAreaFilled(false);
        day3Button.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        day3Button.setMaximumSize(new java.awt.Dimension(120, 70));
        day3Button.setMinimumSize(new java.awt.Dimension(0, 0));
        day3Button.setPreferredSize(new java.awt.Dimension(120, 70));
        getContentPane().add(day3Button);
        day3Button.setBounds(250, 430, 30, 30);

        day4Button.setBackground(new java.awt.Color(255, 255, 255));
        day4Button.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 24)); // NOI18N
        day4Button.setForeground(new java.awt.Color(255, 255, 255));
        day4Button.setText("4");
        day4Button.setBorder(null);
        day4Button.setContentAreaFilled(false);
        day4Button.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        day4Button.setMaximumSize(new java.awt.Dimension(120, 70));
        day4Button.setMinimumSize(new java.awt.Dimension(0, 0));
        day4Button.setPreferredSize(new java.awt.Dimension(120, 70));
        getContentPane().add(day4Button);
        day4Button.setBounds(285, 430, 30, 30);

        day5Button.setBackground(new java.awt.Color(255, 255, 255));
        day5Button.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 24)); // NOI18N
        day5Button.setForeground(new java.awt.Color(255, 255, 255));
        day5Button.setText("5");
        day5Button.setBorder(null);
        day5Button.setContentAreaFilled(false);
        day5Button.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        day5Button.setMaximumSize(new java.awt.Dimension(120, 70));
        day5Button.setMinimumSize(new java.awt.Dimension(0, 0));
        day5Button.setPreferredSize(new java.awt.Dimension(120, 70));
        getContentPane().add(day5Button);
        day5Button.setBounds(320, 430, 30, 30);

        day6Button.setBackground(new java.awt.Color(255, 255, 255));
        day6Button.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 24)); // NOI18N
        day6Button.setForeground(new java.awt.Color(255, 255, 255));
        day6Button.setText("6");
        day6Button.setBorder(null);
        day6Button.setContentAreaFilled(false);
        day6Button.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        day6Button.setMaximumSize(new java.awt.Dimension(120, 70));
        day6Button.setMinimumSize(new java.awt.Dimension(0, 0));
        day6Button.setPreferredSize(new java.awt.Dimension(120, 70));
        getContentPane().add(day6Button);
        day6Button.setBounds(355, 430, 30, 30);

        day7Button.setBackground(new java.awt.Color(255, 255, 255));
        day7Button.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 24)); // NOI18N
        day7Button.setForeground(new java.awt.Color(255, 255, 255));
        day7Button.setText("7");
        day7Button.setBorder(null);
        day7Button.setContentAreaFilled(false);
        day7Button.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        day7Button.setMaximumSize(new java.awt.Dimension(120, 70));
        day7Button.setMinimumSize(new java.awt.Dimension(0, 0));
        day7Button.setPreferredSize(new java.awt.Dimension(120, 70));
        getContentPane().add(day7Button);
        day7Button.setBounds(390, 430, 30, 30);

        day8Button.setBackground(new java.awt.Color(255, 255, 255));
        day8Button.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 24)); // NOI18N
        day8Button.setForeground(new java.awt.Color(255, 255, 255));
        day8Button.setText("8");
        day8Button.setBorder(null);
        day8Button.setContentAreaFilled(false);
        day8Button.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        day8Button.setMaximumSize(new java.awt.Dimension(120, 70));
        day8Button.setMinimumSize(new java.awt.Dimension(0, 0));
        day8Button.setPreferredSize(new java.awt.Dimension(120, 70));
        getContentPane().add(day8Button);
        day8Button.setBounds(180, 470, 30, 30);

        day9Button.setBackground(new java.awt.Color(255, 255, 255));
        day9Button.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 24)); // NOI18N
        day9Button.setForeground(new java.awt.Color(255, 255, 255));
        day9Button.setText("9");
        day9Button.setBorder(null);
        day9Button.setContentAreaFilled(false);
        day9Button.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        day9Button.setMaximumSize(new java.awt.Dimension(120, 70));
        day9Button.setMinimumSize(new java.awt.Dimension(0, 0));
        day9Button.setPreferredSize(new java.awt.Dimension(120, 70));
        getContentPane().add(day9Button);
        day9Button.setBounds(215, 470, 30, 30);

        day10Button.setBackground(new java.awt.Color(255, 255, 255));
        day10Button.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 24)); // NOI18N
        day10Button.setForeground(new java.awt.Color(255, 255, 255));
        day10Button.setText("10");
        day10Button.setBorder(null);
        day10Button.setContentAreaFilled(false);
        day10Button.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        day10Button.setMaximumSize(new java.awt.Dimension(120, 70));
        day10Button.setMinimumSize(new java.awt.Dimension(0, 0));
        day10Button.setPreferredSize(new java.awt.Dimension(120, 70));
        getContentPane().add(day10Button);
        day10Button.setBounds(250, 470, 30, 30);

        day11Button.setBackground(new java.awt.Color(255, 255, 255));
        day11Button.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 24)); // NOI18N
        day11Button.setForeground(new java.awt.Color(255, 255, 255));
        day11Button.setText("11");
        day11Button.setBorder(null);
        day11Button.setContentAreaFilled(false);
        day11Button.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        day11Button.setMaximumSize(new java.awt.Dimension(120, 70));
        day11Button.setMinimumSize(new java.awt.Dimension(0, 0));
        day11Button.setPreferredSize(new java.awt.Dimension(120, 70));
        getContentPane().add(day11Button);
        day11Button.setBounds(285, 470, 30, 30);

        day12Button.setBackground(new java.awt.Color(255, 255, 255));
        day12Button.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 24)); // NOI18N
        day12Button.setForeground(new java.awt.Color(255, 255, 255));
        day12Button.setText("12");
        day12Button.setBorder(null);
        day12Button.setContentAreaFilled(false);
        day12Button.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        day12Button.setMaximumSize(new java.awt.Dimension(120, 70));
        day12Button.setMinimumSize(new java.awt.Dimension(0, 0));
        day12Button.setPreferredSize(new java.awt.Dimension(120, 70));
        getContentPane().add(day12Button);
        day12Button.setBounds(320, 470, 30, 30);

        day13Button.setBackground(new java.awt.Color(255, 255, 255));
        day13Button.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 24)); // NOI18N
        day13Button.setForeground(new java.awt.Color(255, 255, 255));
        day13Button.setText("13");
        day13Button.setBorder(null);
        day13Button.setContentAreaFilled(false);
        day13Button.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        day13Button.setMaximumSize(new java.awt.Dimension(120, 70));
        day13Button.setMinimumSize(new java.awt.Dimension(0, 0));
        day13Button.setPreferredSize(new java.awt.Dimension(120, 70));
        getContentPane().add(day13Button);
        day13Button.setBounds(355, 470, 30, 30);

        day14Button.setBackground(new java.awt.Color(255, 255, 255));
        day14Button.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 24)); // NOI18N
        day14Button.setForeground(new java.awt.Color(255, 255, 255));
        day14Button.setText("14");
        day14Button.setBorder(null);
        day14Button.setContentAreaFilled(false);
        day14Button.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        day14Button.setMaximumSize(new java.awt.Dimension(120, 70));
        day14Button.setMinimumSize(new java.awt.Dimension(0, 0));
        day14Button.setPreferredSize(new java.awt.Dimension(120, 70));
        getContentPane().add(day14Button);
        day14Button.setBounds(390, 470, 30, 30);

        day15Button.setBackground(new java.awt.Color(255, 255, 255));
        day15Button.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 24)); // NOI18N
        day15Button.setForeground(new java.awt.Color(255, 255, 255));
        day15Button.setText("15");
        day15Button.setBorder(null);
        day15Button.setContentAreaFilled(false);
        day15Button.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        day15Button.setMaximumSize(new java.awt.Dimension(120, 70));
        day15Button.setMinimumSize(new java.awt.Dimension(0, 0));
        day15Button.setPreferredSize(new java.awt.Dimension(120, 70));
        getContentPane().add(day15Button);
        day15Button.setBounds(180, 510, 30, 30);

        day16Button.setBackground(new java.awt.Color(255, 255, 255));
        day16Button.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 24)); // NOI18N
        day16Button.setForeground(new java.awt.Color(255, 255, 255));
        day16Button.setText("16");
        day16Button.setBorder(null);
        day16Button.setContentAreaFilled(false);
        day16Button.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        day16Button.setMaximumSize(new java.awt.Dimension(120, 70));
        day16Button.setMinimumSize(new java.awt.Dimension(0, 0));
        day16Button.setPreferredSize(new java.awt.Dimension(120, 70));
        getContentPane().add(day16Button);
        day16Button.setBounds(215, 510, 30, 30);

        day17Button.setBackground(new java.awt.Color(255, 255, 255));
        day17Button.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 24)); // NOI18N
        day17Button.setForeground(new java.awt.Color(255, 255, 255));
        day17Button.setText("17");
        day17Button.setBorder(null);
        day17Button.setContentAreaFilled(false);
        day17Button.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        day17Button.setMaximumSize(new java.awt.Dimension(120, 70));
        day17Button.setMinimumSize(new java.awt.Dimension(0, 0));
        day17Button.setPreferredSize(new java.awt.Dimension(120, 70));
        getContentPane().add(day17Button);
        day17Button.setBounds(250, 510, 30, 30);

        day18Button.setBackground(new java.awt.Color(255, 255, 255));
        day18Button.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 24)); // NOI18N
        day18Button.setForeground(new java.awt.Color(255, 255, 255));
        day18Button.setText("18");
        day18Button.setBorder(null);
        day18Button.setContentAreaFilled(false);
        day18Button.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        day18Button.setMaximumSize(new java.awt.Dimension(120, 70));
        day18Button.setMinimumSize(new java.awt.Dimension(0, 0));
        day18Button.setPreferredSize(new java.awt.Dimension(120, 70));
        getContentPane().add(day18Button);
        day18Button.setBounds(285, 510, 30, 30);

        day19Button.setBackground(new java.awt.Color(255, 255, 255));
        day19Button.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 24)); // NOI18N
        day19Button.setForeground(new java.awt.Color(255, 255, 255));
        day19Button.setText("19");
        day19Button.setBorder(null);
        day19Button.setContentAreaFilled(false);
        day19Button.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        day19Button.setMaximumSize(new java.awt.Dimension(120, 70));
        day19Button.setMinimumSize(new java.awt.Dimension(0, 0));
        day19Button.setPreferredSize(new java.awt.Dimension(120, 70));
        getContentPane().add(day19Button);
        day19Button.setBounds(320, 510, 30, 30);

        day20Button.setBackground(new java.awt.Color(255, 255, 255));
        day20Button.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 24)); // NOI18N
        day20Button.setForeground(new java.awt.Color(255, 255, 255));
        day20Button.setText("20");
        day20Button.setBorder(null);
        day20Button.setContentAreaFilled(false);
        day20Button.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        day20Button.setMaximumSize(new java.awt.Dimension(120, 70));
        day20Button.setMinimumSize(new java.awt.Dimension(0, 0));
        day20Button.setPreferredSize(new java.awt.Dimension(120, 70));
        getContentPane().add(day20Button);
        day20Button.setBounds(355, 510, 30, 30);

        day21Button.setBackground(new java.awt.Color(255, 255, 255));
        day21Button.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 24)); // NOI18N
        day21Button.setForeground(new java.awt.Color(255, 255, 255));
        day21Button.setText("21");
        day21Button.setBorder(null);
        day21Button.setContentAreaFilled(false);
        day21Button.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        day21Button.setMaximumSize(new java.awt.Dimension(120, 70));
        day21Button.setMinimumSize(new java.awt.Dimension(0, 0));
        day21Button.setPreferredSize(new java.awt.Dimension(120, 70));
        getContentPane().add(day21Button);
        day21Button.setBounds(390, 510, 30, 30);

        day22Button.setBackground(new java.awt.Color(255, 255, 255));
        day22Button.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 24)); // NOI18N
        day22Button.setForeground(new java.awt.Color(255, 255, 255));
        day22Button.setText("22");
        day22Button.setBorder(null);
        day22Button.setContentAreaFilled(false);
        day22Button.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        day22Button.setMaximumSize(new java.awt.Dimension(120, 70));
        day22Button.setMinimumSize(new java.awt.Dimension(0, 0));
        day22Button.setPreferredSize(new java.awt.Dimension(120, 70));
        getContentPane().add(day22Button);
        day22Button.setBounds(180, 550, 30, 30);

        day23Button.setBackground(new java.awt.Color(255, 255, 255));
        day23Button.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 24)); // NOI18N
        day23Button.setForeground(new java.awt.Color(255, 255, 255));
        day23Button.setText("23");
        day23Button.setBorder(null);
        day23Button.setContentAreaFilled(false);
        day23Button.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        day23Button.setMaximumSize(new java.awt.Dimension(120, 70));
        day23Button.setMinimumSize(new java.awt.Dimension(0, 0));
        day23Button.setPreferredSize(new java.awt.Dimension(120, 70));
        getContentPane().add(day23Button);
        day23Button.setBounds(215, 550, 30, 30);

        day24Button.setBackground(new java.awt.Color(255, 255, 255));
        day24Button.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 24)); // NOI18N
        day24Button.setForeground(new java.awt.Color(255, 255, 255));
        day24Button.setText("24");
        day24Button.setBorder(null);
        day24Button.setContentAreaFilled(false);
        day24Button.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        day24Button.setMaximumSize(new java.awt.Dimension(120, 70));
        day24Button.setMinimumSize(new java.awt.Dimension(0, 0));
        day24Button.setPreferredSize(new java.awt.Dimension(120, 70));
        getContentPane().add(day24Button);
        day24Button.setBounds(250, 550, 30, 30);

        day25Button.setBackground(new java.awt.Color(255, 255, 255));
        day25Button.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 24)); // NOI18N
        day25Button.setForeground(new java.awt.Color(255, 255, 255));
        day25Button.setText("25");
        day25Button.setBorder(null);
        day25Button.setContentAreaFilled(false);
        day25Button.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        day25Button.setMaximumSize(new java.awt.Dimension(120, 70));
        day25Button.setMinimumSize(new java.awt.Dimension(0, 0));
        day25Button.setPreferredSize(new java.awt.Dimension(120, 70));
        getContentPane().add(day25Button);
        day25Button.setBounds(285, 550, 30, 30);

        day26Button.setBackground(new java.awt.Color(255, 255, 255));
        day26Button.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 24)); // NOI18N
        day26Button.setForeground(new java.awt.Color(255, 255, 255));
        day26Button.setText("26");
        day26Button.setBorder(null);
        day26Button.setContentAreaFilled(false);
        day26Button.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        day26Button.setMaximumSize(new java.awt.Dimension(120, 70));
        day26Button.setMinimumSize(new java.awt.Dimension(0, 0));
        day26Button.setPreferredSize(new java.awt.Dimension(120, 70));
        getContentPane().add(day26Button);
        day26Button.setBounds(320, 550, 30, 30);

        day27Button.setBackground(new java.awt.Color(255, 255, 255));
        day27Button.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 24)); // NOI18N
        day27Button.setForeground(new java.awt.Color(255, 255, 255));
        day27Button.setText("27");
        day27Button.setBorder(null);
        day27Button.setContentAreaFilled(false);
        day27Button.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        day27Button.setMaximumSize(new java.awt.Dimension(120, 70));
        day27Button.setMinimumSize(new java.awt.Dimension(0, 0));
        day27Button.setPreferredSize(new java.awt.Dimension(120, 70));
        getContentPane().add(day27Button);
        day27Button.setBounds(355, 550, 30, 30);

        day28Button.setBackground(new java.awt.Color(255, 255, 255));
        day28Button.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 24)); // NOI18N
        day28Button.setForeground(new java.awt.Color(255, 255, 255));
        day28Button.setText("28");
        day28Button.setBorder(null);
        day28Button.setContentAreaFilled(false);
        day28Button.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        day28Button.setMaximumSize(new java.awt.Dimension(120, 70));
        day28Button.setMinimumSize(new java.awt.Dimension(0, 0));
        day28Button.setPreferredSize(new java.awt.Dimension(120, 70));
        getContentPane().add(day28Button);
        day28Button.setBounds(390, 550, 30, 30);

        day29Button.setBackground(new java.awt.Color(255, 255, 255));
        day29Button.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 24)); // NOI18N
        day29Button.setForeground(new java.awt.Color(255, 255, 255));
        day29Button.setText("29");
        day29Button.setBorder(null);
        day29Button.setContentAreaFilled(false);
        day29Button.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        day29Button.setMaximumSize(new java.awt.Dimension(120, 70));
        day29Button.setMinimumSize(new java.awt.Dimension(0, 0));
        day29Button.setPreferredSize(new java.awt.Dimension(120, 70));
        getContentPane().add(day29Button);
        day29Button.setBounds(180, 590, 30, 30);

        day30Button.setBackground(new java.awt.Color(255, 255, 255));
        day30Button.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 24)); // NOI18N
        day30Button.setForeground(new java.awt.Color(255, 255, 255));
        day30Button.setText("30");
        day30Button.setBorder(null);
        day30Button.setContentAreaFilled(false);
        day30Button.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        day30Button.setMaximumSize(new java.awt.Dimension(120, 70));
        day30Button.setMinimumSize(new java.awt.Dimension(0, 0));
        day30Button.setPreferredSize(new java.awt.Dimension(120, 70));
        getContentPane().add(day30Button);
        day30Button.setBounds(215, 590, 30, 30);

        day31Button.setBackground(new java.awt.Color(255, 255, 255));
        day31Button.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 24)); // NOI18N
        day31Button.setForeground(new java.awt.Color(255, 255, 255));
        day31Button.setText("31");
        day31Button.setBorder(null);
        day31Button.setContentAreaFilled(false);
        day31Button.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        day31Button.setMaximumSize(new java.awt.Dimension(120, 70));
        day31Button.setMinimumSize(new java.awt.Dimension(0, 0));
        day31Button.setPreferredSize(new java.awt.Dimension(120, 70));
        getContentPane().add(day31Button);
        day31Button.setBounds(250, 590, 30, 30);

        yearField.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 24)); // NOI18N
        yearField.setForeground(new java.awt.Color(255, 255, 255));
        yearField.setText("Year");
        yearField.setToolTipText("");
        yearField.setBorder(null);
        yearField.setCaretColor(new java.awt.Color(255, 255, 255));
        yearField.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        yearField.setMaximumSize(new java.awt.Dimension(80, 40));
        yearField.setMinimumSize(new java.awt.Dimension(0, 0));
        yearField.setOpaque(false);
        yearField.setPreferredSize(new java.awt.Dimension(80, 40));
        yearField.setSelectionColor(new java.awt.Color(255, 255, 255));
        getContentPane().add(yearField);
        yearField.setBounds(180, 620, 120, 40);

        yearShadow.setBackground(new java.awt.Color(255, 255, 255));
        yearShadow.setForeground(new java.awt.Color(255, 255, 255));
        yearShadow.setMaximumSize(new java.awt.Dimension(350, 40));
        yearShadow.setOpaque(true);
        yearShadow.setPreferredSize(new java.awt.Dimension(350, 40));
        getContentPane().add(yearShadow);
        yearShadow.setBounds(180, 620, 120, 40);

        okButton.setBackground(new java.awt.Color(255, 255, 255));
        okButton.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 24)); // NOI18N
        okButton.setForeground(new java.awt.Color(255, 255, 255));
        okButton.setBorder(null);
        okButton.setContentAreaFilled(false);
        okButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        okButton.setMaximumSize(new java.awt.Dimension(120, 70));
        okButton.setMinimumSize(new java.awt.Dimension(0, 0));
        okButton.setPreferredSize(new java.awt.Dimension(120, 70));
        getContentPane().add(okButton);
        okButton.setBounds(400, 640, 40, 40);

        dateShadow.setBackground(new java.awt.Color(255, 255, 255));
        dateShadow.setForeground(new java.awt.Color(255, 255, 255));
        dateShadow.setMaximumSize(new java.awt.Dimension(350, 40));
        dateShadow.setOpaque(true);
        dateShadow.setPreferredSize(new java.awt.Dimension(350, 40));
        getContentPane().add(dateShadow);
        dateShadow.setBounds(155, 380, 285, 300);

        areaSpot.setEditable(false);
        areaSpot.setBackground(new java.awt.Color(255, 255, 255));
        areaSpot.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 24)); // NOI18N
        areaSpot.setForeground(new java.awt.Color(255, 255, 255));
        areaSpot.setText("Area");
        areaSpot.setToolTipText("");
        areaSpot.setBorder(null);
        areaSpot.setCaretColor(new java.awt.Color(255, 255, 255));
        areaSpot.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        areaSpot.setMaximumSize(new java.awt.Dimension(70, 40));
        areaSpot.setMinimumSize(new java.awt.Dimension(0, 0));
        areaSpot.setOpaque(false);
        areaSpot.setPreferredSize(new java.awt.Dimension(70, 40));
        areaSpot.setSelectionColor(new java.awt.Color(255, 255, 255));
        getContentPane().add(areaSpot);
        areaSpot.setBounds(50, 650, 70, 40);

        area1Button.setBackground(new java.awt.Color(255, 255, 255));
        area1Button.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 24)); // NOI18N
        area1Button.setForeground(new java.awt.Color(255, 255, 255));
        area1Button.setToolTipText("");
        area1Button.setBorder(null);
        area1Button.setContentAreaFilled(false);
        area1Button.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        area1Button.setMaximumSize(new java.awt.Dimension(40, 40));
        area1Button.setMinimumSize(new java.awt.Dimension(0, 0));
        area1Button.setName("1"); // NOI18N
        area1Button.setPreferredSize(new java.awt.Dimension(40, 40));
        getContentPane().add(area1Button);
        area1Button.setBounds(110, 650, 40, 40);

        area2Button.setBackground(new java.awt.Color(255, 255, 255));
        area2Button.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 24)); // NOI18N
        area2Button.setForeground(new java.awt.Color(255, 255, 255));
        area2Button.setBorder(null);
        area2Button.setContentAreaFilled(false);
        area2Button.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        area2Button.setMaximumSize(new java.awt.Dimension(40, 40));
        area2Button.setMinimumSize(new java.awt.Dimension(0, 0));
        area2Button.setName("2"); // NOI18N
        area2Button.setPreferredSize(new java.awt.Dimension(40, 40));
        getContentPane().add(area2Button);
        area2Button.setBounds(155, 650, 40, 40);

        area3Button.setBackground(new java.awt.Color(255, 255, 255));
        area3Button.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 24)); // NOI18N
        area3Button.setForeground(new java.awt.Color(255, 255, 255));
        area3Button.setBorder(null);
        area3Button.setContentAreaFilled(false);
        area3Button.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        area3Button.setMaximumSize(new java.awt.Dimension(40, 40));
        area3Button.setMinimumSize(new java.awt.Dimension(0, 0));
        area3Button.setName("3"); // NOI18N
        area3Button.setPreferredSize(new java.awt.Dimension(40, 40));
        getContentPane().add(area3Button);
        area3Button.setBounds(200, 650, 40, 40);

        area4Button.setBackground(new java.awt.Color(255, 255, 255));
        area4Button.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 24)); // NOI18N
        area4Button.setForeground(new java.awt.Color(255, 255, 255));
        area4Button.setBorder(null);
        area4Button.setContentAreaFilled(false);
        area4Button.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        area4Button.setMaximumSize(new java.awt.Dimension(120, 70));
        area4Button.setMinimumSize(new java.awt.Dimension(0, 0));
        area4Button.setName("4"); // NOI18N
        area4Button.setPreferredSize(new java.awt.Dimension(120, 70));
        getContentPane().add(area4Button);
        area4Button.setBounds(245, 650, 40, 40);

        area5Button.setBackground(new java.awt.Color(255, 255, 255));
        area5Button.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 24)); // NOI18N
        area5Button.setForeground(new java.awt.Color(255, 255, 255));
        area5Button.setBorder(null);
        area5Button.setContentAreaFilled(false);
        area5Button.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        area5Button.setMaximumSize(new java.awt.Dimension(120, 70));
        area5Button.setMinimumSize(new java.awt.Dimension(0, 0));
        area5Button.setName("5"); // NOI18N
        area5Button.setPreferredSize(new java.awt.Dimension(120, 70));
        getContentPane().add(area5Button);
        area5Button.setBounds(290, 650, 40, 40);

        area6Button.setBackground(new java.awt.Color(255, 255, 255));
        area6Button.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 24)); // NOI18N
        area6Button.setForeground(new java.awt.Color(255, 255, 255));
        area6Button.setBorder(null);
        area6Button.setContentAreaFilled(false);
        area6Button.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        area6Button.setMaximumSize(new java.awt.Dimension(120, 70));
        area6Button.setMinimumSize(new java.awt.Dimension(0, 0));
        area6Button.setName("6"); // NOI18N
        area6Button.setPreferredSize(new java.awt.Dimension(120, 70));
        getContentPane().add(area6Button);
        area6Button.setBounds(335, 650, 40, 40);

        area7Button.setBackground(new java.awt.Color(255, 255, 255));
        area7Button.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 24)); // NOI18N
        area7Button.setForeground(new java.awt.Color(255, 255, 255));
        area7Button.setBorder(null);
        area7Button.setContentAreaFilled(false);
        area7Button.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        area7Button.setMaximumSize(new java.awt.Dimension(120, 70));
        area7Button.setMinimumSize(new java.awt.Dimension(0, 0));
        area7Button.setName("7"); // NOI18N
        area7Button.setPreferredSize(new java.awt.Dimension(120, 70));
        getContentPane().add(area7Button);
        area7Button.setBounds(380, 650, 40, 40);

        PINField.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 24)); // NOI18N
        PINField.setForeground(new java.awt.Color(255, 255, 255));
        PINField.setText("PIN");
        PINField.setToolTipText("");
        PINField.setBorder(null);
        PINField.setCaretColor(new java.awt.Color(255, 255, 255));
        PINField.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        PINField.setMaximumSize(new java.awt.Dimension(350, 40));
        PINField.setMinimumSize(new java.awt.Dimension(0, 0));
        PINField.setOpaque(false);
        PINField.setPreferredSize(new java.awt.Dimension(350, 40));
        PINField.setSelectionColor(new java.awt.Color(255, 255, 255));
        getContentPane().add(PINField);
        PINField.setBounds(50, 470, 350, 40);

        functionPane.setBackground(new java.awt.Color(255, 255, 255));
        functionPane.setBorder(null);
        functionPane.setForeground(new java.awt.Color(255, 255, 255));
        functionPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        functionPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        functionPane.setMaximumSize(new java.awt.Dimension(250, 80));
        functionPane.setMinimumSize(new java.awt.Dimension(0, 0));
        functionPane.setOpaque(false);
        functionPane.setPreferredSize(new java.awt.Dimension(250, 80));

        functionField.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 24)); // NOI18N
        functionField.setForeground(new java.awt.Color(255, 255, 255));
        functionField.setCaretColor(new java.awt.Color(255, 255, 255));
        functionField.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        functionField.setMaximumSize(new java.awt.Dimension(250, 80));
        functionField.setMinimumSize(new java.awt.Dimension(0, 0));
        functionField.setOpaque(false);
        functionField.setPreferredSize(new java.awt.Dimension(250, 80));
        functionField.setSelectionColor(new java.awt.Color(255, 255, 255));
        functionPane.setViewportView(functionField);

        getContentPane().add(functionPane);
        functionPane.setBounds(150, 515, 250, 80);

        salaryField.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 24)); // NOI18N
        salaryField.setForeground(new java.awt.Color(255, 255, 255));
        salaryField.setText("Salary");
        salaryField.setToolTipText("");
        salaryField.setBorder(null);
        salaryField.setCaretColor(new java.awt.Color(255, 255, 255));
        salaryField.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        salaryField.setMaximumSize(new java.awt.Dimension(350, 40));
        salaryField.setMinimumSize(new java.awt.Dimension(0, 0));
        salaryField.setOpaque(false);
        salaryField.setPreferredSize(new java.awt.Dimension(350, 40));
        salaryField.setSelectionColor(new java.awt.Color(255, 255, 255));
        getContentPane().add(salaryField);
        salaryField.setBounds(50, 605, 350, 40);

        functionShadow.setBackground(new java.awt.Color(255, 255, 255));
        functionShadow.setForeground(new java.awt.Color(255, 255, 255));
        functionShadow.setMaximumSize(new java.awt.Dimension(250, 80));
        functionShadow.setOpaque(true);
        functionShadow.setPreferredSize(new java.awt.Dimension(250, 80));
        getContentPane().add(functionShadow);
        functionShadow.setBounds(150, 515, 250, 80);

        salaryShadow.setBackground(new java.awt.Color(255, 255, 255));
        salaryShadow.setForeground(new java.awt.Color(255, 255, 255));
        salaryShadow.setMaximumSize(new java.awt.Dimension(350, 40));
        salaryShadow.setOpaque(true);
        salaryShadow.setPreferredSize(new java.awt.Dimension(350, 40));
        getContentPane().add(salaryShadow);
        salaryShadow.setBounds(50, 605, 350, 40);

        maleSexLabel.setBackground(new java.awt.Color(255, 255, 255));
        maleSexLabel.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        maleSexLabel.setForeground(new java.awt.Color(255, 255, 255));
        maleSexLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        maleSexLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        maleSexLabel.setMaximumSize(new java.awt.Dimension(50, 50));
        maleSexLabel.setPreferredSize(new java.awt.Dimension(50, 50));
        getContentPane().add(maleSexLabel);
        maleSexLabel.setBounds(100, 420, 50, 50);

        femaleSexLabel.setBackground(new java.awt.Color(255, 255, 255));
        femaleSexLabel.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        femaleSexLabel.setForeground(new java.awt.Color(255, 255, 255));
        femaleSexLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        femaleSexLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        femaleSexLabel.setMaximumSize(new java.awt.Dimension(50, 50));
        femaleSexLabel.setPreferredSize(new java.awt.Dimension(50, 50));
        getContentPane().add(femaleSexLabel);
        femaleSexLabel.setBounds(155, 420, 50, 50);

        PINShadow.setBackground(new java.awt.Color(255, 255, 255));
        PINShadow.setForeground(new java.awt.Color(255, 255, 255));
        PINShadow.setMaximumSize(new java.awt.Dimension(350, 40));
        PINShadow.setOpaque(true);
        PINShadow.setPreferredSize(new java.awt.Dimension(350, 40));
        getContentPane().add(PINShadow);
        PINShadow.setBounds(50, 470, 350, 40);

        showEmployees.setBackground(new java.awt.Color(255, 255, 255));
        showEmployees.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 24)); // NOI18N
        showEmployees.setForeground(new java.awt.Color(255, 255, 255));
        showEmployees.setToolTipText("");
        showEmployees.setBorder(null);
        showEmployees.setContentAreaFilled(false);
        showEmployees.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        showEmployees.setMaximumSize(new java.awt.Dimension(130, 80));
        showEmployees.setMinimumSize(new java.awt.Dimension(0, 0));
        showEmployees.setPreferredSize(new java.awt.Dimension(250, 250));
        getContentPane().add(showEmployees);
        showEmployees.setBounds(925, 250, 250, 250);

        nextButton.setBackground(new java.awt.Color(255, 255, 255));
        nextButton.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 36)); // NOI18N
        nextButton.setForeground(new java.awt.Color(255, 255, 255));
        nextButton.setBorder(null);
        nextButton.setBorderPainted(false);
        nextButton.setContentAreaFilled(false);
        nextButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        nextButton.setInheritsPopupMenu(true);
        nextButton.setMaximumSize(new java.awt.Dimension(130, 130));
        nextButton.setMinimumSize(new java.awt.Dimension(0, 0));
        nextButton.setPreferredSize(new java.awt.Dimension(130, 130));
        nextButton.setRequestFocusEnabled(false);
        getContentPane().add(nextButton);
        nextButton.setBounds(630, 200, 130, 130);

        prevButton.setBackground(new java.awt.Color(255, 255, 255));
        prevButton.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 36)); // NOI18N
        prevButton.setForeground(new java.awt.Color(255, 255, 255));
        prevButton.setBorder(null);
        prevButton.setBorderPainted(false);
        prevButton.setContentAreaFilled(false);
        prevButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        prevButton.setInheritsPopupMenu(true);
        prevButton.setMaximumSize(new java.awt.Dimension(130, 130));
        prevButton.setMinimumSize(new java.awt.Dimension(0, 0));
        prevButton.setPreferredSize(new java.awt.Dimension(130, 130));
        prevButton.setRequestFocusEnabled(false);
        getContentPane().add(prevButton);
        prevButton.setBounds(630, 350, 130, 130);

        allEmployeesPanel.setBackground(new java.awt.Color(255, 255, 255));
        allEmployeesPanel.setForeground(new java.awt.Color(255, 255, 255));
        allEmployeesPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        allEmployeesPanel.setOpaque(false);
        allEmployeesPanel.setPreferredSize(new java.awt.Dimension(570, 670));
        getContentPane().add(allEmployeesPanel);
        allEmployeesPanel.setBounds(760, 20, 570, 670);

        allEmployeesLabel.setBackground(new java.awt.Color(255, 255, 255));
        allEmployeesLabel.setForeground(new java.awt.Color(255, 255, 255));
        allEmployeesLabel.setMaximumSize(new java.awt.Dimension(570, 670));
        allEmployeesLabel.setOpaque(true);
        allEmployeesLabel.setPreferredSize(new java.awt.Dimension(570, 670));
        getContentPane().add(allEmployeesLabel);
        allEmployeesLabel.setBounds(760, 20, 570, 670);

        employeeViewLabel.setBackground(new java.awt.Color(255, 255, 255));
        employeeViewLabel.setForeground(new java.awt.Color(255, 255, 255));
        employeeViewLabel.setMaximumSize(new java.awt.Dimension(570, 670));
        employeeViewLabel.setOpaque(true);
        employeeViewLabel.setPreferredSize(new java.awt.Dimension(570, 670));
        getContentPane().add(employeeViewLabel);
        employeeViewLabel.setBounds(40, 20, 570, 670);

        text2Spot.setEditable(false);
        text2Spot.setBackground(new java.awt.Color(255, 255, 255));
        text2Spot.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 24)); // NOI18N
        text2Spot.setForeground(new java.awt.Color(255, 255, 255));
        text2Spot.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        text2Spot.setToolTipText("");
        text2Spot.setBorder(null);
        text2Spot.setCaretColor(new java.awt.Color(255, 255, 255));
        text2Spot.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        text2Spot.setMaximumSize(new java.awt.Dimension(110, 40));
        text2Spot.setMinimumSize(new java.awt.Dimension(0, 0));
        text2Spot.setOpaque(false);
        text2Spot.setPreferredSize(new java.awt.Dimension(110, 40));
        text2Spot.setSelectionColor(new java.awt.Color(255, 255, 255));
        getContentPane().add(text2Spot);
        text2Spot.setBounds(420, 540, 100, 40);

        text1Spot.setEditable(false);
        text1Spot.setBackground(new java.awt.Color(255, 255, 255));
        text1Spot.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 24)); // NOI18N
        text1Spot.setForeground(new java.awt.Color(255, 255, 255));
        text1Spot.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        text1Spot.setToolTipText("");
        text1Spot.setBorder(null);
        text1Spot.setCaretColor(new java.awt.Color(255, 255, 255));
        text1Spot.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        text1Spot.setMaximumSize(new java.awt.Dimension(110, 40));
        text1Spot.setMinimumSize(new java.awt.Dimension(0, 0));
        text1Spot.setOpaque(false);
        text1Spot.setPreferredSize(new java.awt.Dimension(110, 40));
        text1Spot.setSelectionColor(new java.awt.Color(255, 255, 255));
        getContentPane().add(text1Spot);
        text1Spot.setBounds(500, 540, 100, 40);

        backButton.setBackground(new java.awt.Color(255, 255, 255));
        backButton.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 28)); // NOI18N
        backButton.setForeground(new java.awt.Color(255, 255, 255));
        backButton.setText("Back");
        backButton.setBorder(null);
        backButton.setContentAreaFilled(false);
        backButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        backButton.setMaximumSize(new java.awt.Dimension(120, 50));
        backButton.setMinimumSize(new java.awt.Dimension(0, 0));
        backButton.setPreferredSize(new java.awt.Dimension(120, 50));
        getContentPane().add(backButton);
        backButton.setBounds(630, 690, 120, 50);

        background.setBackground(new java.awt.Color(255, 255, 255));
        background.setForeground(new java.awt.Color(255, 255, 255));
        background.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        background.setMaximumSize(new java.awt.Dimension(1400, 780));
        background.setPreferredSize(new java.awt.Dimension(1400, 780));
        getContentPane().add(background);
        background.setBounds(0, 0, 1400, 770);
        background.getAccessibleContext().setAccessibleName("background");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField PINField;
    private javax.swing.JLabel PINShadow;
    private javax.swing.JButton addButton;
    private javax.swing.JButton addPhotoButton;
    private javax.swing.JTextPane adressField;
    private javax.swing.JScrollPane adressPane;
    private javax.swing.JLabel adressShadow;
    private javax.swing.JTextField adressSpot;
    private javax.swing.JLabel allEmployeesLabel;
    private javax.swing.JPanel allEmployeesPanel;
    private javax.swing.JTextPane allertMessageField;
    private javax.swing.JLabel allertMessageLabel;
    private javax.swing.JScrollPane allertMessagePane;
    private javax.swing.JButton area1Button;
    private javax.swing.JButton area2Button;
    private javax.swing.JButton area3Button;
    private javax.swing.JButton area4Button;
    private javax.swing.JButton area5Button;
    private javax.swing.JButton area6Button;
    private javax.swing.JButton area7Button;
    private javax.swing.JTextField areaSpot;
    private javax.swing.JButton backButton;
    private javax.swing.JLabel background;
    private javax.swing.JTextField birthDateSpot;
    private javax.swing.JButton changeButton;
    private javax.swing.JTextField cityField;
    private javax.swing.JLabel cityShadow;
    private javax.swing.JTextField countryField;
    private javax.swing.JLabel countryShadow;
    private javax.swing.JLabel dateShadow;
    private javax.swing.JButton day10Button;
    private javax.swing.JButton day11Button;
    private javax.swing.JButton day12Button;
    private javax.swing.JButton day13Button;
    private javax.swing.JButton day14Button;
    private javax.swing.JButton day15Button;
    private javax.swing.JButton day16Button;
    private javax.swing.JButton day17Button;
    private javax.swing.JButton day18Button;
    private javax.swing.JButton day19Button;
    private javax.swing.JButton day1Button;
    private javax.swing.JLabel day1Label;
    private javax.swing.JButton day20Button;
    private javax.swing.JButton day21Button;
    private javax.swing.JButton day22Button;
    private javax.swing.JButton day23Button;
    private javax.swing.JButton day24Button;
    private javax.swing.JButton day25Button;
    private javax.swing.JButton day26Button;
    private javax.swing.JButton day27Button;
    private javax.swing.JButton day28Button;
    private javax.swing.JButton day29Button;
    private javax.swing.JButton day2Button;
    private javax.swing.JLabel day2Label;
    private javax.swing.JButton day30Button;
    private javax.swing.JButton day31Button;
    private javax.swing.JButton day3Button;
    private javax.swing.JButton day4Button;
    private javax.swing.JButton day5Button;
    private javax.swing.JButton day6Button;
    private javax.swing.JButton day7Button;
    private javax.swing.JButton day8Button;
    private javax.swing.JButton day9Button;
    private javax.swing.JLabel employeeViewLabel;
    private javax.swing.JLabel femaleSexLabel;
    private javax.swing.JTextPane functionField;
    private javax.swing.JScrollPane functionPane;
    private javax.swing.JLabel functionShadow;
    private javax.swing.JTextField functionSpot;
    private javax.swing.JButton goAddButton;
    private javax.swing.JLabel inactiveBackground;
    private javax.swing.JLabel maleSexLabel;
    private javax.swing.JLabel mounth1Label;
    private javax.swing.JLabel mounth2Label;
    private javax.swing.JLabel mounthShadow;
    private javax.swing.JTextField mounthSpot;
    private javax.swing.JTextField nameField;
    private javax.swing.JLabel nameShadow;
    private javax.swing.JButton nextButton;
    private javax.swing.JButton nextMounthButton;
    private javax.swing.JButton okButton;
    private javax.swing.JTextField phoneNumberField;
    private javax.swing.JLabel phoneNumberShadow;
    private javax.swing.JLabel photoLabel;
    private javax.swing.JButton pickButton;
    private javax.swing.JButton prevButton;
    private javax.swing.JButton prevMounthButton;
    private javax.swing.JTextField regionField;
    private javax.swing.JLabel regionShadow;
    private javax.swing.JButton retryButton;
    private javax.swing.JTextField salaryField;
    private javax.swing.JLabel salaryShadow;
    private javax.swing.JButton setButton;
    private javax.swing.JTextField sexSpot;
    private javax.swing.JButton showEmployees;
    private javax.swing.JTextField text1Spot;
    private javax.swing.JTextField text2Spot;
    private javax.swing.JTextField text3Spot;
    private javax.swing.JButton updateButton;
    private javax.swing.JLabel year1Label;
    private javax.swing.JLabel year2Label;
    private javax.swing.JLabel year3Label;
    private javax.swing.JLabel year4Label;
    private javax.swing.JTextField yearField;
    private javax.swing.JLabel yearShadow;
    // End of variables declaration//GEN-END:variables
}
