package training.resources;

public class Blackboard extends BaseEntity {
	
	private boolean smartBoard;
	
	public Blackboard(boolean isSmartBoard) {
		this.smartBoard = isSmartBoard;
	}
	
	public boolean isSmartBoard() {
		return smartBoard;
	}

	public void setSmartBoard(boolean smartBoard) {
		this.smartBoard = smartBoard;
	}
	
}
