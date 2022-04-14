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

Task 3: 

## Part 2 - Contained in the folder/package hw3b in the src folder.

I utilized the same LockFreeList that I used in part 1 to design my Temperature Module. I had planned to use a concurrentskiplistset, but it did not work properly, and ended up burning a lot of time. I have a for loop in my main method that executes 60 times for every 
