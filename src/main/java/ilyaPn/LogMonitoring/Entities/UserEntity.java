package ilyaPn.LogMonitoring.Entities;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by ilyaP on 21.08.2018.
 */
@XmlRootElement
public class UserEntity {
    private String id;
    private String URL;
    private long average;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public long getAverage() {
        return average;
    }

    public void setAverage(long average) {
        this.average = average;
    }
}
