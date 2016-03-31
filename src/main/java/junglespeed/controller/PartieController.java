/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package junglespeed.controller;

import java.util.List;
import javax.servlet.http.HttpSession;
import junglespeed.entity.Carte;
import junglespeed.entity.Partie;
import junglespeed.entity.Utilisateur;
import junglespeed.enumeration.Etat;
import junglespeed.enumeration.Statut;
import junglespeed.service.ActualisationService;
import junglespeed.service.CarteCrudService;
import junglespeed.service.PartieCrudService;
import junglespeed.service.PartieService;
import junglespeed.service.UtilisateurCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author tom
 */
@Controller
@RequestMapping("/partie")
public class PartieController {

    @Autowired
    PartieCrudService partieCrudService;

    @Autowired
    PartieService partieService;

    @Autowired
    UtilisateurCrudService utilisateurCrudService;

    @Autowired
    ActualisationService actualisationService;

    @Autowired
    CarteCrudService carteCrudService;

    @RequestMapping(value = "lister", method = RequestMethod.GET)
    public String lister(Model model) {

        model.addAttribute("parties", partieCrudService.findByStatut(Statut.LIBRE));

        return "/partie/lister";
    }

    @RequestMapping(value = "creer", method = RequestMethod.GET)
    public String creer(Model model) {

        partieService.creer();

        return "redirect:/partie/lister";
    }

    @RequestMapping(value = "rejoindre/{id}", method = RequestMethod.GET)
    public String rejoindre(@PathVariable(value = "id") long monId, Model model) {
        Partie partie = partieCrudService.findOne(monId);
        Utilisateur j = new Utilisateur();
        j.setPartie(partie);
        model.addAttribute("joueur", j);
        return "/partie/rejoindre";
    }

    @RequestMapping(value = "rejoindre", method = RequestMethod.POST)
    public String rejoindrePost(@ModelAttribute(value = "joueur") Utilisateur joueur, Model model, HttpSession session) {
        utilisateurCrudService.save(joueur);
        Partie partie = partieCrudService.findOne(joueur.getPartie().getId());
        partie.getUtilisateurs().add(joueur);
        partieCrudService.save(partie);

        session.setAttribute("util", joueur);
        session.setAttribute("idPartie", partie.getId());
        actualisationService.misAJourPartieLibre();
//        Partie partie = partieCrudService.findOne(monId);
        List<Utilisateur> joueurs = partie.getUtilisateurs();
//        model.addAttribute("joueur", joueur);
        model.addAttribute("partie", partie);
//        model.addAttribute("joueurs", joueurs);

        return "redirect:/plateau";

    }

    @RequestMapping(value = "/plateau", method = RequestMethod.GET)
    public String plateau(HttpSession session, Model model) {

        Long id = (Long) session.getAttribute("idPartie");
        Utilisateur joueur = (Utilisateur) session.getAttribute("util");
        Partie partie = partieCrudService.findOne(id);
        List<Utilisateur> joueurs = partie.getUtilisateurs();
        Utilisateur j1 = joueurs.get(0);
        Utilisateur j2 = joueurs.get(1);

        List<Carte> cartesRetournee1 = carteCrudService.findByUtilisateurIdAndEtat(j1.getId(), Etat.RETOURNE);
        List<Carte> cartesRetournee2 = carteCrudService.findByUtilisateurIdAndEtat(j1.getId(), Etat.RETOURNE);
        List<Carte> cartesCachee1 = carteCrudService.findByUtilisateurIdAndEtat(j1.getId(), Etat.CACHE);
        List<Carte> cartesCachee2 = carteCrudService.findByUtilisateurIdAndEtat(j1.getId(), Etat.CACHE);
        int n1 = cartesRetournee1.size();
        int n2 = cartesRetournee2.size();
        if (cartesRetournee1.size() != 0) {
            Carte carteRetournee1 = cartesRetournee1.get(n1 - 1);
            Carte carteRetournee2 = cartesRetournee2.get(n2 - 1);
            model.addAttribute("carteRetournee1", carteRetournee1);
            model.addAttribute("carteRetournee2", carteRetournee2);
        }

        Carte carteCachee1 = cartesCachee1.get(0);
        Carte carteCachee2 = cartesCachee2.get(0);

        model.addAttribute("joueur1", j1);
        model.addAttribute("joueur2", j2);

        model.addAttribute("score1", carteCrudService.countByUtilisateurId(j1.getId()));
        model.addAttribute("score2", carteCrudService.countByUtilisateurId(j2.getId()));

        model.addAttribute("carteCachee1", carteCachee1);
        model.addAttribute("carteCachee2", carteCachee2);

        return "partie/plateau";
    }
}
