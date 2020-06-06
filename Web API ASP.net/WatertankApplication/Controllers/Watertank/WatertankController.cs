using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using WebApplication1.Models;

namespace WebApplication1.Controllers.Watertank
{
    public class WatertankController : Controller
    {
        // GET: Watertank
        public ActionResult Index()
        {
            WaterModels client = new WaterModels();
            return View(client);
        }

        [AcceptVerbs(HttpVerbs.Post)]
        public ActionResult Index(WaterModels visiteur)
        {
            WaterModels client = new WaterModels();

            string vol_1 = "";
            string vol_2 = "";
            string vol_target = "";

            vol_1 = Request.Form["vol_1"];
            vol_2 = Request.Form["vol_2"];
            vol_target = Request.Form["vol_target"];
            client.water1 = vol_1;
            client.water2 = vol_2;
            client.water_wanted = vol_target;

            ViewData["message"] =  GetMyResult(int.Parse(vol_1), int.Parse(vol_2), int.Parse(vol_target));
            return View("Index", client);

        }

        private String GetMyResult(int var1, int var2, int var3)
        {

            List<int> containers_volume = new List<int>();
            List<List<String>> steps = new List<List<String>> ();
            containers_volume.Add(var1);
            containers_volume.Add(var2);
            int vol_wanted = var3;
            string resultat = String.Join("< br />", count(containers_volume, vol_wanted, steps));
            Console.WriteLine(resultat);
            return String.Join(" || ", count(containers_volume, vol_wanted, steps));


        }

        public static int gcd(int a, int b)
        {
            if (b == 0)
            {
                return a;
            }
            else
            {
                return gcd(b, a % b);
            }
        }

        public static int transfer(List<int> cont_vol, int x, List<List<String>> steps)
        {
            //initializing max volumes
            int to_cont_max = (int)cont_vol[0];
            int from_cont_max = (int)cont_vol[1];

            // filling the first Tank
            int from_cont = from_cont_max;
            int to_cont = 0;
            int count = 1;
            List<String> steps_temp = new List<String>();
            steps_temp.Add("*->A : (" + from_cont + "," + to_cont + ")");


            while ((from_cont != x) && (to_cont != x))
            {
                // Get the maximum water capacity to be transfered
                int temp = Math.Min(from_cont, to_cont_max - to_cont);

                // We can transfer from a tank to the other one and add the step
                to_cont += temp;
                from_cont -= temp;
                count += 1;
                steps_temp.Add("A->B : (" + from_cont + "," + to_cont + ")");

                // If one of the tank reach the target value, break the loop
                if ((from_cont == x) || (to_cont == x))
                {
                    break;
                }

                // If the first tank (filler) is empty, fill it
                else if (from_cont == 0)
                {
                    from_cont = from_cont_max;
                    count += 1;
                    steps_temp.Add("*->A : (" + from_cont + "," + to_cont + ")");

                }

                // If the second tank (receiver) is full, empty it
                else if (to_cont == to_cont_max)
                {
                    to_cont = 0;
                    count += 1;
                    steps_temp.Add("B->* : (" + from_cont + "," + to_cont + ")");

                }
            }
            steps.Add(steps_temp);
            return count;
        }

        public static List<String> count(List<int> cont_vol, int vol_wanted, List<List<String>> steps)
        {
            List<String> result = new List<String>();
            // If water required is superior of the biggest tank, there's no solution
            // With the Bezout Lemma we can check if there's a solution such as + by = z
            // Lemma : if x and y are nonzero integers and g = gcd(x,y),
            // then there exist integers a and b such that ax+by=g.
            if (vol_wanted > Math.Max((int)cont_vol[0], (int)cont_vol[1]) || (vol_wanted % (gcd((int)cont_vol[0], (int)cont_vol[1])) != 0))
            {
                result.Add("No solution");
                return result;
            }

            // With 2 tanks, there is two path to reach the solution
            // We try both solution by filling the bigger tank and then the smaller one
            // And then take the one with the smallest count of steps.
            int sol_1 = transfer(cont_vol, vol_wanted, steps);
            cont_vol.Reverse();
            int sol_2 = transfer(cont_vol, vol_wanted, steps);

            result.Add("Solution in " + Math.Min(sol_1, sol_2) + " steps");

            if (sol_1 < sol_2)
            {
                foreach(String st in steps[1])
                {
                    result.Add(st);
                }

                

            }
            else
            {
                foreach (String st in steps[0])
                {
                    result.Add(st);
                }


            }

            return result;



        }

    }


}