package com.project.mywatchlist.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(
            @RequestParam(required = false) String error,
            @RequestParam(required = false) String logout,
            Model model) {

        if (error != null) {
            model.addAttribute("mensagem", "Usuário ou senha inválidos!");
            model.addAttribute("sucesso", false);
        }

        if (logout != null) {
            model.addAttribute("mensagem", "Você foi desconectado com sucesso!");
            model.addAttribute("sucesso", true);
        }

        return "login";
    }
}
