import java.util.ArrayList;
import java.util.HashMap;

// dynamic programming method using Breadth-First Search (BFS) Algorithm

public class Watertank_n_tank {

    public static void main(String[] args) {
        // General Parameters
        ArrayList<Integer> containers_volume = new ArrayList<>();
        ArrayList<Integer> starting_node = new ArrayList<>();

        int vol_wanted=Integer.parseInt(args[0]);
        int containers_count = Integer.parseInt(args[1]);

        for(int i =0; i<containers_count; i++) {
            starting_node.add(0);
            containers_volume.add(Integer.parseInt((args[i + 2])));
        }
        HashMap<Double, Boolean>  check_dict = new HashMap<>();



        search(starting_node, containers_volume, vol_wanted, check_dict,containers_count);

    }

    public static void search(ArrayList<Integer> starting_node, ArrayList<Integer> containers_volume, int vol_wanted, HashMap<Double, Boolean>  check_dict, int containers_count)
    {
        ArrayList<ArrayList<Integer>> target=new ArrayList<>();
        ArrayList<ArrayList<Integer>> q =new ArrayList<>();
        ArrayList<ArrayList<Integer>> path = new ArrayList<>();
        ArrayList<ArrayList<Integer>> next_moves;
        boolean accomplished= false;

        q.add(starting_node);


        while(q.size() != 0) {
            ArrayList<Integer> path_2;
            path_2 = q.get(0);
            ArrayList<Integer> comp = new ArrayList<>();
            for(int i =0; i<containers_count; i++)
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
            double b = get_index(last_path, containers_count);
            check_dict.put(b, true);

            // If target is reached, exit the loop
            if (is_vol_wanted(path, vol_wanted,containers_count)) {
                accomplished = true;
                target = path;
                break;
            }

            // If target no reached, getting the next move
            next_moves = next_step(containers_volume, path, check_dict, containers_count);
            q.addAll(next_moves);
        }
        // If accomplished, print the path
        if(accomplished)
        {
            print_path(target);
            System.out.println(target);
        }
        else
        {
            System.out.println("no solution");
        }

    }

    public static boolean been_there(ArrayList<Integer> node, HashMap<Double, Boolean> check_dict,int containers_count)
    // Check if a node had already been visited
    {
        double a=get_index(node, containers_count);
        return !check_dict.containsKey(a);
    }

    public static ArrayList next_step(ArrayList<Integer> containers_volume, ArrayList<ArrayList<Integer>> path, HashMap<Double, Boolean>  check_dict, int containers_count)
    // Finding the next path with checking the ones already checked
    {
        ArrayList result=new ArrayList<>();
        ArrayList<ArrayList<Integer>> next_nodes=new ArrayList<>();
        ArrayList<Integer> node=new ArrayList<>();
        ArrayList<Integer> last_path;
        ArrayList<Integer> max_containers_volume = new ArrayList<>();
        ArrayList<Integer> containers_status = new ArrayList<>();
        ArrayList<Integer> the_node = new ArrayList<>();

        for(int i =0; i<containers_count; i++)
            max_containers_volume.add(containers_volume.get(i));


        last_path = path.get(path.size()-1);
        for(int i =0; i<containers_count; i++)
            containers_status.add(last_path.get(i));


        // fill in the first tank.
        for(int j =0; j < containers_count; j++) {
            for (int i = 0; i < containers_count; i++)
                the_node.add(containers_status.get(i));
            the_node.set(j, max_containers_volume.get(j));
            if(been_there(the_node, check_dict, containers_count))
                next_nodes.add(new ArrayList<>(the_node));
            the_node.clear();
        }

        for(int j =0; j < containers_count; j++) {
            for (int i = 0; i < containers_count; i++)
                the_node.add(containers_status.get(i));
            the_node.set(j,0);
            if(been_there(the_node, check_dict, containers_count))
                next_nodes.add(new ArrayList<>(the_node));
            the_node.clear();
        }

        // Transfer from tank to another
        for(int i =0; i < containers_count; i++) {
            for (int j = 0; j < containers_count; j++) {
                if (j != i) {
                    the_node = new ArrayList<>(containers_status);
                    the_node.set(i, Math.min(max_containers_volume.get(i), the_node.get(i) + the_node.get(j)));
                    the_node.set(j, (the_node.get(j) - (the_node.get(i) - containers_status.get(i))));
                    if (been_there(the_node, check_dict, containers_count))
                        next_nodes.add(new ArrayList<>(the_node));
                    the_node.clear();
                }
            }

        }

            // Create the list of possible next path
        for (ArrayList arrayList : next_nodes) {
            ArrayList<ArrayList<Integer>> temp;
            temp = new ArrayList<>(path);
            temp.add(arrayList);
            result.add(new ArrayList(temp));
            temp.removeAll(result);
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
//        for(int i =0; i<path.size()-1; i++)
//        {
//            write_transition(path.get(i), path.get(i+1));
//            String step;
//            step = path.get(i+1).toString().replaceAll("\\[","\\(").replaceAll("]","\\)");
//            System.out.println(step);
//        }
    }

    public static boolean is_vol_wanted(ArrayList<ArrayList<Integer>> path, int vol_wanted, int containers_count)
    {
        //Checking the last node and return True if we reached the targeted value

        int temp4;
        ArrayList<Integer> last_path;
        last_path = path.get(path.size()-1);
        for(int i = 0; i < containers_count; i++)
        {
            temp4 = last_path.get(i);
            if(temp4 == vol_wanted) return true;
        }
        return false;

    }

    public static double get_index(ArrayList<Integer> node, int containers_count)
    {
        // generating a random number for the key index using pow to be sure there's no duplicate
        double result_ind = 1;
        for(int i = 0; i < containers_count; i++)
        {
            result_ind = result_ind * Math.pow(3+i, node.get(i));
        }
        return result_ind;
    }
}



