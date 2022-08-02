# CS205 Artificial Intelligence - PuzzleSolver
The Eight Puzzle


# Introduction
This project is for the Introduction to AI course, part of the project assigned by professor Dr.
Eamonn Keogh during the Spring 2022 quarter at University of California Riverside.
The 8 puzzle is a 3 * 3 grid filled with numbers from 0 to 8. A real-world scenario would have zero
represent an empty square. A unique number would go in each square. To solve this puzzle,
one needs to order the numbers in such a way that they ascend from left to right and left to
right, i.e. successively in ascending order in a matrix leaving a last space with 0(or empty).
In this project, three algorithms are used to solve this puzzle. Algorithms implemented in the
submission include Uniform Cost Search, A* using Misplaced Tiles Heuristic, and A* using
Manhattan Distance Heuristic.
# Comparison of Algorithms
# Uniform Cost Search
Uniform cost search is implemented by choosing the path with the lowest cost for g(n). Every
time an iteration is performed, a new state is reached in order to reach the goal state. Since no
heuristics are used, h(n) is set to 0 and only g(n) is taken into account in calculating the overall
cost. Each movement of 0 to its neighboring position in the puzzle costs 1. The cost is increased
by 1 each time 0 is moved in any direction. In this algorithm, all the expanded nodes of the
same level are considered for expanded nodes count.
# A* Misplaced tiles heuristic
An informed generic search algorithm A* Misplaced tiles heuristic is implemented by comparing
all the misplaced numbers in the puzzle with the final state. In each iteration, the value of h(n) is
considered along with g(n) to calculate the estimated cost of f(n). Here, the position of 0 (empty
space) is not considered while calculating the h. By choosing the lower estimated cost node f(n),
the priority queue will poll the next nodes until the final state is reached.
# A* Manhattan distance heuristic
Manhattan distance heuristics are informed generic search algorithms that calculate the cost of
misplaced numbers in a puzzle by calculating their distance from the final goal. Taking the h(n)
cost and g cost together, we can calculate the estimated cost f. As a result, nodes are expanded
by finding the lowest f(n) cost. Each node is checked to see if it has reached its final state.
# Performance Analysis
The above graph is plot based on the implementation of all 3 algorithms. Here, if we consider
uniform cost search, The total number of nodes expanded goes exponentially after depth 12.
Large number of nodes are expanded and try to find the goal state when the depth increases.
An A* algorithms perform much lesser nodes expansion when compared to uniform cost search.
The Manhattan distance algorithm expands very less nodes compared to other 2 algorithms
which helps in finding the goal state at a much faster pace.
Above is a graph illustrating how much time each algorithm took. In this case, we can clearly
see that all 3 algorithms take less time for simpler and smaller depth puzzles. When the depth
gets increased, however, the uniform cost algorithm takes a much longer time. Misplaced tiles
are, however, much faster than uniform cost algorithms. The Manhattan distance algorithm
performs the search more quickly than the other two algorithms.
# Conclusion
By observing the outputs, performance and time taken by all the algorithms, we can clearly see
how A* heuristics outperforms the uniform cost search algorithm. The Manhattan distance
heuristic essentially performs better than the uniform cost search algorithm and Misplaced tiles
algorithm. We can conclude that the A* algorithms are faster and expands lesser nodes to
perform the search operation efficiently.

# Sample Execution
<img width="684" alt="image" src="https://user-images.githubusercontent.com/97561730/182487406-b2c4ce92-5716-4e67-87d7-f2ed067214fd.png">

4 5 6
