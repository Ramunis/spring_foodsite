package com.foodsite.foodsite.controllers;

import com.foodsite.foodsite.models.Client;
import com.foodsite.foodsite.models.Food;
import com.foodsite.foodsite.models.Theme;
import com.foodsite.foodsite.repo.ClientRepository;
import com.foodsite.foodsite.repo.FoodRepository;
import com.foodsite.foodsite.repo.ThemeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class MainController {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private ThemeRepository themeRepository;

    @GetMapping("/")
    public String Index(Model model) {
        List<Food> listlast = foodRepository.getBySortOld();
        model.addAttribute("foods", listlast);
        List<Food> listold = foodRepository.getBySortLast();
        model.addAttribute("fn", listold);
        List<Food> tf = foodRepository.getByTodayRec();
        model.addAttribute("tf", tf);
        return "Index";
    }

    @GetMapping("/recipes")
    public String Recipes(Model model) {
        Iterable<Food> foods = foodRepository.findAll();
        model.addAttribute("foods", foods);
        Iterable<Theme> themes = themeRepository.findAll();
        model.addAttribute("themes", themes);
        return "Recipes";
    }

    @GetMapping("/chiefs")
    public String Chiefs(Model model) {
        Iterable<Client> clients = clientRepository.findAll();
        model.addAttribute("clients", clients);
        List<Client> tc = clientRepository.getByTodayChief();
        model.addAttribute("tc", tc);
        return "Chiefs";
    }

    @GetMapping("/coc")
    public String Coc(Model model) {
        Integer number=1;
        List<Food> listfoods = foodRepository.getFoodByTheme(number);
        model.addAttribute("foods", listfoods);
        return "Coc";
    }

    @GetMapping("/wine")
    public String Wine(Model model) {
        Integer number=2;
        List<Food> listfoods = foodRepository.getFoodByTheme(number);
        model.addAttribute("foods", listfoods);
        return "Wine";
    }


    //
    @GetMapping("/recip/{id}")
    public String Recip(@PathVariable(value="id") long id, Model model) {
        Optional<Food> food = foodRepository.findById(id);
        ArrayList<Food> res = new ArrayList<>();
        food.ifPresent(res::add);
        model.addAttribute("food", res);
        return "Recip";
    }

    @GetMapping("/user/{id}")
    public String User(@PathVariable(value="id") long id, Model model) {
        Optional<Client> client = clientRepository.findById(id);
        ArrayList<Client> res = new ArrayList<>();
        client.ifPresent(res::add);
        model.addAttribute("client", res);
        List<Food> fl = foodRepository.getFoodByChief(id);
        model.addAttribute("fl", fl);

        return "User";
    }

    @GetMapping("/filter/{id}")
    public String Filter(@PathVariable(value="id") long id, Model model) {
        List<Food> listfoods = foodRepository.getFoodByTheme((int) id);
        model.addAttribute("foods", listfoods);
        Iterable<Theme> themes = themeRepository.findAll();
        model.addAttribute("themes", themes);
        return "Recipes";
    }

    @PostMapping("/search")
    public String Search(@RequestParam String search, Model model) {
        List<Food> listfoods = foodRepository.getFoodByName(search);
        model.addAttribute("foods", listfoods);
        Iterable<Theme> themes = themeRepository.findAll();
        model.addAttribute("themes", themes);
        return "redirect:/recipes";
    }

    //
    @GetMapping("/mybook")
    public String Mybook(Model model) {
        model.addAttribute("title", "Mybook");
        return "Mybook";
    }

    @GetMapping("/myrec")
    public String Myrec(Model model) {
        model.addAttribute("title", "Myrec");
        return "Myrec";
    }
    //
    @GetMapping("/reg")
    public String Reg(Model model) {
        model.addAttribute("title", "Reg");
        return "Reg";
    }

    @PostMapping("/reg")
    public String RegAdd(@RequestParam String username,@RequestParam String password,@RequestParam String firstName, @RequestParam String lastName,@RequestParam String country,@RequestParam String city,@RequestParam String email,@RequestParam String picture, Model model) {

        Date date=new Date();
        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate=dateFormat.format(date);
        String dr=formattedDate;

        Client client = new Client(username, password, dr ,firstName, lastName, country, city,email, picture);
        clientRepository.save(client);
        return "redirect:/recipes";
    }

    @GetMapping("/add")
    public String Add(Model model) {
        Iterable<Theme> themes = themeRepository.findAll();
        model.addAttribute("themes", themes);
        return "Add";
    }

    @PostMapping("/add")
    public String AddAdd(@RequestParam String Name,@RequestParam int area,@RequestParam String About,@RequestParam String Len, @RequestParam String Ing,@RequestParam String Step,@RequestParam String Picture,@RequestParam String Youtube, Model model) {

        Date date=new Date();
        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate=dateFormat.format(date);
        String dr=formattedDate;
        Client client = clientRepository.findById(Long.valueOf(1)).orElseThrow();
        Theme theme = themeRepository.findById(Long.valueOf(area)).orElseThrow();
        Food food = new Food(dr, Name, About, Len, Ing, Step, Picture, Youtube, client, theme);
        foodRepository.save(food);
        return "redirect:/recipes";
    }



}
