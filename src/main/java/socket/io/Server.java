package socket.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * <pre>
 *
 *  File:
 *
 *  Copyright (c) 2016, globalegrow.com All Rights Reserved.
 *
 *  Description:
 *  TODO
 *
 *  Revision History
 *  Date,					Who,					What;
 *  2018年11月27日			zhenglian			    Initial.
 *
 * </pre>
 */
public class Server {
    
    public static void main(String[] args) throws Exception {
        ServerSocket ss = new ServerSocket();
        ss.bind(new InetSocketAddress("localhost", 8090));
        Socket socket;
        while (true) {
            System.out.println("server started...");
            socket = ss.accept();
            new Thread(new MyThreadHandler(socket)).start();
        }
    }
    
    private static class MyThreadHandler implements Runnable {
        private Socket socket;
        public MyThreadHandler(Socket socket) {
            this.socket =socket;
        }

        @Override
        public void run() {
            BufferedReader reader = null;
            PrintStream out = null;
            try {
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintStream(socket.getOutputStream());
                int count = 0;
                while (count++ < 5) {
                    String message = reader.readLine();
                    System.out.println("recieve: " + message);
                    out.println(message + " ok!");
                }
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    out.close();
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
}
