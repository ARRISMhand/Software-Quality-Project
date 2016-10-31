package com.ece.mediatheque.DocumentTest;

import com.ece.mediatheque.mediatheque.Genre;
import com.ece.mediatheque.mediatheque.Localisation;
import com.ece.mediatheque.mediatheque.OperationImpossible;
import com.ece.mediatheque.mediatheque.document.Video;
import com.ece.mediatheque.util.InvariantBroken;
import org.junit.Assert;
import org.testng.annotations.Test;

/**
 * Created by DamnAug on 31/10/2016.
 */
public class VideoTest {

    @Test(expectedExceptions=OperationImpossible.class,
            expectedExceptionsMessageRegExp = "Ctr Video dureeFile = 0 mentionLegale = mention")
    public void test_video_constructeur_dureeFilmNull() throws OperationImpossible, InvariantBroken {

        String code = "444";
        Localisation localisation = new Localisation("salle", "rayon");
        String titre = "titre";
        String auteur = "auteur";
        String annee = "1995";
        Genre genre = new Genre("genre");
        int dureeFilm = 0;
        String mentionLegale = "mention";

        Video video = new Video(code, localisation,
                titre, auteur, annee, genre, dureeFilm, mentionLegale);
    }

    @Test(expectedExceptions=OperationImpossible.class,
            expectedExceptionsMessageRegExp = "Ctr Video dureeFile = 156 mentionLegale = null")
    public void test_video_constructeur_mentionLegaleNull() throws OperationImpossible, InvariantBroken {

        String code = "444";
        Localisation localisation = new Localisation("salle", "rayon");
        String titre = "titre";
        String auteur = "auteur";
        String annee = "1995";
        Genre genre = new Genre("genre");
        int dureeFilm = 156;
        String mentionLegale = null;

        Video video = new Video(code, localisation,
                titre, auteur, annee, genre, dureeFilm, mentionLegale);
    }

    @org.junit.Test
    public void test_video_constructeur_OK() throws OperationImpossible, InvariantBroken {
        String code = "444";
        Localisation localisation = new Localisation("salle", "rayon");
        String titre = "titre";
        String auteur = "auteur";
        String annee = "1995";
        Genre genre = new Genre("genre");
        int dureeFilm = 156;
        String mentionLegale = "mention";

        Video video = new Video(code, localisation,
                titre, auteur, annee, genre, dureeFilm, mentionLegale);

        Assert.assertEquals(video.getCode(), code);
        Assert.assertEquals(video.getLocalisation(), localisation);
        Assert.assertEquals(video.getTitre(), titre);
        Assert.assertEquals(video.getAuteur(), auteur);
        Assert.assertEquals(video.getAnnee(), annee);
        Assert.assertEquals(video.getGenre(), genre);
        Assert.assertEquals(video.getDureeFilm(), dureeFilm);
        Assert.assertEquals(video.dureeEmprunt(), 14);
        Assert.assertEquals(video.getMentionLegale(), mentionLegale);
        Assert.assertEquals(video.getNbEmprunts(), 0);
        Assert.assertEquals(video.estEmpruntable(), false);
    }

    @org.junit.Test
    public void test_video_emprunter_OK() throws OperationImpossible, InvariantBroken {

        String code = "444";
        Localisation localisation = new Localisation("salle", "rayon");
        String titre = "titre";
        String auteur = "auteur";
        String annee = "1995";
        Genre genre = new Genre("genre");
        int dureeFilm = 156;
        String mentionLegale = "mention";

        Video video = new Video(code, localisation,
                titre, auteur, annee, genre, dureeFilm, mentionLegale);

        video.metEmpruntable();
        Assert.assertTrue(video.getStat() == 0);
        Assert.assertTrue(video.emprunter());
        Assert.assertTrue(video.getStat() == 1);
        Assert.assertTrue(video.dureeEmprunt() == 14);
        Assert.assertTrue(video.tarifEmprunt() == 1.5);
    }

    @Test(expectedExceptions=InvariantBroken.class,
    expectedExceptionsMessageRegExp = "Video -[Video] \"444\" titre auteur 1995 Genre: genre, nbemprunts:1 Salle/Rayon : salle/rayon 0 SAFE  40 mention")
    public void test_video_invariantVideo_dureeCourte() throws OperationImpossible, InvariantBroken {

        String code = "444";
        Localisation localisation = new Localisation("salle", "rayon");
        String titre = "titre";
        String auteur = "auteur";
        String annee = "1995";
        Genre genre = new Genre("genre");
        int dureeFilm = 40;
        String mentionLegale = "mention";

        Video video = new Video(code, localisation,
                titre, auteur, annee, genre, dureeFilm, mentionLegale);
    }
}

