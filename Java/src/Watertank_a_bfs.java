import java.util.ArrayList;
import java.util.Collections;

// dynamic programming method using Breadth-First Search (BFS) Algorithm

public class Watertank_a_bfs {

    public static void main(String[] args) {
        // General Parameters
        ArrayList<Integer> containers_volume = new ArrayList<Integer>();
        ArrayList<ArrayList<String>> steps = new ArrayList<ArrayList<String>>();

        containers_volume.add(Integer.parseInt(args[0]));
        containers_volume.add(Integer.parseInt(args[1]));
        int vol_wanted=Integer.parseInt(args[2]);
        //count(containers_volume, vol_wanted,steps);


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

    public static boolean check_arg(ArrayList containers_volume, int vol_wanted)
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
}
