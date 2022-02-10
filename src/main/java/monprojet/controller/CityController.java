package monprojet.controller;

import java.net.http.WebSocket.Listener;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import monprojet.dao.CityRepository;
import monprojet.dao.CountryRepository;
import monprojet.entity.City;
import monprojet.entity.Country;

@Controller
@RequestMapping("/city")
public class CityController {

    @Autowired
    private CityRepository cityDAO;

    @Autowired
    private CountryRepository countryDAO;
    
    @GetMapping(path="show")
    public String afficherVueVille( Model model){
        List<City> lesVilles = cityDAO.findAll();
        model.addAttribute("lesVilles", lesVilles);

        List<Country> lesPays = countryDAO.findAll();
        model.addAttribute("lesPays", lesPays);
        return "city";
    }

    @PostMapping(path="save")
    public String ajouterVilleEtRendreVue(@RequestParam String name, @RequestParam int population, @RequestParam int pays){

        Country country = countryDAO.findById(pays).orElseThrow();

        City city = new City(name, country);
        
        city.setPopulation(population);
        cityDAO.save(city);
        
        return "redirect:/city/show";
    }

    @GetMapping(path="update")
    public String modifierVilleEtRendreVue(@RequestParam String id, @RequestParam String name, @RequestParam int population, @RequestParam int pays){
        Country country = countryDAO.findById(pays).orElseThrow();
        City city = cityDAO.findById(Integer.parseInt(id)).orElseThrow();
        city.setCountry(country);
        city.setName(name);
        city.setPopulation(population);
        cityDAO.save(city);
        return "redirect:/city/show";
    }

    @GetMapping(path="delete")
    public String supprimerVilleEtRendreVue(@RequestParam("id") int id){
        City villeSupprime = cityDAO.findById(id).orElseThrow();
        cityDAO.delete(villeSupprime);
        return "redirect:/city/show";
    }

    @GetMapping(path="edit")
    public String remplirFormAvecDataEtRendreVue(Model model, @RequestParam("id") int id){
        City villeModif = cityDAO.findById(id).orElseThrow();
        model.addAttribute("villeModif", villeModif );
        
        List<City> lesVilles = cityDAO.findAll();
        model.addAttribute("lesVilles", lesVilles);

        List<Country> lesPays = countryDAO.findAll();
        model.addAttribute("lesPays", lesPays);
        return "city"; //si on fait un redirect et qu'on ajoute que villeModif en attribute ça marche pas
    }


    

    

}
