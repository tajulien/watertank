import java.util.ArrayList;
import java.util.HashMap;

// dynamic programming method using Breadth-First Search (BFS) Algorithm

public class Watertank_a_bfs {

    public static void main(String[] args) {
        // General Parameters
        ArrayList<Integer> containers_volume = new ArrayList<>();

        containers_volume.add(Integer.parseInt(args[0]));
        containers_volume.add(Integer.parseInt(args[1]));
        int vol_wanted=Integer.parseInt(args[2]);

        ArrayList<Integer> starting_node;
        ArrayList <Integer> starting_node_temp=new ArrayList<>();
        starting_node_temp.add(0);
        starting_node_temp.add(0);
        starting_node= starting_node_temp;
        HashMap<Double, Boolean>  check_dict = new HashMap<>();


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
        if( (vol_wanted> Math.max(containers_volume.get(0), containers_volume.get(1))) || (vol_wanted % (gcd(containers_volume.get(0), containers_volume.get(1))) !=0) )
        {
            System.out.println("No solution");
            return false;
        }
        else
        {
            return true;
        }

    }


    public static void search(ArrayList<Integer> starting_node, ArrayList<Integer> containers_volume, int vol_wanted, HashMap<Double, Boolean>  check_dict)
    {
        ArrayList<ArrayList<Integer>> target=new ArrayList<>();
        ArrayList<ArrayList<Integer>> q =new ArrayList<>();
        ArrayList<ArrayList<Integer>> path = new ArrayList<>();
        ArrayList<ArrayList<Integer>> next_moves;
        boolean accomplished= false;

        q.add(starting_node);

        if(check_arg(containers_volume, vol_wanted))
        {
            while(q.size() != 0) {
                ArrayList<Integer> path_2;
                path_2 = q.get(0);
                ArrayList<Integer> comp = new ArrayList<>();
                comp.add(0);
                comp.add(0);
                if (path_2.equals(comp))
                {
                    path.add(new ArrayList<>(path_2));
                }
                else{
                    path = (new ArrayList (path_2));
                }
                q.remove(0);
                ArrayList<Integer> last_path;
                last_path = path.get(path.size() - 1);
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
                q.addAll(next_moves);
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





    public static boolean been_there(ArrayList<Integer> node, HashMap<Double, Boolean> check_dict)
            // Check if a node had already been visited
    {
        double a=get_index(node);
        return !check_dict.containsKey(a);
    }

    public static ArrayList next_step(ArrayList<Integer> containers_volume, ArrayList<ArrayList<Integer>> path, HashMap<Double, Boolean>  check_dict)
            // Finding the next path with checking the ones already checked
    {
        ArrayList result=new ArrayList<>();
        ArrayList<ArrayList<Integer>> next_node=new ArrayList<>();
        ArrayList<Integer> node=new ArrayList<>();
        ArrayList<Integer> last_path;

        int max_first_cont= containers_volume.get(0);
        int max_second_cont= containers_volume.get(1);


        last_path = path.get(path.size()-1);
        int first_cont= last_path.get(0);  // initial amount in the first tank
        int second_cont= last_path.get(1);  // initial amount in the second tank


        // fill in the first tank
        node.add(max_first_cont);
        node.add(second_cont);
        if(been_there(node, check_dict)){
            next_node.add(new ArrayList<>(node));
        }
        node.clear();

        // fill in the second tank
        node.add(first_cont);
        node.add(max_second_cont);
        if(been_there(node, check_dict)){
            next_node.add(new ArrayList<>(node));

        }
        node.clear();

        // Transfer second tank to first tank (B => A)
        node.add(Math.min(max_first_cont, first_cont + second_cont));
        node.add(second_cont-(node.get(0) -first_cont));
        if(been_there(node, check_dict))
        {
            next_node.add(new ArrayList<>(node));
        }
        node.clear();

        // Transfer first tank to second tank (A => B)
        node.add(Math.min(first_cont + second_cont, max_second_cont));
        node.add(0,second_cont-(node.get(0) -first_cont));
        if(been_there(node, check_dict))
        {
            next_node.add(new ArrayList<>(node));
        }
        node.clear();

        // Empty the first tank
        node.add(0);
        node.add(second_cont);
        if(been_there(node, check_dict))
        {
            next_node.add(new ArrayList<>(node));
        }
        node.clear();

        // Empty the second tank
        node.add(first_cont);
        node.add(0);
        if(been_there(node, check_dict)){
            next_node.add(new ArrayList<>(node));
        }

        {
            // Create the list of possible next path
            for (ArrayList arrayList : next_node) {
                //temp=path;
                ArrayList<ArrayList<Integer>> temp;
                temp = new ArrayList<>(path);
                temp.add(arrayList);
                result.add(new ArrayList(temp));
                temp.removeAll(result);
            }

        }
        return result;

    }

    public static void write_transition(ArrayList<Integer> old, ArrayList<Integer> m_new)
    {
        int first_cont= old.get(0);
        int second_cont= old.get(1);
        int new_first_cont= m_new.get(0);
        int new_second_cont= m_new.get(1);

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

    public static  void print_path(ArrayList<ArrayList<Integer>> path)
    {
        System.out.println("Solution in "+(path.size()-1)+" steps");
        for(int i =0; i<path.size()-1; i++)
        {
            write_transition(path.get(i), path.get(i+1));
            String step;
            step = path.get(i+1).toString().replaceAll("\\[","\\(").replaceAll("]","\\)");
            System.out.println(step);
        }
    }

    public static boolean is_vol_wanted(ArrayList<ArrayList<Integer>> path, int vol_wanted)
    {
        //Checking the last node and return True if we reached the targeted value

        int temp4;
        int temp5;
        ArrayList<Integer> last_path;
        last_path = path.get(path.size()-1);
        temp4= last_path.get(0);
        temp5= last_path.get(1);

        return temp4==vol_wanted || temp5==vol_wanted;

    }

    public static double get_index(ArrayList<Integer> node)
    {
        // generating a random number for the key index using pow to be sure there's no duplicate
        return Math.pow(3, node.get(0)) * Math.pow(4, node.get(1));
    }

}



