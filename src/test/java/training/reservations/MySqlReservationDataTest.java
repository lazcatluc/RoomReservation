package training.reservations;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v5_7_10;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.Sources;
import com.wix.mysql.config.MysqldConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import training.building.RoomTypes;
import training.hotel.Hotel;
import training.hotel.MySQLHotelData;
import training.hotel.MySQLHotelDataTest;

public class MySqlReservationDataTest {

    private EmbeddedMysql mysqld;

    @Before
    public void setUp() {
        MysqldConfig config = aMysqldConfig(v5_7_10)
                .withPort(3306).withServerVariable("bind-address", "127.0.0.1")
                .withUser("mysql", "")
                .build();

        mysqld = anEmbeddedMysql(config)
                .addSchema("rdbms", Sources.fromURL(MySQLHotelDataTest.class
                        .getResource("/init.sql")))
                .start();
    }

    @Test
    public void oneRoomAvailablePerHotel() {

        RoomTypes roomTypes = new RoomTypes();
        roomTypes.setId(1L);
        roomTypes.setType("Single");

        Set<String> expectedHotelNames = new HashSet<>();
        expectedHotelNames.add("Belmond Grand Hotel");
        expectedHotelNames.add("Ritz");
        expectedHotelNames.add("Tongabezi");

        Set<String> actualHotelNames = new HashSet<>();

        new MySQLHotelData("jdbc:mysql://localhost:3306/" +
                "rdbms?user=mysql")
                .findAllWithRoomService().stream()
                .forEach(hotel -> {
                    Reservation reservation = new MySqlReservationData("jdbc:mysql://localhost:3306/" +
                            "rdbms?user=mysql")
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
        if (mysqld != null) {
            mysqld.stop();
        }
    }

}