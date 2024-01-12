package org.voiture.venteoccaz;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.voiture.venteoccaz.Repositories.mongodb.MessagerieRepository;
import org.voiture.venteoccaz.models.mongodb.Messagerie;
import java.util.List;

@SpringBootTest
class VenteOccazApplicationTests {
    private final MessagerieRepository messagerieRepository;
    @Autowired
    public VenteOccazApplicationTests(MessagerieRepository messagerieRepository) {
        this.messagerieRepository = messagerieRepository;
    }

    @Test
    void contextLoads() {
        System.out.println("Hello World !");
    }


    @Test
    void selectMessagerie() {
        List<Messagerie> messagerieList = messagerieRepository.findAll();
        System.out.println("=============\n"+messagerieList+"\n=========");
    }

}
