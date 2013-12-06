package bullettest;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class BoundaryTest {
    Boundary boundary;
    Machine node0;
    Machine node1;
    Machine node2;
    
    public BoundaryTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        boundary = new Boundary();
        //System.out.println(boundary.getPresentGamesIPList());
        node0 = new Machine("0.0.0.0", 0.1);
        node1 = new Machine("0.0.0.0", 1);
        node2 = new Machine("0.0.0.2", -2);
    }
    
    @After
    public void tearDown() {
    }
/*
    @Test
    public void testBoundary() {
        
    }
*/

    @Test
    public void testGetPresentGamesIPList() {
        boundary.updateSide(node0.ip, node0);
        String[] expResult = {node0.ip};
        assert(stringArraysEqual(expResult, boundary.getPresentGamesIPList()));
    }

    @Test
    public void testUpdateSide() {
        boundary.updateSide(node0.ip, node0);
        String[] expPresentGames0 = {node0.ip};
        assert(stringArraysEqual(boundary.getPresentGamesIPList(),expPresentGames0));
        assert(boundary.getRightAddress(node0.distance).equals(node0.ip));
        assert(boundary.getLeftAddress(node0.distance).equals("nullIP"));
        
        boundary.updateSide(node1.ip, node1);
        String[] expPresentGames1 = {node1.ip};
        assert(stringArraysEqual(boundary.getPresentGamesIPList(),expPresentGames1));
        assert(boundary.getRightAddress(node1.distance).equals(node1.ip));
        assert(boundary.getLeftAddress(node1.distance).equals("nullIP"));
        
        boundary.updateSide(node2.ip, node2);
        String[] expPresentGames2 = {node1.ip, node2.ip};
        assert(stringArraysEqual(boundary.getPresentGamesIPList(),expPresentGames2));
        assert(boundary.getRightAddress(node1.distance).equals(node1.ip));
        assert(boundary.getLeftAddress(node1.distance).equals("nullIP"));
        assert(boundary.getLeftAddress(node2.distance).equals(node2.ip));
    }

    @Test
    public void testRemoveSet() {
        boundary.updateSide(node0.ip, node0);
        boundary.updateSide(node2.ip, node2);
        String[] expPresentGames = {node0.ip, node2.ip};
        assert(stringArraysEqual(boundary.getPresentGamesIPList(),expPresentGames));
        
        boundary.removeSet(node0.ip);
        String[] expPresentGames1 = {node2.ip};
        assert(stringArraysEqual(boundary.getPresentGamesIPList(),expPresentGames1));
        
        boundary.removeSet(node2.ip);
        String[] expPresentGames2 = {};
        assert(stringArraysEqual(boundary.getPresentGamesIPList(),expPresentGames2));
        //remove from nothing
        boundary.removeSet(node2.ip);
        assert(stringArraysEqual(boundary.getPresentGamesIPList(),expPresentGames2));
    }

    @Test
    public void testUpdateJoinableGames() {
    }

    @Test
    public void testGetLeftAddress() {
        boundary.updateSide(node2.ip, node2);
        assert(boundary.getLeftAddress(node2.distance).equals(node2.ip));
    }

    @Test
    public void testGetRightAddress() {
        boundary.updateSide(node0.ip, node0);
        assert(boundary.getRightAddress(node0.distance).equals(node0.ip));
        boundary.updateSide(node2.ip, node2);
        assert(boundary.getRightAddress(node0.distance).equals(node0.ip));
    }
    
    private boolean stringArraysEqual(String[] actual, String[] expected){
        if(expected.length!=actual.length) return false;
        for(int i=0; i<expected.length; i++){
            if(!expected[i].equals(actual[i])) return false;
        }
        return true;
    }
}