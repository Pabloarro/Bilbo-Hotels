package es.deusto.bilboHotels.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import es.deusto.bilboHotels.model.Direccion;
import es.deusto.bilboHotels.model.Hotel;
import es.deusto.bilboHotels.model.HotelManager;
import es.deusto.bilboHotels.service.ServicioHotel;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class) 
@WebMvcTest(AdminController.class)
public class AdminControllerTestIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServicioHotel servicioHotel;

    @Test
    public void testGetAllHotels() throws Exception {
        mockMvc.perform(get("/api/hotels"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.hotels").isArray())
                .andExpect(jsonPath("$.hotels[0].id").value(1))
                .andExpect(jsonPath("$.hotels[0].name").value("Hotel Bilbo"))
                .andExpect(jsonPath("$.hotels[0].city").value("Bilbao"));
    }

    @Test
    public void testGetHotelById() throws Exception {
        mockMvc.perform(get("/api/hotels/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Hotel Bilbo"))
                .andExpect(jsonPath("$.city").value("Bilbao"));
    }

    /*@Test
    public void testCreateHotel() throws Exception {
        Direccion direccion = new Direccion("Calle Falsa 123", "Bilbao", "España");
        HotelManager hotelManager = new HotelManager();
        Hotel newHotel = Hotel.builder()
                .id(null)
                .nombre("Hotel Euskalduna")
                .direccion(direccion)
                .hotelManager(hotelManager)
                .build();

        given(servicioHotel.createHotel(newHotel)).willReturn(newHotel);

        String json = new ObjectMapper().writeValueAsString(newHotel);

        mockMvc.perform(post("/api/hotels")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.nombre").value("Hotel Euskalduna"))
                .andExpect(jsonPath("$.direccion.ciudad").value("Bilbao"));
    }

    @Test
    public void testUpdateHotel() throws Exception {
        Direccion direccion = new Direccion("Calle Verdadera 456", "Bilbao", "España");
        HotelManager hotelManager = new HotelManager();
        Hotel updatedHotel = Hotel.builder()
                .id(1L)
                .nombre("Hotel Bilbo Updated")
                .direccion(direccion)
                .hotelManager(hotelManager)
                .build();

        given(servicioHotel.updateHotel(1L, updatedHotel)).willReturn(updatedHotel);

        String json = new ObjectMapper().writeValueAsString(updatedHotel);

        mockMvc.perform(put("/api/hotels/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Hotel Bilbo Updated"))
                .andExpect(jsonPath("$.direccion.ciudad").value("Bilbao"));
    }*/

    @Test
    public void testDeleteHotel() throws Exception {
        mockMvc.perform(delete("/api/hotels/1"))
                .andExpect(status().isNoContent());
    }
}