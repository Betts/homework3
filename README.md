Jacob Betts

COP 4520

4/13/2020



# Homework 3

## Part 1
1. Unfortunately, the servants realized at the end of the day that they had more presents than “Thank you” notes. What could have gone wrong?


My solution implements the LockFreeList from Chapter 9 of the textbook. Explained in terms of this problem:

There are three Tasks that any of the servants may choose to work on at any time. They are as follows:

  Task 1: A servant will look simulate grabbing the top item from the unordered bag of presents by grabbing and atomically incrementing a global atomic addIndex variable. They will then use this index number to pull top present, which will have a randomly generated tag. The bag itself is a static list
	

