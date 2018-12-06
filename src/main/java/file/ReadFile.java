package file;

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
 *  2018年12月04日			zhenglian			    Initial.
 *
 * </pre>
 */
public class ReadFile {
    
    public static void main(String[] args) throws Exception {
        RandomAccessFile aFile = new RandomAccessFile("blahblah.txt", "rw");
        FileChannel inChannel = aFile.getChannel();
        ByteBuffer buf = ByteBuffer.allocate(48);
        while (inChannel.read(buf) != -1) {
            buf.flip();
            while(buf.hasRemaining()){
                System.out.print((char) buf.get());
            }
            buf.clear();
        }
        inChannel.close();
    }
}
