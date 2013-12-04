package bullettest;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OutputAdapter {
    //private static LinkedBlockingQueue<ArrayList<String>> toUpdate = new LinkedBlockingQueue();
    private final static String serverIp = "127.0.0.1";
    private static Boolean isHost = false;
    private static String IP;
    
    public static void start(){
        try {IP=InetAddress.getLocalHost().getHostAddress();}
        catch (UnknownHostException ex){Logger.getLogger(OutputAdapter.class.getName()).log(Level.SEVERE, null, ex);}
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
        send(toServer, ipAddress, message);
    }
    
    private static void send(Boolean toServer, String ip, String message) {
        try {
            Socket s = null;
            if (toServer) s = new Socket(serverIp, 2004);  //Clients on 2002 Servers on 2004
            else          s = new Socket(ip, 2002);
            PrintWriter out = new PrintWriter(s.getOutputStream(), true);

            System.out.println("OUT:  "+message);
            out.println(message);

            out.close();
            s.close();
        }
        catch (UnknownHostException ex) {
            Logger.getLogger(NetworkPusher.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex) {
            Logger.getLogger(NetworkPusher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
