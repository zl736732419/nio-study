package thread;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

/**
 * <pre>
 *
 *  File:
 *
 *  Copyright (c) 2016, globalegrow.com All Rights Reserved.
 *
 *  Description:
 *  ForkJoin框架，将多任务拆分成小任务，然后分别有不同的工作线程进行执行，这一步称为fork
 *  所有子任务执行完成后，结果放入统一队列中，通过另外一个单独线程对任务结果进行合并，这一步成为join
 *  ForkJoin框架使用：
 *  1+2+3+4
 * 
 *  Revision History
 *  Date,					Who,					What;
 *  2018年12月14日			zhenglian			    Initial.
 *
 * </pre>
 */
public class ForkJoinTest {
    
    private static class CountTask extends RecursiveTask<Integer> {

        /**
         * 单个任务允许计算的最大数字总数，这里表示每个任务只能处理两个数相加
         */
        private static final Integer THRESHOD = 2;
        
        private int start;
        private int end;
        
        public CountTask(int start, int end) {
            this.start = start;
            this.end = end;
        }
        
        @Override
        protected Integer compute() {
            // 这里对给定的start,end进行任务拆分
            int sum = 0;
            if (!canComplete()) {
                // 单任务执行
                for (int i = start; i <= end; i++) {
                    sum += i;
                }
            } else {
                // 参与计算的数字太多，需要进行子任务拆分
                int middle = (start + end) / 2;
                CountTask subTask1 = new CountTask(start, middle);
                CountTask subTask2 = new CountTask(middle+1, end);
                // 执行子任务
                subTask1.fork();
                subTask2.fork();
                
                int result1 = subTask1.join();
                int result2 = subTask2.join();
                sum = result1 + result2;
            }
            return sum;
        }
        
        private boolean canComplete() {
            return (end - start) + 1 <= THRESHOD;
        }
    }
    
    
    public static void main(String[] args) throws Exception {
        ForkJoinPool pool = new ForkJoinPool();
        CountTask task = new CountTask(1, 4);
        
        Future<Integer> future = pool.submit(task);
        System.out.println(future.get());
    }
}
