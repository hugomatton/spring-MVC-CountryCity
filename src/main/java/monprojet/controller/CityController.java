package monprojet.controller;

import java.net.http.WebSocket.Listener;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
    
    @GetMapping(path="defaultView")
    public String afficherVueVille( Model model){
        List<City> lesVilles = cityDAO.findAll();
        model.addAttribute("lesVilles", lesVilles);

        List<Country> lesPays = countryDAO.findAll();
        model.addAttribute("lesPays", lesPays);
        return "city";
    }

    @PostMapping(path="defaultView")
    public String ajouterVilleEtRendreVue(@RequestParam String name, @RequestParam String population, @RequestParam int pays){

        Country country = countryDAO.findById(pays).orElseThrow();

        City city = new City(name, country);
        city.setPopulation(Integer.parseInt(population));
        cityDAO.save(city);
        
        return "redirect:defaultView";
    }

}
