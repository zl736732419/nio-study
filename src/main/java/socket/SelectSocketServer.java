package socket;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

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
 *  2018年11月30日			zhenglian			    Initial.
 *
 * </pre>
 */
public class SelectSocketServer {
    
    private static ByteBuffer buffer = ByteBuffer.allocate(100);
    
    public static void main(String[] args) throws Exception {
        ServerSocketChannel ssc =ServerSocketChannel.open();
        ssc.bind(new InetSocketAddress("localhost", 1234));
        // 设置为非阻塞状态，通道注册selector时，不允许通道为阻塞模式
        ssc.configureBlocking(false);
        // 将ssc注册为accept接收操作
        Selector selector = Selector.open();
        // 由OS来决定就绪状态
        ssc.register(selector, SelectionKey.OP_ACCEPT);
        while(true) {
            // 选择就绪的通道
            System.out.println("server start...");
            int select = selector.select();
            // 可能会出现阻塞中断但还没有就绪选择的情况，这里需要做判断
            if (0 == select) {
                continue;
            }
            System.out.println("1 client connected to server.");
            // 遍历已就绪的连接
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            SelectionKey key;
            while (iterator.hasNext()) {
                key = iterator.next();
                // 已经接收到请求,并已经就绪accept
                if (key.isAcceptable()) {
                    ssc = (ServerSocketChannel) key.channel();
                    SocketChannel sc = ssc.accept();
                    registerChannel(sc, selector, SelectionKey.OP_READ);
                    sayHello(sc);
                } else if (key.isReadable()) {
                    read(key);
                }
                // 当前就绪连接已经处理，移除就绪列表
                iterator.remove();
            }
        }
    }

    private static void sayHello(SocketChannel sc) throws Exception {
        buffer.clear();
        buffer.put("Hi there!".getBytes(StandardCharsets.UTF_8));
        buffer.flip();
        sc.write(buffer);
    }

    private static void registerChannel(SocketChannel sc, Selector selector, int ops) throws Exception {
        if (sc == null) {
            return;
        }
        // 选择器注册要求channel非阻塞，否则会抛出异常
        sc.configureBlocking(false);
        sc.register(selector, ops);
    }

    private static void read(SelectionKey key) throws Exception {
        SocketChannel sc = (SocketChannel) key.channel();
        WritableByteChannel out = Channels.newChannel(System.out);
        buffer.clear();
        int count = 0;
        while((count = sc.read(buffer)) > 0) {
            buffer.flip();
            if (buffer.hasRemaining()) {
                out.write(buffer);
            }
            buffer.clear();
        }
        // 没有数据请求，则直接断开当前客户端连接
        if (count < 0) {
            sc.close();
        }
    }
}
