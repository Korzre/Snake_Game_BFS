import java.util.Random;

public class FoodProvider {
	private final Random random = new Random();

	public SnakeNode getFood(Snake snake) {
		while (true) {
			int x = 0, y;
			x = GameContants.MY_SNAKE_WIDTH_HEIGHT
					* (int) (Math.random() * GameContants.GAME_BOARD_WIDTH / GameContants.MY_SNAKE_WIDTH_HEIGHT);
			y = GameContants.MY_SNAKE_WIDTH_HEIGHT
					* (int) (Math.random() * GameContants.GAME_BOARD_HEIGHT / GameContants.MY_SNAKE_WIDTH_HEIGHT);
			SnakeNode n = new SnakeNode(x, y);

			if (!snake.getCompleSnake().contains(n)) {
//				System.out.println("x="+x);
//				System.out.println("y="+y);

				return n;
			}
		}
	}
}
