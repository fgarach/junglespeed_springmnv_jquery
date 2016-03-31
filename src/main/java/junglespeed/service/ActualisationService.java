/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package junglespeed.service;

import java.util.List;
import junglespeed.entity.Partie;
import junglespeed.entity.Utilisateur;
import junglespeed.enumeration.Statut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 *
 * @author admin
 */
@Service
public class ActualisationService {
    
    @Autowired
    PartieCrudService partieCrudService;
    
    @Autowired
    PartieService partieService;
    
//    @Scheduled(fixedDelay = 1000)
//    public void actualiser() {
////        misAJourPartieLibre();
//    }
    
    private void misAJourScore(Partie partie) {
        
    }
    
    public void misAJourPartieLibre() {
        List<Partie> parties = (List<Partie>) partieCrudService.findByStatut(Statut.LIBRE);
        for (Partie p : parties){
            if (p.getUtilisateurs().size()==2){
                List<Utilisateur> joueurs = p.getUtilisateurs();
                
                partieService.lancementPartie(p, joueurs.get(0), joueurs.get(1));
            }
        }
    }
    
    @Scheduled(fixedDelay = 3000)
    public void misAJourCarte() {
        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxx");
        partieService.retourneCarte();
    }
    
}
