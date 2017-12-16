import jdk.nashorn.internal.ir.Block;

import java.lang.*;
import java.util.*;
import java.util.ListIterator;

public class Blocks {
    private int length;
    private int width;
    private int weight;
    HashSet<rectangle> initrectangle = new HashSet<>();
    HashSet<rectangle> goalrectangle = new HashSet<>();
    //judge whether there have some rectangle overlap
    //store the empty coordinate
    //public HashSet<point> empty	= new HashSet<point> ();

    public boolean [] matrix ;

    public Blocks(int length,int width){
        this.length = length;
        this.width = width;
        //for(int i = 0;i<length*width;i++)
        if (length<0 && width<0)
            matrix = new boolean[length * width];
        else    if(length>0 && width>0)
            matrix = new boolean[length * width];
        else   matrix = new boolean[-length * width];

    }
    public Blocks(){matrix = new boolean[999999];}
/*
    public void setLength(int length) {
        this.length = length;
    }

    public void setWidth(int width) {
        this.width = width;
    }*/

    public int getLength() {
        return length;
    }

    public int getWidth() {
        return width;
    }

    public int getWeight(){return weight;}

    public void update(){

        for (rectangle goal: goalrectangle)
        {
            for (rectangle init: initrectangle)
                if (init.rwidth == goal.rwidth && init.rlength == goal.rlength) {
                    this.weight += Math.abs(init.rx - goal.rx) + Math.abs(init.ry - goal.ry);
            }
        }


    }

    public boolean isGoal(){
        //return goalrectangle.equals(initrectangle);
        int correctcount = 0;
        for(rectangle test : goalrectangle)
        {
            if (initrectangle.contains(test))
                correctcount++;
        }
        //every goal have been matched;
        return (correctcount == goalrectangle.size());
    }



    @Override
    public boolean equals (Object o) {
        Blocks b = (Blocks) o;
        for(rectangle test:b.initrectangle)
            if (!this.initrectangle.contains(test))
                return false;
        return true;
    }

    @Override
    public int hashCode ( ) {
        return 10*this.length+11*this.width;
    }




    //add new rectangle to the blocks
    public void add(rectangle r) {
        initrectangle.add(r);
        for(int i = r.rx;i<r.rx+r.rlength;i++)
            for(int j =r.ry;j<r.ry+r.rwidth;j++) {
                if (i * width + j < 0)
                    return ;
                else matrix[i * width + j]=true;
            }
        }

    public boolean ablemove(rectangle r){
        if ((r.rx - 1) * width + r.ry>=0)
            if(!matrix[(r.rx - 1) * width + r.ry])
                return true;
        if ((r.rx + r.rlength ) * width + r.ry + r.rwidth -1< width * length)
            if(!matrix[(r.rx + r.rlength) * width + r.ry + r.rwidth - 1])
                return true;
        if (r.rx * width + r.ry - 1>=0)
            if(!matrix[r.rx * width + r.ry - 1])
                return true;
        if ((r.rx + r.rlength -1 )* width + r.ry + r.rwidth < width * length)
            if(!matrix[(r.rx + r.rlength -1)* width + r.ry + r.rwidth])
                return true;
        return false;
    }


    public Blocks getnextstate(rectangle r,int direction){
        //get every rectangle by iter
        Blocks dead = new Blocks(-2,-2);

        Blocks child = new Blocks(length,width);
            rectangle temp ;
            child.goalrectangle = this.goalrectangle;
            //construct a new Blocks
            child.initrectangle.addAll(this.initrectangle);
            child.initrectangle.remove(r);
            //for(rectangle same : this.initrectangle)

            child.matrix = this.matrix.clone();
            for(int i = r.rx;i<r.rx+r.rlength;i++)
                for(int j =r.ry;j<r.ry+r.rwidth;j++) {
                    child.matrix[i * width + j]=false;
            }
                    switch (direction){
                        //north
                        case 0:
                            temp = new rectangle(r.rlength,r.rwidth,r.rx-1,r.ry);
                            if(this.islegal(temp,direction))
                                child.add(temp);
                            else
                                return dead;
                            break;
                        //south
                        case 1:temp = new rectangle(r.rlength,r.rwidth,r.rx+1,r.ry);
                            if(this.islegal(temp,direction))
                            child.add(temp);
                            else
                                return dead;
                            break;
                        //west
                        case 2:temp = new rectangle(r.rlength,r.rwidth,r.rx,r.ry-1);
                            if(this.islegal(temp,direction))
                            child.add(temp);
                            else
                                return dead;
                            break;
                        //east
                        case 3:temp = new rectangle(r.rlength,r.rwidth,r.rx,r.ry+1);
                            if(this.islegal(temp,direction))
                            child.add(temp);
                            else
                                return dead;
                            break;
                    }
                return child;
    }



    public boolean islegal(rectangle r,int direction) {
        //out of the range
        if (r.ry<0 || r.rx<0 || r.rx+r.rlength>length || r.ry+r.rwidth>width) {
            return false;
        }
        //overlap
        switch (direction){
            case 0://north
                //for(int i = r.rx;i<r.rx+r.rlength;i++)
                for(int j =r.ry;j<r.ry+r.rwidth;j++) {
                    if (matrix[r.rx * width + j])
                        return false;
                }
                break;
            case 1://south
                //for(int i = r.rx;i<r.rx+r.rlength;i++)
                for(int j =r.ry;j<r.ry+r.rwidth;j++) {
                    if (matrix[(r.rx+r.rlength-1) * width + j])
                        return false;
                }
                break;
            case 2://west
                for(int i = r.rx;i<r.rx+r.rlength;i++){
                //for(int j =r.ry;j<r.ry+r.rwidth;j++){
                    if (matrix[i * width + r.ry])
                        return false;
                }
                break;
            case 3://east
                for(int i = r.rx;i<r.rx+r.rlength;i++){
                //for(int j =r.ry;j<r.ry+r.rwidth;j++) {
                    if (matrix[i * width + r.rwidth + r.ry -1])
                        return false;
                }
                break;
        }
        return true;

    }

    public void printself(){
        System.out.printf("len and width: %d %d\n",length,width);
        //System.out.println();
        for(int i = 0;i<length ;i++) {
            for (int j = 0; j < width; j++)
                if (matrix[i * width + j])
                    System.out.print("1");
                else System.out.print("0");
            System.out.println("");
        }
        }

    public void printgoal(){
        for(rectangle temp : goalrectangle)
            System.out.printf("%d %d %d %d\n",temp.rlength,temp.rwidth,temp.rx,temp.ry);
    }
    public void printgoalgraph(){
        int goalmatrix[ ] = new int [length * width];
        for (rectangle goal : goalrectangle)
            goalmatrix[goal.rx*width + goal.ry] = 1;
        for(int i = 0;i<length ;i++) {
            for (int j = 0; j < width; j++)
                System.out.print(goalmatrix[i * width + j]);
            System.out.println("");


    }
    }

    public static void main (String [] args) {
        Blocks c1 = new Blocks (2, 2);
        Blocks c2 = new Blocks (2, 2);

        rectangle b = new rectangle (1, 1, 0, 0);
        c1.add (b);
        c2.add (b);
        System.out.println ("block 1");
        c1.printself ();
        System.out.println ("block 2");
        c2.printself ();
        System.out.println ("equals: " + c1.equals(c2));

        System.out.println ("Move block (1, 1, 0, 0) t0 South ");
        c1.getnextstate (b, 2).printself ();


    }

}

