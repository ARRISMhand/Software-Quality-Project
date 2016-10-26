package com.ece.mediatheque.mediatheque.document;

import com.ece.mediatheque.mediatheque.Genre;
import com.ece.mediatheque.mediatheque.Localisation;
import com.ece.mediatheque.mediatheque.OperationImpossible;

/**
 * Created by Edgar on 26/10/2016.
 */
public class DocumentImpl extends Document {
    public DocumentImpl(String code, Localisation localisation, String titre, String auteur, String annee, Genre genre) throws OperationImpossible {
        super(code, localisation, titre, auteur, annee, genre);
    }

    public int dureeEmprunt() {
        return 1;
    }

    public double tarifEmprunt() {
        return 0;
    }
}
