package es.deusto.bilboHotels.controller;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import es.deusto.bilboHotels.model.Rol;
import es.deusto.bilboHotels.model.dto.UsuarioDTO;
import es.deusto.bilboHotels.service.ServicioUsuario;

@WebMvcTest(MiCuentaController.class)
public class MiCuentaControllerIntegrationTest implements IntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServicioUsuario servicioUsuario;

    private UsuarioDTO usuarioDTO;

    @BeforeEach
    public void setup() {
        usuarioDTO = UsuarioDTO.builder()
                .id(1L)
                .username("user@example.com")
                .nombre("Nombre")
                .apellido("Apellido")
                .rol(new Rol())
                .build();

        Mockito.when(servicioUsuario.buscarUsuarioDTOByUsername(anyString())).thenReturn(usuarioDTO);
    }

    @Test
    @WithMockUser(username = "customer", roles = {"CUSTOMER"})
    public void testShowCustomerAccount() throws Exception {
        mockMvc.perform(get("/customer/account"))
                .andExpect(status().isOk())
                .andExpect(view().name("customer/account"))
                .andExpect(model().attributeExists("usuario"))
                .andExpect(model().attribute("usuario", hasProperty("username", is("user@example.com"))));
    }

    @Test
    @WithMockUser(username = "customer", roles = {"CUSTOMER"})
    public void testShowCustomerEditForm() throws Exception {
        mockMvc.perform(get("/customer/account/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("customer/account-edit"))
                .andExpect(model().attributeExists("usuario"))
                .andExpect(model().attribute("usuario", hasProperty("username", is("user@example.com"))));
    }

    /*@Test
    @WithMockUser(username = "customer", roles = {"CUSTOMER"})
    public void testEditarCuentaCliente() throws Exception {
        mockMvc.perform(post("/customer/account/edit")
                        .flashAttr("usuario", usuarioDTO))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/customer/account?success"));
    }

    @Test
    @WithMockUser(username = "customer", roles = {"CUSTOMER"})
    public void testEditarCuentaCliente_NombreUsuarioYaExisteException() throws Exception {
        Mockito.doThrow(new NombreUsuarioYaExisteException("Nombre de usuario ya registrado!"))
                .when(servicioUsuario).actualizarUsuarioLoggeado(any(UsuarioDTO.class));

        mockMvc.perform(post("/customer/account/edit")
                        .flashAttr("usuario", usuarioDTO))
                .andExpect(status().isOk())
                .andExpect(view().name("customer/account-edit"))
                .andExpect(model().attributeHasFieldErrors("usuario", "username"))
                .andExpect(model().attribute("usuario", hasProperty("username", is("user@example.com"))));
    }*/

    @Test
    @WithMockUser(username = "manager", roles = {"MANAGER"})
    public void testMostrarCuentaManagerHotel() throws Exception {
        mockMvc.perform(get("/manager/account"))
                .andExpect(status().isOk())
                .andExpect(view().name("hotelmanager/account"))
                .andExpect(model().attributeExists("usuario"))
                .andExpect(model().attribute("usuario", hasProperty("username", is("user@example.com"))));
    }

    @Test
    @WithMockUser(username = "manager", roles = {"MANAGER"})
    public void testMostrarFormularioEdicionManagerhotel() throws Exception {
        mockMvc.perform(get("/manager/account/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("hotelmanager/account-edit"))
                .andExpect(model().attributeExists("usuario"))
                .andExpect(model().attribute("usuario", hasProperty("username", is("user@example.com"))));
    }

    /*@Test
    @WithMockUser(username = "manager", roles = {"MANAGER"})
    public void testEditarCuentaHotelManager() throws Exception {
        mockMvc.perform(post("/manager/account/edit")
                        .flashAttr("usuario", usuarioDTO))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/manager/account?success"));
    }

    @Test
    @WithMockUser(username = "manager", roles = {"MANAGER"})
    public void testEditarCuentaHotelManager_NombreUsuarioYaExisteException() throws Exception {
        Mockito.doThrow(new NombreUsuarioYaExisteException("Nombre de usuario ya registrado!"))
                .when(servicioUsuario).actualizarUsuarioLoggeado(any(UsuarioDTO.class));

        mockMvc.perform(post("/manager/account/edit")
                        .flashAttr("usuario", usuarioDTO))
                .andExpect(status().isOk())
                .andExpect(view().name("hotelmanager/account-edit"))
                .andExpect(model().attributeHasFieldErrors("usuario", "username"))
                .andExpect(model().attribute("usuario", hasProperty("username", is("user@example.com"))));
    }*/
}
