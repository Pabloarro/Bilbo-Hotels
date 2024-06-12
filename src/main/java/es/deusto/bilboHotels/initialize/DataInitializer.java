package es.deusto.bilboHotels.initialize;

import es.deusto.bilboHotels.model.*;
import es.deusto.bilboHotels.model.enums.TipoRol;
import es.deusto.bilboHotels.model.enums.TipoHabitacion;
import es.deusto.bilboHotels.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final RepoUsuario repoUsuario;
    private final RepoRol repoRol;
    private final RepoAdmin repoAdmin;
    private final RepoCliente repoCliente;
    private final RepoHotelManager repoHotelManager;
    private final PasswordEncoder passwordEncoder;
    private final RepoDireccion repoDireccion;
    private final RepoHotel repoHotel;
    private final RepoDisponibilidad repoDisponibilidad;

    @Override
    @Transactional
    public void run(String... args) {

        try {
            log.warn("Verificando si se requiere la persistencia de datos de prueba...");

            if (repoRol.count() == 0 && repoUsuario.count() == 0) {
                log.info("Iniciando la persistencia de datos de prueba.");

                Rol rolAdmin = new Rol(TipoRol.ADMIN);
                Rol rolCliente = new Rol(TipoRol.CLIENTE);
                Rol rolManagerHotel = new Rol(TipoRol.HOTEL_MANAGER);

                repoRol.save(rolAdmin);
                repoRol.save(rolCliente);
                repoRol.save(rolManagerHotel);
                log.info("Datos de roles persistidos.");

                Usuario usuario1 = Usuario.builder().username("admin@hotel.com").password(passwordEncoder.encode("1")).nombre("Admin").apellido("Admin").rol(rolAdmin).build();
                Usuario usuario2 = Usuario.builder().username("cliente1@hotel.com").password(passwordEncoder.encode("1")).nombre("cliente").apellido("cliente").rol(rolCliente).build();
                Usuario usuario3 = Usuario.builder().username("manager1@hotel.com").password(passwordEncoder.encode("1")).nombre("manager").apellido("manager").rol(rolManagerHotel).build();
                Usuario usuario4 = Usuario.builder().username("manager2@hotel.com").password(passwordEncoder.encode("1")).nombre("manager2").apellido("manager2").rol(rolManagerHotel).build();

                repoUsuario.save(usuario1);
                repoUsuario.save(usuario2);
                repoUsuario.save(usuario3);
                repoUsuario.save(usuario4);
                

                Admin admin1 = Admin.builder().usuario(usuario1).build();
                Cliente c1 = Cliente.builder().usuario(usuario2).build();
                HotelManager mh1 = HotelManager.builder().usuario(usuario3).build();
                HotelManager mh2 = HotelManager.builder().usuario(usuario4).build();

                repoAdmin.save(admin1);
                repoCliente.save(c1);
                repoHotelManager.save(mh1);
                repoHotelManager.save(mh2);
             

                Direccion addressBrk1 = Direccion.builder().lineaDireccion("Rio Castaños Kalea, 2, 48903 ").ciudad("Barakaldo")
                        .pais("Pais Vasco").build();
                Direccion addressBrk2 = Direccion.builder().lineaDireccion("Retuerto, 69 Barrio De Kareaga Norte, Nº Reg, Hbi01170, 48903").ciudad("Barakaldo")
                        .pais("Pais Vasco").build();
                Direccion addressBrk3 = Direccion.builder().lineaDireccion("Eskauritza Kalea, 22, 48903 El Regato").ciudad("Barakaldo")
                        .pais("Pais Vasco").build();

                Direccion addressBilbo1 = Direccion.builder().lineaDireccion("C/ General Concha 28 Nº Reg H.Bi.01209, 48010").ciudad("Bilbao")
                        .pais("Pais Vasco").build();
                Direccion addressBilbo2 = Direccion.builder().lineaDireccion("Colón de Larreátegui K., 9, Abando, 48001").ciudad("Bilbao")
                        .pais("Pais Vasco").build();
                Direccion addressBilbo3 = Direccion.builder().lineaDireccion(" C. Bidebarrieta Kalea, 2, Ibaiondo, 48005").ciudad("Bilbao")
                        .pais("Pais Vasco").build();

                repoDireccion.save(addressBrk1);
                repoDireccion.save(addressBrk2);
                repoDireccion.save(addressBrk3);
                repoDireccion.save(addressBilbo1);
                repoDireccion.save(addressBilbo2);
                repoDireccion.save(addressBilbo3);

                Hotel hotelBilbao1 = Hotel.builder().nombre("Hotel ibis Bilbao")
                        .direccion(addressBilbo1).hotelManager(mh2).build();
                Hotel hotelBilbao2 = Hotel.builder().nombre("Hotel Abando")
                        .direccion(addressBilbo2).hotelManager(mh2).build();
                Hotel hotelBilbao3 = Hotel.builder().nombre("Petit Palace Arana")
                        .direccion(addressBilbo3).hotelManager(mh2).build();
                        
                Hotel hotelBrk1 = Hotel.builder().nombre("Hotel Puerta bilbao")
                        .direccion(addressBrk1).hotelManager(mh1).build();
                Hotel hotelBrk2 = Hotel.builder().nombre("Ibis Bilbao")
                        .direccion(addressBrk2).hotelManager(mh1).build();
                Hotel hotelBrk3 = Hotel.builder().nombre("Hotel azeko")
                        .direccion(addressBrk3).hotelManager(mh1).build();



                Habitacion INDIVIDUALRoomBrk1 = Habitacion.builder().tipoHabitacion(TipoHabitacion.INDIVIDUAL)
                        .precioPorNoche(370).contadorHabitacion(35).hotel(hotelBrk1).build();
                Habitacion DOBLERoomBrk1 = Habitacion.builder().tipoHabitacion(TipoHabitacion.DOBLE)
                        .precioPorNoche(459).contadorHabitacion(45).hotel(hotelBrk1).build();

                Habitacion INDIVIDUALRoomBrk2 = Habitacion.builder().tipoHabitacion(TipoHabitacion.INDIVIDUAL)
                        .precioPorNoche(700).contadorHabitacion(25).hotel(hotelBrk2).build();
                Habitacion DOBLERoomBrk2 = Habitacion.builder().tipoHabitacion(TipoHabitacion.DOBLE)
                        .precioPorNoche(890).contadorHabitacion(30).hotel(hotelBrk2).build();

                Habitacion INDIVIDUALRoomBrk3 = Habitacion.builder().tipoHabitacion(TipoHabitacion.INDIVIDUAL)
                        .precioPorNoche(691).contadorHabitacion(30).hotel(hotelBrk3).build();
                Habitacion DOBLERoomBrk3 = Habitacion.builder().tipoHabitacion(TipoHabitacion.DOBLE)
                        .precioPorNoche(800).contadorHabitacion(75).hotel(hotelBrk3).build();

                Habitacion INDIVIDUALRoomBilbao1 = Habitacion.builder().tipoHabitacion(TipoHabitacion.INDIVIDUAL)
                        .precioPorNoche(120.0).contadorHabitacion(25).hotel(hotelBilbao1).build();
                Habitacion DOBLERoomBilbao1 = Habitacion.builder().tipoHabitacion(TipoHabitacion.DOBLE)
                        .precioPorNoche(250.0).contadorHabitacion(15).hotel(hotelBilbao1).build();

                Habitacion INDIVIDUALRoomBilbao2 = Habitacion.builder().tipoHabitacion(TipoHabitacion.INDIVIDUAL)
                        .precioPorNoche(300).contadorHabitacion(50).hotel(hotelBilbao2).build();
                Habitacion DOBLERoomBilbao2 = Habitacion.builder().tipoHabitacion(TipoHabitacion.DOBLE)
                        .precioPorNoche(400).contadorHabitacion(50).hotel(hotelBilbao2).build();

                Habitacion INDIVIDUALRoomBilbao3 = Habitacion.builder().tipoHabitacion(TipoHabitacion.INDIVIDUAL)
                        .precioPorNoche(179).contadorHabitacion(45).hotel(hotelBilbao3).build();
                Habitacion DOBLERoomBilbao3 = Habitacion.builder().tipoHabitacion(TipoHabitacion.DOBLE)
                        .precioPorNoche(256).contadorHabitacion(25).hotel(hotelBilbao3).build();

                hotelBrk1.getHabitaciones().addAll(Arrays.asList(INDIVIDUALRoomBrk1,DOBLERoomBrk1));
                hotelBrk2.getHabitaciones().addAll(Arrays.asList(INDIVIDUALRoomBrk2,DOBLERoomBrk2));
                hotelBrk3.getHabitaciones().addAll(Arrays.asList(INDIVIDUALRoomBrk3,DOBLERoomBrk3));
                hotelBilbao1.getHabitaciones().addAll(Arrays.asList(INDIVIDUALRoomBilbao1,DOBLERoomBilbao1));
                hotelBilbao2.getHabitaciones().addAll(Arrays.asList(INDIVIDUALRoomBilbao2,DOBLERoomBilbao2));
                hotelBilbao3.getHabitaciones().addAll(Arrays.asList(INDIVIDUALRoomBilbao3,DOBLERoomBilbao3));

                repoHotel.save(hotelBrk1);
                repoHotel.save(hotelBrk2);
                repoHotel.save(hotelBrk3);
                repoHotel.save(hotelBilbao1);
                repoHotel.save(hotelBilbao2);
                repoHotel.save(hotelBilbao3);
                log.info("Datos del hotel persistidos");

                Disponibilidad dis1Bilbao1 = Disponibilidad.builder().hotel(hotelBilbao1)
                        .fecha(LocalDate.of(2024,5,20)).habitacion(INDIVIDUALRoomBrk1).habitacionesDisponibles(5).build();
                Disponibilidad dis2Bilbao1 = Disponibilidad.builder().hotel(hotelBilbao1)
                        .fecha(LocalDate.of(2024,5,21)).habitacion(DOBLERoomBrk1).habitacionesDisponibles(7).build();

                repoDisponibilidad.save(dis1Bilbao1);
                repoDisponibilidad.save(dis2Bilbao1);
                log.info("Datos de disponibilidad persistidos");

            } else {
                log.info("No se requiere la persistencia de datos de prueba");
            }
            log.warn("Aplicacion lista :)");
        } catch (DataAccessException e) {
            log.error("Se produjo una excepción durante la persistencia de datos: " + e.getMessage());
        } catch (Exception e) {
            log.error("Se produjo una exception inesperada: " + e.getMessage());
        }
    }
}
