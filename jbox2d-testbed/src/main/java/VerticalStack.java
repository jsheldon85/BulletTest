/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author vsc243
 */
import com.sun.xml.internal.ws.wsdl.writer.document.soap.Body;
import com.sun.xml.internal.ws.wsdl.writer.document.soap.BodyType;
import org.jbox2d.testbed.framework.TestbedSettings;
import org.jbox2d.testbed.framework.TestbedTest;

public class VerticalStack extends TestbedTest {
  private static final long BULLET_TAG = 1;
  
  public static final int e_columnCount = 5;
  public static final int e_rowCount = 16;

  Body m_bullet;
  
  @Override
  public Long getTag(Body argBody) {
    if(argBody == m_bullet){
      return BULLET_TAG;
    }
    return super.getTag(argBody);
  }
  
  @Override
  public void processBody(Body argBody, Long argTag) {
    if(argTag == BULLET_TAG){
      m_bullet = argBody;
      return;
    }
    super.processBody(argBody, argTag);
  }
  
  @Override
  public boolean isSaveLoadEnabled() {
    return true;
  }

  @Override
  public void initTest(boolean deserialized) {
    if(deserialized){
      return;
    }
    {
      BodyDef bd = new BodyDef();
      Body ground = getWorld().createBody(bd);

      PolygonShape shape = new PolygonShape();
      shape.setAsEdge(new Vec2(-40.0f, 0.0f), new Vec2(40.0f, 0.0f));
      ground.createFixture(shape, 0.0f);

      shape.setAsEdge(new Vec2(20.0f, 0.0f), new Vec2(20.0f, 20.0f));
      ground.createFixture(shape, 0.0f);
    }

    float xs[] = new float[] { 0.0f, -10.0f, -5.0f, 5.0f, 10.0f };

    for (int j = 0; j < e_columnCount; ++j) {
      PolygonShape shape = new PolygonShape();
      shape.setAsBox(0.5f, 0.5f);

      FixtureDef fd = new FixtureDef();
      fd.shape = shape;
      fd.density = 1.0f;
      fd.friction = 0.3f;

      for (int i = 0; i < e_rowCount; ++i) {
        BodyDef bd = new BodyDef();
        bd.type = BodyType.DYNAMIC;
        int n = j * e_rowCount + i;
        float x = 0.0f;
        bd.position.set(xs[j] + x, 0.752f + 1.54f * i);
        Body body = getWorld().createBody(bd);

        body.createFixture(fd);
      }
    }

    m_bullet = null;
  }

  @Override
  public void keyPressed(char argKeyChar, int argKeyCode) {
    switch (argKeyChar) {
      case ',':
        if (m_bullet != null) {
          getWorld().destroyBody(m_bullet);
          m_bullet = null;
        }

        {
          CircleShape shape = new CircleShape();
          shape.m_radius = 0.25f;

          FixtureDef fd = new FixtureDef();
          fd.shape = shape;
          fd.density = 20.0f;
          fd.restitution = 0.05f;

          BodyDef bd = new BodyDef();
          bd.type = BodyType.DYNAMIC;
          bd.bullet = true;
          bd.position.set(-31.0f, 5.0f);

          m_bullet = getWorld().createBody(bd);
          m_bullet.createFixture(fd);

          m_bullet.setLinearVelocity(new Vec2(400.0f, 0.0f));
        }
        break;
    }
  }

  @Override
  public void step(TestbedSettings settings) {
    super.step(settings);
    addTextLine("Press ',' to launch bullet.");
  }

  @Override
  public String getTestName() {
    return "Vertical Stack";
  }
}
