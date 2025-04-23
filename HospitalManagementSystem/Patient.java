package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient 
{
	private Connection con;
	private Scanner s;
	
	public Patient(Connection con,Scanner s)
	{
		this.con=con;
		this.s=s;
	}
	
	// method to add Patient Details 
	//addPatient() starts here
	public void addPatient()
	{
		// id will be auto incremented so no need to take from the user end
		System.out.print("Enter Patient Name : ");
		String name=s.next();
		System.out.print("Enter Patient Age : ");
		int age=s.nextInt();
		System.out.print("Enter Patient Gender : ");
		String gender=s.next();
		
		try
		{
			String query="INSERT INTO patients(name, age, gender) VALUES(?,?,?)";
			PreparedStatement prepareStatement=con.prepareStatement(query);
			prepareStatement.setString(1, name);
			prepareStatement.setInt(2, age);
			prepareStatement.setString(3, gender);
			int ra=prepareStatement.executeUpdate();
			if(ra>0)
			{
				System.out.println("Patient Added Successfully!!");
				
			}
			else
			{
				System.out.println("Failed to add Patient!!");
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}

	}
	//addPatient() Ends here
	
	// method to view the Patient Details
	//viewPatients() starts here
	public void viewPatients()
	{
		String query ="select * from patients";
		try
		{
			PreparedStatement preparedStatement=con.prepareStatement(query);
			ResultSet res=preparedStatement.executeQuery();
			System.out.println("Patients: ");
			System.out.println("+------------+----------------------+--------+----------");
			System.out.println("| Patient Id | Name                 | Age    | Gender  |");
			System.out.println("+------------+----------------------+--------+----------");
			while(res.next())
			{
				int id=res.getInt("id");
				String name=res.getString("name");
				int age=res.getInt("age");
				String gender=res.getString("gender");
				System.out.printf("| %-10s | %-20s | %-6s | %-7s |\n",id,name,age,gender);
				System.out.println("+------------+----------------------+--------+----------");
			
			} 
		
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	//viewPatients() ends here
	
	
	// method to get the Patients Details by id
	//getPatientById() Stars here
	
	public boolean getPatientById(int id)
	{
		String query="Select * from patients where id=?";
		try
		{
			PreparedStatement preparedStatement=con.prepareStatement(query);
			preparedStatement.setInt(1, id);
			ResultSet res=preparedStatement.executeQuery();
			if (res.next())
			{
				return true;
			}
			else
			{
				return false;
			}
					
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	//getPatientById() Ends here
	
}
