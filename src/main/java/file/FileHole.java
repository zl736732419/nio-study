package file;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 文件空洞
 * 表示文件内容不连续，中间出现没有填充数据的情况，这种情况下，文件会以0填充但不占用磁盘空间
 */
public class FileHole {
    public static void main(String[] argv) throws IOException {
        File temp = new File("holy.txt");
        RandomAccessFile file = new RandomAccessFile(temp, "rw");
        FileChannel channel = file.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(100);
        putData(0, byteBuffer, channel);
        putData(5000000, byteBuffer, channel);
        putData(50000, byteBuffer, channel);
        System.out.println("Wrote temp file '" + temp.getPath() + "', size=" + channel.size());
        channel.close();
        file.close();
    }

    private static void putData(int position, ByteBuffer buffer, FileChannel channel) throws IOException {
        String string = "*<-- location " + position;
        buffer.clear();
        buffer.put(string.getBytes("US-ASCII"));
        buffer.flip();
        channel.position(position);
        channel.write(buffer);
    }
}