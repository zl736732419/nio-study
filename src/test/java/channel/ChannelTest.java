package channel;

import org.junit.Test;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.ServerSocketChannel;

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
 *  2018年11月26日			zhenglian			    Initial.
 *
 * </pre>
 */
public class ChannelTest {
    
    @Test
    public void getSocketChannel() throws Exception {
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ServerSocket ss = ssc.socket();
        ss.bind(new InetSocketAddress("localhost", 8082));
        ServerSocketChannel channel = ss.getChannel();
        System.out.println(channel);
    }
}
