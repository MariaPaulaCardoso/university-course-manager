package dao;

import java.sql.*;
import util.ConnectionFactory;
import model.Fase;

public class FaseDAO {
    public int insertFase(Fase fase) {
        String sql = "INSERT INTO fases (nome, qtd_disciplinas, qtd_professores, curso_id) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, fase.getFase());
            pstmt.setInt(2, fase.getQtd_disciplinas());
            pstmt.setInt(3, fase.getQtd_professores());
            pstmt.setInt(4, fase.getCurso().getId());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Inserting fase failed, no rows affected.");
            }

            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else {
                throw new SQLException("Inserting fase failed, no ID obtained.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
    
}
