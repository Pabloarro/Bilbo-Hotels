package es.deusto.bilboHotels.model.dto;

import es.deusto.bilboHotels.model.enums.TipoRol;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class RegistroUsuarioDTOTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testRegistroUsuarioDTOValid() {
        RegistroUsuarioDTO registroUsuarioDTO = RegistroUsuarioDTO.builder()
                .username("usuario@example.com")
                .password("password")
                .nombre("Jon")
                .apellido("Garcia")
                .tipoRol(TipoRol.CLIENTE)
                .build();

        Set<ConstraintViolation<RegistroUsuarioDTO>> violations = validator.validate(registroUsuarioDTO);

        assertTrue(violations.isEmpty(), "No debería haber violaciones de validación");
    }

    @Test
    public void testRegistroUsuarioDTOInvalidEmail() {
        RegistroUsuarioDTO registroUsuarioDTO = RegistroUsuarioDTO.builder()
                .username("usuarioexample.com")
                .password("password")
                .nombre("Jon")
                .apellido("Garcia")
                .tipoRol(TipoRol.CLIENTE)
                .build();

        Set<ConstraintViolation<RegistroUsuarioDTO>> violations = validator.validate(registroUsuarioDTO);

        assertEquals(1, violations.size(), "Debe haber una violación de validación");
        ConstraintViolation<RegistroUsuarioDTO> violation = violations.iterator().next();
        assertEquals("Email invalido", violation.getMessage());
    }

    @Test
    public void testRegistroUsuarioDTOInvalidPassword() {
        RegistroUsuarioDTO registroUsuarioDTO = RegistroUsuarioDTO.builder()
                .username("usuario@example.com")
                .password("pwd")
                .nombre("Jon")
                .apellido("Garcia")
                .tipoRol(TipoRol.CLIENTE)
                .build();

        Set<ConstraintViolation<RegistroUsuarioDTO>> violations = validator.validate(registroUsuarioDTO);

        assertEquals(1, violations.size(), "Debe haber una violación de validación");
        ConstraintViolation<RegistroUsuarioDTO> violation = violations.iterator().next();
        assertEquals("La contrasenia debe tener entre 6 y 20 caracteres.", violation.getMessage());
    }

    @Test
    public void testEqualsAndHashCode() {
        RegistroUsuarioDTO usuario1 = RegistroUsuarioDTO.builder()
                .username("usuario@example.com")
                .password("password")
                .nombre("Jon")
                .apellido("Garcia")
                .tipoRol(TipoRol.CLIENTE)
                .build();

        RegistroUsuarioDTO usuario2 = RegistroUsuarioDTO.builder()
                .username("usuario@example.com")
                .password("password")
                .nombre("Jon")
                .apellido("Garcia")
                .tipoRol(TipoRol.CLIENTE)
                .build();

        assertEquals(usuario1, usuario2);
        assertEquals(usuario1.hashCode(), usuario2.hashCode());
    }

    @Test
    public void testToString() {
        RegistroUsuarioDTO usuario = RegistroUsuarioDTO.builder()
                .username("usuario@example.com")
                .password("password")
                .nombre("Jon")
                .apellido("Garcia")
                .tipoRol(TipoRol.CLIENTE)
                .build();

        String expectedToString = "RegistroUsuarioDTO(username=usuario@example.com, password=password, nombre=Jon, apellido=Garcia, tipoRol=CLIENTE)";
        assertEquals(expectedToString, usuario.toString());
    }

    @Test
    public void testGettersAndSetters() {
        RegistroUsuarioDTO usuario = RegistroUsuarioDTO.builder()
            .username("usuario@example.com")
            .password("password")
            .nombre("Jon")
            .apellido("Garcia")
            .tipoRol(TipoRol.CLIENTE)
            .build();

        assertEquals("usuario@example.com", usuario.getUsername());
        assertEquals("password", usuario.getPassword());
        assertEquals("Jon", usuario.getNombre());
        assertEquals("Garcia", usuario.getApellido());
        assertEquals(TipoRol.CLIENTE, usuario.getTipoRol());

        usuario.setUsername("usuario@example.com");
        assertEquals("usuario@example.com", usuario.getUsername());

        usuario.setPassword("password");
        assertEquals("password", usuario.getPassword());

        usuario.setNombre("Jon");
        assertEquals("Jon", usuario.getNombre());

        usuario.setApellido("Garcia");
        assertEquals("Garcia", usuario.getApellido());

        usuario.setTipoRol(TipoRol.CLIENTE);
        assertEquals(TipoRol.CLIENTE, usuario.getTipoRol());
    }
}