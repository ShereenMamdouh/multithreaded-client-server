/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package start2;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintStream;
import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;

/**
 *
 * @author Sherin
 */
public class Start {

    
    
    @SuppressWarnings("empty-statement")
    public static void main(String args[]) throws InterruptedException, IOException {

        // The server socket.
        ServerSocket serverSocket = null;
        // The client socket.
        Socket clientSocket = null;

        String fileName = "sys.txt";
        String line = null;
        String servIP = null;
        int servPort = 0;
        int i = 0;
        int readers = 0;
        int writers = 0;
        int access = 0;
        try {
// FileReader reads text files in the default encoding.
            FileReader fileReader
                    = new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader
                    = new BufferedReader(fileReader);

            while ((line = bufferedReader.readLine()) != null) {

                String[] parts = line.split("=");

                if (i == 0) {
                    servIP = parts[1];
                    System.out.println(servIP);
                }
                if (i == 1) {
                    servPort = Integer.parseInt(parts[1]);
                    System.out.println(servPort);
                }
                if (i == 2) {
                    readers = Integer.parseInt(parts[1]);
                    for (int j = 0; j < readers; j++) {
                        bufferedReader.readLine();
                    }
                    System.out.println(readers);
                }
                if (i == 3) {
                    writers = Integer.parseInt(parts[1]);
                    for (int j = 0; j < writers; j++) {
                        bufferedReader.readLine();
                    }
                    System.out.println(writers);
                }
                if (i == 4) {
                    access = Integer.parseInt(parts[1]);
                    System.out.println(access);
                }

                System.out.println(line);
                i++;
            }

            // Always close files.
            bufferedReader.close();
        } catch (FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '"
                    + fileName + "'");
        } catch (IOException ex) {
            System.out.println(
                    "Error reading file '"
                    + fileName + "'");
        }
        // Or we could just do this: 
        // ex.printStackTrace();
        Object MUTEX = 1;
        FileInputStream in = null;
        FileOutputStream out = null;
        //*** on 2 laptops *****//
       // server mainserver = new server(servIP, servPort, "server", MUTEX);
        //mainserver.start();
        
        int count = 0;
        
        for (int k = 0; k < readers; k++) {
            for (int j = 0; j < access; j++) {
              
                reader reader11 = new reader(servIP, servPort, "reader" + k, MUTEX);
                reader11.start();
                count++;
              //  while (reader11.stopRunning());

            }
        }
          for (int k = 0; k < writers; k++) {
            for (int j = 0; j < access; j++) {  
                
                writer writer11 = new writer(servIP, servPort, "reader" + k, MUTEX);
                writer11.start();
                count++;
                //while (writer11.stopRunning());
    } }}
}

