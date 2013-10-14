/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bullettest;

import java.awt.Component;
import java.awt.Rectangle;
import javax.swing.JFrame;
import org.jbox2d.testbed.framework.TestbedController;
import org.jbox2d.testbed.framework.TestbedFrame;
import org.jbox2d.testbed.framework.TestbedModel;
import org.jbox2d.testbed.framework.TestbedPanel;
import org.jbox2d.testbed.framework.j2d.TestPanelJ2D;


/**
 *
 * @author vsc243
 */
public class TestbedMain {
      public static void main(String[] args){
      TestbedModel model = new TestbedModel();
      //model.setPanelWidth(5000);
      VerticalStack test = new VerticalStack();
      //test.initTest(true);
      model.addCategory("Test tests");
      model.addTest(test);
      TestbedPanel panel = new TestPanelJ2D(model);
      JFrame testbed = new TestbedFrame(model, panel, TestbedController.UpdateBehavior.UPDATE_CALLED);
      testbed.remove(testbed.getContentPane().getComponent(1));
      testbed.setVisible(true);
      System.out.println(testbed.getAccessibleContext().getAccessibleComponent().getBounds());
      //testbed.getAccessibleContext().getAccessibleComponent().setBounds(new Rectangle(0,22,2170,762));
      testbed.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
    }
}
