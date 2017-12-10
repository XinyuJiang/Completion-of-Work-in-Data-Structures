import javax.swing.text.html.HTMLDocument;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

/**
 * AmoebaFamily: Tree and Iterator's practice
 *
 *  @author XinyuJiang
 *    @date 2017/10/3
 * @project Iterator Exercises
 */
public class AmoebaFamily
{
    private Amoeba myHead;  // Head of the AmoebaFamily
    private Stack<Amoeba> s = new Stack<Amoeba>();
    private Stack<Amoeba> sprt = new Stack<Amoeba>();
    int count = 1;
    /**
     * Constructs a new AmoebaFamily containing a single Amoeba (the head of
     * the family).
     * @param name Name of the head Amoeba.
     */
    public AmoebaFamily(String name) { myHead = new Amoeba(name, null); }

    /**
     * Returns a new iterator for this AmoebaFamily.
     * @return New AmoebaIterator instance corresponding to this AmoebaFamily.
     */
    public AmoebaIterator iterator()
    {
        return new AmoebaIterator();
    }
    /**
     * Add a new Amoeba child with a given name to the specified Amoeba parent.
     * Precondition: The AmoebaFamily contains an Amoeba named parentName.
     * @param parentName Name of the parent Amoeba that we want to add a child
     *                   to.
     * @param childName Name of the new Amoeba child.
     */
    public void addChild(String parentName, String childName)
    {
        if(myHead.myName == parentName) {
            myHead.addChild(childName);
        } else {
            Amoeba current = findParent(myHead, parentName);
            current.addChild(childName);
        }
    }
    public Amoeba findParent(Amoeba myHead,String parentName)
    {
        Amoeba result;
        Iterator t = myHead.myChildren.iterator();
        while(t.hasNext()) {
            Amoeba current = (Amoeba) t.next();
            if (current.myName == parentName)
                return current;
            result = findParent(current, parentName);
            if (result != null)
                return result;
        }
        return null;
    }

    /**
     * Print the names of all amoebas in the family. Names should appear in
     * preorder, with children's names printed oldest first. Members of the
     * family constructed with the main driver program below should be printed
     * in the following sequence:
     *
     *     Amos McCoy, mom/dad, me, Mike, Bart, Lisa, Homer, Marge, Bill,
     *     Hilary, Fred, Wilma
     */
    public void print()
    {
        System.out.println(myHead);
        preprint(myHead);

    }

    public void preprint (Amoeba myHead)
    {
        Iterator t = myHead.myChildren.iterator();
        while(t.hasNext()){
            Amoeba current = (Amoeba) t.next();
            System.out.println(current);
            preprint(current);
        }
    }

    /**
     * Construct a family of Amoebas, and then print the family tree using the
     * print() method as well as the AmoebaIterator.
     * @param args Command-line arguments.
     */
    public static void main(String[] args)
    {
        AmoebaFamily family = new AmoebaFamily("Amos McCoy");
        family.addChild("Amos McCoy", "mom/dad");
        family.addChild("mom/dad", "me");
        family.addChild("mom/dad", "Fred");
        family.addChild("mom/dad", "Wilma");
        family.addChild("me", "Mike");
        family.addChild("me", "Homer");
        family.addChild("me", "Marge");
        family.addChild("Mike", "Bart");
        family.addChild("Mike", "Lisa");
        family.addChild("Marge", "Bill");
        family.addChild("Marge", "Hilary");

        System.out.println("Here's the family:");
        family.print();

        System.out.println("");
        System.out.println("Here it is again!");
        AmoebaIterator iter = family.iterator();
        while (iter.hasNext())
        {
            System.out.println(iter.next());
        }
    }

    /**
     * AmoebaIterator: Amoebas in the family are iterated over in preorder,
     * with oldest children first. Members of the family constructed with the
     * main program above should be iterated over in the following sequence:
     *
     *     Amos McCoy, mom/dad, me, Mike, Bart, Lisa, Homer, Marge, Bill,
     *     Hilary, Fred, Wilma
     *
     * Complete iteration of a family of N amoebas should take O(N) operations.
     */
    private class AmoebaIterator implements Iterator
    {
        Stack<Amoeba> dfs = new Stack<Amoeba>();
        List<Amoeba> bfs = new ArrayList<Amoeba>();

        public AmoebaIterator(){
            dfs.push(myHead);
            bfs.add(myHead);
        }
        //dfs search tree
/*        public Amoeba next(){
            Stack<Amoeba> tempdfs = new Stack<Amoeba>();
            Amoeba temp = dfs.pop();
            for (Iterator t = temp.myChildren.iterator();t.hasNext();)
                tempdfs.push((Amoeba) t.next());
            for (Iterator t = tempdfs.iterator();t.hasNext();)
                dfs.push(tempdfs.pop());
            return temp;
        }*/

        //bfs search tree
        public Amoeba next(){
            Amoeba temp = bfs.get(0);
            bfs.remove(0);
            for (Iterator t = temp.myChildren.iterator();t.hasNext();)
                bfs.add((AmoebaFamily.Amoeba)t.next());
            return  temp;
        }


        public boolean hasNext(){
//        if (dfs.size() != 0)
//            return true;
        if (bfs.size() != 0)
            return true;
        return false;
        }

    }

    /**
     * Amoeba: Defines an Amoeba with a name, a parent, and children.
     */
    private class Amoeba
    {
        public String myName;                   // Amoeba's Name
        public Amoeba myParent;                 // Amoeba's Parent
        public ArrayList<Amoeba> myChildren;    // Amoeba's Children

        /**
         * Constructs a new Amoeba with a given name and parent.
         * @param name Name of this Amoeba.
         * @param parent Parent of this Amoeba.
         */
        public Amoeba(String name, Amoeba parent)
        {
            myName = name;
            myParent = parent;
            myChildren = new ArrayList<Amoeba>();
        }

        /**
         * Returns the name of this Amoeba.
         * @return Amoeba name.
         */
        public String getName()
        {
            return myName;
        }

        /**
         * Returns the parent of this Amoeba.
         * @return Parent Amoeba.
         */
        public Amoeba getParent()
        {
            return myParent;
        }

        /**
         * Returns the children of this Amoeba.
         * @return ArrayList containing the children of this Amoeba.
         */
        public ArrayList<Amoeba> getChildren()
        {
            return myChildren;
        }

        /**
         * Constructs a new Amoeba with a given name, and adds it as the
         * youngest child of this current Amoeba.
         * @param childName Amoeba child name.
         */
        public void addChild(String childName)
        {
            Amoeba child = new Amoeba(childName, this);
            myChildren.add(child);
        }

        /**
         * Returns the String representation of this Amoeba.
         * @return Name of this Amoeba.
         */
        public String toString()
        {
            return myName;
        }
    }
}