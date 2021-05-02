import java.util.Scanner;

import javax.swing.JFrame;

public class SnakeMain {

	public static Snake1 snake1;
	public static SnakeFrame window;

	public SnakeMain() {

	}

	public static void main(String[] args) {

		Scanner input = new Scanner(System.in);

		int opcao = 0;
		do {
			System.out.println("Menu");
			System.out.println("1-Para jogar com o agente persona");
			System.out.println("2-Para jogar com o agente IA/BFS/DFS");

			opcao = input.nextInt();
			System.out.print("\n");
			switch (opcao) {
			case 1:
				Snake1.agentpersona();
				break;
			case 2:
				System.out.println("B para BFS");
				System.out.println("D para DFS");

				window = new SnakeFrame();
				window.setVisible(true);
			
				break;

			default:
				System.out.println("Opção Inválida!");
				break;
			}
		} while (opcao != 0);

	}

}
