package file;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;

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
public class FilesTest {
    @Test
    public void exists() throws Exception {
        // exists
        Path path = Paths.get("D:\\hello\\1.txt");
        System.out.println(Files.exists(path, LinkOption.NOFOLLOW_LINKS)); // false
        path = Paths.get("D:\\projects\\work-study\\blahblah.txt");
        System.out.println(Files.exists(path, LinkOption.NOFOLLOW_LINKS)); // true
        path = Paths.get("D:\\projects\\work-study\\blahblah.txt");
        System.out.println(Files.exists(path, LinkOption.NOFOLLOW_LINKS)); // true
    }
    
    @Test
    public void createDirectory() throws Exception {
        // createDirectory
        Path dir = Paths.get("D://hello");
        if (!Files.exists(dir, LinkOption.NOFOLLOW_LINKS)) {
            Files.createDirectory(dir);
        }
    }
    
    @Test
    public void copyFile() throws Exception {
        Path source = Paths.get("D:\\projects\\work-study\\blahblah.txt");
        Path target = Paths.get("D://hello", "1.txt");
        Files.copy(source, target);
    }
    
    @Test
    public void forceCopyFile() throws Exception {
        Path source = Paths.get("D:\\projects\\work-study\\MappedHttp.out");
        Path target = Paths.get("D://hello", "1.txt");
        Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
    }
    
    @Test
    public void move() throws Exception {
        Path source = Paths.get("D:\\projects\\work-study\\blahblah.txt");
        Path target = Paths.get("D://hello", "1.txt");
        Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
    }
    
    @Test
    public void delete() throws Exception {
        // delete file or empty directory
        Path path = Paths.get("D://hello", "1.txt");
        Files.delete(path);
    }
    
    @Test
    public void deleteFully() throws Exception {
        // 完全清空整个目录，即使目录中有子文件
        Path path = Paths.get("D:\\projects\\work-study\\target1");
        if (Files.isDirectory(path)) {
            Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    System.out.println("delete file: " + file.toString());
                    Files.delete(file);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    Files.delete(dir);
                    System.out.println("delete dir: " + dir);
                    return FileVisitResult.CONTINUE;
                }
            });
        } else {
            System.out.println("delete file: " + path.toString());
            Files.delete(path);
        }
    }
    
    @Test
    public void walkFiles() {
        // 实现按照树形结构打印目录
        Path path = Paths.get("D:\\projects\\work-study");
        printDirectoryTree(path, 0);
    }

    private void printDirectoryTree(Path path, int level) {
        if (Files.isDirectory(path)) {
            try {
                Files.walkFileTree(path, new MySimpleFileVisitor(level));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            printFileName(path, level);
        }
    }
    
    private class MySimpleFileVisitor extends SimpleFileVisitor<Path> {
        private int level;
        
        public MySimpleFileVisitor(int level) {
            this.level = level;
        }

        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
            printFileName(dir, ++level);
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
            level--;
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            printDirectoryTree(file, level + 1);
            return FileVisitResult.CONTINUE;
        }
    }
    
    private void printFileName(Path file, int level) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < level; i++) {
            builder.append("-");
        }
        builder.append(file.toFile().getName());
        System.out.println(builder.toString());
    }
    
    @Test
    public void size() throws Exception {
        // 单个文件大小
        Path path = Paths.get("D:\\projects\\work-study");
        long size = Files.size(path);
        System.out.println(size);
    }

    @Test
    public void totalSize() throws Exception {
        // 统计文件大小(包括文件夹或者文件),文件夹的大小统计的是其子文件的大小总和
        Path path = Paths.get("D:\\projects\\work-study");
        Map<String, Long> map = new HashMap<>();
        map.put("size", 0L);
        if (Files.isDirectory(path)) {
            Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    sumFileSize(map, Files.size(file));
                    return FileVisitResult.CONTINUE;
                }
            });
        } else {
            sumFileSize(map, Files.size(path));
        }
        long total = map.get("size");
        System.out.println((total / 1024) + "KB");
    }
    
    private void sumFileSize(Map<String, Long> map, Long fileSize) {
        long total = map.get("size");
        total += fileSize;
        map.put("size", total);
    }
    
}
