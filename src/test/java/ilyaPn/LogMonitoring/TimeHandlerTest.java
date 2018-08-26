package ilyaPn.LogMonitoring;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by ilyaP on 26.08.2018.
 */
public class TimeHandlerTest {
    @Test
    public void itHappensInOneDayFalse() throws Exception {
        boolean res = TimeHandler.itHappensInOneDay(1455839970L, 100);
        Assert.assertEquals(false, res);
    }

    @Test
    public void itHappensInOneDayTrue() throws Exception {
        boolean res = TimeHandler.itHappensInOneDay(1455839970L, 1);
        Assert.assertEquals(true, res);
    }

}