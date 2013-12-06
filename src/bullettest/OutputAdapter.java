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
    private final static String serverIp = "216.159.69.100";
    private static Boolean isHost = false;
    private static String IP;
    
    public static void start(){
        try {IP=InetAddress.getLocalHost().getHostAddress();}
        catch (UnknownHostException ex){Logger.getLogger(OutputAdapter.class.getName()).log(Level.SEVERE, null, ex);}
    }
    
    public static void joinGame(String hostIP, String absDistance){
        String[] args = {"joinGame", hostIP, absDistance};
        encodeServerRequest(args);
    }
    
    public static void leaveGame(String hostIP){
        if(isHost && hostIP.equals(IP)) isHost=false;
        String[] args = {"leaveGame", hostIP};
        encodeServerRequest(args);
    }
    
    public static void changeDistance(String absDistance){
        String[] args = {"changeDistance", absDistance};
        encodeServerRequest(args);
    }
    
    public static void hostGame(String absDistance){
        if(!isHost){
            isHost = true;
            String[] args = {"hostGame", absDistance};
            encodeServerRequest(args); //command | distance
        }
    }
    
    public static void sendObject(String ip, Float pos_x, Float vel_x, Float vel_y){
        String[] args = {"createObject", pos_x.toString(), vel_x.toString(), vel_y.toString()};
        encodeClientRequest(args, ip);
    }
    
    public static void getHosts(){
        String[] args = {"getHosts"};
        encodeServerRequest(args);
    }
    
    private static void encodeServerRequest(String[] params){
        encodeRequest(params, true, serverIp);
    }
    
    private static void encodeClientRequest(String[] params, String ipAddress){
        encodeRequest(params, false, ipAddress);
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
            Socket s;
            if (toServer) s = new Socket(serverIp, 2004);  //Clients on 2002 Servers on 2004
            else          s = new Socket(ip, 2002);
            PrintWriter out = new PrintWriter(s.getOutputStream(), true);

            System.out.println("OUT:  "+message);
            out.println(message);

            out.close();
            s.close();
        }
        catch (UnknownHostException ex) {
            Logger.getLogger(OutputAdapter.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex) {
            Logger.getLogger(OutputAdapter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
