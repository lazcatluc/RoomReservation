package training.hotel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MySQLHotelData implements HotelData {
    private static final Logger LOGGER = LoggerFactory.getLogger(MySQLHotelData.class);
    private final String connectionString;

    public MySQLHotelData(String connectionString) {
        this.connectionString = connectionString;
    }

    @Override
    public List<Hotel> findAll() {
        try (Connection connection = DriverManager.getConnection(connectionString);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("select * from hotels")) {
            List<Hotel> hotels = new ArrayList<>();
            writeMetaData(resultSet);
            while (resultSet.next()) {
                Hotel hotel = new Hotel();
                hotel.setAddressId(resultSet.getLong("address_id"));
                hotel.setId(resultSet.getLong("id"));
                hotel.setName(resultSet.getString("name"));
                hotel.setStars(resultSet.getInt("stars"));
                hotels.add(hotel);
            }
            return hotels;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public List<Hotel> findAllWithRoomService() {
        try (Connection connection = DriverManager.getConnection(connectionString);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("select h.*\n" +
                     "\n" +
                     "from hotels h JOIN rooms r on h.id = r.hotel_id\n" +
                     "\n" +
                     "JOIN room_amenities ra on r.id = ra.room_id\n" +
                     "\n" +
                     "JOIN amenities a on ra.amenity_id = a .id\n" +
                     "\n" +
                     "where a.name = \"Room Service\"\n" +
                     "\n" +
                     "group by h.name\n" +
                     "\n" +
                     "order by h.name;  ")) {
            List<Hotel> hotels = new ArrayList<>();
            writeMetaData(resultSet);
            while (resultSet.next()) {
                Hotel hotel = new Hotel();
                hotel.setAddressId(resultSet.getLong("address_id"));
                hotel.setId(resultSet.getLong("id"));
                hotel.setName(resultSet.getString("name"));
                hotel.setStars(resultSet.getInt("stars"));
                hotels.add(hotel);
            }
            return hotels;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    private void writeMetaData(ResultSet resultSet) throws SQLException {
        LOGGER.info("Table: {}", resultSet.getMetaData().getTableName(1));
        LOGGER.info("The columns in the table are: ");
        for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
            LOGGER.info("Column {}: {} ", i, resultSet.getMetaData().getColumnName(i));
        }
    }
}

