import java.io.*;
import java.util.Scanner;
import java.util.NoSuchElementException;
import java.lang.ArrayIndexOutOfBoundsException;
public class Assign2{
    public static void main(String [] args) throws IOException{
        boolean givenFile=false;//for if a file is given at command line
        boolean useFile=false;//for if a file is being used at all
        Scanner input=new Scanner(System.in);//for user input, if file isn't given
        Scanner reader=null;//to be set once file is given
        Store BananaStand=null;//our store
        String fileName=null;//the name of the file that's given
        PrintWriter out=null;//PrintWriter for printing to a txt file, creates a new file
        try{
            if(args[0]!=null){//if a file is given at command line, reader is set up to read it and our Store is created
                fileName=args[0];
                reader=new Scanner(new FileReader(args[0]));
                givenFile=true;
                BananaStand=new Store(reader, givenFile);
            }
        }
        catch (ArrayIndexOutOfBoundsException e){//no file is given and user input is taken
            givenFile=false;
        }
        
        if(!givenFile){//checks if the user wants to use a file
            System.out.println("Do you want to use a file(yes/no)?");
            if(input.next().toLowerCase().equals("yes")){
                useFile=true;
            }
        }
        
        if(useFile&&!givenFile){//creates a store and reads from a file
            System.out.println("What file do you want to read?");
            String file=input.next();
            fileName=file;
            reader=new Scanner(new FileReader(file));
            BananaStand=new Store(reader,useFile);
        }
        else if(!useFile&&!givenFile){//creates a store and uses user input
            BananaStand=new Store(input,useFile);
        }
        
        if(fileName!=null){//if given a file name, adds -out.txt to it for the final output file
            String fileNameTemp=fileName.substring(0,fileName.length()-4);
            out=new PrintWriter(fileNameTemp+"-out.txt");
        }
        else{//if no file given, just prints it to Assign2-out.txt
            out = new PrintWriter("Assign2-out.txt");
        }
        boolean storeOperate=true;
        String command;
        String output;
        while(storeOperate){
            command=null;
            if(!useFile&&!givenFile){
                System.out.println("Please enter what you want to do(Recieve/Sell/Report/Display/Skip/Exit): ");
                command=input.next();
            }
            else{
                try {//takes a command from the file
                    command=reader.next();
                } catch (NoSuchElementException e) {//if no cammand, ends program and closes txt
                    System.out.println("End of simulation.");
                    out.println("End of simulation.");
                    out.close();
                    System.exit(0);
                }
            }
            
            switch(command.toLowerCase()){//runs through possible commands
                case "receive":
                int x;
                    if(!useFile&&!givenFile){//takes the number of crates to be received
                        System.out.println("Enter the number of crates: ");
                        x=input.nextInt();
                    }
                    else{
                        x=reader.nextInt();
                    }
                    output=BananaStand.receive(x);
                    out.println(output+"\r\n");
                    break;
                case "sell":
                int y;
                    if(!useFile&&!givenFile){//takes number of crates to sold
                        System.out.println("Enter how much produce you are selling: ");
                        y=input.nextInt();
                    }
                    else{
                        y=reader.nextInt();
                    }
                    output=BananaStand.sell(y);
                    out.println(output+"\r\n");
                    break;
                case "report"://gives a report
                    output=BananaStand.report();
                    out.println(output+"\r\n");
                    break;
                case "display"://gives a display
                    output=BananaStand.display();
                    out.println(output+"\r\n");
                    break;
                case "skip"://skips to text day
                    output=BananaStand.skip();
                    out.println(output+"\r\n");
                    break;
                case "exit"://exits program, only for user input
                    if(!useFile){
                        System.out.println("Have a good day.");
                    }
                    out.println("Have a good day.");
                    storeOperate=false;
                    break;
                default://invalid command, only for user input
                    if(!useFile){
                        System.out.println("Invalid Command");
                    }
                    out.println("Invalid Command");
                    break;
            }
        }
    }
}
