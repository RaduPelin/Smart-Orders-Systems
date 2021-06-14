/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import view.SignInFrame;

/**
 *
 * @author Radu
 */
public class Main {
    //Aplicatia va cere identificarea la fiecare rulare.
    public static void main(String [] args){
        SignInFrame frame = new SignInFrame();
        
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
}
