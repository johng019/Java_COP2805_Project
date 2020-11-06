package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

import static sample.GameUI.totalCount;

public class Controller {


    private static int correctCount = 0;
    private static double myScore = 0;

    protected static void HandleButtonClick(ArrayList<String> player, Label char_1_Label, Label char_2_Label, int selectedButton, Label scoreLabel, Label winner_Label, Label correctOrNot, Label vs_Label) {

        if (totalCount >= (player.size() / 3)-1) {
            totalCount++;
            myScore = calculateScore(totalCount,correctCount);
            //updateScore(myScore,scoreLabel);
            GameOver(char_1_Label, char_2_Label,vs_Label);

        } else {

            if(IsWinner(player, selectedButton))
            {
                setWinner(player,winner_Label,correctOrNot,"Correct");
                totalCount++;   // Advancing the totalCount here. If we advance it before determining the winner we are comparing the next line.
                // The scoring must take into account the actual number of tries regardless of win/lose.
                myScore = calculateScore(totalCount,++correctCount);

                getUserChoice(winner_Label);
            } else {
                setWinner(player,winner_Label,correctOrNot,"Wrong");
                totalCount++;
                myScore = calculateScore(totalCount,correctCount);

                getUserChoice(winner_Label);
            }
            // here we want to advance to the next challenger, which we have done by advancing totalCount regardless of prior win/lose.
            NextChallenge(player.get(totalCount * 3),player.get(totalCount *3 +1),char_1_Label,char_2_Label);
        }

        updateScore(myScore,scoreLabel);


    }

    private static void setWinner(ArrayList<String> player, Label winner_Label, Label correctOrNot, String status) {
        winner_Label.setText(player.get(totalCount * 3 + 2));
        correctOrNot.setText(status);
    }

    public static void NextChallenge(String leftPlayerName, String rightPlayerName,Label leftPlayer, Label rightPlayer){
        leftPlayer.setText(leftPlayerName);
        rightPlayer.setText(rightPlayerName);
    }

    public static boolean IsWinner(ArrayList<String> player, int buttonId) {
        if (player.get(buttonId + totalCount * 3).equals(player.get(totalCount * 3 + 2))) {
            return true;
        } else {
            return false;
        }
    }

    private static double calculateScore(double totalCount, double correctCount) {
        return correctCount / totalCount;
    }

    private static void updateScore(double myScore, Label score) {
        score.setText(String.format("%.2f",myScore *= 100.0f)+"%");
    }

    private static void GameOver(Label player1, Label player2,Label vs_Label) {
        setEmptyChallengers(player1, player2);
        vs_Label.setText("GAME OVER");
    }

    private static void setEmptyChallengers(Label char_1_Label, Label char_2_Label) {
        char_1_Label.setText(" ");
        char_2_Label.setText(" ");
    }

    private static void getUserChoice(Label winner_Label) {
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
}

