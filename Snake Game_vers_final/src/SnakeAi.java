import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class SnakeAi {
	SnakeNode previousStartNode;
	SnakeNode previousGoal;
	SnakePanel panel;

	public SnakeAi(SnakePanel panel) {
		this.panel = panel;
	}

	public LinkedList<SnakeNode> performSearch(int type, SnakeNode startNode, SnakeNode destinationNode) {
		switch (type) {
		case GameContants.SEARCH_TYPE_A_STAR: {
			return performAstar(startNode, destinationNode);
		}
		case GameContants.SEARCH_TYPE_BFS: {
			return perFormBFS(startNode, destinationNode);
		}
		case GameContants.SEARCH_TYPE_DFS: {
			return perFormDFS(startNode, destinationNode);
		}
		}

		throw new RuntimeException("Wrong searhc Type " + type);
	}

	/**
	 * Calculate Manhattan distance
	 */
	public int manhattanDis(SnakeNode source, SnakeNode destination) {
		int xAbs = Math.abs(source.getX() - destination.getX());
		int yAbs = Math.abs(source.getY() - destination.getY());
		return xAbs + yAbs;
	}

	/**
	 * Calculate euclidian distance
	 */
	public int euclidianDis(SnakeNode source, SnakeNode destination) {
		int xAbs = Math.abs(source.getX() - destination.getX());
		int yAbs = Math.abs(source.getY() - destination.getY());

		return (int) Math.sqrt(xAbs * xAbs + yAbs * yAbs);
	}

	/**
	 * 
	 * @param startNode
	 * @param destinationNode
	 * @return LinkedList<MySnakeNode>
	 */
	private LinkedList<SnakeNode> performAstar(SnakeNode startNode, SnakeNode destinationNode) {
		PriorityQueue<SnakeNode> openNodesQueue = new PriorityQueue<SnakeNode>();

		LinkedList<SnakeNode> closedNodesList = new LinkedList<SnakeNode>();

		startNode.setG(0);

		startNode.setH(manhattanDis(startNode, destinationNode));

		startNode.setParent(null);
		openNodesQueue.add(startNode);

		// Run loop until openNodesQueue became empty
		while (!openNodesQueue.isEmpty()) {

			// remove a node from the queue
			// because it is PriorityQueue so it will give us the node which has lowest F
			SnakeNode polledNode = (SnakeNode) openNodesQueue.remove();

			// if polledNode is destination then just make path and return
			if (polledNode.equals(destinationNode)) {
				return makePath(polledNode);
			}

			// find all neighbour of the polledNode
			List<SnakeNode> neighbourNodes = polledNode.myNeighbors();

			for (int i = 0; i < neighbourNodes.size(); i++) {

				// get neighbour one by one
				SnakeNode neighborNode = neighbourNodes.get(i);

				boolean isInOpen = openNodesQueue.contains(neighborNode);
				boolean isInClosed = closedNodesList.contains(neighborNode);

				// calculate distance between polledNode and this neighborNode
				// and add that distance into the polledNode G which was his own distance from
				// startNode

				int neighborDistanceFromStart = polledNode.getG() + manhattanDis(polledNode, neighborNode);

				if ((!isInOpen && !isInClosed) /* || neighborDistanceFromStart < neighborNode.getG() */) {

					// set the parameters of this neighborNode
					neighborNode.setParent(polledNode);
					neighborNode.setG(neighborDistanceFromStart);
					neighborNode.setH(manhattanDis(neighborNode, destinationNode));

					if (isInClosed) {
						closedNodesList.remove(neighborNode);
					}
					if (!isInOpen && shouldProcess(neighborNode)) {
						openNodesQueue.add(neighborNode);
					}
				}
			}
			closedNodesList.add(polledNode);
		}

		return null;
	}

	/**
	 * Discover Neighbors and add them in ArrayList at end Then pop one Node from
	 * end of ArrayList because it is DFS
	 * 
	 * @param startNode
	 * @param destinationNode
	 * @return LinkedList<MySnakeNode>
	 */
	private LinkedList<SnakeNode> perFormDFS(SnakeNode startNode, SnakeNode destinationNode) {

		ArrayList<SnakeNode> open = new ArrayList<SnakeNode>();
		ArrayList<SnakeNode> closed = new ArrayList<SnakeNode>();

		open.add(startNode);
		startNode.setParent(null);
		while (!open.isEmpty()) {

			SnakeNode node = open.remove(open.size() - 1);

			if (node.equals(destinationNode)) {
				LinkedList<SnakeNode> path = makePath(node);
				return path;
			}
			closed.add(node);
			List<SnakeNode> neighbourNodes = node.myNeighbors();

			for (SnakeNode node1 : neighbourNodes) {
				boolean isInOpen = open.contains(node1);
				boolean isInClosed = closed.contains(node1);

				if (!isInOpen && !isInClosed && shouldProcess(node1)) {
					node1.setParent(node);
					open.add(node1);
				}
			}

		}

		return null;
	}

	/**
	 * Discover Neighbors and add them in ArrayList at end Then pop one Node from
	 * start of ArrayList because it is BFS
	 * 
	 * @param startNode
	 * @param destinationNode
	 * @return LinkedList<MySnakeNode>
	 */
	private LinkedList<SnakeNode> perFormBFS(SnakeNode startNode, SnakeNode destinationNode) {

		ArrayList<SnakeNode> open = new ArrayList<SnakeNode>();
		ArrayList<SnakeNode> closed = new ArrayList<SnakeNode>();

		open.add(startNode);
		startNode.setParent(null);
		while (!open.isEmpty()) {

			SnakeNode node = open.remove(0);

			if (node.equals(destinationNode)) {
				LinkedList<SnakeNode> path = makePath(node);
				return path;
			}
			closed.add(node);
			List<SnakeNode> neighbourNodes = node.myNeighbors();

			for (SnakeNode node1 : neighbourNodes) {
				boolean isInOpen = open.contains(node1);
				boolean isInClosed = closed.contains(node1);

				if (!isInOpen && !isInClosed && shouldProcess(node1)) {
					node1.setParent(node);
					open.add(node1);
				}
			}

		}

		return null;
	}

	/**
	 * Find a appropriate random Neighbor
	 * 
	 * @return MySnakeNode
	 */
	public SnakeNode getRandomMove() {
		List<SnakeNode> list = panel.mySnake.getHead().myNeighbors();

		for (int a = 0; a < list.size(); a++) {
			SnakeNode node = list.get(a);

			if (shouldProcess(node)) {
				return node;
			}

		}
		System.out.println("moveRandom failed");
		return null;
	}

	public LinkedList<SnakeNode> makePath(SnakeNode node) {

		LinkedList<SnakeNode> path = new LinkedList<SnakeNode>();

		while (node.getParent() != null) {
			path.addFirst(node);
			node = node.getParent();
		}

		return path;
	}

	/**
	 * @return should we process this node
	 */
	public boolean shouldProcess(SnakeNode n) {
		// if node is out of screen MAX
		if (n.getX() > (GameContants.GAME_BOARD_WIDTH - GameContants.MY_SNAKE_WIDTH_HEIGHT)
				|| n.getY() > (GameContants.GAME_BOARD_HEIGHT - GameContants.MY_SNAKE_WIDTH_HEIGHT)) {
			return false;
		}
		// if node is out of screen MIN
		if (n.getX() < 0 || n.getY() < 0) {
			return false;
		}

		boolean shouldProceed = !panel.mySnake.getCompleSnake().contains(n);
//		System.out.println("shouldProcess: "+shouldProceed);
		return shouldProceed;

	}

}
