package inserting_and_Retrieve_images;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class Inserting 
{
		
	public static void main(String[] args) 
	{
		String url="jdbc:mysql://localhost:3306/mydatabase";
		String username="root";
		String password="root";
		String image_path="C:\\Users\\Dell\\Desktop\\Images\\𝗝𝗮𝗶 𝗝𝗮𝗴𝗮𝗻𝗻𝗮𝘁𝗵 .jpg"; 
		String query="insert into image(image_data) values(?)";
		
		
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Driver loaded successfully!!");
			
			Connection con=DriverManager.getConnection(url, username, password);
			System.out.println("connection established successfully!!\n\n");
			
			FileInputStream fileInputStream=new FileInputStream(image_path);
			byte[] imageData=new byte[fileInputStream.available()];
			fileInputStream.read(imageData);
			
			PreparedStatement preparedStatement=con.prepareStatement(query);
			preparedStatement.setBytes(1, imageData);
			
			int affectedRows=preparedStatement.executeUpdate();
			
			if(affectedRows>0)
				System.out.println("Image Inserted Successfully!!.....");
			else
				System.out.println("Image not Inserted!!");
				
			
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
