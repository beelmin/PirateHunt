
public class Pirate {
	
	static final int DOWN = 2;
	static final int LEFT = 4;
	static final int RIGHT = 6;
	static final int UP = 8;
	
	static final int UP_LEFT = 7;
	static final int UP_RIGHT = 9;
	static final int DOWN_LEFT = 3;
	static final int DOWN_RIGHT = 5;

	private int row;
	private int col;
	private int direction;
	private boolean alive;
	
	Pirate(int row, int col, int dir, boolean alive) {
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
	
	public void setDirection(int direction) {
		this.direction = direction;
	}
	
	public boolean getStatus() {
		return alive;
	}
	
	public void setStatus(boolean status) {
		this.alive = status;
	}
	
	void move() {
		if (this.direction == this.UP) {
			this.row--;
		}
		if (this.direction == this.RIGHT) {
			this.col++;
		}
		if (this.direction == this.LEFT) {
			this.col--;
		}
		if (this.direction == this.DOWN) {
			this.row++;
		}
		
		
		if(this.direction == this.UP_LEFT) {
			this.row--;
			this.col--;
			
		}
		if(this.direction == this.UP_RIGHT) {
			this.row--;
			this.col++;
		}
		if(this.direction == this.DOWN_LEFT) {
			this.row++;
			this.col--;
		}
		if(this.direction == this.DOWN_RIGHT) {
			this.row++;
			this.col++;
		}
	}

}
