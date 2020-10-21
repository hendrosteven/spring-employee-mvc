package com.demo.controller;

import java.util.List;

import com.demo.model.entity.Karyawan;
import com.demo.model.repository.KaryawanRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private KaryawanRepo repo;

    @GetMapping
    public String index(Model model){
        Iterable<Karyawan> listKaryawan = repo.findAll();
        model.addAttribute("listOfKaryawan", listKaryawan);
        return "index";
    }
}
