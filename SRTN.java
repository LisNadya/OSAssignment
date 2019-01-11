import java.util.*;
import java.io.*; 

/**
 * SRTN - Shortest Remaining Time Next 
 */
public class SRTN{
	private ArrayList <Process> iList = new ArrayList<Process>();
	
	SRTN(ArrayList <Process> aList){
		iList = aList;
	}
	
	public void initialize(){
		int totb = 0; //totalbursttime
		int count = 0;//timer
		int n = iList.size();
		ArrayList<Process> sj = new ArrayList<Process>(iList); //Arraylist for shortest job iLists
		ArrayList<Integer> result = new ArrayList<>();
		ArrayList<Integer> gant = new ArrayList<>();
		
		int minc = 1000;
		for (Process p: sj){ 
			totb += p.getBurst();//calculating total burst time
			if(p.getArrival()<=minc){
				minc = p.getArrival();
			}
		}
		
		if (minc != 0){
			System.out.println("It seems like none of your processes arrives at time 0,\n We will handle that for you... \n");
			for(Process p : sj){ // making sure that everything starts from 0 
			p.setArrival(p.getArrival() - minc);
			}
		}
		
		
		
		do{
			result.add(count);
			int min = 1000, tbp = 99; //minimum burst time, to be processed = 99(indication number for idle)
			for (int i=0; i<n; i++){
				if ((sj.get(i).getArrival() <= count) && (sj.get(i).getBurst()) != 0){
					if (sj.get(i).getBurst()<min){
						min = sj.get(i).getBurst();
						tbp = i;
					}
					
					else if(sj.get(i).getBurst() == min){
						if(sj.get(tbp).getPriority() > sj.get(i).getPriority()){ //compare priority
						min = sj.get(i).getBurst();
						tbp = i;
						}
					}
					
					//else processes.get(tbh).getprio() <= processes.get(i).getprio();
					//min is still the same
				}
				
			}
			
			
			
			if (tbp == 99){
				count ++;
			}
			
			else if (tbp > n){
				System.out.println("error");
			}
			else{
				int res = (sj.get(tbp).getBurst()) - 1;
				sj.get(tbp).setBurst(res);
				count ++;
			}
			//printing the results
			result.add(tbp);
			
		}while(count<totb);
		
		int k = 0;
		
		gant.add(result.get(0)); //time 0 
		gant.add(result.get(1)); //first process
		for(int i=3; i<result.size(); i=i+2){ 
			if (result.get(i) == result.get(i-2)){
				//nothing
			}
			else{
				gant.add(result.get(i-1));//time
				gant.add(result.get(i));//add process
				
			}
		}
		gant.add(totb);
		
		boolean print = false;
		for(int i=1; i<=gant.size(); i=i+2){
			if(i==(gant.size())){
				System.out.println("");
				print = true;
				i = -2;
			}
			else if(print == false){
				if (gant.get(i) == 99){ // if its idle
					System.out.print("idle" + "\t| ");
				}
				else{
					System.out.print("p"+ gant.get(i) + "\t| ");
				}
				
			}
			
			else if(print == true){
				System.out.print(gant.get(i) + "\t");
			}
			
		}
	}
}