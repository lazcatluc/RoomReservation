package training.reservations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import org.hibernate.query.Query;
import training.SessionFactoryProvider;
import training.building.Room;
import training.building.RoomTypes;
import training.hotel.Hotel;

public class HQLReservationData implements ReservationData{

    private final SessionFactoryProvider sessionFactoryProvider;

    public HQLReservationData(SessionFactoryProvider sessionFactoryProvider) {
        this.sessionFactoryProvider = sessionFactoryProvider;
    }

    @Override
    public Reservation bookARoom(Hotel hotel, Date from, Date to, String name, RoomTypes type) {

        Room room = findAvailableRoom(hotel, from, to, type);
        if(room == null) {
            return null;
        }
        Reservation reservation = new Reservation();
        reservation.setGuest(name);
        reservation.setFrom(from);
        reservation.setTo(to);
        reservation.setRoom(room);

        sessionFactoryProvider.getSession().save(reservation);

        return reservation;
    }

    public Room findAvailableRoom(Hotel hotel, Date from, Date to, RoomTypes type){

        Query<Room> query = sessionFactoryProvider.getSession()
                .createQuery("select r from Room r " +
                        "where r.roomTypes = :roomTypes and r.hotel =:hotel" +
                        " and r not in (select r1 from Room r1 " +
                        "join r1.reservations re " +
                        "where re.from < :toDate and re.to > :fromDate)", Room.class);


        query.setParameter("roomTypes",type);
        query.setParameter("hotel",hotel);
        query.setParameter("toDate",to);
        query.setParameter("fromDate",from);

        return query.setMaxResults(1).uniqueResult();

    }
}
