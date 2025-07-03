package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "chave";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void dropAllTables() {
        // Use actual table names and correct order
        String[] tables = {"tb_professores", "tb_disciplinas", "tb_fases", "tb_cursos"};
        StringBuilder errors = new StringBuilder();
        int dropped = 0;
        try (Connection conn = getConnection();
             java.sql.Statement stmt = conn.createStatement()) {
            for (String table : tables) {
                String sql = "DROP TABLE IF EXISTS public." + table + " CASCADE";
                System.out.println("Executando: " + sql);
                try {
                    stmt.executeUpdate(sql);
                    dropped++;
                } catch (Exception e) {
                    errors.append("Erro ao dropar tabela ").append(table).append(": ").append(e.getMessage()).append("\n");
                }
            }
            if (errors.length() > 0) {
                javax.swing.SwingUtilities.invokeLater(() -> {
                    javax.swing.JOptionPane.showMessageDialog(null, errors.toString(), "Erro ao dropar tabelas", javax.swing.JOptionPane.ERROR_MESSAGE);
                });
            } else if (dropped > 0) {
                javax.swing.SwingUtilities.invokeLater(() -> {
                    javax.swing.JOptionPane.showMessageDialog(null, "Tabelas removidas com sucesso!", "Sucesso", javax.swing.JOptionPane.INFORMATION_MESSAGE);
                });
            } else {
                javax.swing.SwingUtilities.invokeLater(() -> {
                    javax.swing.JOptionPane.showMessageDialog(null, "Nenhuma tabela foi removida.", "Aviso", javax.swing.JOptionPane.WARNING_MESSAGE);
                });
            }
        } catch (Exception e) {
            javax.swing.SwingUtilities.invokeLater(() -> {
                javax.swing.JOptionPane.showMessageDialog(null, e.getMessage(), "Erro de conexão", javax.swing.JOptionPane.ERROR_MESSAGE);
            });
            e.printStackTrace();
        }
    }

}
