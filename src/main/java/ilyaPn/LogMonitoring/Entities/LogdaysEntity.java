package ilyaPn.LogMonitoring.Entities;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

/**
 * Created by ilyaP on 22.08.2018.
 */
@XmlRootElement(name = "output")
public class LogdaysEntity {
    private ArrayList<LogdayEntity> logday = new ArrayList<>();

    public ArrayList<LogdayEntity> getLogday() {
        return logday;
    }

    public void setLogday(ArrayList<LogdayEntity> logday) {
        this.logday = logday;
    }
}
