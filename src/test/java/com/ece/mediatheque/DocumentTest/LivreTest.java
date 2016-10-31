package com.ece.mediatheque.DocumentTest;

import com.ece.mediatheque.mediatheque.Genre;
import com.ece.mediatheque.mediatheque.Localisation;
import com.ece.mediatheque.mediatheque.OperationImpossible;
import com.ece.mediatheque.mediatheque.document.Livre;
import com.ece.mediatheque.util.InvariantBroken;
import org.junit.Assert;
import org.testng.annotations.Test;

/**
 * Created by DamnAug on 31/10/2016.
 */
public class LivreTest {

    @Test(expectedExceptions=OperationImpossible.class,
            expectedExceptionsMessageRegExp = "Ctr Livre nombrePage = 0")
    public void test_livre_constructeur_nbPageError() throws OperationImpossible, InvariantBroken {

        String code = "444";
        Localisation localisation = new Localisation("salle", "rayon");
        String titre = "titre";
        String auteur = "auteur";
        String annee = "1995";
        Genre genre = new Genre("genre");
        int nbPages = 0;
        String mentionLegale = "mention";

        Livre livre = new Livre(code, localisation,
                titre, auteur, annee, genre, nbPages);
    }

    @org.junit.Test
    public void test_livre_constructeur_OK() throws OperationImpossible, InvariantBroken {

        String code = "444";
        Localisation localisation = new Localisation("salle", "rayon");
        String titre = "titre";
        String auteur = "auteur";
        String annee = "1995";
        Genre genre = new Genre("genre");
        int nbPages = 120;
        String mentionLegale = "mention";

        Livre livre = new Livre(code, localisation,
                titre, auteur, annee, genre, nbPages);

        Assert.assertEquals(livre.getCode(), code);
        Assert.assertEquals(livre.getLocalisation(), localisation);
        Assert.assertEquals(livre.getTitre(), titre);
        Assert.assertEquals(livre.getAuteur(), auteur);
        Assert.assertEquals(livre.getAnnee(), annee);
        Assert.assertEquals(livre.getGenre(), genre);
        Assert.assertEquals(livre.getNbEmprunts(), 0);
        Assert.assertEquals(livre.estEmpruntable(), false);
    }

    @org.junit.Test
    public void test_livre_emprunter_OK() throws OperationImpossible, InvariantBroken {

        String code = "444";
        Localisation localisation = new Localisation("salle", "rayon");
        String titre = "titre";
        String auteur = "auteur";
        String annee = "1995";
        Genre genre = new Genre("genre");
        int nbPages = 120;
        String mentionLegale = "mention";

        Livre livre = new Livre(code, localisation,
                titre, auteur, annee, genre, nbPages);

        livre.metEmpruntable();
        Assert.assertTrue(livre.getStat() == 0);
        Assert.assertTrue(livre.emprunter());
        Assert.assertTrue(livre.getStat() == 1);
        Assert.assertTrue(livre.dureeEmprunt() == 42);
        Assert.assertTrue(livre.tarifEmprunt() == 0.5);
    }
}

