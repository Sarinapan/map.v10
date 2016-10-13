/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainMenu;

import EventWindow.LoadEventWindow;
import LogWindow.LoadLogWindow;
import MapHTML.LoadMap;
import PendingTableView.LoadPendingTable;
import UnitTableView.LoadUnitTable;
import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author Sakina Pan
 */
public class LoadMainMenu extends Application {

    private static double xOffset = 0;
    private static double yOffset = 0;
    private Stage MainWindow;
    Scene scene1, scene2, scene3;

    @Override
    public void start(Stage primaryStage) {

        MainWindow = new Stage();
        MainWindow.initStyle(StageStyle.TRANSPARENT);
        MainWindow.getIcons().add(new Image("/Images/CP.PNG"));
        MainWindow.setTitle("Main menu");
        MainWindow.getOpacity();
        Button scenario = new Button("SCENARIO");
        scenario.setFont(Font.font("segoe print", FontWeight.BOLD, 16));
        scenario.setTextFill(Color.LIGHTGRAY);
        scenario.setStyle("-fx-background-color: #333333");
        scenario.setPrefSize(220, 30);
        scenario.setTranslateX(100);
        scenario.setTranslateY(300);
        scenario.setOnAction(e -> MainWindow.setScene(scene2));

        Button about = new Button("ABOUT");
        about.setFont(Font.font("segoe print", FontWeight.BOLD, 16));
        about.setTextFill(Color.LIGHTGRAY);
        about.setStyle("-fx-background-color: #333333");
        about.setPrefSize(220, 30);
        about.setTranslateX(100);
        about.setTranslateY(310);
        about.setOnAction(e -> MainWindow.setScene(scene3));
        //scenario.setOnAction(e -> MainWindow.setScene(scene2));

        Button ExitCP = new Button("EXIT");
        ExitCP.setFont(Font.font("segoe print", FontWeight.BOLD, 16));
        ExitCP.setTextFill(Color.LIGHTGRAY);
        ExitCP.setStyle("-fx-background-color: #333333");
        ExitCP.setPrefSize(220, 30);
        ExitCP.setTranslateX(100);
        ExitCP.setTranslateY(320);
        //scenario.setOnAction(e -> MainWindow.setScene(scene2));

        Glow shadow = new Glow();
        //Adding the shadow when the mouse cursor is on
        scenario.addEventHandler(MouseEvent.MOUSE_ENTERED,
                new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                scenario.setEffect(shadow);
            }
        });
//Removing the shadow when the mouse cursor is off
        scenario.addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                scenario.setEffect(null);
            }
        });

        //Glow shadow = new Glow();
        //Adding the shadow when the mouse cursor is on
        about.addEventHandler(MouseEvent.MOUSE_ENTERED,
                new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                about.setEffect(shadow);
            }
        });
//Removing the shadow when the mouse cursor is off
        about.addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                about.setEffect(null);
            }
        });
        ExitCP.addEventHandler(MouseEvent.MOUSE_ENTERED,
                new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                ExitCP.setEffect(shadow);
            }
        });
//Removing the shadow when the mouse cursor is off
        ExitCP.addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                ExitCP.setEffect(null);
            }
        });

        ExitCP.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                MainWindow.close();
            }
        });

        // scenario.setBackground(new Background(new BackgroundFill(Color.DIMGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        //MainWindow
        Image image = new Image("/Images/CTPBG.PNG");
        VBox vbox1 = new VBox(0);
        vbox1.setBackground(new Background(new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
        vbox1.setPadding(new Insets(100, 120, 20, 90));
        vbox1.getChildren().addAll(scenario, about, ExitCP);
        scene1 = new Scene(vbox1, 600, 600);

        Label Label2Heading = new Label("SCENARIOS");
        
        Label2Heading.setFont(Font.font("segoe print", FontWeight.BOLD, 20));
        Label2Heading.setTextFill(Color.ORANGE);
        Label2Heading.setPrefSize(260, 140);
        Label2Heading.setTranslateX(-15);
        Label2Heading.setTranslateY(-130);
        
        // Event Whitby label
        Label labelWhitby = new Label("Whitby");
        labelWhitby.setFont(Font.font("segoe print", FontWeight.BOLD, 16));
        labelWhitby.setTextFill(Color.BLACK);
        labelWhitby.setPrefSize(260, 140);
       labelWhitby.setTranslateX(-10);
        labelWhitby.setTranslateY(-180);
        
        // Information about the Whitby Scenario
        
       Label WhitbyInfo = new Label("The whitby Scenario consist of two major events:\n" +
                "1.	House burglary and the offender is on the move\n" +
                "2.	Shop lifting and the offender is on the move");
        WhitbyInfo.setFont(Font.font("segoe print", FontWeight.NORMAL, 14));
     WhitbyInfo.setTextFill(Color.BLACK);
       WhitbyInfo.setPrefSize(500, 140);
      WhitbyInfo.setTranslateX(-10);
        WhitbyInfo.setTranslateY(-250);
        
           // Event Petone label
        Label Petone = new Label("Petone");
        Petone.setFont(Font.font("segoe print", FontWeight.BOLD, 16));
        Petone.setTextFill(Color.DARKGREY);
       Petone.setPrefSize(260, 140);
      Petone.setTranslateX(-10);
        Petone.setTranslateY(-210);
        
        // Information about the Pentone Scenario
        
       Label PetoneInfo = new Label("The Petone Scenario consist of two major events:\n" +
                "1.	Armed robbery and the offender is on the move\n" +
                "2.	kidnapping and the offender is on the move");
        PetoneInfo.setFont(Font.font("segoe print", FontWeight.BOLD, 14));
     PetoneInfo.setTextFill(Color.DARKGREY);
       PetoneInfo.setPrefSize(500, 140);
      PetoneInfo.setTranslateX(-10);
        PetoneInfo.setTranslateY(-230);
        
            // Event Petone label
        Label Wellington = new Label("Wellington");
        Wellington .setFont(Font.font("segoe print", FontWeight.BOLD, 16));
        Wellington .setTextFill(Color.LIGHTGRAY);
       Wellington .setPrefSize(260, 140);
      Wellington .setTranslateX(-10);
        Wellington .setTranslateY(-210);
        
        // Information about the Pentone Scenario
        
       Label WelInfo = new Label("The Petone Scenario consist of two major events:\n" +
                "1.	Armed robbery and the offender is on the move\n" +
                "2.	kidnapping and the offender is on the move");
       WelInfo.setFont(Font.font("segoe print", FontWeight.BOLD, 14));
     WelInfo.setTextFill(Color.LIGHTGREY);
      WelInfo.setPrefSize(500, 140);
      WelInfo.setTranslateX(-10);
        WelInfo.setTranslateY(-230);
        
// Return to main menu Button
        Button Back2Main = new Button("RETURN TO MENU");
        Back2Main.setFont(Font.font("segoe print", FontWeight.BOLD, 10));
        Back2Main.setTextFill(Color.BLACK);
        Back2Main.setStyle("-fx-background-color: #FFA500");
        Back2Main.setPrefSize(130, 30);
        Back2Main.setTranslateX(-10);
        Back2Main.setTranslateY(450);
        Back2Main.setOnAction(e -> MainWindow.setScene(scene1));
    
//Select  Event Button
        Button Scenario1 = new Button("SELECT");
        Scenario1.setFont(Font.font("segoe print", FontWeight.BOLD, 10));
        Scenario1.setTextFill(Color.BLACK);
        Scenario1.setStyle("-fx-background-color: #ADD8E6");
        Scenario1.setPrefSize(130, 30);
        Scenario1.setTranslateX(-10);
        Scenario1.setTranslateY(-20);
        //Scenario1.setOnAction(e -> map.start(primaryStage);
   
        Scenario1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                LoadEventWindow eventWindow = new LoadEventWindow();
                LoadMap map = new LoadMap();
                LoadLogWindow logWindow = new LoadLogWindow();
                LoadUnitTable unitTable = new LoadUnitTable();
                LoadPendingTable pendingTable = new LoadPendingTable();

                logWindow.start(MainWindow);
                map.start(MainWindow);
                eventWindow.start(MainWindow);
                unitTable.start(MainWindow);
                pendingTable.start(MainWindow);
              

                MainWindow.close();
            }
        });
        //Select Button for Petone scenario
          Button Scenario2 = new Button("SELECT");
        Scenario2.setFont(Font.font("segoe print", FontWeight.BOLD, 10));
      Scenario2.setTextFill(Color.BLACK);
        Scenario2.setStyle("-fx-background-color: #A9A9A9");
       Scenario2.setPrefSize(130, 30);
       Scenario2.setTranslateX(-10);
       Scenario2.setTranslateY(-200);
        
        //TextArea scenrioTextArea = new TextArea("hdhddhdhdhdhdhdhdhdhdhhdhddhdhdhdhd");
        
        
        //scenrioTextArea.getStylesheets().add("/MainMenu/menu.css");
                
            //CP Image
        Image image3= new Image("/Images/NCP.PNG");
        ImageView SIcon = new ImageView();
        SIcon.setImage(image3);
        SIcon.setFitWidth(40);
        SIcon.setPreserveRatio(true);
        SIcon.setSmooth(true);
        SIcon.setCache(true);
        SIcon.setTranslateX(-80);
        SIcon.setTranslateY(-290);

        
        Image image1 = new Image("Images/EventBackG.jpg");
        VBox vbox2 = new VBox(0);
        vbox2.setBackground(new Background(new BackgroundImage(image1, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
        vbox2.setPadding(new Insets(100, 120, 20, 90));
        vbox2.getChildren().addAll(Back2Main, Label2Heading, Scenario1,labelWhitby,SIcon,WhitbyInfo,Petone,PetoneInfo,Scenario2);
        scene2 = new Scene(vbox2, 600, 600);
        MainWindow.setScene(scene1);

        Button about2Main = new Button("RETURN TO MENU");
        about2Main.setFont(Font.font("segoe print", FontWeight.BOLD, 10));
        about2Main.setTextFill(Color.BLACK);
        about2Main.setStyle("-fx-background-color: #FFA500");
        about2Main.setPrefSize(130, 30);
        about2Main.setTranslateX(-60);
        about2Main.setTranslateY(250);
        about2Main.setOnAction(e -> MainWindow.setScene(scene1));

        Label Label3Heading = new Label("ABOUT");
        Label3Heading.setFont(Font.font("segoe print", FontWeight.BOLD, 20));
        Label3Heading.setTextFill(Color.ORANGE);
        Label3Heading.setPrefSize(260, 140);
        Label3Heading.setTranslateX(160);
        Label3Heading.setTranslateY(-180);

        Image image2 = new Image("/Images/ScenarioBackG.jpg");
        VBox vbox3 = new VBox(0);
        vbox3.setBackground(new Background(new BackgroundImage(image2, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
        vbox3.setPadding(new Insets(100, 120, 20, 90));
        vbox3.getChildren().addAll(about2Main, Label3Heading);
        scene3 = new Scene(vbox3, 600, 400);
        MainWindow.setScene(scene1);

        MainWindow.show();

        MainWindow.getScene().setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = MainWindow.getX() - event.getScreenX();
                yOffset = MainWindow.getY() - event.getScreenY();
            }
        });
        MainWindow.getScene().setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                MainWindow.setX(event.getScreenX() + xOffset);
                MainWindow.setY(event.getScreenY() + yOffset);
            }
        });

    }

}
