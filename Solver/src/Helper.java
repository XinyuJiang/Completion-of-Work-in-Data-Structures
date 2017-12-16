import java.io.BufferedReader;
import java.io.FileReader;

public class Helper
{
    public static Blocks readFiles(String init, String goal) throws Exception{
        BufferedReader init_reader = new BufferedReader (new FileReader (init));
        BufferedReader goal_reader = new BufferedReader (new FileReader(goal));

        //read line of the file
        String line;
        //analyze every token of the line
        String tok[];

        line = init_reader.readLine();
        //split every token by blank
        tok = line.split(" ");

        Blocks initblock = new Blocks(Integer.parseInt(tok[0]),Integer.parseInt(tok[1]));

        System.out.println("Blocksize:"+initblock.getLength()+"x"+initblock.getWidth());

        //store the init rectangle
        while ( (line = init_reader.readLine()) != null ) {
			/* implement to parse the representation of block */
            tok = line.split(" ");
            initblock.add( new rectangle(Integer.parseInt(tok[0]), Integer.parseInt(tok[1]), Integer.parseInt(tok[2]), Integer.parseInt(tok[3])) );
        }
        //store the goal rectangle
        while ( (line = goal_reader.readLine()) != null ) {
			/* implement to parse the representation of block */
            tok = line.split(" ");
            initblock.goalrectangle.add ( new rectangle(Integer.parseInt(tok[0]), Integer.parseInt(tok[1]),
                    Integer.parseInt(tok[2]), Integer.parseInt(tok[3])) );
        }
        init_reader.close();
        goal_reader.close();
        return initblock;
    }

    public static void main (String [] args) throws Exception{
        int debugging = 0;
        String initfile = null,goalfile = null;
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
        Blocks c1 = readFiles(initfile,goalfile);

        System.out.println("Init Blocks:");
        c1.printself();
        System.out.println("Goal Blocks:");
        c1.printgoal();



    }


}

