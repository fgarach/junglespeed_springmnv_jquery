/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package junglespeed.service;


import java.util.List;
import junglespeed.entity.Carte;
import junglespeed.entity.Partie;
import junglespeed.enumeration.Etat;
import junglespeed.enumeration.Statut;
import org.springframework.data.repository.CrudRepository;


/**
 *
 * @author tom
 */
public interface CarteCrudService extends CrudRepository<Carte, Long>{
    
    public int countByUtilisateurId(Long id);
    public List<Carte> findByUtilisateurId (Long id);
    public List<Carte> findByUtilisateurIdAndEtat (Long id, Etat etat);
    
}
