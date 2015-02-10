import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import org.jgrapht.WeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

public class Graph {

	private static WeightedGraph<String, DefaultWeightedEdge> g = new SimpleWeightedGraph<String, DefaultWeightedEdge>(
			DefaultWeightedEdge.class);
	private static WeightedGraph<String, DefaultWeightedEdge> mst = new SimpleWeightedGraph<String, DefaultWeightedEdge>(
			DefaultWeightedEdge.class);

	private static int totalVertices;
	private static String dfsColor[] = null;
	static String bfsColor[] = null;
	private static int dfsTime[][] = null;
	static int bfsTime[][] = null;
	private static ArrayList<String> dfsOrder = new ArrayList<String>();
	private static ArrayList<String> bfsOrder = new ArrayList<String>();
	private static int bfsCount;

	private static void readFile(String args[]) {
		Scanner s = null;

		try {
			s = new Scanner(new File(args[0]));
		} catch (FileNotFoundException e) {
			// can't find file
			System.out.println("cant find file");
		}

		DefaultWeightedEdge e = null;
		String line[], input;

		totalVertices = s.nextInt();

		for (int i = 0; i < totalVertices; i++) {
			g.addVertex(Integer.toString(i));
		}

		s.nextLine();

		while (s.hasNextLine()) {
			input = s.nextLine();
			line = input.split(" ");

			e = g.addEdge(line[0], line[1]);
			g.setEdgeWeight(e, Double.parseDouble(line[2]));
		}

	}

	public static void main(String args[]) {

		readFile(args);

		// test reading of files
		// System.out.println(g.toString());
		// Set<DefaultWeightedEdge> edges = g.edgeSet();
		// for (DefaultWeightedEdge e: edges){
		// System.out.print(e);
		// System.out.println(" " +g.getEdgeWeight(e));
		// }

		DFS();
		System.out.println("Depth First Search Traversal: " + dfsOrder);

		BFSMain();
		System.out.println("Breadth First Search Traversal: " + bfsOrder);
		System.out.println("");

		ArrayList<String> vertexSet = new ArrayList<String>();
		for (String s : g.vertexSet()) {
			vertexSet.add(s);
		}

		MST(vertexSet);
		double totalWeight = 0;
		System.out.println("MST: ");
		System.out.println("V: " + mst.vertexSet());
		System.out.println("E: " + mst.edgeSet());
		System.out.print("Wt: [ ");
		for (DefaultWeightedEdge e : mst.edgeSet()) {
			System.out.print(mst.getEdgeWeight(e) + ", ");
			totalWeight += mst.getEdgeWeight(e);
		}
		System.out.println("]");
		System.out.println("Total weight: "
				+ (double) Math.round(totalWeight * 100) / 100);
		
		
		System.out.println("");
		ArrayList<DijkstrasVertex> dv;
		for (int i = 0; i < totalVertices-1; i++) {
			dv = ShortestPath(i);
			for (int j = i+1; j < totalVertices; j++) {

				System.out.println(i +" -> " +j +" = " +backSolveSP(dv, i, j));
			}
			dv.clear();


		}
		

	}

	private static String backSolveSP(ArrayList<DijkstrasVertex> solution,
			int startPt, int endPt) {
		
		String answer = "";
		double weight = 0;
		DijkstrasVertex d = null;

		//System.out.println(solution);
		for (DijkstrasVertex dv : solution) {
			if (dv.getV() == endPt) {
				if (dv.getP() == null) {
					answer = "No path exists";
					return answer;
				} else {
					d = dv;
				}
			}
		}

		
		while (d != null && d.getP() != null) {
			for (DijkstrasVertex dv : solution) {
				if (Integer.toString(dv.getV()).equals(d.getP())) {
					answer += "("
							+ dv.getV()
							+ ", "
							+ d.getV()
							+ ", "
							+ getWeight(Integer.toString(d.getV()),
									Integer.toString(dv.getV())) + ")";
					weight += getWeight(Integer.toString(d.getV()), Integer.toString(dv.getV()));
					d = dv;
					if (d.getP() != null){
						answer += " ---> ";
					}
					break;
				}

			}
			


		}


		return answer + " Weight: " +(double)Math.round(weight*100)/100;

	}

	private static void DFS() {

		dfsColor = new String[totalVertices];
		int temp, count = 1;
		dfsTime = new int[totalVertices][2];
		Stack<String> s = new Stack<String>();
		ArrayList<String> neighbors = null;
		String x, y;

		for (int i = 0; i < totalVertices; i++) {
			dfsColor[i] = "w";
		}
		for (int k = 0; k < totalVertices; k++) {
			if (dfsColor[k].equals("w")) {
				s.push(Integer.toString(k));
				dfsColor[k] = "g";
				dfsOrder.add(Integer.toString(k));
				dfsTime[k][0] = count;
				count++;

				while (!s.isEmpty()) {

					x = s.peek();
					y = null;
					neighbors = getNeighbors(x);
					Collections.sort(neighbors);

					// find next white neighbor of x
					for (String n : neighbors) {
						temp = Integer.parseInt(n);
						if (dfsColor[temp].equals("w")) {
							y = n;
							break;
						}
					}

					if (y == null) {
						x = s.pop();
						temp = Integer.parseInt(x);
						dfsColor[temp] = "b";
						dfsTime[temp][1] = count;
						count++;
					} else {
						s.push(y);
						temp = Integer.parseInt(y);
						dfsOrder.add(y);
						dfsColor[temp] = "g";
						dfsTime[temp][0] = count;
						count++;
					}

				}
			}
		}

	}

	private static void BFSMain() {
		bfsColor = new String[totalVertices];
		bfsTime = new int[totalVertices][2];

		for (int i = 0; i < totalVertices; i++) {
			bfsColor[i] = "w";
		}

		bfsCount = 0;

		for (int i = 0; i < totalVertices; i++) {
			if (bfsColor[i].equals("w")) {
				BFS(Integer.toString(i));
			}
		}

	}

	private static void BFS(String v) {
		bfsCount++;
		int temp = Integer.parseInt(v);
		String x;
		Queue<String> q = new ArrayDeque<String>();
		ArrayList<String> neighbor = new ArrayList<String>();

		bfsColor[temp] = "g";
		bfsTime[temp][0] = bfsCount;
		bfsOrder.add(v);

		q.add(v);
		while (!q.isEmpty()) {
			x = q.poll();
			neighbor = getNeighborWithWt(x);
			for (String y : neighbor) {
				temp = Integer.parseInt(y);
				if (bfsColor[temp].equals("w")) {
					bfsCount++;
					bfsTime[temp][0] = bfsCount;
					bfsColor[temp] = "g";
					bfsOrder.add(y);
					q.add(y);
				}
			}
			bfsCount++;
			temp = Integer.parseInt(x);
			bfsTime[temp][1] = bfsCount;
			bfsColor[temp] = "b";

		}

	}

	private static void MST(ArrayList<String> initialSet) {
		ArrayList<String> v = new ArrayList<String>();

		v.add(initialSet.get(0));
		ArrayList<DefaultWeightedEdge> fringe = getFringe(v);
		mst.addVertex(initialSet.get(0));
		DefaultWeightedEdge e = null;
		double weight;

		for (int i = 1; i < totalVertices; i++) {
			if (fringe.isEmpty()) {
				// restart fringe on remaining vertices
				ArrayList<String> remaining = new ArrayList<String>();
				for (String v1 : g.vertexSet()) {
					if (!mst.vertexSet().contains(v1)) {
						remaining.add(v1);
					}
				}
				if (!remaining.isEmpty()) {
					MST(remaining);
				}
			} else {
				e = fringe.get(0);

				// add v to set and mst
				if (v.contains(g.getEdgeTarget(e).toString())) {
					v.add(g.getEdgeSource(e).toString());
					mst.addVertex(g.getEdgeSource(e).toString());
				} else {
					v.add(g.getEdgeTarget(e).toString());
					mst.addVertex(g.getEdgeTarget(e).toString());
				}

				// add edge and weight to mst
				mst.addEdge(g.getEdgeSource(e).toString(), g.getEdgeTarget(e)
						.toString());
				weight = g.getEdgeWeight(e);
				e = mst.getEdge(g.getEdgeSource(e).toString(),
						g.getEdgeTarget(e).toString());
				mst.setEdgeWeight(e, weight);

				// recalculate fringe
				fringe = getFringe(v);
			}
		}

	}

	private static ArrayList<DijkstrasVertex> ShortestPath(int s) {

		Comparator<DijkstrasVertex> distanceComparator = new Comparator<DijkstrasVertex>() {
			public int compare(DijkstrasVertex d1, DijkstrasVertex d2) {
				if (d1.getD() < d2.getD()) {
					return -1;
				} else {
					return 1;
				}
			}
		};

		ArrayList<DijkstrasVertex> result = new ArrayList<DijkstrasVertex>();
		ArrayList<String> neighbor = new ArrayList<String>();
		ArrayList<DijkstrasVertex> neighborDV = new ArrayList<DijkstrasVertex>();
		Queue<DijkstrasVertex> q = new PriorityQueue<DijkstrasVertex>(999,
				distanceComparator);
		Queue<DijkstrasVertex> temp;


		DijkstrasVertex u = new DijkstrasVertex();

		for (int i = 0; i < totalVertices; i++) {
			if (i != s) {
				q.add(new DijkstrasVertex(i, Double.MAX_VALUE, null));
			}
		}

		q.add(new DijkstrasVertex(s, 0, null));
		for (int i = 0; i < totalVertices; i++) {
			u = q.poll();
			result.add(new DijkstrasVertex(u));
			neighbor = getNeighbors(Integer.toString(u.getV()));
			neighborDV.clear();
			// map every vertex in u's neighborhood to a disjkstrasvertex obj
			// by finding the neighbor of u - result set
			for (String n : neighbor) {
				for (DijkstrasVertex dv : q) {
					// once object is found in queue
					if (Integer.toString(dv.getV()).equals(n)) {
						// check to see if it is already in solution set
						boolean inSet = false;

						for (DijkstrasVertex dv2 : result) {
							if (Integer.toString(dv2.getV()).equals(n)) {
								inSet = true;
								break;
							}
						}
						// if not in set, add to neighborhood to check
						if (!inSet) {
							neighborDV.add(new DijkstrasVertex(dv));
							break;
						}
					}
				}
			}
			
			temp = new PriorityQueue(q);
			DijkstrasVertex dvTemp = null;
			for (DijkstrasVertex dv : neighborDV) {
				if (u.getD()
						+ getWeight(Integer.toString(dv.getV()),
								Integer.toString(u.getV())) < dv.getD()) {
					
					for (DijkstrasVertex d1 : temp){
						if (d1.getV() == dv.getV()){
							dvTemp = d1;
							break;
						}
					}
					
					temp.remove(dvTemp);
					

					dv.setD(u.getD()
							+ getWeight(Integer.toString(dv.getV()),
									Integer.toString(u.getV())));
					dv.setP(Integer.toString(u.getV()));
					// decrease priority of u
					temp.add(dv);
				}
			}
			q = new PriorityQueue(temp);

		}
		return result;
	}

	private static double getWeight(String v1, String v2) {

		DefaultWeightedEdge e = g.getEdge(v1, v2);

		return g.getEdgeWeight(e);

	}

	private static ArrayList<DefaultWeightedEdge> getFringe(
			ArrayList<String> vertexSet) {

		Set<DefaultWeightedEdge> temp;

		ArrayList<DefaultWeightedEdge> a = new ArrayList<DefaultWeightedEdge>();
		ArrayList<DefaultWeightedEdge> removal = new ArrayList<DefaultWeightedEdge>();

		// get all neighbors added to a
		for (String s : vertexSet) {
			temp = g.edgesOf(s);
			for (DefaultWeightedEdge e : temp) {
				a.add(e);
			}
		}

		// comparator to sort by weight
		Comparator<DefaultWeightedEdge> WeightComparator = new Comparator<DefaultWeightedEdge>() {
			public int compare(DefaultWeightedEdge e1, DefaultWeightedEdge e2) {
				if (g.getEdgeWeight(e1) < g.getEdgeWeight(e2)) {
					return -1;
				} else {
					return 1;
				}
			}
		};
		// sort by weight
		Collections.sort(a, WeightComparator);

		// find list of edges to remove
		for (DefaultWeightedEdge e : a) {

			if (vertexSet.contains(g.getEdgeTarget(e).toString())
					&& vertexSet.contains(g.getEdgeSource(e).toString())) {
				removal.add(e);
			}

		}
		// remove edges from a
		for (DefaultWeightedEdge e : removal) {
			a.remove(e);
		}

		return a;
	}

	private static ArrayList<String> getNeighbors(String s) {
		Set<DefaultWeightedEdge> neighbors;
		neighbors = g.edgesOf(s);
		ArrayList<String> n = new ArrayList<String>();

		for (DefaultWeightedEdge e : neighbors) {

			if (g.getEdgeTarget(e).toString().equals(s)) {
				n.add(g.getEdgeSource(e).toString());
			} else {
				n.add(g.getEdgeTarget(e).toString());
			}
		}

		return n;
	}

	private static ArrayList<String> getNeighborWithWt(String s) {
		Set<DefaultWeightedEdge> neighbors;
		ArrayList<DefaultWeightedEdge> a = new ArrayList<DefaultWeightedEdge>();
		ArrayList<String> v = new ArrayList<String>();

		neighbors = g.edgesOf(s);
		for (DefaultWeightedEdge e : neighbors) {
			a.add(e);
		}

		Comparator<DefaultWeightedEdge> WeightComparator = new Comparator<DefaultWeightedEdge>() {
			public int compare(DefaultWeightedEdge e1, DefaultWeightedEdge e2) {
				if (g.getEdgeWeight(e1) < g.getEdgeWeight(e2)) {
					return -1;
				} else {
					return 1;
				}
			}
		};

		Collections.sort(a, WeightComparator);

		for (DefaultWeightedEdge e : a) {

			if (g.getEdgeTarget(e).toString().equals(s)) {
				v.add(g.getEdgeSource(e).toString());
			} else {
				v.add(g.getEdgeTarget(e).toString());
			}
		}

		return v;
	}

}
