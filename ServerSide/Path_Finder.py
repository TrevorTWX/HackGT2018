from __future__ import division

import heapq
import os
import pickle
import csv

E = 0
V = 0
vertices = []

class PriorityQueue(object):
    """
    A queue structure where each element is served in order of priority.
    Elements in the queue are popped based on the priority with higher priority
    elements being served before lower priority elements.  If two elements have
    the same priority, they will be served in the order they were added to the
    queue.
    Traditionally priority queues are implemented with heaps, but there are any
    number of implementation options.
    (Hint: take a look at the module heapq)
    Attributes:
        queue (list): Nodes added to the priority queue.
        current (int): The index of the current node in the queue.
    """

    def __init__(self):
        """Initialize a new Priority Queue."""

        self.queue = []

    def pop(self):
        """
        Pop top priority node from queue.
        Returns:
            The node with the highest priority.
        """

        heap_queue = self.queue
        heapq.heapify(heap_queue)
        popped = heapq.heappop(heap_queue)
        self.queue = heap_queue
        return popped

        # TODO: finish this function!
        # raise NotImplementedError

    def remove(self, node_id):
        return self.queue.pop(node_id)

        # raise NotImplementedError

    def __iter__(self):
        """Queue iterator."""

        return iter(sorted(self.queue))

    def __str__(self):
        """Priority Queue to string."""

        return 'PQ:%s' % self.queue

    def append(self, node):
        """
        Append a node to the queue.
        Args:
            node: Comparable Object to be added to the priority queue.
        """

        self.queue.append(node)

        # TODO: finish this function!
        # raise NotImplementedError

    def __contains__(self, key):
        """
        Containment Check operator for 'in'
        Args:
            key: The key to check for in the queue.
        Returns:
            True if key is found in queue, False otherwise.
        """

        return key in [n for _, n in self.queue]

    def __eq__(self, other):
        """
        Compare this Priority Queue with another Priority Queue.
        Args:
            other (PriorityQueue): Priority Queue to compare against.
        Returns:
            True if the two priority queues are equivalent.
        """

        return self == other

    def size(self):
        """
        Get the current size of the queue.
        Returns:
            Integer of number of items in queue.
        """

        return len(self.queue)

    def clear(self):
        """Reset queue to empty (no nodes)."""

        self.queue = []

    def top(self):
        """
        Get the top item in the queue.
        Returns:
            The first item stored in teh queue.
        """

        return self.queue[0]

# get the neighbors of vertex v
def get_neighbors(edges, v):
    global V
    global vertices
    neighbor_list = edges[vertices.index(v)]
    return_list = []
    for neighbor in neighbor_list:
        # print neighbor
        return_list.append(neighbor[0])
    # print 'asked neighors for ',v, ' and I found ',return_list
    return return_list

def get_distance(edges, s, e):
    global V
    neighbor_list = edges[vertices.index(s)]
    # # print 'neighbor_list is ',neighbor_list
    for neighbor in neighbor_list:
        if neighbor[0] == e:
            # # print 'we find'
            return neighbor[1]

def ucs_search(edges, start, goal):

    if start == goal:
        return []

    visited = [] # track the nodes have been visited
    pending = PriorityQueue() # in the frontier
    parent = [] # parent-ship [parent, child]
    # Let's start
    pending.append((0,start))

    while pending.size() > 0:
        # pop the priority node and add to visited
        current_node_full = pending.pop()
        current_node = current_node_full[1]
        current_weight = current_node_full[0]
        visited.append(current_node)
        # check if we are done
        if current_node == goal:
            result_path = []
            traversing_node = goal
            while traversing_node != start:
                result_path.append(traversing_node)
                for i in range(0,len(parent)):
                    if parent[i][1] == traversing_node:
                        traversing_node = parent[i][0]
                        break
            result_path.append(start)
            # # # print 'ucs: ',graph.explored_nodes()
            return list(reversed(result_path))
        # if not done, keep searching ...
        # add all its unvisited neighbors to pending
        for neighbor_node in get_neighbors(edges, current_node):
            # check if it is visited
            if neighbor_node not in visited:
                # check if already had a weight:
                if neighbor_node in pending:
                    # check whether or not we need to update the distance
                    new_distance = get_distance(edges, current_node, neighbor_node) + current_weight
                    in_pending_index = [keys[1] for keys in pending.queue].index(neighbor_node)
                    in_pending = pending.remove(in_pending_index)
                    old_distance = in_pending[0]
                    if new_distance < old_distance:
                        pending.append((new_distance, neighbor_node))
                        # update parent
                        for pair in parent:
                            if pair[1] == neighbor_node:
                                parent[parent.index(pair)] = [current_node,neighbor_node]
                    else:
                        pending.append((old_distance, neighbor_node))
                else:
                # add to pending with initial distance
                    # # print 'current node is ',current_node, '    neighbor_node is ', neighbor_node
                    # # print get_distance(edges, current_node, neighbor_node)
                    pending.append((get_distance(edges, current_node, neighbor_node)+current_weight, neighbor_node))
                    parent.append([current_node,neighbor_node])

# get the path from start_point to end_point using edgelist
def get_path(edgelist, start_point, end_point):
    # print 'I got a search for: ',start_point,' to ',end_point
    global V
    global E
    global vertices
    for i in range(0,len(edgelist)):
        for j in range(0,3):
            # print 'orginal is ', edgelist[i][j]
            edgelist[i][j] = int(edgelist[i][j])
            # print 'and I store it as ', edgelist[i][j]
    V = 0   # store the number of vertices
    len_e = len(edgelist)
    E = len_e*2   # store the number of edges
    vertices = []   # list of vertices
    # find all the vertices
    for i in range(0,len_e):
        edge = edgelist[i]
        if edge[0] not in vertices:
            vertices.append(edge[0])
            V += 1
        if edge[1] not in vertices:
            vertices.append(edge[1])
            V += 1
        edgelist.append([edge[1], edge[0], edge[2]])    # also append the 'other direction' to the edgelist

    # adjecent list for edges
    edges = []
    for i in range(0,V):
        edges.append([])
    for edge in edgelist:
        # print 'edge is ', edge
        # print 'appended ',edge[1],' ',edge[2]
        edges[vertices.index(edge[0])].append((edge[1],edge[2]))

    return ucs_search(edges, start_point, end_point)




    # # sort the edgelist
    # edgelist = sorted(edgelist, key=lambda element: (element[0], element[1]))
    # for row in edgelist:
    #     # print row
    #
    # off_set = [0]*(V+1)    # off_set list
    # adjecent_v = [] # adjecent vertices
    # distance = []   # distance for each edge
    # neighbor_count = [0]*V # temperary list for storing how many neighbors a vertex has
    # # fill the neighbor_count list and the adjecnet_v and distance
    # for edge in edgelist:
    #     neighbor_count[edge[0]] += 1
    #     adjecent_v.append(edge[1])
    #     distance.append(edge[2])
    # # fill the off_set:
    # off_set[0] = neighbor_count[0]-1
    # for i in range(1,len(neighbor_count)):
    #     off_set[i] = neighbor_count[i] + off_set[i-1]
    # off_set[V] = off_set[V-1] + neighbor_count
