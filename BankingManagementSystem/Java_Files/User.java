package BankingManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class User 
{
	private Connection con;
	private Scanner s;
	
	public User(Connection con,Scanner s)
	{
		this.con=con;
		this.s=s;
	}
	
	
	// method for user registration
	//register() starts here
	public void register() 
	{
		s.nextLine();
		System.out.print("Full Name:");
		String full_name=s.nextLine();
		System.out.print("Email:");
		String email=s.nextLine();
		System.out.print("Password:");
		String password=s.nextLine();
		
		if(user_exist(email))
		{
			System.out.println("User Already Exists for this Email Address!!\n");
			return;
		}
		String query="insert into user(full_name,email,password) Values(?,?,?)";
		try
		{
			PreparedStatement preparedStatement=con.prepareStatement(query);
			preparedStatement.setString(1, full_name);
			preparedStatement.setString(2, email);
			preparedStatement.setString(3, password);
			int afferctedRows=preparedStatement.executeUpdate();
			
			if(afferctedRows>0)
				System.out.println("Registration Successfully!!\n");
			else
				System.out.println("Registration Failed!!\n");
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	//register() endss here
	
	
	//method for login
	// login() starts  here
	public String login()
	{
		s.nextLine();
		System.out.print("Email:");
		String email=s.nextLine();
		System.out.print("Password:");
		String password=s.nextLine();
		
		String query="select * from user where email=? and password=?";
		
		try
		{
			PreparedStatement preparedStatement=con.prepareStatement(query);
			preparedStatement.setString(1, email);
			preparedStatement.setString(2, password);
			ResultSet resultSet=preparedStatement.executeQuery();
			
			if(resultSet.next())
				return email;
			else
				return null;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// login() ends  here
	
	
	// method to check whether user already exists or not
	// user_exist() starts here
	
	public boolean user_exist(String email) 
	{
		String query="Select * from user where email=?";
		try
		{
			PreparedStatement preparedStatement=con.prepareStatement(query);
			preparedStatement.setString(1, email);
			ResultSet resultSet=preparedStatement.executeQuery();
			if(resultSet.next())
			{
				return true;
			}
			else
				return false;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	// user_exist() ends here
	
}
