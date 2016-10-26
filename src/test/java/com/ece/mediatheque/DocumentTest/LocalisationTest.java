package com.ece.mediatheque.DocumentTest;


import com.ece.mediatheque.mediatheque.Localisation;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by DamnAug on 26/10/2016.
 */
public class LocalisationTest {

    @Test
    public void test_genre_equals_symetric() {
        Localisation l1 = new Localisation("salle1", "rayon1");
        Localisation l2 = new Localisation("salle1", "rayon1");

        Assert.assertTrue(l1.equals(l2) && l2.equals(l1));
        Assert.assertTrue(l1.hashCode() == l2.hashCode());
    }

    @Test
    public void test_genre_notEquals() {
        Localisation l1 = new Localisation("salle1", "rayon1");
        Localisation l2 = new Localisation("salle2", "rayon1");
        Localisation l3 = new Localisation("salle1", "rayon2");
        Localisation l4 = new Localisation("salle2", "rayon2");

        Assert.assertFalse(l1.equals(l2) &&
                l1.equals(l3) &&
                l1.equals(l4));
        Assert.assertFalse(l2.equals(l1) &&
                l2.equals(l3) &&
                l2.equals(l4));
        Assert.assertFalse(l3.equals(l1) &&
                l3.equals(l2) &&
                l3.equals(l4));
        Assert.assertFalse(l4.equals(l1) &&
                l4.equals(l2) &&
                l4.equals(l3));
        Assert.assertFalse(l1.equals(null));
        Assert.assertFalse(l1.equals("salle1"));
        Assert.assertFalse(l1.equals("rayon1"));
    }
}
