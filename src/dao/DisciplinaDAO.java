package dao;

import java.sql.*;
import java.util.*;
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
    
    public List<Disciplina> getAllDisciplinas() {
        List<Disciplina> disciplinas = new ArrayList<>();
        String sql = "SELECT * FROM tb_disciplinas";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Disciplina d = new Disciplina();
                d.setId(rs.getInt("id"));
                d.setCodigo(rs.getString("codigo"));
                // FaseId is available as rs.getInt("fase_id") if needed
                disciplinas.add(d);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return disciplinas;
    }
    
    public void updateDisciplina(Disciplina disciplina) {
        String sql = "UPDATE tb_disciplinas SET fase_id = ?, codigo = ? WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, disciplina.getFaseId().getId());
            pstmt.setString(2, disciplina.getCodigo());
            pstmt.setInt(3, disciplina.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void deleteDisciplina(int id) {
        String sql = "DELETE FROM tb_disciplinas WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
