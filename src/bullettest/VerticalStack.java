/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bullettest;

import java.awt.event.KeyEvent;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.testbed.framework.TestbedTest;
import static java.lang.Math.*;


/**
 *
 * @author vsc243
 */





public class VerticalStack extends TestbedTest {
    Body body;
    
    public void initTest(boolean argDeserialized) {
    
        setTitle("Vertical Stack");

        getWorld().setGravity(new Vec2(0, 0));
    

    //for (int i = 0; i < 10; i++) {
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(1, 1);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;
        //bodyDef.position.set(-10, 0);
        //bodyDef.angle = (float) (Math.PI / 4 * i);
        bodyDef.allowSleep = false;
        body = getWorld().createBody(bodyDef);
        body.createFixture(polygonShape, 5f);

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
                    
                    
        }
        }
    }
    
    @Override
    public void lanchBomb() {
        super.lanchBomb();
        bomb = getBomb(); //fix this, but yay for re-figuring out super.!
    }
      
    
    

  @Override
  public String getTestName() {
    return "Couple of Things";
  }
}


