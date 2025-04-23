package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Doctor
{
	private Connection con;
	
	public Doctor(Connection con)
	{
		this.con=con;
	}
	
	// method to view the Doctors
	// viewDoctors() starts here
	
	public void viewDoctors() 
	{
		String query="select * from doctors";
		try
		{
			PreparedStatement preparedStatement=con.prepareStatement(query);
			ResultSet res=preparedStatement.executeQuery();
			System.out.println("Doctors: ");
			System.out.println("+------------+--------------------+------------------+");
			System.out.println("| Doctor Id  | Name               | Specialization   |");
			System.out.println("+------------+--------------------+------------------+");
			while(res.next())
			{
				int id=res.getInt("id");
				String name=res.getString("name");
				String specialization=res.getString("specialization");
				System.out.printf("| %-10s | %-18s | %-16s |\n",id,name,specialization);
				System.out.println("+------------+--------------------+------------------+");
				
				
			}
			
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// viewDoctors() starts here
	
	
	// method to get Doctors by id
	//getDoctorsById starts here
	public boolean getDoctorsById(int id) 
	{
		String query="Select * from doctors where id=?";
		
		try
		{
			PreparedStatement preparedStatement=con.prepareStatement(query);
			preparedStatement.setInt(1, id);
			ResultSet res=preparedStatement.executeQuery();
			if(res.next())
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return false;
		
	}
	
	//getDoctorsById ends here
	
}
