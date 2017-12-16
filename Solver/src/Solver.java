import jdk.nashorn.internal.ir.Block;

import java.util.*;

public class Solver {

    private static int debugging = 0;
    private static Helper helper = new Helper();
    //Blocks dead = new Blocks(-2,-2);



    public static void main(String[] args)throws Exception{

        //initfile is the address of the init file
        //goalfile is the address of the goal file
        String initfile = null,goalfile = null;
        //record the init block and the goal block
        //Blocks puzzle = new Blocks();

        //record the number of the args
        int argslenth = args.length;
        //don't have the optional configuration
        if (argslenth == 2)
            {
                initfile = args[0];
                goalfile = args[1];
            }
        //debugging method
        if (argslenth == 3)
            {
                initfile = args[1];
                goalfile = args[2];
                if (args[0].equals("-o1"))
                {debugging = 1;
                }
                else if (args[0].equals("-o2"))
                    debugging = 2;
                else
                {System.out.printf("OPTION ERROR!");
                    System.exit(1);}
            }
        Blocks puzzle = helper.readFiles(initfile,goalfile);
        System.out.println("init configuration:");
        puzzle.printself();
        System.out.println("goal configuration:");
        puzzle.printgoal();
        if(debugging==1) {
            System.out.println("Goalgraph print by matrix:");
            puzzle.printgoalgraph();
        }
        HashSet<rectangle> tempinit = new HashSet<>();
        HashSet<rectangle> tempgoal = new HashSet<>();
        if (puzzle.getLength() * puzzle.getWidth()-puzzle.goalrectangle.size() -1==0)
        {
            for(int i = 0; i< puzzle.getWidth();i++)
                for(int j = 0; j< puzzle.getLength();j++)
                {    tempinit.add(new rectangle(1,1,i,j));
                    tempgoal.add(new rectangle(1,1,i,j));}
            tempinit.removeAll(puzzle.initrectangle);
            tempgoal.removeAll(puzzle.goalrectangle);
            puzzle.initrectangle.clear();
            puzzle.goalrectangle.clear();
            puzzle.goalrectangle.addAll(tempinit);
            puzzle.initrectangle.addAll(tempgoal);
        }
            if (uniformcostsearch(puzzle))
                System.out.printf("Got it!");
            else
                System.out.printf("Can't found!");
                System.exit(1);


    }


    public static Comparator<Blocks> idComparator = new Comparator<Blocks>(){

        @Override
        public int compare(Blocks c1, Blocks c2) {
            return (c1.getWeight() - c2.getWeight());
        }
    };

    //use ucs to achieve the key search algorithm
    public static boolean uniformcostsearch(Blocks puzzle){
        //using priorityqueue achieving queue to store next point
        Queue <Blocks> fringe = new PriorityQueue<>(1000,idComparator);
        HashSet<Blocks> close = new HashSet<Blocks>();
        if (!puzzle.isGoal())
        {
            puzzle.update();
            fringe.add(puzzle);
            while(!fringe.isEmpty())
            {
                //pop one element from fringe
                Blocks temp = fringe.remove();
                //this stage haven't been arrived
                if (!close.contains(temp))
                {
                    close.add(temp);
                    if(temp.isGoal())
                        return true;

                    //for every rectangle in the temp
                    for(rectangle r:temp.initrectangle)
                        if (temp.ablemove(r))
                        //try four direction for them
                        for(int direction = 0;direction<4;direction++)
                        //if the new state haven't been arrived before
                        {
                            Blocks next = temp.getnextstate(r,direction);
                            //we didn't find the next state by this rectangle
                            if(next.getLength()<0 || next.getWidth()<0)
                                continue;
                            next.goalrectangle = temp.goalrectangle;
                            if (!close.contains(next)){
                                //close.add(next);
                                if(debugging == 2)
                                {    next.printself();
                                System.out.println ( " Move Rectangle(" + r + ") to " + (
                                        direction == 0? "North": direction == 1? "South":
                                                direction == 2? "West": "East"));}
                                if (!next.isGoal())
                                {next.update();
                                fringe.add(next);}
                                else return true;
                            }
                        }
                }

            }
            return false;
        }
        return true;
    }
    //use dfs to achieve the key search algorithm
    public static boolean depthfirstsearch(Blocks puzzle){
        //using ArrayList achieving queue to store next point
        //*ArrayList is the achieve class of deque(two sides queue), using offer and poll to achieve queue, using pop and push to achieve stack*//*
        ArrayList <Blocks> fringe = new ArrayList<Blocks>();
        HashSet<Blocks> close = new HashSet<Blocks>();
        Blocks puzzle11 = new Blocks(puzzle.getLength(),puzzle.getWidth());
        for (rectangle i : puzzle.initrectangle)
            puzzle11.add(i);
        puzzle11.goalrectangle.addAll(puzzle.goalrectangle);
        //ArrayList<Blocks> route = new ArrayList<Blocks>();
        if (!puzzle.isGoal())
        {
            puzzle11.update();
            fringe.add(puzzle11);
            while(!fringe.isEmpty())
            {
                //pop one element from fringe
                Blocks temp = fringe.remove(fringe.size()-1);
                //this stage haven't been arrived
                if (!close.contains(temp))
                {
                    close.add(temp);
                    if(temp.isGoal())
                        return true;

                    //for every rectangle in the temp
                    for(rectangle r:temp.goalrectangle)
                        if (temp.ablemove(r))
                            //try four direction for them
                            for(int direction = 0;direction<4;direction++)
                            //if the new state haven't been arrived before
                            {
                                Blocks next = temp.getnextstate(r,direction);
                                //we didn't find the next state by this rectangle
                                if(next.getLength()<0 || next.getWidth()<0)
                                    continue;
                                next.initrectangle = temp.initrectangle;
                                if (!close.contains(next)){
                                    //close.add(next);
                                    if(debugging==2)
                                    {next.printself();
                                        System.out.println ( " Move Rectangle(" + r + ") to " + (
                                               direction == 0? "North": direction == 1? "South":
                                                       direction == 2? "West": "East"));}
                                        if (!next.isGoal())
                                        {next.update();
                                            fringe.add(next);}
                                        else return true;
                                }
                            }
                }

            }
            return false;
        }
        return true;
    }
}
