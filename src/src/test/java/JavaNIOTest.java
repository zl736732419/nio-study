import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

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
 *  2018年11月21日			zhenglian			    Initial.
 *
 * </pre>
 */
public class JavaNIOTest {
    
    @Test
    public void printByteOrder() {
        System.out.println(ByteOrder.nativeOrder().toString());
    }
    
    @Test
    public void viewOrder() {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        buffer.position(3).limit(5);
        ByteBuffer view = buffer.slice();
        ByteOrder order = view.order();
        System.out.println("view order: " + order.toString());
        view.order(ByteOrder.LITTLE_ENDIAN);
        order = view.order();
        System.out.println("view order: " + order.toString());
    }
}
