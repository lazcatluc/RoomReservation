package training.reservations;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v5_7_10;
import static org.assertj.core.api.Assertions.assertThat;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.Sources;
import com.wix.mysql.config.MysqldConfig;
import org.hibernate.query.Query;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import training.SessionFactoryProvider;
import training.building.Room;
import training.building.RoomTypes;
import training.hotel.HQLHotelData;
import training.hotel.HQLHotelDataTest;
import training.hotel.Hotel;
import training.hotel.MySQLHotelData;

public class HQLReservationDataTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(HQLReservationDataTest.class);

    private EmbeddedMysql mysqld;
    private SessionFactoryProvider sessionFactoryProvider;

    @Before
    public void setUp() {
        MysqldConfig config = aMysqldConfig(v5_7_10)
                .withPort(3306).withServerVariable("bind-address", "127.0.0.1")
                .withUser("mysql", "")
                .build();

        mysqld = anEmbeddedMysql(config)
                .addSchema("rdbms", Sources.fromURL(HQLHotelDataTest.class
                        .getResource("/init.sql")))
                .start();
        sessionFactoryProvider = new SessionFactoryProvider("test-hibernate.cfg.xml");
        sessionFactoryProvider.getSession().beginTransaction();

    }

    @Test
    public void findAvailableRoom() {

        RoomTypes roomType = new RoomTypes();
        roomType.setId(1L);
        roomType.setType("Single");

        Query<Hotel> query = sessionFactoryProvider.getSession()
                .createQuery("select h from Hotel h " +
                        "left join fetch h.address " +
                        "where h.name = 'Ritz'", Hotel.class);

        Hotel myHotel = query.uniqueResult();

        HQLReservationData hqlReservationData = new HQLReservationData(sessionFactoryProvider);
        Room availableRoom = hqlReservationData.findAvailableRoom(myHotel, format("12-05-2018"),
                format("14-05-2018"), roomType);
        assertThat(availableRoom).isNotNull();

        LOGGER.info("{}",availableRoom);

    }

    @Test
    public void bookARoom(){

        RoomTypes roomTypes = new RoomTypes();
        roomTypes.setId(1L);
        roomTypes.setType("Single");

        Set<String> expectedHotelNames = new HashSet<>();
        expectedHotelNames.add("Belmond Grand Hotel");
        expectedHotelNames.add("Ritz");
        expectedHotelNames.add("Tongabezi");

        Set<String> actualHotelNames = new HashSet<>();

        new HQLHotelData(sessionFactoryProvider)
                .findAllWithRoomService().stream()
                .forEach(hotel -> {
                    Reservation reservation = new HQLReservationData(sessionFactoryProvider)
                            .bookARoom(hotel, format("12-05-2018"),
                                    format("14-05-2018"), "Ionescu",
                                    roomTypes);

                    if(reservation != null)
                        actualHotelNames.add(hotel.getName());
                });

        assertThat(actualHotelNames).isEqualTo(expectedHotelNames);
    }


    private Date format(String s) {

        try {
            return new SimpleDateFormat("dd-MM-yyyy").parse(s);
        } catch (ParseException e) {
            throw new IllegalStateException(e);
        }
    }

    @After
    public void tearDown() {
        if (sessionFactoryProvider != null) {
            sessionFactoryProvider.getSession().close();
            sessionFactoryProvider.shutdown();
        }
        if (mysqld != null) {
            mysqld.stop();
        }
    }
}