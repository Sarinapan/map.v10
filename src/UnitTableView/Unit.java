package UnitTableView;

public class Unit {
    protected enum Type {
        I("I"), S("S"), Q("Q"), T("T"), D("D");
        
        private String car;
        Type(String car){
            this.car = car;
        }
        public String getType(){
            return car;
        }
    };

    protected enum Status { 
        avail("Available"), onRot("Onroute"), 
        onSen("Onscene"), unAvail("Unavailable");
        
        private String status;   
        Status(String status){
            this.status = status;
        }
        
        public String getStatus() {
            return status;
        }
    }

    private final String id;
    private String callSign;
    private String currLocation;
    private String currEvent;
    private String time;
    private final Type type;
    private Status status;
    
    Unit(String uniqId, String callSign, String currLocation, String currEvent, String time, String type, String stat) {
        this.id = uniqId;
        this.callSign = callSign;
        this.currLocation = currLocation;
        this.currEvent = currEvent;
        this.time = time; //not final TBD 'Type'
        this.type = setType(type);
        this.status = setStatus(stat);
    }
    
//setters
    public void setTime(String time) {
        this.time = time; //not final TBD Type
    }
         
    public Status setStatus(String status){ 
        switch(status){
            case "avail": return this.status.avail; 
            case "unAvail": return this.status.unAvail; 
            case "onRot": return this.status.onRot; 
            case "onSen": return this.status.onSen; 
            default: break;
        }
        return null;
    }   
    
    public Type setType(String type){   
        switch(type){
            case "I": return this.type.I;
            case "S": return this.type.S;
            case "Q": return this.type.Q;
            case "T": return this.type.T;
            case "D": return this.type.D;
            default: break;
        }
        return null;
    }   
    
//getters    
    public String getId(){
        return id;
    }
    
    public String getCallSign() {
        return callSign;
    }

    public String getCurrLocation() {
        return currLocation;
    }

    public String getCurrEvent() {
        return currEvent;
    }

    public String getTime() {
        return time;
    }

    public String getType(){
        return type.getType();
    }
    
    public String getStatus(){
        return status.getStatus();
    }
}
