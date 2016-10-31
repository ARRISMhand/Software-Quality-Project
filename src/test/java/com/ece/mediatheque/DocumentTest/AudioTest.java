package com.ece.mediatheque.DocumentTest;

import com.ece.mediatheque.mediatheque.Genre;
import com.ece.mediatheque.mediatheque.Localisation;
import com.ece.mediatheque.mediatheque.OperationImpossible;
import com.ece.mediatheque.mediatheque.document.Audio;
import com.ece.mediatheque.util.InvariantBroken;
import org.junit.Assert;
import org.testng.annotations.Test;

/**
 * Created by DamnAug on 31/10/2016.
 */
public class AudioTest {

    @Test(expectedExceptions=OperationImpossible.class,
            expectedExceptionsMessageRegExp = "Ctr Audio classification = null")
    public void test_audio_constructeur_classificationNull() throws OperationImpossible, InvariantBroken {

        String code = "444";
        Localisation localisation = new Localisation("salle", "rayon");
        String titre = "titre";
        String auteur = "auteur";
        String annee = "1995";
        Genre genre = new Genre("genre");

        Audio audio = new Audio(code, localisation,
                titre, auteur, annee, genre, null);
    }

    @Test
    public void test_audio_constructeur_OK() throws OperationImpossible, InvariantBroken {
        String classification = "BD";
        String code = "444";
        Localisation localisation = new Localisation("salle", "rayon");
        String titre = "titre";
        String auteur = "auteur";
        String annee = "1995";
        Genre genre = new Genre("genre");

        Audio audio = new Audio(code, localisation,
                titre, auteur, annee, genre, classification);

        Assert.assertEquals(audio.getCode(), code);
        Assert.assertEquals(audio.getLocalisation(), localisation);
        Assert.assertEquals(audio.getTitre(), titre);
        Assert.assertEquals(audio.getAuteur(), auteur);
        Assert.assertEquals(audio.getAnnee(), annee);
        Assert.assertEquals(audio.getGenre(), genre);
        Assert.assertEquals(audio.getClassification(), classification);
        Assert.assertEquals(audio.estEmpruntable(), false);
    }

    @Test
    public void test_audio_emprunter_OK() throws OperationImpossible, InvariantBroken {

        String classification = "BD";
        String code = "444";
        Localisation localisation = new Localisation("salle", "rayon");
        String titre = "titre";
        String auteur = "auteur";
        String annee = "1995";
        Genre genre = new Genre("genre");

        Audio audio = new Audio(code, localisation,
                titre, auteur, annee, genre, classification);

        audio.metEmpruntable();
        Assert.assertTrue(audio.getNbEmprunts() == 0);
        Assert.assertTrue(audio.emprunter());
        Assert.assertTrue(audio.getNbEmprunts() == 1);
        Assert.assertTrue(audio.dureeEmprunt() == 28);
        Assert.assertTrue(audio.tarifEmprunt() == 1.0);
    }
}

