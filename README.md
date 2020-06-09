<h1 align="center">Watertank</h1>

<div align="center">
  :bathtub::bathtub::bathtub:
</div>
<div align="center">
  <strong>Solving in Python and Java</strong>
</div>
<br />

==> Next Improvement: Using another algorithm to solve the enigma

## Java 
###  Files in src folder   
- __Watertank.java__ \
Brute force method with watertank.java : just filling one tank and looping one of the three steps available until we got the solution.\
We do the same with the second tank and we take the minimum of the two path which is the lowest steps possible.\
To start watertank.java :
```
Watertank\src> java Watertank.java args1 args2 args3
```
where :\
`args1` : Tank volume 1 \
`args2` : Tank volume 2\
`args3` : Volume desired

- __Watertank_a_bfs.java__ \
Dynamic programming method with watertank_a_bfs.py : we are trying to recreate all the nodes available and we search all the neighborhood.\
We are trying to get the lowest path to the goal.

To start watertank_a_bfs.java :
```
Watertank\src> java Watertank_a_bfs.java args1 args2 args3
```
where :\
`args1` : Tank volume 1 \
`args2` : Tank volume 2\
`args3` : Volume desired

- __Watertank_n_tank.java__ \
Java version of the BFS algorithm for N-watertank !
![Awesome](https://cdn.rawgit.com/sindresorhus/awesome/d7305f38d29fed78fa85652e3a63e154dd8e8829/media/badge.svg) 

To start Watertank_n_tank.java :
```
Watertank\src> java Watertank_n_tank args1 args2 args3 args4 ....
```
where :\
`args1` : Volume desired \
`args2` : Numbers of Tank \
`args3 to N` : Tank volume 3 to N 

## Python
###  Files in Python folder   
- __watertank.py__ \
Python version of the brute force algorithm for watertank

To start watertank.py :
```
> python watertank.py args1 args2 args3
```
where :\
`args1` : Tank volume 1 \
`args2` : Tank volume 2\
`args3` : Volume desired


- __watertank_a_bfs.py__ \
Python version of the BFS algorithm for watertank

To start watertank_a_bfs.py :
```
> python watertank_a_bfs.py args1 args2 args3
```
where :\
`args1` : Tank volume 1 \
`args2` : Tank volume 2\
`args3` : Volume desired

- __watertank_n_tank.py__ \
Python version of the BFS algorithm for N-watertank !
![Awesome](https://cdn.rawgit.com/sindresorhus/awesome/d7305f38d29fed78fa85652e3a63e154dd8e8829/media/badge.svg) 

To start watertank_n_tank.py :
```
> python watertank_n_tank.py args1 args2 args3 args4 ....
```
where :\
`args1` : Volume desired \
`args2` : Numbers of Tank \
`args3 to N` : Tank volume 3 to N 

## Web API ASP.Net
###  MVC files only
- Views:  Classic view might be improved with some images or arts representing Tanks level.
- Controller: Working but might be improved with a better algorithm (such as the BFS one). 
- Models: N/A 

