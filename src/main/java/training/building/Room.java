package training.building;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import training.hotel.Hotel;
import training.reservations.Reservation;
import training.resources.Phone;

@Table(name = "rooms")
@Entity
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int capacity;

    @Transient
    private long typeId;

    @Transient
    private long hotelId;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private RoomTypes roomTypes;

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    private double price;
    private int floor;
    @Transient
    private Phone phone;
    @ManyToMany
    @JoinTable(name = "room_amenities",
            joinColumns = {
                    @JoinColumn(name = "room_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "amenity_id")
            })
    private List<Amenity> amenities;

    @OneToMany(mappedBy = "room")
    private List<Reservation> reservations;

    public Phone getPhone() {
        return phone;
    }

    public void setPhone(Phone phone) {
        this.phone = phone;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }


    public long getTypeId() {
        return typeId;
    }

    public void setTypeId(long typeId) {
        this.typeId = typeId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getHotelId() {
        return hotelId;
    }

    public void setHotelId(long hotelId) {
        this.hotelId = hotelId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public List<Amenity> getAmenities() {
        return amenities;
    }

    public void setAmenities(List<Amenity> amenities) {
        this.amenities = amenities;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Room{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", capacity=").append(capacity);
        sb.append(", roomTypes=").append(roomTypes);
        sb.append(", hotel=").append(hotel);
        sb.append(", price=").append(price);
        sb.append(", floor=").append(floor);
        sb.append(", amenities=").append(amenities);
        sb.append('}');
        return sb.toString();
    }
}
