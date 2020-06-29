using System;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.IO;
using System.Collections;
using System.Collections.Generic;

// not my solution but comes from Quentin

namespace ConsoleApplication1
{
    class Watertank_try_n
    {

        public static int vol_wanted;
        public static int containers_count;
        public static int[] containers_volume;

        public static void Main(String[] args)
        {
            vol_wanted = 9;
            containers_count = 3;
            containers_volume = new int[containers_count];
            //for (int i = 0; i < containers_count; i++)
            containers_volume[0] = 7;
            containers_volume[1] = 8;
            containers_volume[2] = 11;
            var x = Find().ToString();
            Console.WriteLine("Solution in " + x + " steps");
        }


        public static int GetHashCode(int[] array)
        {
            return ((IStructuralEquatable)array).GetHashCode(EqualityComparer<int>.Default);
        }

        public static int[] Do(List<int[]> S, HashSet<int> H, int[] p, Action<int[]> action)
        {
            var s = p.Clone() as int[];
            action(s);
            var h = GetHashCode(s);
            if (!H.Contains(h))
            {
                H.Add(h);
                S.Add(s);
            }
            return s;
        }

        public static int Find()
        {
            if (vol_wanted == 0)
                return 0;
            var P = new List<int[]> { new int[containers_count] };
            int count = 0;
            while (++count > 0)
            {
                var S = new List<int[]>();
                var H = new HashSet<int>();
                foreach (var p in P)
                {
                    for (int i = 0; i < containers_count; i++)
                    {
                        if (p[i] > 0 && p.Sum() > p[i])
                        {
                            Do(S, H, p, o => o[i] = 0);
                        }
                        if (p[i] < containers_volume[i])
                        {
                            var s = Do(S, H, p, o => o[i] = containers_volume[i]);
                            if (s[i] == vol_wanted)
                                return count;
                        }
                        for (int j = 0; j < containers_count; j++)
                        {
                            if (i == j)
                                continue;
                            if (p[j] < containers_volume[j])
                            {
                                var s = Do(S, H, p, o => {
                                    o[j] = Math.Min(p[j] + p[i], containers_volume[j]);
                                    o[i] = p[i] - o[j] + p[j];
                                });
                                if (s[i] == vol_wanted || s[j] == vol_wanted)
                                    return count;
                            }
                        }
                    }
                }
                P = S;
            }
            return 0;
        }


    }

}

