package com.example.simplejparest;

import com.example.simplejparest.data.World;
import com.example.simplejparest.repository.WorldRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.hasToString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = SimpleJpaRestApplication.class
)
@AutoConfigureMockMvc
public class WorldRestRepositoryTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private WorldRepository worldRepository;

    @Test
    public void givenListOfWorldsThenReturnsJsonArray() throws Exception {
        mvc.perform(put("/worlds/{id}", 9L)
                        .content(
                                asJsonString(
                                        new World(9L, "Pluto")
                                )
                        )
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is2xxSuccessful());

        mvc.perform(get("/worlds").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.worlds", hasSize(9)));
    }

    @Test
    public void givenListOfWorldsThenReturnsMercuryInFirstPlace() throws Exception {
        mvc.perform(put("/worlds/{id}", 9L)
                        .content(
                                asJsonString(
                                        new World(9L, "Pluto")
                                )
                        )
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is2xxSuccessful());

        mvc.perform(get("/worlds").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.worlds", hasSize(9)))
                .andExpect(jsonPath("$._embedded.worlds[0].name", hasToString("Mercury")));
    }

    @Test
    public void givenListOfWorldsThenReturnsEarthInThirdPlace() throws Exception {
        mvc.perform(put("/worlds/{id}", 9L)
                        .content(
                                asJsonString(
                                        new World(9L, "Pluto")
                                )
                        )
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is2xxSuccessful());

        mvc.perform(get("/worlds").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.worlds", hasSize(9)))
                .andExpect(jsonPath("$._embedded.worlds[2].name", hasToString("Earth")));
    }

    @Test
    public void givenEarthById() throws Exception {
        mvc.perform(get("/worlds/{id}","3").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", hasToString("Earth")));
    }

    @Test
    public void createPluto() throws Exception {
        mvc.perform(post("/worlds")
                        .content(
                                asJsonString(
                                        new World(9L, "Pluto")
                                )
                        )
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated());
    }

    @Test
    public void updatePluto() throws Exception {
        mvc.perform(put("/worlds/{id}", 9L)
                        .content(
                                asJsonString(
                                        new World(9L, "Pluto")
                                )
                        )
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is2xxSuccessful());

        mvc.perform(put("/worlds/{id}", 9L)
                        .content(
                                asJsonString(
                                        new World(9L, "Pluto++")
                                )
                        )
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void deletePluto() throws Exception {
        mvc.perform(put("/worlds/{id}", 9L)
                        .content(
                                asJsonString(
                                        new World(9L, "Pluto")
                                )
                        )
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is2xxSuccessful());

        mvc.perform(delete("/worlds/{id}", 9L)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is2xxSuccessful());
    }

    private String asJsonString(World world) {
        try {
            return new ObjectMapper().writeValueAsString(world);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
