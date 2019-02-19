package training;

import java.util.Date;

import training.building.Room;

public class MyReservation {
	private String requester;
	private Room room;
	private Date startDate;
	private Date endDate;

	public String getRequester() {
		return requester;
	}

	public Room getRoom() {
		return room;
	}

	public Date getStartDate() {
		return startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setRequester(String requester) {
		this.requester = requester;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}
