package dao;

import java.sql.*;
import util.ConnectionFactory;
import model.Disciplina;

public class DisciplinaDAO {
    public int insertDisciplina(Disciplina disciplina) {
        String sql = "INSERT INTO disciplinas (nome, carga_horaria, fase_id) VALUES (?, ?, ?)";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, disciplina.getNome());
            pstmt.setInt(2, disciplina.getCarga_horaria());
            pstmt.setInt(3, disciplina.getFaseId().getId());
            
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Inserting disciplina failed, no rows affected.");
            }
            
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else {
                throw new SQLException("Inserting disciplina failed, no ID obtained.");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
    
}
