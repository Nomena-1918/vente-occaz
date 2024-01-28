package org.voiture.venteoccaz;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.voiture.venteoccaz.models.Annonce;
import org.voiture.venteoccaz.services.AnnonceService;

import java.util.List;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class TestAnnonce {
    private final AnnonceService annonceService;

    @Autowired
    public TestAnnonce(AnnonceService annonceService) {
        this.annonceService = annonceService;
    }

    @Test
    void testNoFiltre() {
        List<Annonce> allAnnoncesValidesNonVendues = annonceService.getAllAnnoncesValidesNonVendues();
        var a = allAnnoncesValidesNonVendues;
    }

    @Test
    void testNoFiltreUser() {
        List<Annonce> allAnnoncesValidesNonVendues = annonceService.getAllAnnoncesValidesNonVendues(2);
        var a = allAnnoncesValidesNonVendues;
    }

    @Test
    void testEtatFavoriNoFiltreUser() {
        List<Annonce> allAnnoncesValidesNonVendues = annonceService.getAnnonceEtatFavoriValidesNonVendues(1);
        var a = allAnnoncesValidesNonVendues;
    }
}
