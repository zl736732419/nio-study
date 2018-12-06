import java.text.MessageFormat;
import java.util.concurrent.CountDownLatch;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <pre>
 *
 *  File: ELog1.java
 *
 *  Copyright (c) 2016, globalegrow.com All Rights Reserved.
 *
 *  Description:
 *  TODO
 *
 *  Revision History
 *  Date,					Who,					What;
 *  2018年07月18日				zhenglian				.
 *
 * </pre>
 */
public class Helloworld {

    private final CountDownLatch keepAliveLatch = new CountDownLatch(1);

    public static void main(String[] args) {

//      new Helloworld().testMessageFormat();
//        new Helloworld().runningThread();
        new Helloworld().portMatcher();

    }

    private void portMatcher() {
        Pattern p = Pattern.compile("^.+[:]\\d{1,5}\\s*$");
        Matcher matcher = p.matcher("192.168.10:5200    ");
        System.out.println(matcher.matches());
    }

    private void testMessageFormat() {
        String pattern = "oh, ''{0}'' is a pig";
        System.out.println(MessageFormat.format(pattern, "zhangsan"));
        System.out.println(MessageFormat.format("'{0}'", "what"));
    }

    public void runningThread() {
        Thread thread = new Thread(new Runnable() {
            public void run() {
                try {
                    System.out.println("thread start...");
                    keepAliveLatch.await();
                } catch(Exception e) {
                }
            }
        });
        thread.setDaemon(false);
        // Keep this thread alive (non daemon thread) until we shutdown
        Runtime.getRuntime().addShutdownHook(new Thread()
        {
            @Override
            public void run()
            {
                System.out.println("shutdown...");
                keepAliveLatch.countDown();
            }
        });
        thread.start();
    }
}
