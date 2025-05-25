package dao;

import java.sql.*;
import util.ConnectionFactory;
import model.Disciplina;

public class DisciplinaDAO {
    public int insertDisciplina(Disciplina disciplina) {
        String sql = "INSERT INTO tb_disciplinas (fase_id, codigo, qtd_professores) VALUES (?, ?, ?)";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            // Ensure codigo is at most 40 characters
            String codigo = disciplina.getCodigo();
            if (codigo == null) codigo = "";
            if (codigo.length() > 40) codigo = codigo.substring(0, 40);
            pstmt.setInt(1, disciplina.getFaseId().getId());
            pstmt.setString(2, codigo);
            pstmt.setInt(3, disciplina.getProfessores().size());
            
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Inserting disciplina failed, no rows affected.");
            }
            
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int id = generatedKeys.getInt(1);
                disciplina.setId(id);
                return id;
            } else {
                throw new SQLException("Inserting disciplina failed, no ID obtained.");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
    
}
