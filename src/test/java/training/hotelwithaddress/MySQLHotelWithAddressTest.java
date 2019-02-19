package training.hotelwithaddress;

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
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import training.address.Address;
import training.hotel.Hotel;
import training.hotel.MySQLHotelData;
import training.hotel.MySQLHotelDataTest;

public class MySQLHotelWithAddressTest {
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
    public void findAllHotelsWithAddress() {
        assertThat(new MySQLHotelWithAddressData("jdbc:mysql://localhost:3306/" +
                "rdbms?user=mysql")
                .findAllHotelsWithAddress().size()).isEqualTo(8);
    }

    @Test
    public void findsAddressesForHotels() {
        Set<Address> expectedAddresses = new HashSet<>();

        expectedAddresses.add(new Address("France", "Paris", "2927, Donec Ave"));
        expectedAddresses.add(new Address("USA", "Rome", "5765, Ultrices Road, Kentucky"));
        expectedAddresses.add(new Address("USA", "Paris", "273-5124, Lectus Road, Illinois"));
        expectedAddresses.add(new Address("Italy", "Rome", "618-9647, Nunc Av."));
        expectedAddresses.add(new Address("Italy", "Rome", "9282, Semper Street"));
        expectedAddresses.add(new Address("Italy", "Verona", "8631, Tellus Street"));
        expectedAddresses.add(new Address("France", "Paris", "7497, Euismod Ave"));
        expectedAddresses.add(new Address("Italy", "Palermo", "963-3851, Est. Rd."));

        Assertions.assertThat(new MySQLHotelWithAddressData("jdbc:mysql://localhost:3306/" +
                "rdbms?user=mysql")
                .findAllHotelsWithAddress().stream().map(HotelWithAddress::getAddress)
                .collect(Collectors.toSet()))
                .isEqualTo(expectedAddresses);
    }

    @Test
    public void findsHotelNamesWithAddresses() {
        Set<String> expectedHotelNames = new HashSet<>();
        expectedHotelNames.add("Belmond Grand Hotel");
        expectedHotelNames.add("Longitude 131");
        expectedHotelNames.add("Ritz");
        expectedHotelNames.add("Tongabezi");
        expectedHotelNames.add("Masseria");
        expectedHotelNames.add("Hotel Escondido");
        expectedHotelNames.add("Blackberry Farm");
        expectedHotelNames.add("Le Grand Bellevue");
        Assertions.assertThat(new MySQLHotelWithAddressData("jdbc:mysql://localhost:3306/" +
                "rdbms?user=mysql")
                .findAllHotelsWithAddress().stream().map(HotelWithAddress::getHotel)
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
