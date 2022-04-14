Jacob Betts

COP 4520

4/13/2020



# Homework 3

## Part 1 - Contained in folder/package hw3 in the src folder. 
1. Unfortunately, the servants realized at the end of the day that they had more presents than “Thank you” notes. What could have gone wrong?

The data structure described in the minotaurs strategy is similar to a sequential linked list. These lists are not thread-safe, and more than one servant/thread could try and alter the same node at the same time. This also could lead to a situation where two servants/threads try to add a present at the same time, and since the list does not contain that item at that instance of time, both servants/threads will be able to add to the list, leaving us with more presnts than thank you cards. This is exactly what we see in the minotaur scenario.

To solve this issue I will implement a Concurrent data structure in place of the Sequential Linked List that the minotaur attempted to use. 
My solution implements the LockFreeList from Chapter 9 of the textbook. Explained in terms of this problem:

There are three Tasks that any of the servants may choose to work on at any time. They are as follows:

  Task 1: A servant will look simulate grabbing the top item from the unordered bag of presents by grabbing and atomically incrementing a global atomic addIndex variable. This atomic variable helps to ensure the same number is not added twice. They will then use this index number to pull top present, which will have a randomly generated tag. The bag itself is a shuffled list of numbers between 1 and 500k. Our Add Method in our lockfreelist datastructure does a check to make sure the current key is not contained in the list.
  
 Task 2: A servant will simulate removing a present a sending a thank you card to the gifter. This is handled similarly to task1, with an atomic int that runs through every index of our list, removing each guest from it until none are left. The act of removal in the LockFreeList datastructure makes use a delete node marking, which will be the stand in for our "ty card sent" marking. Once a node is marked for deletion, consider this the act of marking it as having a thank you card send. 

Task 3: At the behest of the Minotaur, a servant will check to see if a number chosen at random by the Minotaur is currently in the list. In my program I execute task 3 every 100000 addition cycles. In task 3 I generate a random number between 500000 and 1 to represent the minotaurs choice, I then store the result of a .contains execution that checks to see if this random number is in the list. The results are printed to the screen.

The program utilizes an Executor Service and a fixed thread pool of 4 threads (as specified in the assignment). Threads pick up and execute tasks as they are available to do so. Solution seems to be very efficient with runtimes of 1.5 - 2.1 seconds. 

## Part 2 - Contained in the folder/package hw3b in the src folder.

I utilized the same LockFreeList that I used in part 1 to design my Temperature Module. I had planned to use a concurrentskiplistset, but it did not work properly, and ended up burning a lot of time. I have a for loop in my main method that executes 60 times for every 1 hour of runtime. You can adjust how many hours the program executes by changing the hour variable in main (by default this is set to 1). Within this for loop 'service.submit(new TempModule().new sensorArray());' is executed 8 times, with each execution representing one of our 8 total sensors that make up the array. Also within the for loop is an if statement that checks if the current index is divisible by 60, or if an hour has elapsed. If an hour has elapsed, then we generate a report.

During each execution of our SensorArray method, we perform checks and keep track of the top 5 highest values seen and top 5 lowest values seen. We do this by utlizing comparison statements and global ints. See Commenting in the program. 

The program utilizes an Executor Service and a fixed thread pool of 8 threads (as specified in the assignment). Threads pick up and execute tasks as they are available to do so. Solution seems to be very efficient with runtimes around .008 - .012 seconds. This solution also ensures that the act of generating the report does not delay or hinder the act of taking readings every minute.
