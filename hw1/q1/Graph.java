//-----------------------------------------------------
// Title: Graph class
// Author: Asli Algin
// ID: 23275494084
// Section: 02
// Assignment: 1
// Description: The Graph class represents an undirected graph, providing methods to check, add or remove connections 
//and calculate the shortest distances using BFS.
//-----------------------------------------------------
public class Graph {
	private int totalVertices;
	private int[][] connections;

	public Graph(int vertices) { // constructor
		this.totalVertices = vertices;
		this.connections = new int[vertices][];
		for (int i = 0; i < vertices; i++) {
			this.connections[i] = new int[0];
		}
	}

	public boolean isConnection(int u, int v) {
		// This method checks if there is a connection between two vertices in an
		// undirected graph.
		for (int neighbor : this.connections[u]) {
			if (neighbor == v) {
				return true;
			}
		}
		return false;
	}

	public void addConnection(int u, int v) {
		// This method adds an undirected connection between two vertices in a graph.
		if (!this.isConnection(u, v)) {
			this.connections[u] = this.appendElement(this.connections[u], v);
			this.connections[v] = this.appendElement(this.connections[v], u);
		}
	}

	public void removeConnection(int u, int v) {
		// This method removes an undirected connection between two vertices in a graph.
		this.connections[u] = this.remove(this.connections[u], v);
		this.connections[v] = this.remove(this.connections[v], u);
	}

	public int[] calculateShortestDistances(int startVertex) {
		// --------------------------------------------------------
		// Summary: This method uses Breadth-First Search
		// (BFS) to compute the shortest distances.
		// Precondition: startVertex is integer.
		// Postcondition: It updates an array with the calculated distances.
		// --------------------------------------------------------
		int[] distances = new int[this.totalVertices];
		boolean[] visited = new boolean[this.totalVertices];

		for (int i = 0; i < this.totalVertices; i++) {
			distances[i] = -1;
		}

		distances[startVertex] = 0;
		visited[startVertex] = true;

		int[] queue = new int[this.totalVertices];
		int front = 0, rear = 0;
		queue[rear++] = startVertex;

		while (front < rear) {
			int currentVertex = queue[front++];

			for (int neighbor : this.connections[currentVertex]) {
				if (!visited[neighbor]) {
					visited[neighbor] = true;
					distances[neighbor] = distances[currentVertex] + 1;
					queue[rear++] = neighbor;
				}
			}
		}

		return distances;
	}

	private int[] appendElement(int[] array, int element) {
		// This method adds a specified element to the end of an integer array.
		int length = array.length;
		int[] newArray = new int[length + 1];
		System.arraycopy(array, 0, newArray, 0, length);
		newArray[length] = element;
		return newArray;
	}

	private int[] remove(int[] array, int element) {
		// --------------------------------------------------------
		// Summary: This method eliminates a specified element from an integer array,
		// creating a new array without the specified element.
		// Precondition: array is integer, element is integer.
		// Postcondition: It returns the new modified array.
		// --------------------------------------------------------
		int length = array.length;
		int[] newArray = new int[length - 1];
		int newIndex = 0;

		for (int i : array) {
			if (i != element) {
				newArray[newIndex++] = i;
			}
		}

		if (newIndex < newArray.length) {
			System.arraycopy(array, newIndex, newArray, newIndex, newArray.length - newIndex);
		}

		return newArray;
	}
}
