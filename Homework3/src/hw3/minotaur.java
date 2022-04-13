package hw3;

import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class minotaur extends Thread{
	LockFreeList<Integer> listy = new LockFreeList<Integer>();
	//We can control our number of guests here. In the final program this should be set to 500,001
	static int numGuests = 500001;
	static AtomicInteger addIndex = new AtomicInteger(0);
	static AtomicInteger removeIndex = new AtomicInteger(0);
	
	int loopy = 0;
	static List<Integer> a = IntStream.range(1, numGuests + 1).boxed().collect(Collectors.toList());

	// Task1 is responsible for grabbing a present from the unsorted bag, and placing it in its correct position
	// in our LockFreeList
	public class Task1 extends Thread{
		
		@Override
		public void run() {
		  
			// This get and increment of the Index variable will allow the next thread to grab the next present from the unordered bag (a)
			int index = addIndex.getAndIncrement();
		    
		    // Base Case: We have to subtract 2 from numGuests because we getandIncrement before this step. 
		    if (index >= numGuests - 2) {
		    	System.out.println("All presents added and sorted");
		    	return;
		    }
		    
		    // A servant utilizes our LockFreeList data structure to add a present from the unsorted bag into our list,
		    // at the correct position
		    listy.add(a.get(index));
		    
		    // Toggle the following print statement on to to see which Guests are being added in real time
		    //System.out.println("Present from following tag added to chain: " + a.get(index));
		    
		    return;
		  }
	}
	
	public class Task2 extends Thread{
		
		@Override
		public void run() {
			
			// This get and increment of the Index variable will allow the next thread to grab the next present from the unordered bag (a)
			int index = removeIndex.getAndIncrement();
			// Uncomment for debugging or to see which index and number are being added in real time
			//System.out.println("Remove Index = " + index);
			//System.out.println("A number = " + a.get(index));
			
					    
		    // Base Case: We have to subtract 2 from numGuests because we getandIncrement before this step. 
		    if (index >= numGuests - 2) {
		    	listy.remove(a.get(index));
		    	System.out.println("All guests sent TY Cards.");
		    	return;
		    }
		    
		    // A servant utilizes our LockFreeList data structure to add a present from the unsorted bag into our list,
		    // at the correct position
		    listy.remove(a.get(index));
		    
		    // Toggle the following print statement on to to see which Guests are being removed in real time
		    //System.out.println("Present from following tag sent TY card: " + a.get(index));
					    
		    return;
		}
	}
	
	// Checks whether a gift with a particular tag is present in the list or not. 
	public class Task3 extends Thread{
		
		@Override
		public void run() {
			// Simulating the minotaur picking a random number
			Random rand = new Random();
			int upperbound = 500000; 
			int minotaursChoice = rand.nextInt(upperbound);
			// Checking to see if the value chosen by the Minotaur is contained in the list
		    boolean result = listy.contains(minotaursChoice);
		    // Uncomment this line to see results in real time. 
		    System.out.println("Minotaur has chosen! Is number: " + minotaursChoice + " " + "Currently in TY Card Queue / Sorted Presents?: " + result); 
		    
		  }
	}
	
	public static void main(String[] args) throws InterruptedException {
		System.out.println("Beginning Operations...");
		// Shuffling list a to give us our original conditions (Unordered bag)
		Collections.shuffle(a);
		
		// Creating our thread/servantpool of 4 servants. 
		final ExecutorService service = Executors.newFixedThreadPool(4);
		
		Instant start = Instant.now();
		for ( int k = 0; k < numGuests - 1; k++)
		{
			service.execute(new minotaur().new Task1());
			service.execute(new minotaur().new Task2());
			// To simulate task 3 executing randomly / on demand I will check if an element from the unsorted bag has been
			// added to our list yet every 100000 iterations of k. This number can be adjusted. I'm not sure if there was a minimum amount of times we had to check?
			if (k % 100000 == 0) {
				service.execute(new minotaur().new Task3());
			}
		}
		
		
		service.shutdown();
		// Obtaining our total for our tasks once they are complete
		Instant end = Instant.now();
		long runtime = Duration.between(start, end).toMillis();
		double runtimes = (double) runtime / 1000;
		System.out.println("All tasks completed in: " + runtimes + " seconds");
		return;
	}
}
