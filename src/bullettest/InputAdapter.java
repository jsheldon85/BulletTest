package bullettest;

public class InputAdapter {
    Boundary boundary;
    
    public InputAdapter(Boundary boundary){
        this.boundary = boundary;
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
                boundary.updateJoinableGames(params[1].split(","));
                break;
            case("createObject"): //posY, velX, velY
                System.out.println("createObject");

                float[] floatParams = new float[(params.length-2)];
                for(int i=2;i<params.length;i++){
                    floatParams[i-1] = Float.parseFloat(params[i]);
                }
                createObject(floatParams[0],floatParams[1],floatParams[2]);
                break;
        }
    }
    
    private void updateSide(String hostIP, Machine node){
        boundary.updateSide(hostIP, node);
    }
    
    private void removeSet(String hostIP){
        boundary.removeSet(hostIP);
    }
    
    private void createObject(float posY, float velX, float velY){ //we should probably pass posX now, too, or we'll have to find the distance in boundary based on the IP it came from
        //Create the object in JBox2D
    }
}
