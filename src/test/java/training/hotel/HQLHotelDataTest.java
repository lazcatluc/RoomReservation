package training.hotel;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v5_7_10;
import static org.assertj.core.api.Assertions.assertThat;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.Sources;
import com.wix.mysql.config.MysqldConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import training.SessionFactoryProvider;

public class HQLHotelDataTest {
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
    public void findsRoomServiceHotels() {

        assertThat(new HQLHotelData(sessionFactoryProvider)
                .findAllWithRoomService().size()).isEqualTo(4);
    }

    @Test
    public void findsAllHotels() {
        assertThat(new HQLHotelData(sessionFactoryProvider)
                .findAll().size())
                .isEqualTo(8);
    }

    @Test
    @Ignore
    public void findsRoomServiceHotelNames() {

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