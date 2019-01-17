import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JOptionPane;

class Node {
	private int row;
	private int col;
	private int dist;
	
	Node(int row, int col, int dist) {
		this.row = row;
		this.col = col;
		this.dist = dist;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getCol() {
		return col;
	}
	
	public int getDist() {
		return dist;
	}
	
}

public class Game {

	static final int NUM_OF_PIRATES = 4;
	
	static final int PLAYER = 0;
	static final int PIRATE = 1;
	
	static final int DOWN = 2;
	static final int LEFT = 4;
	static final int RIGHT = 6;
	static final int UP = 8;
	
	static final int UP_LEFT = 7;
	static final int UP_RIGHT = 9;
	static final int DOWN_LEFT = 3;
	static final int DOWN_RIGHT = 5;
	
	
	private int points;
	
	
	Board board;
	Player player;
	Pirate[] pirates;
	
	public void initGUI() {
		this.points = 0;
		board = new Board();
		player = new Player(5,3,player.DOWN,true);
		board.setPlayer(player.getRow(), player.getCol());
		
		pirates = new Pirate[this.NUM_OF_PIRATES];
		for(int i = 0; i < this.NUM_OF_PIRATES; i++) {
			pirates[i] = new Pirate(2*i+1,3*i,pirates[i].UP,true);
			board.setPirate(pirates[i].getRow(), pirates[i].getCol());
		}
		
	}
	
	public void initConsole() {
		
		initGUI();
		
		board.toConsole();
		
		Scanner reader = new Scanner(System.in); 
		
		while(!isEndGame()) {
			System.out.println("Trenutni broj bodova : " + this.points);
			System.out.println("Dole: 2, Lijevo: 4, Desno: 6, Gore: 8 ");
			System.out.println("Unesite potez: ");
			int dir = reader.nextInt();
			
			while(dir != 2 && dir != 4 && dir != 6 && dir != 8) {
				System.out.println("Pogresan potez!");
				System.out.println("Unesite pravilan potez : ");
				dir = reader.nextInt();
			}
			
			player.setDirection(dir);
			movePlayer();
			if(isEndGame()) {
				break;
			}
			
			movePirates();
			board.toConsole();		
		}
		
		reader.close();
	}
	
	public void movePlayer() {
		int playerRow = player.getRow();
		int playerCol = player.getCol();
		int direction = player.getDirection();

		if (checkMove(playerRow, playerCol, direction,PLAYER)) {
			board.removePlayer(playerRow, playerCol);
			player.changePosition();
			int row = player.getRow();
			int col = player.getCol();
			if(board.board[row][col] == board.PIRATE) {
				player.setStatus(false);
			}else {
				board.setPlayer(player.getRow(), player.getCol());
			}
			
		}
	}
	
	boolean checkMove(int row, int col, int dir, int type) {
		int dimRow = board.getDimRow();
		int dimCol = board.getDimCol();

		if (row == 0 && dir == UP) {
			return false;
		}else if (col == 0 && dir == LEFT) {
			return false;
		}else if (row == dimRow - 1 && dir == DOWN) {
			return false;
		}else if (col == dimCol - 1 && dir == RIGHT) {
			return false;
		}else if(dir == UP_LEFT && (row == 0 || col == 0)) {
			return false;
		}else if(dir == UP_RIGHT && (col == dimCol-1 || row == 0)) {
			return false;
		}else if(dir == DOWN_LEFT && (col == 0 || row == dimRow-1)) {
			return false;
		}else if(dir ==DOWN_RIGHT && (col == dimCol-1 || row == dimRow-1)) {
			return false;
		}

		if (dir == UP) {
			row = row - 1;
		}else if (dir == LEFT) {
			col = col - 1;
		}else if (dir == DOWN) {
			row = row + 1;
		}else if (dir == RIGHT) {
			col = col + 1;
		}else if(dir == UP_LEFT) {
			row = row - 1;
			col = col - 1;
		}else if(dir == UP_RIGHT) {
			row = row - 1;
			col = col + 1;
		}else if(dir == DOWN_LEFT) {
			row = row + 1;
			col = col - 1;
		}else if(dir == DOWN_RIGHT) {
			row = row + 1;
			col = col + 1;
		}
			
			
		if (type == PLAYER && board.board[row][col] == board.ISLAND) {
			return false;
		}
		
		
		if (type == PIRATE && board.board[row][col] == board.PORTAL) {
			return false;
		}

		return true;
	}
	
	
	int minDistance(int row,int col) {
		
		boolean[][] visited = new boolean[board.getDimRow()][board.getDimCol()];
		if(row >= board.getDimRow() || row < 0 || col >= board.getDimCol() || col < 0) {
			return Integer.MAX_VALUE;
		}
		Node source = new Node(row,col,0);
		
		for(int i = 0; i < board.getDimRow(); i++) {
			for(int j = 0; j < board.getDimCol(); j++) {
				if(board.board[i][j] == 4) {
					visited[i][j] = true;
				}else {
					visited[i][j] = false;
				}
			}
		}
		
		Queue<Node> q = new LinkedList<>(); 
		q.add(source);
		visited[row][col] = true;
		
		while(q.size() != 0) {
			Node temp = q.remove(); 
			
			if(board.board[temp.getRow()][temp.getCol()] == 2) {
				return temp.getDist();
			}
			
			// UP
			if(temp.getRow()-1 >= 0 && visited[temp.getRow()-1][temp.getCol()] == false) {
				q.add(new Node(temp.getRow()-1,temp.getCol(),temp.getDist()+1));
				visited[temp.getRow()-1][temp.getCol()] = true;
			}
			
			// DOWN
			if(temp.getRow()+1 < board.getDimRow() && visited[temp.getRow()+1][temp.getCol()] == false) {
				q.add(new Node(temp.getRow()+1,temp.getCol(),temp.getDist()+1));
				visited[temp.getRow()+1][temp.getCol()] = true;
			}
			
			// LEFT
			if(temp.getCol()-1 >= 0 && visited[temp.getRow()][temp.getCol()-1] == false) {
				q.add(new Node(temp.getRow(),temp.getCol()-1,temp.getDist()+1));
				visited[temp.getRow()][temp.getCol()-1] = true;
			}
			
			// RIGHT
			if(temp.getCol()+1 < board.getDimCol() && visited[temp.getRow()][temp.getCol()+1] == false) {
				q.add(new Node(temp.getRow(),temp.getCol()+1,temp.getDist()+1));
				visited[temp.getRow()][temp.getCol()+1] = true;
			}
			
			// UP_LEFT
			if((temp.getRow()-1 >= 0 && temp.getCol()-1 >= 0) && visited[temp.getRow()-1][temp.getCol()-1] == false) {
				q.add(new Node(temp.getRow()-1,temp.getCol()-1,temp.getDist()+1));
				visited[temp.getRow()-1][temp.getCol()-1] = true;
			}
			
			// UP_RIGHT
			if((temp.getRow()-1 >= 0 && temp.getCol()+1 < board.getDimCol()) && visited[temp.getRow()-1][temp.getCol()+1] == false ) {
				q.add(new Node(temp.getRow()-1,temp.getCol()+1,temp.getDist()+1));
				visited[temp.getRow()-1][temp.getCol()+1] = true;
			}
			
			// DOWN_LEFT
			if((temp.getRow()+1 < board.getDimRow() && temp.getCol()-1 >= 0) && visited[temp.getRow()+1][temp.getCol()-1] == false) {
				q.add(new Node(temp.getRow()+1,temp.getCol()-1,temp.getDist()+1));
				visited[temp.getRow()+1][temp.getCol()-1] = true;
			}
			
			// DOWN_RIGHT
			if((temp.getRow()+1 < board.getDimRow() && temp.getCol()+1 < board.getDimCol()) && visited[temp.getRow()+1][temp.getCol()+1]) {
				q.add(new Node(temp.getRow()+1,temp.getCol()+1,temp.getDist()+1));
				visited[temp.getRow()+1][temp.getCol()+1] = true;
			}
			
		}
		
		return Integer.MAX_VALUE;
		
	}
	
	int newDirection(int row,int col) {
		int[] minDist = new int[8];
		minDist[0] = minDistance(row+1,col); // DOWN
		minDist[1] = minDistance(row-1,col); // UP
		minDist[2] = minDistance(row,col+1); // RIGHT
		minDist[3] = minDistance(row,col-1); // LEFT
		
		minDist[4] = minDistance(row-1,col-1); // UP_LEFT
		minDist[5] = minDistance(row-1,col+1); // UP_RIGHT
		minDist[6] = minDistance(row+1,col-1);  // DOWN_LEFT
		minDist[7] = minDistance(row+1,col+1);  // DOWN_RIGHT
		
		int min = minDist[0];
		int index = 0;
		
		for(int i = 1; i < 8; i++) {
			if(minDist[i] < min) {
				min = minDist[i];
				index = i;
			}
		}
		
		
		index = getRandom(index);
		
		if(index == 0) {
			return DOWN;
		}else if(index == 1) {
			return UP;
		}else if(index == 2) {
			return RIGHT;
		}else if(index == 3) {
			return LEFT;
		}else if(index == 4) {
			return UP_LEFT;
		}else if(index == 5) {
			return UP_RIGHT;
		}else if(index == 6) {
			return DOWN_LEFT;
		}else {
			return DOWN_RIGHT;
		}
		
	}
	
	int getRandom(int number) {
		if (Math.random() < 0.75) {
			return number;
			 
		}
		
		Random rand = new Random(); 
		int rand_int = rand.nextInt(1000); 
		return rand_int % 8;
	}
	
	private void AI() {
		
		for (int i = 0; i < this.NUM_OF_PIRATES; i++) {
			if(pirates[i].getStatus() == true) {
				while (true) {
					int pirateRow = pirates[i].getRow();
					int pirateCol = pirates[i].getCol();
					pirates[i].setDirection(newDirection(pirates[i].getRow(),pirates[i].getCol()));
					
					int direction = pirates[i].getDirection();

					if (checkMove(pirateRow, pirateCol, direction,PIRATE)) {
						break;
					}
				}	
			}	
		}
	}
	public void movePirates() {
		AI();
		for(int i = 0; i < this.NUM_OF_PIRATES; i++) {
			if(pirates[i].getStatus() == true) {
				int pirateRow = pirates[i].getRow();
				int pirateCol = pirates[i].getCol();
				board.removePirate(pirateRow, pirateCol );
				pirates[i].move();	
			}
		}
		
		for (int i = 0; i < this.NUM_OF_PIRATES; i++) {
			
			for(int j = 0; j < this.NUM_OF_PIRATES; j++) {
				if(i == j) {
					continue;
				}
				if(pirates[i].getStatus() == true && pirates[j].getStatus() == true) {
					if(pirates[i].getRow() == pirates[j].getRow() && pirates[i].getCol() == pirates[j].getCol()) {
						pirates[i].setStatus(false);
						pirates[j].setStatus(false);
						board.setIsland(pirates[i].getRow(), pirates[i].getCol());
						this.points += 40;
					}
				}
			}	
		}
		
		for(int i = 0; i < this.NUM_OF_PIRATES; i++) {
			if(pirates[i].getStatus() == true) {
				int pirateRow = pirates[i].getRow();
				int pirateCol = pirates[i].getCol();
				
			
				if(board.board[pirateRow][pirateCol] == board.PLAYER) {
					board.removePlayer(pirateRow, pirateCol);
					player.setStatus(false);
					board.setPirate(pirateRow, pirateCol);
				}
				
		
				if(board.board[pirateRow][pirateCol] == board.ISLAND) {
					pirates[i].setStatus(false);
					this.points += 20;
				}
				
				if(board.board[pirateRow][pirateCol] == board.EMPTY) {
					board.setPirate(pirateRow, pirateCol);
				}
					
			}
		}
		
	}
	
	boolean isEndGame() {
		if(player.getStatus() == false) {
			System.out.println("Igrac je izgubio!");
			System.out.println("Ostvareni broj bodova : " + this.points);
			return true;
		}
		
		int playerRow = player.getRow();
		int playerCol = player.getCol();
		int n = board.getDimRow();
		int m = board.getDimCol();
		
		boolean playerWin = false;
		
		if(playerRow == 0 && playerCol == 0) {
			playerWin = true;
		}else if(playerRow == 0 && playerCol == m-1) {
			playerWin = true;
		}else if(playerRow == n-1 && playerCol == 0) {
			playerWin = true;
		}else if(playerRow == n-1 && playerCol == m-1) {
			playerWin = true;
		}
		
		if(playerWin) {
			System.out.println("Igrac je pobjedio!");
			this.points += 100;
			System.out.println("Ostvareni broj bodova : " + this.points);
			return true;
		}
		
		
		boolean piratesAreAlive = false;
		for(int i = 0; i < this.NUM_OF_PIRATES; i++) {
			if(pirates[i].getStatus() == true) {
				piratesAreAlive = true;
				break;
			}
		}
		
		if(!piratesAreAlive) {
			System.out.println("Igrac je pobijedio!");
			System.out.println("Ostvareni broj bodova : " + this.points);
			return true;
		}
		
		
		return false;
	}
	
	boolean isEndGameGUI() {
		if(player.getStatus() == false) {
			String s = "Igrac je izgubio\n" + "Ostvareni broj bodova : " + this.points;
			JOptionPane.showMessageDialog(null, s);
			return true;
		}
		
		int playerRow = player.getRow();
		int playerCol = player.getCol();
		int n = board.getDimRow();
		int m = board.getDimCol();
		
		boolean playerWin = false;
		
		if(playerRow == 0 && playerCol == 0) {
			playerWin = true;
		}else if(playerRow == 0 && playerCol == m-1) {
			playerWin = true;
		}else if(playerRow == n-1 && playerCol == 0) {
			playerWin = true;
		}else if(playerRow == n-1 && playerCol == m-1) {
			playerWin = true;
		}
		
		if(playerWin) {
			this.points += 100;
			String s = "Igrac je pobjedio\n" + "Ostvareni broj bodova : " + this.points;
			JOptionPane.showMessageDialog(null, s);
			return true;
		}
		
		
		boolean piratesAreAlive = false;
		for(int i = 0; i < this.NUM_OF_PIRATES; i++) {
			if(pirates[i].getStatus() == true) {
				piratesAreAlive = true;
				break;
			}
		}
		
		if(!piratesAreAlive) {
			String s = "Igrac je pobjedio\n" + "Ostvareni broj bodova : " + this.points;
			JOptionPane.showMessageDialog(null, s);
			return true;
		}
		
		return false;
	}
	
}
