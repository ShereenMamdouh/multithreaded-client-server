/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package start2;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Sherin;
 */
public class reader extends Thread {

    public static String IP;
    public static int port;
    private Thread t;
    String threadName;
    public Socket clientSocket = null;
    public int flag = 0;
    private volatile boolean running = true;
    Object MUTEX;
     boolean  k=true;
     int id;
    reader(String IP, int port, String name, Object MUTEX) throws IOException {
        this.IP = IP;
        this.port = port;
        this.MUTEX = MUTEX;
        this.threadName = name;
         String s = threadName.substring(threadName.length() - 1);
        this.id = Integer.parseInt(s);
        System.out.println("READER: in reader constructor");
    }

    @Override
    public void run() {
        try {
            sleep(3000);
        } catch (InterruptedException ex) {
            Logger.getLogger(writer.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("READER :Running " + threadName);
        ServerSocket serv = null;

            try {  
                clientSocket = new Socket(IP, port);
                OutputStream dOut = clientSocket.getOutputStream();
                dOut.write(1);
                dOut.write(id);
                while (true) {
                    if (clientSocket.getInputStream().available() != 0) {

                        DataInputStream dis = new DataInputStream(clientSocket.getInputStream());

                        int f = dis.read();
                        System.out.println("reader: reading Oval" + "   " + f + "  " + threadName);
                        //  sleep(2000);
                        flag = 1;
                        break;

                    }
                    if (flag == 1) {
                        break;
                    }  
                }        clientSocket.close();    k=false;
                  
            } catch (IOException ex) {
                Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
            }
       

      //  }  
         
    }

  public boolean stopRunning() {
     // System.out.println("STOPPING " + threadName);
     return k;
         
    }

    @Override
    public void start() {
        System.out.println("READER:Starting " + threadName);
        if (t == null) {
            t = new Thread(this, threadName);
            t.start();
        }

    }
}

