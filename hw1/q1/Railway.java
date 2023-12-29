
//-----------------------------------------------------
// Title: Railway class
// Author: Asli Algin
// Section: 02
// Assignment: 1
// Description: The Railway class reads information about railway stations, tracks, and their connections, 
//calculates the shortest distances between specified stations.
//-----------------------------------------------------
import java.util.Scanner;

public class Railway {

	public static void main(String[] args) {
		try (Scanner scan = new Scanner(System.in)) {
			int N = scan.nextInt(); // stations
			int M = scan.nextInt(); // undirected railway tracks
			int X = scan.nextInt();
			int Y = scan.nextInt();

			Graph graph = new Graph(N);

			int i = 0;
			while (i < M) {
				int a = scan.nextInt() - 1;
				int b = scan.nextInt() - 1;
				graph.addConnection(a, b);
				i++;
			}

			int[] realdistance = graph.calculateShortestDistances(X - 1); /// x: home statiton
			int realD = realdistance[Y - 1]; // y: TEDU station

			int[] newTracks = findNewTracks(graph, N, X, Y, realD);

			printTracks(newTracks);
		}
	}

	private static void printTracks(int[] tracks) {
		// --------------------------------------------------------
		// Summary: This method prints the discovered railway tracks, or -1
		// if none are found.
		// Precondition: The tracks array is integer.
		// Postcondition: It prints by iterating through a sequence of track indices and
		// displaying pairs of station numbers.
		// --------------------------------------------------------

		if (tracks.length == 0) {
			return;
		}

		int count = tracks.length / 2;

		if (count == 0) {
			System.out.println(-1);
		} else {
			System.out.println(count);
			for (int i = 0; i < tracks.length; i += 2) {
				System.out.printf("%d %d%n", tracks[i], tracks[i + 1]);
			}
		}
	}

	private static int[] findNewTracks(Graph graph, int N, int X, int Y, int realD) {
		// --------------------------------------------------------
		// Summary:This method finds all possible pairs of railway stations, finds
		// tracks, recalculates the shortest distances, and temporarily removes
		// connections.
		// Precondition: N, X, Y and realD are integers.
		// Postcondition: It returns the discovered tracks, returns -1 if not found.
		// --------------------------------------------------------

		int[] track = new int[N * N];
		int count = 0;

		int i = 0;
		while (i < N) {
			int j = i + 1;
			while (j < N) {

				boolean hasConnection = graph.isConnection(i, j);

				if (hasConnection == false) {
					graph.addConnection(i, j);
					int[] newDistance = graph.calculateShortestDistances(X - 1);
					graph.removeConnection(i, j);

					if (newDistance[Y - 1] >= realD) {
						track[count++] = i + 1;
						track[count++] = j + 1;
					}
				}
				j = j + 1;
			}
			i = i + 1;
		}

		if (count == 0) {
			System.out.println(-1);
			return new int[0];
		} else {
			int[] result = new int[count];
			System.arraycopy(track, 0, result, 0, count);
			return result;
		}
	}
}
