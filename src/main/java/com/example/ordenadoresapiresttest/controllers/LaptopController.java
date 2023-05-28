package com.example.ordenadoresapiresttest.controllers;


import com.example.ordenadoresapiresttest.entities.Laptop;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.ordenadoresapiresttest.repositories.LaptopRepository;

import java.util.List;
import java.util.Optional;

@RestController
public class LaptopController {
    LaptopRepository laptopRepository;

    private final Logger log = LoggerFactory.getLogger(LaptopController.class);

    public LaptopController(LaptopRepository laptopRepository) {
        this.laptopRepository = laptopRepository;
    }
    @GetMapping("/laptop/hola")
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
    public Laptop createLaptop(@RequestBody Laptop laptop){
        return laptopRepository.save(laptop);
    }

    @PutMapping("/laptop/update")
    public ResponseEntity<Laptop> updateLaptop(@RequestBody Laptop laptop){
        if(laptop.getId() == null || laptop.getId() < 0){
            log.warn("trying update laptop without id");
            return ResponseEntity.badRequest().build();
        }else if(!laptopRepository.existsById(laptop.getId())){
            log.warn("doesn't exist the laptop");
            return ResponseEntity.notFound().build();
        }
        Laptop update = laptopRepository.save(laptop);
        return ResponseEntity.ok(update);
    }

    @DeleteMapping("/laptop/deleteById/{id}")
    public ResponseEntity<Laptop> deleteById(@PathVariable Long id){
        if(id == null || id < 0){
            return ResponseEntity.badRequest().build();
        } else if(!laptopRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        laptopRepository.deleteById(id);
        return  ResponseEntity.ok().build();
    }

    @DeleteMapping("/laptop/deleteAllLaptops")
    public ResponseEntity<Laptop> deleteAllLaptops(){
        if(laptopRepository.count() < 0){
            log.warn("doesn´t exists laptops");
            return ResponseEntity.notFound().build();
        }
        laptopRepository.deleteAll();
        return  ResponseEntity.ok().build();
    }
}
