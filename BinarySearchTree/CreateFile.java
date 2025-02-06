package cs342;

import java.io.File;
import java.io.IOException;

public class CreateFile {
	public static void main(String[] args) {
		 try {
		   File myObj = new File("InputData.txt");
		   if (myObj.createNewFile()) {
		     System.out.println("File created: " + myObj.getName());
		   } else {
		     System.out.println("File already exists.");
		   }
		   System.out.println("Absolute path: " + myObj.getAbsolutePath()); // Get file path
		 } catch (IOException e) {
		   System.out.println("An error occurred.");
		   e.printStackTrace();
		 }
	}
}
