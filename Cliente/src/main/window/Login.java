package window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Login extends JFrame {
    private List<Cliente> clientes;

    public Login() {
        super("Login");

        // Inicializar la lista de clientes
        clientes = new ArrayList<>();

        // Crear algunos clientes de prueba
        clientes.add(new Cliente("12345678A", "Alejandro", "Pelegrin", "Alejandro", "01/01/1990"));
        clientes.add(new Cliente("87654321B", "Pablo", "Arroyuelos", "Pablo", "15/06/1985"));
        clientes.add(new Cliente("77777777C", "Alvaro", "Martinez", "Alvaro", "20/08/2000"));

        // Configuración de la ventana
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar la ventana en la pantalla

        // Panel principal
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));

        // Botón "Iniciar Sesión"
        JButton btnIniciarSesion = new JButton("Iniciar Sesión");
        btnIniciarSesion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lógica para iniciar sesión
                JTextField txtNombre = new JTextField();
                JPasswordField txtContrasenia = new JPasswordField();

                Object[] message = {
                        "Nombre de usuario:", txtNombre,
                        "Contraseña:", txtContrasenia
                };

                int option = JOptionPane.showConfirmDialog(null, message, "Iniciar Sesión", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    String nombre = txtNombre.getText();
                    String contrasenia = new String(txtContrasenia.getPassword());
                    Cliente cliente = iniciarSesion(nombre, contrasenia);
                    if (cliente != null) {
                        JOptionPane.showMessageDialog(null, "Inicio de sesión exitoso: " + cliente.getNombre());
                    } else {
                        JOptionPane.showMessageDialog(null, "Inicio de sesión fallido. Nombre de usuario o contraseña incorrectos.");
                    }
                }
            }
        });
        panel.add(btnIniciarSesion);

        // Botón "Registrarse"
        JButton btnRegistrarse = new JButton("Registrarse");
        btnRegistrarse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lógica para registrarse
                JTextField txtDni = new JTextField();
                JTextField txtNombre = new JTextField();
                JTextField txtApellido = new JTextField();
                JTextField txtFechaNacimiento = new JTextField();
                JPasswordField txtContrasenia = new JPasswordField();

                Object[] message = {
                        "DNI:", txtDni,
                        "Nombre:", txtNombre,
                        "Apellido:", txtApellido,
                        "Fecha de Nacimiento (dd/mm/aaaa):", txtFechaNacimiento,
                        "Contraseña:", txtContrasenia
                };

                int option = JOptionPane.showConfirmDialog(null, message, "Registrarse", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    String dni = txtDni.getText();
                    String nombre = txtNombre.getText();
                    String apellido = txtApellido.getText();
                    String fechaNacimiento = txtFechaNacimiento.getText();
                    String contrasenia = new String(txtContrasenia.getPassword());

                    registrarCliente(dni, nombre, apellido, contrasenia, fechaNacimiento);
                    JOptionPane.showMessageDialog(null, "Registro exitoso");
                }
            }
        });
        panel.add(btnRegistrarse);

        // Botón "Entrar como Invitado"
        JButton btnInvitado = new JButton("Entrar como Invitado");
        btnInvitado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lógica para entrar como invitado
                JOptionPane.showMessageDialog(null, "Bienvenido como invitado");
            }
        });
        panel.add(btnInvitado);

        // Agregar el panel a la ventana principal
        getContentPane().add(panel);

        // Mostrar la ventana
        setVisible(true);
    }

    // Método para iniciar sesión
    private Cliente iniciarSesion(String nombre, String contrasenia) {
        for (Cliente cliente : clientes) {
            if (cliente.getNombre().equals(nombre) && cliente.getContrasenia().equals(contrasenia)) {
                return cliente;
            }
        }
        return null;
    }

    // Método para registrar un nuevo cliente
    private void registrarCliente(String dni, String nombre, String apellido, String contrasenia, String fechaNacimiento) {
        clientes.add(new Cliente(dni, nombre, apellido, contrasenia, fechaNacimiento));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Login();
            }
        });
    }
}
