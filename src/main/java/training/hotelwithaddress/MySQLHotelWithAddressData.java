package training.hotelwithaddress;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import training.address.Address;
import training.hotel.Hotel;

public class MySQLHotelWithAddressData implements HotelWithAddressData{

    private static final Logger LOGGER = LoggerFactory.getLogger(MySQLHotelWithAddressData.class);
    private final String connectionString;

    public MySQLHotelWithAddressData(String connectionString) {
        this.connectionString = connectionString;
    }


    @Override
    public List<HotelWithAddress> findAllHotelsWithAddress() {
        try (Connection connection = DriverManager.getConnection(connectionString);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("select h.*, a.*\n" +
                     "\n" +
                     "from hotels h LEFT JOIN addresses a\n" +
                     "\n" +
                     "on h.address_id = a.id;")) {
            List<HotelWithAddress> hotelsWithAddressList = new ArrayList<>();
            while (resultSet.next()) {
                HotelWithAddress hotelWithAddress = new HotelWithAddress();

                Address address = new Address();
                address.setId(resultSet.getLong("address_id"));
                address.setCity(resultSet.getString("city"));
                address.setCountry(resultSet.getString("country"));
                address.setStreet(resultSet.getString("street"));

                Hotel hotel = new Hotel();
                hotel.setAddressId(resultSet.getLong("address_id"));
                hotel.setId(resultSet.getLong("id"));
                hotel.setName(resultSet.getString("name"));
                hotel.setStars(resultSet.getInt("stars"));

                hotelWithAddress.setAddress(address);
                hotelWithAddress.setHotel(hotel);

                hotelsWithAddressList.add(hotelWithAddress);
            }
            return hotelsWithAddressList;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }

    }
}
