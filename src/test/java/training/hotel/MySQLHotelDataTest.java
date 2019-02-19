package training.hotel;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v5_7_10;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.Sources;
import com.wix.mysql.config.MysqldConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MySQLHotelDataTest {
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
    public void findsRoomServiceHotels() {
        assertThat(new MySQLHotelData("jdbc:mysql://localhost:3306/" +
                "rdbms?user=mysql")
                .findAllWithRoomService().size()).isEqualTo(4);
    }

    @Test
    public void findsAllHotels() {
        assertThat(new MySQLHotelData("jdbc:mysql://localhost:3306/" +
                "rdbms?user=mysql")
                .findAll().size())
                .isEqualTo(8);
    }

    @Test
    public void findsRoomServiceHotelNames() {
        Set<String> expectedHotelNames = new HashSet<>();
        expectedHotelNames.add("Belmond Grand Hotel");
        expectedHotelNames.add("Longitude 131");
        expectedHotelNames.add("Ritz");
        expectedHotelNames.add("Tongabezi");
        assertThat(new MySQLHotelData("jdbc:mysql://localhost:3306/" +
                "rdbms?user=mysql")
                .findAllWithRoomService().stream()
        .map(Hotel::getName).collect(Collectors.toSet()))
                .isEqualTo(expectedHotelNames);
    }

    @After
    public void tearDown() {
        if (mysqld != null) {
            mysqld.stop();
        }
    }
}