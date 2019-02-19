package training.hotel;

import java.util.List;
import training.SessionFactoryProvider;

public class HQLHotelData implements HotelData {
    private final SessionFactoryProvider sessionFactoryProvider;

    public HQLHotelData(SessionFactoryProvider sessionFactoryProvider) {
        this.sessionFactoryProvider = sessionFactoryProvider;
    }

    @Override
    public List<Hotel> findAll() {
        return sessionFactoryProvider.getSession()
                .createQuery("select h from Hotel h " +
                        "left join fetch h.address", Hotel.class)
                .list();
    }

    @Override
    public List<Hotel> findAllWithRoomService() {

        return sessionFactoryProvider.getSession()
                .createQuery("select h from Hotel h " +
                        "join h.rooms r " +
                        "join r.amenities a "+
                        "where a.name = 'Room Service' " +
                        "group by h.name " +
                        "order by h.name", Hotel.class)
                .list();

    }
}
