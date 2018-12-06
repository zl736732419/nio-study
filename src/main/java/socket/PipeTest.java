package socket;

import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.Pipe;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.util.Random;

/**
 * <pre>
 *
 *  File:
 *
 *  Copyright (c) 2016, globalegrow.com All Rights Reserved.
 *
 *  Description:
 *  pipe提供一组read-write channel,用来单向传输数据
 *  通过pipe.sink写数据,然后通过pipe.source读数据
 *
 *  Revision History
 *  Date,					Who,					What;
 *  2018年11月29日			zhenglian			    Initial.
 *
 * </pre>
 */
public class PipeTest {
    
    public static void main(String[] args) throws Exception {
        WritableByteChannel out = Channels.newChannel(System.out);
        ReadableByteChannel workerChannel = startWorker (10);
        ByteBuffer buffer = ByteBuffer.allocate(100);
        while (workerChannel.read(buffer) > 0) {
            buffer.flip();
            while (buffer.hasRemaining()) {
                out.write(buffer);
            }
            buffer.clear();
        }
        out.close();
        workerChannel.close();
    }

    private static ReadableByteChannel startWorker(int reps) throws Exception {
        Pipe pipe = Pipe.open();
        Worker worker = new Worker(pipe.sink(), reps);
        worker.start();
        return pipe.source();
    }
    
    private static class Worker extends Thread {
        private Pipe.SinkChannel sink;
        private Integer reps;

        private String [] products = { "No good deed goes unpunished", "To be, or what?",
                "No matter where you go, there you are",
                "Just say \"Yo\"", "My karma ran over my dogma" }; 
        private Random rand = new Random( );
        
        public Worker(Pipe.SinkChannel sink, Integer reps) {
            this.sink = sink;
            this.reps = reps;
        }
        
        @Override
        public void run() {
            ByteBuffer buffer = ByteBuffer.allocate (100);
            try {
                for (int i = 0; i < reps; i++) {
                    doSomeWork (buffer);
                    while (sink.write (buffer) > 0) {
                    }
                }
                sink.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void doSomeWork(ByteBuffer buffer) {
            int product = rand.nextInt (products.length);
            buffer.clear( ); 
            buffer.put (products [product].getBytes( )); 
            buffer.put ("\r\n".getBytes( )); 
            buffer.flip( );
        }
    }
}
