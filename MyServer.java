import java.io.*;
import java.net.*;

public class MyServer {
    public static void main(String[]args) {
        try {
            ServerSocket ss = new ServerSocket(6666);
            Socket s = ss.accept(); //establishes connection
            DataInputStream din = new DataInputStream(s.getInputStream());
            DataOutputStream dout = new DataOutputStream(s.getOutputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            String str = "",str2="";
            str = din.readUTF();
            System.out.println("client says:" + str);
            
            str2 = "G'DAY";
            dout.writeUTF(str2);
            dout.flush();
            try {
                Thread.sleep(1500);
            } catch(Exception e) {
                System.out.println(e);
            }

            str = din.readUTF();
            System.out.println("client says:" + str);

            str2 = "BYE";
            dout.writeUTF(str2);
            dout.flush();

            try {
                Thread.sleep(1500);
            } catch(Exception e) {
                System.out.println(e);
            }



            din.close();
            s.close();
            ss.close();
            
        }catch(Exception e){System.out.println(e);}
    }
}