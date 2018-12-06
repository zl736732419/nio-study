package socket.channel;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
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
public class Client {
    public static void main(String[] args) throws Exception {
        SocketChannel channel = SocketChannel.open(new InetSocketAddress("localhost", 8090));
        String message = "hello!";
        byte[] bytes = message.getBytes(StandardCharsets.UTF_8);
        ByteBuffer buffer;
        ByteBuffer readBuffer = ByteBuffer.allocate(16 * 1024);
        WritableByteChannel console = Channels.newChannel(System.out);
        while (true) {
            buffer = ByteBuffer.wrap(bytes);
            channel.write(buffer);
            channel.read(readBuffer);
            readBuffer.flip();
            console.write(readBuffer);
            System.out.println();
            Thread.sleep(1000);
        }
    }
}
