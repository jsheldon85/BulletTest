package bullettest;

import java.util.ArrayList;

public class Boundary {

    private ArrayList<String> hostIPList;
    private ArrayList<String> ipAddresses;
    private ArrayList<Double> leftDistances;
    private ArrayList<Double> rightDistances;
    
    public void Boundary(){
        hostIPList = new ArrayList();
        ipAddresses = new ArrayList();
        
        leftDistances = new ArrayList();
        rightDistances = new ArrayList();
    }
    
    protected void setGroup(String hostIP, double leftDist, double rightDist){
        int index = hostIPList.indexOf(hostIP);
        if(index!=-1){
            leftDistances.set(index, leftDist);
            rightDistances.set(index, rightDist);
        }
        else{
            ipAddresses.add(hostIP);
            leftDistances.add(leftDist);
            rightDistances.add(rightDist);
        }
    }
    
    protected void updateSide(String hostIP, Machine node){ //node is new peer
        int index = ipAddresses.indexOf(hostIP);
        double newDistance = node.distance;
        if(index==-1){
            ipAddresses.add(hostIP);
            if(node.distance < 0){
                leftDistances.add(newDistance);
            } else {
                rightDistances.add(newDistance);
            }
        }
        else{
            if(node.distance < 0){
                leftDistances.set(index, newDistance);
            } else {
                rightDistances.set(index, newDistance);
            }
        }
    }
    
    protected void removeSet(String hostIP){
        int index = ipAddresses.indexOf(hostIP);
        if(index!=-1){
            hostIPList.remove(index);
            ipAddresses.remove(index);
            leftDistances.remove(index);
            rightDistances.remove(index);
        }
    }
    
    public String getLeftAddress(double distance){
        int index = leftDistances.indexOf(distance);
        if(index!=-1){
            return ipAddresses.get(index);
        }
        return "";
    }
    
    public String getRightAddress(double distance){
        int index = rightDistances.indexOf(distance);
        if(index!=-1){
            return ipAddresses.get(index);
        }
        return "";
    }
}
