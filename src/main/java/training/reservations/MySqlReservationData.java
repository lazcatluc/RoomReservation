package training.reservations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import training.building.Room;
import training.building.RoomTypes;
import training.hotel.Hotel;

public class MySqlReservationData implements ReservationData {

    private final String connectionString;

    public MySqlReservationData(String connectionString) {
        this.connectionString = connectionString;
    }


    @Override
    public Reservation bookARoom(Hotel hotel, Date from, Date to, String name, RoomTypes type) {

        try (Connection connection = DriverManager.getConnection(connectionString);
             PreparedStatement statement = connection.prepareStatement(
                     "insert into reservations values(null, ? , ?, ?, ?) "
             )) {
            Room room = findAvailableRoom(hotel, from, to, type, connection);
            if(room == null) {
                return null;
            }
            Reservation reservation = new Reservation();
            reservation.setGuest(name);
            reservation.setFrom(from);
            reservation.setTo(to);
            reservation.setRoomId(room.getId());
            statement.setString(1, name);
            statement.setDate(2, new java.sql.Date(from.getTime()));
            statement.setDate(3, new java.sql.Date(to.getTime()));
            statement.setLong(4, room.getId());
            statement.executeUpdate();



            return reservation;

        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }


    }

    private Room findAvailableRoom(Hotel hotel, Date from, Date to, RoomTypes type, Connection connection) throws SQLException {


        try (PreparedStatement statement = connection.prepareStatement(
                "select r2.* from rooms r2\n" +
                        "where r2.type_id = ? and r2.hotel_id = ?\n" +
                        "and r2.id not in\n" +
                        "( select r.id from rooms r\n" +
                        "inner join reservations re on r.id = re.room_id\n" +
                        "where  re.from_date < ? and re.to_date > ? ) limit 1")) {

            statement.setLong(1, type.getId());
            statement.setLong(2, hotel.getId());
            statement.setDate(3, new java.sql.Date(to.getTime()));
            statement.setDate(4, new java.sql.Date(from.getTime()));
            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    return null;
                }

                Room room = new Room();
                room.setId(resultSet.getLong("id"));
                room.setCapacity(resultSet.getInt("capacity"));
                room.setName(resultSet.getString("name"));
                room.setHotelId(resultSet.getInt("hotel_id"));
                room.setTypeId(resultSet.getInt("type_id"));
                room.setFloor(resultSet.getInt("floor"));
                room.setPrice(resultSet.getDouble("price"));

                return room;
            }
        }


    }
}
