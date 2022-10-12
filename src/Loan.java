import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
import com.mysql.jdbc.Connection;

public class Loan {
    static boolean status = false;
    static boolean count = true;
    String email;
    Loan(String email) throws ClassNotFoundException, SQLException{
        this.email = email;
        System.out.print("\033[H\033[2J");
        System.out.println("Welcome to Loan Section\n");
        System.out.println("1. Apply for Loan");
        System.out.println("2. Repay Loan");
        System.out.println("3. Check Loan Status");
        System.out.println("4. Exit");
        new Loader('-');
        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();
        switch(choice){
            case 1:{
                if (Status(email)) {
                    System.out.println("You have already applied for a loan");
                }
                else{
                    Apply();
                }
                break;
            }
            case 2:
                Repay(email);
                break;
            case 3:
                Check(email);
                break;
            case 4:
                System.out.print("Exiting");
                new Loader('.');
                break; 
            default:
                System.out.println("Invalid Choice");
        }
    }

    // Apply Method
    public void Apply() throws ClassNotFoundException, SQLException{
        SendMail send = new SendMail(this.email);
        Scanner sc = new Scanner(System.in);
        String email = this.email;
        System.out.println("Enter your loan amount(10,000 - 1,00,000)");
        String amount = sc.nextLine();
        if (Integer.parseInt(amount) > 100000) {
            System.out.println("You can't apply for loan more than 1,00,000");
            sc.nextLine();
            Apply();
        }
        System.out.println("Enter your loan duration in months(1-9)");
        String duration = sc.nextLine();
        System.out.println("Enter your loan purpose (Education, Business, Home, Car,etc)");
        String purpose = sc.nextLine();
        send.start();
        System.out.println("Enter the verification sent on your mail");
        String code = sc.nextLine();
        if (send.getCode().equals(code)) {
            // System.out.println("Verification process is in progress you can check it later on our portal");
        }
        else{
            System.out.println("You have provided invalid OTP");
            sc.nextLine();
            new Loan(this.email);
            System.out.print("\033[H\033[2J");
            }
  
        try{
            status = true;
            java.sql.Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","");
            String query = "insert into `loan`(`email`,`amount`, `duration`, `purpose`, `status`) values('"+email+"','"+amount+"','"+duration+"','"+purpose+"','"+status+"')";
            java.sql.Statement st = con.createStatement();
            st.executeUpdate(query);
            System.out.println("You have successfully applied for a loan of Rs."+amount+" for "+duration+" months");
            count = false;
            System.out.println("press enter to goto main menu");
            sc.nextLine();
            new Loan(email);
        } catch (Exception e) {
            System.out.println(e);
        }  
    }   // End of Apply Method


    // Repayment Method
    public void Repay(String email) throws ClassNotFoundException, SQLException{
        if(TotalAmount()>0){
            System.out.print("\033[H\033[2J");
            Scanner sc = new Scanner(System.in);
        System.out.println("Loan Repayment ===========================");
        System.out.println("you have taken a loan of Rs."+TotalAmount());
        System.out.println("Enter Amount you want to repay");
        int amount = Integer.parseInt(sc.nextLine());
        int total = TotalAmount()-amount;
        if(total>=0){
            try {
                java.sql.Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","");
                String query = "UPDATE `loan` SET `amount`='"+total+"' WHERE email = '"+email+"' ";
                java.sql.Statement st = con.createStatement();
                st.executeUpdate(query);
                
            } catch (Exception e) {
                // TODO: handle exception
            }
            System.out.println("you have Repayed Rs."+amount+" your Remaining loan is Rs."+total);
        }
            System.out.println("Press enter to goto main menu");
            sc.nextLine();
            new Loan(email);
    }
    else{
        try {
            java.sql.Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","");
            String query = "DELETE FROM `loan` WHERE email = '"+email+"' ";
            java.sql.Statement st = con.createStatement();
            st.executeUpdate(query);
            
        } catch (Exception e) {
            // TODO: handle exception
        }
        System.out.println("No active loan(s)");
        System.out.println("Press enter to goto main menu");
        Scanner sc = new Scanner(System.in);
        sc.nextLine();
        new Loan(email);
    }

    }   // End of Repayment method
    

    // Check Status Method
    public void Check(String email) throws ClassNotFoundException, SQLException{
        Scanner sc = new Scanner(System.in);
        if (Status(email)) {
            System.out.println("your remaining loan amount to be repayed is Rs."+TotalAmount()+"\npress enter to goto main menu");
            sc.nextLine();
            new Loan(email);
        } else {
            System.out.println("You have no remaining loan \npress enter to goto main menu");
            sc.nextLine();
            new Loan(email);
            
        }
    }   // End of Check status method
    
    public boolean Status(String email) throws ClassNotFoundException, SQLException{
        Class.forName("com.mysql.jdbc.Driver");
        java.sql.Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","");
        java.sql.Statement st = con.createStatement();
        String qr = "select * from `loan` where `email` = '"+email+"'";
        java.sql.ResultSet rs = st.executeQuery(qr);
        while(rs.next()){
                if (rs.getString("email").equals(email)) {
                    return true;
                }
                else{
                    return false;
                }
            }
        return false;
    }

    // method to get total amount of loan
    public int TotalAmount(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            java.sql.Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","");
            java.sql.Statement st = con.createStatement();
            String qr = "select * from `loan` where `email` = '"+email+"'";
            java.sql.ResultSet rs = st.executeQuery(qr);
            while(rs.next()){
               int res= Integer.parseInt(rs.getString("amount")); 
               return res;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return 0;
    }   // End of total amount Method

}   // End of Loan class

