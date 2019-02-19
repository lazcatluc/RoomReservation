package training.hotelwithaddress;

import training.address.Address;
import training.hotel.Hotel;

public class HotelWithAddress {

    private Hotel hotel;
    private Address address;

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
