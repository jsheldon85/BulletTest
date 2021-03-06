/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bullettest;

import java.awt.GraphicsEnvironment;
import java.awt.event.KeyEvent;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.testbed.framework.TestbedSettings;
import org.jbox2d.testbed.framework.TestbedTest;
import javax.swing.JOptionPane;
import java.awt.Toolkit;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.EdgeShape;


/**
 *
 * @author vsc243
 */

public class Test extends TestbedTest {
    Body body;
    Body bomb;
    double screenWidthMeters;
    double screenHeightMeters;
    Boundary boundary;
    float oldBomb_x = 0f;
    
    public Test(Boundary boundary){
        this.boundary = boundary;
    }
    
    public void initTest(boolean argDeserialized) {
        screenHeightMeters = pixelToMeter(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height);
        screenWidthMeters = pixelToMeter(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width);
        System.out.println("Screen width in meters:" + screenWidthMeters);
        System.out.println("Screen height in meters:" + screenHeightMeters);
        setTitle("Angry Physics");
        
        getWorld().setGravity(new Vec2(0, -19.6f));
        float timeStep = 1.0f / 60.f;
        int velocityIterations = 10;
        int positionIterations = 8;
        //getWorld().Step(timeStep, velocityIterations, positionIterations);
    
        BodyDef bd = new BodyDef();
        Body ground = getWorld().createBody(bd);
        EdgeShape shape = new EdgeShape();
        shape.set(new Vec2((float)(-screenWidthMeters/2), -30.0f), new Vec2((float)(screenWidthMeters/2), -30.0f));
        ground.createFixture(shape, 0.0f);
        

    for (int i = 0; i < 10; i++) {
        CircleShape polygonShape = new CircleShape();
        polygonShape.setRadius(1);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;
        bodyDef.position.set(0, 0-i*2);
        bodyDef.angle = (float) (Math.PI / 4 * i);
        bodyDef.allowSleep = false;
        body = getWorld().createBody(bodyDef);
        body.createFixture(polygonShape, 5f);
        body.getFixtureList().setRestitution(.75f);
    }
        
        


        //body.applyLinearImpulse(new Vec2(0, 500), new Vec2(getWorld().getBodyList().getWorldCenter()));
      

    }
    
    @Override
    public void keyPressed(char keyCar, int keyCode){
        System.out.println(keyCode);
        if (body != null){
            switch(keyCode){
                case KeyEvent.VK_UP:
                    body.applyLinearImpulse(new Vec2(0, 200), new Vec2(body.getWorldCenter()));
                    break;
                case KeyEvent.VK_RIGHT:
                    body.applyLinearImpulse(new Vec2(200, 0), new Vec2(body.getWorldCenter()));
                    break;
                case KeyEvent.VK_DOWN:
                    body.applyLinearImpulse(new Vec2(0, -200), new Vec2(body.getWorldCenter()));
                    break;
                case KeyEvent.VK_LEFT:
                    body.applyLinearImpulse(new Vec2(-200, 0), new Vec2(body.getWorldCenter()));
                    break;
                case KeyEvent.VK_PERIOD:
                    if(bomb != null)System.out.println(bomb.getWorldCenter());
                    break;
                    
        }
        }
    }
    
    /*@Override
    public void lanchBomb() {
        super.lanchBomb();
        Body newBomb = getBomb();
        newBomb.applyLinearImpulse(new Vec2(5000, 0), new Vec2(body.getWorldCenter()));
          //fix this, but yay for re-figuring out super.!
    }*/
      
    @Override
    public synchronized void launchBomb(Vec2 position, Vec2 velocity){
        //Toolkit.getDefaultToolkit().beep();
        super.launchBomb(position, velocity);
        bomb = getBomb();
        bomb.getFixtureList().setRestitution(.5f);
        //bomb.applyLinearImpulse(new Vec2(0f, 0f), new Vec2(body.getWorldCenter()));
        //System.out.println(bomb.getWorldCenter());
//        System.out.println(getWorld());
        //System.out.println(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());
    }
    
    @Override
    public synchronized void step(TestbedSettings settings){
        super.step(settings);
        if (bomb != null){
//            if (    bomb.getWorldCenter().x > screenWidthMeters / 2
//                    || bomb.getWorldCenter().x < screenWidthMeters / 2
//                    || bomb.getWorldCenter().y < -37
//                    || bomb.getWorldCenter().y > 58){
//
//                int error = 1/0;
//            }
            
            //bombHitEdgeLogic();
            sendBullet();
            //System.out.println(bomb.getWorldCenter());
        }
    }
    
    private double pixelToMeter(double pixels){
        return pixels * .1;
    }
    
    /*private boolean isBombOffscreen(){
        return (   bomb.getWorldCenter().x > screenWidthMeters / 2
                || bomb.getWorldCenter().x < -(screenWidthMeters / 2)); //Don't care about y, only x
    }*/
    
    private void sendBullet(){
        float bomb_x = bomb.getWorldCenter().x;
        
        String ip = (bomb.m_linearVelocity.x < 0)? boundary.getLeftAddress(oldBomb_x, bomb_x) : boundary.getRightAddress(oldBomb_x, bomb_x);
        if(!ip.equals("nullIP")){
            System.out.println("sendBullet!");
            OutputAdapter.sendObject(ip, bomb.getWorldCenter().x, bomb.getWorldCenter().y, bomb.getLinearVelocity().x , bomb.getLinearVelocity().y);
        }
        oldBomb_x = bomb_x;
    }
    
    /*private void bombHitEdgeLogic(){
        if(isBombOffscreen()){
            JOptionPane.showMessageDialog(null, "Linear Velocity is: " + bomb.m_linearVelocity);
            getWorld().destroyBody(bomb);
            bomb = null;
        }
    }*/

  @Override
  public String getTestName() {
    return "Couple of Things";
  }
}


