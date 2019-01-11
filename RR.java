import java.util.*;
import java.io.*; 

/**
 * RR - Round Robin
 */
public class RR{
	private ArrayList <Process> iList = new ArrayList<Process>();
	private int timeQuantum = 0;
	
	RR(ArrayList <Process> aList, int aTimeQuantum){
		iList = aList;
		timeQuantum = aTimeQuantum;
	}
	
	public void initialize(){
		//aList sorts list according to priority
		LinkedList <Process> aList = new LinkedList <Process>(iList);
		Collections.sort(aList, Process.arrivalComparator); 
		//pList sorts list according to priority
		LinkedList <Process> pList = new LinkedList <Process>(iList);
		Collections.sort(pList, Process.priorityComparator); 
		//sched is round robin scheduling
		LinkedList <Process> sched = new LinkedList<Process>();
		//name is order of processor names
		ArrayList <String> name = new ArrayList <String>();
		//serve is serve time for iLists
		ArrayList <Integer> serve = new ArrayList <Integer>();
		//time is program timer
		int time=0; //time starts 
		int lastArrival = aList.getLast().getArrival(); //get last processor arrival 
		int totEndBurst = 0; //real time total end burst 
		Process pic = null; //pic is processor in control
		boolean schedEmpty = true; //schedEmpty if sched is empty
		Process pFirst = aList.getFirst();
		Process pLast = pList.getLast();
		//serve.add(time);
		
		//------------------------------------------
		// IF ARRIVAL LIST STILL HAS PROCESSORS 
		//------------------------------------------
		while(!aList.isEmpty()){
			if(sched.isEmpty())
				schedEmpty=true;
			else
				schedEmpty=false;
			//------------------------------------------
			// IF PROCESSOR HAS COMPLETED THEIR SERVE TIME
			//------------------------------------------
			if(totEndBurst<=time){
				//--------------> IF SCHEDULE IS EMPTY <--------------------
				if(schedEmpty){
					if(aList.getFirst().getArrival()==time)
						sched.add(aList.poll());
				}
				
				//--------------> CHECK PROCESSOR'S ARRIVAL <--------------------
				while(true){
					//--------------------------------------------------------
					// IF ARRIVAL LIST IS EMPTY OR NEXT ARRIVAL LIST HASN'T ARRIVED YET
					//--------------------------------------------------------
					if(aList.isEmpty()||aList.peek().getArrival()>time)
						break;
					//--------------------------------------------------------
					// IF NEXT IN ARRIVAL LIST ARRIVES
					//--------------------------------------------------------
					else if(aList.getFirst().getArrival()==time){ //check arrival of processor from arrival list
						//--------------------------------------------------------
						// [IF ARRIVAL PRIORITY < PIC PRIORITY]
						//--------------------------------------------------------
						if(pList.indexOf(aList.peek())<pList.indexOf(pic)){ //if arrival of processor has higher priority than pic
							
							while(true){
								//--------------------------------------------------------
								// [IF THE HEAD IN SCHEDULE PRIORITY = ARRIVAL PRIORITY]
								//--------------------------------------------------------
								if(aList.peek().getPriority()==sched.peek().getPriority()){
									for(Process p:sched){
										if(p.getPriority()>aList.peek().getPriority()){
											sched.add(sched.indexOf(p), aList.poll());
											break;
										}
									}
								}
								//--------------------------------------------------------
								// [IF THE HEAD IN SCHEDULE PRIORITY > ARRIVAL PRIORITY]
								//--------------------------------------------------------
								else if(aList.peek().getPriority()<sched.peek().getPriority()){
									sched.push(aList.poll()); //add processor into the front of schedule
									break;
								}
								else{
									sched.add(aList.poll());
									break;
								}
							}
							
						}
						//--------------------------------------------------------
						// [IF ARRIVAL PRIORITY > PIC PRIORITY]
						//--------------------------------------------------------
						else{ //if arrival of processor has lower priority than pic
							for(Process p:sched){
								//--------------------------------------------------------
								// [IF THE HEAD IN SCHEDULE PRIORITY > ARRIVAL PRIORITY]
								//--------------------------------------------------------
								if(pList.indexOf(aList.peek())<pList.indexOf(p)){
									sched.add(sched.indexOf(p),aList.poll());
									break;
								}
								//--------------------------------------------------------
								// [IF THE HEAD IN SCHEDULE PRIORITY < ARRIVAL PRIORITY]
								//--------------------------------------------------------
								if(sched.getLast().equals(p)){
									if(pList.indexOf(p)<pList.indexOf(aList.peek())){
										sched.add(aList.poll()); //adds arrival to the back of the schedule
										break;
									}
								}
							}
							
						}
					}
					
				}
				//--------------------------------------------------------
				
				
				//--------------------------------------------------------
				// RETURNING PIC BACK INTO SCHEDULE
				//--------------------------------------------------------
				if(pic!=null){
					//--------------> CHECK TO DEDUCT BURST TIME YET <-------------------
					int picBurst = pic.getBurst();
					picBurst--;
					pic.setBurst(picBurst); //deduct pic burst
					//--------------> CHECK IF PIC HAS REMAINING BURST OR NOT <-------------------
					if(pic.getBurst()!=0){ //if pic still has remaining burst
						boolean prioTrue=false;
						for(Process p:sched){
							if(pic.getPriority()<p.getPriority()){ //to find position of processor when added back into the schedule
								sched.add(sched.indexOf(p),pic);
								prioTrue=true;
								break;
							}
						}
						
						if(!prioTrue||sched.isEmpty())
							sched.add(pic);
					}
					
					//--------------------------------------------------------
				}
				
				//--------------------------------------------------------
				// TAKING NEXT PROCESSOR
				//--------------------------------------------------------
				if(!sched.isEmpty()){ //if schedule has processor queuing
					pic=sched.poll();
				}
				
				if(pic!=null){
					serve.add(time);
					name.add(pic.getName());
					//--------------> GET TOTAL END BURST <-------------------
					int remBurst = pic.getBurst() - timeQuantum; //remaining burst = burst - time quantum
					if(remBurst<=0){ //if remBurst is less or equal to 0
						totEndBurst = time+pic.getBurst(); //total end burst = time + remaining burst
					}
					else{ //if remBurst is more than 0
						totEndBurst = time+timeQuantum; //total end burst = time + time quantum
					}
				}
				//--------------------------------------------------------
			}
			//------------------------------------------
			// IF BURST TIME IS STILL RUNNING
			//------------------------------------------
			else if(totEndBurst>time){
				//--------------> CHECK PROCESSOR'S ARRIVAL <--------------------
				while(true){
					//--------------------------------------------------------
					// IF ARRIVAL LIST IS EMPTY OR NEXT ARRIVAL LIST HASN'T ARRIVED YET
					//--------------------------------------------------------
					if(aList.isEmpty()||aList.peek().getArrival()>time)
						break;
					//--------------------------------------------------------
					// IF NEXT IN ARRIVAL LIST ARRIVES
					//--------------------------------------------------------
					else if(aList.getFirst().getArrival()==time){ //check arrival of processor from arrival list
						//--------------------------------------------------------
						// [IF ARRIVAL PRIORITY < PIC PRIORITY]
						//--------------------------------------------------------
						if(pList.indexOf(aList.peek())<pList.indexOf(pic)&&!sched.isEmpty()){ //if arrival of processor has higher priority than pic
							while(true){
								//--------------------------------------------------------
								// [IF THE HEAD IN SCHEDULE PRIORITY = ARRIVAL PRIORITY]
								//--------------------------------------------------------
								if(aList.peek().getPriority()==sched.peek().getPriority()){
									for(Process p:sched){
										if(p.getPriority()>aList.peek().getPriority()){
											sched.add(sched.indexOf(p), aList.poll());
											break;
										}
									}
								}
								//--------------------------------------------------------
								// [IF THE HEAD IN SCHEDULE PRIORITY > ARRIVAL PRIORITY]
								//--------------------------------------------------------
								else{
									sched.push(aList.poll()); //add processor into the front of schedule
									break;
								}
							}
						}
						//--------------------------------------------------------
						// [IF ARRIVAL PRIORITY > PIC PRIORITY]
						//--------------------------------------------------------
						else if(pList.indexOf(aList.peek())>pList.indexOf(pic)&&!sched.isEmpty()){ //if arrival of processor has lower priority than pic
							for(Process p:sched){
								//--------------------------------------------------------
								// [IF THE HEAD IN SCHEDULE PRIORITY > ARRIVAL PRIORITY]
								//--------------------------------------------------------
								if(pList.indexOf(aList.peek())<pList.indexOf(p)){
									sched.add(sched.indexOf(p),aList.poll());
									break;
								}
								//--------------------------------------------------------
								// [IF THE HEAD IN SCHEDULE PRIORITY < ARRIVAL PRIORITY]
								//--------------------------------------------------------
								if(sched.getLast().equals(p)){
									if(pList.indexOf(p)<pList.indexOf(aList.peek())){
										sched.add(aList.poll()); //adds arrival to the back of the schedule
										break;
									}
								}
							}
							
						}
						else if(sched.isEmpty())
							sched.add(aList.poll());
	
					}
					
				}
				//--------------------------------------------------------
				if(pic!=null){
					int picBurst = pic.getBurst();
					picBurst--;
					pic.setBurst(picBurst); //deduct pic burst
				}
			}
			//------------------------------------------
			time++;
		}
		while(true){
			if(sched.isEmpty()&&pic==null){
				break;
			}
			else if(totEndBurst==time){
				//--------------------------------------------------------
				// RETURNING PIC BACK INTO SCHEDULE
				//--------------------------------------------------------
				if(pic!=null){
					//--------------> CHECK TO DEDUCT BURST TIME YET <-------------------
					int picBurst = pic.getBurst();
					picBurst--;
					pic.setBurst(picBurst); //deduct pic burst
					//--------------> CHECK IF PIC HAS REMAINING BURST OR NOT <-------------------
					if(pic.getBurst()!=0){ //if pic still has remaining burst
						boolean prioTrue=false;
						for(Process p:sched){
							if(pic.getPriority()<p.getPriority()){ //to find position of processor when added back into the schedule
								sched.add(sched.indexOf(p),pic);
								prioTrue=true;
								break;
							}
						}
						
						if(!prioTrue||sched.isEmpty())
							sched.add(pic);
					}
					
					//--------------------------------------------------------
				}
				
				//--------------------------------------------------------
				pic = sched.poll();
				
				if(pic!=null){
					//--------------> UPDATE GANT CHATT <--------------------
					serve.add(time);
					name.add(pic.getName());
					//--------------------------------------------------------
					//--------------> GET TOTAL SERVE TIME <-------------------
					int remBurst = pic.getBurst() - timeQuantum; //remaining burst = burst - time quantum
					
					if(remBurst<=0){ //if remBurst is less or equal to 0
						totEndBurst = time+pic.getBurst(); //total end burst = time + remaining burst
					}
					else{ //if remBurst is more than 0
						totEndBurst = time+timeQuantum; //total end burst = time + time quantum
					}
				}
				else
					serve.add(time);
			}
			else if(totEndBurst>time){
				int picBurst = pic.getBurst();
				picBurst--;
				pic.setBurst(picBurst); //deduct pic burst
			}
			
			while(true){
				if(!sched.isEmpty()){ 
					if(sched.peek().getBurst()==0)
						sched.poll();
					else
						break;
				}
				else{
					break;
				}
			}
			time++;
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