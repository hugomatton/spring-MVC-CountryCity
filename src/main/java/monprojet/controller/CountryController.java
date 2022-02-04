package monprojet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import monprojet.dao.CountryRepository;
import monprojet.entity.Country;

@Controller
@RequestMapping("/country")
public class CountryController {

    @Autowired
    CountryRepository countryDAO;
    
    @GetMapping("vueCountry")
    public String afficherVueCountry(Model model){

        List<Country> lesPays = countryDAO.findAll();

        model.addAttribute("lesPays", lesPays);

        return "country";
    }

    @PostMapping("vueCountry")
    public String ajouterPaysEtRendreVue(@RequestParam String code, @RequestParam String name){
        Country c = new Country(code, name);
        countryDAO.save(c);
        return "redirect:vueCountry";
    }
}
