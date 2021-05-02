import java.awt.EventQueue;
import javax.swing.JFrame;

public class Snake1 extends JFrame {

	public Snake1() {

		initUI();
	}

	private void initUI() {

		add(new Board());

		setResizable(false);
		pack();

		setTitle("Snake Game");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void agentpersona() {
		EventQueue.invokeLater(() -> {
			JFrame ex = new Snake1();
			ex.setVisible(true);
		});
	}
}
