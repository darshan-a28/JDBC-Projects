package Hotel_Reservation_System;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class HotelReservationSystem {

	private static final String url = "jdbc:mysql://localhost:3306/hotel_mgmt";
	private static final String username = "root";
	private static final String password = "root";

	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, username, password);
			while (true) {
				System.out.println();
				System.out.println("----WELCOME TO HOTEL RESERVATION SYSTEM----\n");
				Scanner s = new Scanner(System.in);
				System.out.println("1. Reserve a room");
				System.out.println("2. View Reservations");
				System.out.println("3. Get Room Number");
				System.out.println("4. Update Reservations");
				System.out.println("5. Delete Reservations");
				System.out.println("6. Exit");
				System.out.print("Choose an option:");
				int ch = s.nextInt();
				s.nextLine(); // consume newline

				switch (ch) {
					case 1:
						reserveRoom(con, s);
						break;

					case 2:
						viewReservations(con);
						break;

					case 3:
						getRoomNumber(con, s);
						break;

					case 4:
						updateReservation(con, s);
						break;

					case 5:
						deleteReservation(con, s);
						break;

					case 6:
						exit();
						s.close();
						return;

					default:
						System.out.println("Invalid choice");
						break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// method to reserve room
	// reserveRoom() starts here

	private static void reserveRoom(Connection con, Scanner s) {
		try {
			System.out.print("Enter guest name:");
			String guestName = s.nextLine();
			System.out.print("Enter room number:");
			int roomNumber = s.nextInt();
			s.nextLine(); // consume newline
			System.out.print("Enter contact number:");
			String contactNumber = s.nextLine();

			String query = "INSERT INTO reservations(guest_name, room_number, contact_number) VALUES (?, ?, ?)";

			try (PreparedStatement ps = con.prepareStatement(query)) {
				ps.setString(1, guestName);
				ps.setInt(2, roomNumber);
				ps.setString(3, contactNumber);

				int af = ps.executeUpdate();
				if (af > 0) {
					System.out.println("Reservation Successful");
				} else
					System.out.println("Reservation failed");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// reserveRoom() ends here

	// method to view Reservations

	// viewReservations() stars here
	private static void viewReservations(Connection con) throws SQLException {
		String query = "SELECT * FROM reservations";
		try (Statement statement = con.createStatement();
			 ResultSet res = statement.executeQuery(query)) {
			System.out.println("Current Reservations: ");
			System.out.println("+----------------+-----------------+---------------+----------------------+-------------------------+");
			System.out.println("| Reservation ID | Guest           | Room Number   | Contact Number       | Reservation Date        |");
			System.out.println("+----------------+-----------------+---------------+----------------------+-------------------------+");

			while (res.next()) {
				int resId = res.getInt("reservation_id");
				String gname = res.getString("guest_name");
				int roomNumber = res.getInt("room_number");
				String cNumber = res.getString("contact_number");
				String resDate = res.getTimestamp("reservation_date").toString();

				System.out.printf("| %-14d | %-15s | %-13d | %-20s | %-21s   |\n", resId, gname, roomNumber, cNumber, resDate);
			}

			System.out.println("+----------------+-----------------+---------------+----------------------+-------------------------+");
		}
	}
	// viewReservations() ends here

	// method to get room number
	// getRoomNumber() starts here
	private static void getRoomNumber(Connection con, Scanner s) {
		try {
			System.out.print("Enter Reservation ID:");
			int reservationId = s.nextInt();
			s.nextLine();
			System.out.print("Enter guest name:");
			String guestName = s.nextLine();

			String query = "SELECT room_number FROM reservations WHERE reservation_id = ? AND guest_name = ?";

			try (PreparedStatement ps = con.prepareStatement(query)) {
				ps.setInt(1, reservationId);
				ps.setString(2, guestName);
				ResultSet res = ps.executeQuery();

				if (res.next()) {
					int roomNumber = res.getInt("room_number");
					System.out.println("Room number for Reservation ID " + reservationId + " and Guest " + guestName + " is: " + roomNumber);
				} else
					System.out.println("Reservation not found for the given ID and Guest name");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// getRoomNumber() ends here

	// method to update the Reservations
	// updateReservation() Starts here
	private static void updateReservation(Connection con, Scanner s) {
		try {
			System.out.print("Enter reservation ID to update:");
			int reservationId = s.nextInt();
			s.nextLine();

			if (!reservationExists(con, reservationId)) {
				System.out.println("Reservation not found for the given ID");
				return;
			}

			System.out.print("Enter new Guest name:");
			String newGuestName = s.nextLine();
			System.out.print("Enter new room number:");
			int newRoomNumber = s.nextInt();
			s.nextLine();
			System.out.print("Enter new contact number:");
			String newContactNumber = s.nextLine();

			String query = "UPDATE reservations SET guest_name = ?, room_number = ?, contact_number = ? WHERE reservation_id = ?";

			try (PreparedStatement ps = con.prepareStatement(query)) {
				ps.setString(1, newGuestName);
				ps.setInt(2, newRoomNumber);
				ps.setString(3, newContactNumber);
				ps.setInt(4, reservationId);

				int ar = ps.executeUpdate();
				if (ar > 0)
					System.out.println("Reservation Updated Successfully!!");
				else
					System.out.println("Reservation update failed");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// updateReservation() Ends here

	// method to delete Reservation
	// deleteReservation() starts here
	private static void deleteReservation(Connection con, Scanner s) {
		try {
			System.out.print("Enter reservation ID to delete:");
			int reservationId = s.nextInt();

			if (!reservationExists(con, reservationId)) {
				System.out.println("Reservation not found for the given ID");
				return;
			}

			String query = "DELETE FROM reservations WHERE reservation_id = ?";

			try (PreparedStatement ps = con.prepareStatement(query)) {
				ps.setInt(1, reservationId);
				int ra = ps.executeUpdate();

				if (ra > 0)
					System.out.println("Reservation deleted Successfully!!");
				else
					System.out.println("Reservation delete failed");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// deleteReservation() ends here

	// method to check whether reservation exists or not
	// reservationExists() starts here
	private static boolean reservationExists(Connection con, int reservationId) {
		try {
			String query = "SELECT reservation_id FROM reservations WHERE reservation_id = ?";

			try (PreparedStatement ps = con.prepareStatement(query)) {
				ps.setInt(1, reservationId);
				ResultSet resultSet = ps.executeQuery();
				return resultSet.next(); // if there is a result, the reservation exists
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	// reservationExists() ends here

	// exit(), here we are using thread
	// starts here
	public static void exit() throws InterruptedException {
		System.out.print("Exiting System");
		int i = 5;
		while (i != 0) {
			System.out.print(".");
			Thread.sleep(1000);
			i--;
		}
		System.out.println();
		System.out.println("Thank You for using Hotel Reservation System!!❤️");
	}
	// ends here
}

