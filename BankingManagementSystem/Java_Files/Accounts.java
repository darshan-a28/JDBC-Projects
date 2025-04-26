package BankingManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Accounts 
{
	private Connection con;
	private Scanner s;
	
	public Accounts(Connection con,Scanner s)
	{
		this.con=con;
		this.s=s;
	}
	
	// method for account opening
	//open_account() starts here
	
	public long open_account(String email)
	{
		if(!account_exist(email))
		{
			String query="INSERT INTO Accounts(account_number, full_name, email, balance, security_pin) VALUES(?, ?, ?, ?, ?)";
			s.nextLine();
			System.out.print("Enter Full Name:");
			String full_name=s.nextLine();
			System.out.print("Enter Initial Amount:");
			double balance=s.nextDouble();
			s.nextLine();
			System.out.print("Enter Security Pin: ");
			String security_pin=s.nextLine();
			
			try
			{
				long account_number=generateAccountNumber();
				PreparedStatement preparedStatement=con.prepareStatement(query);
				preparedStatement.setLong(1, account_number);
				preparedStatement.setString(2, full_name);
				preparedStatement.setString(3, email);
				preparedStatement.setDouble(4, balance);
				preparedStatement.setString(5, security_pin);
				
				int rowsAffected = preparedStatement.executeUpdate();
				if(rowsAffected>0)
					return account_number;
				else
					throw new RuntimeException("Account Creation Failed!!\n");
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		throw new RuntimeException("Account Already Exist\n");
	}
	
	//open_account() ends here
	
	
	
	// method to get account number
	//getAccountNumber() starts here
	
	public long getAccountNumber(String email)
	{
		String query="Select account_number from accounts where email=?";
		
		try
		{
			PreparedStatement preparedStatement=con.prepareStatement(query);
			preparedStatement.setString(1, email);
			ResultSet resultSet=preparedStatement.executeQuery();
			
			if(resultSet.next())
				return resultSet.getLong("account_number");
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		throw new RuntimeException("Account Number Doesn't Exist!\n");

	}
	
	//getAccountNumber() ends here
	
	
	// method to generate account number
	// generateAccountNumber() starts here
	
	private long generateAccountNumber()
	{
		String query="Select account_number from accounts order by account_number Desc limit 1";
		try
		{
			Statement statement=con.createStatement();
			ResultSet resultSet=statement.executeQuery(query);
			
			if(resultSet.next())
			{
				long last_account_number=resultSet.getLong("account_number");
				return last_account_number+1;
			}
			else
			{
				return 10000100;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return 10000100;
	}
	// generateAccountNumber() ends here
	
	
	// method to check whether account exist or not
	//
	public boolean account_exist(String email)
	{
		String query="select account_number from accounts where email=?";
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
	
}
