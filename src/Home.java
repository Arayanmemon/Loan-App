import java.sql.*;
import java.util.Scanner;

import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;

class Home {
    Home(){
        Scanner sc = new Scanner(System.in);
        System.out.println("press 1 to Register");
        System.out.println("press 2 to Login");
        System.out.println("press 3 to Exit");
        System.out.println("--------------------");
        int choice = sc.nextInt();
        switch(choice){
            case 1:
                new Register();
                break;
            case 2:
                new Login();
                break;
            case 3:
            System.out.print("Exiting");
                new Loader('.');
                break;
            default:
                System.out.println("Invalid Choice");
        }
    }
}

class Register{
    Register(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your name");
        String name = sc.nextLine();
        System.out.println("Enter your email");
        String email = sc.nextLine();
        System.out.println("Enter your password");
        String password = sc.nextLine();
        System.out.println("Enter your mobile number");
        String mobile = sc.nextLine();
        System.out.println("Enter your address");
        String address = sc.nextLine();

        try {
            String query = "insert into `register`(`name`, `email`, `password`, `address`, `mobile`) values('"+name+"','"+email+"','"+password+"','"+address+"','"+mobile+"')";
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","");
            java.sql.Statement st = con.createStatement();
            st.executeUpdate(query);
            System.out.println("You have been registered successfully");
            new Login();
        } catch (Exception e) {
            System.out.println(e);
        }  
    }
}

class Login{
    Login(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your email");
        String email = sc.nextLine();
        System.out.println("Enter your password");
        String password = sc.nextLine();
        try {
            String query = "select * from register where email='"+email+"' and password='"+password+"'";
            Class.forName("com.mysql.jdbc.Driver");
            Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","");
            java.sql.Statement st = c.createStatement();
            java.sql.ResultSet rs = st.executeQuery(query);
            if(rs.next()){
                System.out.println("Login Successfull");
                System.out.println("Redirecting to Home Page.....");
                new Loan();
            }else{
                System.out.println("Invalid Email or Password");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}