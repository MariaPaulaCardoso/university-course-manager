package dao;

import java.sql.*;
import util.ConnectionFactory;
import model.Professor;

public class ProfessorDAO {
    public int insertProfessor(Professor professor) {
        String sql = "INSERT INTO tb_professores (disciplina_id, nome, titulo_docente) VALUES (?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, professor.getDisciplina().getId());
            
           
            String nome = professor.getNome();
            if (nome == null) nome = "";
            if (nome.length() > 40) nome = nome.substring(0, 40);
            pstmt.setString(2, nome);
            
            pstmt.setInt(3, professor.getTitulo_docente());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Inserting professor failed, no rows affected.");
            }

            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int id = generatedKeys.getInt(1);
                professor.setId(id);
                return id;
            } else {
                throw new SQLException("Inserting professor failed, no ID obtained.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
