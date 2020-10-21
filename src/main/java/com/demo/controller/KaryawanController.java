package com.demo.controller;

import javax.validation.Valid;

import com.demo.dto.ErrorMessageDto;
import com.demo.dto.KaryawanDto;
import com.demo.model.entity.Karyawan;
import com.demo.model.repository.DepartemenRepo;
import com.demo.model.repository.KaryawanRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/karyawan")
public class KaryawanController {

    @Autowired
    private KaryawanRepo repo;

    @Autowired
    private DepartemenRepo depRepo;
    
    @GetMapping("/input")
    public String input(Model model){
        model.addAttribute("karyawanForm", new KaryawanDto());
        model.addAttribute("listOfDepartemen", depRepo.findAll());
        return "input";
    }

    @PostMapping("/save")
    public String save(@Valid KaryawanDto karyawanForm, BindingResult resultErrors, Model model){
        
        if(!resultErrors.hasErrors()){
            Karyawan karyawan = new Karyawan();
            karyawan.setNip(karyawanForm.getNip());
            karyawan.setFirstName(karyawanForm.getFirstName());
            karyawan.setLastName(karyawanForm.getLastName());
            karyawan.setAddress(karyawanForm.getAddress());
            karyawan.setEmailAddress(karyawanForm.getEmailAddress());
            karyawan.setPhoneNumber(karyawanForm.getPhoneNumber());
            karyawan.setDepartemen(depRepo.findById(karyawanForm.getDepartemenId()).get());
            repo.save(karyawan); // save to DB
            return "redirect:/";
        }else{
            ErrorMessageDto messages = new ErrorMessageDto();
            for(ObjectError err: resultErrors.getAllErrors()){
                messages.getMessages().add(err.getDefaultMessage());
            }
            model.addAttribute("karyawanForm", karyawanForm);
            model.addAttribute("ERROR", messages);
            return "input";
        }
    }

    @GetMapping("/remove/{id}")
    public String remove(@PathVariable("id") int id){
        repo.deleteById(id);
        return "redirect:/";
    }


    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") int id, Model model){
        Karyawan karyawan = repo.findById(id).get();
        KaryawanDto dto = new KaryawanDto();
        dto.setId(karyawan.getId());
        dto.setNip(karyawan.getNip());
        dto.setFirstName(karyawan.getFirstName());
        dto.setLastName(karyawan.getLastName());
        dto.setAddress(karyawan.getAddress());
        dto.setEmailAddress(karyawan.getEmailAddress());
        dto.setPhoneNumber(karyawan.getPhoneNumber());
        model.addAttribute("karyawanForm", dto);
        return "edit";
    }

    @PostMapping("/update")
    public String update(@Valid KaryawanDto karyawanForm, BindingResult resultErrors, RedirectAttributes redirectAttributes){
        if(!resultErrors.hasErrors()){
            Karyawan karyawan = repo.findById(karyawanForm.getId()).get();
            karyawan.setNip(karyawanForm.getNip());
            karyawan.setFirstName(karyawanForm.getFirstName());
            karyawan.setLastName(karyawanForm.getLastName());
            karyawan.setAddress(karyawanForm.getAddress());
            karyawan.setEmailAddress(karyawanForm.getEmailAddress());
            karyawan.setPhoneNumber(karyawanForm.getPhoneNumber());
            repo.save(karyawan);
            return "redirect:/";
        }else{
            ErrorMessageDto messages = new ErrorMessageDto();
            for(ObjectError err: resultErrors.getAllErrors()){
                messages.getMessages().add(err.getDefaultMessage());
            }
            redirectAttributes.addFlashAttribute("ERROR", messages);
            return "redirect:/karyawan/edit/"+karyawanForm.getId();
        }
    }

}
