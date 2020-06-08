import collections
import sys

# Getting parameters
vol_wanted = int(sys.argv[1])
containers_count = int(sys.argv[2])
containers_volume = []
for i in range(0, containers_count):
    containers_volume.append(int(sys.argv[i+3]))
check_dict = {}
starting_node = [[]]
for i in range(0, containers_count):
    starting_node[0].append(0)


# def check_args(cont_vol, vol_wanted):
#     """
#         If water required is superior of the biggest tank, there's no solution
#         With the Bezout Lemma we can check if there's a solution such as + by = z
#         Lemma : if x and y are nonzero integers and g = gcd(x,y),
#         then there exist integers a and b such that ax+by=g.
#     """
#     if vol_wanted > max(cont_vol[0], cont_vol[1]) or (vol_wanted % (gcd(cont_vol[0], cont_vol[1])) is not 0):
#         print("No solution")
#         return False
#     return True


def search(starting_node, containers_volume, vol_wanted, check_dict):
    # Starting state
    target = []
    accomplished = False

    # Using collections and deque to have a list with improved method appendleft, popleft etc..
    q = collections.deque()
    q.appendleft(starting_node)

    # if check_args(containers_volume, vol_wanted):
    while len(q) != 0:
        path = q.popleft()
        check_dict[get_index(path[-1])] = True

        # If target is reached, exit the loop
        if is_vol_wanted(path, vol_wanted):
            accomplished = True
            target = path
            break

        # If target no reached, getting the next move
        next_moves = next_step(containers_volume, path, check_dict)
        for i in next_moves:
            q.append(i)

    # If accomplished, print the path
    if accomplished:
        print_path(target)
    else:
        print("No Solution")
        return


def been_there(node, check_dict):
    # Check if a node had already been visited

    return check_dict.get(get_index(node), False)


def next_step(containers_volume, path, check_dict):
    # Finding the next path with checking the ones already checked

    result = []
    next_nodes = []
    max_containers_vol = []
    containers_status = []
    the_node =[]

    for i in range(0, containers_count):
        max_containers_vol.append(containers_volume[i])

    for i in range(0, containers_count):
        containers_status.append(path[-1][i])

    # Filling tanks one by one and add the path
    for j in range(0, containers_count):
        for i in range(0, containers_count):
            the_node.append(containers_status[i])
        the_node[j] = max_containers_vol[j]
        if not been_there(the_node, check_dict):
            next_nodes.append(the_node)
        the_node = []

    # Empty tank one by one and add the path
    for j in range(0, containers_count):
        for i in range(0, containers_count):
            the_node.append(containers_status[i])
        the_node[j] = 0
        if not been_there(the_node, check_dict):
            next_nodes.append(the_node)
        the_node = []

    for i in range(0, containers_count):
        for j in range(0, containers_count):
            if j != i:
                new_node = containers_status[:]
                new_node[i] = (min(max_containers_vol[i], new_node[i] + new_node[j]))
                new_node[j] = (new_node[j] - (new_node[i] - containers_status[i]))
                if not been_there(new_node, check_dict):
                    next_nodes.append(new_node)

    # Create the list of possible next path
    for i in range(0, len(next_nodes)):
        temp = list(path)
        temp.append(next_nodes[i])
        result.append(temp)

    return result


def write_transition(old, new):
    alphabet = ["A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N"]
    diff_count = []
    for index, (first, second) in enumerate(zip(old, new)):
        if first != second:
            diff_count.append(index)
    if len(diff_count) == 1:
        if new[diff_count[0]] == 0:
            return f'{alphabet[diff_count[0]]}->*'
        else:
            return f'*->{alphabet[diff_count[0]]}'
    else:
        if new[diff_count[0]] > old[diff_count[0]]:
            return f'{alphabet[diff_count[1]]}->{alphabet[diff_count[0]]}'
        else:
            return f'{alphabet[diff_count[0]]}->{alphabet[diff_count[1]]}'


def print_path(path):
    print(f'Solution in {len(path) - 1} steps')
    for i in range(0, len(path) - 1):
        print(write_transition(path[i], path[i + 1]), tuple(path[i + 1]))


def is_vol_wanted(path, vol_wanted):
    # Checking the last node and return True if we reached the targeted value
    for i in range(0, containers_count):
        if path[-1][i] == vol_wanted:
            return True
    return False


def get_index(node):
    # generating a random number for the key index using " ** " to be sure there's no duplicate
    result_ind = 1
    for i in range(0, containers_count):
        result_ind *= (3+i) ** node[i]
    return result_ind


(search(starting_node,containers_volume,vol_wanted,check_dict))