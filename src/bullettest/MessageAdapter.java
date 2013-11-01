/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bullettest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vsc243
 */
public class MessageAdapter {
    static LinkedBlockingQueue<ArrayList<String>> toUpdate = new LinkedBlockingQueue();
    HashMap<String, String[]> serverRequests = new HashMap();
    String serverIp = "216.159.152.221";
    Integer reqNum = 0;
    
    Node leftNode;
    Node rightNode;
    
    private void encodeRequest(String[] params){
        String reqNum = Integer.toString(this.reqNum++);
        serverRequests.put(reqNum, params);
        
        String message = reqNum + " | ";
        
        for (String param : params){
            message += param + " | ";
        }
        message = message.substring(0, message.length()-3);
        
        ArrayList<String> temp = new ArrayList<>(2);
        temp.add(serverIp);
        temp.add(message);
        try {
            toUpdate.put(temp);
        } catch (InterruptedException ex) {
            Logger.getLogger(MessageAdapter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //cmd | positionx | positiony | velocityx | velocityyyyyyyyyy!!!!!!
    private void parse(String message,String ipAddress){//General Form: command | param1 | param2 | ... | paramn
        String[] params = message.split(" \\| ");
        try{
            ArrayList<String> temp = new ArrayList<>(2);
            temp.add(ipAddress);
            switch(params[0]){
                case("setAdjacentNode"): //ip, distance
                    System.out.println("setAdjacentNode");
                    
                    setAdjacentNode(params[1],Float.parseFloat(params[2]));
                    break;
                    
                    
                case("createObject"): //posY, velX, velY
                    System.out.println("createObject");
                    
                    float[] floatParams = new float[(params.length-1)];
                    for(int i=1;i<params.length;i++){
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
    
    private void setAdjacentNode(String ip, float distance){
        if(distance>=0) rightNode = new Node(ip, distance);
        else leftNode = new Node(ip, distance);
    }
    
    private void createObject(float posY, float velX, float velY){
        
    }
}
