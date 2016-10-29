package com.ece.mediatheque.clientTest;

import com.ece.mediatheque.mediatheque.client.HashClient;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Edgar on 25/10/2016.
 */
public class HashClientTest {

    @Test
    public void test_creatHashClient() {
        HashClient hc = new HashClient("Denis", "Denise");
        Assert.assertEquals("Denis", hc.getNom());
        Assert.assertEquals("Denise", hc.getPrenom());
    }

    @Test
    public void test_equals_Symmetric() {
        HashClient x = new HashClient("Denis", "denise");  // equals and hashCode check name field value
        HashClient y = new HashClient("Denis", "denise");
        Assert.assertTrue(x.equals(y) && y.equals(x));
        Assert.assertTrue(x.hashCode() == y.hashCode());
    }

    @Test
    public void test_notEquals() {
        HashClient x = new HashClient("Denis", "denise");
        HashClient y = new HashClient("Denis 2", "denise");
        Assert.assertFalse(x.equals(y) && y.equals(x));
        Assert.assertFalse(x.equals(null));
        Assert.assertFalse(x.equals("Denis"));
    }
}
