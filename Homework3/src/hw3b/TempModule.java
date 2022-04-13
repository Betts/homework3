package hw3b;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;

public class TempModule{

	
	public class genReport extends Thread {
		
		@Override
		public void run() {
			
		}

	}
	
	public class minUpdate extends Thread {
		@Override
		public void run() {
				// We assume the report was generated after 1 minute, so we do not have to actually wait out the time (I believe?)
				//int number = Random.nextInt(max - min) + min;
			
		}

	}	

	public static void main(String[] args) {
		
		System.out.println("Hello World");
		// TODO: 
		// Use java concurrentskiplistset as our datastructure since it is perfect for this application
		// Every minute a new number is randomly generated between -100F and 70F
		// Every hour a report is generated that shows the top 5 values from the last hour, the bottom 5 temps, 
		// And the 10-minute interval of time that had the largest temp difference 
		
		// How to do this? 
		// Every 10 minutes a new set is made, every 60 minutes these sets are combined into an hourset and the top and bottom 5 values are displayed
		// 

	}
	
	
}
