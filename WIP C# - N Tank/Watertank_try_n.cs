using System;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.IO;
using System.Collections;
using System.Collections.Generic;

// tweaking explicit sol from Quentin, trying to build the path

namespace ConsoleApplication1
{
    class Watertank_try_n
    {

        public static int vol_wanted;
        public static int containers_count;
        public static int[] containers_volume;

        public static void Main(String[] args)
        {
            vol_wanted = 4;
            containers_count = 2;
            containers_volume = new int[containers_count];
            //for (int i = 0; i < containers_count; i++)
            containers_volume[0] = 5;
            containers_volume[1] = 3;
            // containers_volume[2] = 3;
            var x = Find().ToString();
            if (x=="0")
            {
                Console.WriteLine("No solution");
            }
            else
            {
                Console.WriteLine("Solution in " + x + " steps");
             }
        }


        public static int GetHashCode(int[] array)
        {
            return ((IStructuralEquatable)array).GetHashCode(EqualityComparer<int>.Default);
        }

        public static int[] CheckExist(List<int[]> big_List, HashSet<int> Hashindex, int[] p, Action<int[]> action)
        {
            var s = p.Clone() as int[];
            action(s);
            var hashed = GetHashCode(s);
            if (!Hashindex.Contains(hashed))
            {
                Hashindex.Add(hashed);
                big_List.Add(s);
            }
            //Console.WriteLine(Hashindex);
            return s;
        }

        public static int Find()
        {
            if (vol_wanted == 0)
                return 0;
            var step_list = new List<int[]> { new int[containers_count] };
            int count = 0;
            while (++count > 0)
            {
                var big_List = new List<int[]>();
                var Hashindex = new HashSet<int>();
                foreach (var sub_list in step_list)
                {
                    for (int i = 0; i < containers_count; i++)
                    {
                        if (sub_list[i] > 0 && sub_list.Sum() > sub_list[i])
                        {
                            CheckExist(big_List, Hashindex, sub_list, o => o[i] = 0);
                        }
                        if (sub_list[i] < containers_volume[i])
                        {
                            var path = CheckExist(big_List, Hashindex, sub_list, o => o[i] = containers_volume[i]);
                            Console.WriteLine(path[i]);
                            if (path[i] == vol_wanted)
                                return count;
                        }
                        for (int j = 0; j < containers_count; j++)
                        {
                            if (i == j)
                                continue;
                            if (sub_list[j] < containers_volume[j])
                            {
                                
                                var path = CheckExist(big_List, Hashindex, sub_list, o => {
                                    o[j] = Math.Min(sub_list[j] + sub_list[i], containers_volume[j]);
                                    o[i] = sub_list[i] - o[j] + sub_list[j];
                                });
                                Console.WriteLine(path[i] + " "+ path[j]);
                                if (path[i] == vol_wanted || path[j] == vol_wanted)
                                    return count;
                            }
                        }
                    }
                }
                step_list = big_List;
            }
            return 0;
        }


    }

}

