/**
 * Build Order: You are given a list of projects and a list of dependencies (which is a list of pairs of projects, where the second project is dependent on the first project). 
 * All of a project’s dependencies must be built before the project is. Find a build order that will allow the projects to be built. 
 * If there is no valid build order, return an error.
 * 
 * Input
 * projects:      a, b, c, d, e, f
 * dependencies: (a, d), (f, b), (b, d), (f, a), (d, c) 
 * Output:        f, e, a, b, d, c
 * 
 */



import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Node {
  private char project;
  private boolean referenced; 
  private List<Node> adjacents = new ArrayList<>();
  private boolean marked;     
}

public class Dependency {
  private char from;
  private char to;
}

public char[] buildOrder(char[] projects, Dependency[] dependencies) {
  List<Node> nodes = buildNodes(projects);
  buildGraph(nodes, dependencies);
  List<Node> roots = findRoots(nodes);
  if (roots.isEmpty()) {
    throw new IllegalException("circular dependencies");
  }
  return bfsTraverse(roots, projects.length);
}

// O(n)
private List<Node> buildNodes(char[] projects) {
  List<Node> res = new ArrayList<>();
  for (char p : projects) {
    res.add(new Node(p));
  }
  return res;
}

// O(n)
private void buildGraph(List<Node> nodes, Dependency[] dependencies) {
  Map<Char, Node> nodeMap = buildNodeMap(nodes);
  for (Dependency dep : dependencies) {
    Node from = nodeMap.get(dep.from);
    Node to = nodeMap.get(dep.to);
    to.referenced = true;
    from.adjacents.add(to);
  }
}

// O(n)
private List<Node> findRoots(List<Node> nodes) {
  List<Node> res = new ArrayList<>();
  for (Node n : nodes) {
    if (!n.referenced) {
      res.add(n);
    }
  }
  return res;
}


private char[] bfsTraverse(List<Node> nodes, int total) {
  List<Node> current = nodes;  
  char[] res = new char[total];
  int index = 0;
  while (!current.isEmpty()) {
    List<Node> next = new ArrayList<>();
    for (Node n : current) {
      res[index++] = n.project;
      if (n.marked) {
        throw new IllegalException("circular dependency: " + res);
      }
      n.marked = true;
      for (Node a : node.adjacents) {
        next.add(a);
      }
    }
    current = next;
  }
  return res;
}