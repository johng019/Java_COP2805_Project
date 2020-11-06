package sample;

import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.util.Duration;

import javax.print.DocFlavor;
import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import static java.lang.Thread.sleep;
import static javax.swing.plaf.basic.BasicGraphicsUtils.drawString;

public class ScrollingQuotes implements Runnable{
    //private final double WIDTH = 540;

    //setting up the Constructor
    int refreshRate;
    String name;

    public ScrollingQuotes(int refreshRate, String name){
        this.refreshRate = refreshRate;
        this.name = name;
    }

    //Set a string to the file to be read from
    String path = "src\\quotes.txt";

        public void run() {
            GameUI gs = new GameUI();
            //Wrap code in a try/ catch to help with exception handling.
            try {
                Random r = new Random();
                //Read all lines from the input file
                Scanner s = new Scanner(new FileReader(path));
                StringBuilder sb = new StringBuilder();
                //sets first Quote to begin at specified time
                if (name.equals("Scrolling Thread")) {
                    refreshRate = 60000;
                    while(s.hasNextLine()) {
                        sb.append(s.nextLine() + "                                                                  ");
                    }
                    String newString = sb.toString();
                    scrollingText(gs.scrollingArea, newString);
                }
                //Exceptions handling
            } catch (FileNotFoundException e) {
                System.out.println(e);
            }
        }

    //method for the scrolling text component
    public void scrollingText(HBox parent, String s){
            Text scrollingText = new Text(s);
            scrollingText.setText(s);
            parent.getChildren().add(scrollingText);
            scrollingText.setLayoutX(0);
            scrollingText.setLayoutY(40);
            scrollingText.setWrappingWidth(parent.getMaxWidth());
            TranslateTransition tt = new TranslateTransition(Duration.millis(90000), scrollingText);
            tt.setFromX(scrollingText.getWrappingWidth() + 550);
            tt.setToX(0 - scrollingText.getWrappingWidth() - 5000);
            tt.setCycleCount(Timeline.INDEFINITE);
            tt.setAutoReverse(false);
            tt.play();

    }
}

