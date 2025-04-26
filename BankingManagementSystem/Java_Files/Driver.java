package BankingManagementSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;

public class Driver 
{
	private static final String url="jdbc:mysql://localhost:3306/banking_mgmt";
	private static final String username="root";
	private static final String password="root";
	
	public static void main(String[] args) 
	{
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection(url, username, password);
			Scanner s=new Scanner(System.in);
			User user=new User(con, s);
			Accounts accounts=new Accounts(con, s);
			AccountManager accountManager=new AccountManager(con, s);
			
			String email;
			long account_number;
			
			while(true)
			{
				System.out.println("*** WELCOME TO BANKING SYSTEM ***\n");
				System.out.println("1. Register");
				System.out.println("2. Login");
				System.out.println("3. Exit\n");
				System.out.println("Enter your choice:");
				int choice=s.nextInt();
				
				switch (choice) 
				{
				case 1:
						user.register();
					break;
				case 2:
					email=user.login();
					if(email!=null)
					{
						System.out.println("\nUser Logged In!!\n");
						if(!accounts.account_exist(email))
						{
							System.out.println("\n1.Open a new Account");
							System.out.println("2.Exit");
							if(s.nextInt()==1)
							{
								account_number=accounts.open_account(email);
								System.out.println("Account Created Succcessfully!!");
								System.out.println("Your Account number is:"+account_number+"\n");
								
							}
							else
							{
								break;
							}
						}
						
						account_number=accounts.getAccountNumber(email);
						int ch2=0;
						while(ch2!=5)
						{
							System.out.println("\n1. Debit Money");
							System.out.println("2. Credit Money");
							System.out.println("3. Transfer Money");
							System.out.println("4. Check Balance");
							System.out.println("5. Log out\n");
							System.out.println("Enter your choice:");
							ch2=s.nextInt();
							switch(ch2)
							{
								case 1:
									accountManager.debit_money(account_number);
									break;
									
								case 2:
									accountManager.credit_money(account_number);
									break;
									
								case 3:
									accountManager.transfer_money(account_number);
									break;
									
								case 4:
									accountManager.getBalance(account_number);
									break;
									
								case 5:
									break;
									
									default:
										System.out.println("Enter Valid Choice!");
										break;
							}
							
						}
					}
					else
					{
						System.out.println("Incorrect Email or Password\n");
					}
					
					break;
				case 3:
					System.out.println("\nThank you for using BANKING MANAGEMENT SYSTEM❤️!!");
					System.out.println("Exiting System!");
					return;
					
					

				default:
					System.out.println("Enter Valid Choice");
					break;
				}
				
				
			}
				
			
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
