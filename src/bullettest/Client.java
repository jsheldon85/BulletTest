/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bullettest;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import org.jbox2d.testbed.framework.TestbedController;
import org.jbox2d.testbed.framework.TestbedFrame;
import org.jbox2d.testbed.framework.TestbedModel;
import org.jbox2d.testbed.framework.TestbedPanel;
import org.jbox2d.testbed.framework.TestbedSetting;
import org.jbox2d.testbed.framework.j2d.TestPanelJ2D;
import javax.swing.JOptionPane;
import javax.swing.*;


/**
 *
 * @author vsc243
 */
public class Client {
    
    private static String absDistance;
    private static FileAdapter adapter;
    private static Boundary boundary;
    private static ButtonListener listener;
    private static OutputAdapter output;
    private static InputAdapter input;
    
    public static void setAbsDistance(String newDistance){
        //TO USE DIALOG BOX PASS IN EMPTY STRING
        while(newDistance.equals("")){
            newDistance = JOptionPane.showInputDialog("Input absolute distance");
            if(newDistance==null) return;
            try{
                Float.parseFloat(newDistance);
            }
            catch(Exception e){
                JOptionPane.showMessageDialog(new JFrame(), "Distance must be a valid decimal number");
                newDistance = "";
            }
        }
        absDistance = newDistance;
        System.out.println("newDistance= "+absDistance);
        adapter.writeDistance(absDistance);
    }
    
    public static void main(String[] args){
        //Instantiate instance vars
        boundary = new Boundary();
        adapter = new FileAdapter();
        listener = new ButtonListener();
        input = new InputAdapter(boundary);
        OutputAdapter.start();
        
        //Check file for distance and request input if necessary
        String fileDistance = adapter.initialRead();
        setAbsDistance(fileDistance);
        //GraphicsDevice gdevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice(); //Don't need this but keeping just in case
        
        //Set up test and add to model
        TestbedModel model = new TestbedModel();
        Test test = new Test();
        model.addCategory("Test tests");
        model.addTest(test);
        
        //Create physics engine GUI components
        TestbedPanel panel = new TestPanelJ2D(model);
        JFrame testbed = new TestbedFrame(model, panel, TestbedController.UpdateBehavior.UPDATE_CALLED);
        testbed.remove(testbed.getContentPane().getComponent(1));
        
        //Create buttons and add action listener
        JButton hostGameButton = new JButton("Host Game");
        hostGameButton.addActionListener(listener);
        JButton joinGameButton = new JButton("Join Game");
        joinGameButton.addActionListener(listener);
        JButton leaveGameButton = new JButton("Leave Game");
        leaveGameButton.addActionListener(listener);
        JButton changeDistanceButton = new JButton("Change Distance");
        changeDistanceButton.addActionListener(listener);
        JButton helpButton = new JButton("Help");
        helpButton.addActionListener(listener);
        
        //Create + add toolbar and add buttons
        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        toolbar.add(hostGameButton);
        toolbar.add(joinGameButton);
        toolbar.add(leaveGameButton);
        toolbar.add(changeDistanceButton);
        toolbar.add(helpButton);
        testbed.add(toolbar, "North");
        
        //EVERYTHING IS BROKEN!!!!!!!!!!!!!!!!!!! jk fixed it
        testbed.setVisible(true);
        
        System.out.println(testbed.getAccessibleContext().getAccessibleComponent().getBounds());
        //testbed.getAccessibleContext().getAccessibleComponent().setBounds(new Rectangle(0,0,1920,1080));
        testbed.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
    
    private static class ButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            switch(e.getActionCommand()){
                case("Host Game"):
                    System.out.println("Host Game Button");
                    //Toolkit.getDefaultToolkit().beep();
                    output.hostGame(absDistance);
                    break;
                case("Join Game"):
                    System.out.println("Join Game Button");
                    output.joinGame("0.0.0.0", absDistance);
                    break;
                case("Leave Game"):
                    System.out.println("Leave Game Button");
                    output.leaveGame("0.0.0.0");
                    break;
                case("Change Distance"):
                    System.out.println("Change Distance Button");
                    setAbsDistance("");
                    output.changeDistance(absDistance);
                    break;
                case("Help"):
                    System.out.println("Help Button");
                    break;
            }
        }
        
    }
}
