import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import javax.lang.model.type.ArrayType;
import java.lang.reflect.Array;
import java.util.*;

// dynamic programming method using Breadth-First Search (BFS) Algorithm

public class Watertank_a_bfs {

    public static void main(String[] args) {
        // General Parameters
        ArrayList<Integer> containers_volume = new ArrayList<Integer>();
        ArrayList<ArrayList<String>> steps = new ArrayList<ArrayList<String>>();

        containers_volume.add(Integer.parseInt(args[0]));
        containers_volume.add(Integer.parseInt(args[1]));
        int vol_wanted=Integer.parseInt(args[2]);

        ArrayList starting_node=new ArrayList<>();
        ArrayList <Integer> starting_node_temp=new ArrayList<>();
        starting_node_temp.add(0);
        starting_node_temp.add(0);
        starting_node= starting_node_temp;
        HashMap check_dict = new HashMap();


        search(starting_node, containers_volume, vol_wanted, check_dict);

    }

    // # GCD function needed to check if there is a solution
    public static int gcd(int a, int b)
    {
        if(b==0)
        {
            return a;
        }
        else
        {
            return gcd(b,a%b) ;
        }
    }

    public static boolean check_arg(ArrayList<Integer> containers_volume, int vol_wanted)
    {
        // If water required is superior of the biggest tank OR Bezout lemma is not respected, it's false (if x and y are nonzero integers and g = gcd(x,y),
        // then there exist integers a and b such that ax+by=g.
        if( (vol_wanted> Math.max( (int) containers_volume.get(0), (int) containers_volume.get(1) )) || (vol_wanted % (gcd((int) containers_volume.get(0), (int) containers_volume.get(1))) !=0) )
        {
            System.out.println("No solution");
            return false;
        }
        else
        {
            return true;
        }

    }


    public static void search(ArrayList starting_node, ArrayList containers_volume, int vol_wanted, HashMap check_dict)
    {
        ArrayList<Integer> target=new ArrayList<>();
        ArrayList<ArrayList<Integer>> q =new ArrayList<>();
        ArrayList path = new ArrayList<>();
        ArrayList path_2;
        ArrayList<ArrayList<Integer>> next_moves = new ArrayList<>();
        boolean accomplished= false;

        q.add(starting_node);

        if(check_arg(containers_volume, vol_wanted))
        {
            while(q.size() != 0) {
                path_2 = (ArrayList) q.get(0);
                ArrayList comp = new ArrayList();
                comp.add(0);
                comp.add(0);
                ArrayList comp_2 = comp;
                if (path_2.equals(comp_2))
                {
                    path.add(new ArrayList(path_2));
                }
                else{
                    path = (new ArrayList(path_2));
                }
                q.remove(0);
                ArrayList last_path = new ArrayList();
                last_path = (ArrayList) path.get(path.size() - 1);
                double b = get_index(last_path);
                check_dict.put(b, true);

                // If target is reached, exit the loop
                if (is_vol_wanted(path, vol_wanted)) {
                    accomplished = true;
                    target = path;
                    break;
                }

                // If target no reached, getting the next move
                next_moves = next_step(containers_volume, path, check_dict);
                for (int i = 0; i < next_moves.size(); i++) {
                    q.add(next_moves.get(i));

                }
            }
            // If accomplished, print the path
            if(accomplished)
            {
                print_path(target);
            }
            else
            {
                System.out.println("no solution");
            }
        }
    }





    public static boolean been_there(ArrayList node, HashMap check_dict)
            // Check if a node had already been visited
    {
        double a=get_index(node);
        if (check_dict.containsKey(a))
        {
            return true;
        }
        else {
            return false;
        }
    }

    public static ArrayList next_step(ArrayList containers_volume, ArrayList path, HashMap check_dict)
            // Finding the next path with checking the ones already checked
    {
        ArrayList result=new ArrayList<>();
        ArrayList<ArrayList> next_node=new ArrayList<>();
        ArrayList<Integer> node=new ArrayList<>();
        ArrayList last_path;

        int max_first_cont= (int) containers_volume.get(0);
        int max_second_cont= (int) containers_volume.get(1);


        last_path = (ArrayList) path.get(path.size()-1);
        int first_cont=(int) last_path.get(0);  // initial amount in the first tank
        int second_cont=(int) last_path.get(1);  // initial amount in the second tank


        // fill in the first tank
        node.add(max_first_cont);
        node.add(second_cont);
        if(!been_there(node, check_dict)){
            next_node.add(new ArrayList(node));
        }
        node.clear();

        // fill in the second tank
        node.add(first_cont);
        node.add(max_second_cont);
        if(!been_there(node, check_dict)){
            next_node.add(new ArrayList(node));

        }
        node.clear();

        // Transfer second tank to first tank (B => A)
        node.add(Math.min(max_first_cont, first_cont + second_cont));
        node.add(second_cont-((int) node.get(0)-first_cont));
        if(!been_there(node, check_dict))
        {
            next_node.add(new ArrayList(node));
        }
        node.clear();

        // Transfer first tank to second tank (A => B)
        node.add(Math.min(first_cont + second_cont, max_second_cont));
        node.add(0,second_cont-((int) node.get(0)-first_cont));
        if(!been_there(node, check_dict))
        {
            next_node.add(new ArrayList(node));
        }
        node.clear();

        // Empty the first tank
        node.add(0);
        node.add(second_cont);
        if(!been_there(node,check_dict))
        {
            next_node.add(new ArrayList(node));
        }
        node.clear();

        // Empty the second tank
        node.add(first_cont);
        node.add(0);
        if(!been_there(node, check_dict)){
            next_node.add(new ArrayList(node));
        }

        if(next_node.size() != 0);
        {
            // Create the list of possible next path
            for(int i=0; i<next_node.size(); i++)
            {
                //temp=path;
                ArrayList temp = new ArrayList();
                temp = new ArrayList(path);
                temp.add(next_node.get(i));
                result.add(new ArrayList(temp));
                temp.removeAll(result);
            }

        }
        return result;

    }

    public static void write_transition(ArrayList old, ArrayList m_new)
    {
        int first_cont=(int) old.get(0);
        int second_cont=(int) old.get(1);
        int new_first_cont=(int) m_new.get(0);
        int new_second_cont=(int) m_new.get(1);

        if(first_cont>new_first_cont){
            if(second_cont==new_second_cont)
            {
                System.out.print("A->* : ");
            }
            else
            {
                System.out.print("A->B : ");
            }

        }
        else
        {
            if (second_cont > new_second_cont) {
                if (first_cont == new_first_cont){
                    System.out.print("B->* : ");
                }
                else
                {
                    System.out.print("B->A : ");
                }
            }
            else
            {
                if(first_cont == new_first_cont)
                {
                    System.out.print("*->B : ");
                }
                else
                {
                    System.out.print("*->A : ");
                }
            }
        }

    }

    public static  void print_path(ArrayList path)
    {
        System.out.println("Solution in "+(path.size()-1)+" steps");
        for(int i =0; i<path.size()-1; i++)
        {
            write_transition((ArrayList) path.get(i), (ArrayList) path.get(i+1));
            String step = new String();
            step = path.get(i+1).toString().replaceAll("\\[","\\(").replaceAll("\\]","\\)");
            System.out.println(step);
        }
    }

    public static boolean is_vol_wanted(ArrayList path, int vol_wanted)
    {
        //Checking the last node and return True if we reached the targeted value

        Integer temp4=0;
        Integer temp5=0;
        ArrayList last_path = new ArrayList();
        last_path = (ArrayList) path.get(path.size()-1);
        temp4= (int) last_path.get(0);
        temp5= (int) last_path.get(1);

        return temp4==vol_wanted || temp5==vol_wanted;

    }

    public static double get_index(ArrayList node)
    {
        // generating a random number for the key index using pow to be sure there's no duplicate
        return Math.pow(3,(int) node.get(0)) * Math.pow(4,(int) node.get(1));
    }

}



