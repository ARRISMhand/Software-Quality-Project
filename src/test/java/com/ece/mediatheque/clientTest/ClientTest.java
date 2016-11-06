package com.ece.mediatheque.clientTest;

import com.ece.mediatheque.mediatheque.*;
import com.ece.mediatheque.mediatheque.client.CategorieClient;
import com.ece.mediatheque.mediatheque.client.Client;
import com.ece.mediatheque.util.Datutil;
import org.junit.Assert;
import org.mockito.Mockito;

import java.util.Date;

/**
 * Created by Edgar on 25/10/2016.
 */
public class ClientTest { //TestNG for testing exceptions

    @org.testng.annotations.Test
    public void test_create_client_with_fullName() {
        Client client = new Client("Denis", "Denise");
        Assert.assertEquals("Denis", client.getNom());
        Assert.assertEquals("Denise", client.getPrenom());
        Assert.assertEquals( Datutil.dateDuJour(), client.getDateInscription());
        Assert.assertEquals(Datutil.addDate(Datutil.dateDuJour(), 365), client.getDateCotisation());
        Assert.assertNotNull(client.getLesEmprunts());
    }

    @org.testng.annotations.Test(expectedExceptions = OperationImpossible.class,
            expectedExceptionsMessageRegExp = "Call with client type denis and no reduction code")
    public void test_create_client_with_nom_prenom_adresse_categorieClient_should_throw_exception()
            throws Exception {
        CategorieClient categorieClient = new CategorieClient("denis", 1000, 10.2, 1.1, 1.2, true);
        Client client = new Client("Denis", "Denise", "Paris", categorieClient);
    }

    @org.testng.annotations.Test(expectedExceptions = OperationImpossible.class,
            expectedExceptionsMessageRegExp = "Parametre null dans constructeur client : nom =null" +
                    " prenom =Denise  adresse= Paris categorie = denis")
    public void test_initAttr_should_throw_exception()
            throws Exception {
        CategorieClient categorieClient = new CategorieClient("denis", 1000, 10.2, 1.1, 1.2, false);
        Client client = new Client(null, "Denise", "Paris", categorieClient);
    }

    @org.testng.annotations.Test
    public void test_create_client_with_nom_prenom_adresse_categorieClient()
            throws Exception {
        CategorieClient categorieClient = new CategorieClient("denis", 1000, 10.2, 1.1, 1.2, false);
        Client client = new Client("Denis", "Denise", "Paris", categorieClient);
        Assert.assertEquals("Denis", client.getNom());
        Assert.assertEquals("Denise", client.getPrenom());
        Assert.assertEquals("Paris", client.getAdresse());
        Assert.assertEquals(categorieClient, client.getCategorie());
        Assert.assertEquals( Datutil.dateDuJour(), client.getDateInscription());
        Assert.assertEquals(Datutil.addDate(Datutil.dateDuJour(), 365), client.getDateCotisation());
    }

    @org.testng.annotations.Test(expectedExceptions = OperationImpossible.class,
            expectedExceptionsMessageRegExp = "Call with client type catClient and reduction code")
    public void test_create_client_with_nom_prenom_adresse_categorieClient_code_should_throw_exception()
            throws Exception {
        CategorieClient categorieClient = new CategorieClient("catClient", 1000, 10.2, 1.1, 1.2, false);
        Client client = new Client("Denis", "Denise", "Paris", categorieClient, 10);
    }

    @org.testng.annotations.Test
    public void test_create_client_with_nom_prenom_adresse_categorieClient_code()
            throws Exception {
        CategorieClient categorieClient = new CategorieClient("denis", 1000, 10.2, 1.1, 1.2, true);
        Client client = new Client("Denis", "Denise", "Paris", categorieClient, 10);
        Assert.assertEquals("Denis", client.getNom());
        Assert.assertEquals("Denise", client.getPrenom());
        Assert.assertEquals("Paris", client.getAdresse());
        Assert.assertEquals(categorieClient, client.getCategorie());
        Assert.assertEquals( Datutil.dateDuJour(), client.getDateInscription());
        Assert.assertEquals(Datutil.addDate(Datutil.dateDuJour(), 365), client.getDateCotisation());
        Assert.assertEquals(10, client.getReduc());
    }

    @org.testng.annotations.Test
    public void test_equals_Symmetric()
            throws Exception {// equals and hashCode check name field value
        CategorieClient categorieClient = new CategorieClient("denis", 1000, 10.2, 1.1, 1.2, false);
        Client x = new Client("Denis", "Denise", "Paris", categorieClient);
        Client y = new Client("Denis", "Denise", "Paris", categorieClient);
        Assert.assertTrue(x.equals(y) && y.equals(x));
        Assert.assertTrue(x.hashCode() == y.hashCode());
    }

    @org.testng.annotations.Test
    public void test_notEquals()
            throws Exception {
        CategorieClient categorieClient = new CategorieClient("denis", 1000, 10.2, 1.1, 1.2, false);
        Client x = new Client("DenisX", "Denise", "Paris", categorieClient);
        Client y = new Client("Denis", "Denise", "Paris", categorieClient);
        Assert.assertFalse(x.equals(y) && y.equals(x));
        Assert.assertFalse(x.equals(null));
        Assert.assertFalse(x.equals("Denis"));
    }

    @org.testng.annotations.Test
    public void test_peutEmprunter_return_false()
            throws Exception {
        CategorieClient categorieClient = new CategorieClient("denis", 1000, 10.2, 1.1, 1.2, false);
        Client client = new Client("Denis", "Denise", "Paris", categorieClient);
        client.emprunter();
        client.emprunter();
        Assert.assertFalse(client.peutEmprunter());
    }

    @org.testng.annotations.Test
    public void test_peutEmprunter_return_true()
            throws Exception {
        CategorieClient categorieClient = new CategorieClient("denis", 1000, 10.2, 1.1, 1.2, false);
        Client client = new Client("Denis", "Denise", "Paris", categorieClient);
        Assert.assertTrue(client.peutEmprunter());
    }

    @org.testng.annotations.Test
    public void test_aDesEmpruntsEnCours_return_false()
            throws Exception {
        CategorieClient categorieClient = new CategorieClient("denis", 1000, 10.2, 1.1, 1.2, false);
        Client client = new Client("Denis", "Denise", "Paris", categorieClient);
        Assert.assertFalse(client.aDesEmpruntsEnCours());
    }

    @org.testng.annotations.Test
    public void test_aDesEmpruntsEnCours_return_true()
            throws Exception {
        CategorieClient categorieClient = new CategorieClient("denis", 1000, 10.2, 1.1, 1.2, false);
        Client client = new Client("Denis", "Denise", "Paris", categorieClient);
        FicheEmprunt mock = Mockito.mock(FicheEmprunt.class);
        client.emprunter(mock);
        Assert.assertTrue(client.aDesEmpruntsEnCours());
    }

    @org.testng.annotations.Test(expectedExceptions = OperationImpossible.class,
            expectedExceptionsMessageRegExp = "emprunt is null")
    public void test_emprunter_null_should_throw_exception()
            throws Exception {
        CategorieClient categorieClient = new CategorieClient("denis", 1000, 10.2, 1.1, 1.2, false);
        Client client = new Client("Denis", "Denise", "Paris", categorieClient);
        client.emprunter(null);
    }

    @org.testng.annotations.Test(expectedExceptions = OperationImpossible.class,
            expectedExceptionsMessageRegExp = "Can not borrow")
    public void test_emprunter_with_peutEmprunter_false_should_throw_exception()
            throws Exception {
        CategorieClient categorieClient = new CategorieClient("denis", 1000, 10.2, 1.1, 1.2, false);
        Client client = new Client("Denis", "Denise", "Paris", categorieClient);
        client.emprunter();
        client.emprunter();

        FicheEmprunt mock = Mockito.mock(FicheEmprunt.class);
        client.emprunter(mock);
    }

    @org.testng.annotations.Test
    public void test_emprunter_2()
            throws Exception {
        CategorieClient categorieClient = new CategorieClient("denis", 1000, 10.2, 1.1, 1.2, false);
        Client client = new Client("Denis", "Denise", "Paris", categorieClient);

        FicheEmprunt mock = Mockito.mock(FicheEmprunt.class);
        client.emprunter(mock);
        client.emprunter(mock);

        Assert.assertEquals(2, client.getNbEmpruntsEnCours());
        Assert.assertEquals(2, client.getNbEmpruntsEffectues());
        Assert.assertEquals(2, client.getLesEmprunts().size());
    }

    @org.testng.annotations.Test(expectedExceptions = OperationImpossible.class,
            expectedExceptionsMessageRegExp = "Restituer sans emprunt 0")
    public void test_restituer_input_false_AND_nbEmpruntsEnCours_0_should_throw_exception()
            throws Exception {
        CategorieClient categorieClient = new CategorieClient("denis", 1000, 10.2, 1.1, 1.2, false);
        Client client = new Client("Denis", "Denise", "Paris", categorieClient);
        client.restituer(false);
    }

    @org.testng.annotations.Test
    public void test_restituer_input_false_AND_nbEmpruntsEnCours_1()
            throws Exception {
        CategorieClient categorieClient = new CategorieClient("denis", 1000, 10.2, 1.1, 1.2, false);
        Client client = new Client("Denis", "Denise", "Paris", categorieClient);
        FicheEmprunt mock = Mockito.mock(FicheEmprunt.class);
        client.emprunter(mock);
        client.emprunter(mock);

        client.restituer(false);
        Assert.assertEquals(1, client.getNbEmpruntsEnCours());
        Assert.assertEquals(0, client.getNbEmpruntsDepasses());
    }

    @org.testng.annotations.Test(expectedExceptions = OperationImpossible.class,
            expectedExceptionsMessageRegExp = "Restituer en retard sans retard 0")
    public void test_restituer_input_true_AND_nbEmpruntsDepasses_0_should_throw_exception()
            throws Exception {
        CategorieClient categorieClient = new CategorieClient("denis", 1000, 10.2, 1.1, 1.2, false);
        Client client = new Client("Denis", "Denise", "Paris", categorieClient);
        FicheEmprunt mock = Mockito.mock(FicheEmprunt.class);
        client.emprunter(mock);
        client.emprunter(mock);

        client.restituer(true);
    }

    @org.testng.annotations.Test
    public void test_restituer_input_ture_AND_nbEmpruntsEnCours_1_nbEmpruntsDepasses_0()
            throws Exception {
        CategorieClient categorieClient = new CategorieClient("denis", 1000, 10.2, 1.1, 1.2, false);
        Client client = new Client("Denis", "Denise", "Paris", categorieClient);
        FicheEmprunt mock = Mockito.mock(FicheEmprunt.class);
        client.emprunter(mock);
        client.emprunter(mock);
        client.marquer();

        client.restituer(true);
        Assert.assertEquals(1, client.getNbEmpruntsEnCours());
        Assert.assertEquals(1, client.getNbEmpruntsEnCours());
        Assert.assertEquals(0, client.getNbEmpruntsDepasses());
    }

    @org.testng.annotations.Test
    public void test_restiuer_with_FicheEmprunt()
            throws Exception {
        CategorieClient categorieClient = new CategorieClient("denis", 1000, 10.2, 1.1, 1.2, false);
        Client client = new Client("Denis", "Denise", "Paris", categorieClient);
        FicheEmprunt mock = Mockito.mock(FicheEmprunt.class);
        client.emprunter(mock);

        client.restituer(mock);
        Assert.assertEquals(0, client.getLesEmprunts().size());
    }

    @org.testng.annotations.Test
    public void test_afficherStatCli()
            throws Exception {
        CategorieClient categorieClient = new CategorieClient("denis", 1000, 10.2, 1.1, 1.2, false);
        Client client = new Client("Denis", "Denise", "Paris", categorieClient);
        FicheEmprunt mock = Mockito.mock(FicheEmprunt.class);
        client.emprunter(mock);
        client.emprunter(mock);

        Assert.assertEquals("(stat) Nombre d'emprunts effectues par \"Denis\" : 2", client.afficherStatCli());
    }

    @org.testng.annotations.Test
    public void test_dateRetour()
            throws Exception {
        CategorieClient categorieClient = new CategorieClient("denis", 1000, 10.2, 1.1, 1.2, false);
        Client client = new Client("Denis", "Denise", "Paris", categorieClient);

        Date today = new Date();
        Assert.assertEquals(Datutil.addDate(today, 5),client.dateRetour(today, 5));
        Assert.assertEquals(Datutil.addDate(today, 50),client.dateRetour(today, 50));
        Assert.assertEquals(Datutil.addDate(today, 0),client.dateRetour(today, 0));
    }

    @org.testng.annotations.Test
    public void test_sommeDue()
            throws Exception {
        CategorieClient categorieClient = new CategorieClient("denis", 1000, 10.2, 1.1, 0, false);
        Client client = new Client("Denis", "Denise", "Paris", categorieClient);

        Assert.assertEquals(0,client.sommeDue(5), 0);

        CategorieClient categorieClient1 = new CategorieClient("denis", 1000, 10.2, 1.1, 1.2, false);
        Client client1 = new Client("Denis", "Denise", "Paris", categorieClient1);

        Assert.assertEquals(1.2*5,client1.sommeDue(5), 0);

    }

    @org.testng.annotations.Test
    public void test_nbMaxEmprunt()
            throws Exception {
        CategorieClient categorieClient = new CategorieClient("denis", 1000, 10.2, 1.1, 1.2, false);
        Client client = new Client("Denis", "Denise", "Paris", categorieClient);

        Assert.assertEquals(2, client.nbMaxEmprunt());
    }

    @org.testng.annotations.Test
    public void test_nbEmpruntsEnRetard_OK() throws OperationImpossible {

        CategorieClient categorieClient = new CategorieClient("denis", 1000, 10.2, 1.1, 1.2, false);
        Client client = new Client("Denis", "Denise", "Paris", categorieClient);

        //Emprunter
        client.emprunter();
        //Mettre l'emprunt en retard
        client.marquer();
        //Verifier que le nombre emprunts non rendu dans les délais est de 1
        Assert.assertTrue(client.getNbEmpruntsDepasses() == 1);
    }

    @org.testng.annotations.Test(expectedExceptions = OperationImpossible.class,
            expectedExceptionsMessageRegExp = "Categorie necessite un code de reduction")
    public void test_setCategorie_should_throw_exception()
            throws Exception {
        CategorieClient categorieClient = new CategorieClient("denis", 1000, 10.2, 1.1, 1.2, false);
        Client client = new Client("Denis", "Denise", "Paris", categorieClient);

        CategorieClient categorieClient1 = new CategorieClient("denis1", 1000, 10.2, 1.1, 1.2, true);
        client.setCategorie(categorieClient1);
    }

    @org.testng.annotations.Test
    public void test_setCategorie()
            throws Exception {
        CategorieClient categorieClient = new CategorieClient("denis", 1000, 10.2, 1.1, 1.2, false);
        Client client = new Client("Denis", "Denise", "Paris", categorieClient);

        CategorieClient categorieClient1 = new CategorieClient("denis1", 1000, 10.2, 1.1, 1.2, false);
        client.setCategorie(categorieClient1);
        Assert.assertEquals(categorieClient1, client.getCategorie());
    }

    @org.testng.annotations.Test(expectedExceptions = OperationImpossible.class,
            expectedExceptionsMessageRegExp = "Categorie sans code de reduction")
    public void test_setCategorie_with_code_should_throw_exception()
            throws Exception {
        CategorieClient categorieClient = new CategorieClient("denis", 1000, 10.2, 1.1, 1.2, false);
        Client client = new Client("Denis", "Denise", "Paris", categorieClient);

        CategorieClient categorieClient1 = new CategorieClient("denis1", 1000, 10.2, 1.1, 1.2, false);
        client.setCategorie(categorieClient1, 3);
    }

    @org.testng.annotations.Test
    public void test_setCategorie_with_code()
            throws Exception {
        CategorieClient categorieClient = new CategorieClient("denis", 1000, 10.2, 1.1, 1.2, false);
        Client client = new Client("Denis", "Denise", "Paris", categorieClient);

        CategorieClient categorieClient1 = new CategorieClient("denis1", 1000, 10.2, 1.1, 1.2, true);
        client.setCategorie(categorieClient1, 3);
        Assert.assertEquals(categorieClient1, client.getCategorie());
        Assert.assertEquals(3, client.getReduc());
    }



}
