package regexp;

import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
 *  2018年12月03日			zhenglian			    Initial.
 *
 * </pre>
 */
public class RegexpTest {
    private String str = "poodle zoo";
    private String regexp = "";

    @Test
    public void split() {
        Pattern pattern = Pattern.compile("d");
        // 将拆分(limit - 1)次
        String[] split = pattern.split(str, 3);
        System.out.println(StringUtils.join(split));
    }

    @Test
    public void lookingAt() {
        // 检查模式是否从首个字符进行匹配
        Pattern pattern = Pattern.compile("p");
        Matcher matcher = pattern.matcher(str);
        System.out.println(matcher.lookingAt());
    }

    @Test
    public void find() {
        // 检查模式是否从首个字符进行匹配
        Pattern pattern = Pattern.compile("o");
        Matcher matcher = pattern.matcher(str);
        boolean result = matcher.find();
        System.out.println(result);
        // 获取查找到的子序列
        if (result) {
            System.out.println(str.substring(matcher.start(), matcher.end()));
        }

        // 继续查找，matcher会从已经找到的位置往后继续查找
        result = matcher.find();
        System.out.println(result);
        // 获取查找到的子序列
        if (result) {
            System.out.println(str.substring(matcher.start(), matcher.end()));
        }

        // 继续查找，matcher会从已经找到的位置往后继续查找
        result = matcher.find();
        System.out.println(result);
        // 获取查找到的子序列
        if (result) {
            System.out.println(str.substring(matcher.start(), matcher.end()));
        }

        // 继续查找，matcher会从已经找到的位置往后继续查找
        result = matcher.find();
        System.out.println(result);
        // 获取查找到的子序列
        if (result) {
            System.out.println(str.substring(matcher.start(), matcher.end()));
        }

        // 继续查找，matcher会从已经找到的位置往后继续查找
        result = matcher.find();
        System.out.println(result);
        // 获取查找到的子序列
        if (result) {
            System.out.println(str.substring(matcher.start(), matcher.end()));
        }
    }

    @Test
    public void replace() {
        str = "aabfooaabfooabfoob";
        regexp = "a*b";
    }

    @Test
    public void replaceGroup() {
        str = "Byte for byte";
        regexp = "([bB])yte";
    }

    @Test
    public void appendReplacement() {
        Pattern pattern = Pattern.compile("([Tt])hanks");
        Matcher matcher = pattern.matcher("Thanks, What, thanks very much");
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            if (matcher.group(1).equals("T")) {
                matcher.appendReplacement(sb, "Thank you");
            } else {
                matcher.appendReplacement(sb, "thank you");
            }
        }
        matcher.appendTail(sb);
        System.out.println(sb.toString());
    }

    @Test
    public void positiveForwardAssertion() {
        str = "hello world";
        // 向前正断定，表示hel后面的字符必须为l，但是只会包括hel，不会包括后面的字符
        regexp = "hel(?=l)";
    }
    
    @Test
    public void negativeForwardAssertion() {
        str = "hello world";
        // 向前的负断定，表示hel前面一个字符一定不是a,同时只会包括hel，不会包括后面的字符
        regexp = "hel(?!a)";
    }

    @Test
    public void positiveBackwordAssertion() {
        str = "hello world";
        // 向后匹配，e字符前面的字符一定要是h
        regexp = "(?<=h)ello";
    }
    
    @Test
    public void negativeBackwordAssertion() {
        str = "hello world";
        // 向后匹配，l前面的字符一定不是e
        regexp = "(?<!e)llo";
    }
    
    @Test
    public void positiveForwordAssertionIgnore() {
        str = "hello world";
        regexp = "hel(?>lo)";
    }
    
    @Test
    public void A() {
        str = "i love you !";
        // \A等同^，表示开头，不匹配新行起始
        regexp = "\\A";
    }

    @After
    public void after() {
        printResult(str, regexp);
    }

    private void printResult(String str, String regexp) {
        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(str);
        boolean result = matcher.find();
        System.out.println(result);
        if (result) {
            System.out.println(str.subSequence(matcher.start(), matcher.end()));
        }
    }
}
