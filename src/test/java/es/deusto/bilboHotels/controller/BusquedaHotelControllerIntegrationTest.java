package es.deusto.bilboHotels.controller;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import es.deusto.bilboHotels.model.dto.HotelBusquedaDTO;
import es.deusto.bilboHotels.model.dto.HotelDisponibilidadDTO;
import es.deusto.bilboHotels.service.ServicioBusquedaHotel;

@SpringBootTest
@AutoConfigureMockMvc
public class BusquedaHotelControllerIntegrationTest implements IntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServicioBusquedaHotel servicioBusquedaHotel;

    private HotelBusquedaDTO hotelBusquedaDTO;

    @BeforeEach
    public void setup() {
        hotelBusquedaDTO = HotelBusquedaDTO.builder()
                .ciudad("Bilbao")
                .fechaCheckIn(LocalDate.of(2024, 7, 1))
                .fechaCheckOut(LocalDate.of(2024, 7, 10))
                .build();
    }

    @Test
    @WithMockUser(username = "user")
    public void testShowSearchForm() throws Exception {
        mockMvc.perform(get("/search"))
                .andExpect(status().isOk())
                .andExpect(view().name("hotelsearch/search"))
                .andExpect(model().attributeExists("hotelBusquedaDTO"));
    }

    @Test
    @WithMockUser(username = "user")
    public void testFindAvailableHotelsByCityAndDate() throws Exception {
        mockMvc.perform(post("/search")
                        .flashAttr("hotelBusquedaDTO", hotelBusquedaDTO))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/search-results?ciudad=Bilbao&fechaCheckIn=2024-07-01&fechaCheckOut=2024-07-10"));
    }

    @Test
@WithMockUser(username = "user")
public void testShowSearchResults() throws Exception {
    HotelDisponibilidadDTO hotelDisponibilidadDTO = HotelDisponibilidadDTO.builder()
            .id(1L)
            .nombre("Hotel Bilbao")
            .build();

    Mockito.when(servicioBusquedaHotel.buscarHotelesDisponiblesByCiudadAndFecha("Bilbao", LocalDate.of(2024, 7, 1), LocalDate.of(2024, 7, 10)))
            .thenReturn(Collections.singletonList(hotelDisponibilidadDTO));

    mockMvc.perform(get("/search-results")
                    .param("ciudad", "Bilbao")
                    .param("fechaCheckIn", "2024-07-01")
                    .param("fechaCheckOut", "2024-07-10"))
            .andExpect(status().isOk())
            .andExpect(view().name("hotelsearch/search-results"))
            .andExpect(model().attributeExists("hotels"))
            .andExpect(model().attribute("hotels", hasSize(1)))
            .andExpect(model().attribute("hotels", hasItem(
                    allOf(
                            hasProperty("id", is(1L)),
                            hasProperty("nombre", is("Hotel Bilbao"))
                    )
            )))
            .andExpect(model().attributeExists("ciudad"))  // Verifica que 'ciudad' existe en el modelo
            .andExpect(model().attribute("ciudad", is("Bilbao")))
            .andExpect(model().attributeExists("dias"))
            .andExpect(model().attributeExists("fechaCheckIn"))
            .andExpect(model().attributeExists("fechaCheckOut"));
}


    /*@Test
    @WithMockUser(username = "user")
    public void testShowHotelDetails() throws Exception {
        HotelDisponibilidadDTO hotelDisponibilidadDTO = HotelDisponibilidadDTO.builder()
                .id(1L)
                .nombre("Hotel Bilbao")
                .build();

        Mockito.when(servicioBusquedaHotel.buscarHotelDisponibleById(1L, LocalDate.of(2024, 7, 1), LocalDate.of(2024, 7, 10)))
                .thenReturn(hotelDisponibilidadDTO);

        mockMvc.perform(get("/hotel-details/1")
                        .param("fechaCheckIn", "2024-07-01")
                        .param("fechaCheckOut", "2024-07-10"))
                .andExpect(status().isOk())
                .andExpect(view().name("hotelsearch/hotel-details"))
                .andExpect(model().attributeExists("hotel"))
                .andExpect(model().attribute("hotel", hasProperty("id", is(1L))))
                .andExpect(model().attribute("hotel", hasProperty("nombre", is("Hotel Bilbao"))))
                .andExpect(model().attributeExists("duracionDias"))
                .andExpect(model().attributeExists("fechaCheckIn"))
                .andExpect(model().attributeExists("fechaCheckOut"));
    }*/
}
