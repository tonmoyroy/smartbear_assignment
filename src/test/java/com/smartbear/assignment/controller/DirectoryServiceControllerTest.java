package com.smartbear.assignment.controller;

import com.smartbear.assignment.model.Entry;
import com.smartbear.assignment.repository.DirectoryServiceRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class DirectoryServiceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DirectoryServiceRepository directoryServiceRepository;

    @Test
    public void ping() throws Exception{
        mockMvc.perform(get("/api/ping")).andExpect(status().isOk()).andExpect(content().string("Application is working!"));
    }

    @Test
    public void createEntry() throws Exception{
        Entry output = new Entry();
        output.setName("tonmoy");
        output.setPhone("01722221212");
        output.setEmail("tonmoy.iitg@gmail.com");

        String input = "{\"name\":\"tonmoy\",\"phone\":\"01722221212\",\"email\":\"tonmoy.iitg@gmail.com\"}";

        Mockito.when(directoryServiceRepository.save(Mockito.any(Entry.class))).thenReturn(output);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/create")
                .accept(MediaType.APPLICATION_JSON).content(input)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    @Test
    public void readEntry() throws Exception{
        Mockito.when(directoryServiceRepository.findAll()).thenReturn(Collections.emptyList());

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/read")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    public void updateEntry() throws Exception{
        String input = "{\"name\":\"tonmoy\"}";
        Entry output = new Entry();
        output.setName("tonmoy");
        output.setPhone("01722221212");
        output.setEmail("tonmoy.iitg@gmail.com");

        Mockito.when(directoryServiceRepository.findByEmail("tonmoy.iitg@gmail.com")).thenReturn(output);

        Mockito.when(directoryServiceRepository.updateEntryByEmail(output.getName(), output.getPhone(), "tonmoy.iitg@gmail.com")).thenReturn(1);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/api/update/tonmoy.iitg@gmail.com")
                .accept(MediaType.APPLICATION_JSON).content(input)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    public void deleteEntry() throws Exception{
        Entry output = new Entry();
        output.setName("tonmoy");
        output.setPhone("01722221212");
        output.setEmail("tonmoy.iitg@gmail.com");

        Mockito.when(directoryServiceRepository.findByEmail("tonmoy.iitg@gmail.com")).thenReturn(output);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/api/delete/tonmoy.iitg@gmail.com")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }
}