import java.io.*;
import java.util.Scanner;
@SuppressWarnings("unchecked")
public class Store
{
    int globalClock;
    StackInterface produceStack;
    StackInterface tempStack;
    Scanner info;
    Crate counterCrate;
    boolean useFile;
    int moves;
    int totalMoves;
    int recentCrates;
    int totalCrates;
    double recentProduceCost;
    double totalProduceCost;
    double recentTotalCost;
    double totalCost;
    
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
    
    public String receive(int num){
        String out="Receiving "+num+" crates of produce.";
        if(!useFile){
            System.out.println("Receiving "+num+" crates of produce.");
        }
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
            stackMoving(temp);
        }
        tempToTop();
        recentTotalCost=recentProduceCost+(double)moves;
        totalMoves=totalMoves+moves;
        totalProduceCost=totalProduceCost+recentProduceCost;
        totalCost=totalCost+recentTotalCost;
        return out;
    }
   
    public String sell(int x){
        String out=x+" produce needed for that order.";
        Pair temp=checkCounterCrate();
        if((boolean)temp.getFirst()){
            if(!("".equals(temp.getSecond()))){
                String tempStr=(String)temp.getSecond();
                out=out+"\r\n"+tempStr;
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
        else{
            System.out.println("Sorry, we don't have that much produce. We have given you everything we have.");
        }
        if(!useFile){
                System.out.print(out);
            }
        return out;
    }
    
    public String display(){
        String out="";
        if(counterCrate!=null){
            if(!useFile){
                System.out.print("Current crate: ");
                System.out.println(counterCrate);
            }
            out=out+"Current crate: "+counterCrate;
        }
        if(!produceStack.isEmpty()){
            if(!useFile){
                System.out.println("Stack crates (top to bottom):");
            }
            out=out+"\r\nStack crates (top to bottom):";
        }
        else{
            if(!useFile){
                System.out.println("No crates in the stack - please reorder!");
            }
            out=out+"\r\nNo crates in the stack - please reorder!";
        }
        while(!produceStack.isEmpty()){
            tempStack.push(produceStack.pop());
            if(!useFile){
                System.out.println(tempStack.peek());
            }
            out=out+"\r\n"+tempStack.peek();
        }
        tempToTop();
        return out;
    }
    
    public String skip(){
        String out="";
        globalClock++;
        if(!useFile){
            System.out.println("It is now day "+globalClock+".");
        }
        out="It is now day "+globalClock+".";
        Pair temp=checkCounterCrate();
        if((boolean)temp.getFirst()){
            if(!("".equals(temp.getSecond()))){
                String tempStr=(String)temp.getSecond();
                out=out+"\r\n"+tempStr;
            }
            if(checkExp(counterCrate)){
                if(!useFile){
                    System.out.println("Current crate: "+counterCrate+" is expired!");
                }
                out=out+"\r\nCurrent crate: "+counterCrate+" is expired!";
                counterCrate=null;
            }
        }
        while(!produceStack.isEmpty()&&checkExp((Crate)produceStack.peek())){
            Crate ex=(Crate)produceStack.pop();
            if(!useFile){
                System.out.println("Top crate: "+ex+" is expired!");
            }
            out=out+"\r\nTop crate: "+ex+" is expired!";
        }
        return out;
    }
    
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
        if(!useFile){
            System.out.println(out);
        }
        return out;
    }
    
    public boolean checkExp(Crate a){
        if(a.getDate()<globalClock){
            return true;
        }
        else{
            return false;
        }
    }
    
    public void stackMoving(Crate temp){
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
    
    public String crateSellRotate(int x){
        String out="";
        Pair temp=checkCounterCrate();
        if((boolean)temp.getFirst()){
            if(!("".equals(temp.getSecond()))){
                String tempStr=(String)temp.getSecond();
                out=out+"\r\n"+tempStr;
            }
            if(x>counterCrate.getRemaining()){
                int r=counterCrate.getRemaining();
                for(int i=0;i<r;i++){
                    counterCrate.removeProduce();
                }
                out=out+"\r\n"+r+" produce used from current crate.";
                int m=x-r;
                out=out+"\r\n"+crateSellRotate(m);
                
            }
            else if(x<=counterCrate.getRemaining()){
                for(int i=0;i<x;i++){
                    counterCrate.removeProduce();
                }
                out=out+"\r\n"+x+" produce used from current crate.";
            }
        }
        return out;
    }
    
    public void tempToTop(){
        while(!tempStack.isEmpty()){
            moves++;
            produceStack.push(tempStack.pop());
        }
    }
    
    public Pair checkCounterCrate(){
        Pair x=new Pair<Boolean,String>(true,"");
        if(counterCrate==null||counterCrate.isEmpty()){
            if(produceStack.isEmpty()){
                x.changeFirst(false);
                return x;
            }
            else{
                counterCrate=(Crate)produceStack.pop();
                if(!useFile){
                    System.out.println("Getting crate: "+counterCrate);
                }
                x.changeFirst(true);
                x.changeSecond("Getting crate: "+counterCrate);
                return x;
            }
        }
        x.changeFirst(true);
        return x;
    }
    

}
