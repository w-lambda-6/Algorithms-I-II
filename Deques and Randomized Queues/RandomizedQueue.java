import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] array; // use array instead of linked list, easier to access elements by index
    private int size; //number of elements
    private int capacity; // size of the array
    private final int originalSize = 8;
    private final double ratio = 0.25; //used for array shrinking


    private class RQIterator implements Iterator<Item> {
        private Item[] randomCopy; // you really don't want to change the order of the original array
        private int remaining;
        // no need, they didn't say from front to back, can be any random order even, private int index;

        public RQIterator(){
            randomCopy = (Item[]) new Object[size];
            for (int i = 0; i < size; i++){
                randomCopy[i] = array[i];
            }
            StdRandom.shuffle(randomCopy); // no need to assign, returns null, just shuffles the order of the array
            remaining = size;
        }

        @Override
        public Item next(){
            if(remaining == 0){
                throw new NoSuchElementException("End of queue");
            }
            return randomCopy[--remaining];
        }

        @Override
        public boolean hasNext(){
            return remaining > 0;
        }

        @Override
        public void remove(){
            throw new UnsupportedOperationException("No such operation");
        }
    }

    /////////////////////////////////////////////////////////////////////

    // construct an empty randomized queue
    public RandomizedQueue() {
        array = (Item[]) new Object[originalSize];
        size = 0;
        capacity = originalSize;
        // no need to fill the empty slots with null, just don't access them
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item){
        if (item == null){
            throw new IllegalArgumentException("Cannot add null");
        }

        if (size == capacity) {
            Item [] oldArray = array;
            capacity *= 2;
            array = (Item[]) new Object[capacity];
            for (int i = 0; i < size; i++) {
                array[i] = oldArray[i];
            }
        }
        array[size] = item;
        size++;
    }

    // remove and return a random item
    // no need to move all the elements after the removed element to fill
    // up the gap, because of RQ's random nature
    public Item dequeue(){
        if (isEmpty()){
            throw new NoSuchElementException("Empty queue");
        }

        int randomIndex = StdRandom.uniform(size);
        Item removed = array[randomIndex];
        //filling up the gap simply with the last element, easy-peas
        array[randomIndex] = array[size-1];
        array[size-1] = null; //important!!!
        size--;

        //shrinking the array if necessary
        if (((double)size/capacity) <= ratio && size > 0) {
            Item [] oldArray = array;
            capacity /= 2;
            array = (Item[]) new Object[capacity];
            for (int i = 0; i < size; i++) {
                array[i] = oldArray[i];
            }
        }
        return removed;
    }

    // return a random item (but do not remove it)
    public Item sample(){
        if (isEmpty()){
            throw new NoSuchElementException("Empty queue");
        }
        int randomIndex = StdRandom.uniform(size);
        return array[randomIndex];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator(){
        return new RQIterator();
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<>();
        for (int i = 0; i < 18; i++) {
            rq.enqueue("A" + i);
        }
        System.out.println("first iterator");
        for (String s : rq) {
            System.out.print(s + " ");
        }
        System.out.println();
        System.out.println("second iterator ");
        for (String s : rq) {
            System.out.print(s + " ");
        }
        System.out.println();
        for (int i = 0; i < 18; i++) {
            System.out.print("deque ");
            System.out.print(rq.dequeue());
            System.out.println(". remain " + rq.size() + " elements. now capacity ");
        }
    }
}
