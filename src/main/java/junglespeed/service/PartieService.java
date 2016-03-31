/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package junglespeed.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import junglespeed.Config;
import junglespeed.entity.Carte;
import junglespeed.entity.Partie;
import junglespeed.entity.Utilisateur;
import junglespeed.enumeration.Couleur;
import junglespeed.enumeration.Etat;
import junglespeed.enumeration.Statut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author admin
 */
@Service
public class PartieService {

    @Autowired
    PartieCrudService partieCrudService;

    @Autowired
    UtilisateurCrudService utilisateurCrudService;

    @Autowired
    CarteCrudService carteCrudService;

    public void creer() {
        List<Partie> parties = new ArrayList<Partie>();
        parties = (List<Partie>) partieCrudService.findByStatut(Statut.LIBRE);
        if (parties.size() < 5) {
            for (int i = 0; i < (Config.nombrePartieDispo - parties.size()); i++) {
                Partie p = new Partie(Statut.LIBRE);
                partieCrudService.save(p);
            }
        }
    }

    public void lancementPartie(Partie p, Utilisateur u1, Utilisateur u2) {
        p.setStatut(Statut.DEMARRE);
        partieCrudService.save(p);
        this.distributionCarte(u1, u2);
    }

    public void distributionCarte(Utilisateur u1, Utilisateur u2) {

        List<Carte> cartes = new ArrayList<Carte>();

        for (int i = 0; i < Config.nombreCarte / 5; i++) {
            Carte c = new Carte(Couleur.ROUGE, Etat.CACHE);
            cartes.add(c);
        }
        for (int i = 0; i < Config.nombreCarte / 5; i++) {
            Carte c = new Carte(Couleur.JAUNE, Etat.CACHE);
            cartes.add(c);
        }
        for (int i = 0; i < Config.nombreCarte / 5; i++) {
            Carte c = new Carte(Couleur.BLEU, Etat.CACHE);
            cartes.add(c);
        }
        for (int i = 0; i < Config.nombreCarte / 5; i++) {
            Carte c = new Carte(Couleur.VERT, Etat.CACHE);
            cartes.add(c);
        }
        for (int i = 0; i < Config.nombreCarte / 5; i++) {
            Carte c = new Carte(Couleur.VIOLET, Etat.CACHE);
            cartes.add(c);
        }

        ArrayList<Carte> jeuMelange = new ArrayList<Carte>();
        int n;
        while (cartes.size() != 0) {
            // Calcul d'un indice alÃ©atoire dans le jeu de cartes
            n = (int) (Math.random() * cartes.size());
            // DÃ©placement d'une carte du jeu vers la liste mÃ©langÃ©es
            jeuMelange.add(cartes.remove(n));
        }
        cartes = jeuMelange;
        for (int i = 0; i < cartes.size() / 2; i++) {

            Carte c = cartes.get(i);
            c.setUtilisateur(u1);
            u1.getCartes().add(c);
            carteCrudService.save(c);
            utilisateurCrudService.save(u1);
        }

        for (int i = cartes.size() / 2; i < cartes.size(); i++) {
            Carte c = cartes.get(i);
            c.setUtilisateur(u2);
            u2.getCartes().add(c);
            carteCrudService.save(c);
            utilisateurCrudService.save(u2);
        }

    }
    
    public void retourneCarte(){
        
        List<Partie> parties = (List<Partie>) partieCrudService.findByStatut(Statut.DEMARRE);
        for(Partie p : parties){
            List<Utilisateur> joueurs = p.getUtilisateurs();
            Utilisateur j1 = joueurs.get(0);
            Utilisateur j2 = joueurs.get(1);
            List<Carte> cartes1 = carteCrudService.findByUtilisateurIdAndEtat(j1.getId(), Etat.CACHE);
            List<Carte> cartes2 = carteCrudService.findByUtilisateurIdAndEtat(j2.getId(),Etat.CACHE);
            Carte c1 =  cartes1.get(0);
            Carte c2 =  cartes2.get(0);
            c1.setEtat(Etat.RETOURNE);
            c2.setEtat(Etat.RETOURNE);
            carteCrudService.save(c1);
            carteCrudService.save(c2);
            
        }
        
    }

}
