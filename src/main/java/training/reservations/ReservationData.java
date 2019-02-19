package training.reservations;

import java.util.Date;
import training.building.RoomTypes;
import training.hotel.Hotel;

public interface ReservationData {

    Reservation bookARoom(Hotel hotel, Date from, Date to, String name, RoomTypes type);


}
