package com.ece.mediatheque.DocumentTest;

import com.ece.mediatheque.mediatheque.Genre;
import com.ece.mediatheque.mediatheque.Localisation;
import com.ece.mediatheque.mediatheque.Mediatheque;
import com.ece.mediatheque.mediatheque.OperationImpossible;
import com.ece.mediatheque.mediatheque.client.CategorieClient;
import org.junit.Assert;
import org.testng.annotations.Test;


/**
 * Created by DamnAug on 27/10/2016.
 */
public class MediathequeTest {

    @Test
    public void test_mediatheque_inexistanteMediatheque() {
        Mediatheque mediatheque = new Mediatheque("inexistanteMediatheque");
        Assert.assertFalse(mediatheque.initFromFile());
    }

    @Test
    public void test_mediatheque_exsitanteMediatheque() { //TODO: créer datafile

        Mediatheque mediatheque = new Mediatheque("mediatheque");
        Assert.assertTrue(mediatheque.initFromFile());
    }

    @Test
    public void test_mediatheque_empty() {

        Mediatheque mediatheque = new Mediatheque("mediatheque");

        mediatheque.empty();
        Assert.assertTrue(mediatheque.getGenresSize() == 0);
        Assert.assertTrue(mediatheque.getLocalisationsSize() == 0);
        Assert.assertTrue(mediatheque.getDocumentsSize() == 0);
        Assert.assertTrue(mediatheque.getClientsSize() == 0);
        Assert.assertTrue(mediatheque.getFicheEmpruntsSize() == 0);
        Assert.assertTrue(mediatheque.getCategoriesSize() == 0);
    }

    @Test
    public void test_mediatheque_chercherGenre_nonTrouve() {

        Mediatheque mediatheque = new Mediatheque("mediatheque");
        Assert.assertNull(mediatheque.chercherGenre("GenreInconnu"));
    }

    @Test
    public void test_mediatheque_chercherGenre_OK() { //TODO: créer datafile

        Mediatheque mediatheque = new Mediatheque("mediatheque");
        Genre genre = mediatheque.chercherGenre("Livre");
        Assert.assertTrue(genre.getNom().equals("Livre"));

    }

    @Test(expectedExceptions=OperationImpossible.class,
            expectedExceptionsMessageRegExp = "Genre SupprimerGenreInconnu inexistant")
    public void test_mediatheque_supprimerGenre_nonTrouve() throws OperationImpossible {

        Mediatheque mediatheque = new Mediatheque("mediatheque");
        mediatheque.supprimerGenre("SupprimerGenreInconnu");
    }

    /**
     * Cas où le genre est dans la médiatheque mais aussi dans les documents.
     */
    @Test(expectedExceptions=OperationImpossible.class,
            expectedExceptionsMessageRegExp = "Suppression de genre impossible. Il existe au moins un document associe au genre CD")
    public void test_mediatheque_supprimerGenre_nonPresentDansDocument() throws OperationImpossible {

        Mediatheque mediatheque = new Mediatheque("mediatheque");
        mediatheque.supprimerGenre("CD"); //TODO: Choisir le bon genre et le reporter dans l'exception
    }

    @Test
    public void test_mediatheque_supprimerGenre_OK() throws OperationImpossible {

        String genre = "Livre";
        Mediatheque mediatheque = new Mediatheque("mediatheque");
        mediatheque.supprimerGenre(genre);

        // Assert si genre est toujours présent dans les genres de la médiathèque
        Assert.assertNull(mediatheque.chercherGenre(genre));
    }

    /**
     * Ajout d'un genre déja exitant
     */
    @Test(expectedExceptions=OperationImpossible.class,
            expectedExceptionsMessageRegExp = "ajouter Genre existant: Livre")
    public void test_mediatheque_ajouterGenre_dejaExistant() throws OperationImpossible {

        String genre = "Livre";
        Mediatheque mediatheque = new Mediatheque("mediatheque");

        mediatheque.ajouterGenre(genre);

    }

    @Test
    public void test_mediatheque_ajouterGenre_OK() throws OperationImpossible {

        String genre = "Livre";
        Mediatheque mediatheque = new Mediatheque("mediatheque");

        mediatheque.ajouterGenre(genre);

        // Assert que le genre est bien présent dans la médiathèque
        Assert.assertNotNull(mediatheque.chercherGenre(genre));
    }

    @Test(expectedExceptions=OperationImpossible.class,
            expectedExceptionsMessageRegExp = "Genre \"VHS\" inexistant")
    public void test_mediatheque_modifierGenre_genreInexistant() throws OperationImpossible {

        String old = "VHS";
        String modified = "USB";
        Mediatheque mediatheque = new Mediatheque("mediatheque");

        mediatheque.modifierGenre(old, modified);
    }

    @Test
    public void test_mediatheque_modifierGenre_OK() throws OperationImpossible {

        String old = "Livre";
        String modified = "Liseuse";
        Mediatheque mediatheque = new Mediatheque("mediatheque");

        mediatheque.modifierGenre(old, modified);

        // L'ancien genre ne doit plus exister
        Assert.assertNull(mediatheque.chercherGenre(old));
        // Le nouveau doit exister
        Assert.assertNotNull(mediatheque.chercherGenre(modified));
    }

    @Test
    public void test_mediatheque_chercherLocalisation_nonTrouve() {

        Mediatheque mediatheque = new Mediatheque("mediatheque");
        Assert.assertNull(mediatheque.chercherLocalisation("SalleInconnu", "RayonInconnu"));
    }

    @Test
    public void test_mediatheque_chercherLocalisation_OK() { //TODO: créer datafile

        String salle = "Principale";
        String rayon = "Aventure";
        Mediatheque mediatheque = new Mediatheque("mediatheque");
        Localisation localisation = mediatheque.chercherLocalisation(salle, rayon);
        Assert.assertTrue(localisation.getRayon().equals(rayon));
        Assert.assertTrue(localisation.getSalle().equals(salle));
    }

    @Test(expectedExceptions=OperationImpossible.class,
            expectedExceptionsMessageRegExp = "Localisation SalleInconnu RayonInconnu inexistant")
    public void test_mediatheque_supprimerLocalisation_nonTrouve() throws OperationImpossible {

        String salle = "SalleInconnu";
        String rayon = "RayonInconnnu";

        Mediatheque mediatheque = new Mediatheque("mediatheque");
        mediatheque.supprimerLocalisation(salle, rayon);
    }

    /**
     * Cas où la localisation est dans la médiatheque mais aussi dans les documents.
     */
    @Test(expectedExceptions=OperationImpossible.class,
            expectedExceptionsMessageRegExp = "Suppression de localisation impossible. Il existe au moins un document � la localisation Salle/Rayon : Principale/Aventure")
    public void test_mediatheque_supprimerLocalisation_nonPresentDansDocument() throws OperationImpossible {

        String salle = "Principale";
        String rayon = "Aventure";

        Mediatheque mediatheque = new Mediatheque("mediatheque");
        mediatheque.supprimerLocalisation(salle, rayon); //TODO: Choisir la bonne localisation et la reporter dans l'exception
    }

    @Test
    public void test_mediatheque_supprimerLocalisation_OK() throws OperationImpossible {

        String salle = "Principale";
        String rayon = "Manga";

        Mediatheque mediatheque = new Mediatheque("mediatheque");
        mediatheque.supprimerLocalisation(salle, rayon);

        // Assert si localisation est toujours présent dans les genres de la médiathèque
        Assert.assertNull(mediatheque.chercherLocalisation(salle, rayon));
    }

    @Test(expectedExceptions=OperationImpossible.class,
            expectedExceptionsMessageRegExp = "Localisation \"Principale Aventure\" deja existant")
    public void test_mediatheque_ajouterLocalisation_dejaExistant() throws OperationImpossible {

        String salle = "Principale";
        String rayon = "Aventure";

        Mediatheque mediatheque = new Mediatheque("mediatheque");
        mediatheque.ajouterLocalisation(salle, rayon);
    }

    @Test
    public void test_mediatheque_ajouterLocalisation_OK() throws OperationImpossible {

        String salle = "Principale";
        String rayon = "NouveauRayon";
        Mediatheque mediatheque = new Mediatheque("mediatheque");

        mediatheque.ajouterLocalisation(salle, rayon);

        // Assert que la localisation est bien présente dans la médiathèque
        Assert.assertNotNull(mediatheque.chercherLocalisation(salle, rayon));
    }


    @Test(expectedExceptions=OperationImpossible.class,
            expectedExceptionsMessageRegExp = "Modifier Localisation inexistante")
    public void test_mediatheque_modifierLocalisation_localisationInexistante() throws OperationImpossible {

        String salle = "Principale";
        String rayon = "NouveauRayon";
        Mediatheque mediatheque = new Mediatheque("mediatheque");

        mediatheque.modifierLocalisation(new Localisation(salle, rayon), "sal", "loc");
    }

    @Test
    public void test_mediatheque_modifierLocalisation_memeLocalisation() throws OperationImpossible {

        String salle = "Principale";
        String rayon = "NouveauRayon";
        String nSalle = "nouvelleSalle";
        String nRayon = "nouveauRayon";

        Mediatheque mediatheque = new Mediatheque("mediatheque");

        mediatheque.modifierLocalisation(new Localisation(salle, rayon), salle, rayon);

        // La nouvelle doit exister
        Assert.assertNotNull(mediatheque.chercherLocalisation(salle, rayon));
    }

    @Test
    public void test_mediatheque_modifierLocalisation_OK() throws OperationImpossible {

        String salle = "Principale";
        String rayon = "NouveauRayon";
        String nSalle = "nouvelleSalle";
        String nRayon = "nouveauRayon";

        Mediatheque mediatheque = new Mediatheque("mediatheque");

        mediatheque.modifierLocalisation(new Localisation(salle, rayon), nSalle, nRayon);

        // L'ancienne localisation ne doit plus exister
        Assert.assertNull(mediatheque.chercherLocalisation(salle, rayon));
        // La nouvelle doit exister
        Assert.assertNotNull(mediatheque.chercherLocalisation(nSalle, nRayon));
    }

    @Test
    public void test_mediatheque_chercherCatClient_nonTrouve() {

        Mediatheque mediatheque = new Mediatheque("mediatheque");
        Assert.assertNull(mediatheque.chercherCatClient("CatClientInconnu"));
    }

    @Test
    public void test_mediatheque_chercherClient_OK() {

        String nom = "Premium";
        Mediatheque mediatheque = new Mediatheque("mediatheque");
        CategorieClient categorieClient = mediatheque.chercherCatClient(nom);

        Assert.assertTrue(categorieClient.getNom().equals(nom));
    }

    @Test(expectedExceptions=OperationImpossible.class,
            expectedExceptionsMessageRegExp = "Categorie catClientInconnu inexistante")
    public void test_mediatheque_supprimerCatClient_nonTrouve() throws OperationImpossible {

        String catClient = "catClientInconnu";
        Mediatheque mediatheque = new Mediatheque("mediatheque");
        mediatheque.supprimerCatClient(catClient);
    }

    /**
     * Cas où la catClient est dans la médiatheque mais aussi dans les documents.
     */
    @Test(expectedExceptions=OperationImpossible.class,
            expectedExceptionsMessageRegExp = "Il existe un client dans la categorie Premium")
    public void test_mediatheque_supprimerCatClient_nonPresentDansDocument() throws OperationImpossible {

        String catClient = "Premium";
        Mediatheque mediatheque = new Mediatheque("mediatheque");
        mediatheque.supprimerCatClient(catClient); //TODO: Choisir le bon catClient et le reporter dans l'exception
    }

    @Test
    public void test_mediatheque_supprimerCatClient_OK() throws OperationImpossible {

    String catClient = "TOP 5 CLIENT";
        Mediatheque mediatheque = new Mediatheque("mediatheque");
        mediatheque.supprimerCatClient(catClient);

        // Assert si genre est toujours présent dans les genres de la médiathèque
        Assert.assertNull(mediatheque.chercherCatClient(catClient));
    }
}