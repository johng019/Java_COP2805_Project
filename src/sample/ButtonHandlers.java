package sample;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

public class ButtonHandlers extends GameUI{
    GameUI gameUI = new GameUI();
    FileManager fileManager = new FileManager();
    ArrayList<String> player = fileManager.readFromFile();
    final double[] totalCount = {0};
    final double[] correctCount = {0};
    final double[] myScore = {0};


    public void character_1_Button(){
        FileManager fileManager = new FileManager();
        Stack<Map> st = new Stack<>();
        ArrayList<String> player = fileManager.readFromFile();
        if(totalCount[0]> 0 && totalCount[0] < 11) {
            //Setting Timestamp time and format.
            Date date = new Date();
            Timestamp ts = new Timestamp(date.getTime());
            SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss");
            //Setting up HashMap.
            Map<String, String> myMap1 = new HashMap();
            myMap1.put(formatter.format(ts), winner_Label.getText());
            //Print out Time Stamp and winner with each selection.
            System.out.println(myMap1);
        }

        if(totalCount[0] < 9) {
            int i = 2;
            String w = player.get(i);
            gameUI.winner_Label.setText(w +   "\n Correct");
            String c1 = player.get(i + 1);
            String c2 = player.get(i + 2);
            gameUI.char_1_Label.setText(c1);
            gameUI.char_2_Label.setText(c2);
            player.remove(0);
            player.remove(1);
            player.remove(2);
            totalCount[0] += 1;
            correctCount[0] += 1;
            myScore[0] = correctCount[0] / totalCount[0];
            gameUI.score.setText(String.valueOf(myScore[0] *= 100));
        }
        else if(totalCount[0] == 9){
            int i = 2;
            String w = player.get(i);
            winner_Label.setText(w + "\n Correct");
            player.remove(0);
            totalCount[0] += 1;
            correctCount[0] += 1;
            myScore[0] = correctCount[0] / totalCount[0];
            score.setText(String.valueOf(myScore[0] *= 100));
        }
        else if(totalCount[0] == 10){
            int i = 0;
            String w = player.get(i);
            winner_Label.setText(w + "\n Correct");
            totalCount[0] += 1;
            correctCount[0] += 1;
            fileManager.writeToFile(nameInput,myScore[0] );
        }
        else if(totalCount[0] > 10){
            winner_Label.setText("Game Over!");
            char_1_Label.setText(" ");
            char_2_Label.setText(" ");
        }
    }

    public void character_2_button(){
        FileManager fileManager = new FileManager();
        ArrayList<String> player = fileManager.readFromFile();
        if(totalCount[0] > 0 && totalCount[0] < 11) {
            Stack<Map> st = new Stack<>();
            //Setting Timestamp time and format.
            Date date = new Date();
            Timestamp ts = new Timestamp(date.getTime());
            SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss");
            //Setting up HashMap.
            Map<String, String> myMap1 = new HashMap();
            myMap1.put(formatter.format(ts), winner_Label.getText());
            //Print out Time Stamp and winner with each selection.
            System.out.println(myMap1);
        }

        if(totalCount[0] < 9) {
            int i = 2;
            String w = player.get(i);
            winner_Label.setText(w + "\n Wrong!");
            String c1 = player.get(i + 1);
            String c2 = player.get(i + 2);
            char_1_Label.setText(c1);
            char_2_Label.setText(c2);
            player.remove(0);
            player.remove(1);
            player.remove(2);
            totalCount[0]++;
            myScore[0] = correctCount[0] / totalCount[0];
            score.setText(String.valueOf(myScore[0] *= 100));
        }
        else if(totalCount[0] == 9 ){
            int i = 1;
            String w = player.get(i);
            winner_Label.setText(w + "\n Wrong!");
            player.remove(0);
            totalCount[0]++;
            myScore[0] = correctCount[0] / totalCount[0];
            score.setText(String.valueOf(myScore[0] *= 100));
        }
        else if(totalCount[0] == 10){
            int i = 0;
            String w = player.get(i);
            winner_Label.setText(w + "\n Wrong!");
            totalCount[0]++;
            fileManager.writeToFile(gameUI.nameInput,myScore[0] );
        }
        else{
            winner_Label.setText("Game Over!");
            char_1_Label.setText(" ");
            char_2_Label.setText(" ");
        }
    }

    public void printScores(){
        HighScoreDBManager highScoreDBManager = new HighScoreDBManager();
        try {
            fileManager.openFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Record record = new Record(nameInput,Double.parseDouble(score.getText()));
        highScoreDBManager.callDB();
        highScoreDBManager.insertRecord(record);
        try {
            System.out.println("Database: " + highScoreDBManager.findAllRecords());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
