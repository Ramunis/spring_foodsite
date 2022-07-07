package com.foodsite.foodsite.controllers;

import com.foodsite.foodsite.models.*;
import com.foodsite.foodsite.repo.ClientRepository;
import com.foodsite.foodsite.repo.FoodRepository;
import com.foodsite.foodsite.repo.ThemeRepository;
import com.foodsite.foodsite.repo.UserRepository;
import org.apache.coyote.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class MainController {

    @Autowired
    private UserRepository userRepository;
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

    @GetMapping("/filterc/{id}")
    public String Filter(@PathVariable(value="id") String id, Model model) {
        Iterable<Client> clients = clientRepository.getFoodByRegion(id);
        model.addAttribute("clients", clients);
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

    @GetMapping("/recip/{id}/edit")
    public String RecipEdit(@PathVariable(value="id") long id, Model model) {
        Optional<Food> food = foodRepository.findById(id);
        ArrayList<Food> res = new ArrayList<>();
        food.ifPresent(res::add);
        model.addAttribute("food", res);
        return "Edit";
    }

    @PostMapping("/recip/{id}/edit")
    public String RecipUpdate(@PathVariable(value="id") long id,@RequestParam String Name,@RequestParam String About,@RequestParam String Len, @RequestParam String Ing,@RequestParam String Step,@RequestParam String Picture,@RequestParam String Youtube, Model model) {
        Food food = foodRepository.findById(id).orElseThrow();
        food.setTitle(Name);
        food.setText(About);
        food.setLen(Len);
        food.setIng(Ing);
        food.setStep(Step);
        food.setPic(Picture);
        food.setYoutube(Youtube);

        foodRepository.save(food);
        return "redirect:/recipes";
    }

    @PostMapping("/recip/{id}/remove")
    public String RecipDel(@PathVariable(value="id") long id, Model model) {
        Food food = foodRepository.findById(id).orElseThrow();
        foodRepository.delete(food);

        return "redirect:/recipes";
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

    @GetMapping("/search")
    public String Search(Model model,@RequestParam String s) {
        List<Food> listfoods = foodRepository.getFoodByTitle(s);
        model.addAttribute("foods", listfoods);

        Iterable<Theme> themes = themeRepository.findAll();
        model.addAttribute("themes", themes);
        return "Recipes";
    }


    @GetMapping("/account")
    public String Acccount(Model model,Authentication authentication) {
        if (authentication != null) {
            Authentication authentication1 = SecurityContextHolder.getContext().getAuthentication();
            String login = authentication1.getName();
            Integer uid = userRepository.getidByName(login);
            //return "redirect:/user/"+uid+"";
            Optional<Client> client = clientRepository.findById(Long.valueOf(uid));
            ArrayList<Client> res = new ArrayList<>();
            client.ifPresent(res::add);
            model.addAttribute("client", res);
            List<Food> fl = foodRepository.getFoodByChief(Long.valueOf(uid));
            model.addAttribute("fl", fl);

            return "Account";
        }
        else
            return "redirect:/reg";
    }
    //
    @GetMapping("/reg")
    public String Reg(Model model) {
        model.addAttribute("title", "Reg");
        return "Reg";
    }

    @PostMapping("/reg")
    public String RegAdd(User user,@RequestParam String username,@RequestParam String password,@RequestParam String firstName, @RequestParam String lastName,@RequestParam String country,@RequestParam String city,@RequestParam String email,@RequestParam String picture, Model model) {

        User userFromDb = userRepository.findByUsername(username);

        if (userFromDb != null) {
            return "Reg";
        }

        Date date=new Date();
        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate=dateFormat.format(date);
        String dr=formattedDate;

        user.setPassword(password);
        user.setUsername(username);
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        userRepository.save(user);

        Client client = new Client(username, "1", dr ,firstName, lastName, country, city,email, picture);
        clientRepository.save(client);
        return "redirect:/recipes";
    }

    @GetMapping("/add")
    public String Add(Model model,Authentication authentication) {
        Iterable<Theme> themes = themeRepository.findAll();
        model.addAttribute("themes", themes);

        if (authentication != null)
            return "Add";
        else
            return "redirect:/login";

    }

    @PostMapping("/add")
    public String AddAdd(@RequestParam String Name,@RequestParam int area,@RequestParam String About,@RequestParam String Len, @RequestParam String Ing,@RequestParam String Step,@RequestParam String Picture,@RequestParam String Youtube, Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        Integer uid = userRepository.getidByName(login);

        Date date=new Date();
        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate=dateFormat.format(date);
        String dr=formattedDate;
        Client client = clientRepository.findById(Long.valueOf(uid)).orElseThrow();
        Theme theme = themeRepository.findById(Long.valueOf(area)).orElseThrow();
        Food food = new Food(dr, Name, About, Len, Ing, Step, Picture, Youtube, client, theme);
        foodRepository.save(food);
        return "redirect:/recipes";
    }



}
