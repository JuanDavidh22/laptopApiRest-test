package com.example.ordenadoresapiresttest;

import com.example.ordenadoresapiresttest.entities.Laptop;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import com.example.ordenadoresapiresttest.repositories.LaptopRepository;

@SpringBootApplication
public class OrdenadoresApiresttestApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(OrdenadoresApiresttestApplication.class, args);
        LaptopRepository laptopRepository = context.getBean(LaptopRepository.class);

        Laptop laptop = new Laptop(null, "Lenovo", "900GB", "2020-06-06");
        laptopRepository.save(laptop);
    }

}
