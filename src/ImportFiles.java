package com.mycompany.dotplotter;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;


public class ImportFiles {
       
    private final static String [] sequences = new String[2];
    private static String X = new String();
    private static String Y = new String();
    
    
    public static  String [] select()throws FileNotFoundException{
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(true);
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("*.txt files", "txt"));
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        fileChooser.setDialogTitle("Choose sequences");
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File[] files = fileChooser.getSelectedFiles();
            if(files.length>2 || files.length<2){
                String message = "Error! Please choose exactly 2 text files containing the sequences to be compared";
                PopUp.show(message);
                System.out.println("Please choose 2 files containing the sequences to be compared");
                System.out.println("Exiting...");
                System.out.println("-----------------------------------");               
                System.exit(1);
            }
            for(int i = 0; i<files.length; i++){
                Scanner sc = new Scanner(files[i]);

                while (sc.hasNextLine()){
                    if(i==0){
                        X = sc.nextLine();
                        sequences[i]=X;
                    }
                    if(i==1){
                        Y = sc.nextLine();
                        sequences[i]=Y;
                    }
                }
            }
        }
        else{
            System.out.println();
            String message = "Operation canceled by user";
            PopUp.show(message);
            System.out.println("Operation canceled by user");
            System.out.println("Exiting...");
            System.out.println("-----------------------------------");
            System.out.println();
            System.exit(1);
        }        
        return sequences;
    }
     
    
}
