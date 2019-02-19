package training.hotelRooms;

import java.util.Objects;

public class HotelsWithNumberRooms {
    private String nameHotel;
    private long numberRooms;

    public HotelsWithNumberRooms(String nameHotel, long numberRooms) {

        this.nameHotel = nameHotel;
        this.numberRooms = numberRooms;
    }

    public HotelsWithNumberRooms() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HotelsWithNumberRooms that = (HotelsWithNumberRooms) o;
        return numberRooms == that.numberRooms &&
                Objects.equals(nameHotel, that.nameHotel);
    }

    @Override
    public int hashCode() {

        return Objects.hash(nameHotel, numberRooms);
    }
}
