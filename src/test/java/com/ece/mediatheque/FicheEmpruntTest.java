package com.ece.mediatheque;

import com.ece.mediatheque.mediatheque.*;
import com.ece.mediatheque.mediatheque.client.CategorieClient;
import com.ece.mediatheque.mediatheque.client.Client;
import com.ece.mediatheque.mediatheque.document.DocumentImpl;
import com.ece.mediatheque.util.Datutil;
import com.ece.mediatheque.util.InvariantBroken;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class FicheEmpruntTest {

    FicheEmprunt ficheEmprunt;
    DocumentImpl document;
    Client client;
    CategorieClient categorieClient;

    @Before
    public void setUp() throws OperationImpossible, InvariantBroken {
        String code = "444";
        Localisation localisation = new Localisation("salle", "rayon");
        String titre = "titre";
        String auteur = "auteur";
        String annee = "1995";
        Genre genre = new Genre("genre");
        categorieClient = new CategorieClient("denis", 1000, 10.2, 1.1, 1.2, false);
        client = new Client("Denis", "Denise", "Paris", categorieClient);
        document = new DocumentImpl(code, localisation, titre, auteur, annee, genre);
        document.metEmpruntable();

        ficheEmprunt = new FicheEmprunt(new Mediatheque("mediatheque"),
                client,
                document);

    }

    @Test
    public void creat_FicheEmprunt()
            throws OperationImpossible, InvariantBroken {

        Assert.assertEquals(client, ficheEmprunt.getClient());
        Assert.assertEquals(Datutil.dateDuJour(), ficheEmprunt.getDateEmprunt());
        Assert.assertEquals(client.dateRetour(Datutil.dateDuJour(), document.dureeEmprunt()), ficheEmprunt.getDateLimite());
        Assert.assertEquals(false, ficheEmprunt.getDepasse());
        Assert.assertEquals(document, ficheEmprunt.getDocument());
        Assert.assertEquals(document.getNbEmprunts(), 1);
        Assert.assertEquals(client.getNbEmpruntsEffectues(), 1);
    }

    @Test
    public void  test_verifier_depasse_false_dateLimite_after_dateActuelle()
            throws OperationImpossible{
        ficheEmprunt.verifier();
        Assert.assertFalse(ficheEmprunt.getDepasse());
    }

    @Test
    public void  test_verifier_depasse_false_dateLimite_before_dateActuelle()
            throws OperationImpossible, InvariantBroken, ParseException {

        String code = "444";
        Localisation localisation = new Localisation("salle", "rayon");
        String titre = "titre";
        String auteur = "auteur";
        String annee = "1995";
        Genre genre = new Genre("genre");
        Client clientMock = Mockito.mock(Client.class);
        DocumentImpl document1 = new DocumentImpl(code, localisation, titre, auteur, annee, genre);
        document1.metEmpruntable();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date date = sdf.parse("29/10/2015");
        Mockito.when(clientMock.dateRetour(Datutil.dateDuJour(), document1.dureeEmprunt()))
                .thenReturn(date);

        FicheEmprunt ficheEmprunt1 = new FicheEmprunt(new Mediatheque("mediatheque"),
                                                        clientMock,
                                                        document1);

        ficheEmprunt1.verifier();
        Assert.assertTrue(ficheEmprunt1.getDepasse());

    }

    @Test
    public void test_verifier_depasse_true()
            throws OperationImpossible, InvariantBroken, ParseException {
        String code = "444";
        Localisation localisation = new Localisation("salle", "rayon");
        String titre = "titre";
        String auteur = "auteur";
        String annee = "1995";
        Genre genre = new Genre("genre");
        Client clientMock = Mockito.mock(Client.class);
        DocumentImpl document1 = new DocumentImpl(code, localisation, titre, auteur, annee, genre);
        document1.metEmpruntable();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date date = sdf.parse("29/10/2015");
        Mockito.when(clientMock.dateRetour(Datutil.dateDuJour(), document1.dureeEmprunt()))
                .thenReturn(date);

        FicheEmprunt ficheEmprunt1 = new FicheEmprunt(new Mediatheque("mediatheque"),
                clientMock,
                document1);

        FicheEmprunt ficheEmpruntSpy = Mockito.spy(ficheEmprunt1);

        ficheEmpruntSpy.verifier();
        ficheEmpruntSpy.verifier();
        Mockito.verify(ficheEmpruntSpy, Mockito.times(1)).relancer();
    }

    @Test
    public void test_correspond() {
        Assert.assertTrue(ficheEmprunt.correspond(client, document));
    }

    @Test
    public void test_restituer()
            throws OperationImpossible, InvariantBroken, ParseException {
        String code = "444";
        Localisation localisation = new Localisation("salle", "rayon");
        String titre = "titre";
        String auteur = "auteur";
        String annee = "1995";
        Genre genre = new Genre("genre");
        Client clientMock = Mockito.mock(Client.class);
        DocumentImpl document1 = new DocumentImpl(code, localisation, titre, auteur, annee, genre);
        DocumentImpl documentSpy = Mockito.spy(document1);
        documentSpy.metEmpruntable();

        FicheEmprunt ficheEmprunt1 = new FicheEmprunt(new Mediatheque("mediatheque"),
                clientMock,
                documentSpy);

        ficheEmprunt1.restituer();
        Mockito.verify(documentSpy, Mockito.times(1)).restituer();
        Mockito.verify(clientMock, Mockito.times(1)).restituer(ficheEmprunt1);
    }

    @Test
    public void test_getDureeEmprunt() {
        Assert.assertEquals((int) ((ficheEmprunt.getDateLimite().getTime () - ficheEmprunt.getDateEmprunt().getTime ()) / (1000 * 60 * 60 * 24)), ficheEmprunt.getDureeEmprunt());
    }

    @Test
    public void test_changementCategorie_depasse_false_return_false()
            throws OperationImpossible {
        Assert.assertFalse(ficheEmprunt.changementCategorie());
    }

    @Test
    public void test_changementCategorie_depasse_false_return_true()
            throws OperationImpossible, ParseException, InvariantBroken {
        String code = "444";
        Localisation localisation = new Localisation("salle", "rayon");
        String titre = "titre";
        String auteur = "auteur";
        String annee = "1995";
        Genre genre = new Genre("genre");
        Client clientMock = Mockito.mock(Client.class);
        DocumentImpl document1 = new DocumentImpl(code, localisation, titre, auteur, annee, genre);
        document1.metEmpruntable();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date date = sdf.parse("29/10/2015");
        Mockito.when(clientMock.dateRetour(Datutil.dateDuJour(), document1.dureeEmprunt()))
                .thenReturn(date);

        FicheEmprunt ficheEmprunt1 = new FicheEmprunt(new Mediatheque("mediatheque"),
                clientMock,
                document1);

        Assert.assertTrue(ficheEmprunt1.changementCategorie());
    }

    @Test
    public void test_changementCategorie_depasse_true_return_true()
            throws OperationImpossible, ParseException, InvariantBroken {
        String code = "444";
        Localisation localisation = new Localisation("salle", "rayon");
        String titre = "titre";
        String auteur = "auteur";
        String annee = "1995";
        Genre genre = new Genre("genre");
        Client clientMock = Mockito.mock(Client.class);
        DocumentImpl document1 = new DocumentImpl(code, localisation, titre, auteur, annee, genre);
        document1.metEmpruntable();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date date = sdf.parse("29/10/2015");
        Mockito.when(clientMock.dateRetour(Datutil.dateDuJour(), document1.dureeEmprunt()))
                .thenReturn(date);

        FicheEmprunt ficheEmprunt1 = new FicheEmprunt(new Mediatheque("mediatheque"),
                clientMock,
                document1);
        ficheEmprunt1.verifier();

        Assert.assertTrue(ficheEmprunt1.changementCategorie());
    }
}
