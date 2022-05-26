import java.io.*;
import java.net.*;

public class available {

    public static void main(String[] args) {
        try {
            Socket s = new Socket("127.0.0.1", 50000);
            BufferedReader din = new BufferedReader(new InputStreamReader(s.getInputStream()));
            DataOutputStream dout = new DataOutputStream(s.getOutputStream());

            String sendSTR = "", recieveSTR = "";
            boolean capFlag = false;
            String[][] waitArr = null;

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

            String lType = ""; // Largest Server Name
            int lCore = 0; // Largest Server Core size
            int sCount = 0; // Count of largest server
            boolean checked = false; // Checks if the client has already requested server information
            int capableCore = 0;
            int capableMem = 0;
            int capableDisk = 0;
            Integer waittime = 999999999;

            int job = 0; // jobID always start at 0
            int serverID = 0; // ServerID always start from 0

            while (!recieveSTR.equals("NONE")) {

                sendSTR = "REDY\n";
                dout.write((sendSTR).getBytes());
                dout.flush();
                System.out.println("Client says:" + sendSTR);

                recieveSTR = din.readLine();
                System.out.println("Server says:" + recieveSTR);

                String[] capCheck = recieveSTR.split(" ");

                if (capCheck[0].equals("JOBN")) {
                    // schedule job
                    capableCore = Integer.parseInt(capCheck[4]);
                    capableMem = Integer.parseInt(capCheck[5]);
                    capableDisk = Integer.parseInt(capCheck[6]);
                    job = Integer.parseInt(capCheck[2]);
                    System.out.println("checked");

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

                    // recieveSTR = din.readLine();
                    // System.out.println("Server says:" + recieveSTR);

                    // sendSTR = "OK\n";
                    // dout.write((sendSTR).getBytes());
                    // dout.flush();
                    if (nRecs.equals(0)) {
                        recieveSTR = din.readLine();
                        System.out.println("Server says:" + recieveSTR);

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
                        waitArr = new String[nRecs][];

                    }

                    for (int i = 0; i < nRecs; i++) {
                        recieveSTR = din.readLine();
                        System.out.println("Server says:" + recieveSTR);

                        String[] serverINFO = recieveSTR.split(" ");
                        if (capFlag == true) {
                            waitArr[i] = new String[] { serverINFO[0], serverINFO[1], "0" };
                        } else {
                            if (i == 0) {
                                lType = serverINFO[0];
                                serverID = Integer.parseInt(serverINFO[1]);
                            }
                        }

                        // if(i == 0) {
                        // lType = serverINFO[0];
                        // serverID = Integer.parseInt(serverINFO[1]);
                        // }
                    }
                    if (capFlag == true) {
                        sendSTR = "OK\n";
                        dout.write((sendSTR).getBytes());
                        dout.flush();
                        System.out.println("Client says:" + sendSTR);

                        recieveSTR = din.readLine();
                        System.out.println("Server says:" + recieveSTR);

                        for (int j = 0; j < nRecs; j++) {
                            sendSTR = "EJWT " + waitArr[j][0] + " " + waitArr[j][1] + "\n";
                            dout.write((sendSTR).getBytes());
                            dout.flush();
                            System.out.println("Client says:" + sendSTR);

                            recieveSTR = din.readLine();
                            System.out.println("Server says:" + recieveSTR);
                            waitArr[j][2] = recieveSTR;
                            String tester = waitArr[j][0] + waitArr[j][1] + waitArr[j][2];
                            System.out.println(tester);
                        }

                        for (Integer k = 0; k < nRecs; k++) {
                            
                            if (k.equals(0)) {
                                lType = waitArr[k][0];
                                serverID = Integer.parseInt(waitArr[k][1]);
                                waittime = Integer.parseInt(waitArr[k][2]);
                            } 
                            // else if(Integer.parseInt(waitArr[k][2]) == 0){
                            //     lType = waitArr[k][0];
                            //     serverID = Integer.parseInt(waitArr[k][1]);
                            //     waittime = Integer.parseInt(waitArr[k][2]);
                            //     break;
                            // } 
                            else {
                                if (waittime.compareTo(Integer.parseInt(waitArr[k][2])) ==1 ) {
                                    lType = waitArr[k][0];
                                    serverID = Integer.parseInt(waitArr[k][1]);
                                    waittime = Integer.parseInt(waitArr[k][2]);
                                }
                            }

                        }
                    }
                    if(capFlag != true){
                    sendSTR = "OK\n";
                    dout.write((sendSTR).getBytes());
                    dout.flush();
                    System.out.println("Client says:" + sendSTR);


                    recieveSTR = din.readLine();
                    System.out.println("Server says:" + recieveSTR);
                    }
                    capFlag = false;

                    sendSTR = "SCHD " + job + " " + lType + " " + serverID + "\n";
                    dout.write((sendSTR).getBytes());
                    dout.flush();
                    System.out.println("Client says:" + sendSTR);

                    recieveSTR = din.readLine();
                    System.out.println("Server says:" + recieveSTR);

                }
                // sendSTR = "OK\n";
                // dout.write((sendSTR).getBytes());
                // dout.flush();

                // sendSTR = "GETS Capable" +" " +capableCore+ " " + capableMem + " " +
                // capableDisk +"\n";
                // dout.write((sendSTR).getBytes());
                // dout.flush();
                // System.out.println("Client says:" + sendSTR);

                // recieveSTR = din.readLine();
                // System.out.println("Server says:" + recieveSTR);

                // String[] dataArr = recieveSTR.split(" ");
                // int nRecs = Integer.parseInt(dataArr[1]);

                // sendSTR = "OK\n";
                // dout.write((sendSTR).getBytes());
                // dout.flush();
                // System.out.println("Client says:" + sendSTR);

                // recieveSTR = din.readLine();
                // System.out.println("Server says:" + recieveSTR);

                // // sendSTR = "OK\n";
                // // dout.write((sendSTR).getBytes());
                // // dout.flush();

                // for(int i = 0; i < nRecs-1; ++i){
                // recieveSTR = din.readLine();
                // System.out.println("Server says:" + recieveSTR);

                // String[] serverINFO = recieveSTR.split(" ");
                // if(i == 0) {
                // lType = serverINFO[0];
                // serverID = Integer.parseInt(serverINFO[1]);
                // }
                // }

                // sendSTR = "OK\n";
                // dout.write((sendSTR).getBytes());
                // dout.flush();
                // System.out.println("Client says:" + sendSTR);

                // recieveSTR = din.readLine();
                // System.out.println("Server says:" + recieveSTR);

                // sendSTR = "SCHD " + job + " " + lType + " " + serverID + "\n";
                // dout.write((sendSTR).getBytes());
                // dout.flush();
                // System.out.println("Client says:" + sendSTR);

                // recieveSTR = din.readLine();
                // System.out.println("Server says:" + recieveSTR);

            }
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