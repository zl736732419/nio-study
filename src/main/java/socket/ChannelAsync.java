package socket;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.SocketChannel;
import java.nio.channels.WritableByteChannel;

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
 *  2018年11月29日			zhenglian			    Initial.
 *
 * </pre>
 */
public class ChannelAsync {
    public static void main(String[] args) {
        String host = "localhost";
        Integer port = 1234;
        InetSocketAddress address = new InetSocketAddress(host, port);
        SocketChannel sc;
        try {
            sc = SocketChannel.open();
            // 非阻塞模式
            sc.configureBlocking(false);
            sc.connect(address);
            ByteBuffer readBuffer = ByteBuffer.allocate(100);
            while (!sc.finishConnect()) {
                Thread.sleep(500);
                System.out.println("waiting client connect to server...");
            }
            while (sc.read(readBuffer) <= 0) {
                Thread.sleep(500);
                System.out.println("waiting server send message...");
            }
            WritableByteChannel out = Channels.newChannel(System.out);
            readBuffer.flip();
            while (readBuffer.hasRemaining()) {
                System.out.println("write");
                out.write(readBuffer);
            }
            System.out.println("finish");
            readBuffer.clear();
            sc.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
