/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.controllers;

import application.pojos.Transfer;
import java.util.Collection;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import application.Launcher;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 *
 * @author Ivan
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Launcher.class)
@WebAppConfiguration
public class RestControllerTest 
{
    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream().filter(
                hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().get();

        Assert.assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }
    
    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
    
    public RestControllerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of test method, of class RestController.
     */
    @Test
    public void testTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/online")).andExpect(MockMvcResultMatchers.content().string("Online"));
    }

    /**
     * Test of newAccount method, of class RestController.
     */
    @Test
    public void testNewAccount() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.put("/signup").content("Ivan")).andExpect(MockMvcResultMatchers.status().isCreated());
        mockMvc.perform(MockMvcRequestBuilders.put("/signup").content("Ivan")).andExpect(MockMvcResultMatchers.status().isConflict());
    }

    /**
     * Test of newTransfer method, of class RestController.
     */
    @Test
    public void testNewTransfer() throws Exception{
        Transfer transfer = new Transfer();
        transfer.setAmount(10);
        transfer.setSender("Vasja");
        transfer.setReciever("Kolja");
        mockMvc.perform(MockMvcRequestBuilders.post("/transfer").content(this.json(transfer)).contentType(contentType)).andExpect(MockMvcResultMatchers.status().isAccepted());
    }

    /**
     * Test of getLog method, of class RestController.
     */
    @Test
    public void testGetLog() throws Exception{
        testNewTransfer();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/account").content("Vasja")).andExpect(MockMvcResultMatchers.content().contentType(contentType)).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }
    
}
