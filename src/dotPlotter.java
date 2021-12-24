package com.mycompany.dotplotter;


import java.lang.*;
import java.util.*;
import java.io.*;


public class dotPlotter {
    public static void main(String[] args) throws FileNotFoundException, IOException{

        System.out.println();
        System.out.println();        
        
        System.out.println("-----------------------------------");
        System.out.println("Welcome to dotPlotter");
        System.out.println("program developed by M.A.Rouni");
        System.out.println("-----------------------------------");
        System.out.println();


        String X = new String(); 
        String Y = new String(); 
        String [] sequences;
        Scanner scan = new Scanner(System.in);
       
        
        if(args.length == 1){
           System.out.println("Preparing to generate random DNA sequences");
           System.out.println("----------------------------------------------");  
           System.out.println("Set sequence X length: ");
           int sequence_x_length = scan.nextInt();
           System.out.println("-------------------------");
           X = RandomDNAsequences.create(sequence_x_length);
           System.out.println("Generated X sequence");
           System.out.println();
           System.out.println(X);
           System.out.println("-------------------------");                  
           System.out.println("Save X sequence");
           RandomDNAsequences.save(X);
           
           
           
           System.out.println("-------------------------");           
           System.out.println("Set sequence Y length: ");
           int sequence_y_length = scan.nextInt();
           System.out.println("-------------------------");
           Y = RandomDNAsequences.create(sequence_y_length);
           System.out.println("Generated Y sequence");
           System.out.println();           
           System.out.println(Y);
           System.out.println("-------------------------");                  
           System.out.println("Save Y sequence");
           RandomDNAsequences.save(Y);           
        }
        
        else if(args.length == 0){
            System.out.println("Import DNA sequences");
            sequences = ImportFiles.select();
            //System.out.println(sequences[1]);            
            X = sequences[0];
            Y = sequences[1];   
        }



       
       int [][] D;
       
       System.out.println("-------------------------");
       System.out.println("Generating dot matrix D...");
       System.out.println();       
       D = DotMatrix.create(X,Y);
       DotMatrix.plot(D);
       System.out.println("-------------------------");
       //System.out.println("Save dot matrix D");
       //DotMatrix.save(D);
       
       
      
       //System.out.println("-------------------------");
       System.out.println("Set k length: ");
       int k = scan.nextInt();
       DotMatrix.findK(D, k);

        
       //System.out.println(diagonals);
       
       ArrayList<ArrayList<Character>> kSequences = new ArrayList<>();

       
       ArrayList<ArrayList<Character>> invertKsequences = new ArrayList<>();

       

       kSequences = DotMatrix.getKsequences();
       System.out.println("-------------------------");
       System.out.println("Main K sequences");
       System.out.println();       
       System.out.println(kSequences);

       
       invertKsequences = DotMatrix.getInvertKsequences();
       System.out.println("-------------------------");
       System.out.println("Invert K sequences");
       System.out.println();              
       System.out.println(invertKsequences);        

       ArrayList<ArrayList<Character>> allKsequences = new ArrayList<>();
       allKsequences.addAll(kSequences);
       allKsequences.addAll(invertKsequences);
       System.out.println("-------------------------");       
       System.out.println("All K sequences");
       System.out.println();              
       System.out.println(allKsequences);

       ArrayList<ArrayList<Character>> maxKsequences; 
       
       System.out.println("-------------------------");       
       System.out.println("Max k sequences");
       System.out.println();              
       maxKsequences = DotMatrix.findMax(allKsequences);
       System.out.println(maxKsequences);

        
       int [][] kD = DotMatrix.getkD();
       System.out.println("-------------------------");
       System.out.println("Dot matrix kD, of k length sequences:");
       System.out.println();       
       DotMatrix.plot(kD);
       
       System.out.println();       
       System.out.println("-------------------------");
       //System.out.println("Save dot matrix kD");
       //DotMatrix.saveKD(kD);
       
       
       System.out.println();
       System.out.println("Save results");
       DotMatrix.saveResults();
       System.out.println("-------------------------");
    }
}