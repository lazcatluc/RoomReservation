package training.hotelRooms;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MysqlHotelsWithNumberRooms implements HotelsWithNumbersRoomsData {
    private final String connectionString;

    public MysqlHotelsWithNumberRooms(String connectionString) {
        this.connectionString = connectionString;
    }

    @Override
    public List<HotelsWithNumberRooms> getHotelsAndNumberRooms(long biggerThanNumberRooms) {
        try (Connection connection = DriverManager.getConnection(connectionString);
             PreparedStatement statement = connection.prepareStatement(
                     "select h.name ,count(r.id)as nr from hotels h join " +
                             " rooms r on h.id=r.hotel_id group by h.id " +
                             "having nr >=?")) {
            statement.setLong(1, biggerThanNumberRooms);

            try (ResultSet resultSet = statement.executeQuery()) {
                List<HotelsWithNumberRooms> hotelsWithNumberRooms = new ArrayList<>();
                while (resultSet.next()) {
                    HotelsWithNumberRooms h1 = new HotelsWithNumberRooms(
                            resultSet.getString("name"),
                            resultSet.getLong("nr"));

                    hotelsWithNumberRooms.add(h1);

                }
                return  hotelsWithNumberRooms;
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }

    }

}
