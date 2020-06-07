import com.sun.tools.jconsole.JConsoleContext;
import java.util.ArrayList;
import javax.lang.model.type.ArrayType;
import java.lang.reflect.Array;
import java.util.*;

public class waterdub {

    public static void main(String[] args) {
        int vol_wanted=0;
        ArrayList<Integer> containers_volume= new ArrayList<>();
        //Scanner sc = new Scanner(System.in);
        //System.out.println("Entrez le nombre de volume désiré");
        //vol_wanted=sc.nextInt();
        vol_wanted = 4;
        //System.out.println("entrer le premier volume ");
        //int temp=sc.nextInt();
        containers_volume.add(3);
        //System.out.println("entrer le deuxième volume ");
        //temp=sc.nextInt();
        containers_volume.add(5);
        ArrayList <ArrayList<Integer>> starting_node=new ArrayList<>();
        ArrayList <Integer> starting_node_temp=new ArrayList<>();
        starting_node_temp.add(0);
        starting_node_temp.add(0);
        starting_node=(ArrayList)   starting_node_temp;
        HashMap check_dict = new HashMap();


        search(starting_node, containers_volume, vol_wanted, check_dict);



    }

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
        ArrayList path=new ArrayList<>();
        ArrayList path_2 = new ArrayList();
        ArrayList<ArrayList<Integer>> next_moves = new ArrayList<>();
        boolean accomplished= false;

        q.add(starting_node);
//        System.out.println(q);

        if(check_arg(containers_volume, vol_wanted))
        {
            while(q.size() != 0) {
                path_2 = (ArrayList) q.get(0);
                System.out.println(path_2.size());
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
                System.out.println("le path2" + path_2);
                System.out.println("le q" + q);
                System.out.println("le path"+path);
                q.remove(0);
                System.out.println("le q apres remove" + q);
                ArrayList last_path = new ArrayList();
                last_path = (ArrayList) path.get(path.size() - 1);
                System.out.println("le last path"+last_path);
                double b = get_index(last_path);
                System.out.println(b);
                check_dict.put(b, true);
                System.out.println(check_dict);
                if (is_vol_wanted(path, vol_wanted)) {
                    accomplished = true;
                    target = path;
                    break;
                }

                next_moves = next_step(containers_volume, path, check_dict);
                System.out.println(("le next move" + next_moves));
                for (int i = 0; i < next_moves.size(); i++) {
                    System.out.println(q);
                    System.out.println(next_moves.get(i));
                    q.add(next_moves.get(i));
                    System.out.println(q);
                }
                System.out.println(q);
            }
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
    {
        double a=get_index(node);
//        System.out.println(node);
//        System.out.println(a);
//        System.out.println(check_dict);

        if (check_dict.containsKey(a))
        {
            System.out.println("true");
            return true;
        }
        else {
            System.out.println(("false"));
            //check_dict.put(a,True);
            //System.out.println(check_dict);
            return false;
        }
    }

    public static ArrayList next_step(ArrayList containers_volume, ArrayList path, HashMap check_dict)
    {
        ArrayList result=new ArrayList<>();
        ArrayList<ArrayList> next_node=new ArrayList<>();
        ArrayList<Integer> node=new ArrayList<>();
        ArrayList last_path = new ArrayList();

        int max_first_cont= (int) containers_volume.get(0);
        int max_second_cont= (int) containers_volume.get(1);
        //ArrayList temp = new ArrayList();
        //System.out.println(temp);
//        System.out.println(path);

        last_path = (ArrayList) path.get(path.size()-1);
        int first_cont=(int) last_path.get(0);
        int second_cont=(int) last_path.get(1);
//        System.out.println(first_cont);
//        System.out.println(second_cont);
//        System.out.println(max_first_cont);
//        System.out.println(max_second_cont);

        node.add(max_first_cont);
        node.add(second_cont);
        System.out.println(node);
        if(!been_there(node, check_dict)){
            next_node.add(new ArrayList(node));
        }
        node.clear();
//        System.out.println(next_node);
//        System.out.println(node);

        node.add(first_cont);
        node.add(max_second_cont);
        if(!been_there(node, check_dict)){
            next_node.add(new ArrayList(node));

        }
        node.clear();

        node.add(Math.min(max_first_cont, first_cont + second_cont));
        node.add(second_cont-((int) node.get(0)-first_cont));
        if(!been_there(node, check_dict))
        {
            next_node.add(new ArrayList(node));
        }
        node.clear();

        node.add(Math.min(first_cont + second_cont, max_second_cont));
        node.add(0,second_cont-((int) node.get(0)-first_cont));
        if(!been_there(node, check_dict))
        {
            next_node.add(new ArrayList(node));
        }
        node.clear();

        node.add(0);
        node.add(second_cont);
        if(!been_there(node,check_dict))
        {
            next_node.add(new ArrayList(node));
        }
        node.clear();

        node.add(first_cont);
        node.add(0);
        if(!been_there(node, check_dict)){
            next_node.add(new ArrayList(node));
        }
        System.out.println("le next node est"+next_node);
        System.out.println(next_node.size());
        if(next_node.size() != 0);
        {
            for(int i=0; i<next_node.size(); i++)
            {
                //temp=path;
                ArrayList temp = new ArrayList();
                temp = new ArrayList(path);
                System.out.println(temp);
                temp.add(next_node.get(i));
                result.add(new ArrayList(temp));
                System.out.println(result);
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
            System.out.println(path.get(i+1));
        }
    }

    public static boolean is_vol_wanted(ArrayList path, int vol_wanted)
    {
        Integer temp4=0;
        Integer temp5=0;
        System.out.println(path);
        ArrayList last_path = new ArrayList();
        last_path = (ArrayList) path.get(path.size()-1);
        temp4= (int) last_path.get(0);
        temp5= (int) last_path.get(1);
        return temp4==vol_wanted || temp5==vol_wanted;

    }

    public static double get_index(ArrayList node)
    {
        System.out.println(node);
        return Math.pow(3,(int) node.get(0)) * Math.pow(4,(int) node.get(1));
    }

}

