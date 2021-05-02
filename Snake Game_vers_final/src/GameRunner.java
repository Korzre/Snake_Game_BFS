
public class GameRunner implements Runnable {

	private SnakePanel panel;

	public GameRunner(SnakePanel panel) {
		this.panel = panel;
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(GameContants.GAME_DELAY);
				panel.repaint();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public SnakePanel getPanel() {
		return panel;
	}

	public void setPanel(SnakePanel panel) {
		this.panel = panel;
	}

}
