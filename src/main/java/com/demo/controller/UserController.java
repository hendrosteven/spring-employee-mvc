package com.demo.controller;

import javax.servlet.http.HttpSession;

import com.demo.dto.LoginDto;
import com.demo.helpers.MD5Generator;
import com.demo.model.entity.User;
import com.demo.model.repository.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping
public class UserController {

    @Autowired
    private HttpSession session;

    @Autowired UserRepo repo;
    
    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("loginDto", new LoginDto());
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(LoginDto login, RedirectAttributes redirectAttributes) {
        User user = repo.findByEmail(login.getEmail());
        if(user!=null){
            try{
                if(user.getPassword().equals(MD5Generator.generate(login.getPassword()))){
                    session.setAttribute("CURRENT_USER", user.getEmail());
                    return "redirect:/";
                }
            }catch(Exception ex){
                redirectAttributes.addFlashAttribute("ERROR", ex.getMessage());
                return "redirect:/login";
            }           
        }
        redirectAttributes.addFlashAttribute("ERROR", "Login failed");
        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String doLogout(){
        session.invalidate();
        return "redirect:/";
    }
}
