package file;

import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

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
 *  2018年12月06日			zhenglian			    Initial.
 *
 * </pre>
 */
public class PathTest {
    
    @Test
    public void paths() {
        // /dir1/dir2将会解析为当前磁盘驱动器根目录的相对位置：D:\hello\1.txt
        Path path = Paths.get("/hello/1.txt");
        System.out.println(path.toAbsolutePath());
        // relative path D:\hello\1.txt
        path = Paths.get("D:\\hello", "1.txt");
        System.out.println(path.toAbsolutePath());
        
        // 特殊目录符号 . , ..,需要使用normalize进行特殊处理，否则. ..会当成普通字符串作为路径的一部分
        path = Paths.get("D:\\hello\\world\\..\\1.txt");
        path = path.normalize();
        System.out.println(path.toAbsolutePath());

        path = Paths.get("D:\\hello\\.\\1.txt");
        path = path.normalize();
        System.out.println(path.toAbsolutePath());
    }
    
    
    
}
