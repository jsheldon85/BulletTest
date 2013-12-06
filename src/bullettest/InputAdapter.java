package bullettest;

import org.jbox2d.common.Vec2;

public class InputAdapter {
    Boundary boundary;
    Test test;
    
    public InputAdapter(Boundary boundary, Test test){
        this.boundary = boundary;
        this.test = test;
        NetworkListener in = new NetworkListener(this);
        in.start();
    }
    
    //cmd | positionx | positiony | velocityx | velocityyyyyyyyyy!!!!!!
    public void parse(String message,String ipAddress){//General Form: command | param1 | param2 | ... | paramn
        String[] params = message.split(" \\| ");
        switch(params[0]){
            case("updateSide"): // hostIP, distance, ip
                System.out.println("updateSide");
                updateSide(params[1], new Machine(params[3],Float.parseFloat(params[2])));
                break;
            case("removeSet"): // hostIP
                System.out.println("removeSide");
                removeSet(params[1]);
                break;
            case("updateGames"):// n host IPs
                System.out.println("updateGames");
                if(params.length<2) boundary.updateJoinableGames(new String[]{}); //if no joinable games
                else boundary.updateJoinableGames(params[1].split(","));
                break;
            case("createObject"): //posX, posY, velX, velY
                System.out.println("createObject");
                createObject(ipAddress, Float.parseFloat(params[1]),Float.parseFloat(params[2]),Float.parseFloat(params[3]), Float.parseFloat(params[4]));
                break;
        }
    }
    
    private void updateSide(String hostIP, Machine node){
        boundary.updateSide(hostIP, node);
    }
    
    private void removeSet(String hostIP){
        boundary.removeSet(hostIP);
    }
    
    private void createObject(String ip, float posX, float posY, float velX, float velY){ //we should probably pass posX now, too, or we'll have to find the distance in boundary based on the IP it came from
        //float delta =(float)(velX<0? boundary.getRightDistance(ip) : boundary.getLeftDistance(ip));
        //System.out.println(delta);
        test.launchBomb(new Vec2(velX*-1, posY), new Vec2(velX, velY));
    }
}
