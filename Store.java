import java.io.*;
import java.util.Scanner;
@SuppressWarnings("unchecked")
public class Store
{
    int globalClock; //records date
    StackInterface produceStack; //first stack, holding most produce
    StackInterface tempStack; //temporary stack only for receiving crates
    Scanner info; //scanner for reading a file or user input
    Crate counterCrate; //holds Crate of the current crate in use
    boolean useFile; //records if a file is in use or not
    int moves; //records moves needed in most recent shipment
    int totalMoves; //records moves needed across all shipments
    int recentCrates; //records number of crates received in most recent shipment
    int totalCrates; //records number of crates received across all shipments
    double recentProduceCost; //records cost of crates in most recent shipment
    double totalProduceCost; //records cost of crates across all shipments
    double recentTotalCost; //records cost of labor and crates in most recent shipment
    double totalCost; //records cost of labor and crates across all shipments
    
    /**creates a stores and sets the variables
     * @param Scanner for either user input or reading a file, boolean to tell if it a file is being used
     */
    public Store(Scanner input,boolean x){
        globalClock=0;
        produceStack=new LinkedStack<Crate>();
        tempStack=new LinkedStack<Crate>();
        info=input;
        useFile=x;
        moves=0;
        totalMoves=0;
        recentCrates=0;
        totalCrates=0;
        recentProduceCost=0;
        totalProduceCost=0;
        recentTotalCost=0;
        totalCost=0;
    }
    
    /**receives a number of crates and sorts them into the stack
     * @param int of number of crates being received
     * @returns String everything printed to System
     */
    public String receive(int num){
        String out="Receiving "+num+" crates of produce.";
          
        System.out.println("Receiving "+num+" crates of produce.");
        
        int a;
        int b;
        double c;
        Crate temp;
        moves=0;
        recentCrates=num;
        totalCrates=totalCrates+num;
        recentProduceCost=0;
        recentTotalCost=0;
        for(int i=0;i<num;i++){
            int x=i+1;
            if(!useFile){
                System.out.println("Enter crate "+x+"'s expiration date: ");
            }
            a=info.nextInt();
            if(!useFile){
                System.out.println("Enter crate "+x+"'s amount of produce: ");
            }
            b=info.nextInt();
            if(!useFile){
                System.out.println("Enter crate "+x+"'s cost: ");
            }
            c=info.nextDouble();
            temp=new Crate(a,b,c);
            recentProduceCost=recentProduceCost+temp.getCost();
            stackMoving(temp);//sends it to the stack
        }
        tempToTop();
        recentTotalCost=recentProduceCost+(double)moves;
        totalMoves=totalMoves+moves;
        totalProduceCost=totalProduceCost+recentProduceCost;
        totalCost=totalCost+recentTotalCost;
        System.out.println("\r\n");
        return out;
    }
   
    /**removes the number of produce from the counter crate to be sold, replaces crate if used up, indicates if store is out of produce
     * @param int of number of crates being sold
     * @returns String everything printed to System
     */
    public String sell(int x){
        String out=x+" produce needed for that order.";
        Pair temp=checkCounterCrate();
        if((boolean)temp.getFirst()){//gets true or false if there's a counter crate
            if(!("".equals(temp.getSecond()))){
                String tempStr="\r\n"+(String)temp.getSecond();
                out=out+tempStr;
            }
            if(x<=counterCrate.getRemaining()){
                for(int i=0;i<x;i++){
                    counterCrate.removeProduce();
                }
                out=out+"\r\n"+x+" produce used from current crate.";
            }
            else{
                out=out+crateSellRotate(x);
            }
        }
        else{//if there is no crates left
            out=out+"\r\nThe store is out of produce. There was "+x+" produce left in the order.";
        }
        
        System.out.print(out);
        System.out.println("\r\n");
        return out;
    }
    
     /**gives a printout of the crate on the counter and the crates in the stack
     * @returns String everything printed to System, in this case the list
     */
    public String display(){
        String out="";
        if(counterCrate!=null){//only if there is a crate on the counter
            
            System.out.print("\r\nCurrent crate: ");
            System.out.println(counterCrate);
            
            out=out+"Current crate: "+counterCrate;
        }
        if(!produceStack.isEmpty()){
            
            System.out.println("Stack crates (top to bottom):");
            
            out=out+"\r\nStack crates (top to bottom):";
        }
        else{
            
            System.out.println("No crates in the stack - please reorder!");
            
            out=out+"\r\nNo crates in the stack - please reorder!";
        }
        while(!produceStack.isEmpty()){//lists the crates by popping them into the temp and then putting the back when done
            tempStack.push(produceStack.pop());
            
            System.out.println(tempStack.peek());
            
            out=out+"\r\n"+tempStack.peek();
        }
        System.out.println("\r\n");
        tempToTop();
        return out;
    }
    
    /**moves the day counter up one and checks for expired crates
     * @returns String everything printed to System, including things that expired
     */
    public String skip(){
        String out="";
        globalClock++;
        
        System.out.println("It is now day "+globalClock+".");
        
        out="It is now day "+globalClock+".";
        Pair temp=checkCounterCrate();
        if((boolean)temp.getFirst()){
            if(!("".equals(temp.getSecond()))){
                String tempStr=(String)temp.getSecond();
                out=out+"\r\n"+tempStr;
            }
            if(checkExp(counterCrate)){
            
                System.out.println("Current crate: "+counterCrate+" is expired!");
                
                out=out+"\r\nCurrent crate: "+counterCrate+" is expired!";
                counterCrate=null;
            }
        }
        while(!produceStack.isEmpty()&&checkExp((Crate)produceStack.peek())){
            Crate ex=(Crate)produceStack.pop();
            
            System.out.println("Top crate: "+ex+" is expired!");
            
            out=out+"\r\nTop crate: "+ex+" is expired!";
        }
        System.out.println("\r\n");
        return out;
    }
    
    /**gives a printout of the most recent shipment and all shipments, including cost of crates and labor
     * @returns String everything printed to System, in this case the report
     */
    public String report(){
        String out="Country Produce Store Financial Statement:";
        out=out+"\r\n\tMost Recent Shipment:";
        out=out+"\r\n\t\tCrates: "+recentCrates;
        out=out+"\r\n\t\tProduce cost: "+recentProduceCost;
        out=out+"\r\n\t\tLabor (moves): "+moves;
        out=out+"\r\n\t\tLabor cost: "+(double)moves;
        out=out+"\r\n\t\t------------------------";
        out=out+"\r\n\t\tTotal: "+recentTotalCost;
        out=out+"\r\n\r\n\tOverall Expenses:";
        out=out+"\r\n\t\tCrates: "+totalCrates;
        out=out+"\r\n\t\tProduce cost: "+totalProduceCost;
        out=out+"\r\n\t\tLabor (moves): "+totalMoves;
        out=out+"\r\n\t\tLabor cost: "+(double)totalMoves;
        out=out+"\r\n\t\t------------------------";
        out=out+"\r\n\t\tTotal Cost: "+totalCost;
        
        System.out.println(out);
        System.out.println("\r\n");
        return out;
    }
    
    /**checks if crates are expired
     * @returns true if crate is expired, false if not
     */
    private boolean checkExp(Crate a){
        if(a.getDate()<globalClock){
            return true;
        }
        else{
            return false;
        }
    }
    
    /**moves the crates onto the stacks. checks if crate is newer than crates on the main stack or older than ones on the temp stack and places them where they go
     * @param Crate to be placed on the stack
     */
    private void stackMoving(Crate temp){
        if((produceStack.isEmpty()||0>=temp.compareTo((Crate)produceStack.peek()))&&(tempStack.isEmpty()||0<=temp.compareTo((Crate)tempStack.peek()))){
                moves++;
                produceStack.push(temp);   
        }
        else if(0<temp.compareTo((Crate)produceStack.peek())){
                moves++;
                tempStack.push(produceStack.pop());
                stackMoving(temp);
        }
        else if(0>temp.compareTo((Crate)tempStack.peek())){
                moves++;
                produceStack.push(tempStack.pop());
                stackMoving(temp);  
        }
        else{
                moves++;
                produceStack.push(temp);
        }
    }
    
    /**rotates through the stack if the current crate cannot fulfill the order on its own
     * @param int of produce to be sold
     */
    private String crateSellRotate(int x){
        String out="";
        Pair temp=checkCounterCrate();
        if((boolean)temp.getFirst()){
            if(!("".equals(temp.getSecond()))){
                String tempStr=(String)temp.getSecond();
                out=out+tempStr;
            }
            if(x>counterCrate.getRemaining()){
                int r=counterCrate.getRemaining();
                for(int i=0;i<r;i++){
                    counterCrate.removeProduce();
                }
                out=out+"\r\n"+r+" produce used from current crate.";
                int m=x-r;
                out=out+"\r\n"+crateSellRotate(m);//recursion
                
            }
            else if(x<=counterCrate.getRemaining()){
                for(int i=0;i<x;i++){
                    counterCrate.removeProduce();
                }
                out=out+"\r\n"+x+" produce used from current crate.";
            }
        }
        else{
            out=out+"\r\nThe Store is out of produce. There was "+x+" produce left in the order.";
        }
        return out;
    }
    
    /**
     * moves the temp stack to the top of the produce stack one at a time
     */
    private void tempToTop(){
        while(!tempStack.isEmpty()){
            moves++;
            produceStack.push(tempStack.pop());
        }
    }
    /**checks if there is a crate on the counter or if it can be replaced
     * @return Pair of boolean and String, true if there is a crate or if it can be replaced, false if it can't, and a String for getting crate
     */
    private Pair checkCounterCrate(){
        Pair x=new Pair<Boolean,String>(true,"");
        if(counterCrate==null||counterCrate.isEmpty()){
            if(produceStack.isEmpty()){
                x.changeFirst(false);
                return x;
            }
            else{
                counterCrate=(Crate)produceStack.pop();
                
                x.changeFirst(true);
                x.changeSecond("Getting crate: "+counterCrate);
                return x;
            }
        }
        x.changeFirst(true);
        return x;
    }
    

}
