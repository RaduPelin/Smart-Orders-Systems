/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import db.Area;
import db.Order;
import db.Resource;
import db.Restaurant;
import db.Table;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import service.MainServiceMySQL;
import service.RotatedIcon;
/**
 *
 * @author Radu
 */
public class AdminFrame extends javax.swing.JFrame {

     private final List<Resource> resources = MainServiceMySQL.getInstance().getResources();
     private final List<Restaurant> restaurants = MainServiceMySQL.getInstance().getRestaurants();
     private List<Area> areas;
     private List<Table> tables;
     private int index;
     private int start = 0;
     private int restaurantID;
    /**
     * Se creaza pagina Admin
     */
    public AdminFrame() {
        initComponents();
        
        inactiveBackground.setBackground(new Color(0,0,0,200));
        inactiveBackground.setVisible(false);
        allertMessageLabel.setIcon(getIcon(430, 240, resources.get(14).getResource()));
        allertMessageLabel.setVisible(false);
        allertMessagePane.setVisible(false);
        allertMessagePane.getViewport().setOpaque(false);
        welcomeLogo.setIcon(new ImageIcon(resources.get(135).getResource()));
        logoLabel.setVisible(false);
        titleSpot.setForeground(new Color(255,255,255,150));
        tablesField.setVisible(false);
        tablesField.setBackground(new Color(0,0,0,100));  
        retryButton.setIcon(getIcon(180, 70, resources.get(4).getResource()));
        retryButton.setPressedIcon(getIcon(180, 70, resources.get(5).getResource()));
        retryButton.setVisible(false);
        retryButton.addActionListener(e->retry());
        editButton.setIcon(getIcon(180, 70,resources.get(4).getResource()));
        editButton.setPressedIcon(getIcon(180, 70,resources.get(5).getResource()));
        editButton.addActionListener(e->edit());
        soldButton.setIcon(getIcon(180, 70,resources.get(4).getResource()));
        soldButton.setPressedIcon(getIcon(180, 70,resources.get(5).getResource()));
        orderButton.setIcon(getIcon(180, 70,resources.get(4).getResource()));
        orderButton.setPressedIcon(getIcon(180, 70,resources.get(5).getResource()));
        orderButton.addActionListener(e-> {
            start = 0;
            
            corelateAreaWithTables(start, 1);
            
            if (areas.isEmpty()) {
                showError("There is no table in your restaurant!"); 
            } else {

                welcomeLogo.setVisible(false);
                titleSpot.setVisible(false);
                logoLabel.setVisible(true);
                tablesField.setVisible(true);
                
                if (tables.size() < 9) {
                showTables(tables.size());
                } else { 
                    showTables(9);
                }
                
                if (areas.size() > 1) {
                    nextButton.setVisible(true);
                }
                
                if (tables.size() > 9) {
                    downButton.setVisible(true);
                }   
            }
        });
        soldButton.setIcon(getIcon(180, 70,resources.get(4).getResource()));
        soldButton.setPressedIcon(getIcon(180, 70,resources.get(5).getResource()));
        soldButton.addActionListener(e-> {
            String username = restaurants.stream()
                .filter(r -> r.getRestaurantID() == restaurantID)
                .map(r -> r.getUsername())
                .findFirst()
                .orElse(null);
            List<Order> finishedOrder = MainServiceMySQL.getInstance().getOrders(username, true);
            List<Order> unfinishedOrder = MainServiceMySQL.getInstance().getOrders(username, false);
            
            if (finishedOrder.isEmpty() && unfinishedOrder.isEmpty()) {
                showError("At this point, you have no order!");
            } else {
                goToSold();
            }
        });
        signOutButton.setIcon(getIcon(120,50,resources.get(4).getResource()));
        signOutButton.setPressedIcon(getIcon(120,50,resources.get(5).getResource()));
        signOutButton.addActionListener(e->logOff());
        prevButton.setIcon(getIcon(100, 100, resources.get(93).getResource()));
        prevButton.setPressedIcon(getIcon(100, 100, resources.get(94).getResource()));
        prevButton.setVisible(false);
        prevButton.addActionListener(e-> {
            upButton.setVisible(false);
            downButton.setVisible(false);
            
            start = 0;
            index--;
            
            text1Spot.setText(areas.get(index-1).getName());
            corelateAreaWithTables(0, index);
            
            if (tables.size() < 9) {
                showTables(tables.size());
            } else { 
                showTables(9);
            }

            if (index - 1 < 1) {
                prevButton.setVisible(false);
            }
            
            nextButton.setVisible(true);
            
            if (tables.size() > 9) {
                downButton.setVisible(true);
            }
        });
        nextButton.setIcon(getIcon(100, 100, resources.get(95).getResource()));
        nextButton.setPressedIcon(getIcon(100, 100, resources.get(96).getResource()));
        nextButton.setVisible(false);
        nextButton.addActionListener(e-> {
            upButton.setVisible(false);
            downButton.setVisible(false);
            
            start = 0;
            index++;
            
            text1Spot.setText(areas.get(index-1).getName());
            
            if (index == 4) {
                text1Spot.setText("Auxiliar Area");
            }
            
            corelateAreaWithTables(0, index);
            
            if (tables.size() < 9) {
                showTables(tables.size());
            } else { 
                showTables(9);
            }

            if (index + 1 > areas.size()) {
                nextButton.setVisible(false);
            }
            
            prevButton.setVisible(true);
            
            if (tables.size() > 9) {
                downButton.setVisible(true);
            }
        });
        ImageIcon downUnpressed = getIcon(100, 100, resources.get(93).getResource());
        ImageIcon downPressed = getIcon(100, 100, resources.get(94).getResource());
        ImageIcon upUnpressed = getIcon(100, 100, resources.get(95).getResource());
        ImageIcon upPressed = getIcon(100, 100, resources.get(96).getResource());
        RotatedIcon rDownUnpressed = new RotatedIcon(downUnpressed, -90.0);
        RotatedIcon rDownPressed = new RotatedIcon(downPressed, -90.0);
        RotatedIcon rUpUnpressed = new RotatedIcon(upUnpressed, 270.0);
        RotatedIcon rUpPressed = new RotatedIcon(upPressed, 270.0);
        downButton.setIcon(rDownUnpressed);
        downButton.setPressedIcon(rDownPressed);
        downButton.setVisible(false);
        downButton.addActionListener(e-> {
            start = start + 9;
            
            corelateAreaWithTables(start, index);
            addTextsTable(start/9);
            
            if (start + 9 <= tables.size()) {
                showTables(9);
            } else {
                showTables(tables.size()%9);
                downButton.setVisible(false);
            }
            
            upButton.setVisible(true);
        });
        upButton.setIcon(rUpUnpressed);
        upButton.setPressedIcon(rUpPressed);
        upButton.setVisible(false);
        upButton.addActionListener(e-> {
            start = start - 9;
            
            corelateAreaWithTables(start, index);
            addTextsTable(start/9);
            showTables(tables.size());
            
            if (start <= 0) {
                upButton.setVisible(false);
            }
            
            downButton.setVisible(true);
        });
        table1Spot.setVisible(false);
        table2Spot.setVisible(false);
        table3Spot.setVisible(false);
        table4Spot.setVisible(false);
        table5Spot.setVisible(false);
        table6Spot.setVisible(false);
        table7Spot.setVisible(false);
        table8Spot.setVisible(false);
        table9Spot.setVisible(false);
    }
    
    //Metoda de intrare in pagina Sold.
    private void goToSold() { 
        SoldFrame sold = new SoldFrame();
        
        sold.setLocationRelativeTo(null);
        sold.setVisible(true);
        sold.settingBackground(background.getIcon(), index, restaurantID, 0);
        this.setVisible(false);
    }
    
    //Metoda de numerotare mese.
    private void addTextsTable(int number) {
        if (number == 0) {
            table1Spot.setText("" + (number*10 + 1));
            table2Spot.setText("" + (number*10 + 2));
            table3Spot.setText("" + (number*10 + 3));
            table4Spot.setText("" + (number*10 + 4));
            table5Spot.setText("" + (number*10 + 5));
            table6Spot.setText("" + (number*10 + 6));
            table7Spot.setText("" + (number*10 + 7));
            table8Spot.setText("" + (number*10 + 8));
            table9Spot.setText("" + (number*10 + 9));
        } else {
            table1Spot.setText("" + (number*10-number + 1));
            table2Spot.setText("" + (number*10-number + 2));
            table3Spot.setText("" + (number*10-number + 3));
            table4Spot.setText("" + (number*10-number + 4));
            table5Spot.setText("" + (number*10-number + 5));
            table6Spot.setText("" + (number*10-number + 6));
            table7Spot.setText("" + (number*10-number + 7));
            table8Spot.setText("" + (number*10-number + 8));
            table9Spot.setText("" + (number*10-number + 9));
        }    
    }
    
    //Metoda de afisare a meselor.
    private void showTables(int number) {
        table1Label.setVisible(false);
        table2Label.setVisible(false);
        table3Label.setVisible(false);
        table4Label.setVisible(false);
        table5Label.setVisible(false);
        table6Label.setVisible(false);
        table7Label.setVisible(false);
        table8Label.setVisible(false);
        table9Label.setVisible(false);
        table1Spot.setVisible(false);
        table2Spot.setVisible(false);
        table3Spot.setVisible(false);
        table4Spot.setVisible(false);
        table5Spot.setVisible(false);
        table6Spot.setVisible(false);
        table7Spot.setVisible(false);
        table8Spot.setVisible(false);
        table9Spot.setVisible(false);
        
        if (start == 0) {
            addTextsTable(0);
        }
        
        if (number == 1) {
            table1Label.setVisible(true);
            table1Spot.setVisible(true);
        } else {
            if (number  == 2) {
                table1Label.setVisible(true);
                table2Label.setVisible(true);
                table1Spot.setVisible(true);
                table2Spot.setVisible(true);
            } else {
                if (number  == 3) {
                table1Label.setVisible(true);
                table2Label.setVisible(true);
                table3Label.setVisible(true);
                table1Spot.setVisible(true);
                table2Spot.setVisible(true);
                table3Spot.setVisible(true);
                } else {
                    if (number  == 4) {
                        table1Label.setVisible(true);
                        table2Label.setVisible(true);
                        table3Label.setVisible(true);
                        table4Label.setVisible(true);
                        table1Spot.setVisible(true);
                        table2Spot.setVisible(true);
                        table3Spot.setVisible(true);
                        table4Spot.setVisible(true);
                    } else {
                        if (number == 5) {
                            table1Label.setVisible(true);
                            table2Label.setVisible(true);
                            table3Label.setVisible(true);
                            table4Label.setVisible(true);
                            table5Label.setVisible(true);
                            table1Spot.setVisible(true);
                            table2Spot.setVisible(true);
                            table3Spot.setVisible(true);
                            table4Spot.setVisible(true);
                            table5Spot.setVisible(true);
                        } else {
                            if (number  == 6) {
                                table1Label.setVisible(true);
                                table2Label.setVisible(true);
                                table3Label.setVisible(true);
                                table4Label.setVisible(true);
                                table5Label.setVisible(true);
                                table6Label.setVisible(true);
                                table1Spot.setVisible(true);
                                table2Spot.setVisible(true);
                                table3Spot.setVisible(true);
                                table4Spot.setVisible(true);
                                table5Spot.setVisible(true);
                                table6Spot.setVisible(true);
                            } else {
                                if (number  == 7) {
                                    table1Label.setVisible(true);
                                    table2Label.setVisible(true);
                                    table3Label.setVisible(true);
                                    table4Label.setVisible(true);
                                    table5Label.setVisible(true);
                                    table6Label.setVisible(true);
                                    table7Label.setVisible(true);
                                    table6Label.setVisible(true);
                                    table1Spot.setVisible(true);
                                    table2Spot.setVisible(true);
                                    table3Spot.setVisible(true);
                                    table4Spot.setVisible(true);
                                    table5Spot.setVisible(true);
                                    table6Spot.setVisible(true);
                                    table7Spot.setVisible(true);
                                } else {
                                    if (number  == 8) {
                                        table1Label.setVisible(true);
                                        table2Label.setVisible(true);
                                        table3Label.setVisible(true);
                                        table4Label.setVisible(true);
                                        table5Label.setVisible(true);
                                        table6Label.setVisible(true);
                                        table7Label.setVisible(true);
                                        table8Label.setVisible(true);
                                        table1Spot.setVisible(true);
                                        table2Spot.setVisible(true);
                                        table3Spot.setVisible(true);
                                        table4Spot.setVisible(true);
                                        table5Spot.setVisible(true);
                                        table6Spot.setVisible(true);
                                        table7Spot.setVisible(true);
                                        table8Spot.setVisible(true);
                                    } else { 
                                        table1Label.setVisible(true);
                                        table2Label.setVisible(true);
                                        table3Label.setVisible(true);
                                        table4Label.setVisible(true);
                                        table5Label.setVisible(true);
                                        table6Label.setVisible(true);
                                        table7Label.setVisible(true);
                                        table8Label.setVisible(true);
                                        table9Label.setVisible(true);
                                        table1Spot.setVisible(true);
                                        table2Spot.setVisible(true);
                                        table3Spot.setVisible(true);
                                        table4Spot.setVisible(true);
                                        table5Spot.setVisible(true);
                                        table6Spot.setVisible(true);
                                        table7Spot.setVisible(true);
                                        table8Spot.setVisible(true);
                                        table9Spot.setVisible(true);
                                    }
                                }   
                            }   
                        }   
                    }   
                }   
            }
        }
    }
    
    //Metoda de setare a reprezentarii grafice pentru mese, in starea liber sau ocupat.
    private void chooseTableLabel(int index, Resource resource) {
        if (index == 0) {
            table1Label.setIcon(getIcon(200,150,resource.getResource()));
        } else {
            if (index == 1) {
                table2Label.setIcon(getIcon(200,150,resource.getResource()));
            } else {
                if (index == 2) {
                    table3Label.setIcon(getIcon(200,150,resource.getResource()));
                }  else {
                    if (index  == 3) {
                        table4Label.setIcon(getIcon(200,150,resource.getResource()));
                    } else {
                        if (index  == 4) {
                            table5Label.setIcon(getIcon(200,150,resource.getResource()));
                        } else {
                            if (index == 5) {
                                table6Label.setIcon(getIcon(200,150,resource.getResource()));
                            } else {
                                if (index  == 6) {
                                    table7Label.setIcon(getIcon(200,150,resource.getResource()));
                                } else {
                                    if (index  == 7) {
                                        table8Label.setIcon(getIcon(200,150,resource.getResource()));
                                    } else {
                                        table9Label.setIcon(getIcon(200,150,resource.getResource()));
                                    }
                                } 
                            }
                        }
                    } 
                }
            }
        }
    }
    
    //Corelare zone cu masa si specificare grafica a starii meselor: liber, ocupat +  numar persoane la masa
    private void corelateAreaWithTables(int start, int index) {
        String username = restaurants.stream()
                .filter(r -> r.getRestaurantID() == restaurantID)
                .map(r -> r.getUsername())
                .findFirst()
                .orElse(null);
        areas = MainServiceMySQL.getInstance().getAreas(username);
        
        if (!areas.isEmpty()) {
            
            String areaName = areas.stream()
                    .filter(a -> a.getAreaID() == areas.get(index-1).getAreaID())
                    .map(r -> r.getName())
                    .findFirst()
                    .orElse(null);
            List<Order> unfinishedOrders = MainServiceMySQL.getInstance().getOrders(username, false);
            int totalNumber = MainServiceMySQL.getInstance().getTotal(username);
            int size;
            int startf = start; 
            
            text1Spot.setText(areaName);
            
            tables = MainServiceMySQL.getInstance().getTables(areaName, username);
            
            text2Spot.setText(tables.size() + "/" + totalNumber);
            
            if (tables.size() <10) {
                size = tables.size();
            } else {
                if (startf == 0) {
                    size = 9;
                } else {
                    if (tables.size() < startf+9) {
                        size = tables.size();  
                    } else {
                         size = startf + 9;
                    } 
                } 
            }
            
            for (int i = startf; i <size; i++) {
                if (tables.get(i).getType() == 1) {
                    boolean ocupied = false;
                    
                    for (Order order:unfinishedOrders) {
                        if (tables.get(i).getTableID() == order.getTableID()) {
                            ocupied = true;
                            break;
                        }
                    }
                    
                    if (ocupied == true) {
                        if (tables.get(i).getNumber() == 1) {
                            chooseTableLabel(i%9, resources.get(15));
                        } else {
                            if (tables.get(i).getNumber() == 2) {
                                chooseTableLabel(i%9, resources.get(17));
                            } else {
                                if (tables.get(i).getNumber() == 3) {
                                    chooseTableLabel(i%9, resources.get(19));
                                } else {
                                    chooseTableLabel(i%9,resources.get(21));
                                }
                            }
                        }
                    } else {
                        chooseTableLabel(i%9,resources.get(23));
                    }
                } else {
                    if (tables.get(i).getType() == 2 ) {
                        boolean ocupied = false;
                        
                        for (Order order:unfinishedOrders) {
                            if (tables.get(i).getTableID() == order.getTableID()) {
                                ocupied = true;
                                
                                break;
                            }
                        }
                        
                        if (ocupied == true) {
                            if (tables.get(i).getNumber() == 1) {
                                chooseTableLabel(i%9, resources.get(24));
                            } else {
                                if (tables.get(i).getNumber() == 2) {
                                    chooseTableLabel(i%9, resources.get(26));
                                } else {
                                    if (tables.get(i).getNumber() == 3) {
                                        chooseTableLabel(i%9, resources.get(28));
                                    } else {
                                        if (tables.get(i).getNumber() == 4) {
                                            chooseTableLabel(i%9, resources.get(30));
                                        } else {
                                            if (tables.get(i).getNumber() == 5) {
                                                chooseTableLabel(i%9, resources.get(32));
                                            } else {
                                                chooseTableLabel(i%9, resources.get(34));
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            chooseTableLabel(i%9, resources.get(36));
                        }
                    } else {
                            if (tables.get(i).getType() == 3 ) {
                                boolean ocupied = false;
                                
                                for (Order order:unfinishedOrders) {
                                    if (tables.get(i).getTableID() == order.getTableID()) {
                                        ocupied = true;
                                        
                                        break;
                                    }
                                }
                                
                                if (ocupied == true) {
                                    if (tables.get(i).getNumber() == 1) {
                                        chooseTableLabel(i%9, resources.get(37));
                                    } else {
                                        if (tables.get(i).getNumber() == 2) {
                                            chooseTableLabel(i%9, resources.get(39));
                                        } else {
                                            if (tables.get(i).getNumber() == 3) {
                                                chooseTableLabel(i%9, resources.get(41));
                                            } else {
                                                if (tables.get(i).getNumber() == 4) {
                                                    chooseTableLabel(i%9, resources.get(43));
                                                } else {
                                                    if (tables.get(i).getNumber() == 5) {
                                                        chooseTableLabel(i%9, resources.get(45));
                                                    } else {
                                                        if (tables.get(i).getNumber() == 6) {
                                                            chooseTableLabel(i%9, resources.get(47));
                                                        } else {
                                                            if (tables.get(i).getNumber() == 7) {
                                                                chooseTableLabel(i%9, resources.get(49));
                                                            } else {
                                                                chooseTableLabel(i%9, resources.get(51));
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    chooseTableLabel(i%9,resources.get(53));
                                }
                            } else {
                                boolean ocupied = false;
                                
                                for (Order order:unfinishedOrders) {
                                    if (tables.get(i).getTableID() == order.getTableID()){
                                        ocupied = true;
                                        
                                        break;
                                    }
                                }
                                
                                if (ocupied == true) {
                                    if (tables.get(i).getNumber() == 1) {
                                        chooseTableLabel(i%9, resources.get(54));
                                    } else {
                                        if (tables.get(i).getNumber() == 2) {
                                            chooseTableLabel(i%9, resources.get(56));
                                        } else {
                                            if (tables.get(i).getNumber() == 3) {
                                                chooseTableLabel(i%9, resources.get(58));
                                            } else {
                                                if (tables.get(i).getNumber() == 4) {
                                                    chooseTableLabel(i%9, resources.get(60));
                                                } else {
                                                    if (tables.get(i).getNumber() == 5) {
                                                        chooseTableLabel(i%9, resources.get(62));
                                                    } else {
                                                        if (tables.get(i).getNumber() == 6) {
                                                            chooseTableLabel(i%9, resources.get(64));
                                                        } else {
                                                            if (tables.get(i).getNumber() == 7) {
                                                                chooseTableLabel(i%9, resources.get(66));
                                                            } else {
                                                                if (tables.get(i).getNumber() == 8) {
                                                                    chooseTableLabel(i%9, resources.get(68));
                                                                } else {
                                                                    if (tables.get(i).getNumber() == 9) {
                                                                        chooseTableLabel(i%9, resources.get(70));
                                                                    } else {
                                                                        chooseTableLabel(i%9, resources.get(72));
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    chooseTableLabel(i%9,resources.get(74));
                                }   
                            }
                        }
                    } 
            }
            this.index = index;
        } 
    }
    
    //Metoda de afisare a erorii.
    private void showError(String message) {
        signOutButton.setVisible(false);
        editButton.setVisible(false);
        orderButton.setVisible(false);
        soldButton.setVisible(false);
        allertMessageLabel.setVisible(true);
        allertMessagePane.setVisible(true);
        allertMessageField.setText("<htm><center><b><font face =yu-gothic size=32 color=rgb(255,255,255)>"+message+"</b></center></html>");
        retryButton.setVisible(true);
        inactiveBackground.setVisible(true);
    }
    
    //Metoda de revenire la pagina principala, dupa aparitia unei erori.
    private void retry() {
        allertMessageLabel.setVisible(false);
        allertMessagePane.setVisible(false);
        retryButton.setVisible(false);
        inactiveBackground.setVisible(false);
        signOutButton.setVisible(true);
        editButton.setVisible(true);
        orderButton.setVisible(true);
        soldButton.setVisible(true);
    }
    
    //Metoda de intrare in pagina Edit.
    private void edit() {
        String username = restaurants.stream()
                .filter(r -> r.getRestaurantID() == restaurantID)
                .map(r -> r.getUsername())
                .findFirst()
                .orElse(null);
        List<Order> unfinishedOrders = MainServiceMySQL.getInstance().getOrders(username, false);
        if (!unfinishedOrders.isEmpty()) {
            showError("You can edit while is activity in your restaurant!");
        } else {
            EditFrame edit = new EditFrame();
        
            edit.setLocationRelativeTo(null);
            edit.setVisible(true);
            edit.settingBackground(background.getIcon(), index, restaurantID);
            this.setVisible(false);
        }
    }
    
    //Metoda de 
    private void  logOff(){
        SignInFrame frame = new SignInFrame();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.settingBackground(background.getIcon(), index);
        this.setVisible(false);
    }
    
    //Metoda de setare a fundalului apelata din pagina Sign In.
    public void settingBackground( Icon resource, int index, int restaurantID){
       background.setIcon(resource);
       
       this.index = index;
       this.restaurantID = restaurantID;
    }
    
    //Metoda de setare a logo-ului restaurantului, apelata din pagina Sign In.
    public void  setLogoField(Icon resource){
        logoLabel.setIcon(resource);
    }
    
    //Metoda folosita de setare a iconitelor preluate din baza de date, a tuturor componentelor(butoane, cadre, fundaluri).
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
     *Nu modifica!
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        retryButton = new javax.swing.JButton();
        allertMessagePane = new javax.swing.JScrollPane();
        allertMessageField = new javax.swing.JTextPane();
        allertMessageLabel = new javax.swing.JLabel();
        inactiveBackground = new javax.swing.JLabel();
        soldButton = new javax.swing.JButton();
        logoLabel = new javax.swing.JLabel();
        orderButton = new javax.swing.JButton();
        editButton = new javax.swing.JButton();
        text1Spot = new javax.swing.JTextField();
        text2Spot = new javax.swing.JTextField();
        table1Spot = new javax.swing.JTextField();
        table2Spot = new javax.swing.JTextField();
        table3Spot = new javax.swing.JTextField();
        table4Spot = new javax.swing.JTextField();
        table5Spot = new javax.swing.JTextField();
        table6Spot = new javax.swing.JTextField();
        table7Spot = new javax.swing.JTextField();
        table8Spot = new javax.swing.JTextField();
        table9Spot = new javax.swing.JTextField();
        table1Label = new javax.swing.JLabel();
        table2Label = new javax.swing.JLabel();
        table3Label = new javax.swing.JLabel();
        table4Label = new javax.swing.JLabel();
        table5Label = new javax.swing.JLabel();
        table6Label = new javax.swing.JLabel();
        table7Label = new javax.swing.JLabel();
        table8Label = new javax.swing.JLabel();
        table9Label = new javax.swing.JLabel();
        prevButton = new javax.swing.JButton();
        nextButton = new javax.swing.JButton();
        downButton = new javax.swing.JButton();
        upButton = new javax.swing.JButton();
        tablesField = new javax.swing.JLabel();
        titleSpot = new javax.swing.JTextField();
        welcomeLogo = new javax.swing.JLabel();
        signOutButton = new javax.swing.JButton();
        background = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(1400, 780));
        setName("Admin"); // NOI18N
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

        soldButton.setBackground(new java.awt.Color(255, 255, 255));
        soldButton.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 30)); // NOI18N
        soldButton.setForeground(new java.awt.Color(255, 255, 255));
        soldButton.setBorder(null);
        soldButton.setContentAreaFilled(false);
        soldButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        soldButton.setLabel("Sold");
        soldButton.setMaximumSize(new java.awt.Dimension(180, 70));
        soldButton.setMinimumSize(new java.awt.Dimension(0, 0));
        soldButton.setPreferredSize(new java.awt.Dimension(180, 70));
        getContentPane().add(soldButton);
        soldButton.setBounds(460, 30, 180, 70);

        logoLabel.setBackground(new java.awt.Color(255, 255, 255));
        logoLabel.setForeground(new java.awt.Color(255, 255, 255));
        logoLabel.setMaximumSize(new java.awt.Dimension(300, 180));
        logoLabel.setPreferredSize(new java.awt.Dimension(300, 180));
        getContentPane().add(logoLabel);
        logoLabel.setBounds(1080, 30, 300, 180);

        orderButton.setBackground(new java.awt.Color(255, 255, 255));
        orderButton.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 30)); // NOI18N
        orderButton.setForeground(new java.awt.Color(255, 255, 255));
        orderButton.setText("Order");
        orderButton.setBorder(null);
        orderButton.setContentAreaFilled(false);
        orderButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        orderButton.setMaximumSize(new java.awt.Dimension(180, 70));
        orderButton.setMinimumSize(new java.awt.Dimension(0, 0));
        orderButton.setPreferredSize(new java.awt.Dimension(180, 70));
        getContentPane().add(orderButton);
        orderButton.setBounds(250, 30, 180, 70);

        editButton.setBackground(new java.awt.Color(255, 255, 255));
        editButton.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 30)); // NOI18N
        editButton.setForeground(new java.awt.Color(255, 255, 255));
        editButton.setText("Edit");
        editButton.setBorder(null);
        editButton.setContentAreaFilled(false);
        editButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        editButton.setMaximumSize(new java.awt.Dimension(190, 80));
        editButton.setMinimumSize(new java.awt.Dimension(0, 0));
        editButton.setName(""); // NOI18N
        editButton.setPreferredSize(new java.awt.Dimension(180, 70));
        getContentPane().add(editButton);
        editButton.setBounds(40, 30, 180, 70);

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
        text1Spot.setBounds(400, 680, 140, 40);

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
        text2Spot.setBounds(410, 120, 100, 40);

        table1Spot.setEditable(false);
        table1Spot.setBackground(new java.awt.Color(255, 255, 255));
        table1Spot.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 60)); // NOI18N
        table1Spot.setForeground(new java.awt.Color(255, 255, 255));
        table1Spot.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        table1Spot.setText("1");
        table1Spot.setToolTipText("");
        table1Spot.setBorder(null);
        table1Spot.setCaretColor(new java.awt.Color(255, 255, 255));
        table1Spot.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        table1Spot.setMaximumSize(new java.awt.Dimension(200, 150));
        table1Spot.setMinimumSize(new java.awt.Dimension(0, 0));
        table1Spot.setOpaque(false);
        table1Spot.setPreferredSize(new java.awt.Dimension(200, 150));
        table1Spot.setSelectionColor(new java.awt.Color(255, 255, 255));
        getContentPane().add(table1Spot);
        table1Spot.setBounds(100, 170, 200, 150);

        table2Spot.setEditable(false);
        table2Spot.setBackground(new java.awt.Color(255, 255, 255));
        table2Spot.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 60)); // NOI18N
        table2Spot.setForeground(new java.awt.Color(255, 255, 255));
        table2Spot.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        table2Spot.setText("2");
        table2Spot.setToolTipText("");
        table2Spot.setBorder(null);
        table2Spot.setCaretColor(new java.awt.Color(255, 255, 255));
        table2Spot.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        table2Spot.setMaximumSize(new java.awt.Dimension(200, 150));
        table2Spot.setMinimumSize(new java.awt.Dimension(0, 0));
        table2Spot.setOpaque(false);
        table2Spot.setPreferredSize(new java.awt.Dimension(200, 150));
        table2Spot.setSelectionColor(new java.awt.Color(255, 255, 255));
        getContentPane().add(table2Spot);
        table2Spot.setBounds(620, 170, 200, 150);

        table3Spot.setEditable(false);
        table3Spot.setBackground(new java.awt.Color(255, 255, 255));
        table3Spot.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 60)); // NOI18N
        table3Spot.setForeground(new java.awt.Color(255, 255, 255));
        table3Spot.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        table3Spot.setText("3");
        table3Spot.setToolTipText("");
        table3Spot.setBorder(null);
        table3Spot.setCaretColor(new java.awt.Color(255, 255, 255));
        table3Spot.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        table3Spot.setMaximumSize(new java.awt.Dimension(200, 150));
        table3Spot.setMinimumSize(new java.awt.Dimension(0, 0));
        table3Spot.setOpaque(false);
        table3Spot.setPreferredSize(new java.awt.Dimension(200, 150));
        table3Spot.setSelectionColor(new java.awt.Color(255, 255, 255));
        getContentPane().add(table3Spot);
        table3Spot.setBounds(620, 520, 200, 150);

        table4Spot.setEditable(false);
        table4Spot.setBackground(new java.awt.Color(255, 255, 255));
        table4Spot.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 60)); // NOI18N
        table4Spot.setForeground(new java.awt.Color(255, 255, 255));
        table4Spot.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        table4Spot.setText("4");
        table4Spot.setToolTipText("");
        table4Spot.setBorder(null);
        table4Spot.setCaretColor(new java.awt.Color(255, 255, 255));
        table4Spot.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        table4Spot.setMaximumSize(new java.awt.Dimension(200, 150));
        table4Spot.setMinimumSize(new java.awt.Dimension(0, 0));
        table4Spot.setOpaque(false);
        table4Spot.setPreferredSize(new java.awt.Dimension(200, 150));
        table4Spot.setSelectionColor(new java.awt.Color(255, 255, 255));
        getContentPane().add(table4Spot);
        table4Spot.setBounds(100, 520, 200, 150);

        table5Spot.setEditable(false);
        table5Spot.setBackground(new java.awt.Color(255, 255, 255));
        table5Spot.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 60)); // NOI18N
        table5Spot.setForeground(new java.awt.Color(255, 255, 255));
        table5Spot.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        table5Spot.setText("5");
        table5Spot.setToolTipText("");
        table5Spot.setBorder(null);
        table5Spot.setCaretColor(new java.awt.Color(255, 255, 255));
        table5Spot.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        table5Spot.setMaximumSize(new java.awt.Dimension(200, 150));
        table5Spot.setMinimumSize(new java.awt.Dimension(0, 0));
        table5Spot.setOpaque(false);
        table5Spot.setPreferredSize(new java.awt.Dimension(200, 150));
        table5Spot.setSelectionColor(new java.awt.Color(255, 255, 255));
        getContentPane().add(table5Spot);
        table5Spot.setBounds(360, 345, 200, 160);

        table6Spot.setEditable(false);
        table6Spot.setBackground(new java.awt.Color(255, 255, 255));
        table6Spot.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 60)); // NOI18N
        table6Spot.setForeground(new java.awt.Color(255, 255, 255));
        table6Spot.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        table6Spot.setText("6");
        table6Spot.setToolTipText("");
        table6Spot.setBorder(null);
        table6Spot.setCaretColor(new java.awt.Color(255, 255, 255));
        table6Spot.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        table6Spot.setMaximumSize(new java.awt.Dimension(200, 150));
        table6Spot.setMinimumSize(new java.awt.Dimension(0, 0));
        table6Spot.setOpaque(false);
        table6Spot.setPreferredSize(new java.awt.Dimension(200, 150));
        table6Spot.setSelectionColor(new java.awt.Color(255, 255, 255));
        getContentPane().add(table6Spot);
        table6Spot.setBounds(100, 345, 200, 150);

        table7Spot.setEditable(false);
        table7Spot.setBackground(new java.awt.Color(255, 255, 255));
        table7Spot.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 60)); // NOI18N
        table7Spot.setForeground(new java.awt.Color(255, 255, 255));
        table7Spot.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        table7Spot.setText("7");
        table7Spot.setToolTipText("");
        table7Spot.setBorder(null);
        table7Spot.setCaretColor(new java.awt.Color(255, 255, 255));
        table7Spot.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        table7Spot.setMaximumSize(new java.awt.Dimension(200, 150));
        table7Spot.setMinimumSize(new java.awt.Dimension(0, 0));
        table7Spot.setOpaque(false);
        table7Spot.setPreferredSize(new java.awt.Dimension(200, 150));
        table7Spot.setSelectionColor(new java.awt.Color(255, 255, 255));
        getContentPane().add(table7Spot);
        table7Spot.setBounds(620, 345, 200, 150);

        table8Spot.setEditable(false);
        table8Spot.setBackground(new java.awt.Color(255, 255, 255));
        table8Spot.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 60)); // NOI18N
        table8Spot.setForeground(new java.awt.Color(255, 255, 255));
        table8Spot.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        table8Spot.setText("8");
        table8Spot.setToolTipText("");
        table8Spot.setBorder(null);
        table8Spot.setCaretColor(new java.awt.Color(255, 255, 255));
        table8Spot.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        table8Spot.setMaximumSize(new java.awt.Dimension(200, 150));
        table8Spot.setMinimumSize(new java.awt.Dimension(0, 0));
        table8Spot.setOpaque(false);
        table8Spot.setPreferredSize(new java.awt.Dimension(200, 150));
        table8Spot.setSelectionColor(new java.awt.Color(255, 255, 255));
        getContentPane().add(table8Spot);
        table8Spot.setBounds(360, 520, 200, 150);

        table9Spot.setEditable(false);
        table9Spot.setBackground(new java.awt.Color(255, 255, 255));
        table9Spot.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 60)); // NOI18N
        table9Spot.setForeground(new java.awt.Color(255, 255, 255));
        table9Spot.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        table9Spot.setText("9");
        table9Spot.setToolTipText("");
        table9Spot.setBorder(null);
        table9Spot.setCaretColor(new java.awt.Color(255, 255, 255));
        table9Spot.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        table9Spot.setMaximumSize(new java.awt.Dimension(200, 150));
        table9Spot.setMinimumSize(new java.awt.Dimension(0, 0));
        table9Spot.setOpaque(false);
        table9Spot.setPreferredSize(new java.awt.Dimension(200, 150));
        table9Spot.setSelectionColor(new java.awt.Color(255, 255, 255));
        getContentPane().add(table9Spot);
        table9Spot.setBounds(360, 170, 200, 150);

        table1Label.setBackground(new java.awt.Color(255, 255, 255));
        table1Label.setForeground(new java.awt.Color(255, 255, 255));
        table1Label.setMaximumSize(new java.awt.Dimension(200, 150));
        table1Label.setPreferredSize(new java.awt.Dimension(200, 150));
        table1Label.setRequestFocusEnabled(false);
        table1Label.setVerifyInputWhenFocusTarget(false);
        getContentPane().add(table1Label);
        table1Label.setBounds(100, 170, 200, 150);

        table2Label.setBackground(new java.awt.Color(255, 255, 255));
        table2Label.setForeground(new java.awt.Color(255, 255, 255));
        table2Label.setMaximumSize(new java.awt.Dimension(850, 600));
        table2Label.setPreferredSize(new java.awt.Dimension(850, 600));
        table2Label.setRequestFocusEnabled(false);
        table2Label.setVerifyInputWhenFocusTarget(false);
        getContentPane().add(table2Label);
        table2Label.setBounds(620, 170, 200, 150);

        table3Label.setBackground(new java.awt.Color(255, 255, 255));
        table3Label.setForeground(new java.awt.Color(255, 255, 255));
        table3Label.setMaximumSize(new java.awt.Dimension(850, 600));
        table3Label.setPreferredSize(new java.awt.Dimension(850, 600));
        table3Label.setRequestFocusEnabled(false);
        table3Label.setVerifyInputWhenFocusTarget(false);
        getContentPane().add(table3Label);
        table3Label.setBounds(620, 520, 200, 150);

        table4Label.setBackground(new java.awt.Color(255, 255, 255));
        table4Label.setForeground(new java.awt.Color(255, 255, 255));
        table4Label.setMaximumSize(new java.awt.Dimension(850, 600));
        table4Label.setPreferredSize(new java.awt.Dimension(850, 600));
        table4Label.setRequestFocusEnabled(false);
        table4Label.setVerifyInputWhenFocusTarget(false);
        getContentPane().add(table4Label);
        table4Label.setBounds(100, 520, 200, 150);

        table5Label.setBackground(new java.awt.Color(255, 255, 255));
        table5Label.setForeground(new java.awt.Color(255, 255, 255));
        table5Label.setMaximumSize(new java.awt.Dimension(850, 600));
        table5Label.setPreferredSize(new java.awt.Dimension(850, 600));
        table5Label.setRequestFocusEnabled(false);
        table5Label.setVerifyInputWhenFocusTarget(false);
        getContentPane().add(table5Label);
        table5Label.setBounds(360, 345, 200, 150);

        table6Label.setBackground(new java.awt.Color(255, 255, 255));
        table6Label.setForeground(new java.awt.Color(255, 255, 255));
        table6Label.setMaximumSize(new java.awt.Dimension(200, 150));
        table6Label.setPreferredSize(new java.awt.Dimension(200, 150));
        table6Label.setRequestFocusEnabled(false);
        table6Label.setVerifyInputWhenFocusTarget(false);
        getContentPane().add(table6Label);
        table6Label.setBounds(100, 345, 200, 150);

        table7Label.setBackground(new java.awt.Color(255, 255, 255));
        table7Label.setForeground(new java.awt.Color(255, 255, 255));
        table7Label.setMaximumSize(new java.awt.Dimension(850, 600));
        table7Label.setPreferredSize(new java.awt.Dimension(850, 600));
        table7Label.setRequestFocusEnabled(false);
        table7Label.setVerifyInputWhenFocusTarget(false);
        getContentPane().add(table7Label);
        table7Label.setBounds(620, 345, 200, 150);

        table8Label.setBackground(new java.awt.Color(255, 255, 255));
        table8Label.setForeground(new java.awt.Color(255, 255, 255));
        table8Label.setMaximumSize(new java.awt.Dimension(850, 600));
        table8Label.setPreferredSize(new java.awt.Dimension(850, 600));
        table8Label.setRequestFocusEnabled(false);
        table8Label.setVerifyInputWhenFocusTarget(false);
        getContentPane().add(table8Label);
        table8Label.setBounds(360, 520, 200, 150);

        table9Label.setBackground(new java.awt.Color(255, 255, 255));
        table9Label.setForeground(new java.awt.Color(255, 255, 255));
        table9Label.setMaximumSize(new java.awt.Dimension(850, 600));
        table9Label.setPreferredSize(new java.awt.Dimension(850, 600));
        table9Label.setRequestFocusEnabled(false);
        table9Label.setVerifyInputWhenFocusTarget(false);
        getContentPane().add(table9Label);
        table9Label.setBounds(360, 170, 200, 150);

        prevButton.setBackground(new java.awt.Color(255, 255, 255));
        prevButton.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 36)); // NOI18N
        prevButton.setForeground(new java.awt.Color(255, 255, 255));
        prevButton.setBorder(null);
        prevButton.setBorderPainted(false);
        prevButton.setContentAreaFilled(false);
        prevButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        prevButton.setInheritsPopupMenu(true);
        prevButton.setMaximumSize(new java.awt.Dimension(100, 100));
        prevButton.setMinimumSize(new java.awt.Dimension(0, 0));
        prevButton.setPreferredSize(new java.awt.Dimension(100, 100));
        prevButton.setRequestFocusEnabled(false);
        getContentPane().add(prevButton);
        prevButton.setBounds(35, 625, 100, 100);

        nextButton.setBackground(new java.awt.Color(255, 255, 255));
        nextButton.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 36)); // NOI18N
        nextButton.setForeground(new java.awt.Color(255, 255, 255));
        nextButton.setBorder(null);
        nextButton.setBorderPainted(false);
        nextButton.setContentAreaFilled(false);
        nextButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        nextButton.setInheritsPopupMenu(true);
        nextButton.setMaximumSize(new java.awt.Dimension(100, 100));
        nextButton.setMinimumSize(new java.awt.Dimension(0, 0));
        nextButton.setPreferredSize(new java.awt.Dimension(100, 100));
        nextButton.setRequestFocusEnabled(false);
        getContentPane().add(nextButton);
        nextButton.setBounds(785, 625, 100, 100);

        downButton.setBackground(new java.awt.Color(255, 255, 255));
        downButton.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 36)); // NOI18N
        downButton.setForeground(new java.awt.Color(255, 255, 255));
        downButton.setBorder(null);
        downButton.setBorderPainted(false);
        downButton.setContentAreaFilled(false);
        downButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        downButton.setInheritsPopupMenu(true);
        downButton.setMaximumSize(new java.awt.Dimension(100, 100));
        downButton.setMinimumSize(new java.awt.Dimension(0, 0));
        downButton.setPreferredSize(new java.awt.Dimension(100, 100));
        downButton.setRequestFocusEnabled(false);
        getContentPane().add(downButton);
        downButton.setBounds(795, 115, 100, 100);

        upButton.setBackground(new java.awt.Color(255, 255, 255));
        upButton.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 36)); // NOI18N
        upButton.setForeground(new java.awt.Color(255, 255, 255));
        upButton.setBorder(null);
        upButton.setBorderPainted(false);
        upButton.setContentAreaFilled(false);
        upButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        upButton.setInheritsPopupMenu(true);
        upButton.setMaximumSize(new java.awt.Dimension(100, 100));
        upButton.setMinimumSize(new java.awt.Dimension(0, 0));
        upButton.setPreferredSize(new java.awt.Dimension(100, 100));
        upButton.setRequestFocusEnabled(false);
        getContentPane().add(upButton);
        upButton.setBounds(35, 115, 100, 100);

        tablesField.setBackground(new java.awt.Color(255, 255, 255));
        tablesField.setForeground(new java.awt.Color(255, 255, 255));
        tablesField.setMaximumSize(new java.awt.Dimension(850, 600));
        tablesField.setOpaque(true);
        tablesField.setPreferredSize(new java.awt.Dimension(850, 600));
        tablesField.setRequestFocusEnabled(false);
        tablesField.setVerifyInputWhenFocusTarget(false);
        getContentPane().add(tablesField);
        tablesField.setBounds(40, 120, 850, 600);

        titleSpot.setEditable(false);
        titleSpot.setBackground(new java.awt.Color(255, 255, 255));
        titleSpot.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 65)); // NOI18N
        titleSpot.setForeground(new java.awt.Color(255, 255, 255));
        titleSpot.setText("Smart Orders&Services is Here for You");
        titleSpot.setBorder(null);
        titleSpot.setCaretColor(new java.awt.Color(255, 255, 255));
        titleSpot.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        titleSpot.setMaximumSize(new java.awt.Dimension(990, 120));
        titleSpot.setMinimumSize(new java.awt.Dimension(0, 0));
        titleSpot.setOpaque(false);
        titleSpot.setPreferredSize(new java.awt.Dimension(990, 120));
        titleSpot.setSelectionColor(new java.awt.Color(255, 255, 255));
        getContentPane().add(titleSpot);
        titleSpot.setBounds(120, 120, 1270, 110);

        welcomeLogo.setBackground(new java.awt.Color(255, 255, 255));
        welcomeLogo.setForeground(new java.awt.Color(255, 255, 255));
        welcomeLogo.setMaximumSize(new java.awt.Dimension(300, 180));
        welcomeLogo.setPreferredSize(new java.awt.Dimension(300, 180));
        getContentPane().add(welcomeLogo);
        welcomeLogo.setBounds(415, 310, 510, 290);

        signOutButton.setBackground(new java.awt.Color(255, 255, 255));
        signOutButton.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 20)); // NOI18N
        signOutButton.setForeground(new java.awt.Color(255, 255, 255));
        signOutButton.setText("Sign Out");
        signOutButton.setBorder(null);
        signOutButton.setContentAreaFilled(false);
        signOutButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        signOutButton.setMaximumSize(new java.awt.Dimension(120, 50));
        signOutButton.setMinimumSize(new java.awt.Dimension(0, 0));
        signOutButton.setPreferredSize(new java.awt.Dimension(120, 50));
        getContentPane().add(signOutButton);
        signOutButton.setBounds(1210, 670, 120, 50);

        background.setBackground(new java.awt.Color(255, 255, 255));
        background.setForeground(new java.awt.Color(255, 255, 255));
        background.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        background.setText("jLabel1");
        background.setMaximumSize(new java.awt.Dimension(1400, 780));
        background.setMinimumSize(new java.awt.Dimension(0, 0));
        background.setPreferredSize(new java.awt.Dimension(1400, 780));
        getContentPane().add(background);
        background.setBounds(0, 0, 1400, 780);
        background.getAccessibleContext().setAccessibleName("background");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextPane allertMessageField;
    private javax.swing.JLabel allertMessageLabel;
    private javax.swing.JScrollPane allertMessagePane;
    private javax.swing.JLabel background;
    private javax.swing.JButton downButton;
    private javax.swing.JButton editButton;
    private javax.swing.JLabel inactiveBackground;
    private javax.swing.JLabel logoLabel;
    private javax.swing.JButton nextButton;
    private javax.swing.JButton orderButton;
    private javax.swing.JButton prevButton;
    private javax.swing.JButton retryButton;
    private javax.swing.JButton signOutButton;
    private javax.swing.JButton soldButton;
    private javax.swing.JLabel table1Label;
    private javax.swing.JTextField table1Spot;
    private javax.swing.JLabel table2Label;
    private javax.swing.JTextField table2Spot;
    private javax.swing.JLabel table3Label;
    private javax.swing.JTextField table3Spot;
    private javax.swing.JLabel table4Label;
    private javax.swing.JTextField table4Spot;
    private javax.swing.JLabel table5Label;
    private javax.swing.JTextField table5Spot;
    private javax.swing.JLabel table6Label;
    private javax.swing.JTextField table6Spot;
    private javax.swing.JLabel table7Label;
    private javax.swing.JTextField table7Spot;
    private javax.swing.JLabel table8Label;
    private javax.swing.JTextField table8Spot;
    private javax.swing.JLabel table9Label;
    private javax.swing.JTextField table9Spot;
    private javax.swing.JLabel tablesField;
    private javax.swing.JTextField text1Spot;
    private javax.swing.JTextField text2Spot;
    private javax.swing.JTextField titleSpot;
    private javax.swing.JButton upButton;
    private javax.swing.JLabel welcomeLogo;
    // End of variables declaration//GEN-END:variables
}
