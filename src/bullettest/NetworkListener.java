/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bullettest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Brian
 */
class NetworkListener extends Thread{
    InputAdapter adapter;
    
    public NetworkListener(InputAdapter adapter){
        this.adapter = adapter;
    }
    
    @Override
    public void run(){
        try{
            ServerSocket sock = new ServerSocket(2002);
            while(true){//while logged in
                Socket client = sock.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                String ipAddress = client.getInetAddress().toString().split("/")[1];

                String message = in.readLine();
                System.out.println("INBOUND: " + message);

                adapter.parse(message, ipAddress);

                in.close();
                client.close();
            }
        }
        catch(IOException e){
            System.out.println(e);
        }
    }
}
