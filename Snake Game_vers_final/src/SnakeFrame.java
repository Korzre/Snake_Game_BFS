import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

public class SnakeFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SnakeFrame() {

		SnakePanel panel = new SnakePanel();
		add(panel);
		pack();
		setTitle("Snake Game");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		setPreferredSize(new Dimension(700, 700));
		setResizable(false);
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setLocationRelativeTo(null);
		setFocusable(true);
		addKeyListener(panel);

	}
}
