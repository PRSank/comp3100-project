import java.io.*;
import java.net.*;

public class MyClient {
    public static void main(String[] args) {
        try {
            Socket s = new Socket("localhost",6666);
            DataInputStream din = new DataInputStream(s.getInputStream());
            DataOutputStream dout = new DataOutputStream(s.getOutputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            
            String str = "",str2="";
            str = "Helo";
            dout.writeUTF(str);
            dout.flush();
            try {
                Thread.sleep(5000);
            } catch(Exception e) {
                System.out.println(e);
            }

            str2=din.readUTF();
            System.out.println("Server says:"+str2);
            
            str = "BYE";
            dout.writeUTF(str);
            dout.flush();

            try {
                Thread.sleep(1500);
            } catch(Exception e) {
                System.out.println(e);
            }

            str2=din.readUTF();
            System.out.println("Server says:"+str2);

            dout.close();
            s.close();
        } catch(Exception e){System.out.println(e);}
    }
}