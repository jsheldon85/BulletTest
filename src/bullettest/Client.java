/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bullettest;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import javax.swing.JFrame;
import org.jbox2d.testbed.framework.TestbedController;
import org.jbox2d.testbed.framework.TestbedFrame;
import org.jbox2d.testbed.framework.TestbedModel;
import org.jbox2d.testbed.framework.TestbedPanel;
import org.jbox2d.testbed.framework.TestbedSetting;
import org.jbox2d.testbed.framework.j2d.TestPanelJ2D;
import javax.swing.JOptionPane;


/**
 *
 * @author vsc243
 */
public class Client {
    
    private static String absDistance;
    private static FileAdapter adapter;
    private static Boundary boundary = new Boundary();
    
    public static String getAbsDistance(){
        return absDistance;
    }
    
    public static void setAbsDistance(String newDistance){
        //TO USE DIALOG BOX PASS IN EMPTY STRING
        while(newDistance.equals("")){
            newDistance = JOptionPane.showInputDialog("Input absolute distance");
            try{
                Float.parseFloat(newDistance);
            }
            catch(Exception e){
                JOptionPane.showMessageDialog(new JFrame(), "Distance must be a valid decimal number");
                newDistance = "";
            }
        }
        absDistance = newDistance;
        adapter.writeDistance(absDistance);
    }
    
    public static void main(String[] args){
        
        adapter = new FileAdapter();
        String fileDistance = adapter.initialRead();
        setAbsDistance(fileDistance);
        
        GraphicsDevice gdevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        TestbedModel model = new TestbedModel();
        //model.setPanelWidth(5000);
        Test test = new Test();
        //test.initTest(true);
        model.addCategory("Test tests");
        model.addTest(test);
        TestbedPanel panel = new TestPanelJ2D(model);
        JFrame testbed = new TestbedFrame(model, panel, TestbedController.UpdateBehavior.UPDATE_CALLED);
        //testbed.setSize(new Dimension(1920,1080));
        testbed.remove(testbed.getContentPane().getComponent(1));
        
        
//        if(gdevice.isFullScreenSupported()){
//             gdevice.setFullScreenWindow(testbed);
//           }

        testbed.setVisible(false);
        testbed.setVisible(true);
        
        System.out.println(testbed.getAccessibleContext().getAccessibleComponent().getBounds());
        //testbed.getAccessibleContext().getAccessibleComponent().setBounds(new Rectangle(0,0,1920,1080));
        testbed.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
}
