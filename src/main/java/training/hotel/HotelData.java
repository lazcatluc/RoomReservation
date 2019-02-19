package training.hotel;

import java.util.List;

public interface HotelData {
    List<Hotel> findAll();
    List<Hotel> findAllWithRoomService();
}
