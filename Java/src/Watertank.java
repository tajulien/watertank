import java.util.ArrayList;
import java.util.Collections;

public class Watertank {

    public static void main(String[] args) {
        // General Parameters
        ArrayList<Integer> containers_volume = new ArrayList<Integer>();
        ArrayList<ArrayList<String>> steps = new ArrayList<ArrayList<String>>();

        int vol_wanted=Integer.parseInt(args[0]);
        containers_volume.add(Integer.parseInt(args[1]));
        containers_volume.add(Integer.parseInt(args[2]));
        count(containers_volume, vol_wanted,steps);

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

    public static int transfer(ArrayList cont_vol, int x , ArrayList<ArrayList<String>> steps)
    {
        //initializing max volumes
        int to_cont_max= (int) cont_vol.get(0);
        int from_cont_max= (int) cont_vol.get(1);

        // filling the first Tank
        int from_cont=from_cont_max;
        int to_cont=0;
        int count=1;
        ArrayList<String> steps_temp = new ArrayList<String>();
        steps_temp.add("*->A : ("+from_cont+","+to_cont+")");


        while ((from_cont != x) && (to_cont!=x))
        {
            // Get the maximum water capacity to be transfered
            int temp= Math.min(from_cont, to_cont_max - to_cont);

            // We can transfer from a tank to the other one and add the step
            to_cont += temp;
            from_cont -= temp;
            count += 1;
            steps_temp.add("A->B : ("+from_cont+","+to_cont+")");

            // If one of the tank reach the target value, break the loop
            if((from_cont == x)||(to_cont == x))
            {
                break;
            }

            // If the first tank (filler) is empty, fill it
            else if (from_cont == 0)
            {
                from_cont = from_cont_max;
                count += 1;
                steps_temp.add("*->A : ("+from_cont+","+to_cont+")");

            }

            // If the second tank (receiver) is full, empty it
            else if(to_cont == to_cont_max)
            {
                to_cont = 0;
                count += 1;
                steps_temp.add("B->* : ("+from_cont+","+to_cont+")");

            }
        }
        steps.add(steps_temp);
        return count;
    }

    public static int count(ArrayList cont_vol, int vol_wanted,  ArrayList<ArrayList<String>> steps)
    {
        // If water required is superior of the biggest tank, there's no solution
        if (vol_wanted > Math.max((int)cont_vol.get(0), (int)cont_vol.get(1))){
            System.out.println("No solution");
            return 0;
        }
        //With the Bezout Lemma we can check if there's a solution such as + by = z
        // Lemma : if x and y are nonzero integers and g = gcd(x,y),
        // then there exist integers a and b such that ax+by=g.
        else if (vol_wanted % (gcd((int)cont_vol.get(0), (int)cont_vol.get(1))) != 0){
            System.out.println("No solution");
            return  0;
        }
        // With 2 tanks, there is two path to reach the solution
        // We try both solution by filling the bigger tank and then the smaller one
        // And then take the one with the smallest count of steps.
        ArrayList cont_vol_rev = new ArrayList<Integer>();
        int sol_1 = transfer(cont_vol, vol_wanted, steps);
        Collections.reverse(cont_vol);
        int sol_2 = transfer(cont_vol, vol_wanted, steps);
        System.out.println("Solution in "+ Math.min(sol_1,sol_2)+ " steps");

        if(sol_1<sol_2)
        {
            steps.get(0).forEach(name ->{
                System.out.println(name); });

        }
        else
        {
            steps.get(1).forEach(name ->{
                System.out.println(name); });

        }

        return 1;



    }
}
