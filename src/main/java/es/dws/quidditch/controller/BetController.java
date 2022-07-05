package es.dws.quidditch.controller;

import es.dws.quidditch.model.Bet;
import es.dws.quidditch.model.User;
import es.dws.quidditch.service.BetService;
import es.dws.quidditch.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class BetController {
    @Autowired
    private UserService userService;
    @Autowired
    private BetService service;
    @PostMapping("/newBet")
    @ResponseStatus(HttpStatus.CREATED)
    public String create(Bet bet, String userID){
        User user = this.userService.get(userID);
        if(user != null){
            user = this.service.post(user,bet);
            this.userService.put(userID,user);
            return "confirm";
        }else{
            return "error";
        }
    }
    @GetMapping("/bet")
    public String show (Model model){
        //Bet bet = this.service.get(id);
        return "bet_page";
    }
    @PostMapping("/edit_bet")
    @ResponseStatus(HttpStatus.OK)
    public String edit(Model model, long betID, Bet newBet, HttpServletRequest request){
        if(request.isUserInRole("ADMIN")){
            this.service.put(betID,newBet);
            model.addAttribute("confirm","Cambio realizado");
            model.addAttribute("confirm_message","Se ha modificado la apuesta correctamente");
        }else{
            model.addAttribute("confirm","Error");
            model.addAttribute("confirm_message","No estás autorizado para esta acción");
        }
        return "confirm";
    }
    @PostMapping("/delete_bet")
    @ResponseStatus(HttpStatus.OK)
    public String delete(Model model,long betID){
        this.service.delete(betID);
        return "confirm";
    }
}
