package es.deusto.bilboHotels.service;

import es.deusto.bilboHotels.BilboHotelsApplication;
import es.deusto.bilboHotels.model.Direccion;
import es.deusto.bilboHotels.model.dto.DireccionDTO;
import es.deusto.bilboHotels.model.Hotel;
import es.deusto.bilboHotels.model.dto.HotelDTO;
import es.deusto.bilboHotels.model.dto.HotelRegistroDTO;
import es.deusto.bilboHotels.service.ServicioDireccion;
import es.deusto.bilboHotels.service.ServicioHotel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = BilboHotelsApplication.class)
public class ServicioHotelImplIntegrationTest {

    @Autowired
    private ServicioHotel servicioHotel;

    @MockBean
    private ServicioDireccion servicioDireccion;

    @BeforeEach
    void setUp() {
        // Inicialización de mocks
        MockitoAnnotations.openMocks(this);
    }
/*
    @Test
    public void testGuardarYBuscarHotel() {
        // Datos de prueba
        DireccionDTO direccionDTO = new DireccionDTO();
        direccionDTO.setLineaDireccion("Calle Falsa 123");
        direccionDTO.setCiudad("Bilbao");
        direccionDTO.setPais("España");

        Direccion direccion = new Direccion();
        direccion.setId(1L);
        direccion.setLineaDireccion("Calle Falsa 123");
        direccion.setCiudad("Bilbao");
        direccion.setPais("España");

        HotelDTO hotelDTO = new HotelDTO();
        hotelDTO.setNombre("Hotel XYZ");
        hotelDTO.setDireccionDTO(direccionDTO);

        Hotel hotelGuardado = new Hotel();
        hotelGuardado.setId(1L);
        hotelGuardado.setNombre("Hotel XYZ");
        hotelGuardado.setDireccion(direccion);

        // Simulación de comportamiento del servicio de dirección
        when(servicioDireccion.guardarDireccion(any(DireccionDTO.class))).thenReturn(direccion);
        when(servicioDireccion.buscarDireccionById(1L)).thenReturn(direccionDTO);

        // Simulación de comportamiento del servicio de hotel
        when(servicioHotel.guardarHotel(any(HotelRegistroDTO.class))).thenReturn(hotelGuardado);

        // Llamada al método del servicio de hotel para buscar un hotel por su ID
        Optional<Hotel> hotelRecuperado = servicioHotel.buscarHotelById(1L);

        // Verificación de que el hotel se recuperó correctamente
        assertEquals("Hotel XYZ", hotelRecuperado.get().getNombre());
    }*/
}