package testPackage;

import java.util.*;
import java.io.*;

public class PrintOutHittingFiles {

	public static void main(String[] args) throws Exception {
		 // Create a File instance
	    File file = new java.io.File("hitting.txt");

	    // Create a Scanner for the file
	    Scanner input = new Scanner(file);
	    
	    // Read data from a file
	    while (input.hasNext()) {
	      String firstName = input.next();
	      String lastName = input.next();
	      String firstAndLast = firstName+ " " +lastName;
	      String position = input.next();
	      String team = input.next();
	      
	      int g = input.nextInt();
	      int ab = input.nextInt();
	      int r = input.nextInt();
	      
	      int h = input.nextInt();
	      int twoB = input.nextInt();
	      int threeB = input.nextInt();
	      
	      int hr = input.nextInt();
	      int rbi = input.nextInt();
	      int bb = input.nextInt();
	      
	      int so = input.nextInt();
	      int sb = input.nextInt();
	      int cs = input.nextInt();
	      
	      float avg = input.nextFloat();
	      float obp = input.nextFloat();
	      float slg = input.nextFloat();
	      
	      float ops = input.nextFloat();
	      
	     
	      System.out.println(
	        firstAndLast + " " + position + " " +  team + " " 
	      + g + " " + ab + " " + r + " " + h + " " + twoB + " " + threeB + " " + hr + " " +
	      rbi + " " + bb + " " + so + " " + sb + " " + cs + " " + avg +  " " + obp + " " 
	      + slg + " " +  ops );
	    }

	    // Close the file
	    input.close();
	  }
	} 

