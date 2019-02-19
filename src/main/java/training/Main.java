package training;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import training.building.Room;
import training.importer.PhoneImporter;
import training.resources.Phone;

public class Main {

	private static final String GET_CAPACITY_FOR_ROOM = "whatIsCapacityOfRoom";
	private static final String CREATE_RESERVATION = "createNewReservation";
	private static final String VIEW_RESERVED_ROOMS = "viewRooms";
	private static final String VIEW_AVAILABLE_ROOMS = "viewAvailableRooms";
	private static final DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");

	private static List<Room> rooms;
	private static List<Phone> phones;
	private static List<MyReservation> reservations;

	// Se executa inainte de main
	static {
		System.out.println("Loading files:");
		loadPhonesFromFile();
		loadRoomsFromFile();
	}

	public static void main(String[] args) {
	
		System.out.println("--------MAIN------");
		for (Room r : rooms) {
			System.out.println("Sala: " + r.getName());
			System.out.println("\t capacitate:" + r.getCapacity());
			System.out.println("\t phone: ");
			if (r.getPhone() != null) {
				System.out.println("\t\t" + r.getPhone().getInventoryNumber() + " - " + r.getPhone().getType());
			} else {
				System.out.println("\t\t no phone available");
			}
		}
		System.out.println("----------------------");
	}

	private static void loadPhonesFromFile() {
		System.out.println("... Loading Phone list");
		try (InputStreamReader fileReader = new InputStreamReader(new FileInputStream("phones.csv"))) {
			phones = new PhoneImporter().importFile(fileReader);
		} catch (IOException e) {
			System.out.println("Phones file could not be loaded");
		}
		System.out.print("\n Phone list was loaded: {");
		for(Phone phone:phones){
			System.out.print("["+phone.getInventoryNumber()+"-"+phone.getType()+"]");
		}
		System.out.print("}\n\n");
	}

	private static void loadRoomsFromFile() {
		rooms = new ArrayList<>();
		BufferedReader br;
		File roomsFile = new File("rooms.csv");
		try {
			String thisLine = "";
			InputStream fis = new FileInputStream(roomsFile);
			br = new BufferedReader(new InputStreamReader(fis));
			while ((thisLine = br.readLine()) != null) {
				rooms.add(makeRoomFromCSVString(thisLine));
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * face un obiect de tip Room dintr-o linie csv (se poate muta in Room ->
	 * constructor)
	 */
	private static Room makeRoomFromCSVString(String thisLine) {
		Room room = new Room();
		String[] lineValues = thisLine.split(",");
		room.setName(lineValues[0]);
		room.setCapacity(Integer.parseInt(lineValues[1]));
		room.setPhone(getPhoneById(lineValues[2]));
		return room;
	}

	/**
	 * returneaza obiectul de tip Phone cu id-ul dat
	 */
	private static Phone getPhoneById(String id) {
		int intId = Integer.parseInt(id);
		for (Phone phone : phones) {
			if (phone.getInventoryNumber() == intId)
				return phone;
		}
		return null;
	}
}
