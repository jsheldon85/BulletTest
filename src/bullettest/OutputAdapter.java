package bullettest;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OutputAdapter {
    private static LinkedBlockingQueue<ArrayList<String>> toUpdate = new LinkedBlockingQueue();
    private final static String serverIp = "127.0.0.1";
    private static Boolean isHost = false;
    private static String IP;
    
    public static void start(){
        try {IP=InetAddress.getLocalHost().getHostAddress();}
        catch (UnknownHostException ex){Logger.getLogger(OutputAdapter.class.getName()).log(Level.SEVERE, null, ex);}
        NetworkPusher pusher = new NetworkPusher(toUpdate);
        //pusher.start();
    }
    
    public static void joinGame(String hostIP, String absDistance){
        String[] args = {"joinGame", hostIP, absDistance};
        encodeRequest(args, true);
    }
    
    public static void leaveGame(String hostIP){
        if(isHost && hostIP.equals(IP)) isHost=false;
        String[] args = {"leaveGame", hostIP};
        encodeRequest(args, true);
    }
    
    public static void changeDistance(String absDistance){
        String[] args = {"changeDistance", absDistance};
        encodeRequest(args, true);
    }
    
    public static void hostGame(String absDistance){
        if(!isHost){
            isHost = true;
            String[] args = {"hostGame", absDistance};
            encodeRequest(args, true); //command | distance
        }
    }
    
    public static void getHosts(){
        String[] args = {"getHosts"};
        encodeRequest(args, true);
    }
    
    private static void encodeRequest(String[] params, Boolean toServer){
        encodeRequest(params, toServer, serverIp);
    }
    
    private static void encodeRequest(String[] params, Boolean toServer, String ipAddress){        
        String message = "";
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
            Logger.getLogger(OutputAdapter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
