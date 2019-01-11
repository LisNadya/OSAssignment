import java.util.*;
import java.io.*; 

/**
 * FCFS - First Come First Serve
 */
public class FCFS{
	private ArrayList <Process> iList = new ArrayList<Process>();
	
	FCFS(ArrayList <Process> aList){
		iList = aList;
	}
	
	public void initialize(){
		//aList sorts list according to priority
		LinkedList <Process> aList = new LinkedList <Process>(iList);
		Collections.sort(aList, Process.arrivalComparator); 
		//pList sorts list according to priority
		LinkedList <Process> pList = new LinkedList <Process>(iList);
		Collections.sort(pList, Process.priorityComparator); 
		//schedule is the order of iLists that will be executed
		LinkedList <Process> sched = new LinkedList <Process>();
		//name is order of processor names
		ArrayList <String> name = new ArrayList <String>();
		//serve is serve time for iLists
		ArrayList <Integer> serve = new ArrayList <Integer>();
		
		Process pic = null;
		int endBurst = 0;
		int time=0;
		int lastArrival = aList.getLast().getArrival();
		boolean schedEmpty = true;
		Process pFirst = aList.getFirst();
		
		serve.add(time);
		while(!aList.isEmpty()){
			
			if(sched.isEmpty())
				schedEmpty = true;
			else
				schedEmpty = false;
			
			//------------------------------------------
			// IF PROCESSOR HAS COMPLETED THEIR SERVE TIME
			//------------------------------------------
			if(endBurst<=time){
				//--------------> IF SCHEDULE IS EMPTY <--------------------
				if(schedEmpty){ //if schedule is empty
					if(aList.getFirst().getArrival()==time){ //get from arrival list
						sched.add(aList.poll());
					}
					else
						pic=null;
				}
				//----------------------------------------------------------
					
				//--------------> CHECK PROCESSOR'S ARRIVAL <--------------------
				while(true){
					if(aList.isEmpty()||aList.peek().getArrival()>time)
						break;
					else if(aList.peek().getArrival()==time){ //check arrival of processor from arrival list
						for(Process p:sched){
							if(pList.indexOf(p)>pList.indexOf(aList.peek())){ //if arrival processor has a higher or equal priority than scheduled
								//------------------------------------------
								// [sched priority > arrival priority] 
								//------------------------------------------
								sched.add(sched.indexOf(p), aList.poll());
								break;
							}
							
							if(sched.getLast().equals(p)){
								if(pList.indexOf(p)<pList.indexOf(aList.peek())){
									//------------------------------------------
									// [last processor in schedule priority < arrival priority] 
									//------------------------------------------
									sched.add(aList.poll()); //adds arrival to the back of the schedule
									break;
								}
							}
						}
					}
				}
				
				//--------------------------------------------------------	
				if(!schedEmpty){
					pic = sched.poll();
				}
				
				if(pic!=null){
					if(!pic.equals(pFirst)&&pic.getArrival()!=0)
						serve.add(time);
					name.add(pic.getName());
					endBurst = time+pic.getBurst();
					int remBurst = pic.getBurst();
					remBurst--;
					pic.setBurst(remBurst);
				}
				
				
			}
			//------------------------------------------
			
			//------------------------------------------
			// IF BURST TIME IS STILL RUNNING
			//------------------------------------------
			else if(endBurst>time){
				boolean picChange = false;
				//--------------> CHECK PROCESSOR'S ARRIVAL <--------------------
				while(true){
					if(aList.isEmpty()||aList.peek().getArrival()>time)
						break;
					else if(aList.peek().getArrival()==time){ //check arrival of processor from arrival list
						if(pList.indexOf(aList.peek())<pList.indexOf(pic)){ //if arrival of processor has higher priority than pic
								//------------------------------------------
								// [arrival priority < pic priority] 
								//------------------------------------------
								sched.push(aList.poll()); //adds arrival to the front of schedule
								
								for(Process p:sched){ 
									//------------------------------------------
									// find where to place pic back into schedule before handing CPU
									//------------------------------------------
									if(pList.indexOf(p)>pList.indexOf(pic)){
										//------------------------------------------
										// [sched priority > pic priority] 
										//------------------------------------------
										sched.add(sched.indexOf(p), pic);
										break;
									}
									if(sched.getLast().equals(p)){
										if(pList.indexOf(p)<pList.indexOf(pic)){
										//------------------------------------------
										// [last processor in schedule priority < pic priority] 
										//------------------------------------------
										sched.add(pic); //adds pic to the back of the schedule
										break;
									}
								}
						
								picChange = true; //change in CPU is true
								//------------------------------------------
							}
						}
						else{  
							//------------------------------------------
							// [arrival priority >= pic priority] 
							//------------------------------------------
							for(Process p:sched){
								if(sched.getLast().equals(p)){
									if(pList.indexOf(p)<pList.indexOf(aList.peek())){
										//------------------------------------------
										// [last processor in schedule priority < arrival priority] 
										//------------------------------------------
										sched.add(aList.poll()); //adds pic to the back of the schedule
										break;
									}
									continue;
								}
								
								if(pList.indexOf(p)>pList.indexOf(aList.peek())){ //if arrival processor has a higher or equal priority than pic 
									//------------------------------------------
									// [sched priority > arrival priority] 
									//------------------------------------------
									sched.add(sched.indexOf(p), aList.poll());
									break;
								}
							}
						}
					}
				}
				//--------------------------------------------------------	
				if(pic!=null){
					int remBurst = pic.getBurst();
					remBurst--;
					pic.setBurst(remBurst);
				}
				if(picChange){
					pic = sched.poll();
					name.add(pic.getName());
					serve.add(time);
					endBurst = time+pic.getBurst();
				}
			}
			//------------------------------------------
		
			time++;
		}
		
		while(true){
			//------------------------------------------
			// IF PROCESSOR HAS COMPLETED THEIR SERVE TIME
			//------------------------------------------
			if(endBurst==time){
				if(!sched.isEmpty())
					pic = sched.poll();
				name.add(pic.getName());
				serve.add(time);
				endBurst = time+pic.getBurst();
				
				int remBurst = pic.getBurst();
				remBurst--;
				pic.setBurst(remBurst);
			}
			else if(endBurst>time){
				int remBurst = pic.getBurst();
				remBurst--;
				pic.setBurst(remBurst);
			}
			time++;
			
			if(sched.isEmpty()&&pic.getBurst()==0){
				serve.add(time);
				break;
			}
			
		}
		
		
		/****************************************************************
		 * the Gant Chart of FCFS
		 ***************************************************************/
		System.out.println("----------------------------------------------------------------");
		System.out.println("                            Gant Chatt                          ");
		System.out.print("----------------------------------------------------------------");
		System.out.print("\n|");
		for(String s:name){ //prints iLists' names in order
			System.out.format("%8s",s+"  |");
		}
		
		System.out.println("");
		for(int s:serve){ //prints the service time of each iList
			if(serve.indexOf(s)==0)
				System.out.print(s);
			else
				System.out.format("%8s",s);
		}
		/****************************************************************/
		
	}
}