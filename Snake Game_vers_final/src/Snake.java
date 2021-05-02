import java.util.ArrayList;
import java.util.List;

public class Snake {

	private SnakeNode head;
	private SnakeNode tail;
	private SnakeNode food;

	private List<SnakeNode> compleSnake = new ArrayList<>();

	public Snake(SnakeNode head, SnakeNode tail) {
		this.head = head;
		this.tail = tail;
	}

	public List<SnakeNode> getCompleSnake() {
		return compleSnake;
	}

	public void setCompleSnake(List<SnakeNode> compleSnake) {
		this.compleSnake = compleSnake;
	}

	public SnakeNode getHead() {
		return head;
	}

	public void setHead(SnakeNode head) {
		this.head = head;
	}

	public SnakeNode getTail() {
		return tail;
	}

	public void setTail(SnakeNode tail) {
		this.tail = tail;
	}

	public SnakeNode getFood() {
		return food;
	}

	public void setFood(SnakeNode food) {
		this.food = food;
	}

	public void moveSnake(SnakeNode moveTo) {
		compleSnake.add(0, moveTo);

		setHead(compleSnake.get(0));

		if (moveTo.equals(food)) {
		} else {
			compleSnake.remove(getTail());
			setTail(compleSnake.get(compleSnake.size() - 1));
		}
	}

}
