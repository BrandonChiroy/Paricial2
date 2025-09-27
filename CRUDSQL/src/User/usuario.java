package User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.*;
import app.Conexion;
import app.Alumnos;
import usuarioAlumno.Alumno;

public class usuario {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(usuario::mostrarLogin);
    }

    private static void mostrarLogin() {
        JFrame frame = new JFrame("Login");
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);

        JLabel userLabel = new JLabel("Usuario:");
        userLabel.setBounds(20, 30, 80, 25);
        frame.add(userLabel);

        JLabel passwordLabel = new JLabel("Contraseña:");
        passwordLabel.setBounds(20, 70, 80, 25);
        frame.add(passwordLabel);

        JTextField userText = new JTextField(20);
        userText.setBounds(120, 30, 150, 25);
        frame.add(userText);

        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(120, 70, 150, 25);
        frame.add(passwordText);

        JButton loginButton = new JButton("Iniciar sesión");
        loginButton.setBounds(80, 120, 140, 30);
        frame.add(loginButton);

        loginButton.addActionListener((ActionEvent e) -> {
            String usuario = userText.getText();
            String contrasena = new String(passwordText.getPassword());

            if (validarUsuario(usuario, contrasena)) {
                JOptionPane.showMessageDialog(frame, "¡Login exitoso!");
                frame.dispose();
                mostrarMenu();
            } else {
                JOptionPane.showMessageDialog(frame, "Usuario o contraseña incorrectos");
            }
        });

        frame.setVisible(true);
    }

    private static boolean validarUsuario(String usuario, String contrasena) {
        // Si quieres forzar un usuario/contraseña fija sin BD, descomenta:
        // return usuario.equals("admin") && contrasena.equals("1234");

        Connection con = Conexion.getConexion();
        if (con != null) {
            String sql = "SELECT * FROM Usuarios WHERE usuario=? AND contrasena=?";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, usuario);
                ps.setString(2, contrasena);
                ResultSet rs = ps.executeQuery();
                return rs.next();
            } catch (SQLException e) {
                System.out.println("Error al validar usuario: " + e.getMessage());
            } finally {
                try { con.close(); } catch (SQLException ex) {}
            }
        }
        return false;
    }

    private static void mostrarMenu() {
        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Gestión Alumnos", new Alumnos().getContentPane());
        tabs.addTab("Buscar Alumno", new Alumno().getContentPane());

        JFrame menuFrame = new JFrame("Menú Principal");
        menuFrame.setSize(500, 400);
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setLocationRelativeTo(null);
        menuFrame.add(tabs);
        menuFrame.setVisible(true);
    }
}





