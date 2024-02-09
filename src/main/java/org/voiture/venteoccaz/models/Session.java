package org.voiture.venteoccaz.models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;

@Entity
@Data
@Table(name = "sessions")
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idsession")
    Integer idSession;

    @ManyToOne
    @JoinColumn(name = "idutilisateur", nullable = false)
    Utilisateur utilisateur;
    
    @Column(name = "dateheurelogin", nullable = false)
    LocalDateTime dateHeureLogin;

    @Column(name = "code", nullable = false)
    String code;
    
    @Column(name = "isconnected", nullable = false)
    Integer isConnected;

    @Column(name = "token", nullable = false)
    String token;

    @Column(name = "token_fcm")
    String tokenFcm;
}
