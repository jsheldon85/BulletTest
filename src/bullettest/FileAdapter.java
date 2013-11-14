package bullettest;

import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;


public class FileAdapter {
    
    PrintWriter out;
    BufferedReader in;
    
    public FileAdapter(){}

    
    public void writeDistance(String absDistance){
        try{
            //Will create file if it doesn't exist
            out = new PrintWriter("distance.txt");
        }
        catch(Exception e){
            System.out.println("Error creating PrintWriter!");
        }
        out.println(absDistance);
        out.close();
    }
    
    public String initialRead(){
        try{
            in = new BufferedReader(new FileReader(new File("distance.txt")));
            String distance = in.readLine();
            Float.parseFloat(distance);
            in.close();
            return distance;
        }
        catch(Exception e){
            //System.out.println(e);
            return "";
        }
    }
    
    public void closeFile(){
        out.close();
    }
}
