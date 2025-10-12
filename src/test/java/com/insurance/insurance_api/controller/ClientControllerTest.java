package com.insurance.insurance_api.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.insurance.insurance_api.dto.request.ClientPatchRequest;
import com.insurance.insurance_api.dto.request.CompanyRequest;
import com.insurance.insurance_api.dto.request.PersonRequest;
import com.insurance.insurance_api.entity.Client;
import com.insurance.insurance_api.entity.Company;
import com.insurance.insurance_api.entity.Person;
import com.insurance.insurance_api.service.ClientService;
import com.insurance.insurance_api.utils.MockValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class ClientControllerTest
 {
     private ClientService clientService;
     private ClientController clientController;
     private MockMvc mockMvc;
     private ObjectMapper objectMapper;

     @BeforeEach
     void setUp() {
         clientService = mock(ClientService.class);
         clientController = new ClientController(clientService);
         mockMvc = MockMvcBuilders.standaloneSetup(clientController).build();

         objectMapper = new ObjectMapper();
         objectMapper.registerModule(new JavaTimeModule());
     }

     // ---------------- GET /api/client/{id} ----------------
     @Test
     void getClientByIdShouldReturnClient() throws Exception {
         Client client = MockValue.mockClient1();
         when(clientService.getClientById(1L)).thenReturn(client);

         mockMvc.perform(get("/api/client/1"))
                 .andExpect(status().isOk())
                 .andExpect(jsonPath("$.id").value(client.getId()))
                 .andExpect(jsonPath("$.name").value(client.getName()))
                 .andExpect(jsonPath("$.email").value(client.getEmail()))
                 .andExpect(jsonPath("$.phone").value(client.getPhone()));

         verify(clientService).getClientById(1L);
     }

     // ---------------- POST /api/client/person ----------------
     @Test
     void createPersonShouldReturnCreatedPerson() throws Exception {
         Person person = MockValue.mockPerson1();

         PersonRequest request = new PersonRequest();
         request.setName(person.getName());
         request.setEmail(person.getEmail());
         request.setPhone(person.getPhone());
         request.setBirthDate(person.getBirthdate());

         when(clientService.createPerson(
                 person.getName(),
                 person.getPhone(),
                 person.getEmail(),
                 person.getBirthdate()
         )).thenReturn(person);

         mockMvc.perform(post("/api/client/person")
                         .contentType(MediaType.APPLICATION_JSON)
                         .content(objectMapper.writeValueAsString(request)))
                 .andExpect(status().isCreated())
                 .andExpect(jsonPath("$.id").value(person.getId()))
                 .andExpect(jsonPath("$.name").value(person.getName()))
                 .andExpect(jsonPath("$.email").value(person.getEmail()))
                 .andExpect(jsonPath("$.phone").value(person.getPhone()));

         verify(clientService).createPerson(
                 person.getName(),
                 person.getPhone(),
                 person.getEmail(),
                 person.getBirthdate()
         );
     }

     // ---------------- POST /api/client/company ----------------
     @Test
     void createCompanyShouldReturnCreatedCompany() throws Exception {
         Company company = MockValue.mockCompany1();

         CompanyRequest request = new CompanyRequest();
         request.setName(company.getName());
         request.setEmail(company.getEmail());
         request.setPhone(company.getPhone());

         when(clientService.createCompany(
                 company.getName(),
                 company.getPhone(),
                 company.getEmail()
         )).thenReturn(company);

         mockMvc.perform(post("/api/client/company")
                         .contentType(MediaType.APPLICATION_JSON)
                         .content(objectMapper.writeValueAsString(request)))
                 .andExpect(status().isCreated())
                 .andExpect(jsonPath("$.id").value(company.getId()))
                 .andExpect(jsonPath("$.name").value(company.getName()))
                 .andExpect(jsonPath("$.email").value(company.getEmail()))
                 .andExpect(jsonPath("$.phone").value(company.getPhone()));

         verify(clientService).createCompany(
                 company.getName(),
                 company.getPhone(),
                 company.getEmail()
         );
     }

     // ---------------- PATCH /api/client ----------------
     @Test
     void patchClientShouldReturnNoContent() throws Exception {
         ClientPatchRequest request = new ClientPatchRequest();
         request.setId(1L);
         request.setName("Updated Name");

         mockMvc.perform(patch("/api/client")
                         .contentType(MediaType.APPLICATION_JSON)
                         .content(objectMapper.writeValueAsString(request)))
                 .andExpect(status().isNoContent());

         verify(clientService).patchClientById(request);
     }

     // ---------------- DELETE /api/client/{id} ----------------
     @Test
     void deleteClientShouldReturnNoContent() throws Exception {
         mockMvc.perform(delete("/api/client/1"))
                 .andExpect(status().isNoContent());

         verify(clientService).deleteClientById(1L);
     }
}
