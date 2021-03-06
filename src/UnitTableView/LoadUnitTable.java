package UnitTableView;

import LogWindow.RecordInput;
import java.io.IOException;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import MapHTML.LoadMap;
import NotificationWindow.LoadNotification;
import PendingTableView.LoadPendingTable;
import com.sun.media.sound.JavaSoundAudioClip;
import java.io.File;
import java.io.FileInputStream;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.control.TableRow;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;

public class LoadUnitTable extends Application {
    private int id;
    private static Unit x = null;
    private static String unitID;
    private static String callSign;
    private static String defLocation;
    private static String currEvent;
    private static String time;
    private static String type;
    private static String status;
    
    private ObservableList<Unit> unitSelected; 
    private TableView<Unit> table;    
    private Stage unitWindow;
   
    private Text actionStatus;
    private static double xOffset = 0;
    private static double yOffset = 0;   
   
    private int numCordonDisp = 0;
    private LoadMap mapEngine = new LoadMap();
    private LoadPendingTable lpt = new LoadPendingTable();
    private RecordInput log = new RecordInput();
    
    @Override
    public void start(Stage primaryStage){//throws ParserConfigurationException, SAXException, IOException{
        //Load UnitTable   
        createTable();
        createContextMenu();
 
        unitWindow = new Stage();
        unitWindow.initStyle(StageStyle.UNDECORATED);
        unitWindow.getIcons().add(new Image("/Images/NCP.PNG"));
        unitWindow.setTitle("Unit Table");
        table.getStylesheets().add("UnitTableView/Unittable.css");
        
        Label label = new Label("Unit Table");
        
        //FontStyle
        label.setTextFill(Color.LIGHTGRAY);
        label.setFont(Font.font("Calibri", FontWeight.BOLD, 16));
        label.setTranslateX(55);
        label.setTranslateY(-6);
        
        //CP Image
        Image image = new Image("/Images/NCP.PNG");
        ImageView TIcon = new ImageView();
        TIcon.setImage(image);
        TIcon.setFitWidth(45);
        TIcon.setPreserveRatio(true);
        TIcon.setSmooth(true);
        TIcon.setCache(true);
        TIcon.setTranslateX(5);
        TIcon.setTranslateY(-18);
        
        //Exit Image
        ImageView Exit = new ImageView("/Images/ExitButton2.PNG");
        Exit.setFitHeight(18);
        Exit.setFitWidth(18);
        Exit.setTranslateX(575);
        Exit.setTranslateY(-10);
        
        
        ImageView Min = new ImageView("/Images/minimizeButton1.PNG");
        Min.getStyleClass().add("ImageView");
        Min.setFitHeight(18);
        Min.setFitWidth(18);
        Min.setTranslateX(556);
        Min.setTranslateY(-13);
        
        actionStatus = new Text();
        actionStatus.setFill(Color.FIREBRICK);  
        
        VBox vbox = new VBox(-15);   
        vbox.setBackground(new Background(new BackgroundFill(Color.GREY, CornerRadii.EMPTY, Insets.EMPTY)));
        vbox.setPadding(new Insets(12, 0, 15, 0));
        vbox.getChildren().addAll(label,Exit,Min,TIcon,table, actionStatus);
        Scene scene = new Scene(vbox, 600,300); // w x h 
        unitWindow.setScene(scene);
        unitWindow.show();
        
        //Positioning the window on the screen
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        unitWindow.setX((primScreenBounds.getWidth() - unitWindow.getWidth()) /100); 
        unitWindow.setY((primScreenBounds.getHeight() - unitWindow.getHeight()) / 1.35);      
        
        //Exit Button
        Exit.setOnMouseClicked((MouseEvent t) -> {
            System.exit(0);
        });    
        
        Min.setOnMouseClicked((MouseEvent me) -> {
            unitWindow.setIconified(true);     
        });
       
        unitWindow.getScene().setOnMousePressed((MouseEvent event) -> {
            xOffset = unitWindow.getX() - event.getScreenX();
            yOffset = unitWindow.getY() - event.getScreenY();
        });
        unitWindow.getScene().setOnMouseDragged((MouseEvent event) -> {
            unitWindow.setX(event.getScreenX() + xOffset);
            unitWindow.setY(event.getScreenY() + yOffset);            
        });     
    }
    
    public void createContextMenu(){
        //Multiple selection
        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        //Get Selected Item
        unitSelected = table.getSelectionModel().getSelectedItems(); 
     
        //ContextMenu
        ContextMenu contextMenu = new ContextMenu();
        
        //menu items
        MenuItem item1 = new MenuItem("Disp Enroute");
        MenuItem item2 = new MenuItem("Disp On Scene");
        MenuItem item3 = new MenuItem("Disp Assign");
        MenuItem item4 = new MenuItem("Change Location");
        MenuItem item5 = new MenuItem("K1");
        MenuItem item6 = new MenuItem("K6");
        MenuItem item7 = new MenuItem("K9");
        
        //Add item to context menu
        contextMenu.getItems().addAll(item1,item2,item3,item4,item5,item6,item7);
        
        //set context menu to table
        table.setContextMenu(contextMenu);
        
        //dispatchLocation function
        item1.setOnAction(e -> dispatchCordon());   
        
        //OnScene Function       
        item2.setOnAction(e -> OnScene());
        
        //changeLocationFunction     
        item4.setOnAction(e -> changeLocation());      
        
        //CloseEvent
        item7.setOnAction(e -> k9()); 
    }
    
    public void k9(){  
        for(int i = 0; i < unitSelected.size(); i++){     
            if(!lpt.getIsEventOn() && !unitSelected.get(i).getStatus().equals("Onscene")){// if disptacher tries to close event when the cordon has not been assigned
                log.writeLog(14, unitSelected.get(i).getId()); 
            }else if(!mapEngine.getDogHandlerStatus() && unitSelected.get(i).getStatus().equals("Onscene")){//if dispatcher tries to close event while the offender has not been caught
                log.writeLog(15, unitSelected.get(i).getId()); 
            }else if(lpt.getIsEventOn() && !unitSelected.get(i).getStatus().equals("Onscene")){// if disptacher tries to close event an on route cordon when the offender has not yet been caught
                log.writeLog(15, unitSelected.get(i).getId());
            }else if (mapEngine.getDogHandlerStatus() && unitSelected.get(i).getStatus().equals("Onscene")){ //check if dog handler has caught the offender and cordon  is on scene
                numCordonDisp -= 1;
                System.out.println(numCordonDisp);
                unitSelected.get(i).setStatus("avail");
                log.writeLog(13, unitSelected.get(i).getId());
                if(numCordonDisp == 0){ // check if all cordons has been unassigned to the event.
                    mapEngine.refreshMap();
                    lpt.enAbleRow();
                    log.writeLog(16, "");
                }
            }
        }
        updateTableColour();
    }
    
    public void OnScene(){
        CalculateDistance cd = new CalculateDistance();
        for(int x = 0; x < unitSelected.size(); x++){
            if(unitSelected.get(x).getStatus().equals("Onroute") && cd.isOnScene(unitSelected.get(0).getId())){//if cordon status is on route and has arrived on scene
                unitSelected.get(x).setStatus("onSen");   
                log.writeLog(10, unitSelected.get(x).getId());
                unitSelected.get(x).setCurrLocation(mapEngine.getEvent());
                table.refresh();
                if(unitSelected.get(x).getType().equals("D")) {
                    //temporary-------------------------------------------------
                    mapEngine.startTracking( -41.1130274, 174.8924949 );
                    checkOnDogHandler();
                    //----------------------------------------------------------
                }
                updateTableColour();
            }
            else if("Onscene".equals(unitSelected.get(x).getStatus()) && lpt.getIsEventOn()){// if dispatcher changes the status of cordon twice
                log.writeLog(4, unitSelected.get(x).getId());
            }else if(!lpt.getIsEventOn()){// if dispatcher changes cordon status to on scene without assigning it to an event
                log.writeLog(3, unitSelected.get(x).getId());
            }else if(!unitSelected.get(x).getStatus().equals("Onroute")){// if dispatcher changes cordon status to on scene without assigning it to an event
                log.writeLog(3, unitSelected.get(x).getId());
            }else if(!cd.isOnScene(unitSelected.get(0).getId()) && unitSelected.get(x).getStatus().equals("Onroute")){ //if disptacher tries to change status of cordon when it has not arrived on scene
                log.writeLog(5, unitSelected.get(x).getId());
            }
        }
        table.getSelectionModel().clearSelection();
    }
    
    public void changeLocation(){    
        if("Onscene".equals(unitSelected.get(0).getStatus()) && lpt.getIsEventOn()){ // if unit is onscene and is assigned to an event then execute
            unitSelected.sorted();
            id = checkId(unitSelected.get(0).getId());  
            mapEngine.changeLocation(id); 
            getMarkerLocation(id, unitSelected.get(0).getId());
        }else if(!"Onscene".equals(unitSelected.get(0).getStatus()) && lpt.getIsEventOn()){ //if unit is not onscene but is assigned to an event
            log.writeLog(6, unitSelected.get(0).getId());
        }else if(!lpt.getIsEventOn()){ //if unit is not assigned to any event
            log.writeLog(7, unitSelected.get(0).getId());
        }
        table.getSelectionModel().clearSelection();
    }
    
    public void dispatchCordon(){
       unitSelected.sorted();
        for(int x = 0; x < unitSelected.size(); x++){
            if(!lpt.getIsEventOn()){//if unit is not assigned to an event
                log.writeLog(1, unitSelected.get(x).getId());           
            }else if(!unitSelected.get(x).getStatus().equals("Onroute") && unitSelected.get(x).getStatus().equals("Available")){//check if unit is not already dispatched and is available    
                id = checkId(unitSelected.get(x).getId());
                if(!unitSelected.get(x).getType().equals("D")){ //if type is not dog handler then create marker
                    mapEngine.setMarkerId(id);  
                }
                unitSelected.get(x).setStatus("onRot"); //change status to on route                       
                numCordonDisp += 1; //monitor how many corodn has been dispatch
 
                log.writeLog(9, unitSelected.get(x).getId());              
                CalculateDistance cd = new CalculateDistance();
                //temporary-----------------------------------------------------
                Random rn = new Random();
                cd.calculateDistance(unitSelected.get(x).getId(), rn.nextInt(10000) + 5000);//delay when the cordon arrives. 
                //--------------------------------------------------------------
            }else{//if dispatcher tries to disptach unit twice 
                log.writeLog(2, unitSelected.get(x).getId());
            }           
        }     
        updateTableColour();
        mapEngine.createMarker();
        table.getSelectionModel().clearSelection();  
    }
    
    public void updateTableColour(){
      table.setRowFactory(tv -> new TableRow<Unit>(){
            @Override
            public void updateItem(Unit unit, boolean empty) {
                super.updateItem(unit, empty);
                if (unit == null) {
                    setStyle("");
                } else if (unit.getStatus().equals("Onscene")) {
                    setStyle("-fx-background-color: lightcoral;");
                } else if (unit.getStatus().equals("Onroute")) {
                    setTextFill(Color.LIGHTGREEN);
                    setStyle("-fx-background-color: goldenrod");
                } else if (unit.getStatus().equals("Available")) {
                    setTextFill(Color.LIGHTGREEN);
                    setStyle("");
                } else {
                    setStyle("");
                }
            }
        });
    }   
    private boolean isCaught;
    public void checkOnDogHandler(){
        isCaught = true;
        Runnable task = new Runnable(){
            public void run(){                            
                while(isCaught){  
                    try{
                        Thread.sleep(1000);
                        Platform.runLater(new Runnable(){
                        @Override
                            public void run(){  
                                //System.out.println(mapEngine.getDogHandlerStatus());
                                if(mapEngine.getDogHandlerStatus() && isCaught){
                                    isCaught = false;
                                    LoadNotification ln = new LoadNotification("Dog handler has caught the offender."); 
                                    ln.start(ln.notificationWindow); 
                                    try {
                                        new JavaSoundAudioClip(new FileInputStream(new File("notify.wav"))).play();
                                    } catch (IOException ex) {
                                        Logger.getLogger(LoadUnitTable.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }   
                            }                  
                        }); 
                    }catch(InterruptedException e){} 
                }log.writeLog(12,"none");                                 
            }
        };
        Thread backgroundThread = new Thread(task);
        backgroundThread.setDaemon(true);
        backgroundThread.start();      
    }
    
    private String prevLoc = "none";
    private String currLoc;
    private boolean isTrue = true;  
    public void getMarkerLocation(int id, String name){
        prevLoc = mapEngine.getCordonCurrLocation(id);
        isTrue = true;
        Runnable task = new Runnable(){
            public void run(){                            
                while(isTrue){  
                    try{
                        Thread.sleep(500);
                        Platform.runLater(new Runnable(){
                        @Override
                            public void run(){                             
                                currLoc = mapEngine.getCordonCurrLocation(id);
                                //System.out.println("The previous location of: " + name +  " is " +prevLoc);  
                                if(!currLoc.equals(prevLoc)){
                                    isTrue = false;  
                                }                                 
                            }                  
                        }); 
                    }catch(InterruptedException e){} 
                }log.writeLog(11, name, currLoc);                    
            }
        };
        Thread backgroundThread = new Thread(task);
        backgroundThread.setDaemon(true);
        backgroundThread.start();      
    }  
    
    public int checkId(String id){
        switch(id){
           case "UNI1": return 1;
           case "UNS1": return 2; 
           case "UNQ1": return 3; 
           case "UNT1": return 4; 
           case "UND1": return 5; 
           default: return -1;
        }      
    }
  
    public void createTable(){//throws ParserConfigurationException, SAXException, IOException{
        //ID column
        TableColumn<Unit, String> uniqIdCol = new TableColumn<>("ID");
        uniqIdCol.setMinWidth(100);
        uniqIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        //Location column
        TableColumn<Unit, String> currLocationCol = new TableColumn<>("Location");
        currLocationCol.setMinWidth(300);
        currLocationCol.setCellValueFactory(new PropertyValueFactory<>("currLocation"));
        //Time column
        TableColumn<Unit, String> timeCol = new TableColumn<>("Time");
        timeCol.setMinWidth(100);
        timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));
        //Type column
        TableColumn<Unit, String> typeCol = new TableColumn<>("Type");
        typeCol.setMinWidth(100);
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
    
        //create Table
        table = new TableView<>();
        table.setItems(getUnits());
        table.getColumns().addAll(uniqIdCol,currLocationCol,timeCol,typeCol); 
        updateTableColour();
    }
    
    public ObservableList<Unit> getUnits(){//throws ParserConfigurationException, SAXException, IOException{       
        ObservableList<Unit> unit = FXCollections.observableArrayList();    
        //Temporary
        x = new Unit("UNI1", "signUndef", "Porirua Police Station", "eventUndef", "11:05 am", "I", "avail");
        unit.add(x);
        x = new Unit("UNS1", "signUndef", "Porirua Police Station", "eventUndef", "10:58 am", "S", "avail");
        unit.add(x);
        x = new Unit("UNQ1", "signUndef", "Porirua Police Station", "eventUndef", "11:00 am", "Q", "avail");
        unit.add(x);
        x = new Unit("UNT1", "signUndef", "Porirua Police Station", "eventUndef", "10:30 am", "T", "avail");
        unit.add(x);
        x = new Unit("UND1", "signUndef", "Porirua Police Station", "eventUndef", "11:13 am", "D", "avail");
        unit.add(x);
        //XML CODE
        /*ObservableList<Unit> unit = FXCollections.observableArrayList();    
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse("UnitInfo.xml");
        NodeList nList = doc.getElementsByTagName("unit");
        
        //-------------------------------------------------------------------
        //code below uses xml, comment out if using database     
        for (int i = 0; i < nList.getLength(); i++) {
            Node node = nList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) node;
                uniqID = eElement.getAttribute("id");
                callSign = eElement.getElementsByTagName("callSign").item(0).getTextContent();
                defLocation = eElement.getElementsByTagName("defLocation").item(0).getTextContent();
                currEvent = eElement.getElementsByTagName("currEvent").item(0).getTextContent();
                time = eElement.getElementsByTagName("time").item(0).getTextContent();
                type = eElement.getElementsByTagName("type").item(0).getTextContent();
                status = eElement.getElementsByTagName("status").item(0).getTextContent();
                x = new Unit(uniqID, callSign, defLocation, currEvent, time, type, status);
                unit.add(x);
            }
        }*/
        //--------------------------------------------------------------------
        //--------------------------------------------------------------------
        //Code below reads database instead of XML file
        /*
        Connection conn = null;
        DataBaseConn dbConn = null;
        PreparedStatement ps;
        ResultSet rs, rs2;
        Statement stmt;
        try {
            dbConn = new DataBaseConn();
            conn = dbConn.getConnection();
            stmt = conn.createStatement();
            
            rs = stmt.executeQuery("select * from unit");
            
            ps = conn.prepareStatement(
                    "select unit_type_name from unit "
                    + "inner join unit_type "
                    + "on unit_type_fk = unit_type_id "
                    + "where unit_name = ?"
                    );
            while( rs.next() ) {
                uniqID = rs.getString("unit_name");
                callSign = rs.getString("unit_name");
                defLocation = "locUndefined";
                currEvent = "currUndefined";
                time = "timeUndefined";
                status = "avail";
                currEvent = "evtUndefined";
                ps.setString( 1, callSign );
                rs2 = ps.executeQuery();
                rs2.first();
                type = rs2.getString("unit_type_name");
                System.out.println(type);
                x = new Unit(uniqID, callSign, defLocation, currEvent, time, type, status);
                unit.add(x);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }*/
        //--------------------------------------------------------------------
        return unit;
    }
}
