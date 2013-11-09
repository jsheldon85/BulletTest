package bullettest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MessageAdapter {
    static LinkedBlockingQueue<ArrayList<String>> toUpdate = new LinkedBlockingQueue();
    HashMap<String, String[]> serverRequests = new HashMap();
    final static String serverIp = "216.159.152.221";
    Integer reqNum = 0;
    
    Boolean isHost;
    
    //String absoluteDistance="0";
    String hostIp = "123.123.1234"; //DUMMY IP, get from GUI user I/O
    
    protected void encodeRequest(String[] params, Boolean toServer){
        encodeRequest(params, toServer, serverIp);
    }
    
    protected void encodeRequest(String[] params, Boolean toServer, String ipAddress){
        String reqNum = Integer.toString(this.reqNum++);
        serverRequests.put(reqNum, params);
        
        String message = reqNum + " | ";
        
        for (String param : params){
            message += param + " | ";
        }
        message = message.substring(0, message.length()-3);
        
        ArrayList<String> temp = new ArrayList<>(3);
        temp.add(toServer.toString());
        temp.add(ipAddress);
        temp.add(message);
        try {
            toUpdate.put(temp);
        } catch (InterruptedException ex) {
            Logger.getLogger(MessageAdapter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //req # | cmd | positionx | positiony | velocityx | velocityyyyyyyyyy!!!!!!
    protected void parse(String message,String ipAddress){//General Form: command | param1 | param2 | ... | paramn
        String[] params = message.split(" \\| ");
        if (serverRequests.containsKey(params[0])){
            String[] args = serverRequests.get(params[0]);
        }
        try{
            ArrayList<String> temp = new ArrayList<>(2);
            temp.add(ipAddress);
            switch(params[0]){
                case("updateSide"): // left or right, distance, ip
                    System.out.println("updateSide");
                    updateSide(params[1].equals("right"), new Machine(params[3],Float.parseFloat(params[2])));
                    break;
                case("removeSide"): // left or right
                   System.out.println("removeSide");
                   removeSide(params[1].equals("right"));
                   break;
                case("createObject"): //posY, velX, velY
                    System.out.println("createObject");
                    
                    float[] floatParams = new float[(params.length-2)];
                    for(int i=2;i<params.length;i++){
                        floatParams[i-1] = Float.parseFloat(params[i]);
                    }
                    createObject(floatParams[0],floatParams[1],floatParams[2]);
                    break;
            }
        }
       /*     if (temp.size()==2) Users.toUpdate.put(temp);  Don't need, keeping to remember structure to add to toUpdate
        } catch (InterruptedException ex) {
            Logger.getLogger(MessageAdapter.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        catch(Error e){
            System.out.println(e);
        }
    }
    
    private void updateSide(Boolean isRight, Machine node){
        Boundary.setMachine(isRight, node);
    }
    
    private void removeSide(Boolean isRight){
        Boundary.removeMachine(isRight);
    }
    
    private void createObject(float posY, float velX, float velY){
        //Create the object in JBox2D
    }    
    
    public void hostGame(){
        //MachineList game = new MachineList();
        //hostIPMachineListMap.put(node.ip, game);
        isHost = true;
        String[] args = {"hostGame", Client.getAbsDistance()};
        encodeRequest(args, true); //command | distance
    }
    
    public void joinGame(String hostIP, Machine node){
        String[] args = {"joinGame", Client.getAbsDistance()};
        encodeRequest(args, true);
    }
    
    public void leaveGame(String hostIP, Machine node){
        String[] args = {"leaveGame", Client.getAbsDistance()};
        encodeRequest(args, true);
    }
    
    public void changeDistance(Machine node){
        String[] args = {"changeDistance", Client.getAbsDistance()};
        encodeRequest(args, true);
    }
}