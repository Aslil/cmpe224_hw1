
//-----------------------------------------------------
// Title: MuseumVisit class
// Author: Asli Algin
// Section: 02
// Assignment: 1
// Description: The MuseumVisit class represents a museum visit scenario using a graph representation, 
//where museums are nodes, and roads connecting them are weighted edges. Program uses DFS (depth-first search) algorithm.
//-----------------------------------------------------
import java.util.ArrayList;
import java.util.Scanner;

public class MuseumVisit {

	static ArrayList<ArrayList<Museum>> graph;
	static int[] visited;

	public static void main(String[] args) {
		// --------------------------------------------------------
		// Summary: This method takes input for the number of museums, then creates a
		// graph representation of the museums and roads.The program performs a
		// DFS starting from each museum to find the optimal path.
		// Precondition: The args array is String.
		// Postcondition: If no valid path is found, it prints -1. It uses an adjacency
		// list to represent the graph.
		// --------------------------------------------------------

		try (Scanner scanner = new Scanner(System.in)) {
			int M = scanner.nextInt(); // Number of museums
			int N = scanner.nextInt(); // Number of roads

			graph = new ArrayList<>(M + 1);
			visited = new int[M + 1];

			for (int i = 0; i <= M; i++) {
				graph.add(new ArrayList<>());
			}

			for (int i = 0; i < N; i++) {
				int U = scanner.nextInt();
				int V = scanner.nextInt();
				int weight = scanner.nextInt();

				graph.get(U).add(new Museum(V, weight));
				graph.get(V).add(new Museum(U, weight));
			}

			int minTime = Integer.MAX_VALUE;
			ArrayList<Integer> optimalPath = null;

			// Try starting the journey from each museum
			for (int i = 1; i <= M; i++) {
				visited = new int[M + 1];
				ArrayList<Integer> path = new ArrayList<>();
				int time = dfs(i, path);

				if (time != -1 && time < minTime) {
					minTime = time;
					optimalPath = new ArrayList<>(path);
				}
			}

			if (optimalPath == null) {
				System.out.println(-1);
			} else {
				System.out.println(minTime);
				for (int museum : optimalPath) {
					System.out.print(museum + " ");
				}
			}
		}
	}

	static int dfs(int currentMuseum, ArrayList<Integer> path) {
		// --------------------------------------------------------
		// Summary: This method takes the entire amount of time spent throughout the
		// visit using a recursive DFS traverse with the current museum as a parameter.
		// Precondition: The currentMuseum is integer and path is arraylist which takes
		// integer..
		// Postcondition: It returns the total time spent during the visit.
		// --------------------------------------------------------

		visited[currentMuseum] = 1;
		path.add(currentMuseum);

		int totalTime = 0;

		for (Museum neighbor : graph.get(currentMuseum)) {
			if (visited[neighbor.id] == 0) {
				totalTime += neighbor.weight;
				totalTime += dfs(neighbor.id, path);
			}
		}

		return totalTime;
	}
}
