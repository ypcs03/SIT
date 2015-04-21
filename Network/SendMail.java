import java.lang.String;
import java.io.Console;
import java.util.Scanner;
import java.util.Date;
import java.text.SimpleDateFormat;
import javax.net.ssl.*;
import java.net.*;
import java.io.*;
import java.util.Base64;
import java.lang.Thread;

public class SendMail{
    Socket socket = null;
    String user = null;
    String pwd = null;

    PrintWriter sendout;
    BufferedReader receive;

    public static void main(String []args){
        SendMail client = new SendMail();
    }


    public SendMail(){
        try{
            System.out.print("\t\t1. Send from google mail server;\n\t\t2. Send from stevens mail server;\n\t\tInput: ");
            Scanner readinput = new Scanner(System.in);
            int choice = readinput.nextInt();
            if(choice == 2){
                socket = new Socket("mail.stevens.edu", 587);
            }
            else if(choice == 1){
                socket = (SSLSocket)((SSLSocketFactory)(SSLSocketFactory.getDefault())).createSocket("smtp.gmail.com", 465);
            }
            else{
                System.out.println("Input error");
                return ;
            }
              
            System.out.print("Input the username: ");
            user = new String(readinput.next());
            System.out.print("Input the password: ");
            pwd = new String(System.console().readPassword());
            
            sendout = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            receive = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("socket => " + receive.readLine());
            sendout.println("ehlo " + user);
            sendout.flush();
            System.out.println(receive.readLine());
            
            Thread.sleep(3000);
            //login to the server
            sendout.println("auth login");
            sendout.flush();
            System.out.println("auth login ==> " + receive.readLine());
            Thread.sleep(3000);

            sendout.println(Base64.getEncoder().encodeToString(user.getBytes()));
            sendout.flush();
            System.out.println(user + receive.readLine());
            Thread.sleep(3000);

            sendout.println(Base64.getEncoder().encodeToString(pwd.getBytes()));
            sendout.flush();
            System.out.println(receive.readLine());

            Thread.sleep(3000);
            System.out.print("Input the address to sent this mail: ");
            String mailto = readinput.next();
            sendout.println("mail from:<" + user + "> BODY=8BITMIME");
            sendout.flush();
            System.out.println(receive.readLine());
            Thread.sleep(3000);

            sendout.println("rcpt to:<" + mailto + ">");
            sendout.flush();
            System.out.println(receive.readLine());
            Thread.sleep(3000);

            sendout.println("DATA");
            sendout.flush();
            System.out.println(receive.readLine());
            Thread.sleep(3000);
   
            String content = new String("Subject: Test for the socket mail");
            content += "\r\n";
            System.out.print("Input the mail content(end with the last line \"EOF\": ");
            do{
                mailto = readinput.nextLine();
                content += mailto;
                content += "\r\n";
            }while(!(mailto.toLowerCase().equals("eof")));

            SimpleDateFormat Ctime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            content += Ctime.format(new Date());
            content += "\r\n.\r\n";

            sendout.println(content);
            sendout.flush();
            Thread.sleep(3000);
            System.out.println(receive.readLine());
            System.out.println(receive.readLine());
            Thread.sleep(3000);
            
            socket.close();
            sendout.close();
            receive.close();

            System.out.println("Mail sent!");           
        }catch(Exception e){
            System.out.println("Error " + e);
            return ;
        }
    }
}


