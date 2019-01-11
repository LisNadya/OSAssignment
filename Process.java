import java.util.*;
import java.io.*;

/**
 * Process - class for Processor
 */
public class Process implements Comparable<Process>{
	//name - name of processor
	private String name;
	//burst - burst time
	private int burst;
	//arrival - arrival time
	private int arrival;
	//priority - priority 
	private int priority;
	
	/**
	 * Process - constructor for Process
	 * @param name - processor name
	 * @param burst - burst time
	 * @param arrival - arrival time
	 * @param priority - priority 
	 */
	Process(String name, int burst, int arrival, int priority){
		this.name=name;
		this.burst=burst;
		this.arrival=arrival;
		this.priority=priority;
	}
	
	/**
	 * getName - to return name
	 * @return name - return name
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * getName - to return name
	 * @return name - return name
	 */
	public int getBurst(){
		return burst;
	}
	
	/**
	 * getArrival - to return arrival
	 * @return arrival - return arrival
	 */
	public int getArrival(){
		return arrival;
	}
	
	/**
	 * getPriority - to return priority
	 * @return priority - return priority
	 */
	public int getPriority(){
		return priority;
	}
	
	/**
	 * setName - to set name
	 * @param name - processor name
	 */
	public void setName(String name){
		this.name=name;
	}
	
	/**
	 * setBurst - to set burst
	 * @param burst - processor burst
	 */
	public void setBurst(int burst){
		this.burst=burst;
	}
	
	/**
	 * setArrival - to set arrival
	 * @param arrival - processor arrival
	 */
	public void setArrival(int arrival){
		this.arrival=arrival;
	}
	
	/**
	 * setPriority - to set priority
	 * @param priority - processor priority 
	 */
	public void setPriority(int priority){
		this.priority=priority;
	}
	
	/**
	 * toString - return details of processor
	 * @return name - return processor name;
	 */
	public String toString(){
		return name;
	}
	
	
	/**
	 * compareTo - comparator
	 * @param p - processor 
	 */
	public int compareTo(Process p){  
		return (this.priority - p.priority);
	}
	
	/**
	 * priorityComparator - compare between priority
	 */
	public static Comparator<Process> priorityComparator = new Comparator<Process>() {
		@Override
		public int compare(Process p1, Process p2) {
			if(p1.getPriority() == p2.getPriority()){  
				if(p1.getArrival()<p2.getArrival())
					return -1;
				else if(p1.getPriority()==p2.getPriority())
					return 0;
				else
					return 1;
			}
			else if(p1.getPriority()>p2.getPriority())
				return 1;  
			else
				return -1;  
		}
	};
	
	/**
	 * arrivalComparator - compare between arrival
	 */
	public static Comparator<Process> arrivalComparator = new Comparator<Process>() {
		@Override
		public int compare(Process p1, Process p2) {
			if(p1.getArrival()<p2.getArrival())
				return -1;
			else if(p1.getArrival()==p2.getArrival())
				if(p1.getPriority()<p2.getPriority())
					return -1;
				else if(p1.getPriority()==p2.getPriority())
					return 0;
				else
					return 1;
			else
				return 1;
		}
	};
	
}
