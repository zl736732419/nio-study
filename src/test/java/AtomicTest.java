import org.junit.Test;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

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
 *  2018年12月08日			zhenglian			    Initial.
 *
 * </pre>
 */
public class AtomicTest {
    
    private static class Inner {
        private Integer value;

        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }
    }
    
    @Test
    public void atomicReference() {
        Inner inner = new Inner();
        inner.setValue(1);

        Inner innerNew = new Inner();
        innerNew.setValue(5);
        
        AtomicReference<Inner> obj = new AtomicReference<>(inner);
        obj.compareAndSet(inner, innerNew);

        System.out.println(obj.get().value);
    }
    
    @Test
    public void atomicStampedReference() {
        Inner inner = new Inner();
        inner.setValue(1);

        AtomicStampedReference<Inner> obj = new AtomicStampedReference<>(inner, 1);
    }
    
}
