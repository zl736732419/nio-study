package socket.channel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.StandardCharsets;

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
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.bind(new InetSocketAddress("localhost", 8090));
        while (true) {
            System.out.println("server started...");
            SocketChannel sc = ssc.accept();
            new Thread(new MySocketHandler(sc)).start();
        }
        
    }
    
    private static class MySocketHandler implements Runnable {
        private SocketChannel sc;
        public MySocketHandler(SocketChannel sc) {
            this.sc = sc;
        }

        @Override
        public void run() {
            ByteBuffer buffer = ByteBuffer.allocate(16 * 1024);
            WritableByteChannel console = Channels.newChannel(System.out);
            try {
                int count = 0;
                while (-1 != sc.read(buffer) && count++ != 5) {
                    System.out.print("read client msg: ");
                    buffer.flip();
                    console.write(buffer);
                    System.out.println();
                    buffer.clear();
                    // 回复消息
                    String message = "ok!";
                    ByteBuffer writeBuffer = ByteBuffer.wrap(message.getBytes(StandardCharsets.UTF_8));
                    sc.write(writeBuffer);
                }
                sc.close();
                String message = "ok!";
                ByteBuffer writeBuffer = ByteBuffer.wrap(message.getBytes(StandardCharsets.UTF_8));
                sc.write(writeBuffer);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    console.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
