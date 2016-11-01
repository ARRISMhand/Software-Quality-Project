package com.ece.mediatheque;


import com.ece.mediatheque.mediatheque.*;
import com.ece.mediatheque.mediatheque.client.CategorieClient;
import com.ece.mediatheque.mediatheque.client.Client;
import com.ece.mediatheque.mediatheque.document.Document;
import com.ece.mediatheque.mediatheque.document.Livre;
import com.ece.mediatheque.util.InvariantBroken;
import org.junit.Assert;

/**
 * Created by DamnAug on 27/10/2016.
 */
public class LettreRappelTest {


    private FicheEmprunt setFicheEmprunt() throws OperationImpossible, InvariantBroken {
        String nomMedia= "Livre";

        String code = "1523bd";
        Localisation localisation = new Localisation("Principale", "Aventure");
        String titre = "titre";
        String auteur = "auteur";
        String annee = "1999";
        Genre genre = new Genre("BD");
        int nombrePage = 252;

        Mediatheque mediatheque = new Mediatheque("mediatheque");
        Client client = new Client("guerard", "aurelien", "50 rue etienne dolet",
                new CategorieClient("Premium", 2, 5, 1.5, 1.5, true), 10);
        Livre livre = new Livre(code, localisation, titre, auteur, annee, genre, nombrePage);
        livre.metEmpruntable();

        return new FicheEmprunt(mediatheque, client, livre);
    }

    @org.testng.annotations.Test
    public void test_lettreRappel_constructor_OK()
            throws OperationImpossible, InvariantBroken {

        String nomMedia= "Livre";

        FicheEmprunt ficheEmprunt  = setFicheEmprunt();

        LettreRappel lettreRappel = new LettreRappel(nomMedia, ficheEmprunt);
    }

    @org.testng.annotations.Test
    public void test_lettreRappel_relancer_OK()
            throws OperationImpossible, InvariantBroken {

        String nomMedia= "Livre";

        FicheEmprunt ficheEmprunt  = setFicheEmprunt();

        LettreRappel lettreRappel = new LettreRappel(nomMedia, ficheEmprunt);

        lettreRappel.relancer();

        System.out.println(lettreRappel.getDateRappel());
    }
}
