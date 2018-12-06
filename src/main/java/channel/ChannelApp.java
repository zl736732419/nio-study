package channel;

import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
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
 *  2018年11月26日			zhenglian			    Initial.
 *
 * </pre>
 */
public class ChannelApp {
    public static void main(String[] args) throws Exception {
        new ChannelApp().copy2();
    }
    
    public void copy1() throws Exception {
        ReadableByteChannel source = Channels.newChannel(System.in);
        WritableByteChannel target = Channels.newChannel(System.out);
        ByteBuffer buffer = ByteBuffer.allocate(1024 * 16);
        while (source.read(buffer) != -1) {
            // 从buffer开头读取数据
            buffer.flip();
            target.write(buffer);
            // 将buffer压缩，将已经读取的数据清除
            buffer.compact();
        }
        buffer.flip();
        // buffer.hasRemaining?
        if (buffer.hasRemaining()) {
            target.write(buffer);
        }
    }
    
    public void copy2() throws Exception {
        ReadableByteChannel source = Channels.newChannel(System.in);
        WritableByteChannel target = Channels.newChannel(System.out);
        ByteBuffer buffer = ByteBuffer.allocate(1024 * 16);
        while (source.read(buffer) > 0) {
            buffer.flip();
            while (buffer.hasRemaining()) {
                target.write(buffer);
            }
            buffer.clear();
        }
        
    }
}
