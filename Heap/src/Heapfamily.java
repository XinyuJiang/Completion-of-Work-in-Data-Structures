import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.awt.*;
import java.util.*;

/**
 * Heap: Maintain a binary max heap of ints that contains mySize elements. The
 * entries of the heap are stored sequentially in the myElements array; thus
 * the array represents a binary tree of mySize nodes whose depth is minimal
 * and in which the bottom level is as far "left" as possible. The parent of
 * the entry at position k is at position (k-1)/2; the two children are at
 * positions 2*k+1 and 2*k+2. Each entry in the heap has at least as large a
 * value as its children.
 */
public class Heapfamily
{
    private Heap top;
    private Heap last;

    private Heapfamily(int firstelement){
        top = new Heap(firstelement);
        last=top;}

    private boolean isEmpty() {
        return (top == null);

    }

    private void add(int newElement) {
        int tag = 0;
        //Heap temp = top.lHeap;

        //if whole heap equals to null
        if (isEmpty()) {
            top = new Heap(newElement);
        }
        if (last == top) {
            top.lHeap =new Heap(newElement);
            top.lHeap.parent = top;
            last=top.lHeap;
            bubbleUp(last);

        } else if (last.parent.rHeap == null) {
            last.parent.rHeap = new Heap(newElement);
            last.parent.rHeap.parent = last.parent;
            last = last.parent.rHeap;
            //newElement.top = newElement.parent.top;
        } else {
            while (last == last.parent.rHeap)//enter loop indicate that current is not the last one.if current is right node, up
            {
                last = last.parent;
                if (last == top)//the case that last one is the mostright one
                {
                    tag = 1;//change tag to 1 indicate last one is the mostright one
                    break;
                }
            }
            if (tag == 0)
                last = last.parent.rHeap;//enter right branch;
            while (last.lHeap != null) {
                last = last.lHeap;
            }
            last.lHeap = new Heap(newElement);
            last.lHeap.parent=last;
            last = last.lHeap;
            //newElement.top = newElement.parent.top;
        }
        bubbleUp(last);

    }

    private void remove() throws NoSuchElementException {
        Heap temp = last;//find the one before last one
        if (isEmpty()) {
            throw new NoSuchElementException("attempt to remove from empty heap");
        }
        top.myElement = last.myElement;
        if (last != top) {
            if (last == last.parent.lHeap)
            {
                while(temp == temp.parent.lHeap)
                {temp = temp.parent;
                    if (temp == top)//last is the leftest one
                    {while(temp.rHeap!=null)
                        temp=temp.rHeap;
                        last.parent.lHeap =null;
                        last = temp;
                        bubbleDown(top);
                        return;}
                }
                temp = temp.parent.lHeap;
                while(temp.rHeap!=null)
                    temp = temp.rHeap;
                last.parent.lHeap =null;
                last=temp;}
            else
            {last.parent.rHeap = null;
                last = last.parent.lHeap;}
        }
        bubbleDown(top);

    }

    //got the last node of this heap(key point of add and remove)
    public Heap last() {
        Heap current = top;
        Heap mostright = top;
        while (mostright.rHeap != null)
            mostright = mostright.rHeap;
        while (current.lHeap != null)
            current = current.lHeap;
        int depth = 0;
        int tempdepth = 0;
        depth = depth();
        tempdepth = depth;
        Heap lastone;//store the one before current
        if (current == top)
            return top;
        if (current.parent.rHeap == null)
            return current;
        while (true) {
            lastone = current;
            while (current == current.parent.rHeap)//enter loop indicate that current is not the last one.if current is right node, up
            {
                current = current.parent;
                tempdepth--;
            }
            current = current.parent.rHeap;//enter right branch;
            if (current == null)
                return lastone;
            while (current.lHeap != null) {
                tempdepth++;
                current = current.lHeap;
            }
            //current equals to last one and on the left of the branch
            if (depth - 1 == tempdepth)
                return lastone;
            if (current == mostright && depth == tempdepth)
                return current;
        }
    }

    private void exchange(Heap k1, Heap k2) {
        int t;
        t = k1.myElement;
        k1.myElement = k2.myElement;
        k2.myElement = t;
    }

    private void bubbleUp(Heap current) {
        int t;
        if (current.myElement < current.parent.myElement)
        /*{t = current.myElement;
            current.myElement = current.parent.myElement;
            current.parent.myElement = t;
            bubbleDown(current.parent);
        }*/ {
            exchange(current, current.parent);
            if (current.parent.parent != null)
                bubbleUp(current.parent);
        }


    }


    private void bubbleDown(Heap current) {
        int t;
        if (current.rHeap == null)
            if (current.myElement > current.lHeap.myElement)
                exchange(current, current.lHeap);
            else return;
        else while (current.lHeap.myElement < current.myElement || current.rHeap.myElement < current.myElement) {
            if (current.lHeap.myElement < current.rHeap.myElement) {
                exchange(current, current.lHeap);
                if (current.lHeap.lHeap != null)//if still have child, down
                    bubbleDown(current.lHeap);
            }
            if (current.myElement > current.rHeap.myElement) {
                exchange(current, current.rHeap);
                if (current.rHeap.lHeap != null)//if still have child, down
                    bubbleDown(current.rHeap);
            }
        }

    }

    public int depth() {
        int depth = 0;
        Heap current = top;
        while (current.lHeap != null) {
            depth++;
            current = current.lHeap;
        }
        return depth;
    }


    public void display() {
        double count = 0;
        //int maxcount = 0;//according layer number, calculate the max of number of element
        int templayer = 0;
        int i = 0;
        int j = 0;
        Heap temp;
        int depth = depth();
        //maxcount = (int)Math.pow(2,depth+1)-1;
        Queue<Heap> queue = new LinkedList<Heap>();
        queue.offer(top);
        while (!queue.isEmpty()) {
            int elenumber = (int) Math.pow(2, templayer);
            //templayer = (int)(Math.log(count+1)/Math.log(2.0));
            for (i = 0; i < elenumber; i++) {
                temp = queue.poll();
                for (j = 0; j < Math.pow(2, depth - templayer + 1) - 1; j++)
                    System.out.print(" ");
                System.out.print(temp.myElement);
                for (j = 0; j < Math.pow(2, depth - templayer + 1) - 1; j++)
                    System.out.print(" ");
                count++;
                if (temp.lHeap != null)
                    queue.offer(temp.lHeap);
                if (temp.rHeap != null)
                    queue.offer(temp.rHeap);
                if (queue.isEmpty())
                    break;
            }
            //if(Math.log(count+1)/Math.log(2.0)%2==0)
            System.out.println("");
            templayer++;


        }


    }

    public class Heap {
        private final boolean DEBUGGING = true;


        private int myElement = -999999;
        private Heap lHeap;
        private Heap rHeap;
        private Heap parent;

        /**
         * Initializes an empty heap to hold up to the given number of elements.
         */

        public Heap(int element) {
            myElement = element;
            lHeap = null;
            rHeap = null;
            //top = this;
        }




    }
    public static void main(String[] args) {
        Heapfamily family = new Heapfamily(97);
        family.add(53);
        family.add(59);
        family.add(26);
        family.add(41);
        family.add(58);
        System.out.println("initialize heap:");
        family.display();
        family.add(1);
        System.out.println("add one element:");
        family.display();
        family.add(2);
        System.out.println("add another element");
        family.display();
        System.out.println("remove one element:");
        family.remove();
        family.display();
        System.out.println("remove one element:");
        family.remove();
        family.display();
    }
}
