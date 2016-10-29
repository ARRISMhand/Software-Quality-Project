package com.ece.mediatheque.DocumentTest;

import com.ece.mediatheque.mediatheque.*;
import com.ece.mediatheque.mediatheque.client.CategorieClient;
import com.ece.mediatheque.mediatheque.client.Client;
import com.ece.mediatheque.mediatheque.document.Document;
import com.ece.mediatheque.mediatheque.document.Livre;
import com.ece.mediatheque.util.InvariantBroken;
import org.junit.Assert;
import org.testng.annotations.Test;


/**
 * Created by DamnAug on 27/10/2016.
 */
public class MediathequeTest {

    @Test
    public void test_mediatheque_saveToFile() throws OperationImpossible, InvariantBroken {
        Mediatheque mediatheque = new Mediatheque("mediatheque");

        mediatheque.empty();
        mediatheque.saveToFile();

        mediatheque.ajouterGenre("BD");
        mediatheque.ajouterGenre("Guerre");
        mediatheque.ajouterGenre("Manga");

        mediatheque.ajouterLocalisation("Principale", "Aventure");
        mediatheque.ajouterLocalisation("Principale", "Manga");

        mediatheque.ajouterCatClient("Premium", 2, 5, 1.5, 1.5, false);
        mediatheque.ajouterCatClient("TOP 5 CLIENT", 2000, 500, 15, 15, false);

        mediatheque.ajouterDocument(new Livre("01010", new Localisation("Principale", "Aventure"), "titre", "auteur", "2000", new Genre("Guerre"), 258 ));

        mediatheque.inscrire("guerard", "aurelien", "50 rue etienne dolet", "Premium", 10);
        mediatheque.inscrire("Denis", "Marchant", "50 rue des poulets", "TOP 5 CLIENT", 20);

        mediatheque.saveToFile();
    }

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
        Genre genre = mediatheque.chercherGenre("BD");
        Assert.assertTrue(genre.getNom().equals("BD"));

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
            expectedExceptionsMessageRegExp = "Suppression de genre impossible. Il existe au moins un document associe au genre Genre: Guerre, nbemprunts:1")
    public void test_mediatheque_supprimerGenre_presentDansDocument() throws OperationImpossible {

        Mediatheque mediatheque = new Mediatheque("mediatheque");
        mediatheque.supprimerGenre("Guerre"); //TODO: Choisir le bon genre et le reporter dans l'exception
    }

    @Test
    public void test_mediatheque_supprimerGenre_OK() throws OperationImpossible {

        String genre = "BD";
        Mediatheque mediatheque = new Mediatheque("mediatheque");
        mediatheque.supprimerGenre(genre);

        // Assert si genre est toujours présent dans les genres de la médiathèque
        Assert.assertNull(mediatheque.chercherGenre(genre));
    }

    /**
     * Ajout d'un genre déja exitant
     */
    @Test(expectedExceptions=OperationImpossible.class,
            expectedExceptionsMessageRegExp = "ajouter Genre existant:BD")
    public void test_mediatheque_ajouterGenre_dejaExistant() throws OperationImpossible {

        String genre = "BD";
        Mediatheque mediatheque = new Mediatheque("mediatheque");

        mediatheque.ajouterGenre(genre);

    }

    @Test
    public void test_mediatheque_ajouterGenre_OK() throws OperationImpossible {

        String genre = "Futuriste";
        Mediatheque mediatheque = new Mediatheque("mediatheque");

        mediatheque.ajouterGenre(genre);

        // Assert que le genre est bien présent dans la médiathèque
        Assert.assertNotNull(mediatheque.chercherGenre(genre));
    }

    @Test(expectedExceptions=OperationImpossible.class,
            expectedExceptionsMessageRegExp = "Genre \"MangaInexistant\" inexistant")
    public void test_mediatheque_modifierGenre_genreInexistant() throws OperationImpossible {

        String old = "MangaInexistant";
        String modified = "USB";
        Mediatheque mediatheque = new Mediatheque("mediatheque");

        mediatheque.modifierGenre(old, modified);
    }

    @Test
    public void test_mediatheque_modifierGenre_OK() throws OperationImpossible {

        String old = "BD";
        String modified = "BDNumerique";
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
        String rayon = "RayonInconnu";

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
        String rayon = "Manga";

        Mediatheque mediatheque = new Mediatheque("mediatheque");

        mediatheque.modifierLocalisation(new Localisation(salle, rayon), salle, rayon);

        // La nouvelle doit exister
        Assert.assertNotNull(mediatheque.chercherLocalisation(salle, rayon));
    }

    @Test
    public void test_mediatheque_modifierLocalisation_OK() throws OperationImpossible {

        String salle = "Principale";
        String rayon = "Manga";
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
        mediatheque.resilier("Denis", "Marchant");
        mediatheque.supprimerCatClient(catClient);

        // Assert si genre est toujours présent dans les genres de la médiathèque
        Assert.assertNull(mediatheque.chercherCatClient(catClient));
    }

    @Test(expectedExceptions=OperationImpossible.class,
            expectedExceptionsMessageRegExp = "Categorie client \"Premium\" deja existant")
    public void test_mediatheque_ajouterCatClient_dejaExistant() throws OperationImpossible {

        String name = "Premium";
        int max = 2;
        double cot = 5;
        double coefDuree = 1.5;
        double coefTarif = 1.5;
        boolean codeReducUsed = false;


        Mediatheque mediatheque = new Mediatheque("mediatheque");
        mediatheque.ajouterCatClient(name, max, cot, coefDuree, coefTarif, codeReducUsed);
    }

    @Test
    public void test_mediatheque_ajouterCatClient_OK() throws OperationImpossible {

        String name = "TOP 10 CLIENT";
        int max = 2000;
        double cot = 500;
        double coefDuree = 15;
        double coefTarif = 15;
        boolean codeReducUsed = false;

        Mediatheque mediatheque = new Mediatheque("mediatheque");

        mediatheque.ajouterCatClient(name, max, cot, coefDuree, coefTarif, codeReducUsed);

        // Assert que la localisation est bien présente dans la médiathèque
        Assert.assertNotNull(mediatheque.chercherCatClient(name));
    }

    @Test(expectedExceptions=OperationImpossible.class,
            expectedExceptionsMessageRegExp = "Categorie client \"PremiumInexistant\" inexistante")
    public void test_mediatheque_modifierCatClient_catClientInexistante() throws OperationImpossible {

        String name = "PremiumInexistant";
        int max = 2;
        double cot = 5;
        double coefDuree = 1.5;
        double coefTarif = 1.5;
        boolean codeReducUsed = false;
        Mediatheque mediatheque = new Mediatheque("mediatheque");

        mediatheque.modifierCatClient(new CategorieClient(name, max, cot, coefDuree, coefTarif, codeReducUsed), "Denis", 1,  1, 1, 1, true);
    }

    @Test
    public void test_mediatheque_modifierCatClient_memeCatClient() throws OperationImpossible {

        String name = "Premium";
        int max = 2;
        double cot = 5;
        double coefDuree = 1.5;
        double coefTarif = 1.5;
        boolean codeReducUsed = false;

        Mediatheque mediatheque = new Mediatheque("mediatheque");

        mediatheque.modifierCatClient(new CategorieClient(name, max, cot, coefDuree, coefTarif, codeReducUsed), name, max, cot, coefDuree, coefTarif, codeReducUsed);

        // La nouvelle doit exister
        Assert.assertNotNull(mediatheque.chercherCatClient(name));
    }

    @Test
    public void test_mediatheque_modifierCatClient_OK() throws OperationImpossible {

        String name = "Premium";
        int max = 2;
        double cot = 5;
        double coefDuree = 1.5;
        double coefTarif = 1.5;
        boolean codeReducUsed = false;
        String nName = "Premium+";
        int nMax = 3;
        double nCot = 6;
        double nCoefDuree = 2;
        double nCoefTarif = 2;
        boolean nCodeReducUsed = true;

        Mediatheque mediatheque = new Mediatheque("mediatheque");

        mediatheque.modifierCatClient(new CategorieClient(name, max, cot, coefDuree, coefTarif, codeReducUsed), nName, nMax, nCot, nCoefDuree, nCoefTarif, nCodeReducUsed);

        // L'ancienne ne doit plus exister
        Assert.assertNull(mediatheque.chercherCatClient(name));
        // La nouvelle doit exister
        Assert.assertNotNull(mediatheque.chercherCatClient(nName));
    }

    @Test(expectedExceptions=OperationImpossible.class,
            expectedExceptionsMessageRegExp = "Document \"01010\" deja existant")
    public void test_mediatheque_ajouterDocument_dejaExistant() throws OperationImpossible, InvariantBroken {

        String code = "01010";
        Localisation localisation = new Localisation("Principale", "BD");
        String titre = "titre";
        String auteur = "auteur";
        String annee = "2000";
        Genre genre = new Genre("BD");
        int nombrePage = 258;
        Mediatheque mediatheque = new Mediatheque("mediatheque");

        mediatheque.ajouterDocument(new Livre(code, localisation, titre, auteur, annee, genre, nombrePage));
    }

    @Test(expectedExceptions=OperationImpossible.class,
            expectedExceptionsMessageRegExp = "Ajout d'un document avec un genre non inclus dans la mediatheque")
    public void test_mediatheque_ajouterDocument_genreNonInclu() throws OperationImpossible, InvariantBroken {

        String code = "1523bd";
        Localisation localisation = new Localisation("Principale", "BD");
        String titre = "titre";
        String auteur = "auteur";
        String annee = "1999";
        Genre genre = new Genre("BDNonInclu");
        int nombrePage = 252;
        Mediatheque mediatheque = new Mediatheque("mediatheque");

        mediatheque.ajouterDocument(new Livre(code, localisation, titre, auteur, annee, genre, nombrePage));
    }

    @Test(expectedExceptions=OperationImpossible.class,
            expectedExceptionsMessageRegExp = "Ajout d'un document avec une localisation inexistante")
    public void test_mediatheque_ajouterDocument_localisationNonInclu() throws OperationImpossible, InvariantBroken {

        String code = "1523bd";
        Localisation localisation = new Localisation("Principale", "BDNonInclu");
        String titre = "titre";
        String auteur = "auteur";
        String annee = "1999";
        Genre genre = new Genre("BD");
        int nombrePage = 252;
        Mediatheque mediatheque = new Mediatheque("mediatheque");

        mediatheque.ajouterDocument(new Livre(code, localisation, titre, auteur, annee, genre, nombrePage));
    }

    @Test
    public void test_mediatheque_ajouterDocument_OK() throws OperationImpossible, InvariantBroken {

        String code = "1523bd";
        Localisation localisation = new Localisation("Principale", "Aventure");
        String titre = "titre";
        String auteur = "auteur";
        String annee = "1999";
        Genre genre = new Genre("BD");
        int nombrePage = 252;
        Livre livre = new Livre(code, localisation, titre, auteur, annee, genre, nombrePage);

        Mediatheque mediatheque = new Mediatheque("mediatheque");
        mediatheque.ajouterDocument(livre);

        Assert.assertEquals(mediatheque.chercherDocument(code), livre);
    }

    @Test(expectedExceptions=OperationImpossible.class,
            expectedExceptionsMessageRegExp = "Document \"01010\" emprunte")
    public void test_mediatheque_retirerDocument_emprunte() throws OperationImpossible, InvariantBroken {

        String code = "01010";
        Mediatheque mediatheque = new Mediatheque("mediatheque");

        mediatheque.chercherDocument(code).metEmpruntable();
        mediatheque.emprunter("Denis", "Marchant", code);
        mediatheque.retirerDocument(code);
    }

    @Test(expectedExceptions=OperationImpossible.class,
            expectedExceptionsMessageRegExp = "Document 1977ad inexistant")
    public void test_mediatheque_retirerDocument_nonExistant() throws OperationImpossible {

        String code = "1977ad";
        Mediatheque mediatheque = new Mediatheque("mediatheque");
        mediatheque.retirerDocument(code);
    }

    @Test
    public void test_mediatheque_retirerDocument_OK() throws OperationImpossible {

        String code = "01010";
        Mediatheque mediatheque = new Mediatheque("mediatheque");

        mediatheque.retirerDocument(code);

        Assert.assertNull(mediatheque.chercherDocument(code));
    }

    @Test(expectedExceptions=OperationImpossible.class,
            expectedExceptionsMessageRegExp = "MetEmpruntable code inexistant:1977ad")
    public void test_mediatheque_metEmpruntable_nonExistant() throws OperationImpossible, InvariantBroken {

        String code = "1977ad";
        Mediatheque mediatheque = new Mediatheque("mediatheque");

        mediatheque.metEmpruntable(code);
    }

    @Test
    public void test_mediatheque_metEmpruntable_OK() throws OperationImpossible, InvariantBroken {

        String code = "01010";
        Mediatheque mediatheque = new Mediatheque("mediatheque");

        mediatheque.metEmpruntable(code);
        Assert.assertTrue(mediatheque.chercherDocument(code).estEmpruntable());
    }

    @Test(expectedExceptions=OperationImpossible.class,
            expectedExceptionsMessageRegExp = "MetConsultable code inexistant:1977ad")
    public void test_mediatheque_metConsultable_nonExistant() throws OperationImpossible, InvariantBroken {

        String code = "1977ad";
        Mediatheque mediatheque = new Mediatheque("mediatheque");

        mediatheque.metConsultable(code);
    }

    @Test
    public void test_mediatheque_metConsultable_OK() throws OperationImpossible, InvariantBroken {

        String code = "01010";
        Mediatheque mediatheque = new Mediatheque("mediatheque");

        //mediatheque.metConsultable(code);
        Assert.assertFalse(mediatheque.chercherDocument(code).estEmpruntable());
    }

    @Test(expectedExceptions=OperationImpossible.class,
            expectedExceptionsMessageRegExp = "Client dupont christian inexistant")
    public void test_mediatheque_emprunter_clientInconnu() throws OperationImpossible, InvariantBroken {

        String nom = "dupont";
        String prenom = "christian";

        Mediatheque mediatheque = new Mediatheque("mediatheque");

        mediatheque.emprunter(nom, prenom, "2222");
    }

    @Test(expectedExceptions=OperationImpossible.class,
            expectedExceptionsMessageRegExp = "Can not borrow")
    public void test_mediatheque_emprunter_limiteEmpruntsClient() throws OperationImpossible, InvariantBroken {

        String nom = "guerard";
        String prenom = "aurelien";

        Mediatheque mediatheque = new Mediatheque("mediatheque");

        Client client = mediatheque.chercherClient(nom, prenom);
        String code = "1523bd";
        Localisation localisation = new Localisation("Principale", "Aventure");
        String titre = "titre";
        String auteur = "auteur";
        String annee = "1999";
        Genre genre = new Genre("BD");
        int nombrePage = 252;
        FicheEmprunt ficheEmprunt;
        Livre l;

        code = "1993bd";
        l = new Livre(code, localisation, titre, auteur, annee, genre, nombrePage);
        mediatheque.ajouterDocument(l);
        mediatheque.chercherDocument(code).metEmpruntable();
        ficheEmprunt = new FicheEmprunt(mediatheque, client, l);

        code = "1423bd";
        l = new Livre(code, localisation, titre, auteur, annee, genre, nombrePage);
        mediatheque.ajouterDocument(l);
        mediatheque.chercherDocument(code).metEmpruntable();
        ficheEmprunt = new FicheEmprunt(mediatheque, client, l);

        code = "1323bd";
        l = new Livre(code, localisation, titre, auteur, annee, genre, nombrePage);
        mediatheque.ajouterDocument(l);
        mediatheque.chercherDocument(code).metEmpruntable();
        ficheEmprunt = new FicheEmprunt(mediatheque, client, l);

        mediatheque.emprunter(nom, prenom, "2222");
    }








    @Test
    public void test_mediatheque_emprunter_OK() throws OperationImpossible, InvariantBroken {

        String nom = "guerard";
        String prenom = "aurelien";
        String code = "01010";

        Mediatheque mediatheque = new Mediatheque("mediatheque");

        mediatheque.chercherDocument(code).metEmpruntable();
        mediatheque.emprunter(nom, prenom, code);

        Assert.assertTrue(mediatheque.chercherDocument(code).estEmprunte());
    }

}