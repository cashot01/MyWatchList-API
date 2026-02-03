package com.project.mywatchlist.controller;

import com.project.mywatchlist.model.Filme;
import com.project.mywatchlist.service.FilmeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/filmes")
public class FilmeController {


    private final FilmeService service;


    public FilmeController(FilmeService service) {
        this.service = service;
    }


    // READ - listar todos
    @GetMapping
    public String listar(
            @RequestParam(required = false) String filtro,
            Model model
    ) {

        if ("assistidos".equals(filtro)) {
            model.addAttribute("filmes", service.listarAssistidos());
        } else if ("watchlist".equals(filtro)) {
            model.addAttribute("filmes", service.listarWatchlist());
        } else {
            model.addAttribute("filmes", service.listarTodos());
        }

        model.addAttribute("total", service.totalFilmes());
        model.addAttribute("assistidos", service.totalAssistidos());
        model.addAttribute("watchlist", service.totalWatchlist());

        return "filmes/lista";
    }


    // CREATE - formulário
    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("filme", new Filme());
        return "filmes/form";
    }


    // CREATE - salvar
    @PostMapping
    public String salvar(@ModelAttribute Filme filme) {
        service.salvar(filme);
        return "redirect:/filmes";
    }


    // UPDATE - formulário edição
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Filme filme = service.buscarPorId(id);
        model.addAttribute("filme", filme);
        return "filmes/form";
    }


    // DELETE
    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id) {
        service.deletar(id);
        return "redirect:/filmes";
    }
}