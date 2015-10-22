//Crate class for representing the crates of produce in the stack
public class Crate implements Comparable<Crate>{
    int expDate; //holds expiration date
    int produceStart; //holds the number of produce the crate started with
    int produceRemain; //holds the number of produce remaining
    double crateCost; //holds the cost of the crate
    
    /**creates a crate and sets each value in it.
     * @param int of expiration date, int of produce count, double of cost
     */
    public Crate(int date, int count, double cost){
        expDate=date;
        produceStart=count;
        produceRemain=count;
        crateCost=cost;
    }
    
    /**returns the remaining number of produce
     * @return int of remaining produce
     */
    public int getRemaining(){
            return produceRemain;
    }
    
    /**returns the starting number of produce
     * @returns int of starting produce
     */
    public int getStart(){
        return produceStart;
    }
    
    /**tells if crate is empty
     * @returns true if empty, false if not
     */
    public boolean isEmpty(){
        if(getRemaining()==0){
            return true;
        }
        else{
            return false;
        }
    }
    
    /**removes produce
     * @returns false if empty, true if not and removes from produce remaining
     */
    public boolean removeProduce(){
        if(isEmpty()){
            return false;
        }
        else{
            produceRemain--;
            return true;
        }
    }
    
    /**gets expiration date
     * @returns int of expDate
     */
    public int getDate(){
        return expDate;
    }
    
    /**gets cost of crate
     * @returns double of cost
     */
    public double getCost(){
        return crateCost;
    }
    
    /**returns a formatted String of the crate
     * @returns String of crate with all data
     */
    public String toString(){
        return "Expires:"+getDate()+"  Start Count:"+getStart()+"  Remain:"+getRemaining()+"  Cost:"+getCost();
    }
    
    /**compares one crate to another by expiration date
     * @returns int, negative if second crate has a larger expiration date, positive if first crate does, 0 if the same
     */
    public int compareTo(Crate other){
        return getDate()-other.getDate();
    }

}
