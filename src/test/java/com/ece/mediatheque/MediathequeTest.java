package com.ece.mediatheque;

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

    @org.junit.Test
    public void test_mediatheque_saveToFile() throws OperationImpossible, InvariantBroken {
        Mediatheque mediatheque = new Mediatheque("mediatheque");

        mediatheque.empty();
        mediatheque.saveToFile();

        mediatheque.ajouterGenre("BD");
        mediatheque.ajouterGenre("Guerre");
        mediatheque.ajouterGenre("Manga");

        mediatheque.ajouterLocalisation("Principale", "Aventure");
        mediatheque.ajouterLocalisation("Principale", "Manga");

        mediatheque.ajouterCatClient("Premium", 2, 5, 1.5, 1.5, true);
        mediatheque.ajouterCatClient("TOP 5 CLIENT", 2000, 500, 15, 15, true);
        mediatheque.ajouterCatClient("TOP 10 CLIENT", 2000, 500, 15, 15, false);
        mediatheque.ajouterCatClient("TOP 15 CLIENT", 300, 20, 10, 10, false);

        mediatheque.ajouterDocument(new Livre("01010", new Localisation("Principale", "Aventure"), "titre", "auteur", "2000", new Genre("Guerre"), 258 ));
        mediatheque.ajouterDocument(new Livre("010111", new Localisation("Principale", "Aventure"), "essay", "dupont", "2000", new Genre("Guerre"), 258 ));

        mediatheque.inscrire("guerard", "aurelien", "50 rue etienne dolet", "Premium", 10);
        mediatheque.inscrire("Denis", "Marchant", "50 rue des poulets", "TOP 5 CLIENT", 20);
        mediatheque.inscrire("georgel", "edgar", "rue de la montagne", "TOP 15 CLIENT");

        mediatheque.saveToFile();
    }

    @org.junit.Test
    public void test_mediatheque_inexistanteMediatheque() {
        Mediatheque mediatheque = new Mediatheque("inexistanteMediatheque");
        Assert.assertFalse(mediatheque.initFromFile());
    }

    @org.junit.Test
    public void test_mediatheque_exsitanteMediatheque() { //TODO: créer datafile

        Mediatheque mediatheque = new Mediatheque("mediatheque");
        mediatheque.listerFicheEmprunts();
        mediatheque.listerGenres();
        mediatheque.listerCatsClient();
        mediatheque.listerClients();
        mediatheque.listerFicheEmprunts();
        mediatheque.listerGenres();
        mediatheque.listerDocuments();
        mediatheque.listerLocalisations();
        mediatheque.getCategorieAt(0);
        mediatheque.getClientAt(0);
        mediatheque.getDocumentAt(0);
        mediatheque.getGenreAt(0);
        mediatheque.getLocalisationAt(0);
        mediatheque.getClientAt(0);
        Assert.assertTrue(mediatheque.initFromFile());
    }

    @org.junit.Test
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

    @org.junit.Test
    public void test_mediatheque_chercherGenre_nonTrouve() {

        Mediatheque mediatheque = new Mediatheque("mediatheque");
        Assert.assertNull(mediatheque.chercherGenre("GenreInconnu"));
    }

    @org.junit.Test
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

    @org.junit.Test
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

    @org.junit.Test
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

    @org.junit.Test
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

    @org.junit.Test
    public void test_mediatheque_chercherLocalisation_nonTrouve() {

        Mediatheque mediatheque = new Mediatheque("mediatheque");
        Assert.assertNull(mediatheque.chercherLocalisation("SalleInconnu", "RayonInconnu"));
    }

    @org.junit.Test
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

    @org.junit.Test
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

    @org.junit.Test
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

    @org.junit.Test
    public void test_mediatheque_modifierLocalisation_memeLocalisation() throws OperationImpossible {

        String salle = "Principale";
        String rayon = "Manga";

        Mediatheque mediatheque = new Mediatheque("mediatheque");

        mediatheque.modifierLocalisation(new Localisation(salle, rayon), salle, rayon);

        // La nouvelle doit exister
        Assert.assertNotNull(mediatheque.chercherLocalisation(salle, rayon));
    }

    @org.junit.Test
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

    @org.junit.Test
    public void test_mediatheque_chercherCatClient_nonTrouve() {

        Mediatheque mediatheque = new Mediatheque("mediatheque");
        Assert.assertNull(mediatheque.chercherCatClient("CatClientInconnu"));
    }

    @org.junit.Test
    public void test_mediatheque_chercherCatClient_OK() {

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

    @org.junit.Test
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

    @org.junit.Test
    public void test_mediatheque_ajouterCatClient_OK() throws OperationImpossible {

        String name = "TOP 3 CLIENT";
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

    @org.junit.Test
    public void test_mediatheque_modifierCatClient_CatClientIdentique() throws OperationImpossible {

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

    @org.junit.Test
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

    @org.junit.Test
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

    @org.junit.Test
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

    @org.junit.Test
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

    @org.junit.Test
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
            expectedExceptionsMessageRegExp = "Client guerard non autorise a emprunter")
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
        Genre genre = new Genre("Guerre");
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

        mediatheque.emprunter(nom, prenom, "2222");
    }

    @Test(expectedExceptions=OperationImpossible.class,
            expectedExceptionsMessageRegExp = "Document 1555ef inexistant")
    public void test_mediatheque_emprunter_documentInconnu() throws OperationImpossible, InvariantBroken {

        String nom = "guerard";
        String prenom = "aurelien";
        String code = "1555ef";

        Mediatheque mediatheque = new Mediatheque("mediatheque");

        mediatheque.emprunter(nom, prenom, code);
    }

    @Test(expectedExceptions=OperationImpossible.class,
            expectedExceptionsMessageRegExp = "Document 01010 non empruntable")
    public void test_mediatheque_emprunter_dcumentNonEmpruntable() throws OperationImpossible, InvariantBroken {

        String nom = "guerard";
        String prenom = "aurelien";
        String code = "01010";

        Mediatheque mediatheque = new Mediatheque("mediatheque");

        mediatheque.emprunter(nom, prenom, code);
    }

    @Test(expectedExceptions=OperationImpossible.class,
            expectedExceptionsMessageRegExp = "Document 01010 deja emprunte")
    public void test_mediatheque_emprunter_dejaEmprunte() throws OperationImpossible, InvariantBroken {

        String nom = "guerard";
        String prenom = "aurelien";
        String code = "01010";

        Mediatheque mediatheque = new Mediatheque("mediatheque");

        mediatheque.chercherDocument(code).metEmpruntable();

        mediatheque.emprunter(nom, prenom, code);
        mediatheque.emprunter(nom, prenom, code);
    }

    @org.junit.Test
    public void test_mediatheque_emprunter_OK() throws OperationImpossible, InvariantBroken {

        String nom = "guerard";
        String prenom = "aurelien";
        String code = "01010";

        Mediatheque mediatheque = new Mediatheque("mediatheque");

        mediatheque.chercherDocument(code).metEmpruntable();

        Assert.assertFalse(mediatheque.chercherDocument(code).estEmprunte());
        mediatheque.emprunter(nom, prenom, code);

        Assert.assertTrue(mediatheque.chercherDocument(code).estEmpruntable());
        Assert.assertTrue(mediatheque.chercherDocument(code).estEmprunte());
    }


    @Test(expectedExceptions=OperationImpossible.class,
            expectedExceptionsMessageRegExp = "Client dupont christian inexistant")
    public void test_mediatheque_restituer_clientInconnu() throws OperationImpossible, InvariantBroken {

        String nom = "dupont";
        String prenom = "christian";

        Mediatheque mediatheque = new Mediatheque("mediatheque");

        mediatheque.restituer(nom, prenom, "2222");
    }

    @Test(expectedExceptions=OperationImpossible.class,
            expectedExceptionsMessageRegExp = "Document 1555ef inexistant")
    public void test_mediatheque_restituer_documentInconnu() throws OperationImpossible, InvariantBroken {

        String nom = "guerard";
        String prenom = "aurelien";
        String code = "1555ef";

        Mediatheque mediatheque = new Mediatheque("mediatheque");

        mediatheque.restituer(nom, prenom, code);
    }

    @Test(expectedExceptions=OperationImpossible.class,
            expectedExceptionsMessageRegExp = "Emprunt par \"guerard\" de \"010111\" non trouve")
    public void test_mediatheque_restituer_empruntNonTrouve() throws OperationImpossible, InvariantBroken {

        String nom = "guerard";
        String prenom = "aurelien";
        String code = "01010";

        String nom2 = "guerard2";
        String prenom2 = "aurel2ien";
        String code2 = "010111";
        Mediatheque mediatheque = new Mediatheque("mediatheque");
        mediatheque.inscrire(nom2, prenom2, "rue", "Premium");
        mediatheque.chercherDocument(code).metEmpruntable();
        mediatheque.chercherDocument(code2).metEmpruntable();
        mediatheque.emprunter(nom, prenom, code);
        mediatheque.emprunter(nom2, prenom2, code2);
        mediatheque.restituer(nom, prenom, code2);
    }

    @org.junit.Test
    public void test_mediatheque_restituer_OK() throws OperationImpossible, InvariantBroken {

        String nom = "guerard";
        String prenom = "aurelien";
        String code = "01010";

        Mediatheque mediatheque = new Mediatheque("mediatheque");

        mediatheque.chercherDocument(code).metEmpruntable();
        mediatheque.emprunter(nom, prenom, code);


        Assert.assertTrue(mediatheque.chercherDocument(code).estEmprunte());

        mediatheque.restituer(nom, prenom, code);

        Assert.assertFalse(mediatheque.chercherDocument(code).estEmprunte());
    }

    @Test(expectedExceptions=OperationImpossible.class,
            expectedExceptionsMessageRegExp = "Pas de categorie client CatInconnue")
    public void test_mediatheque_inscire_catClientInconnue() throws OperationImpossible {

        Mediatheque mediatheque = new Mediatheque("mediatheque");
        mediatheque.inscrire("nom", "prenom", "rue", "CatInconnue");
    }

    @org.junit.Test
    public void test_mediatheque_inscrire_sansCode_OK() throws OperationImpossible {

        String nom = "gorge";
        String prenom = "jyli";
        String addresse = "rue";

        String cat = "inscriptionSansCode";
        int max = 16;
        double cot = 500;
        double coefDuree = 15;
        double coefTarif = 15;
        boolean codeReducUsed = false;

        Mediatheque mediatheque = new Mediatheque("mediatheque");
        mediatheque.ajouterCatClient(cat, max, cot, coefDuree, coefTarif,codeReducUsed);

        Assert.assertTrue(mediatheque.inscrire(nom, prenom, addresse, cat) == cot);
        Assert.assertTrue(mediatheque.chercherClient(nom, prenom) != null);
    }

    @org.junit.Test
    public void test_mediatheque_inscrire_avecCode_OK() throws OperationImpossible {

        String nom = "gorge";
        String prenom = "joel";
        String addresse = "rue";

        String cat = "inscriptionSansCode";
        int max = 2000;
        double cot = 500;
        double coefDuree = 20;
        double coefTarif = 15;
        boolean codeReducUsed = true;

        Mediatheque mediatheque = new Mediatheque("mediatheque");
        mediatheque.ajouterCatClient(cat, max, cot, coefDuree, coefTarif,codeReducUsed);

        Assert.assertTrue(mediatheque.inscrire(nom, prenom, addresse, cat, 20) == cot);
        Assert.assertTrue(mediatheque.chercherClient(nom, prenom) != null);
    }

    @Test(expectedExceptions=OperationImpossible.class,
            expectedExceptionsMessageRegExp = "Client guerard aurelien deja existant")
    public void test_mediatheque_inscrire_dejaExistant() throws OperationImpossible {
        String nom = "guerard";
        String prenom = "aurelien";
        String addresse = "rue";

        Mediatheque mediatheque = new Mediatheque("mediatheque");
        mediatheque.inscrire(nom, prenom, addresse, "Premium", 20);
    }

    @Test(expectedExceptions=OperationImpossible.class,
            expectedExceptionsMessageRegExp = "Client nom prenom inexistant")
    public void test_mediatheque_resilier_clientInconnue() throws OperationImpossible {

        Mediatheque mediatheque = new Mediatheque("mediatheque");
        mediatheque.resilier("nom", "prenom");
    }


    @Test(expectedExceptions=OperationImpossible.class,
            expectedExceptionsMessageRegExp = "Client guerard aurelien n'a pas restitue tous ses emprunts")
    public void test_mediatheque_resilier_empruntsEnCours() throws OperationImpossible {

        String nom = "guerard";
        String prenom = "aurelien";

        Mediatheque mediatheque = new Mediatheque("mediatheque");

        mediatheque.chercherClient(nom, prenom).emprunter();

        mediatheque.resilier(nom, prenom);
    }

    @org.junit.Test
    public void test_mediatheque_resilier_OK() throws OperationImpossible {

        String nom = "guerard";
        String prenom = "aurelien";

        Mediatheque mediatheque = new Mediatheque("mediatheque");
        mediatheque.resilier(nom, prenom);

        Assert.assertNull(mediatheque.chercherClient(nom, prenom));
    }

    @Test(expectedExceptions=OperationImpossible.class,
            expectedExceptionsMessageRegExp = "Client nom prenom inexistant")
    public void test_mediatheque_modifierClient_clientInexistant() throws OperationImpossible {

        Mediatheque mediatheque = new Mediatheque("mediatheque");
        mediatheque.modifierClient(new Client("nom", "prenom"), "nom", "prenom", "addresse", "Premium", 20);
    }

    @org.junit.Test
    public void test_mediatheque_modifierClient_memeInfos() throws OperationImpossible {

        String  nom = "guerard";
        String  prenom = "aurelien";
        String  adresse = "50 rue etienne dolet";
        String  catClient = "Premium";
        int code = 10;
        Mediatheque mediatheque = new Mediatheque("mediatheque");
        Client client = new Client(nom, prenom, adresse, new CategorieClient(catClient));
        mediatheque.modifierClient(client, nom, prenom, adresse, catClient, code);
    }

    @org.junit.Test
    public void test_mediatheque_modifierClient_sansCode_OK() throws OperationImpossible {

        String catClient = "TOP 10 CLIENT";

        String catClient2 = "TOP 15 CLIENT";

        String  nom = "guerard";
        String  prenom = "aurelien";
        String  adresse = "50 rue etienne dolet";


        String  nom2 = "guerar2d";
        String  prenom2 = "aurel2ien";
        String  adresse2 = "rue";

        Mediatheque mediatheque = new Mediatheque("mediatheque");

        Client client = new Client(nom, prenom, adresse, new CategorieClient(catClient));
        mediatheque.modifierClient(client, nom2, prenom2, adresse2, catClient2, 10);

        //Vérifie des modif de nom, prenom, adresse, catClient
        Client clientRsult = mediatheque.chercherClient(nom2, prenom2);
        Assert.assertEquals(clientRsult.getAdresse(), adresse2);
        Assert.assertEquals(clientRsult.getCategorie(), new CategorieClient(catClient2));
        Assert.assertEquals(clientRsult.getCategorie(), new CategorieClient(catClient2));

        //L'ancien ne doit plus exister
        Assert.assertNull(mediatheque.chercherClient(nom, prenom));
    }

    @org.junit.Test
    public void test_mediatheque_modifierClient_avecCode_OK() throws OperationImpossible {
        String catClient = "TOP 10 CLIENT";
        int max = 2000;
        double cot = 500;
        double coefDuree = 15;
        double coefTarif = 15;
        boolean codeReducUsed = false;

        String  nom = "guerard";
        String  prenom = "aurelien";
        String  adresse = "50 rue etienne dolet";


        String  nom2 = "guerar2d";
        String  prenom2 = "aurel2ien";
        String  adresse2 = "rue";

        String catClient2 = "Premium";
        int code = 10;
        Mediatheque mediatheque = new Mediatheque("mediatheque");

        Client client = new Client(nom, prenom, adresse, new CategorieClient(catClient));
        Client client2 = new Client(nom2, prenom2, adresse2, new CategorieClient(catClient));
        mediatheque.modifierClient(client, nom2, prenom2, adresse2, catClient2, code);

        //Vérifie des modif de nom, prenom, adresse, catClient
        Client clientRsult = mediatheque.chercherClient(nom2, prenom2);
        Assert.assertEquals(clientRsult.getAdresse(), adresse2);
        Assert.assertEquals(clientRsult.getCategorie(), new CategorieClient(catClient2));
        Assert.assertEquals(clientRsult.getCategorie(), new CategorieClient(catClient2));

        //L'ancien ne doit plus exister
        Assert.assertNull(mediatheque.chercherClient(nom, prenom));
    }

    @Test(expectedExceptions=OperationImpossible.class,
            expectedExceptionsMessageRegExp = "Client nom prenom non trouve")
    public void test_mediatheque_changerCategorie_clientInconnu() throws OperationImpossible {

        Mediatheque mediatheque = new Mediatheque("mediatheque");
        mediatheque.changerCategorie("nom", "prenom", "Premium", 20);
    }

    @Test(expectedExceptions=OperationImpossible.class,
            expectedExceptionsMessageRegExp = "Categorie client catClientInconnu non trouvee")
    public void test_mediatheque_changerCategorie_catClientInconnu() throws OperationImpossible {

        Mediatheque mediatheque = new Mediatheque("mediatheque");
        mediatheque.changerCategorie("guerard", "aurelien", "catClientInconnu", 20);
    }

    @org.junit.Test
    public void test_mediatheque_changerCategorie_avecCode_OK() throws OperationImpossible {

        String nom = "guerard";
        String prenom = "aurelien";
        int code = 156;

        String catClientAvecCode = "TOP 5 CLIENT";
        Mediatheque mediatheque = new Mediatheque("mediatheque");
        mediatheque.changerCategorie(nom, prenom, catClientAvecCode, code);

        Assert.assertEquals(mediatheque.chercherClient(nom, prenom).getReduc(), code);
        Assert.assertEquals(mediatheque.chercherClient(nom, prenom).getCategorie().getNom(), catClientAvecCode);

    }
    @org.junit.Test
    public void test_mediatheque_changerCategorie_sansCode_OK() throws OperationImpossible {


        String catClientSansCode = "TOP 15 CLIENT";
        String nom = "guerard";
        String prenom = "aurelien";
        int code = 156;


        Mediatheque mediatheque = new Mediatheque("mediatheque");


        mediatheque.changerCategorie(nom, prenom, catClientSansCode, code);

        Assert.assertEquals(mediatheque.chercherClient(nom, prenom).getReduc(), 0);
        Assert.assertEquals(mediatheque.chercherClient(nom, prenom).getCategorie().getNom(), catClientSansCode);
    }

    @Test(expectedExceptions=OperationImpossible.class,
            expectedExceptionsMessageRegExp = "Client nom prenom non trouve")
    public void test_mediatheque_changerCodeReduction_clientInconnu() throws OperationImpossible {

        Mediatheque mediatheque = new Mediatheque("mediatheque");
        mediatheque.changerCodeReduction("nom", "prenom", 20);
    }

    @Test(expectedExceptions=OperationImpossible.class,
            expectedExceptionsMessageRegExp = "Changement de code de reduction sur une categorie sans code")
    public void test_mediatheque_changerCodeReduction_categorieSansCode() throws OperationImpossible {

        Mediatheque mediatheque = new Mediatheque("mediatheque");
        mediatheque.changerCodeReduction("georgel", "edgar", 20);
    }

    @org.junit.Test
    public void test_mediatheque_changerCodeReduction_OK() throws OperationImpossible {

        String nom = "guerard";
        String prenom = "aurelien";
        int code = 154;

        Mediatheque mediatheque = new Mediatheque("mediatheque");
        mediatheque.changerCodeReduction(nom, prenom, code);

        Assert.assertEquals(mediatheque.chercherClient(nom, prenom).getReduc(), code);
    }

    @org.junit.Test
    public void test_mediatheque_chercherClient_clientInconnu() {

        Mediatheque mediatheque = new Mediatheque("mediatheque");

        Client client = mediatheque.chercherClient("nom", "prenom");

        Assert.assertNull(client);
    }

    @org.junit.Test
    public void test_mediatheque_chercherClient_OK() {

        Mediatheque mediatheque = new Mediatheque("mediatheque");


        Client client = mediatheque.chercherClient("guerard", "aurelien");

        Assert.assertNotNull(client);
    }

    @org.junit.Test
    public void test_mediatheque_existeClient_vrai() {

        Mediatheque mediatheque = new Mediatheque("mediatheque");

        Assert.assertTrue(mediatheque.existeClient(new CategorieClient("Premium")));
    }

    @org.junit.Test
    public void test_mediatheque_existeClient_faux() {

        Mediatheque mediatheque = new Mediatheque("mediatheque");

        Assert.assertFalse(mediatheque.existeClient(new CategorieClient("TOP 10 CLIENT")));
    }
}