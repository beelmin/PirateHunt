import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Label;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GUI {
	private Game game;
	public JFrame window;
	private JPanel menu;
	private JPanel playground;
	private JPanel main;
	private JButton[][] field;
	private JButton newGame;
	private JButton endGame;
	
	
	GUI(){
		game = new Game();
		game.initGUI();
		
		
		this.window = new JFrame("Pirate Hunt");
		this.menu = new JPanel();
		this.playground = new JPanel(new GridLayout(game.board.getDimRow(),game.board.getDimCol()));
		this.main = new JPanel(new BorderLayout());
		this.window.setContentPane(this.main);
		
		BufferedImage logo;
		try {
			logo = ImageIO.read(new File("Resources/logo.jpg"));
			JLabel logoLabel = new JLabel(new ImageIcon(logo));
			this.menu.add(logoLabel);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		Label l1 = new Label("RULES : ");
		Label l2 = new Label("Player can go right,left,up or down");
		Label l3 = new Label("Pirates can go right,left,up,down,");
		Label l4 = new Label("up left, up right, down left or down right");
		Label l5 = new Label("Portal fields are win fields for player");
		Label l6 = new Label("If pirate hit the island, he disappears");
		Label l7 = new Label("If two pirates collide, the island appears");
		Label l8 = new Label("If there are no more pirates on field, player win");
		this.menu.add(l1);
		this.menu.add(l2);
		this.menu.add(l3);
		this.menu.add(l4);
		this.menu.add(l5);
		this.menu.add(l6);
		this.menu.add(l7);
		this.menu.add(l8);
		
		this.newGame = new JButton("New Game");
		
		this.newGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				game = new Game();
				game.initGUI();
				setBoard();

			}
		});
		
		this.newGame.setFocusable(false);
		this.menu.add(newGame);
		
		this.endGame = new JButton("End Game");
		
		this.endGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		
		this.endGame.setFocusable(false);
		this.menu.add(this.endGame);
		
		BufferedImage logo2;
		try {
			logo2 = ImageIO.read(new File("Resources/logo2.jpg"));
			JLabel logoLabel2 = new JLabel(new ImageIcon(logo2));
			this.menu.add(logoLabel2);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		this.playground.requestFocusInWindow();
		
		
		this.menu.setPreferredSize(new Dimension(300,800));
		this.playground.setPreferredSize(new Dimension(700,800));
		
		
		
		field = new JButton[game.board.getDimRow()][game.board.getDimCol()];
		
		
		for(int i = 0; i < game.board.getDimRow(); i++) {
			for(int j = 0; j < game.board.getDimCol(); j++) {
				field[i][j] = new JButton();
				ImageIcon photo = new ImageIcon("Resources/ocean.png");
				field[i][j].setIcon(photo);
				field[i][j].setFocusable(false);
				
				this.playground.add(field[i][j]);
			}
		}
		
		
		this.window.addKeyListener(new KeyList());
		
		this.main.add(this.menu,BorderLayout.LINE_START);
		this.main.add(this.playground,BorderLayout.CENTER);
		this.window.setSize(1000,800);
		this.window.setVisible(true);
		this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	
	public void setBoard() {
		
		for(int i = 0; i < game.board.getDimRow(); i++) {
			for(int j = 0; j < game.board.getDimCol(); j++) {
				
				int f = game.board.board[i][j];
				ImageIcon photo;
				
				if (f == game.board.ISLAND) {
					photo = new ImageIcon("Resources/island.jpg");
					
				} else if (f == game.board.PIRATE) {
					photo = new ImageIcon("Resources/pirate.png");
					
				} else if (f == game.board.PORTAL) {
					photo = new ImageIcon("Resources/portal.jpg");
					
				} else if (f == game.board.PLAYER) {
					photo = new ImageIcon("Resources/player.jpg");
					
				} else {
					photo = new ImageIcon("Resources/ocean.png");
					
				}

				field[i][j].setIcon(photo);
				
				
			}
		}
		

	}
	
	public static void main(String[] args) {
		GUI g1 = new GUI();
		g1.setBoard();
			
	}
	
	public class KeyList implements KeyListener {

		@Override
		public void keyPressed(KeyEvent e) {
			
			
			if (e.getKeyCode() == 37) {
				
				game.player.setDirection(game.player.LEFT);
				//game.movePlayer();
				
			}

			if (e.getKeyCode() == 38) {
				game.player.setDirection(game.player.UP);
				//game.movePlayer();
				
			}

			if (e.getKeyCode() == 39) {
				game.player.setDirection(game.player.RIGHT);
				//game.movePlayer();
				
			}

			if (e.getKeyCode() == 40) {
				game.player.setDirection(game.player.DOWN);
				//game.movePlayer();
				
			}
			
			game.movePlayer();
			game.movePirates();
			setBoard();
			if(game.isEndGameGUI());
			
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
		
}
