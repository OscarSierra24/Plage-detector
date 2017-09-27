package Ruleta3;

import javax.swing.JFrame;

public class Window extends JFrame{
	private Panel panel;
	public Window(){
		super("DA detector");
		Panel panel=new Panel();
		add(panel);
		pack();
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	public static void main(String[] args){
		Window window=new Window();
	}
}
