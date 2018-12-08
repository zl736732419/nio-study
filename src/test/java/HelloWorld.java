import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.Test;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

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
 *  2018年07月18日			zhenglian				Initial.
 *
 * </pre>
 */
public class HelloWorld {
    @Test
    public void parseNumber() {
        String string = "1234    ";
        System.out.println(Integer.parseInt(string));
    }

    @Test
    public void testChar() {
        char a = '\001';
        System.out.println(a);
    }

    @Test
    public void substring() {
        String userDir = System.getProperty("user.dir");
        System.out.println("userDir: " + userDir);
        System.out.println("substringAfterLast: ");
        System.out.println(StringUtils.substringAfterLast(userDir, File.separator));
        System.out.println("beforeLast: ");
        System.out.println(StringUtils.substringBeforeLast(userDir, File.separator));
    }

    @Test
    public void getPid() {
        RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
        String runtimeName = runtime.getName();
        System.out.println(runtimeName);
        int pid = NumberUtils.toInt(StringUtils.substringBefore(runtimeName, "@"), 0);
        System.out.println(pid);
    }

    @Test
    public void readJson() throws Exception {
        String json = "{\"name\": \"zhangsan\"}";
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(json);
        System.out.println(jsonNode);
    }

}
