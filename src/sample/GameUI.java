package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

import static sample.Controller.HandleButtonClick;
import static sample.Controller.NextChallenge;


public class GameUI extends Thread {
    private static final double WIDTH = 540;
    private static final double HEIGHT = 400;
    public static final HBox scrollingArea = new HBox();

    protected java.awt.Label winner_Label;
    protected java.awt.Label char_1_Label;
    protected java.awt.Label char_2_Label;
    protected String nameInput;
    protected java.awt.Label score;

    public static int totalCount = 0;

    public static void display(String nameInput, String characterInput) {
        HighScoreDBManager highScoreDBManager = new HighScoreDBManager();
        FileManager fileManager = new FileManager();
        //Set up window
        Stage window = new Stage();
        window.setTitle("GamePlay Match-ups");

        BorderPane border = new BorderPane();
        Scene scene = new Scene(border, WIDTH, HEIGHT);
        final HBox hb_Top = new HBox(10);
        hb_Top.setPadding(new Insets(10, 10, 10, 10));
        final VBox vb_Left = new VBox(20);
        vb_Left.setPadding(new Insets(50, 10, 20, 10));
        final VBox vb_Center = new VBox();
        vb_Center.setPadding(new Insets(50, 10, 20, 10));
        final VBox vb_Right = new VBox();
        vb_Right.setPadding(new Insets(50, 10, 20, 10));
        //final HBox hb_Bottom = new HBox();

        border.setTop(hb_Top);
        border.setLeft(vb_Left);
        border.setCenter(vb_Center);
        border.setRight(vb_Right);
        border.setBottom(scrollingArea);

        //Add controls
        Label name_Label = new Label("Name: ");
        Label name = new Label();
        name.setText(nameInput);
        name.setPrefWidth(120);

        Label character_Label = new Label("Character: ");
        Label character = new Label();
        character.setText(characterInput);
        character.setPrefWidth(100);

        Label score_Label = new Label("Score: ");
        Label score = new Label();
        score.setPrefWidth(50);
        //Add controls to layout components
        hb_Top.getChildren().addAll(name_Label, name, character_Label, character, score_Label, score);

        //Add controls
        ArrayList<String> player = fileManager.readFromFile();
        Label char_1_Label = new Label(player.get(0));
        Label vs_Label = new Label("Vs");
        Label char_2_Label = new Label(player.get(1));
        Label winner_Label = new Label("Answer");
        winner_Label.setAlignment(Pos.CENTER);
        Label correctOrNot = new Label(" ");


        //Align and space layout components
        vb_Left.setAlignment(Pos.TOP_CENTER);
        vb_Center.setAlignment(Pos.TOP_CENTER);
        vb_Right.setAlignment(Pos.TOP_CENTER);
        vb_Left.setSpacing(44);
        vb_Center.setSpacing(50);
        vb_Right.setSpacing(40);
        GameFlowController gameFlowController = new GameFlowController(score_Label, player, char_1_Label, vs_Label, char_2_Label, winner_Label, correctOrNot).invoke();
        Button char_1_btn = gameFlowController.getChar_1_btn();
        Button char_2_btn = gameFlowController.getChar_2_btn();


        //Button used for simulating text file printing
        Button print_btn = new Button("Print Scores");

        print_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    fileManager.openFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Record record = new Record(nameInput, Double.parseDouble(score.getText()));
                highScoreDBManager.callDB();
                highScoreDBManager.insertRecord(record);
                try {
                    System.out.println("Database: " + highScoreDBManager.findAllRecords());
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

        ScrollingQuotes s = new ScrollingQuotes(10000, "Scrolling Thread");
        s.run();

        Label phantom_Label = new Label(" ");

        //Add controls to their parent layout component
        vb_Left.getChildren().addAll(char_1_Label, char_1_btn);
        vb_Center.getChildren().addAll(vs_Label, winner_Label, correctOrNot);
        vb_Right.getChildren().addAll(char_2_Label, char_2_btn, phantom_Label, print_btn);

        //Styling
        scene.getStylesheets().add("BattleSetUp.css");
        char_1_Label.setId("label_LG");
        vs_Label.setId("label_LG");
        char_2_Label.setId("label_LG");
        winner_Label.setId("label_LG");
        correctOrNot.setId("label_LG");
        scrollingArea.setId("scroll_Area");

        //Display the window
        window.setScene(scene);
        window.show();

    }


    private static class GameFlowController {
        private Label score_label;
        private ArrayList<String> player;
        private Label char_1_label;
        private Label vs_label;
        private Label char_2_label;
        private Label winner_label;
        private Label correctOrNot;
        private Button char_1_btn;
        private Button char_2_btn;

        public GameFlowController(Label score_Label, ArrayList<String> player, Label char_1_Label, Label vs_Label, Label char_2_Label, Label winner_Label, Label correctOrNot) {
            score_label = score_Label;
            this.player = player;
            char_1_label = char_1_Label;
            vs_label = vs_Label;
            char_2_label = char_2_Label;
            winner_label = winner_Label;
            this.correctOrNot = correctOrNot;
        }

        public Button getChar_1_btn() {
            return char_1_btn;
        }

        public Button getChar_2_btn() {
            return char_2_btn;
        }

        public GameUI.GameFlowController invoke() {
            //Skeleton event handlers for buttons
            char_1_btn = new Button("Choose me");
            NextChallenge(player.get(totalCount * 3),player.get(totalCount *3 +1), char_1_label, char_2_label);
            char_1_btn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    HandleButtonClick(player, char_1_label, char_2_label, 0, score_label, winner_label,correctOrNot, vs_label);
                }
            });

            char_2_btn = new Button("Choose me");
            char_2_btn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {

                    HandleButtonClick(player, char_1_label, char_2_label,  1, score_label, winner_label,correctOrNot, vs_label);
                }
            });
            return this;
        }
    }
}
