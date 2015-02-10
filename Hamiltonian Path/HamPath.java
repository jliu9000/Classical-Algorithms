import java.io.*;
import java.util.*;

import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

public class HamPath {

	private UndirectedGraph<String, DefaultEdge> g = new SimpleGraph<String, DefaultEdge>(
			DefaultEdge.class);
	private ArrayList<String> vertices = new ArrayList<String>();
	private ArrayList<String> hamPath = new ArrayList<String>();

	private void readVertex(String args[]) {
		Scanner s = null;
		int count;
		String line;
		String input[] = null;

		try {
			s = new Scanner(new File(args[0]));
		} catch (FileNotFoundException e) {
			// can't find file
			System.out.println("cant find file");
		}

		count = s.nextInt();
		s.nextLine();

		for (int i = 0; i < count; i++) {
			line = s.nextLine();
			input = line.split(" ");
			g.addVertex(input[0]);
			vertices.add(input[0]);
			// add all edges
			for (int j = 1; j < input.length; j++) {
				if (!g.containsVertex(input[j])) {
					g.addVertex(input[j]);
				}
				if (!g.containsEdge(input[0], input[j])
						&& !input[0].equals(input[j])) {
					g.addEdge(input[0], input[j]);
				}
			}
		}

	}

	public static void main(String args[]) {

		HamPath h = new HamPath();
		h.readVertex(args);
		ArrayList<String> path = new ArrayList<String>();

		for (int i = 0; i <h.vertices.size(); i++) {
			path = new ArrayList<String>();
			path.add(h.vertices.get(i));
			path = h.HamWalk(h.g, path);
			if (path != null) {
				break;
			}
		}

		System.out.println("Hamiltonian path: " + path);

	}

	private ArrayList<String> HamWalk(UndirectedGraph g, ArrayList<String> path) {
		if (path.size() == g.vertexSet().size()) {
			return path;
		}
		ArrayList<String> neighbor;

		neighbor = this.getNeighbors(path.get(path.size() - 1));
		
		for (int i = 0; i < neighbor.size(); i++) {
			if (!path.contains(neighbor.get(i))) {
				path.add(neighbor.get(i));
				if (HamWalk(g, path) == null){
					path.remove(neighbor.get(i));
				} else {
					return path;
				}
			}
			
		}
		
		return null;
	}

	private ArrayList<String> getNeighbors(String s) {
		Set<DefaultEdge> neighbors;
		neighbors = this.g.edgesOf(s);
		ArrayList<String> n = new ArrayList<String>();
		for (DefaultEdge e : neighbors) {
			if (this.g.getEdgeTarget(e).toString().equals(s)) {
				n.add(this.g.getEdgeSource(e).toString());
			} else {
				n.add(this.g.getEdgeTarget(e).toString());
			}
		}
		return n;
	}

}
