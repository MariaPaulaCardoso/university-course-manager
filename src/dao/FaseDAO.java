package dao;

import java.sql.*;
import java.util.*;
import util.ConnectionFactory;
import model.Fase;
import model.Curso;

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

    public List<Fase> getAllFases() {
        List<Fase> fases = new ArrayList<>();
        String sql = "SELECT * FROM tb_fases";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            CursoDAO cursoDAO = new CursoDAO();
            while (rs.next()) {
                Fase fase = new Fase();
                fase.setId(rs.getInt("id"));
                fase.setFase(rs.getString("fase"));
                fase.setQtd_disciplinas(rs.getInt("qtd_disciplinas"));
                fase.setQtd_professores(rs.getInt("qtd_professores"));
                int cursoId = rs.getInt("curso_id");
                // Busca o curso correspondente e seta no objeto Fase
                Curso curso = null;
                for (Curso c : cursoDAO.getAllCursos()) {
                    if (c.getId() == cursoId) {
                        curso = c;
                        break;
                    }
                }
                fase.setCurso(curso);
                fases.add(fase);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fases;
    }

}
