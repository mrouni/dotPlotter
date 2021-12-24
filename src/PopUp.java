package com.mycompany.dotplotter;

import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class PopUp{
    public static void show(String message) {
        JFrame jFrame = new JFrame();
        JOptionPane.showMessageDialog(jFrame, message);
    }
}