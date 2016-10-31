package com.ece.mediatheque;

import com.ece.mediatheque.mediatheque.Genre;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by DamnAug on 26/10/2016.
 */
public class GenreTest {

    @Test
    public void test_genre_emprunter() {

        Genre genre = new Genre("livre");
        Assert.assertEquals(genre.getNbEmprunts(), 1);
        genre.emprunter();
        genre.afficherStatistiques();
        Assert.assertEquals(genre.getNbEmprunts(), 2);
    }

    @Test
    public void test_genre_changementNom() {

        Genre genre = new Genre("livre");
        Assert.assertEquals(genre.getNom(), "livre");
        genre.modifier("cd");
        Assert.assertEquals(genre.getNom(), "cd");
    }

    @Test
    public void test_genre_equals_symetric() {
        Genre g1 = new Genre("livre");
        Genre g2 = new Genre("livre");

        Assert.assertTrue(g1.equals(g2) && g2.equals(g1));
        Assert.assertTrue(g1.hashCode() == g2.hashCode());
    }

    @Test
    public void test_genre_notEquals() {
        Genre g1 = new Genre("livre");
        Genre g2 = new Genre("livre 2");

        Assert.assertFalse(g1.equals(g2) && g2.equals(g1));
        Assert.assertFalse(g1.equals(null));
        Assert.assertFalse(g1.equals("livre"));
    }
}
