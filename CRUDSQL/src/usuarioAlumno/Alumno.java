package usuarioAlumno;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.*;
import app.Conexion;

public class Alumno extends JFrame {

    private JTextField txtBuscar;
    private final JButton btnBuscar;
    private final JTable tblResultados;
    
    public Alumno() {
        setTitle("Buscar Alumno");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        JLabel lblBuscar = new JLabel("Buscar por matricula, nombre o correo:");
        lblBuscar.setBounds(20, 20, 300, 25);
        add(lblBuscar);

        txtBuscar = new JTextField();
        txtBuscar.setBounds(20, 50, 250, 25);
        add(txtBuscar);

        btnBuscar = new JButton("Buscar");
        btnBuscar.setBounds(280, 50, 100, 25);
        add(btnBuscar);

        tblResultados = new JTable();
        JScrollPane scrollPane = new JScrollPane(tblResultados);
        scrollPane.setBounds(20, 90, 440, 250);
        add(scrollPane);

        // Evento del botón
        btnBuscar.addActionListener((ActionEvent e) -> buscarAlumno(txtBuscar.getText().trim()));

        // Cargar tabla vacía
        DefaultTableModel modelo = new DefaultTableModel(
                new Object[][] {},
                new String[] {"ID", "Matricula", "Nombre", "Email"}
        );
        tblResultados.setModel(modelo);
    }

    private void buscarAlumno(String valor) {
        DefaultTableModel modelo = (DefaultTableModel) tblResultados.getModel();
        modelo.setRowCount(0); // Limpiar tabla

        try (Connection con = Conexion.getConexion()) {
            String sql = "SELECT id, matricula, nombre, email FROM alumnos " +
                         "WHERE activo=1 AND (matricula LIKE ? OR nombre LIKE ? OR email LIKE ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            String filtro = "%" + valor + "%";
            ps.setString(1, filtro);
            ps.setString(2, filtro);
            ps.setString(3, filtro);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Object[] fila = {
                    rs.getInt("id"),
                    rs.getString("matricula"),
                    rs.getString("nombre"),
                    rs.getString("email")
                };
                modelo.addRow(fila);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al buscar alumno: " + e.toString());
        }
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            new Alumno().setVisible(true);
        });
    }
}
