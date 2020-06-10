using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Collections;

namespace ConsoleApplication1
{
    class Program
    {
        private static void Main() 
        {

            List<int> containers_volume = new List<int>();
            List<int> starting_node = new List<int>();

            int vol_wanted = 4;
            int containers_count = 2;

            int i;
            starting_node.Add(0);
            starting_node.Add(0);
            //starting_node.Add(0);

            containers_volume.Add(3);
            containers_volume.Add(5);
            //containers_volume.Add(13);

            //for (i = 0; i < containers_count; i++)
            //{
            //    starting_node.Add(0);
            //    containers_volume.Add(Integer.parseInt((args[i + 2])));
            //}
            Dictionary<double, bool> check_dict = new Dictionary<double, bool>();

            search(starting_node, containers_volume, vol_wanted, check_dict, containers_count);

        }

        public static void search(List<int> starting_node, List<int> containers_volume, int vol_wanted, Dictionary<double, bool> check_dict, int containers_count)
        {
            List<List<int>> target = new List<List<int>>();
            List<List<int>> q = new List<List<int>>();
            List<List<int>> path = new List<List<int>>();
            List<List<int>> next_moves;
            bool accomplished = false;

            q.Add(starting_node);
                

            while (q.Count != 0)
            {
                //path_2 = q[0];
                List<int> path_2 = new List<int>();
                List<int> comp = new List<int>();
                List<List<int>> ee = new List<List<int>>();
                List<List<int>> eb = new List<List<int>>();

                for (int i = 0; i < containers_count; i++)
                    comp.Add(0);
                
                if (q[0] == comp)
                {
                    ee.Add(q[0]);
                    path = ee;
                }
                else {
                    path_2 = new List<int>(q[0]);
                    eb.Add(path_2);
                    path = new List<List<int>>(eb);
                }

                q.RemoveAt(0);

                List<int> last_path;
                int zef = 0;
                zef = ((int)path.Count) - 1;
                last_path = path[zef];
                double b = get_index(last_path, containers_count);
                check_dict[b] = true;
                // If target is reached, exit the loop
                if (is_vol_wanted(path, vol_wanted, containers_count))
                {
                    accomplished = true;
                    target = path;
                    break;
                }

                // If target no reached, getting the next move
                next_moves = next_step(containers_volume, path, check_dict, containers_count);
                q.AddRange(next_moves);
            }
            // If accomplished, print the path
            if (accomplished)
            {
                Console.WriteLine("Accomplished");
                print_path(target);
            }
            else
            {
                Console.WriteLine("no solution");
            }

        }

        public static bool been_there(List<int> node,Dictionary<double, bool> check_dict, int containers_count)
        // Check if a node had already been visited
        {
            double a = get_index(node, containers_count);
            return !check_dict.ContainsKey(a);
        }

        public static List<List<int>> next_step(List<int> containers_volume, List<List<int>> path, Dictionary<double, bool> check_dict, int containers_count)
        // Finding the next path with checking the ones already checked
        {
            List<List<int>> result = new List<List<int>>();
            List<List<int>> next_nodes = new List<List<int>>();
            List<int> last_path;
            List<int> max_containers_volume = new List<int>();
            List<int> containers_status = new List<int>();
            List<int> the_node = new List<int>();

            for (int i = 0; i < containers_count; i++)
                max_containers_volume.Add(containers_volume[i]);


            last_path = path[(path.Count - 1)];
            for (int i = 0; i < containers_count; i++)
                containers_status.Add(last_path[i]);


            // fill in the first tank.
            for (int j = 0; j < containers_count; j++)
            {
                for (int i = 0; i < containers_count; i++)
                    the_node.Add(containers_status[i]);
                the_node[j] = max_containers_volume[j];
                if (been_there(the_node, check_dict, containers_count))
                    next_nodes.Add(new List<int>(the_node));
                the_node.Clear();
            }

            for (int j = 0; j < containers_count; j++)
            {
                for (int i = 0; i < containers_count; i++)
                    the_node.Add(containers_status[i]);
                the_node[j] = 0;
                if (been_there(the_node, check_dict, containers_count))
                    next_nodes.Add(new List<int>(the_node));
                the_node.Clear();
            }

            // Transfer from tank to another
            for (int i = 0; i < containers_count; i++)
            {
                for (int j = 0; j < containers_count; j++)
                {
                    if (j != i)
                    {
                        the_node = new List<int>(containers_status);
                        the_node[i] = Math.Min((int) max_containers_volume[i], (int) the_node[i] + (int) the_node[j]);
                        the_node[j] = ((int)the_node[j] - ((int)the_node[i] - (int)containers_status[i]));
                        if (been_there(the_node, check_dict, containers_count))
                            next_nodes.Add(new List<int>(the_node));
                        the_node.Clear();
                    }
                }

            }

            // Create the list of possible next path
            if (next_nodes.Count != 0)
            {
                foreach (List<int> arrayList in next_nodes)
                {
                    List<List<int>> temp;
                    temp = new List<List<int>>(path);
                    temp.Add(arrayList);
                    foreach (List<int> addit in temp)
                    result.Add(addit);
                    temp.Clear();
                }
            }
            return result;

        }

        public static void write_transition(List<int> old, List<int> m_new)
        {
            List<Char> chars = new List<Char>();
            char[] alphabet = "ABCDEFGHIJKLMN".ToCharArray();
            foreach (char ch in alphabet)
                chars.Add(ch);
            ArrayList index_change = new ArrayList();
            int diff_count_numb = 0;
            for (int i = 0; i < old.Count; i++)
            {
                if (old[i] != m_new[i])
                {
                    diff_count_numb = diff_count_numb + 1;
                    index_change.Add(i);
                }
            }
            int te_0 = (int)index_change[0];
            if (diff_count_numb == 1)
            {
                if ((int)m_new[te_0] == 0)
                    Console.WriteLine(chars[te_0] + "->*");
                else Console.WriteLine("*->" + chars[te_0]);

            }
            else
            {
                int te_1 = (int)index_change[1];
                if ((int) m_new[te_0] > (int) old[te_0])
                    Console.WriteLine(chars[te_1] + "->" + chars[te_0]);
                else Console.WriteLine(chars[te_0] + "->" + chars[te_1]);
            }
        }


        public static void print_path(List<List<int>> path)
        {
            Console.WriteLine(path.Sum(x => x.Count));

            Console.WriteLine("Solution in " + (path.Count - 1) + " steps");

            for (int i = 0; i < path.Count - 1; i++)
            {
                write_transition(path[i], path[i + 1]);
                String step;
                step = path[(i + 1)].ToString().Replace("\\[", " \\(").Replace("]", "\\)");
               Console.WriteLine(step);
            }
        }

        public static bool is_vol_wanted(List<List<int>> path, int vol_wanted, int containers_count)
        {
            //Checking the last node and return True if we reached the targeted value

            int temp4;
            List<int> last_path;
            last_path = path[(path.Count - 1)];
            for (int i = 0; i < containers_count; i++)
            {
                temp4 = (int) last_path[i];
                if (temp4 == vol_wanted) return true;
            }
            return false;

        }

        public static double get_index(List<int> node, int containers_count)
        {
            // generating a random number for the key index using pow to be sure there's no duplicate
            double result_ind = 1;
            for (int i = 0; i < containers_count; i++)
            {
                result_ind = result_ind * Math.Pow(3 + i, node[i]);
            }
            return result_ind;
        }

    }
}
