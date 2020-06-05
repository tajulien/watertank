# Watertank

Brute force method with watertank.py : just filling one tank and looping one of the three steps available until we got the solution.\
We do the same with the second tank and we take the minimum of the two path which is the lowest steps possible

To start watertank.py :
> python watertank.py args1 args2 args3

where :\
args1 : Tank volume 1 \
args 2 : Tank volume 2\
args 3 : Volume desired


Dynamic programming method with watertank_a_bfs.py : we are trying to recreate all the nodes available and we search all the neighborhood.\
We are trying to get the lowest path to the goal.

To start watertank_a_bfs.py :
> python watertank_a_bfs.py args1 args2 args3

where :\
args1 : Tank volume 1 \
args 2 : Tank volume 2\
args 3 : Volume desired
