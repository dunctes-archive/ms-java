package me.duncte123.ms;

import org.junit.Test;
import static me.duncte123.ms.Ms.*;
import static org.junit.Assert.assertEquals;

public class MSTest {

    @Test
    public void textToMillisTest() {
        assertEquals(172800000L, ms("2 days"));
        assertEquals(172800000L, ms("2d"));
        assertEquals(86400000L, ms("1d"));
        assertEquals(86400000L, ms("1 day"));
        assertEquals(36000000L, ms("10h"));
        assertEquals(36000000L, ms("10 hours"));
        assertEquals(9000000L, ms("2.5 hrs"));
        assertEquals(7200000L, ms("2h"));
        assertEquals(60000L, ms("1m"));
        assertEquals(60000L, ms("1 minute"));
        assertEquals(5000L, ms("5s"));
        assertEquals(5000L, ms("5 seconds"));
        assertEquals(31557600000L, ms("1y"));
        assertEquals(100L, ms("100"));
        assertEquals(-259200000L, ms("-3 days"));
        assertEquals(-3600000L, ms("-1h"));
        assertEquals(-200L, ms("-200"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void failingTextToMillisTest() {
        ms("Invalid format");
        ms(null);
    }

    @Test
    public void millisToTextTest() {
        assertEquals("1m", ms(60000));
        assertEquals("2m", ms(2 * 60000));
        assertEquals("-3m", ms(-3 * 60000));
        assertEquals("10h", ms(ms("10 hours")));


        assertEquals("1 minute", ms(60000, true));
        assertEquals("2 minutes", ms(2 * 60000, true));
        assertEquals("-3 minutes", ms(-3 * 60000, true));
        assertEquals("10 hours", ms(ms("10 hours"), true));
    }

}
