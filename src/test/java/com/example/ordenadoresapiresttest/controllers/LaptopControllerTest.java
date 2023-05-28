package com.example.ordenadoresapiresttest.controllers;

import com.example.ordenadoresapiresttest.entities.Laptop;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LaptopControllerTest {

    private final static String BASE_URL = "/laptop/";
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void hola() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "hola"))
                .andReturn();

        assertEquals(200, mvcResult.getResponse().getStatus());
        assertEquals("holatest", mvcResult.getResponse().getContentAsString());
    }

    @Test
    void findAll() throws Exception {
        MvcResult mockMvcResult = mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL +"findAll")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        assertEquals(200, mockMvcResult.getResponse().getStatus());
    }

    @Test
    void findById() throws Exception {
        createLaptop();
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "findById/1")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @DisplayName("Comprobar guardar un laptop y su respuesta desde el controlador")
    @Test
    void createLaptop() throws Exception {
        Laptop laptop = buildLaptop();

        MvcResult mockMvcResult = mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL + "create")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapToJson(laptop)))
                .andReturn();

        assertEquals(1L, laptop.getId());
        assertEquals("Apple", laptop.getName());
        assertEquals("1TB", laptop.getStorage());
        assertEquals("2022-06-04", laptop.getRelease());

        assertEquals(200, mockMvcResult.getResponse().getStatus());
    }

    @Test
    void updateLaptop() throws Exception {

        createLaptop();
        Laptop laptop = buildUpdateLaptop();

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put(BASE_URL + "update")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapToJson(laptop)))
                .andReturn();

        assertEquals(200, mvcResult.getResponse().getStatus());

    }

    @Test
    void deleteById() throws Exception {
        createLaptop();

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "deleteById/1")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    void deleteAllLaptops() throws Exception {
        createLaptop();

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "deleteAllLaptops")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        assertEquals(200, mvcResult.getResponse().getStatus());
    }

    private String mapToJson(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }

    private Laptop buildLaptop() {
        Laptop laptop = new Laptop();
        laptop.setId(1L);
        laptop.setName("Apple");
        laptop.setStorage("1TB");
        laptop.setRelease("2022-06-04");
        return laptop;
    }

    private Laptop buildUpdateLaptop() {
        Laptop laptop = new Laptop();
        laptop.setId(1L);
        laptop.setName("HP");
        laptop.setStorage("2TB");
        laptop.setRelease("2021-06-04");
        return laptop;
    }
}