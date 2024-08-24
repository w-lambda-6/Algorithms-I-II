import java.util.Iterator;
import java.util.NoSuchElementException;

//acts like a doubly-circular linked list, due to this fact no need for
//2 pointers: first and last pointers
public class Deque<Item> implements Iterable<Item>{
    //usually these private global variables best be initialized in methods
    private int size;
    private final Node sentinel; // not sure if it's ok

    //well, don't forget that private classes require constructors as well
    private class Node{
        Item item;
        Node next;
        Node pre;
        public Node() {
            item = null;
            next = null;
            pre = null;
        }
        public Node(Item value, Node pre, Node next) {
            this.item = value;
            this.pre = pre;
            this.next = next;
        }
    }

    //deque iterator class used to iterate over deques
    private class dequeIterator implements Iterator<Item>{
        private Node ptr;
        private int remains;

        //private classes must also have constructors
        public dequeIterator(){
            ptr = sentinel.next;
            remains = size;
        }
        @Override
        public Item next(){
            if (remains == 0){
                throw new NoSuchElementException();
            }
            Item retValue = ptr.item;
            ptr = ptr.next;
            remains--;
            return retValue;
        }
        @Override
        public boolean hasNext(){
            return remains > 0;
        }
        @Override
        public void remove(){
            throw new UnsupportedOperationException("No such operation");
        }
    }

////////////////////////////////////////////////////////////////////////////////

    // construct an empty deque
    public Deque(){
        sentinel = new Node();
        sentinel.next = sentinel; // sentinel isn't considered the first node
        sentinel.pre = sentinel;
        size = 0;
    }

    // is the deque empty?
    public boolean isEmpty(){
        return size == 0;
    }

    // return the number of items on the deque
    public int size(){
        return size;
    }

    // add the item to the front
    public void addFirst(Item item){
        if(item == null){
            throw new IllegalArgumentException("Invalid input");
        }
        Node oldfirst = sentinel.next;
        sentinel.next = new Node(item, sentinel, oldfirst);
        oldfirst.pre = sentinel.next;
        size++;
    }

    // add the item to the back
    public void addLast(Item item){
        if(item == null){
            throw new IllegalArgumentException("Invalid input");
        }
        Node oldlast = sentinel.pre;
        sentinel.pre = new Node(item, oldlast, sentinel);
        oldlast.next = sentinel.pre;
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst(){
        if (isEmpty()){
            throw new NoSuchElementException("Cannot remove from empty deque");
        }
        Node oldfirst = sentinel.next;
        sentinel.next = sentinel.next.next;
        oldfirst.next.pre = sentinel;
        //actually no need to, cos everything access through sentinel, but i think it's better
        oldfirst.next = null;
        oldfirst.pre = null;
        size--;
        return oldfirst.item;
    }

    // remove and return the item from the back
    public Item removeLast(){
        if (isEmpty()){
            throw new NoSuchElementException("Cannot remove from empty deque");
        }
        Node oldlast = sentinel.pre;
        sentinel.pre = sentinel.pre.pre;
        oldlast.pre.next = sentinel;
        oldlast.pre = null;
        oldlast.next = null;
        size--;
        return oldlast.item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator(){
        return new dequeIterator();
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> dq = new Deque<>();
        for (int i = 0; i < 5; i++){
            dq.addFirst("A" + i);
        }
        for (int i = 0; i < 5; i++){
            dq.addLast("B" + i);
        }
        for (String s: dq) {
            System.out.println(s);
        }
        System.out.println("dq has" + dq.size() + "elements in total");
        for (int i = 0; i < 10; i++){
            System.out.println(dq.removeFirst());
            System.out.println(dq.removeLast());
            System.out.println(dq.size());
        }
    }
}
