package socket;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class ChannelAccept {
    public static final String GREETING = "Hello I must be going.\r\n";

    public static void main(String[] argv) throws Exception {
        int port = 1234; // default 
        if (argv.length > 0) {
            port = Integer.parseInt(argv[0]);
        }
        ByteBuffer buffer = ByteBuffer.wrap(GREETING.getBytes());
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.bind(new InetSocketAddress(port));
        // 设置为非阻塞模式
        ssc.configureBlocking(false);
        while (true) {
            System.out.println("Waiting for connections");
            SocketChannel sc = ssc.accept();
            // 非阻塞模式进行客户端监听
            if (sc == null) { // no connections, snooze a while 
                Thread.sleep(2000);
            } else {
                System.out.println("Incoming connection from: " + sc.socket().getRemoteSocketAddress());
                buffer.rewind();
                sc.write(buffer);
                sc.close();
            }
        }
    }
}