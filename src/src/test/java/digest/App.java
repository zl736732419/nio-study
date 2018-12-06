package digest;

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
 *  TODO
 *
 *  Revision History
 *  Date,					Who,					What;
 *  2018年09月10日			zhenglian			    Initial.
 *
 * </pre>
 */
public class App {
    
    private Integer appId;
    
    private List<Person> persons = new ArrayList<>();

    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }
    
    public void addPerson(Person person) {
        this.persons.add(person);
    }
}
