import sys

# Getting parameters
vol_wanted = int(sys.argv[3])
containers_count = 2
containers_volume = [int(sys.argv[1]), int(sys.argv[2])]
steps = [[], []] # Steps list is going to store the path to the solution

# GCD function needed to check if there is a solution
def gcd(a, b):
    if b == 0:
        return a
    return gcd(b, a % b)


def transfer(cont_vol, x, n):
    # Initializing max volumes
    to_cont_max, from_cont_max = cont_vol[0], cont_vol[1]

    # Filling the first Tank
    from_cont = from_cont_max
    to_cont = 0
    count = 1
    steps[n].append(f'*->A : ({from_cont},{to_cont})')

    while (from_cont is not x) and (to_cont is not x):

        # Get the maximum water capacity to be transfered
        temp = min(from_cont, to_cont_max - to_cont)

        # We can transfer from a tank to the other one and add the step
        to_cont += temp
        from_cont -= temp
        count += 1
        steps[n].append(f'A->B : ({from_cont},{to_cont})')

        # If of the tank reach the target value, break the loop
        if ((from_cont == x) or (to_cont == x)):
            break

        # If the first tank (filler) is empty, fill it
        if from_cont == 0:
            from_cont = from_cont_max
            count += 1
            steps[n].append(f'*->A : ({from_cont},{to_cont})')

        # If the second tank (receiver) is full, empty it
        if to_cont == to_cont_max:
            to_cont = 0
            count += 1
            steps[n].append(f'B->* : ({from_cont},{to_cont})')

    return count


def count(cont_vol, vol_wanted):
    # If water required is superior of the biggest tank, there's no solution
    if vol_wanted > max(cont_vol[0], cont_vol[1]):
        return ("No solution")

    # With the Bezout Lemma we can check if there's a solution such as + by = z
    # Lemma : if x and y are nonzero integers and g = gcd(x,y),
    # then there exist integers a and b such that ax+by=g.

    elif (vol_wanted % (gcd(cont_vol[0], cont_vol[1])) is not 0):
        return "No solutions"

    # With 2 tanks, there is two path to reach the solution
    # We try both solution by filling the bigger tank and then the smaller one
    # And then take the one with the smallest count of steps.
    cont_vol_rev = cont_vol[::-1]
    sol_1 = transfer(cont_vol, vol_wanted, 0)
    sol_2 = transfer(cont_vol_rev, vol_wanted, 1)
    print("Solution in", min(sol_1,sol_2), "steps")
    
    # Print the result and the different steps
    # Ugly method to compare and print the solutions
    #print("\n".join([elt for elt in steps[0] if (len(steps[0]) < len(steps[1])) ]))
    if sol_1 < sol_2:
        for elt in steps[0]:
            print(elt)
    else:
        for elt in steps[1]:
            print(elt)

count(containers_volume, vol_wanted)





