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
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Sherin
 */
public class server extends Thread {
    //nnnn

    private static final String FILENAME = "readers.txt";
    private static final String FILENAME2 = "writers.txt";
    BufferedWriter bw;
    public static String IP;
    public static int port;
    public Socket clientSocket = null;
    private Thread t;
    String threadName;
    int Oval = 5;
    public int flag = 0;
    public int flag2 = 0;
    Object MUTEX;
    public static int count = 0;
    int id = 0;

    server(String IP, int port, String name, Object MUTEX) throws IOException, InterruptedException {
        this.IP = IP;
        this.port = port;
        threadName = name;
        this.MUTEX = MUTEX;
    }

    @Override
    public void run() {
        System.out.println("SERVER: Running " + threadName);
        ServerSocket serv = null;
        try {
            serv = new ServerSocket(port);
            while (true) {

                clientSocket = serv.accept();
                sleep(1000);
                System.out.println("SERVER: connection accepted");

                if (clientSocket.getInputStream().available() != 0) {
                    DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
                    OutputStream dOut = clientSocket.getOutputStream();
                    int f = dis.read();

                    System.out.println(f);
                    if (f == 2) {
                        count++;

                        while (true) {
                            if (flag2 == 1) {
                                break;
                            }
                            if (clientSocket.getInputStream().available() != 0) {
                                id = dis.read();
                                System.out.println(" SERVER :ID new: " + id);
                                flag2 = 1;
                                // break;

                            }
                        }

                        System.out.println("SERVER write request");
                        while (true) {
                            if (flag == 1) {
                                break;
                            }
                            if (clientSocket.getInputStream().available() != 0) {
                                f = dis.read();
                                System.out.println(" SERVER :Oval new: " + f);
                                Oval = f;
                                flag = 1;
                                // break;

                            }

                            try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILENAME, true))) {
                                String content = "\n" + count + "\t" + Oval + "\t" + id + "\n";
                                bw.write(content);
                                bw.newLine();
                                bw.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                        flag = 0;
                        flag2 = 0;

                    } else if (f == 1) {

                        count++;
                        System.out.println("SERVER: read request ");
                        while (true) {
                            if (flag2 == 1) {
                                break;
                            }
                            if (clientSocket.getInputStream().available() != 0) {
                                id = dis.read();
                                System.out.println(" SERVER :ID new: " + id);
                                flag2 = 1;
                                // break;

                            }
                        }
                        dOut.write(Oval);
                        System.out.println("SERVER: wrote on socket and sleeping");
                        System.out.println("SERVER:rrrr");
                        
                            try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILENAME2, true))) {
                                String content = "\n" + count + "\t" + Oval + "\t" + id + "\n";
                                bw.write(content);
                                bw.newLine();
                                bw.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                    } else {
                        System.out.println("unknown request");
                    }

                }

            }
        } catch (IOException ex) {
            Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void start() {
        System.out.println("SERVER : Starting " + threadName);
        if (t == null) {
            t = new Thread(this, threadName);
            t.start();
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILENAME))) {
            String content = "sSeq\toVal\trID\trNum\n";
            bw.write(content);
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILENAME2))) {
            String content = "sSeq\toVal\twID\n";
            bw.write(content);
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
