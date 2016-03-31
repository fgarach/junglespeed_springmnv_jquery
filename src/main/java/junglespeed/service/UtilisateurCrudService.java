/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package junglespeed.service;

import junglespeed.entity.Utilisateur;
import org.springframework.data.repository.CrudRepository;


/**
 *
 * @author tom
 */
public interface UtilisateurCrudService extends CrudRepository<Utilisateur, Long>{
    
    
    
}
