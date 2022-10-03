import java.sql.DriverManager;
import java.util.Scanner;

import com.mysql.jdbc.Connection;

public class Loan {
    Loan(){
        System.out.print("\033[H\033[2J");
        System.out.println("Welcome to Loan Section\n");
        System.out.println("1. Apply for Loan");
        System.out.println("2. Check Loan Status");
        System.out.println("3. Exit");
        new Loader('-');
        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();
        switch(choice){
            case 1:{
                if (Apply.count == true) {
                    new Apply();
                }
                else{
                    System.out.println("You have already applied for a loan");
                }
                break;
            }
            case 2:
                new Check();
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

class Apply{
    static boolean status = false;
    static boolean count = true;
    int fixed = 100000;
    Apply(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your email");
        String email = sc.nextLine();
        System.out.println("Enter your loan amount(10,000 - 1,00,000)");
        String amount = sc.nextLine();
        if (Integer.parseInt(amount) > 100000) {
            System.out.println("You can't apply for loan more than 1,00,000");
            new Loan();
        }
        System.out.println("Enter your loan duration in months(1-9)");
        String duration = sc.nextLine();
        System.out.println("Enter your loan purpose(Education, Business, Home, Car,etc)");
        String purpose = sc.nextLine();
        try {
            String query = "insert into `loan`(`email`,`amount`, `duration`, `purpose`, `status`) values('"+email+"','"+amount+"','"+duration+"','"+purpose+"','"+status+"')";
            Class.forName("com.mysql.jdbc.Driver");
            java.sql.Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","");
            java.sql.Statement st = con.createStatement();
            st.executeUpdate(query);
            System.out.println("You have successfully applied for a loan of Rs."+amount+" for "+duration+" months");
            status = true;
            count = false;
            System.out.println("Your loan status is pending");
            sc.nextLine();
            new Loan();
        } catch (Exception e) {
            System.out.println(e);
        }  
    }
}

class Check{
    Check(){
        Scanner sc = new Scanner(System.in);
        if (Apply.status == true) {
            System.out.println("Loan Approved");
            sc.nextLine();
            new Loan();
        } else {
            System.out.println("You have not applied for any loan");
            sc.nextLine();
            new Loan();
            
        }
            
        }
}
