package ilyaPn.LogMonitoring.Entities;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by ilyaP on 21.08.2018.
 */
@XmlRootElement
public class LogdayEntity {
    private String day;
    private UsersEntity users = new UsersEntity();

    public UsersEntity getUsers() {
        return users;
    }

    public void setUsers(UsersEntity users) {
        this.users = users;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
