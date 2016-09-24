package PendingTableView;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoadPendingTable extends Application {
    private static String priority;
    private static String time;
    private static String evtNumber;
    private static String type;
    private static String location;
     private static EventList evtList = null;
     
    private TableView table;
    private Text actionStatus;
    private static double xOffset = 0;
    private static double yOffset = 0;
    private Stage pendingList;

    @Override
    public void start(Stage primaryStage) {
        createTable();
        
        pendingList = new Stage();
        pendingList.initStyle(StageStyle.UNDECORATED);
        pendingList.getIcons().add(new Image("/Images/NCP.PNG"));
        //pendingList.setTitle("Unit Table");
        
        //Window Title
        Label label = new Label("Event Pending");
        
        //FontStyle
        label.setTextFill(Color.LIGHTSTEELBLUE);
        label.setFont(Font.font("Calibri", FontWeight.BOLD, 16));
        label.setTranslateX(55);
        label.setTranslateY(-10);
        
        //CP Image
        Image image = new Image("/Images/NCP.PNG");
        ImageView TIcon = new ImageView();
        TIcon.setImage(image);
        TIcon.setFitWidth(45);
        TIcon.setPreserveRatio(true);
        TIcon.setSmooth(true);
        TIcon.setCache(true);
        TIcon.setTranslateX(2);
        TIcon.setTranslateY(9);
        
        //Exit Image
        ImageView Exit = new ImageView("/Images/ExitButton.PNG");
        Exit.getStyleClass().add("ImageView");
        Exit.setFitHeight(20);
        Exit.setFitWidth(20);
        Exit.setTranslateX(570);
        Exit.setTranslateY(20);
        
        //Minimize Image
        ImageView Min = new ImageView("/Images/minimizeButton.PNG");
        Min.getStyleClass().add("ImageView");
        Min.setFitHeight(20);
        Min.setFitWidth(22);
        Min.setTranslateX(545);
        Min.setTranslateY(16);
        
        actionStatus = new Text();
        actionStatus.setFill(Color.FIREBRICK);  
   
        VBox vbox = new VBox(-16);   
        vbox.setBackground(new Background(new BackgroundFill(Color.GREY, CornerRadii.EMPTY, Insets.EMPTY)));
        vbox.setPadding(new Insets(-10, 0, -10, 0));

        vbox.getChildren().addAll(Exit,Min, TIcon, actionStatus,label,table );
        Scene scene = new Scene(vbox, 600,300); // w x h
         
        pendingList.setScene(scene);
        pendingList.show();
        
        pendingList.getScene().setOnMousePressed(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                xOffset = pendingList.getX() - event.getScreenX();
                yOffset = pendingList.getY() - event.getScreenY();
            }
        });
        pendingList.getScene().setOnMouseDragged(new EventHandler<MouseEvent>() { 
            @Override 
            public void handle(MouseEvent event) { 
                pendingList.setX(event.getScreenX() + xOffset);
                pendingList.setY(event.getScreenY() + yOffset);
            } 
        }); 
        
        //Minimize Button
        Min.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent me){
                pendingList.setIconified(true);
            }     
        });
        
        //Exit Button
        Exit.setOnMouseClicked(new EventHandler<MouseEvent>(){
           @Override
           public void handle(MouseEvent t){
               System.exit(0);
           }       
        });  
    }
    
    public void createTable(){
        table = new TableView<>();                
        TableColumn prioityCol = new TableColumn("Prioity");
        prioityCol.setCellValueFactory(new PropertyValueFactory("priority"));
        TableColumn timeCol = new TableColumn("Time");
        timeCol.setCellValueFactory(new PropertyValueFactory("time"));
        TableColumn eventNumberCol = new TableColumn("Event Number");
        eventNumberCol.setCellValueFactory(new PropertyValueFactory("evtNumber"));
        TableColumn TypeCol = new TableColumn("Type");
        TypeCol.setCellValueFactory(new PropertyValueFactory("type"));
        TableColumn locationCol = new TableColumn("Location");
        locationCol.setCellValueFactory(new PropertyValueFactory("location"));
        
        table.setItems(getEvtList());
        table.getColumns().setAll(prioityCol,timeCol,eventNumberCol,TypeCol,locationCol);    
        table.setPrefWidth(450);
        table.setPrefHeight(225);
        table.setTranslateX(0);
        table.setTranslateY(24);    
        
        
        //double click function       
        //Either of the double click function works I just dont know which one is better.

        table.setOnMouseClicked(event -> {
            ObservableList<EventList> evtSelected;
            evtSelected = table.getSelectionModel().getSelectedItems(); 
            if (event.getClickCount() == 2 && (! evtSelected.isEmpty()) ) {
                //Temporary
                System.out.println(evtSelected.get(0).getEvtNumber());
            }
        });
        
        //Alternative
        /*table.setRowFactory( tv -> {
            TableRow<EventList> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    EventList rowData = row.getItem();
                    //Temporary
                    System.out.println(rowData.getEvtNumber());
                }
            });
            return row ;
        });*/
    }
    
    public ObservableList<EventList> getEvtList(){       
        ObservableList<EventList> listOfEvents = FXCollections.observableArrayList();    
        /*for (int i = 0; i < 10; i++){
            priority =;
            time =;
            evtNumber =;
            type =;
            location =;
 
            evtList = new EventList(priority, time, evtNumber, type, location);
            listOfEvents.add(evtList);
        }    */
        evtList = new EventList("High", "12:00", "B1204", "Assault", "Gotham");
        listOfEvents.add(evtList);
        return listOfEvents;
    }
}