package sample;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.awt.Desktop;
import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileManager {

    public void writeToFile(String user, Double score){
        //sets file to be written into - to a string variable.
        String path = "src/HighScoresFile.txt";
        try{//Wrap in try catch for exception handling.
            //Create FileWriter object.
            FileWriter fr = new FileWriter(path,true);
            fr.write( score + "   " + user + "\n");

            fr.close();//close the fileWriter object.
        }
        //File not found Exception Block.
        catch (FileNotFoundException e){
            System.out.println(e);
        }
        //IO Exception Block.
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Set a string to the file to be read from
    String path = "src/Challengers.txt";

    public ArrayList<String> readFromFile(){
        //Creates an ArrayList of type Integer.
       ArrayList<String> results = new ArrayList<>();
        //Wrap code in a try/ catch to help with exception handling.
        try{
            //Create a object of Scanner class
            Scanner s = new Scanner(new FileReader(path));
            //While loop for iterating thru the file.
            // Reads the data and adds it to the ArrayList.
                while(s.hasNext()) {
                    String c1  = s.next();
                    String c2 =  s.next();
                    String w = s.next();
                    results.add(c1);
                    results.add(c2);
                    results.add(w);
                }return results;

        }//File not found exception block.
        catch(FileNotFoundException e){
            System.out.println(e);
        }
        return results;//default return.
    }

    public void openFile() throws IOException {
        Desktop dt = Desktop.getDesktop();
        File file = new File("src/HighScoresFile.txt");
        dt.open(file);
    }
}
