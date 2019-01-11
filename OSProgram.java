import java.util.*;
import java.io.*;  

 /**
 * OSProgram - Simulation of CPU scheduling algorithms  
 * @author Lis Nadya - 1161102208
 * @author Ewana Ayesha Binti Redzuan - 1161102091
 * @author Nuraishah Sabrina Binti Shahrir Zaid - 1161102321
 * @author Sri Thesigan A/L Murugan - 1161102079
 */
public class OSProgram{
	public static void main(String[] args){
		do{
			//users - user selection
			int users = printMenu(); 
			try{
				/**
				 * if user inputs 1, user executes FCFS scheduling
				 */
				if(users==1){ 
					System.out.println("==================================================================================");
					System.out.println("||             FCFS (First Come First Served) Pre-emptive Priority              ||");
					System.out.println("==================================================================================");
					fcfs(); 
					SysPause();
				}
				
				/**
				 * if user inputs 2, user executes RR scheduling
				 */
				else if(users==2){ 
					System.out.println("==================================================================================");
					System.out.println("||                     Round Robin Scheduling with Priority                     ||");
					System.out.println("==================================================================================");
					rr();
					SysPause();
				}
				
				/**
				 * if user inputs 3, user executes threeQueue scheduling
				 */
				else if(users==3){ 
					System.out.println("==================================================================================");
					System.out.println("||                        Three-level Queue Scheduling                          ||");
					System.out.println("==================================================================================");
					threeQueue();
					SysPause();
				}
				
				/**
				 * if user inputs 4, user executes SRTN scheduling
				 */
				else if(users==4){
					System.out.println("==================================================================================");
					System.out.println("||         Shortest Remaining Time Next (SRTN) Scheduling with Priority         ||");
					System.out.println("==================================================================================");
					srtn();
					SysPause();
				}
				
				/**
				 * if user inputs 0, user exits program
				 */
				else if(users==0)
					break;
			}
			catch(InputMismatchException e){ 
				System.out.println("[Error] Please enter an integer value");
				SysPause();
			}
		}while(true);
	}

	/**
	 * printMenu - a function to print the scheduling menu
	 * @return users - user selection
	 */
	public static int printMenu(){ 
		Scanner input = new Scanner(System.in);
		int users;
		
		System.out.println("==================================================================================");
		System.out.println("||                  Welcome to CPU Scheduling Algorithm Simulation              ||");
		System.out.println("==================================================================================");
		
		System.out.println("1. FCFS (First Come First Served)-based Pre-emptive Priority");
		System.out.println("2. Round Robin Scheduling with Priority\n3. Three-level Queue Scheduling");
		System.out.println("4. Shortest Remaining Time Next (SRTN) Scheduling with Priority");
		
		do{
			System.out.print("\nChoose a function [0-Exit]: ");
			users = input.nextInt();
			if(users>=0 && users<=4) 
				break;
			else
				System.out.println("[Error] Incorrect input");
		}while(true);
		
		return users;
	}
	
	/**
	 * addProcessor - a function to ask for iProcessor input
	 * @param iProcessor - parameter for list of iProcessors
	 */
	public static void addProcessor(ArrayList <Process> aProcessor){
		Scanner input = new Scanner(System.in);
		do{
			System.out.println("\n---------------------------------\nAdd Processor\n---------------------------------");
			System.out.print("Number of Processors [3-10]: ");
			int noProcessor = input.nextInt();
			if(noProcessor>2&&noProcessor<11){
				for (int i=0; i<noProcessor; i++){
					System.out.println("\n==> Processor: P" +i);
					String name = "P"+i;
					System.out.print("    -> Burst time (s): ");
					int burst = input.nextInt();
					System.out.print("    -> Arrival time (s): ");
					int arrival=input.nextInt();
					int priority=0;
					while(priority<1||priority>6){
						System.out.print("    -> Priority [1-6]: ");
						priority=input.nextInt();
						if(priority<1||priority>6)
							System.out.println("    [Error] Priority only ranges from 1 to 6");
					}
					aProcessor.add(new Process(name,burst,arrival,priority));
				}
				System.out.println("\n==> Processors have been successfully added!");
			}
			else
				System.out.println("\n==> [ERROR] Invalid number of processors!");
			break;
		}while(true);
		tableProcessor(aProcessor);
	}
	
	/**
	 * tableProcessor - a function to print table
	 * @param aProcessor - parameter for list of iProcessors
	 */
	public static void tableProcessor(ArrayList <Process> aProcessor){
		if(!aProcessor.isEmpty()){
			System.out.println("----------------------------------------------------------------");
			System.out.format("%16s%16s%16s%16s\n","Processor   |","Burst Time  |","Arrival Time |","Priority   |");
			System.out.println("----------------------------------------------------------------");
			for(Process p:aProcessor){
				System.out.format("%16s%16s%16s%16s\n",p.getName()+"       |",p.getBurst()+"       |",p.getArrival()+"       |",p.getPriority()+"      |");
			}
			System.out.println("----------------------------------------------------------------\n");
		}
	}
	
	/**
	 * fcfs - a function for FCFS scheduling
	 */
	public static void fcfs(){ 
		//lProcessor is arraylist for processors
		ArrayList <Process> lProcessor = new ArrayList <>();

		addProcessor(lProcessor);
		
		try{
			if(!lProcessor.isEmpty()){
				FCFS fcfsFunc = new FCFS(lProcessor);
				fcfsFunc.initialize();
			}
		}
		catch(NoSuchElementException ex){
			
		}
		catch(NullPointerException ex){
		}
	}
	
	/**
	 * rr - a function for Round Robin scheduling
	 */
	 
	public static void rr(){
		//lProcessor is arraylist for processors
		ArrayList <Process> lProcessor = new ArrayList <>();
		
		addProcessor(lProcessor);
		
		try{
			if(!lProcessor.isEmpty()){
			Scanner input = new Scanner(System.in);
			System.out.print("Enter time quantum: ");
			//timeQuantum is time quantum
			int timeQuantum = input.nextInt();
			
			RR rrFunc = new RR(lProcessor, timeQuantum);
			rrFunc.initialize();
		}
		}
		catch(NoSuchElementException ex){
			
		}
		catch(NullPointerException ex){
		}
		
	}

	/**
	 * threeQueue - a function for Three Level Queue scheduling
	 */
	public static void threeQueue(){
		//lProcessor is arraylist for processors
		ArrayList <Process> lProcessor = new ArrayList <>();
		addProcessor(lProcessor);
		
		try{
			if(!lProcessor.isEmpty()){
				Scanner input = new Scanner(System.in);
				System.out.print("Enter time quantum: ");
				//timeQuantum is time quantum
				int timeQuantum = input.nextInt();
				
				TLQ tlqFunc = new TLQ(lProcessor, timeQuantum);
				tlqFunc.initialize();
			}
		}
		catch(NoSuchElementException ex){
			
		}
		catch(NullPointerException ex){
		}
	}
	
	/**
	 * srtn - a function for SRTN scheduling
	 */
	public static void srtn(){
		//lProcessor is arraylist for processors
		ArrayList <Process> lProcessor = new ArrayList <>();
		
		addProcessor(lProcessor);
		
		try{
			if(!lProcessor.isEmpty()){
				if(!lProcessor.isEmpty()){	
					SRTN srtnFunc = new SRTN(lProcessor);
					srtnFunc.initialize();
				}
			}
		}
		catch(NoSuchElementException ex){
			
		}
		catch(NullPointerException ex){
		}
		
	}
	
	/**
	 * SysPause - a function to pause system
	 */
	public static void SysPause(){ 
		System.out.print("\n\n[Message] Press Any Key To Continue");
        new java.util.Scanner(System.in).nextLine();
	}
}