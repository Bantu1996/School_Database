package School_Database;

import com.github.javafaker.Faker;
import java.lang.String;
import java.sql.*;
import java.util.Locale;


public class School{
    public static void main(String[] args)
    {
        Connection connection = null;
        try
        {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:schools.db");
            Statement statement = connection.createStatement();
            String sql = "insert into employees (" + "employees_id, first_name," +
                    " email, phone_number,position) VALUES(?,?,?,?,?)";

            statement.setQueryTimeout(30);  // set timeout to 30 sec.
            System.out.println();

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS employees(" +
                    "employees_id integer PRIMARY KEY," +
                    "first_name string,"  +
                    "email string," +
                    "phone_number string," +
                    "position string)");

            ResultSet rs = statement.executeQuery("select * from employees");
            PreparedStatement pstmt = connection.prepareStatement(sql);

            int count = 0;
            while(count < 2){
                Locale locale = new Locale("en-ZA");
                Faker faker = new Faker(locale);

                String firstName = faker.name().firstName();
                String email = faker.internet().emailAddress();
                String phoneNumber = faker.phoneNumber().phoneNumber();
                String position = faker.job().position();


                pstmt.setString(2, firstName);
                pstmt.setString(3, email);
                pstmt.setString(4, phoneNumber);
                pstmt.setString(5, position);

                count++;


                pstmt.executeUpdate();
                System.out.println(firstName);
                System.out.println(email);
                System.out.println(phoneNumber);
                System.out.println(position);


            }
        }
        catch(SQLException e)
        {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        }
        finally
        {
            try
            {
                if(connection != null)
                    connection.close();
            }
            catch(SQLException e)
            {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
    }
}
