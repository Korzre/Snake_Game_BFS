import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

import javax.swing.JPanel;

public class SnakePanel extends JPanel implements KeyListener {

	public Snake mySnake;
	private final GameRunner gameRunner = new GameRunner(this);
	private final FoodProvider foodProvider = new FoodProvider();
	public final FoodPathController foodController = new FoodPathController();

	private final SnakeAi mySnakeAi = new SnakeAi(this);

	private int searchType = GameContants.SEARCH_TYPE_A_STAR;

	public SnakePanel() {
		setPreferredSize(new Dimension(GameContants.GAME_BOARD_WIDTH, GameContants.GAME_BOARD_HEIGHT));
		setBackground(Color.black);
		setFocusable(true);

		SnakeNode head = new SnakeNode(GameContants.GAME_BOARD_WIDTH / 2, GameContants.GAME_BOARD_HEIGHT / 2);
		SnakeNode tail = new SnakeNode(GameContants.GAME_BOARD_WIDTH / 2,
				(GameContants.GAME_BOARD_HEIGHT / 2) + GameContants.MY_SNAKE_WIDTH_HEIGHT);

		mySnake = new Snake(head, tail);
		mySnake.getCompleSnake().add(head);
		mySnake.getCompleSnake().add(tail);

		setFood();
		new Thread(gameRunner).start();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		drawSnake(g);
		drawFood(g);

		if (foodController.getPathToCurrentFood() == null) {

			SnakeNode n = mySnake.getFood();

			LinkedList<SnakeNode> list = mySnakeAi.performSearch(searchType, mySnake.getHead(), mySnake.getFood());
			foodController.setPathToCurrentFood(list);

			if (list == null) {
				moveRandom();
			}
		}
		if (foodController.getPathToCurrentFood() != null) {
			SnakeNode node = foodController.getPathToCurrentFood().remove();
			mySnake.moveSnake(node);
		}

	}

	private void moveRandom() {
		// here we should make a random move so that snake tail move and maybe we find a
		// path in next call
		SnakeNode path = mySnakeAi.getRandomMove();
		if (path != null) {
			mySnake.moveSnake(path);
		}
	}

	private void setFood() {
		SnakeNode food = foodProvider.getFood(mySnake);
		mySnake.setFood(food);
		foodController.setCurretnFood(food);
		foodController.setPathToCurrentFood(null);
	}

	private void drawFood(Graphics g) {
		g.setColor(Color.GREEN);
		g.fillRect(mySnake.getFood().getX(), mySnake.getFood().getY(), GameContants.MY_SNAKE_WIDTH_HEIGHT,
				GameContants.MY_SNAKE_WIDTH_HEIGHT);

	}

	private void drawSnake(Graphics g) {
//		System.out.println("snake length is"+mySnake.getCompleSnake().size());
		// getting complete snake to draw
		for (int a = 0; a < mySnake.getCompleSnake().size(); a++) {
			SnakeNode node = mySnake.getCompleSnake().get(a);

			if (node.equals(mySnake.getHead())) {
				g.setColor(Color.RED);
			} else if (node.equals(mySnake.getTail())) {
				g.setColor(Color.YELLOW);

			} else {
				g.setColor(Color.WHITE);
			}

			if (node.equals(mySnake.getFood())) {
				setFood();
			}
			g.fillRect(node.getX(), node.getY(), GameContants.MY_SNAKE_WIDTH_HEIGHT,
					GameContants.MY_SNAKE_WIDTH_HEIGHT);

		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {

		switch (e.getKeyCode()) {
		case KeyEvent.VK_A: {
			System.out.println("converting to aStar");

			searchType = GameContants.SEARCH_TYPE_A_STAR;
			break;
		}
		case KeyEvent.VK_D: {
			System.out.println("converting to DFS");

			searchType = GameContants.SEARCH_TYPE_DFS;
			break;
		}
		case KeyEvent.VK_B: {
			System.out.println("converting to BFS");

			searchType = GameContants.SEARCH_TYPE_BFS;
			break;
		}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

}
