
public class Player {
	static final int DOWN = 2;
	static final int LEFT = 4;
	static final int RIGHT = 6;
	static final int UP = 8;

	private int row;
	private int col;
	private int direction;
	private boolean alive;
	
	Player(int row, int col, int dir, boolean alive) {
		this.row = row;
		this.col = col;
		this.direction = dir; 
		this.alive = alive;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getCol() {
		return col;
	}
	
	public int getDirection() {
		return direction;
	}
	
	public void setRow(int row) {
		this.row = row;
	}
	
	public void setCol(int col) {
		this.col = col;
	}
	
	public void setDirection(int direction) {
		this.direction = direction;
	}
	
	public boolean getStatus() {
		return alive;
	}
	
	public void setStatus(boolean status) {
		this.alive = status;
	}
	
	void changePosition() {
		if (this.direction == this.UP) {
			this.row --;
		}
		if (this.direction == this.RIGHT) {
			this.col ++;
		}
		if (this.direction == this.LEFT) {
			this.col --;
		}
		if (this.direction == this.DOWN) {
			this.row ++;
		}
	}
	
}
