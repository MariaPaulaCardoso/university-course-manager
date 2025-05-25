package dao;

import java.sql.*;
import util.ConnectionFactory;
import model.Professor;

public class ProfessorDAO {
    public int insertProfessor(Professor professor) {
        String sql = "INSERT INTO professores (nome, disciplina, titulo_docente) VALUES (?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, professor.getNome());
            pstmt.setString(2, professor.getDisciplina().getNome());
            pstmt.setInt(3, professor.getTitulo_docente());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Inserting professor failed, no rows affected.");
            }

            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else {
                throw new SQLException("Inserting professor failed, no ID obtained.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
