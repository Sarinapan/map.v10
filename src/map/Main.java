package map;

import java.io.IOException;
import javafx.application.Application;
import javafx.stage.Stage;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import MapHTML.LoadMap;
import UnitTableView.LoadUnitTable;
import PendingTableView.LoadPendingTable;
import static javafx.application.Application.launch;

public class Main extends Application { 
    private LoadMap map = new LoadMap();
    private LoadUnitTable unitTable = new LoadUnitTable();
    private LoadPendingTable pendingTable = new LoadPendingTable();
    
    public static void main(String[] args) {
        System.out.println("Hello CordonProjectTeam!");
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage)throws ParserConfigurationException, SAXException, IOException{       
        map.start(primaryStage);
        pendingTable.start(primaryStage);
        unitTable.start(primaryStage);
    }
}
  
