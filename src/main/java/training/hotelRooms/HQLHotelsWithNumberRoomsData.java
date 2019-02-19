package training.hotelRooms;

import java.util.List;
import org.hibernate.query.Query;
import training.SessionFactoryProvider;

public class HQLHotelsWithNumberRoomsData implements HotelsWithNumbersRoomsData {
    private final SessionFactoryProvider sessionFactoryProvider;

    public HQLHotelsWithNumberRoomsData(SessionFactoryProvider sessionFactoryProvider) {
        this.sessionFactoryProvider = sessionFactoryProvider;
    }

    @Override
    public List<HotelsWithNumberRooms> getHotelsAndNumberRooms(long biggerThanNumberRooms) {
        Query<HotelsWithNumberRooms> query = sessionFactoryProvider.getSession().createQuery(
            "select new training.hotelRooms.HotelsWithNumberRooms(h.name, count(r)) " +
                    "from Hotel h join h.rooms r " +
                    "group by h having count(r) >= :biggerThanNumberRooms",
                HotelsWithNumberRooms.class);
        query.setParameter("biggerThanNumberRooms", biggerThanNumberRooms);
        return query.list();
    }
}
