package bullettest;

import java.util.ArrayList;

public class Boundary {

    private ArrayList<String> presentGamesIPList;
    private ArrayList<String> joinableGamesIPList;
    private ArrayList<Machine> leftMachines;
    private ArrayList<Machine> rightMachines;
    
    public Boundary(){
        presentGamesIPList = new ArrayList();
        joinableGamesIPList = new ArrayList();
        leftMachines = new ArrayList();
        rightMachines = new ArrayList();
    }
    
    public String[] getPresentGamesIPList(){
        String[] list = new String[presentGamesIPList.size()];
        presentGamesIPList.toArray(list);
        return list;
    }
    
    public String[] getjoinableGamesIPList(){
        String[] list = new String[joinableGamesIPList.size()];
        joinableGamesIPList.toArray(list);
        return list;
    }
    
    
    
    public void updateSide(String hostIP, Machine node){ //node is new peer
        int index = presentGamesIPList.indexOf(hostIP);
        if(index==-1) addInitialSet(hostIP, node);
        else{
            if(node.distance < 0) leftMachines.set(index, node);
            else rightMachines.set(index, node);
        }
    }
    
    private void addInitialSet(String hostIP, Machine node){
        presentGamesIPList.add(hostIP);
        boolean isRight = 0<node.distance;
        ArrayList<Machine> targetList = isRight? rightMachines:leftMachines;
        ArrayList<Machine> otherList = isRight? leftMachines:rightMachines;
        targetList.add(node);
        otherList.add(new Machine("nullIP",0));
    }
    
    public void removeSet(String hostIP){
        int index = presentGamesIPList.indexOf(hostIP);
        if(index!=-1){
            presentGamesIPList.remove(index);
            leftMachines.remove(index);
            rightMachines.remove(index);
        }
    }
    
    public void updateJoinableGames(String[] newIPList){
        ArrayList<String> newJoinableGamesIPList = new ArrayList();
        for(String ip : newIPList){
            if(!presentGamesIPList.contains(ip)) newJoinableGamesIPList.add(ip);
        }
        joinableGamesIPList = newJoinableGamesIPList;
    }
    
    public String getLeftAddress(double oldDistance, double newDistance){
        return getAddress(oldDistance, newDistance, false);
    }
    
    public String getRightAddress(double oldDistance, double newDistance){
        return getAddress(oldDistance, newDistance, true);
    }
    
    private String getAddress(double oldDistance, double newDistance, boolean isRight){
        ArrayList<Machine> machines = isRight? rightMachines:leftMachines;
        int index=0;
        for(; index<machines.size(); index++){
            double boundary = Math.abs(machines.get(index).distance);
            if(Math.abs(oldDistance)<boundary && boundary<=Math.abs(newDistance)) break;
        }
        if(index==machines.size()) return "nullIP";
        return machines.get(index).ip;
    }
}
