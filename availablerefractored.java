import java.io.*;
import java.net.*;

public class availablerefractored {

    public static void main(String[] args) {
        try {
            // Initialising objects for communication functionality
            Socket s = new Socket("127.0.0.1", 50000);
            BufferedReader din = new BufferedReader(new InputStreamReader(s.getInputStream()));
            DataOutputStream dout = new DataOutputStream(s.getOutputStream());

            String sendSTR = "", recieveSTR = "";
            boolean capFlag = false; // Flag to see if capable keyword necessary

            // Authentication Handshake
            sendSTR = "HELO\n";
            dout.write((sendSTR).getBytes());
            dout.flush();

            recieveSTR = din.readLine();
            System.out.println("Server says:" + recieveSTR);

            sendSTR = "AUTH prateek\n";
            dout.write((sendSTR).getBytes());
            dout.flush();

            recieveSTR = din.readLine();
            System.out.println("Server says:" + recieveSTR);

            // Initialising objects to hold server information and job requirements
            String lType = ""; // Largest Server Name
            int job = 0; // jobID always start at 0
            int serverID = 0; // ServerID always start from 0

            int capableCore = 0; // initatilised to a default value
            int capableMem = 0; // initatilised to a default value
            int capableDisk = 0; // initatilised to a default value

            // Loop for querying and scheduling jobs to servers
            while (!recieveSTR.equals("NONE")) {
                // Queries for job
                sendSTR = "REDY\n";
                dout.write((sendSTR).getBytes());
                dout.flush();
                System.out.println("Client says:" + sendSTR);

                recieveSTR = din.readLine();
                System.out.println("Server says:" + recieveSTR);

                String[] capCheck = recieveSTR.split(" ");

                // Checks for job information
                if (capCheck[0].equals("JOBN")) {
                    capableCore = Integer.parseInt(capCheck[4]);
                    capableMem = Integer.parseInt(capCheck[5]);
                    capableDisk = Integer.parseInt(capCheck[6]);
                    job = Integer.parseInt(capCheck[2]);
                    System.out.println("checked");

                    // Queries for available servers with the job requirements
                    sendSTR = "GETS Available" + " " + capableCore + " " + capableMem + " " + capableDisk + "\n";
                    dout.write((sendSTR).getBytes());
                    dout.flush();
                    System.out.println("Client says:" + sendSTR);

                    recieveSTR = din.readLine();
                    System.out.println("Server says:" + recieveSTR);

                    String[] dataArr = recieveSTR.split(" ");
                    Integer nRecs = Integer.parseInt(dataArr[1]);

                    sendSTR = "OK\n";
                    dout.write((sendSTR).getBytes());
                    dout.flush();
                    System.out.println("Client says:" + sendSTR);

                    // If statement to check if no available servers
                    // to then search for capable servers
                    if (nRecs.equals(0)) {
                        recieveSTR = din.readLine();
                        System.out.println("Server says:" + recieveSTR);

                        // Queries for capable servers with job requirements
                        sendSTR = "GETS Capable" + " " + capableCore + " " + capableMem + " " + capableDisk + "\n";
                        dout.write((sendSTR).getBytes());
                        dout.flush();
                        System.out.println("Client says:" + sendSTR);

                        recieveSTR = din.readLine();
                        System.out.println("Server says:" + recieveSTR);

                        dataArr = recieveSTR.split(" ");
                        nRecs = Integer.parseInt(dataArr[1]);

                        sendSTR = "OK\n";
                        dout.write((sendSTR).getBytes());
                        dout.flush();
                        System.out.println("Client says:" + sendSTR);
                        capFlag = true;

                    }
                    // Parsing through server information
                    for (int i = 0; i < nRecs; i++) {
                        recieveSTR = din.readLine();
                        System.out.println("Server says:" + recieveSTR);

                        String[] serverINFO = recieveSTR.split(" ");

                        if (i == 0) {
                            // Stores first relevant servers information for scheduling
                            lType = serverINFO[0];
                            serverID = Integer.parseInt(serverINFO[1]);
                        }
                    }

                    // if (capFlag != true) {
                        sendSTR = "OK\n";
                        dout.write((sendSTR).getBytes());
                        dout.flush();
                        System.out.println("Client says:" + sendSTR);

                        recieveSTR = din.readLine();
                        System.out.println("Server says:" + recieveSTR);
                    // }
                    capFlag = false;

                    // Schedules job to a relevant server
                    sendSTR = "SCHD " + job + " " + lType + " " + serverID + "\n";
                    dout.write((sendSTR).getBytes());
                    dout.flush();
                    System.out.println("Client says:" + sendSTR);

                    recieveSTR = din.readLine();
                    System.out.println("Server says:" + recieveSTR);

                }

            }
            // Quitting protocol and terminating socket
            sendSTR = "QUIT\n";
            dout.write((sendSTR).getBytes());
            dout.flush();

            recieveSTR = din.readLine();
            System.out.println("Server says:" + recieveSTR);

            dout.close();
            s.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}