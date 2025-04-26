package BankingManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class AccountManager 
{
	private Connection con;
	private Scanner s;

	public AccountManager(Connection con, Scanner s) 
	{
		this.con=con;
		this.s=s;
	}


	// method to credit amount to account
	// credit_money() starts here

	public void credit_money(long account_number) throws SQLException
	{
		s.nextLine();
		System.out.print("Enter Amount:");
		double amount=s.nextDouble();
		s.nextLine();
		System.out.print("Enter Security pin:");
		String seccurity_pin=s.nextLine();

		try
		{
			con.setAutoCommit(false);
			String query="Select * from accounts where account_number = ? and security_pin =?";

			if(account_number!=0)
			{
				PreparedStatement preparedStatement=con.prepareStatement(query);
				preparedStatement.setDouble(1, account_number);
				preparedStatement.setString(2, seccurity_pin);
				ResultSet resultSet=preparedStatement.executeQuery();
				if(resultSet.next())
				{
					String credit_query="Update accounts set balance=balance + ? where account_number=?";
					PreparedStatement preparedStatement1=con.prepareStatement(credit_query);
					preparedStatement1.setDouble(1, amount);
					preparedStatement1.setLong(2, account_number);

					int rowsAffected=preparedStatement1.executeUpdate();
					if(rowsAffected>0)
					{
						System.out.println("Rs. "+amount+" credited Successfully\n");
						con.commit();
						con.setAutoCommit(true);
						return;
					}
					else
					{
						System.out.println("Transaction Failed!\n");
						con.rollback();
						con.setAutoCommit(true);
					}
				}
				else
				{
					System.out.println("Invalid Security Pin!\n");
				}
			}

		}
		catch (Exception e) {
			e.printStackTrace();
		}
		con.setAutoCommit(true);
	}
	// credit_money() ends here


	// method to debit amount from account
	// debit_money() starts here

	public void debit_money(long account_number) throws SQLException 
	{
		s.nextLine();
		System.out.print("Enter Amount:");
		double amount =s.nextDouble();
		s.nextLine();
		System.out.print("Enter Security Pin:");
		String security_pin=s.nextLine();
		try
		{
			String query="select * from accounts where account_number = ? and security_pin=?";
			con.setAutoCommit(false);
			if(account_number>0)
			{
				PreparedStatement preparedStatement=con.prepareStatement(query);
				preparedStatement.setLong(1, account_number);
				preparedStatement.setString(2, security_pin);
				ResultSet resultSet=preparedStatement.executeQuery();

				if(resultSet.next())
				{
					double current_balance=resultSet.getDouble("balance");
					if(amount<=current_balance)
					{
						String debit_query="Update accounts set balance = balance - ? where account_number=?";
						PreparedStatement preparedStatement1=con.prepareStatement(debit_query);
						preparedStatement1.setDouble(1, amount);
						preparedStatement1.setLong(2, account_number);

						int rowsAffected=preparedStatement1.executeUpdate();
						if(rowsAffected>0)
						{
							System.out.println("Rs. "+amount+" debited Successfully\n");
							con.commit();
							con.setAutoCommit(true);
							return;
						}
						else
						{
							System.out.println("Transaction Failed!\n");
							con.rollback();
							con.setAutoCommit(true);
						}

					}
				}
				else
				{
					System.out.println("Insufficient Balance!!\n");
				}
			}
			else
			{
				System.out.println("Invalid Pin!\n");
			}

		}
		catch (Exception e) {
			e.printStackTrace();
		}
		con.setAutoCommit(true);
	}

	//debit_money() ends here


	// method to transfer amount
	// transfer_money starts here

	public void transfer_money(long sender_account_number) throws SQLException
	{
		s.nextLine();
		System.out.print("Enter Receiver Account Number:");
		long receiver_account_number=s.nextLong();
		System.out.print("Enter Amount:");
		double amount=s.nextDouble();
		s.nextLine();
		System.out.print("Enter Security Pin:");
		String security_pin=s.nextLine();
		try
		{
			con.setAutoCommit(false);
			if(sender_account_number!=0 && receiver_account_number!=0)
			{
				if(sender_account_number != receiver_account_number) {
					PreparedStatement preparedStatement=con.prepareStatement("select * from accounts where account_number = ? and security_pin = ?");
					preparedStatement.setLong(1, sender_account_number);
					preparedStatement.setString(2, security_pin);
					ResultSet resultSet=preparedStatement.executeQuery();

					if(resultSet.next())
					{
						double current_balance=resultSet.getDouble("balance");

						if(amount<=current_balance)
						{
							String debit_query="update accounts set balance = balance - ? where account_number = ?";
							String credit_query="update accounts set balance = balance + ? where account_number = ?";

							PreparedStatement creditPreparedStatement=con.prepareStatement(credit_query);
							PreparedStatement debitPreparedStatement=con.prepareStatement(debit_query);

							creditPreparedStatement.setDouble(1, amount);
							creditPreparedStatement.setLong(2, receiver_account_number);
							debitPreparedStatement.setDouble(1, amount);
							debitPreparedStatement.setLong(2, sender_account_number);

							int rowsAffected1=debitPreparedStatement.executeUpdate();
							int rowsAffected2=creditPreparedStatement.executeUpdate();

							if(rowsAffected1 >0 && rowsAffected2>0)
							{
								System.out.println("Transaction Successfully!!");
								System.out.println("Rs. "+amount+" Transferred Successfully\n");
								con.commit();
								con.setAutoCommit(true);
								return;
							}
							else
							{
								System.out.println("Transaction Failed!!\n");
								con.rollback();
								con.setAutoCommit(true);
							}
						}
						else
						{
							System.out.println("Insufficient Balance!!\n");
						}
					}
					else
					{
						System.out.println("Invalid Security Pin!!\n");

					}
				}
				else
					System.out.println("\nTransaction Failed!!\nReceiver and Sender Account numbers are same");

			}
			else
			{
				System.out.println("Invalid Account number\n");
			}

		}
		catch (Exception e) {
			e.printStackTrace();
		}
		con.setAutoCommit(true);
	}
	// transfer_money ends here


	// method to get the current balance
	//getBalance() starts here

	public void getBalance(long account_number)
	{
		s.nextLine();
		System.out.print("Enter Security Pin:");
		String security_pin=s.nextLine();

		try
		{
			PreparedStatement preparedStatement=con.prepareStatement("select balance from accounts where account_number = ? and security_pin = ? ");
			preparedStatement.setLong(1, account_number);
			preparedStatement.setString(2, security_pin);

			ResultSet resultSet=preparedStatement.executeQuery();

			if(resultSet.next())
			{
				double balance=resultSet.getDouble("balance");
				System.out.println("Balance:"+balance);
			}
			else
			{
				System.out.println("Invalid Pin!\n");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	//getBalance() ends here
}
