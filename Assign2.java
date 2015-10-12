import java.io.*;
import java.util.Scanner;
import java.util.NoSuchElementException;
import java.lang.ArrayIndexOutOfBoundsException;
public class Assign2{
    public static void main(String [] args) throws IOException{
        boolean givenFile=false;
        boolean useFile=false;
        Scanner input=new Scanner(System.in);
        Scanner reader=null;
        Store BananaStand=null;
        PrintWriter out = new PrintWriter("Assign2-out.txt");
        try{
            if(args[0]!=null){
                reader=new Scanner(new FileReader(args[0]));
                givenFile=true;
                BananaStand=new Store(reader, givenFile);
            }
        }
        catch (ArrayIndexOutOfBoundsException e){
                    givenFile=false;
        }
        
        if(!givenFile){
            System.out.println("Do you want to use a file(yes/no)?");
            if(input.next().toLowerCase().equals("yes")){
                useFile=true;
            }
        }
        
        if(useFile&&!givenFile){
            System.out.println("What file do you want to read?");
            String file=input.next();
            reader=new Scanner(new FileReader(file));
            BananaStand=new Store(reader,useFile);
        }
        else if(!useFile&&!givenFile){
            BananaStand=new Store(input,useFile);
        }

        boolean storeOperate=true;
        String command;
        String output;
        while(storeOperate){
            command=null;
            if(!useFile&&!givenFile){
                System.out.println("Please enter what you want to do(Recieve/Use/Report/Display/Skip/Exit): ");
                command=input.next();
            }
            else{
                try {
                    command=reader.next();
                } catch (NoSuchElementException e) {
                    out.println("End of simulation.");
                    out.close();
                    System.exit(0);
                }
            }
            
            switch(command.toLowerCase()){
                case "receive":
                int x;
                    if(!useFile&&!givenFile){
                        System.out.println("Enter the number of crates: ");
                        x=input.nextInt();
                    }
                    else{
                        x=reader.nextInt();
                    }
                    output=BananaStand.receive(x);
                    out.println(output);
                    break;
                case "use":
                int y;
                    if(!useFile&&!givenFile){
                        System.out.println("Enter how much produce you are selling: ");
                        y=input.nextInt();
                    }
                    else{
                        y=reader.nextInt();
                    }
                    output=BananaStand.sell(y);
                    out.println(output);
                    break;
                case "report":
                    output=BananaStand.report();
                    out.println(output);
                    break;
                case "display":
                    output=BananaStand.display();
                    out.println(output);
                    break;
                case "skip":
                    output=BananaStand.skip();
                    out.println(output);
                    break;
                case "exit":
                    if(!useFile){
                        System.out.println("Have a good day.");
                    }
                    out.println("Have a good day.");
                    storeOperate=false;
                    break;
                default:
                    if(!useFile){
                        System.out.println("Invalid Command");
                    }
                    out.println("Invalid Command");
                    break;
            }
        }
    }
}
