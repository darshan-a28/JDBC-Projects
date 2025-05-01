package inserting_and_Retrieve_images;


import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Retrieving 
{
	
	public static void main(String[] args) 
	{
		String url="jdbc:mysql://localhost:3306/mydatabase";
		String username="root";
		String password="root";
		String folder_path="C:\\Users\\Dell\\Desktop\\Images\\New folder\\"; 
		String query="select image_data from image where image_id =(?)";
		
		
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Driver loaded successfully!!");
			
			Connection con=DriverManager.getConnection(url, username, password);
			System.out.println("connection established successfully!!\n\n");
			
			PreparedStatement preparedStatement=con.prepareStatement(query);
			preparedStatement.setInt(1, 1);
			
			ResultSet resultSet=preparedStatement.executeQuery();
			if(resultSet.next()) 
			{
				byte[] image_data=resultSet.getBytes("image_data");
				String image_path=folder_path+"extractedImage.jpg";
				OutputStream outputStream=new FileOutputStream(image_path);
				outputStream.write(image_data);
				System.out.println("Image has been Retrieved!!....");
			}
			else
			{
				System.out.println("Image not Found!!");
			}
			
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
