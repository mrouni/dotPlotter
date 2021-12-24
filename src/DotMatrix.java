package com.mycompany.dotplotter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;


public class DotMatrix {
    
    private static String x;
    private static String y;
    private final static ArrayList<ArrayList<Character>> dSequences = new ArrayList<>();
    private final static ArrayList<ArrayList<Character>> invertSequences = new ArrayList<>();   
    private final static ArrayList<ArrayList<Integer>> dIndex = new ArrayList<>();
    private final static ArrayList<ArrayList<Integer>> invertIndex = new ArrayList<>();
    
    private static ArrayList<ArrayList<Character>> kSequences = new ArrayList<>();
    private static ArrayList<ArrayList<Character>> invertKsequences = new ArrayList<>();
    private final static ArrayList<ArrayList<Character>> maxKsequences = new ArrayList<>();
    
    private static int [][] mainD;
    private static int [][] kD; 
    private static Integer len;
    
    public static int[][] create(String X, String Y){
       
        
       int sequence_length_x = X.length();
       int sequence_length_y = Y.length();
        
        
        
       int sequence_min = Math.min(sequence_length_x, sequence_length_y);
       int sequence_max = Math.max(sequence_length_x, sequence_length_y);
       
       
       if (sequence_max == sequence_length_x){
           String temp;
           temp = X;
           X = Y;
           Y = temp;
       }
       
    
       int [][] D = new int[sequence_min][sequence_max];

        for(int i=0; i<sequence_min; i++){
           char xx;
           xx = X.charAt(i);
           for(int j=0; j<sequence_max; j++){
               char yy;
               yy = Y.charAt(j);
               if(xx==yy){
                   D[i][j] = 1;
               }
               else
                   D[i][j] = 0;
           }
       }

        mainD = D;
        setXY(X,Y);
        return D;

    }
    public static void plot(int [][] D){
        String X = x;
        String Y = y;
        int sequence_max = Y.length();
        int sequence_min = X.length();
        
        System.out.print(' ');
        System.out.print(' ');
        for(int i=0; i<sequence_max; i++){
            System.out.print(Y.charAt(i));
            System.out.print(' ');
        }
        System.out.println();
        //System.out.println(Y);
        for(int i=0; i<sequence_min; i++){
            System.out.print(X.charAt(i));
            System.out.print(' ');
            for (int j=0; j<sequence_max; j++){
                char marker = 'X';   // 1 as int for square, 'o' for "scatter"
                if(D[i][j]==1){
                    System.out.print(marker);
                    System.out.print(' ');
                }
                else if(D[i][j]==0) {
                    System.out.print(' ');
                    System.out.print(' ');
                }
                //System.out.print(D[i][j]);
                //System.out.print(' ');
            }
            System.out.println();
        }
    }
    public static void save(int [][] D) throws IOException{
       
       JFileChooser file = new JFileChooser();
        file.setCurrentDirectory(new File(System.getProperty("user.home")));
        file.setSelectedFile(new File("DotMatrix.txt"));
        file.setAcceptAllFileFilterUsed(false);
        file.addChoosableFileFilter(new FileNameExtensionFilter("*.txt files", "txt"));
        file.setDialogTitle("Save dot matrix");
        int status = file.showSaveDialog(null); 
        String filename = file.getSelectedFile().getAbsolutePath();
        FileWriter fWriter;
        try
        {   
            if (status == JFileChooser.APPROVE_OPTION) {
                fWriter = new FileWriter(filename);

                int sequence_max = D[0].length;
                int sequence_min = D.length;
                StringBuilder builder = new StringBuilder();

                builder.append(' ');
                builder.append(' ');
                for(int i=0; i<sequence_max; i++){
                    builder.append(y.charAt(i));
                    builder.append(' ');
                }
                builder.append('\n');


                for(int i = 0; i < sequence_min; i++){
                    builder.append(x.charAt(i));
                    builder.append(' ');
                   for(int j = 0; j < sequence_max; j++){
                      builder.append(D[i][j]).append(" ");
                /*      if(j < sequence_min - 1)//if this is not the last row element
                         builder.append(",");//then add comma (if you don't like commas you can use spaces)*/
                   }
                   builder.append("\n");
                }
               try (BufferedWriter writer = new BufferedWriter(fWriter)) {
                   writer.write(builder.toString());
               }
            }
            else if (status == JFileChooser.CANCEL_OPTION) {
                System.out.println("File not saved.");
            }    
            //String message = "File saved";
            //PopUp.show(message);
        }

        catch (FileNotFoundException e){
                System.out.println("Error: " + e.getMessage());			
        }
    
    }

    public static void findK(int[][] D, int k){
        
        len = k;
        
        ArrayList<ArrayList<Integer>> Diagonals;   
        Diagonals = findDiagonals(D);

        ArrayList<ArrayList<Integer>> InvertDiagonals;   
        InvertDiagonals = findInvert(D);        
        

        kSequences = findK(Diagonals,k);
        invertKsequences = findInvertK(InvertDiagonals,k);
 
    }
    public static ArrayList<ArrayList<Character>> findMax(ArrayList<ArrayList<Character>> allKsequences){
        
        int max = 0;
        int length = 0;
        for(int i = 0; i<allKsequences.size(); i++){
            ArrayList<Character> currentKsequence;
            currentKsequence = allKsequences.get(i);
            length = currentKsequence.size();
            if(length>=max){
                max = length;
            }           
        }
        //ArrayList<ArrayList<Character>> maxKsequences = new ArrayList<>();
        for(int i = 0; i<allKsequences.size(); i++){
            ArrayList<Character> currentKsequence;
            currentKsequence = allKsequences.get(i);
            length = currentKsequence.size();
            if(length == max){
                //System.out.println(currentKsequence);
                maxKsequences.add(currentKsequence);
            }           
        }
        
        return maxKsequences;
    }
    public static int [][] getkD(){
        return kD;
    }
    public static ArrayList<ArrayList<Character>> getKsequences(){
        return kSequences;
    }
    public static ArrayList<ArrayList<Character>> getInvertKsequences(){
        return invertKsequences;
    }
    

    public static void plotK(){
        String X = x;
        String Y = y;
        int sequence_max = Y.length();
        int sequence_min = X.length();
        
        System.out.print(' ');
        System.out.print(' ');
        for(int i=0; i<sequence_max; i++){
            System.out.print(Y.charAt(i));
            System.out.print(' ');
        }
        System.out.println();
        //System.out.println(Y);
        for(int i=0; i<sequence_min; i++){
            System.out.print(X.charAt(i));
            System.out.print(' ');
            for (int j=0; j<sequence_max; j++){
                char marker = 'X';   // 1 as int for square, 'o' for "scatter"
                if(kD[i][j]==1){
                    System.out.print(marker);
                    System.out.print(' ');
                }
                else if(kD[i][j]==0) {
                    System.out.print(' ');
                    System.out.print(' ');
                }
                //System.out.print(D[i][j]);
                //System.out.print(' ');
            }
            System.out.println();
        }
    }
    public static void saveKD(int [][] kD) throws IOException{
        JFrame jf = new JFrame( "Dialog" );
        jf.setAlwaysOnTop( true ); 
        JFileChooser file = new JFileChooser();
        file.setCurrentDirectory(new File(System.getProperty("user.home")));
        file.setSelectedFile(new File("kDotMatrix.txt"));
        file.setAcceptAllFileFilterUsed(false);
        file.addChoosableFileFilter(new FileNameExtensionFilter("*.txt files", "txt"));
        file.setDialogTitle("Save dot matrix");
        int status = file.showSaveDialog(jf); 
        jf.dispose();
        String filename = file.getSelectedFile().getAbsolutePath();
        FileWriter fWriter;
        try
        {   
            if (status == JFileChooser.APPROVE_OPTION) {
                fWriter = new FileWriter(filename);

                int sequence_max = kD[0].length;
                int sequence_min = kD.length;
                StringBuilder builder = new StringBuilder();

                builder.append(' ');
                builder.append(' ');
                for(int i=0; i<sequence_max; i++){
                    builder.append(y.charAt(i));
                    builder.append(' ');
                }
                builder.append('\n');


                for(int i = 0; i < sequence_min; i++){
                    builder.append(x.charAt(i));
                    builder.append(' ');
                   for(int j = 0; j < sequence_max; j++){
                      builder.append(kD[i][j]).append(" ");
                /*      if(j < sequence_min - 1)//if this is not the last row element
                         builder.append(",");//then add comma (if you don't like commas you can use spaces)*/
                   }
                   builder.append("\n");
                }
               try (BufferedWriter writer = new BufferedWriter(fWriter)) {
                   writer.write(builder.toString());
                   writer.close();
               } 
            }
            else if (status == JFileChooser.CANCEL_OPTION) {
                System.out.println("File not saved.");
                System.out.println("-----------------------------------");
                System.out.println();
            }
            //String message = "File saved";
            //PopUp.show(message);
        }

        catch (FileNotFoundException e){
                System.out.println("Error: " + e.getMessage());			
        }
    
    }

    public static void saveResults() throws IOException{
       
       JFileChooser file = new JFileChooser();
        file.setCurrentDirectory(new File(System.getProperty("user.home")));
        file.setSelectedFile(new File("DotReport.txt"));
        file.setAcceptAllFileFilterUsed(false);
        file.addChoosableFileFilter(new FileNameExtensionFilter("*.txt files", "txt"));
        file.setDialogTitle("Save results");
        int status = file.showSaveDialog(null); 
        String filename = file.getSelectedFile().getAbsolutePath();
        FileWriter fWriter;
        try
        {   
            if (status == JFileChooser.APPROVE_OPTION) {
                fWriter = new FileWriter(filename);

                int sequence_max = mainD[0].length;
                int sequence_min = mainD.length;
                StringBuilder builder = new StringBuilder();

                builder.append(' ');
                builder.append(' ');
                for(int i=0; i<sequence_max; i++){
                    builder.append(y.charAt(i));
                    builder.append(' ');
                }
                builder.append('\n');


                for(int i = 0; i < sequence_min; i++){
                    builder.append(x.charAt(i));
                    builder.append(' ');
                   for(int j = 0; j < sequence_max; j++){
                       if(mainD[i][j] == 0){
                            builder.append(" ").append(" ");
                       }
                       else{
                            builder.append("*").append(" ");                           
                       }
                /*      if(j < sequence_min - 1)//if this is not the last row element
                         builder.append(",");//then add comma (if you don't like commas you can use spaces)*/
                   }
                   builder.append("\n");
                }
                try(BufferedWriter writer = new BufferedWriter(fWriter)){
                    writer.write('\n');                                                            
                    writer.write("---------- dotPlotter 1.0 ----------");
                    writer.write('\n');
                    writer.write('\n');                                                            
                    writer.write("-------------------------------------"); 
                    writer.write('\n');                    
                    writer.write("            Dot Matrix, D            ");
                    writer.write('\n');                    
                    writer.write("-------------------------------------");
                    writer.write('\n');                    
                    writer.write(builder.toString());
                    writer.write('\n');
                    writer.write("Show subsequences equal or greater than length k = " + len.toString());
                    writer.write('\n');                    
                    //writer.write("-------------------------");
                    writer.write('\n');
                    
                    writer.write("-------------------------------------");
                    writer.write('\n');                    
                    writer.write("           Dot Matrix, kD            ");
                    writer.write('\n');                    
                    writer.write("-------------------------------------"); 
                    writer.write('\n');
                    
                    
                    StringBuilder builderK = new StringBuilder();

                    builderK.append(' ');
                    builderK.append(' ');
                    for(int i=0; i<sequence_max; i++){
                        builderK.append(y.charAt(i));
                        builderK.append(' ');
                    }
                    builderK.append('\n');


                    for(int i = 0; i < sequence_min; i++){
                        builderK.append(x.charAt(i));
                        builderK.append(' ');
                       for(int j = 0; j < sequence_max; j++){
                            if(kD[i][j] == 0){
                                 builderK.append(" ").append(" ");
                            }
                            else{
                                 builderK.append("*").append(" ");                           
                            }
                       }
                       builderK.append("\n");
                    }
                    writer.write(builderK.toString());
                    writer.write('\n');                    
                    writer.write("Main k sequences:");
                    //writer.write("-------------------------");                    
                    writer.write(kSequences.toString());
                    writer.write('\n'); 
                    writer.write('\n');                                        
                    writer.write("Invert k sequences:");
                    //writer.write("-------------------------");               
                    writer.write(invertKsequences.toString());
                    writer.write('\n');  
                    writer.write('\n');                                        
                    ArrayList<ArrayList<Character>> allKsequences = new ArrayList<>();
                    allKsequences.addAll(kSequences);
                    allKsequences.addAll(invertKsequences);               
                    writer.write("All k sequences:");
                    //writer.write("-------------------------");               
                    writer.write(allKsequences.toString());
                    writer.write('\n');   
                    writer.write('\n');                                        
                    writer.write("Max k sequences:");
                    //writer.write("-------------------------");               
                    writer.write(maxKsequences.toString());
                    
                }

              
            }
            else if (status == JFileChooser.CANCEL_OPTION) {
                System.out.println("File not saved.");
            }    
            //String message = "File saved";
            //PopUp.show(message);
        }

        catch (FileNotFoundException e){
                System.out.println("Error: " + e.getMessage());			
        }
    
    }
    
    
    private static ArrayList<ArrayList<Integer>> findDiagonals(int [][] D){
        
        int rows = D.length;
        int columns = D[0].length;
        
        String X = x;
        String Y = y;
        

        ArrayList<ArrayList<Integer>> Diagonals = new ArrayList<>();
        
        for(int row = rows-1; row>=0; row--){
            ArrayList<Integer> currentDiagonal = new ArrayList<>();
            ArrayList<Integer> currentIndex = new ArrayList<>();
            ArrayList<Character> currentSequence = new ArrayList<>();
            int j = row+1;
            for(int col = 0; col<=rows-row-1; col++){
                if (col==0){
                    currentDiagonal.add(D[row][col]);
                    currentIndex.add(row);
                    currentIndex.add(col);
                    //System.out.println("f: " + currentIndex);
                    if(D[row][col]==1)
                        currentSequence.add(X.charAt(row));
                    else
                        currentSequence.add(Y.charAt(col));

                }
                else{
                    currentDiagonal.add(D[j][col]);
                    currentIndex.add(j);
                    currentIndex.add(col);
                    //System.out.println("s: " + currentIndex);
                    if(D[j][col]==1)
                        currentSequence.add(X.charAt(j));
                    else
                        currentSequence.add(Y.charAt(col));                    
                    j++;
                }
            }            
            Diagonals.add(currentDiagonal);
            dSequences.add(currentSequence);
            dIndex.add(currentIndex);
        }

        for(int diag = 1; diag<columns; diag++){
            int row;
            ArrayList<Integer> currentDiagonal = new ArrayList<>();
            ArrayList<Integer> currentIndex = new ArrayList<>();            
            ArrayList<Character> currentSequence = new ArrayList<>();
            if ((rows+diag-1)<columns){
                for(int col = diag; col<=(rows+diag-1); col++){
                    row = col-diag;
                    currentDiagonal.add(D[row][col]);
                    currentIndex.add(row);
                    currentIndex.add(col);
                    if(D[row][col]==1)
                        currentSequence.add(X.charAt(row));
                    else
                        currentSequence.add(Y.charAt(col));
                }
                Diagonals.add(currentDiagonal);
                dSequences.add(currentSequence);
                dIndex.add(currentIndex);                
            }
            else{
                for(int col = diag; col<columns; col++){
                    row = col-diag;
                    currentDiagonal.add(D[row][col]);
                    currentIndex.add(row);
                    currentIndex.add(col);
                    if(D[row][col]==1)
                        currentSequence.add(X.charAt(row));
                    else
                        currentSequence.add(Y.charAt(col));
                }               
                Diagonals.add(currentDiagonal);
                dSequences.add(currentSequence);
                dIndex.add(currentIndex);
            }
        }
        return Diagonals;
    }
    private static ArrayList<ArrayList<Character>> findK(ArrayList<ArrayList<Integer>> Diagonals,int k){
               
        ArrayList<ArrayList<Character>> allKsequences = new ArrayList<>();
        Map<Integer,ArrayList<Integer>> index = new HashMap<>();
        
        kD = new int [x.length()][y.length()];
        for (int row = 0; row < x.length(); row++){
            for (int col = 0; col < y.length(); col++){
                kD[row][col] = 0; 
            }
        }
        
        for(int i = 0; i<Diagonals.size(); i++){
            int sum = 0;
            
            // Continue if size of diagonal < k
            if(Diagonals.get(i).size()<k){
                continue;
            }
            
            // Continue if sum of ones < k
            
            ArrayList<Integer> currentDiagonal;
            currentDiagonal = Diagonals.get(i);
            for(int j = 0; j < currentDiagonal.size(); j++){
                sum += currentDiagonal.get(j);
            }
            if(sum<k){
                //kIndex.get(i).clear();
                //kIndex.removeIf(p -> p.isEmpty());
                continue;
            }
            // Keep ones and their indices in each diagonal
            ArrayList<Integer> ones = new ArrayList<>();
            for(int j=0; j<currentDiagonal.size(); j++){
                if(currentDiagonal.get(j)== 1){
                    ones.add(j);
                }
            }
            index.put(i,ones);
        }
        index.entrySet().forEach(diagonal -> {
            ArrayList<Integer> diffs = new ArrayList<>();
            ArrayList<Integer> currentDiagonal;
            
            currentDiagonal = diagonal.getValue();

            // Diff indices of ones and find sequential ones
            
            for(int i = 0; i<currentDiagonal.size()-1; i++){
                int j = i+1;
                diffs.add(currentDiagonal.get(j)-currentDiagonal.get(i));
            }
            Integer count = 0;
            Integer start = 0;
            Integer i = 0;
            int flag = 1;
            Integer stop = 0;
            while(i<diffs.size()){
                if(diffs.get(i)==1){
                    if (count == 0){
                        start = i;
                        flag = 1;
                    }
                    count++;
                }
                else{
                    stop = i;
                    flag = 0;
                }
                if(count<k-1 && flag ==0){
                    count = 0;
                }
                if(i == (diffs.size()-1) && diffs.get(i)==1 && count>=(k-1)){
                    //System.out.println(diffs);
                    flag = 0;
                    stop = i+1;
                }
                int r;
                int c;
                if(count>=k-1 && flag == 0){
                    count = 0;
                    ArrayList<Character> currentKsequence = new ArrayList<>();
                    ArrayList<Integer> tempIndex = dIndex.get(diagonal.getKey());
                    ArrayList<Character> tempSeq;
                    tempSeq = dSequences.get(diagonal.getKey());
                    for(int j = start; j<stop+1; j++){
                        currentKsequence.add(tempSeq.get(currentDiagonal.get(j)));
                        //System.out.println("diagonal.getKey()" + diagonal.getKey() + " currentDiagonal.get() =  " + currentDiagonal.get(j));
                        //System.out.println(currentDiagonal);
                        //System.out.println(tempIndex);
                        r = 2*currentDiagonal.get(j);
                        c = r+1;
                        kD[tempIndex.get(r)][tempIndex.get(c)] = 1;
                        //System.out.println(tempIndex.get(r));
                        //System.out.println(tempIndex.get(c));
                    }
                    allKsequences.add(currentKsequence);
                }
                i++;
            }
        });
                
        return allKsequences;
    }

    private static ArrayList<ArrayList<Integer>> findInvert(int [][] D){
        int rows = D.length;
        int columns = D[0].length;
        
        String X = x;
        String Y = y;
        

        ArrayList<ArrayList<Integer>> Diagonals = new ArrayList<>();
        
        for(int col=0; col<rows; col++){
            ArrayList<Integer> currentDiagonal = new ArrayList<>();
            ArrayList<Integer> currentIndex = new ArrayList<>();
            ArrayList<Character> currentSequence = new ArrayList<>();            
            int j = col-1;
            for (int row = 0; row<=col; row++){
                if(row == 0){
                    currentDiagonal.add(D[row][col]);
                    currentIndex.add(row);
                    currentIndex.add(col);
                    if(D[row][col]==1)
                        currentSequence.add(X.charAt(row));
                    else
                        currentSequence.add(Y.charAt(col));                    
                }
                else{
                    currentDiagonal.add(D[row][j]);
                    currentIndex.add(row);
                    currentIndex.add(j);
                    if(D[row][j]==1)
                        currentSequence.add(X.charAt(row));
                    else
                        currentSequence.add(Y.charAt(j));                    
                    j--;
                }
                
            }
            Diagonals.add(currentDiagonal);
            invertSequences.add(currentSequence);            
            invertIndex.add(currentIndex); 
        }
        
        for(int col = rows; col<columns; col++ ){
            ArrayList<Integer> currentDiagonal = new ArrayList<>();
            ArrayList<Integer> currentIndex = new ArrayList<>();
            ArrayList<Character> currentSequence = new ArrayList<>();            
            int j = col-1;
            for(int row = 0; row<rows; row++){
                if(row == 0){
                    currentDiagonal.add(D[row][col]);
                    currentIndex.add(row);
                    currentIndex.add(col);
                    if(D[row][col]==1)
                        currentSequence.add(X.charAt(row));
                    else
                        currentSequence.add(Y.charAt(col));                     
                }
                else{
                    currentDiagonal.add(D[row][j]);
                    currentIndex.add(row);
                    currentIndex.add(j);
                    if(D[row][j]==1)
                        currentSequence.add(X.charAt(row));
                    else
                        currentSequence.add(Y.charAt(j));                    
                    j--;  
                }
            }
            Diagonals.add(currentDiagonal);
            invertSequences.add(currentSequence);
            invertIndex.add(currentIndex);
        }        

        int row;
        int col = columns-1;
        int j;
        for(int diag = 1; diag<rows; diag++){
            ArrayList<Integer> currentDiagonal = new ArrayList<>();
            ArrayList<Integer> currentIndex = new ArrayList<>();
            ArrayList<Character> currentSequence = new ArrayList<>(); 
            j = col-1;
            for(row = diag; row<rows; row++){
                if(row==diag){
                    currentDiagonal.add(D[row][col]);
                    currentIndex.add(row);
                    currentIndex.add(col);
                    if(D[row][col]==1)
                        currentSequence.add(X.charAt(row));
                    else
                        currentSequence.add(Y.charAt(col));                                 
                }
                else{
                    currentDiagonal.add(D[row][j]);
                    currentIndex.add(row);
                    currentIndex.add(j);
                    if(D[row][j]==1)
                        currentSequence.add(X.charAt(row));
                    else
                        currentSequence.add(Y.charAt(j));                    
                    j--;                                 
                }
            }
            Diagonals.add(currentDiagonal);
            invertSequences.add(currentSequence);
            invertIndex.add(currentIndex);
        }
 

       
        
        //System.out.println(Diagonals);
        return Diagonals;
    }
    private static ArrayList<ArrayList<Character>> findInvertK(ArrayList<ArrayList<Integer>> Diagonals,int k){
               
        ArrayList<ArrayList<Character>> allKsequences = new ArrayList<>();
        Map<Integer,ArrayList<Integer>> index = new HashMap<>();
        
        /*kD = new int [x.length()][y.length()];
        for (int row = 0; row < x.length(); row++){
            for (int col = 0; col < y.length(); col++){
                kD[row][col] = 0; 
            }
        }*/
        
        for(int i = 0; i<Diagonals.size(); i++){
            int sum = 0;
            
            // Continue if size of diagonal < k
            if(Diagonals.get(i).size()<k){
                continue;
            }
            
            // Continue if sum of ones < k
            
            ArrayList<Integer> currentDiagonal;
            currentDiagonal = Diagonals.get(i);
            for(int j = 0; j < currentDiagonal.size(); j++){
                sum += currentDiagonal.get(j);
            }
            if(sum<k){
                //kIndex.get(i).clear();
                //kIndex.removeIf(p -> p.isEmpty());
                continue;
            }
            // Keep ones and their indices in each diagonal
            ArrayList<Integer> ones = new ArrayList<>();
            for(int j=0; j<currentDiagonal.size(); j++){
                if(currentDiagonal.get(j)== 1){
                    ones.add(j);
                }
            }
            index.put(i,ones);
        }
        index.entrySet().forEach(diagonal -> {
            ArrayList<Integer> diffs = new ArrayList<>();
            ArrayList<Integer> currentDiagonal;
            
            currentDiagonal = diagonal.getValue();

            // Diff indices of ones and find sequential ones
            
            for(int i = 0; i<currentDiagonal.size()-1; i++){
                int j = i+1;
                diffs.add(currentDiagonal.get(j)-currentDiagonal.get(i));
            }
            Integer count = 0;
            Integer start = 0;
            Integer i = 0;
            int flag = 1;
            Integer stop = 0;
            while(i<diffs.size()){
                if(diffs.get(i)==1){
                    if (count == 0){
                        start = i;
                        flag = 1;
                    }
                    count++;
                }
                else{
                    stop = i;
                    flag = 0;
                }
                if(count<k-1 && flag ==0){
                    count = 0;
                }
                if(i == (diffs.size()-1) && diffs.get(i)==1 && count>=(k-1)){
                    //System.out.println(diffs);
                    flag = 0;
                    stop = i+1;
                }
                int r;
                int c;
                if(count>=k-1 && flag == 0){
                    count = 0;
                    ArrayList<Character> currentKsequence = new ArrayList<>();
                    ArrayList<Integer> tempIndex = invertIndex.get(diagonal.getKey());
                    ArrayList<Character> tempSeq;
                    tempSeq = invertSequences.get(diagonal.getKey());
                    for(int j = start; j<stop+1; j++){
                        currentKsequence.add(tempSeq.get(currentDiagonal.get(j)));
                        //System.out.println("diagonal.getKey()" + diagonal.getKey() + " currentDiagonal.get() =  " + currentDiagonal.get(j));
                        //System.out.println(currentDiagonal);
                        //System.out.println(tempIndex);
                        r = 2*currentDiagonal.get(j);
                        c = r+1;
                        kD[tempIndex.get(r)][tempIndex.get(c)] = 1;
                        //System.out.println(tempIndex.get(r));
                        //System.out.println(tempIndex.get(c));
                    }
                    allKsequences.add(currentKsequence);
                }
                i++;
            }
        });
                
        return allKsequences;
    }

 
    private static void setXY(String X, String Y){
        x = X;
        y = Y;
    } 

}
