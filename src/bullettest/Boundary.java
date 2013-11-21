package bullettest;

import java.util.ArrayList;

public class Boundary {

    private ArrayList<String> presentGamesIPList;
    private ArrayList<String> joinableGamesIPList;
    private ArrayList<Machine> leftMachines;
    private ArrayList<Machine> rightMachines;
    
    public void Boundary(){
        presentGamesIPList = new ArrayList();
        leftMachines = new ArrayList();
        rightMachines = new ArrayList();
    }
    
    public void updateSide(String hostIP, Machine node){ //node is new peer
        int index = presentGamesIPList.indexOf(hostIP);
        double newDistance = node.distance;
        if(index==-1){
            presentGamesIPList.add(hostIP);
            if(newDistance < 0){
                leftMachines.add(node);
            } else {
                rightMachines.add(node);
            }
        }
        else{
            if(newDistance < 0){
                leftMachines.set(index, node);
            } else {
                rightMachines.set(index, node);
            }
        }
    }
    
    public void updateJoinableGames(String[] newIPList){
        ArrayList<String> newJoinableGamesIPList = new ArrayList();
        for(String ip : newIPList){
            if(!joinableGamesIPList.contains(ip)) newJoinableGamesIPList.add(ip);
        }
        joinableGamesIPList = newJoinableGamesIPList;
    }
    
    public void removeSet(String hostIP){
        int index = presentGamesIPList.indexOf(hostIP);
        if(index!=-1){
            presentGamesIPList.remove(index);
            leftMachines.remove(index);
            rightMachines.remove(index);
        }
    }
    
    public String getLeftAddress(double distance){
        return getAddress(distance, false);
    }
    
    public String getRightAddress(double distance){
        return getAddress(distance, true);
    }
    
    private String getAddress(double distance, boolean isRight){
        ArrayList<Machine> machines = isRight? rightMachines: leftMachines;
        int index=0;
        for(; index<machines.size(); index++){
            if(machines.get(index).distance==distance) break;
        }
        if(index==machines.size()) return "";
        return machines.get(index).ip;
    }
}
