package com.example.ordenadoresapiresttest.controllers;


import com.example.ordenadoresapiresttest.entities.Laptop;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.ordenadoresapiresttest.repositories.LaptopRepository;

import java.util.List;
import java.util.Optional;

@RestController
public class LaptopController {
    LaptopRepository laptopRepository;

    public LaptopController(LaptopRepository laptopRepository) {
        this.laptopRepository = laptopRepository;
    }
    @GetMapping("/hola")
    public String hola(){
        return "holatest";
    }

    @GetMapping("/laptop/findAll")
    public List<Laptop> findAll(){ return laptopRepository.findAll(); }

    @GetMapping("/laptop/findById/{id}")
    public ResponseEntity<Laptop> findById(@PathVariable Long id){
        Optional<Laptop> optLaptop = laptopRepository.findById(id);
        return optLaptop.isPresent() ? ResponseEntity.ok(optLaptop.get()) : ResponseEntity.notFound().build();
    }

    @PostMapping("/laptop/create")
    public Laptop createLaptop( @RequestBody Laptop laptop){

        return laptopRepository.save(laptop);
    }
}
