package ilyaPn.LogMonitoring;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by ilyaP on 23.08.2018.
 */
public class XMLGeneratorTest {
    XMLGenerator xmlGenerator = new XMLGenerator("","");
    @Test
    public void splitByTimeWithDeferenceDays() throws Exception {
        Map<String,ArrayList<User>> map;
        ArrayList<User> users = new ArrayList<>();
        User user1 = new User(10,"id1","url",10,1);
        User user2 = new User(1455812684000L,"id1","url",10,1);

        users.add(user1);
        users.add(user2);

        map = xmlGenerator.splitByTime(users);
        Assert.assertEquals(map.size(),2);
    }
    @Test
    public void splitByTimeWithOneDay() throws Exception {
        Map<String,ArrayList<User>> map;
        ArrayList<User> users = new ArrayList<>();
        User user1 = new User(10,"id1","url",10,1);
        User user2 = new User(11,"id1","url",10,1);

        users.add(user1);
        users.add(user2);

        map = xmlGenerator.splitByTime(users);
        Assert.assertEquals(map.size(),1);
    }

}