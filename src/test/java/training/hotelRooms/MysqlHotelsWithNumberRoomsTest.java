package training.hotelRooms;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v5_7_10;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.Sources;
import com.wix.mysql.config.MysqldConfig;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import training.hotel.Hotel;
import training.hotel.MySQLHotelDataTest;
import training.hotelwithaddress.HotelWithAddress;
import training.hotelwithaddress.MySQLHotelWithAddressData;

public class MysqlHotelsWithNumberRoomsTest {
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

    @After
    public void tearDown() {
        if (mysqld != null) {
            mysqld.stop();
        }
    }

    @Test
    public void testNumberRooms() {
        List<HotelsWithNumberRooms> myList = new ArrayList<>();
        myList.add(new HotelsWithNumberRooms("Tongabezi",12));
        myList.add(new HotelsWithNumberRooms("Longitude 131",8));
        myList.add(new HotelsWithNumberRooms("Le Grand Bellevue",8));
        myList.add(new HotelsWithNumberRooms("Belmond Grand Hotel",8));

        Assertions.assertThat(new MysqlHotelsWithNumberRooms("jdbc:mysql://localhost:3306/" +
                "rdbms?user=mysql")
                .getHotelsAndNumberRooms(8))
                .isEqualTo(myList);

    }

}