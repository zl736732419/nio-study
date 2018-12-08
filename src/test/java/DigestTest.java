import digest.App;
import digest.Person;
import org.apache.commons.digester3.Digester;
import org.junit.Test;

import java.io.InputStream;

/**
 * <pre>
 *
 *  File: DigestTest.java
 *
 *  Copyright (c) 2016, globalegrow.com All Rights Reserved.
 *
 *  Description:
 *  Digest测试
 *
 *  Revision History
 *  Date,					Who,					What;
 *  2018年09月10日			zhenglian			    Initial.
 *
 * </pre>
 */
public class DigestTest {
    @Test
    public void parse() throws Exception {
//        String path = this.getClass().getResource("/person.xml").getPath();
        InputStream input = this.getClass().getClassLoader().getResourceAsStream("person.xml");
        Digester digester = new Digester();
        digester.setValidating(false);
        
        digester.addObjectCreate("app", App.class);
        digester.addBeanPropertySetter("app/appId", "appId");
        digester.addObjectCreate("app/person", Person.class);
        digester.addSetProperties("app/person");
        digester.addBeanPropertySetter("app/person/id", "id");
        
        digester.addSetNext("app/person", "addPerson");

        App app = digester.parse(input);
        System.out.println(app);
    }
}
