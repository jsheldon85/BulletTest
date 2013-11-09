package bullettest;

public class Boundary {
    private static Machine leftNode;
    private static Machine rightNode;
    
    public static void Boundary(){
        leftNode = new Machine("", 0);
        rightNode = new Machine("", 0);
    }
    
    protected static void setMachine(boolean isRight, Machine node){
        if(isRight) rightNode = node;
        else leftNode = node;
    }
    
    protected static void removeMachine(boolean isRight){
        Machine node = new Machine("", 0);
        if(isRight) rightNode = node;
        else leftNode = node;
    }
    
    public static float getRightDistance(){
        return rightNode.distance;
    }
    
    public static float getLeftDistance(){
        return leftNode.distance;
    }
}
