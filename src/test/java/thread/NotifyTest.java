package thread;

/**
 * <pre>
 *
 *  File:
 *
 *  Copyright (c) 2016, globalegrow.com All Rights Reserved.
 *
 *  Description:
 *  这个例子想要说明的是，在调用Object.wait(), Object.notify(), Object.notifyAll()这些方法时，需要限定这些方法执行的范围是在
 *  同步代码块中，同时这个同步代码块对应的线程持有的锁应该是这个对象的锁才行，否则将会抛出IllegalMonitorStateException异常
 *
 *  Revision History
 *  Date,					Who,					What;
 *  2018年12月17日			zhenglian			    Initial.
 *
 * </pre>
 */
public class NotifyTest {
    /**
     * 将所有线程锁操作封装到一个对象中
     */
    private static class WaitNotify {
        private static Object monitor = new Object();
        /**
         * 防止信号丢失，将信号封装到锁对象中
         */
        private volatile boolean signal = false;

        public void doNotify() {
            synchronized (monitor) {
                signal = true;
                monitor.notify();
            }
        }
        
        public void doWait() {
            synchronized (monitor) {
                System.out.println("[" + Thread.currentThread().getName() + "] enter sync area...");
                // 这里需要处理假唤醒（线程由于某种情况被隐式唤醒），自旋锁
                while (!signal) {
                    try {
                        monitor.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("[" + Thread.currentThread().getName() + "] unlock wait status...");
                }
                signal = false;
                System.out.println("[" + Thread.currentThread().getName() + "] do something...");
            }
        }
        
        public void doNotifyAll() {
            synchronized (monitor) {
                signal = true;
                monitor.notifyAll();
                System.out.println("notify all wait thread");
            }
        }
        
    }

    private static class PrintCls {
        private WaitNotify waitNotify;

        public PrintCls(WaitNotify waitNotify) {
            this.waitNotify = waitNotify;
        }

        public void print(String msg) {
            waitNotify.doWait();
            System.out.println(msg);
        }
    }

    private static class ChangeCls {
        private WaitNotify waitNotify;
        
        public ChangeCls(WaitNotify waitNotify) {
            this.waitNotify = waitNotify;
        }

        public void change() {
            waitNotify.doNotify();
        }
    }

    public static void main(String[] args) throws Exception {
        WaitNotify waitNotify = new WaitNotify();
        PrintCls printCls = new PrintCls(waitNotify);
        ChangeCls changeCls = new ChangeCls(waitNotify);
        
        multiPrinter(printCls, "hello");
        multiPrinter(printCls, "world");
        Thread.sleep(1000);
        waitNotify.doNotifyAll();

//        print(printCls, changeCls, "hello world");
//        Thread.sleep(3000);
//        print(printCls, changeCls, "how are you?");
    }
    
    public static void multiPrinter(PrintCls printCls, String message) {
        new Thread(() -> {
            try {
                printCls.print(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
    
    public static void print(PrintCls printCls, ChangeCls changeCls, String message) throws Exception {
        new Thread(() -> {
            try {
                printCls.print(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        Thread.sleep(1000);
        changeCls.change();
    }
}
