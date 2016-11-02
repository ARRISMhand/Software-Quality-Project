package com.ece.mediatheque.DocumentTest;


import com.ece.mediatheque.mediatheque.Genre;
import com.ece.mediatheque.mediatheque.Localisation;
import com.ece.mediatheque.mediatheque.OperationImpossible;
import com.ece.mediatheque.mediatheque.document.Document;
import com.ece.mediatheque.mediatheque.document.Audio;
import com.ece.mediatheque.mediatheque.document.Document;
import com.ece.mediatheque.mediatheque.document.Livre;
import com.ece.mediatheque.util.InvariantBroken;
import org.junit.Assert;
import org.mockito.Mock;
import org.testng.annotations.Test;


public class DocumentTest {


    @Test(expectedExceptions=OperationImpossible.class,
    expectedExceptionsMessageRegExp = "Ctr Document arguments = code : null, localisation : null, titre : null, auteur : null, annee : null, genre : null")
    public void test_document_constructeur_null() throws OperationImpossible, InvariantBroken {

        Document document = new Audio(null, null,
                null, null, null, null, null);
    }

    @Test
    public void test_document_constructeur_OK() throws OperationImpossible, InvariantBroken {

        String classification = "BD";
        String code = "444";
        Localisation localisation = new Localisation("salle", "rayon");
        String titre = "titre";
        String auteur = "auteur";
        String annee = "1995";
        Genre genre = new Genre("genre");

        Document document = new Audio(code, localisation,
                titre, auteur, annee, genre, classification);

        Assert.assertFalse(document.estEmpruntable());
        Assert.assertFalse(document.estEmprunte());
        Assert.assertTrue(document.getNbEmprunts() == 0);
        Assert.assertEquals(document.getCode(), code);
        Assert.assertEquals(document.getLocalisation(), localisation);
        Assert.assertEquals(document.getTitre(), titre);
        Assert.assertEquals(document.getAuteur(), auteur);
        Assert.assertEquals(document.getAnnee(), annee);
        Assert.assertEquals(document.getGenre(), genre);
    }

    @Test
    public void test_equals_Symmetric() throws OperationImpossible, InvariantBroken {
        String classification = "BD";
        String code = "444";
        Localisation localisation = new Localisation("salle", "rayon");
        String titre = "titre";
        String auteur = "auteur";
        String annee = "1995";
        Genre genre = new Genre("genre");

        Document x = new Audio(code, localisation,
                titre, auteur, annee, genre, classification);  // equals and hashCode check name field value
        Document y = new Audio(code, localisation,
                titre, auteur, annee, genre, classification);
        Assert.assertTrue(x.equals(y) && y.equals(x));
        Assert.assertTrue(x.hashCode() == y.hashCode());
    }

    @Test
    public void test_notEquals() throws OperationImpossible, InvariantBroken {
        String classification = "BD";
        String code = "444";
        String code2 = "446";
        Localisation localisation = new Localisation("salle", "rayon");
        String titre = "titre";
        String auteur = "auteur";
        String annee = "1995";
        Genre genre = new Genre("genre");

        Document x = new Audio(code, localisation,
                titre, auteur, annee, genre, classification);
        Document y = new Audio(code2, localisation,
                titre, auteur, annee, genre, classification);
        Assert.assertFalse(x.equals(y) && y.equals(x));
        Assert.assertFalse(x.equals(null));
        Assert.assertFalse(x.equals(code));
    }

    @Test(expectedExceptions=OperationImpossible.class,
            expectedExceptionsMessageRegExp = "Document metEmpruntable empruntable true code : 444")
    public void test_document_metEmpruntable_dejaEmpruntable() throws OperationImpossible, InvariantBroken {

    String classification = "BD";
        String code = "444";
        Localisation localisation = new Localisation("salle", "rayon");
        String titre = "titre";
        String auteur = "auteur";
        String annee = "1995";
        Genre genre = new Genre("genre");

        Document document = new Audio(code, localisation,
                titre, auteur, annee, genre, classification);

        document.metEmpruntable();
        document.metEmpruntable();
    }


    @Test(expectedExceptions=OperationImpossible.class,
            expectedExceptionsMessageRegExp = "Document metEmpruntable empruntable true code : 444")
    public void test_document_metEmpruntable_invariant() throws OperationImpossible, InvariantBroken {

        String classification = "BD";
        String code = "444";
        Localisation localisation = new Localisation("salle", "rayon");
        String titre = "titre";
        String auteur = "auteur";
        String annee = "1995";
        Genre genre = new Genre("genre");

        Document document = new Audio(code, localisation,
                titre, auteur, annee, genre, classification);

        document.metEmpruntable();
        document.emprunter();
        document.metEmpruntable();
    }


}


