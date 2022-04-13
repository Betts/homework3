package hw3;

import java.util.concurrent.atomic.AtomicMarkableReference;

//Lock Free List Algorithm taken from Chapter 9 of the book
public class LockFreeList<T> {
 
Node head;

 public LockFreeList(){
     this.head = new Node(Integer.MAX_VALUE);
     Node tail = new Node(Integer.MAX_VALUE);

     while (!head.next.compareAndSet(null, tail, false, false));
 }

 // Structure of a "window" 
 class Window {
     public Node pred; 
     public Node curr;
     Window(Node myPred, Node myCurr) {
         pred = myPred; curr = myCurr;
     }
 }
 // This function returns the "window" where the key is found in our list. The "window" is the above structure
 // with its "pred" and "curr" nodes set to the nodes on the left and right of the key 
 public Window find(Node head, int key) {
     Node pred = null, curr = null, succ = null;
     boolean[] marked = {false};
     boolean snip;
     retry: while (true) {
         pred = head;
         curr = pred.next.getReference();
         while (true) {
             succ = curr.next.get(marked);
             while (marked[0]) {
                 snip = pred.next.compareAndSet(curr, succ, false, false);
                 if (!snip) continue retry;
                 curr = succ;
                 succ = curr.next.get(marked);
             }
             if(curr.key >= key)
                 return new Window(pred, curr);
             pred = curr;
             curr = succ;
         }
     }
 }

 // Function for adding new items to the list
 public boolean add(T item) {
     int key = item.hashCode();
     while (true) {
         Window window = find(head, key);
         Node pred = window.pred, curr = window.curr;
         if (curr.key == key) {
             return false;
         } else {
             Node node = new Node(item);
             node.next = new AtomicMarkableReference<>(curr, false);
             if (pred.next.compareAndSet(curr, node, false, false)) {
                 return true;
             }
         }
     }
 }

 // Removal function
 public boolean remove(T item) {
     int key = item.hashCode();
     boolean snip;
     while (true) {
         Window window = find(head, key);
         Node pred = window.pred, curr = window.curr;
         if (curr.key != key) {
             return false;
         } else {
             Node succ = curr.next.getReference();
             snip = curr.next.compareAndSet(succ, succ, false, true);
             if (!snip)
                 continue;
             pred.next.compareAndSet(curr, succ, false, false);
             return true;
         }
     }
 }

 // This function utilizes our window function to search our list for the passed in key
 public boolean contains(T item) {
     int key = item.hashCode();
     Window window = find(head, key);
     Node pred = window.pred;
     Node curr = window.curr;
     return (curr.key == key);
 }
 
 // Our node class
 private class Node {
	 T item;
     int key;
     AtomicMarkableReference<Node> next;

     // Constructors
     public Node(T item) {
         this.item = item;
         this.key = item.hashCode();
         this.next = new AtomicMarkableReference<Node>(null, false);
     }

     public Node(int key) {
         this.item = null;
         this.key = key;
         this.next = new AtomicMarkableReference<Node>(null, false);
     }
 }

}
