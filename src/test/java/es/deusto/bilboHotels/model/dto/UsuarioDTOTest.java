package es.deusto.bilboHotels.model.dto;

import es.deusto.bilboHotels.model.Rol;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class UsuarioDTOTest {

    private UsuarioDTO.UsuarioDTOBuilder usuarioDTOBuilder;
    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        usuarioDTOBuilder = UsuarioDTO.builder()
                .id(1L)
                .username("test@example.com")
                .nombre("John")
                .apellido("Doe")
                .rol(new Rol());
    }

    @Test
    public void testUsuarioDTOCreation() {
        UsuarioDTO usuarioDTO = usuarioDTOBuilder.build();

        assertThat(usuarioDTO.getId(), is(1L));
        assertThat(usuarioDTO.getUsername(), is("test@example.com"));
        assertThat(usuarioDTO.getNombre(), is("John"));
        assertThat(usuarioDTO.getApellido(), is("Doe"));
        assertThat(usuarioDTO.getRol(), is(notNullValue()));
    }

    @Test
    public void testUsuarioDTOValidation_EmptyUsername() {
        UsuarioDTO usuarioDTO = usuarioDTOBuilder.username("").build();

        Set<ConstraintViolation<UsuarioDTO>> violations = validator.validate(usuarioDTO);
        assertThat(violations, is(not(empty())));
        assertThat(violations, hasItem(hasProperty("message", is("La dirección de correo electrónico no puede estar vacía."))));
    }

    @Test
    public void testUsuarioDTOValidation_InvalidEmail() {
        UsuarioDTO usuarioDTO = usuarioDTOBuilder.username("invalid-email").build();

        Set<ConstraintViolation<UsuarioDTO>> violations = validator.validate(usuarioDTO);
        assertThat(violations, is(not(empty())));
        assertThat(violations, hasItem(hasProperty("message", is("Email invalido"))));
    }

    @Test
    public void testUsuarioDTOValidation_EmptyNombre() {
        UsuarioDTO usuarioDTO = usuarioDTOBuilder.nombre("").build();

        Set<ConstraintViolation<UsuarioDTO>> violations = validator.validate(usuarioDTO);
        assertThat(violations, is(not(empty())));
        assertThat(violations, hasItem(hasProperty("message", is("El nombre no puede estar vacio"))));
    }

    @Test
    public void testUsuarioDTOValidation_InvalidNombre() {
        UsuarioDTO usuarioDTO = usuarioDTOBuilder.nombre("John123").build();

        Set<ConstraintViolation<UsuarioDTO>> violations = validator.validate(usuarioDTO);
        assertThat(violations, is(not(empty())));
        assertThat(violations, hasItem(hasProperty("message", is("El nombre solo puede contener letras."))));
    }

    @Test
    public void testUsuarioDTOValidation_EmptyApellido() {
        UsuarioDTO usuarioDTO = usuarioDTOBuilder.apellido("").build();

        Set<ConstraintViolation<UsuarioDTO>> violations = validator.validate(usuarioDTO);
        assertThat(violations, is(not(empty())));
        assertThat(violations, hasItem(hasProperty("message", is("El apellido no puede estar vacío."))));
    }

    @Test
    public void testUsuarioDTOValidation_InvalidApellido() {
        UsuarioDTO usuarioDTO = usuarioDTOBuilder.apellido("Doe123").build();

        Set<ConstraintViolation<UsuarioDTO>> violations = validator.validate(usuarioDTO);
        assertThat(violations, is(not(empty())));
        assertThat(violations, hasItem(hasProperty("message", is("El apellido solo puede contener letras"))));
    }
}
