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
            
            str = "AUTH PRSank\n";
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

            //Finding amount of servers
            String[] dataArr = str2.split(" ");
            int nRecs = Integer.parseInt(dataArr[1]);

            // end
            str = "OK\n";
            dout.write((str).getBytes());
            dout.flush();

            //Cataloging Server sizes
            String lType="";
            int lCore = 0;
            int sCount = 0;
            for(int i =0; i < nRecs;++i) {
                str2=din.readLine();
                String[] str3 = str2.split(" ");

                if(lCore < Integer.parseInt(str3[4])){
                    lCore = Integer.parseInt(str3[4]);
                    lType = str3[0];
                }

                if(lType.equals(str3[0])) {
                    sCount = 1 + Integer.parseInt(str3[1]);
                }
    
                System.out.println("Server says:"+str2);
            }
            System.out.println("Largest Server Type: " +lType+ " Cores: " +lCore +" Count "+ sCount);



            str2=din.readLine();
            System.out.println("Server says:"+str2);


            // str = "OK\n";
            // dout.write((str).getBytes());
            // dout.flush();

            // str2=din.readLine();
            // System.out.println("Server says:"+str2);


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