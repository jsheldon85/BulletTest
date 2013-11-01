package bullettest;

public class Node {
    private float distance;
    private String ip;
    private boolean isRight;
    
    public Node(String ip, float distance){
        this.ip = ip;
        this.distance = distance;
        isRight = distance>=0? true:false;
    }
    
    protected void setDistance(float distance){
        this.distance = distance;
    }
    
    public float getDistance(){
        return distance;
    }
    
    protected void setIp(String ip){
        this.ip = ip;
    }
    
    public String getIp(){
        return ip;
    }
    
    protected void setIsRight(boolean isRight){
        this.isRight = isRight;
    }
    
    public boolean getIsRight(){
        return isRight;
    }
}
