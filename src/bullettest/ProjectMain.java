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


/**
 *
 * @author vsc243
 */
public class ProjectMain {
    
    public static void main(String[] args){
        
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
