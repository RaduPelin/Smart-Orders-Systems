/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import db.Resource;
import db.Restaurant;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import service.MainServiceMySQL;
/**
 *
 * @author Radu
 */
public class EditFrame extends javax.swing.JFrame {

    private final List<Resource> resources = MainServiceMySQL.getInstance().getResources();
    private final List<Restaurant> restaurants = MainServiceMySQL.getInstance().getRestaurants();
    private int index;
    private int restaurantID;
    
    /**
    * Se creaza pagina Edit
    */
    public EditFrame() {
        initComponents();
        
        meniuSettingsButton.setIcon(getIcon(350, 350,resources.get(75).getResource()));
        meniuSettingsButton.setPressedIcon(getIcon(350, 350,resources.get(79).getResource()));
        meniuSettingsButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                showEditInfo(meniuSettingsButton);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                editInfo.setText("");
            }
        });
        employeesSettingsButton.setIcon(getIcon(450, 450,resources.get(76).getResource()));
        employeesSettingsButton.setPressedIcon(getIcon(450, 450,resources.get(79).getResource()));
        employeesSettingsButton.addActionListener(e->goToEmployees());
        employeesSettingsButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                showEditInfo(employeesSettingsButton);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                editInfo.setText("");
            }
        });
        tablesSettingsButton.setIcon(getIcon(350, 350,resources.get(77).getResource()));
        tablesSettingsButton.setPressedIcon(getIcon(350, 350,resources.get(79).getResource()));
        tablesSettingsButton.addActionListener(e->goToTables());
        tablesSettingsButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                showEditInfo(tablesSettingsButton);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                editInfo.setText("");
            }
        });
        accountSettingsButton.setIcon(getIcon(280, 280,resources.get(78).getResource()));
        accountSettingsButton.setPressedIcon(getIcon(280, 280,resources.get(79).getResource()));
        accountSettingsButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                showEditInfo(accountSettingsButton);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                editInfo.setText("");
            }
        });
        backButton.setIcon(getIcon(120,50,resources.get(4).getResource()));
        backButton.setPressedIcon(getIcon(120,50,resources.get(5).getResource()));
        backButton.addActionListener(e->back());
    }
     
    //Metoda de trecere la pagina angajatilor.
    private void goToEmployees() { 
        EmployeesFrame employees = new EmployeesFrame();
        
        employees.setLocationRelativeTo(null);
        employees.setVisible(true);
        employees.settingBackground(background.getIcon(), index, restaurantID);
        this.setVisible(false);
    }
    
    //Metoda de trecere la pagina meselor si zonelor.
    private void goToTables(){ 
        TablesFrame tables = new TablesFrame();
        
        tables.setLocationRelativeTo(null);
        tables.setVisible(true);
        tables.settingBackground(background.getIcon(), index, restaurantID);
        this.setVisible(false);
    }

    //Metoda de afisare a numelui fiecarei pagini, in partea de sus a paginii.
    public void showEditInfo(JButton button) {
        if (button.equals(meniuSettingsButton)) {
              editInfo.setText("Meniu");
        } else {
            if (button.equals(employeesSettingsButton)) {
                editInfo.setText("Employees");
            } else {
                if (button.equals(tablesSettingsButton)) {
                    editInfo.setText("Tables");
                } else {
                    editInfo.setText("Account");
                }
            }
        }     
    }
    
    //Metoda de revenire la pagina principala  administratorului.
    private void back() {
        AdminFrame admin = new AdminFrame();
        
        admin.setLocationRelativeTo(null);
        admin.setVisible(true);
        admin.settingBackground(background.getIcon(), index, restaurantID);
        
        byte[] logo = restaurants.stream()
            .filter(a -> a.getRestaurantID() == restaurantID)
            .map(r -> r.getLogo())
            .findFirst()
            .orElse(null);
         
        admin.setLogoField(getIcon(260,150,logo));
        
        this.setVisible(false);
    }
    
    //Metoda de setare a fundalului, apelata din pagina Admin.
    public void settingBackground(Icon resource, int index, int restaurantID){
        background.setIcon(resource);
        
        this.index = index;
        this.restaurantID = restaurantID;
    }
   
    //Metoda de setare si conversie binara a iconitelor preluate din baza de date, pentru toate componentele (butoane, cadre, fundaluri).
    private ImageIcon getIcon(int width, int height, byte[] image){
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
     * 
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        backButton = new javax.swing.JButton();
        meniuSettingsButton = new javax.swing.JButton();
        tablesSettingsButton = new javax.swing.JButton();
        employeesSettingsButton = new javax.swing.JButton();
        accountSettingsButton = new javax.swing.JButton();
        editInfo = new javax.swing.JTextField();
        background = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(1400, 780));
        setName("Edit"); // NOI18N
        setPreferredSize(new java.awt.Dimension(1400, 780));
        setSize(new java.awt.Dimension(1400, 780));
        getContentPane().setLayout(null);

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
        backButton.setBounds(1210, 670, 120, 50);

        meniuSettingsButton.setBackground(new java.awt.Color(255, 255, 255));
        meniuSettingsButton.setForeground(new java.awt.Color(255, 255, 255));
        meniuSettingsButton.setBorder(null);
        meniuSettingsButton.setContentAreaFilled(false);
        meniuSettingsButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        meniuSettingsButton.setMaximumSize(new java.awt.Dimension(350, 350));
        meniuSettingsButton.setMinimumSize(new java.awt.Dimension(0, 0));
        meniuSettingsButton.setPreferredSize(new java.awt.Dimension(350, 350));
        getContentPane().add(meniuSettingsButton);
        meniuSettingsButton.setBounds(30, 120, 350, 350);

        tablesSettingsButton.setBackground(new java.awt.Color(255, 255, 255));
        tablesSettingsButton.setForeground(new java.awt.Color(255, 255, 255));
        tablesSettingsButton.setBorder(null);
        tablesSettingsButton.setContentAreaFilled(false);
        tablesSettingsButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        tablesSettingsButton.setMaximumSize(new java.awt.Dimension(350, 350));
        tablesSettingsButton.setMinimumSize(new java.awt.Dimension(0, 0));
        tablesSettingsButton.setPreferredSize(new java.awt.Dimension(350, 350));
        getContentPane().add(tablesSettingsButton);
        tablesSettingsButton.setBounds(980, 120, 350, 350);

        employeesSettingsButton.setBackground(new java.awt.Color(255, 255, 255));
        employeesSettingsButton.setForeground(new java.awt.Color(255, 255, 255));
        employeesSettingsButton.setBorder(null);
        employeesSettingsButton.setContentAreaFilled(false);
        employeesSettingsButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        employeesSettingsButton.setMaximumSize(new java.awt.Dimension(450, 450));
        employeesSettingsButton.setMinimumSize(new java.awt.Dimension(0, 0));
        employeesSettingsButton.setPreferredSize(new java.awt.Dimension(450, 450));
        getContentPane().add(employeesSettingsButton);
        employeesSettingsButton.setBounds(450, 80, 450, 450);

        accountSettingsButton.setBackground(new java.awt.Color(255, 255, 255));
        accountSettingsButton.setForeground(new java.awt.Color(255, 255, 255));
        accountSettingsButton.setBorder(null);
        accountSettingsButton.setContentAreaFilled(false);
        accountSettingsButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        accountSettingsButton.setMaximumSize(new java.awt.Dimension(280, 280));
        accountSettingsButton.setMinimumSize(new java.awt.Dimension(0, 0));
        accountSettingsButton.setPreferredSize(new java.awt.Dimension(280, 280));
        getContentPane().add(accountSettingsButton);
        accountSettingsButton.setBounds(530, 480, 280, 280);

        editInfo.setEditable(false);
        editInfo.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 60)); // NOI18N
        editInfo.setForeground(new java.awt.Color(255, 255, 255));
        editInfo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        editInfo.setBorder(null);
        editInfo.setCaretColor(new java.awt.Color(255, 255, 255));
        editInfo.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        editInfo.setMaximumSize(new java.awt.Dimension(400, 100));
        editInfo.setMinimumSize(new java.awt.Dimension(0, 0));
        editInfo.setOpaque(false);
        editInfo.setPreferredSize(new java.awt.Dimension(400, 100));
        editInfo.setSelectionColor(new java.awt.Color(255, 255, 255));
        getContentPane().add(editInfo);
        editInfo.setBounds(470, 20, 400, 100);

        background.setBackground(new java.awt.Color(255, 255, 255));
        background.setForeground(new java.awt.Color(255, 255, 255));
        background.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        background.setMaximumSize(new java.awt.Dimension(1400, 780));
        background.setPreferredSize(new java.awt.Dimension(1400, 780));
        getContentPane().add(background);
        background.setBounds(0, 0, 1400, 780);
        background.getAccessibleContext().setAccessibleName("background");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton accountSettingsButton;
    private javax.swing.JButton backButton;
    private javax.swing.JLabel background;
    private javax.swing.JTextField editInfo;
    private javax.swing.JButton employeesSettingsButton;
    private javax.swing.JButton meniuSettingsButton;
    private javax.swing.JButton tablesSettingsButton;
    // End of variables declaration//GEN-END:variables
}
