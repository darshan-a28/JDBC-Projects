package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Driver 
{
	private static final String url="jdbc:mysql://localhost:3306/hospital";
	private static final String username="root";
	private static final String password="root";
	
	public static void main(String[] args) 
	{
		Scanner s=new Scanner(System.in);
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection(url,username,password);
		
			Patient patient=new Patient(con, s);
			Doctor doctor=new Doctor(con);
			while(true)
			{
				System.out.println("---- Welcome!!----\n\nHospital Management System");
				System.out.println("1. Add Patient");
				System.out.println("2. View Patients");
				System.out.println("3. view Doctors");
				System.out.println("4. Book Appointment");
				System.out.println("5. View Appointment");
				System.out.println("6. Exit\n");
				System.out.print("Enter Your Choice: ");
				int choice=s.nextInt();
				switch (choice) {
				case 1:
					// Add Patient
					patient.addPatient();
					System.out.println();
					break;
				case 2:
					//View Patient
					patient.viewPatients();
					System.out.println();
					break;
				case 3:
					// View Doctors
					doctor.viewDoctors();
					System.out.println();
					break;
				case 4:
					//Book Appointment
					bookAppointment(con, s, patient, doctor);
					System.out.println();
					break;
					
				case 5:
					//View Appointment
					viewAppointments(con);
					System.out.println();
					break;
					
					
					
				case 6:
					// Exit
					System.out.println("Thank you for using!!❤️");
					return ;
				

				default:
					System.out.println("Invalid Choice!!");
					break;
				}
			}
		
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	// method for Appointment Booking
	//bookAppointment() starts here
	
	public static void bookAppointment(Connection con,Scanner s,Patient patient,Doctor doctor) 
	{
		System.out.print("Enter Patient Id: ");
		int patientId=s.nextInt();
		System.out.print("Enter Doctor Id: ");
		int doctorId=s.nextInt();
		System.out.print("Enter Appointment date(YYYY-MM-DD): ");
		String appointmentDate=s.next();
		
		if(patient.getPatientById(patientId) && doctor.getDoctorsById(doctorId))
		{
			if(checkDoctorAvailabilty(doctorId, appointmentDate, con))
			{
				String query="Insert into appointments(patient_id,doctor_id,appointment_date) values(?,?,?)";
				
				try
				{
					PreparedStatement preparedStatement=con.prepareStatement(query);
					preparedStatement.setInt(1,patientId);
					preparedStatement.setInt(2, doctorId);
					preparedStatement.setString(3, appointmentDate);
					int ra=preparedStatement.executeUpdate();
					if(ra>0)
					{
						System.out.println("Appointment Booked!!");
						
					}
					else
					{
						System.out.println("Failed to Book Appointment!!");
					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
					
			}
			else
			{
				System.out.println("Doctor not available on this date!!");
			}
		}
		else
		{
			System.out.println("Either doctor or patient doesn't exist!!!");
		}
		
		
	}
	//bookAppointment() ends here
	
	
	//method to check availability of doctors
	//checkDoctorsAvailablity() Starts here
	
	public static boolean checkDoctorAvailabilty(int doctorId, String appointmentDate, Connection con) 
	{
		String query="Select count(*) from appointments where doctor_id=? and appointment_date=?";
		
		try
		{
			PreparedStatement preparedStatement=con.prepareStatement(query);
			preparedStatement.setInt(1, doctorId);
			preparedStatement.setString(2, appointmentDate);
			ResultSet res=preparedStatement.executeQuery();
			if(res.next())
			{
				int count=res.getInt(1);
				if(count==0)
				{
					return true;
					
				}
				else
					return false;
			}
					
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	//checkDoctorsAvailablity() ends here
	
	
	
	// method to view the booked Appointments
	// viewAppointments() starts here
	public static void viewAppointments(Connection con) {
	    String query = "SELECT a.id, p.name AS patient_name, d.name AS doctor_name, d.specialization, a.appointment_date " +
	                   "FROM appointments a " +
	                   "JOIN patients p ON a.patient_id = p.id " +
	                   "JOIN doctors d ON a.doctor_id = d.id " +
	                   "ORDER BY a.appointment_date";

	    try {
	        PreparedStatement ps = con.prepareStatement(query);
	        ResultSet rs = ps.executeQuery();

	 
	        System.out.println("+---------------+----------------+----------------+------------------+------------------+");
	        System.out.println("| AppointmentID | Patient Name   | Doctor Name    | Specialization   | Appointment Date |");
	        System.out.println("+---------------+----------------+----------------+------------------+------------------+");

	        
	        while (rs.next()) {
	            int id = rs.getInt("id");
	            String patient = rs.getString("patient_name");
	            String doctor = rs.getString("doctor_name");
	            String spec = rs.getString("specialization");
	            String date = rs.getString("appointment_date");

	            
	            System.out.printf("| %-13d | %-14s | %-14s | %-16s | %-16s |\n", id, patient, doctor, spec, date);
	        }

	        
	        System.out.println("+---------------+----------------+----------------+------------------+------------------+");

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	// viewAppointments() ends here
	
	
}

