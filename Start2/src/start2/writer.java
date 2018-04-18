/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package start2;

/**
 *
 * @author Micro Systems
 */
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import static java.lang.Thread.sleep;
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
 * @author Sherin
 */
public class writer extends Thread {

    public static String IP;
    public static int port;
    private Thread t;
    String threadName;
    public Socket clientSocket = null;
    public int flag = 0;
     boolean  k=true;
    Object MUTEX;
    int id;

    writer(String IP, int port, String name,Object MUTEX) throws IOException {
        this.threadName = name;
        this.IP = IP;
        this.port = port;
        this.MUTEX=MUTEX;
        String s = threadName.substring(threadName.length() - 1);
        this.id = Integer.parseInt(s);
        System.out.println("WRITER:in writer constructor" + threadName);
    }

    @Override
    public void run() {
        try {
            sleep(3000);
        } catch (InterruptedException ex) {
            Logger.getLogger(writer.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("WRITER :Running " + threadName);
       // while (running) {
            try { 
                clientSocket = new Socket(IP, port);
                OutputStream dOut = clientSocket.getOutputStream();
                dOut.write(2);
                dOut.write(id);
                
                System.out.println("WRITER: wrote on socket" + threadName);
              //  sleep(2000);
                while (true) {

                   // DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
                    int y = Integer.parseInt(threadName.substring(threadName.length() - 1));
                    int f =y;
                     System.out.println("WRITER SENT THIS VALUE TO SERVER : " + f + threadName);

                    dOut.write(f);
                    
                   
                    flag = 1;
                    break;

                }
                //if (flag == 1) {
           
                //   synchronized(MUTEX){
                 //      join(1000);
          //   MUTEX.wait();
           // stopRunning();
      // }
                 k=false;
            } catch (IOException ex) {
                Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
            }
           
         
     //   }
    }

    public boolean stopRunning() {
     return k;
         
    }

    @Override
    public void start() {
        System.out.println("Starting " + threadName);
        if (t == null) {
            t = new Thread(this, threadName);
            t.start();
        }

    }
}
