package bullettest;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

class NetworkPusher extends Thread{
    LinkedBlockingQueue<ArrayList<String>> toUpdate;
    
    NetworkPusher(LinkedBlockingQueue<ArrayList<String>> toUpdate) {
        this.toUpdate = toUpdate;
   }

    @Override
    public void run() {
        while (true){
            ArrayList<String> message = null;
            try {
                message = toUpdate.take();
            }
            catch (InterruptedException ex) {
                Logger.getLogger(NetworkPusher.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                Socket s = null;
                if (message.get(0).equals("true")) s = new Socket(message.get(1), 2004);  //Clients on 2002 Servers on 2004
                else                               s = new Socket(message.get(1), 2002);
                PrintWriter out = new PrintWriter(s.getOutputStream(), true);

                System.out.println("OUT:  "+message.get(2));
                out.println(message.get(2));

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
}