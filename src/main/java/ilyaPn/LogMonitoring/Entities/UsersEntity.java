package ilyaPn.LogMonitoring.Entities;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

/**
 * Created by ilyaP on 21.08.2018.
 */
@XmlRootElement(name = "user")
public class UsersEntity {
    private ArrayList<UserEntity> user = new ArrayList();

    public ArrayList<UserEntity> getUser() {
        return user;
    }

    public void setUser(ArrayList<UserEntity> user) {
        this.user = user;
    }
}
