import java.io.*;
import java.net.*;

public class MyClient {
    public static void main(String[] args) {
        try {
            Socket s = new Socket("127.0.0.1",50000);
            BufferedReader din = new BufferedReader(new InputStreamReader(s.getInputStream()));
            DataOutputStream dout = new DataOutputStream(s.getOutputStream());
            
            String str = "",str2="";


            handshake(str, str2, din, dout);

        

            //Initialising constants
            String lType="";
            int lCore = 0;
            int sCount = 0;
            boolean checked = false;

            int job = 0;
            int serverID = 0;


            while(!str2.equals("NONE")){
                str = "REDY\n";
                dout.write((str).getBytes());
                dout.flush();

                str2=din.readLine();
                System.out.println("Server says:"+str2);
                

                String[] tester = str2.split(" ");
                //findLargestServer(checked,str, str2, din, dout, lCore, lType, sCount);

                if(checked == false) {
                    // findLargestServer(str, str2, din, dout, lCore, lType, sCount);
                    str = "GETS All\n";
                    dout.write((str).getBytes());
                    dout.flush();

                    str2=din.readLine();
                    System.out.println("Server says:"+str2);

                    //Finding amount of servers
                    String[] dataArr = str2.split(" ");
                    int nRecs = Integer.parseInt(dataArr[1]);

                    //end
                    str = "OK\n";
                    dout.write((str).getBytes());
                    dout.flush();

                    //C/ataloging Server sizes
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

                    str = "OK\n";
                    dout.write((str).getBytes());
                    dout.flush();

                    str2=din.readLine();
                    System.out.println("Server says:"+str2);

                    checked = true;


                }


                if(tester[0].equals("JOBN")){
                    
                    schdAction(str, str2, din, dout, job, lType, serverID);

                    job++;
                    serverID++;
                    if(serverID >= sCount){
                        serverID = 0;
                    }
                }

                 
            }
            quitAction(str, str2, din, dout, s); //quits and closes socket
        } catch(Exception e){System.out.println(e);}
    }















    static void findLargestServer(String send, String recieve, BufferedReader din,DataOutputStream dout,int lCore, String lType, int sCount){
        try {
            

        } catch(Exception e){System.out.println(e);}
    }
    //completes handshake to connect with server
    static void handshake(String send, String recieve, BufferedReader din,DataOutputStream dout) {
        try {
            send = "HELO\n";
            dout.write((send).getBytes());
            dout.flush();
            

            recieve=din.readLine();
            System.out.println("Server says:"+recieve);
            
            send = "AUTH prateek\n";
            dout.write((send).getBytes());
            dout.flush();

            recieve=din.readLine();
            System.out.println("Server says:"+recieve);
            
        } catch(Exception e){System.out.println(e);}
    }

    //sends a quit message and closes socket
    static void quitAction(String send, String recieve, BufferedReader din,DataOutputStream dout, Socket s) {
        try {
            send = "QUIT\n";
            dout.write((send).getBytes());
            dout.flush();

            recieve=din.readLine();
            System.out.println("Server says:"+recieve);

            dout.close();
            s.close();

        } catch(Exception e){System.out.println(e);}
    }

    //sends a scheduling message
    static void schdAction(String send, String recieve, BufferedReader din,DataOutputStream dout, int job , String lType, int serverID) {
        try {
            send = "SCHD " + job +" "+ lType +" "+ serverID+ "\n";
            dout.write((send).getBytes());
            dout.flush();

            recieve=din.readLine();
            System.out.println("Server says:"+recieve);

        } catch(Exception e){System.out.println(e);}
    }

    
}