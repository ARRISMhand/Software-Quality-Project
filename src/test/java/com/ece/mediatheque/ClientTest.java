package com.ece.mediatheque;

import com.ece.mediatheque.mediatheque.*;
import com.ece.mediatheque.mediatheque.client.CategorieClient;
import com.ece.mediatheque.mediatheque.client.Client;
import com.ece.mediatheque.mediatheque.document.Document;
import com.ece.mediatheque.mediatheque.document.DocumentImpl;
import com.ece.mediatheque.util.Datutil;
import org.junit.Assert;
import org.mockito.Mockito;

import java.util.Vector;

/**
 * Created by Edgar on 25/10/2016.
 */
public class ClientTest { //TestNG for testing exceptions

    @org.testng.annotations.Test
    public void test_create_client_with_fullName() {
        Client client = new Client("Denis", "Denise");
        Assert.assertEquals("Denis", client.getNom());
        Assert.assertEquals("Denise", client.getPrenom());
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
        CategorieClient categorieClient = new CategorieClient("denis", 1000, 10.2, 1.1, 1.2, true);
        Client client = new Client(null, "Denise", "Paris", categorieClient);
    }

    @org.testng.annotations.Test
    public void test_create_client_with_nom_prenom_adresse_categorieClient()
            throws Exception {
        CategorieClient categorieClient = new CategorieClient("denis");
        Client client = new Client("Denis", "Denise", "Paris", categorieClient);
        Assert.assertEquals("Denis", client.getNom());
        Assert.assertEquals("Denise", client.getPrenom());
        Assert.assertEquals("Paris", client.getAdresse());
        Assert.assertEquals(categorieClient, client.getCategorie());
        Assert.assertEquals( Datutil.dateDuJour(), client.getDateInscription());
        Assert.assertEquals(Datutil.addDate(Datutil.dateDuJour(), 365), client.getDateCotisation());
    }

    @org.testng.annotations.Test(expectedExceptions = OperationImpossible.class,
            expectedExceptionsMessageRegExp = "Call with client type denis and reduction code")
    public void test_create_client_with_nom_prenom_adresse_categorieClient_code_should_throw_exception()
            throws Exception {
        CategorieClient categorieClient = new CategorieClient("denis", 1000, 10.2, 1.1, 1.2, true);
        Client client = new Client("Denis", "Denise", "Paris", categorieClient, 10);
    }

    @org.testng.annotations.Test
    public void test_create_client_with_nom_prenom_adresse_categorieClient_code()
            throws Exception {
        CategorieClient categorieClient = new CategorieClient("denis");
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
        CategorieClient categorieClient = new CategorieClient("Denis");
        Client x = new Client("Denis", "Denise", "Paris", categorieClient);
        Client y = new Client("Denis", "Denise", "Paris", categorieClient);
        Assert.assertTrue(x.equals(y) && y.equals(x));
        Assert.assertTrue(x.hashCode() == y.hashCode());
    }

    @org.testng.annotations.Test
    public void test_notEquals()
            throws Exception {
        CategorieClient categorieClient = new CategorieClient("Denis");
        Client x = new Client("DenisX", "Denise", "Paris", categorieClient);
        Client y = new Client("Denis", "Denise", "Paris", categorieClient);
        Assert.assertFalse(x.equals(y) && y.equals(x));
        Assert.assertFalse(x.equals(null));
        Assert.assertFalse(x.equals("Denis"));
    }

    @org.testng.annotations.Test
    public void test_peutEmprunter_return_false()
            throws Exception {
        CategorieClient categorieClient = new CategorieClient("denis");
        Client client = new Client("Denis", "Denise", "Paris", categorieClient);
        client.emprunter();
        client.emprunter();
        Assert.assertFalse(client.peutEmprunter());
    }

    @org.testng.annotations.Test
    public void test_peutEmprunter_return_true()
            throws Exception {
        CategorieClient categorieClient = new CategorieClient("denis");
        Client client = new Client("Denis", "Denise", "Paris", categorieClient);
        Assert.assertTrue(client.peutEmprunter());
    }

    @org.testng.annotations.Test
    public void test_aDesEmpruntsEnCours_return_false()
            throws Exception {
        CategorieClient categorieClient = new CategorieClient("denis");
        Client client = new Client("Denis", "Denise", "Paris", categorieClient);
        Assert.assertFalse(client.aDesEmpruntsEnCours());
    }

    @org.testng.annotations.Test
    public void test_aDesEmpruntsEnCours_return_true()
            throws Exception {
        CategorieClient categorieClient = new CategorieClient("denis");
        Client client = new Client("Denis", "Denise", "Paris", categorieClient);
        FicheEmprunt mock = Mockito.mock(FicheEmprunt.class);
        client.emprunter(mock);
        Assert.assertTrue(client.aDesEmpruntsEnCours());
    }

    @org.testng.annotations.Test(expectedExceptions = OperationImpossible.class,
            expectedExceptionsMessageRegExp = "emprunt is null")
    public void test_emprunter_null_should_throw_exception()
            throws Exception {
        CategorieClient categorieClient = new CategorieClient("denis");
        Client client = new Client("Denis", "Denise", "Paris", categorieClient);
        client.emprunter(null);
    }

    @org.testng.annotations.Test(expectedExceptions = OperationImpossible.class,
            expectedExceptionsMessageRegExp = "Can not borrow")
    public void test_emprunter_with_peutEmprunter_false_should_throw_exception()
            throws Exception {
        CategorieClient categorieClient = new CategorieClient("denis");
        Client client = new Client("Denis", "Denise", "Paris", categorieClient);
        client.emprunter();
        client.emprunter();

        FicheEmprunt mock = Mockito.mock(FicheEmprunt.class);
        client.emprunter(mock);
    }

    @org.testng.annotations.Test
    public void test_emprunter_2()
            throws Exception {
        CategorieClient categorieClient = new CategorieClient("denis");
        Client client = new Client("Denis", "Denise", "Paris", categorieClient);

        FicheEmprunt mock = Mockito.mock(FicheEmprunt.class);
        client.emprunter(mock);
        client.emprunter(mock);

        Assert.assertEquals(2, client.getNbEmpruntsEnCours());
        Assert.assertEquals(2, client.getNbEmpruntsEffectues());
    }

    @org.testng.annotations.Test(expectedExceptions = OperationImpossible.class,
            expectedExceptionsMessageRegExp = "Restituer sans emprunt 0")
    public void test_restituer_input_false_AND_nbEmpruntsEnCours_0_should_throw_exception()
            throws Exception {
        CategorieClient categorieClient = new CategorieClient("denis");
        Client client = new Client("Denis", "Denise", "Paris", categorieClient);
        client.restituer(false);
    }

    @org.testng.annotations.Test
    public void test_restituer_input_false_AND_nbEmpruntsEnCours_1()
            throws Exception {
        CategorieClient categorieClient = new CategorieClient("denis");
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
        CategorieClient categorieClient = new CategorieClient("denis");
        Client client = new Client("Denis", "Denise", "Paris", categorieClient);
        FicheEmprunt mock = Mockito.mock(FicheEmprunt.class);
        client.emprunter(mock);
        client.emprunter(mock);

        client.restituer(true);
    }

    @org.testng.annotations.Test
    public void test_restituer_input_ture_AND_nbEmpruntsEnCours_1_nbEmpruntsDepasses_0()
            throws Exception {
        CategorieClient categorieClient = new CategorieClient("denis");
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

    }

}
