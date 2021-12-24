package com.mycompany.dotplotter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

public class RandomDNAsequences {
    
   
    
 /*   public static String create(){
        System.out.println("Set length of DNA sequence: ");
        Scanner input_x = new Scanner(System.in);
        int sequence_length_x = input_x.nextInt();
        String X = create(sequence_length_x);
        return X;
    }*/
    public static String create(int n){
                
        String Bases = "ATGC";
        StringBuilder seq = new StringBuilder(n);
        
        for (int i = 0; i < n; i++) {
  
            // generate a random number between 0 to Bases variable length
            int index = (int)(Bases.length()* Math.random());
  
            // add Character one by one in end of seq
            seq.append(Bases.charAt(index));
        }

        return seq.toString();
    }    

    public static void save(String X){
        JFrame jf = new JFrame( "Dialog" );
        jf.setAlwaysOnTop( true ); 
        JFileChooser file = new JFileChooser();
        file.setCurrentDirectory(new File(System.getProperty("user.home")));
        file.setSelectedFile(new File("RandomSequence.txt"));
        file.setAcceptAllFileFilterUsed(false);
        file.addChoosableFileFilter(new FileNameExtensionFilter("*.txt files", "txt"));
        file.setDialogTitle("Save sequence");        
        int status = file.showSaveDialog(jf);
        jf.dispose();
        String filename = file.getSelectedFile().getAbsolutePath();
        try {
            if (status == JFileChooser.APPROVE_OPTION) {
                try (FileWriter fWriter = new FileWriter(filename)) {
                    fWriter.write(X);
                }
            }
            //String message = "File saved";
            //PopUp.show(message);
        else if (status == JFileChooser.CANCEL_OPTION) {
                System.out.println("File not saved.");
            }
        }
        catch (IOException e) {
            System.out.print(e.getMessage());
        }           
    }
  
}
