import java.io.File;
import java.util.Scanner;

public class Board {

	static final int EMPTY = 0;
	static final int ISLAND = 1;
	static final int PLAYER = 2;
	static final int PIRATE = 3;
	static final int PORTAL = 4;
	
	private int dimRow;
	private int dimCol;
	
	int[][] board;
	
	Board() {
		
		this.getBoard("Resources/Level1");
	}
	
	public void getBoard(String fileName) {
		
		int numRows = 0;
		int numCols = 0;
		int[][] matrix = null;

		try {

			File f = new File(fileName);
			Scanner s = new Scanner(f);

			numRows = s.nextInt();
			numCols = s.nextInt();

			matrix = new int[numRows][numCols];

			for(int i = 0; i < numRows; i++) {
				for(int j = 0; j < numCols; j++) {
					matrix[i][j] = s.nextInt();
				}
			}
			
			s.close();
			
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("ERROR: Board.getBoard() - ");
			System.out.println(ex.getMessage());
		}
		
		this.dimRow = numRows;
		this.dimCol = numCols;
		this.board = matrix;	
		
	}
	
	void toConsole() {
		
		for(int i = 0; i < this.dimRow; i++) {
			for(int j = 0; j < this.dimCol; j++) {
				System.out.print(this.board[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
		
	}
	
	void setPlayer(int row, int col) {
		this.board[row][col] = this.PLAYER;
	}

	void setPirate(int row, int col) {	
		this.board[row][col] = this.PIRATE;
	}
	
	void setIsland(int row, int col) {
		this.board[row][col] = this.ISLAND;
	}

	void removePlayer(int row, int col) {
		this.board[row][col] = this.EMPTY;
	}
	
	void removePirate(int row, int col) {
		this.board[row][col] = this.EMPTY;
	}
	
	public int getDimRow() {
		return dimRow;
	}
	
	public int getDimCol() {
		return dimCol;
	}
	
}
