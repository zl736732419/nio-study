package channel;

import org.junit.Test;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

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
 *  2018年11月28日			zhenglian			    Initial.
 *
 * </pre>
 */
public class FileChannelTest {
    @Test
    public void truncate() throws Exception {
        File temp = new File("holy.txt");
        RandomAccessFile file = new RandomAccessFile(temp, "rw");
        FileChannel channel = file.getChannel();
        ByteBuffer buffer = ByteBuffer.allocateDirect(100);
        String string = "*<-- location " + 1000;
        buffer.clear();
        buffer.put(string.getBytes("US-ASCII"));
        buffer.flip();
        channel.position(1000);
        channel.write(buffer);
        System.out.println("Wrote temp file '" + temp.getPath() + "', size=" + channel.size() + ", position=" + channel.position());
        // truncate会将影响到文件的position,position = truncate size
        channel.truncate(500);
        System.out.println("new position=" + channel.position());
        channel.close();
        file.close();
    }
}
