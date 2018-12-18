package thread;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *
 *  File:
 *
 *  Copyright (c) 2016, globalegrow.com All Rights Reserved.
 *
 *  Description:
 *  实现公平锁
 *
 *  Revision History
 *  Date,					Who,					What;
 *  2018年12月18日			zhenglian			    Initial.
 *
 * </pre>
 */
public class FairTest {
    
    public static class QueueObject {
        private Thread currentThread = Thread.currentThread();
        // 用于自旋锁避免假唤醒
        private boolean signed = false;
        
        public synchronized void doWait() {
            while (!signed) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // 重置锁状态，让其他线程进入假唤醒
            signed = false;
        }
        
        public synchronized void doNotify() {
            signed = true;
            notify();
        }

        @Override
        public String toString() {
            return "[" + currentThread.getName() + "] queueObject";
        }
    }

    /**
     * 实现公平锁
     */
    public static class FairLock {
        private boolean isLocked = false;
        private List<QueueObject> waitingQueues = new ArrayList<>();
        
        public void lock() {
            QueueObject queueObject = new QueueObject();
            // 将锁信息入队列
            synchronized (this) {
                waitingQueues.add(queueObject);
                System.out.println("current queue contains: [ " + waitingQueues + " ]");
            }
            
            boolean isLockedForThisThread = true;
            while(isLockedForThisThread) {
                synchronized (this) {
                    isLockedForThisThread = isLocked || queueObject != waitingQueues.get(0);
                    if (!isLockedForThisThread) { // 没有锁定同时当前queueObject位于队头
                        isLocked = true;
                        waitingQueues.remove(queueObject);
                        System.out.println(queueObject + " get lock");
                        return;
                    }
                }
                queueObject.doWait();
            }
        }
        
        public void unlock() {
            isLocked = false;
            if (waitingQueues.size() > 0) {
                QueueObject queueObject = waitingQueues.get(0);
                queueObject.doNotify();
            }
        }
    }
    
    public static void main(String[] args) throws Exception {
        FairLock fairLock = new FairLock();
        Thread thread;
        int num = 5;
        for (int i = 0; i < num; i++) {
            thread = new Thread(() -> fairLock.lock());
            thread.setName("thread-" + (i+1));
            thread.start();
        }
        
        for (int i = 0; i < (num - 1); i++) {
            Thread.sleep(1000);
            fairLock.unlock();
        }
    }
    
}
