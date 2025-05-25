package dao;

import java.sql.*;
import util.ConnectionFactory;
import model.Fase;

public class FaseDAO {
    public int insertFase(Fase fase) {
        String sql = "INSERT INTO tb_fases (curso_id, fase, qtd_disciplinas, qtd_professores) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, fase.getCurso().getId());
            pstmt.setString(2, fase.getFase());
            pstmt.setInt(3, fase.getQtd_disciplinas());
            pstmt.setInt(4, fase.getQtd_professores());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Inserting fase failed, no rows affected.");
            }

            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int id = generatedKeys.getInt(1);
                fase.setId(id);
                return id;
            } else {
                throw new SQLException("Inserting fase failed, no ID obtained.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
    
}
