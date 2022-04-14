package hw3b;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

import hw3.LockFreeList;


public class TempModule extends Thread {
	private static final int INT_MIN = -25000;
	private static final int INT_MAX = 25000;
	static AtomicInteger addIndex = new AtomicInteger(1);
	LockFreeList<Integer> listy = new LockFreeList<Integer>();
	// These ints will hold the 5 max temps for the next report
	static int maxTemp1 = INT_MIN; // Highest temp
	static int maxTemp2 = INT_MIN;
	static int maxTemp3 = INT_MIN;
	static int maxTemp4 = INT_MIN;
	static int maxTemp5 = INT_MIN;
	// These ints will hold the 5 min temps for the next report
	static int minTemp1 = INT_MAX; // Lowest Temp
	static int minTemp2 = INT_MAX;
	static int minTemp3 = INT_MAX;
	static int minTemp4 = INT_MAX;
	static int minTemp5 = INT_MAX;
	//These ints will keep track of the 10 minute interval of time with the largest change
	static int low = 1;
	static int high = 11;
	
	// Simulates generating our report. Could not get this to work properly after spending hours debugging :(
	// Concurrentskiplistsets add function kept generting new sets instead of adding to an existing one, 
	// And my LockFreeList datastructure that I adadpted form the textbook for part 1 needs more modification to work. 
	// Sad to say I did not finish that within the time limit :( 
	public class genReport implements Runnable {
		
		public void run() {
			System.out.println("Generating Report: ");
			//TODO:
			//
			
			System.out.println("Top 5 highest temps from past hour: " + maxTemp1 + " " + maxTemp2+ " " + maxTemp3+ " " + maxTemp4+ " " + maxTemp5);
			System.out.println("Top 5 lowest temps from past hour: " + minTemp1 + " " + minTemp2+ " " + minTemp3+ " " + minTemp4+ " " + minTemp5);
			System.out.println("The Ten Minute Interval of Greatest Change Occured between Minutes: " + low + " and " + high);
		}

	}
	
	// Have each sensor use .getandset on an atomic int in an array or linked list
	public class sensorArray implements Runnable {
		
		public void run() {
			// We assume the report was generated after 1 minute, so we do not have to actually wait out the time (I believe?)
			int min = -100;
			int max = 70;
			// The variable readTemp is a randomly generated value between -100 and 70, this represents the sensor readings we obtain
			// every 1min
			int readTemp = ThreadLocalRandom.current().nextInt(min, max + 1);
			listy.add(readTemp);
			// This block keeps track of the 5 max temps for the upcoming report
			if (readTemp > maxTemp1 && listy.contains(readTemp)) {
				maxTemp5 = maxTemp4; maxTemp4 = maxTemp3; maxTemp3 = maxTemp2; maxTemp2 = maxTemp1; maxTemp1 = readTemp;
			} else if (readTemp > maxTemp2 && listy.contains(readTemp)) {
				maxTemp5 = maxTemp4; maxTemp4 = maxTemp3; maxTemp3 = maxTemp2; maxTemp2 = readTemp;
			} else if (readTemp > maxTemp3 && listy.contains(readTemp)) {
				maxTemp5 = maxTemp4; maxTemp4 = maxTemp3; maxTemp3 = readTemp;
			} else if (readTemp > maxTemp4 && listy.contains(readTemp)) {
				maxTemp5 = maxTemp4; maxTemp4 = readTemp;
			} else if (readTemp > maxTemp5 && listy.contains(readTemp)) {
				maxTemp5 = readTemp;
			}

			// This block keeps track of the top (bottom?) 5 min temps for the upcoming report
			if (readTemp < minTemp1 && listy.contains(readTemp)) {
				minTemp5 = minTemp4; minTemp4 = minTemp3; minTemp3 = minTemp2; minTemp2 = minTemp1; minTemp1 = readTemp;
			} else if (readTemp < minTemp2 && listy.contains(readTemp)) {
				minTemp5 = minTemp4; minTemp4 = minTemp3; minTemp3 = minTemp2; minTemp2 = readTemp;
			} else if (readTemp < minTemp3 && listy.contains(readTemp)) {
				minTemp5 = minTemp4; minTemp4 = minTemp3; minTemp3 = readTemp;
			} else if (readTemp < minTemp4 && listy.contains(readTemp)) {
				minTemp5 = minTemp4; minTemp4 = readTemp;
			} else if (readTemp < minTemp5 && listy.contains(readTemp)) {
				minTemp5 = readTemp;
			}
			// This block will keep track of the 10 minute interval of greatest change
				//TODO:
				// Keep track of whichever temp was added 9 cycles ago, and subtract it from the newest readTemp
				// Update values as needed and include in report
			
			// Uncomment to see temps added in real time
			//System.out.println("Temperature = " + readTemp);
			
		}

	}	
	

	
	public static void main(String[] args) {
		ConcurrentSkipListSet<Integer>
        set = new ConcurrentSkipListSet<Integer>();
		//Configure how many hours you would like to run the module for with this variable
		int hours = 1;
		// Every minute a new number is randomly generated between -100F and 70F
		// Every hour a report is generated that shows the top 5 values from the last hour, the bottom 5 temps, 
		// And the 10-minute interval of time that had the largest temp difference 
		
		// Creating our threadpool of 8 Threads. 
		final ExecutorService service = Executors.newFixedThreadPool(8);
		
		Instant start = Instant.now();
		
		for ( int k = 1; k <= (hours * 60); k++)
		{
			// 8 Instances of SensorArray for every call simulate there being 8 sensors
			service.submit(new TempModule().new sensorArray());
			service.submit(new TempModule().new sensorArray());
			service.submit(new TempModule().new sensorArray());
			service.submit(new TempModule().new sensorArray());
			service.submit(new TempModule().new sensorArray());
			service.submit(new TempModule().new sensorArray());
			service.submit(new TempModule().new sensorArray());
			service.submit(new TempModule().new sensorArray());
			addIndex.getAndIncrement();
			service.submit(new TempModule());
			if (k % 60 == 0) {
				service.submit(new TempModule().new genReport());
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
