import java.io.*;
import java.net.*;

public class MyClient {
    public static void main(String[] args) {
        try {
            Socket s = new Socket("127.0.0.1",50000);
            BufferedReader din = new BufferedReader(new InputStreamReader(s.getInputStream()));
            DataOutputStream dout = new DataOutputStream(s.getOutputStream());
            
            String str = "",str2="";
            str = "HELO\n";
            dout.write((str).getBytes());
            dout.flush();
            

            str2=din.readLine();
            System.out.println("Server says:"+str2);
            
            str = "AUTH hojoo\n";
            dout.write((str).getBytes());
            dout.flush();

            str2=din.readLine();
            System.out.println("Server says:"+str2);

            str = "REDY\n";
            dout.write((str).getBytes());
            dout.flush();

            str2=din.readLine();
            System.out.println("Server says:"+str2);

            str = "GETS All\n";
            dout.write((str).getBytes());
            dout.flush();

            str2=din.readLine();
            System.out.println("Server says:"+str2);

            str = "OK\n";
            dout.write((str).getBytes());
            dout.flush();

            str2=din.readLine();
            System.out.println("Server says:"+str2);


            str = "OK\n";
            dout.write((str).getBytes());
            dout.flush();

            str2=din.readLine();
            System.out.println("Server says:"+str2);


            str = "SCHD 0 super-silk 0\n";
            dout.write((str).getBytes());
            dout.flush();

            str2=din.readLine();
            System.out.println("Server says:"+str2);

            str = "OK\n";
            dout.write((str).getBytes());
            dout.flush();

            str2=din.readLine();
            System.out.println("Server says:"+str2);

            str = "REDY\n";
            dout.write((str).getBytes());
            dout.flush();   
            
            str2=din.readLine();
            System.out.println("Server says:"+str2);

            dout.close();
            s.close();
        } catch(Exception e){System.out.println(e);}
    }
}