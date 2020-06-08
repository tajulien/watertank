import collections
import sys

# Getting parameters
vol_wanted = 4
containers_count = 4
containers_volume = [5, 7, 8, 6]
check_dict = {}
starting_node = [[]]
for i in range(0, containers_count):
    starting_node[0].append(0)


# # GCD function needed to check if there is a solution
# def gcd(a, b):
#     if b == 0:
#         return a
#     return gcd(b, a % b)


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

        # print other path
        # if len(path) >= 2:
        #    print(transition(path[-2], path[-1], containers_volume), path[-1])

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
    node = []
    max_containers_vol = []
    containers_status = []
    the_node =[]

    for i in range(0, containers_count):
        max_containers_vol[i] = containers_volume[i]

    for i in range(0, containers_count):
        containers_status[i] = path[-1][i]

    # Filling tanks one by one and add the path
    for j in range (0, containers_count):
        for i in range(0, containers_count):
            the_node.append(containers_status[i])
        the_node[j] = max_containers_vol[j]
        if not been_there(node, check_dict):
            next_nodes.append(node)
        the_node = []

    # Empty tank one by one and add the path
    for j in range(0, containers_count):
        for i in range(0, containers_count):
            the_node.append(containers_status[i])
        the_node[j] = 0
        if not been_there(node, check_dict):
            next_nodes.append(node)
        the_node = []

    # TODO Transfer n tank to n other tank (B => A)
    # node.append(min(max_first_cont, first_cont + second_cont))
    # node.append(second_cont - (node[0] - first_cont))
    # if not been_there(node, check_dict):
    #     next_nodes.append(node)
    # node = []
    #
    # ## TODO  reverse transfer (A => B)
    # node.append(min(first_cont + second_cont, max_second_cont))
    # node.insert(0, first_cont - (node[0] - second_cont))
    # if not been_there(node, check_dict):
    #     next_nodes.append(node)
    # node = []

    # Create the list of possible next path
    for i in range(0, len(next_nodes)):
        temp = list(path)
        temp.append(next_nodes[i])
        result.append(temp)

    return result


def write_transition(old, new):
    first_cont = old[0]
    second_cont = old[1]
    new_first_cont = new[0]
    new_second_cont = new[1]

    if first_cont > new_first_cont:
        if second_cont == new_second_cont:
            return f'A->* :'
        else:
            return f'A->B :'
    else:
        if second_cont > new_second_cont:
            if first_cont == new_first_cont:
                return f'B->* :'
            else:
                return f'B->A :'
        else:
            if first_cont == new_first_cont:
                return f'*->B :'
            else:
                return f'*->A :'


def print_path(path):
    print(f'Solution in {len(path) - 1} steps')
    for i in range(0, len(path) - 1):
        # print(i + 1, ":", transition(path[i], path[i + 1], containers_volume), path[i + 1])
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

# print(is_vol_wanted([[1,0,0,4]],4))
# search(starting_node, containers_volume, vol_wanted, check_dict)
def test():
    the_node = []
    max_containers_vol = [5,7,9,4]
    j=0
    containers_status = [5, 7, 9, 4]
    next_node = []
    for j in range (0, containers_count):
        for i in range(0, containers_count):
            the_node.append(containers_status[i])
        print("the node",the_node,"now j",j)
        print("themaxcontj",max_containers_vol[j])
        print("thenodedej",the_node[j])
        the_node[j] = 0
        print("the node apres j",the_node)
        next_node.append(the_node)
        the_node=[]
    print(next_node)

test()