package hw3b;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class TempModule extends Thread{
	ConcurrentSkipListSet<Integer> set = new ConcurrentSkipListSet<Integer>();
	
	
	public class genReport extends Thread {
		
		@Override
		public void run() {
			System.out.println("A report will live here");
			//TODO:
			// Idea for implementation
			// Loop through the list of 60 * 8 elements 10 at a time. Since the list is sorted you only need to
			// Subtract the every 10th from the 10th element after it
			// Starting at position 11 in the set, subtract positon - 10 from position 11. 
			// Keep track of largest variance if (temp > highestseen) highestseen = temp
			
			System.out.println(set);
		}

	}
	
	public class sensor1 extends Thread {
		@Override
		public synchronized void run() {
			// We assume the report was generated after 1 minute, so we do not have to actually wait out the time (I believe?)
			Random rand = new Random();
			int upperbound = 70;
			int lowerbound = -100;
			// The variable readTemp is a randomly generated value between -100 and 70, this represents the sensor readings we obtain
			// every 1min
			int readTemp = rand.nextInt(upperbound - lowerbound) + lowerbound;
			//System.out.println("Temperature = " + readTemp);
			set.add(readTemp);
			System.out.println("set = "+set);
			
		}

	}	
	
	public class sensor2 extends Thread {
		@Override
		public synchronized void run() {
			// We assume the report was generated after 1 minute, so we do not have to actually wait out the time (I believe?)
			Random rand = new Random();
			int upperbound = 70;
			int lowerbound = -100;
			// The variable readTemp is a randomly generated value between -100 and 70, this represents the sensor readings we obtain
			// every 1min
			int readTemp = rand.nextInt(upperbound - lowerbound) + lowerbound;
			//System.out.println("Temperature = " + readTemp);
			set.add(readTemp);
			
		}

	}
	
	public class sensor3 extends Thread {
		@Override
		public void run() {
			// We assume the report was generated after 1 minute, so we do not have to actually wait out the time (I believe?)
			Random rand = new Random();
			int upperbound = 70;
			int lowerbound = -100;
			// The variable readTemp is a randomly generated value between -100 and 70, this represents the sensor readings we obtain
			// every 1min
			int readTemp = rand.nextInt(upperbound - lowerbound) + lowerbound;
			//System.out.println("Temperature = " + readTemp);
			set.add(readTemp);
			
		}

	}
	
	public class sensor4 extends Thread {
		@Override
		public void run() {
			// We assume the report was generated after 1 minute, so we do not have to actually wait out the time (I believe?)
			Random rand = new Random();
			int upperbound = 70;
			int lowerbound = -100;
			// The variable readTemp is a randomly generated value between -100 and 70, this represents the sensor readings we obtain
			// every 1min
			int readTemp = rand.nextInt(upperbound - lowerbound) + lowerbound;
			//System.out.println("Temperature = " + readTemp);
			set.add(readTemp);
			
		}

	}
	
	public class sensor5 extends Thread {
		@Override
		public void run() {
			// We assume the report was generated after 1 minute, so we do not have to actually wait out the time (I believe?)
			Random rand = new Random();
			int upperbound = 70;
			int lowerbound = -100;
			// The variable readTemp is a randomly generated value between -100 and 70, this represents the sensor readings we obtain
			// every 1min
			int readTemp = rand.nextInt(upperbound - lowerbound) + lowerbound;
			//System.out.println("Temperature = " + readTemp);
			set.add(readTemp);
			
		}

	}
	
	public class sensor6 extends Thread {
		@Override
		public void run() {
			// We assume the report was generated after 1 minute, so we do not have to actually wait out the time (I believe?)
			Random rand = new Random();
			int upperbound = 70;
			int lowerbound = -100;
			// The variable readTemp is a randomly generated value between -100 and 70, this represents the sensor readings we obtain
			// every 1min
			int readTemp = rand.nextInt(upperbound - lowerbound) + lowerbound;
			//System.out.println("Temperature = " + readTemp);
			set.add(readTemp);
			
		}

	}

	
	public class sensor7 extends Thread {
		@Override
		public void run() {
			// We assume the report was generated after 1 minute, so we do not have to actually wait out the time (I believe?)
			Random rand = new Random();
			int upperbound = 70;
			int lowerbound = -100;
			// The variable readTemp is a randomly generated value between -100 and 70, this represents the sensor readings we obtain
			// every 1min
			int readTemp = rand.nextInt(upperbound - lowerbound) + lowerbound;
			//System.out.println("Temperature = " + readTemp);
			set.add(readTemp);
		}

	}
	
	public class sensor8 extends Thread {
		@Override
		public void run() {
			// We assume the report was generated after 1 minute, so we do not have to actually wait out the time (I believe?)
			Random rand = new Random();
			int upperbound = 70;
			int lowerbound = -100;
			// The variable readTemp is a randomly generated value between -100 and 70, this represents the sensor readings we obtain
			// every 1min
			int readTemp = rand.nextInt(upperbound - lowerbound) + lowerbound;
			//System.out.println("Temperature = " + readTemp);
			set.add(readTemp);
		}

	}
	
	public static void main(String[] args) {
		ConcurrentSkipListSet<Integer>
        set = new ConcurrentSkipListSet<Integer>();
		//Configure how many hours you would like to run the module for with this variable
		int hours = 2;
		// TODO: 
		// Use java concurrentskiplistset as our datastructure since it is perfect for this application
		// Every minute a new number is randomly generated between -100F and 70F
		// Every hour a report is generated that shows the top 5 values from the last hour, the bottom 5 temps, 
		// And the 10-minute interval of time that had the largest temp difference 
		
		// How to do this? 
		// Every 10 minutes a new set is made, every 60 minutes these sets are combined into an hourset and the top and bottom 5 values are displayed
		// 
		
		// Creating our threadpool of 8 Threads. 
		final ExecutorService service = Executors.newFixedThreadPool(8);
		
		Instant start = Instant.now();
		for ( int k = 1; k <= (hours * 60); k++)
		{
			service.execute(new TempModule().new sensor1());
			service.execute(new TempModule().new sensor2());
			service.execute(new TempModule().new sensor3());
			service.execute(new TempModule().new sensor4());
			service.execute(new TempModule().new sensor5());
			service.execute(new TempModule().new sensor6());
			service.execute(new TempModule().new sensor7());
			service.execute(new TempModule().new sensor8());
			if (k % 60 == 0) {
				service.execute(new TempModule().new genReport());
			}
			
		}

		service.shutdown();
		// Obtaining our total for our tasks once they are complete
		Instant end = Instant.now();
		long runtime = Duration.between(start, end).toMillis();
		double runtimes = (double) runtime / 1000;
		System.out.println("All tasks completed in: " + runtimes + " seconds");		

	}
	
	
}
