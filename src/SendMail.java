import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMail extends Thread {
    private String email;
    private String confirmCode;
    private CodeGenerator cg;

    SendMail(String email) {
        this.email = email;
    }

    public String getCode(){
        return confirmCode;
    }

    @Override 
    public void run() {
        cg = new CodeGenerator();
        confirmCode = cg.Generate();
        String host = "smtp.gmail.com";
        final String user = "arayanmemon338@gmail.com";// change accordingly
        final String password = "tudfqfgqnleomxzi";// change accordingly

        String to = email;// change accordingly

        // Get the session object
        Properties props = System.getProperties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        });

        // Compose the message
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("Authentication Code for LoanApp");
            message.setText("Your Authentication code is "+ confirmCode);
            // message.setSubject("LoanApp Application");
            // message.setText("You have successfully applied for Student Loan of Rs.50000 for the duration of 3 years.\nYou have to start the repayment of your loan after 1 year\n\nSincerely\nTeam LoanApp\nCEO Arayan memon");
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
