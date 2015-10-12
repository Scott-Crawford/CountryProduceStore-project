public class Crate implements Comparable<Crate>{
    int expDate;
    int produceStart;
    int produceRemain;
    double crateCost;
    
    public Crate(int date, int count, double cost){
        expDate=date;
        produceStart=count;
        produceRemain=count;
        crateCost=cost;
    }
    
    public int getRemaining(){
        return produceRemain;
    }
    
    public int getStart(){
        return produceStart;
    }
    
    public boolean isEmpty(){
        if(getRemaining()==0){
            return true;
        }
        else{
            return false;
        }
    }
    
    public boolean removeProduce(){
        if(isEmpty()){
            return false;
        }
        else{
            produceRemain--;
            return true;
        }
    }
    
    public int getDate(){
        return expDate;
    }
    
    public double getCost(){
        return crateCost;
    }
    
    public String toString(){
        return "Expires:"+getDate()+"  Start Count:"+getStart()+"  Remain:"+getRemaining()+"  Cost:"+getCost();
    }
    
    public int compareTo(Crate other){
        return Integer.toString(getDate()).compareTo(Integer.toString(other.getDate()));
    }

}
