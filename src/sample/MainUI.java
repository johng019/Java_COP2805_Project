package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Optional;

public class MainUI extends Application {

        private static final double WIDTH = 425;
        private static final double HEIGHT = 300;

        @Override
        public void start(Stage stage) throws Exception{

            //set up the window
            Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
            Stage window = stage;
            window.setTitle("SignIn / Select Character");
            BorderPane border = new BorderPane();
            Scene scene = new Scene(border,WIDTH,HEIGHT);

            //Define component padding
            HBox hbTop = new HBox(20);
            hbTop.setPadding(new Insets(40,10,40,10));
            VBox vbLeft = new VBox(20);
            vbLeft.setPadding(new Insets(10,10,10,30));
            VBox vbCenter = new VBox(20);
            vbCenter.setPadding(new Insets(10,10,10,10));
            VBox vbRight = new VBox(20);
            vbRight.setPadding(new Insets(10,30,10,10));

            //Setup the window layout of components
            border.setTop(hbTop);
            border.setLeft(vbLeft);
            border.setCenter(vbCenter);
            border.setRight(vbRight);

            //Add the controls
            Label name = new Label("Name:");
            TextField nameField = new TextField();

            Label  yoda  = new Label("Yoda");
            Label obiWan = new Label("Obi-Wan");
            Label mickey = new Label("Mickey");

            Button yodaButton = new Button("Click Me");
            Button obiWanButton = new Button("Click Me");
            Button mickeyButton = new Button("Click Me");

            //Position the controls inside the parent components
            hbTop.setAlignment(Pos.CENTER);
            vbLeft.setAlignment(Pos.TOP_CENTER);
            vbCenter.setAlignment(Pos.TOP_CENTER);
            vbRight.setAlignment(Pos.TOP_CENTER);

            vbLeft.setSpacing(40);
            vbCenter.setSpacing(40);
            vbRight.setSpacing(40);

            //Event handlers
            yodaButton.setOnAction(e ->{
                stage.close();
                GameUI.display(nameField.getText(),"Yoda");
            });

            obiWanButton.setOnAction(e->{
                stage.close();
                GameUI.display(nameField.getText(),"Obi-Wan");
            });

            mickeyButton.setOnAction(e ->{
                System.out.println("Mickey Button");
                stage.close();
                Alert alert = new Alert(Alert.AlertType.INFORMATION,"Not a valid Star Wars Character. Please Try again.", ButtonType.OK);
                Optional<ButtonType> result = alert.showAndWait();
                if(result.get() == ButtonType.OK){
                    alert.close();
                }
                try {
                    start(stage);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            });

            //Add controls to their parent layout component
            hbTop.getChildren().addAll(name,nameField);
            vbLeft.getChildren().addAll(yoda,yodaButton);
            vbCenter.getChildren().addAll(obiWan,obiWanButton);
            vbRight.getChildren().addAll(mickey,mickeyButton);

            //Styling
            scene.getStylesheets().add("GUIMain.css");
            name.setId("label_LG");
            nameField.setId("nameField");
            yoda.setId("label_LG");
            obiWan.setId("label_LG");
            mickey.setId("label_LG");

            //Display the window
            window.setScene(scene);
            stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
