package dao;

import java.sql.*;
import java.util.*;
import model.*;
import util.ConnectionFactory;

public class CursoDAO {
    public int insertCurso(Curso curso) {
        String selectSql = "SELECT id FROM tb_cursos WHERE nome = ? AND data_processamento = ?";
        String insertSql = "INSERT INTO tb_cursos (nome, data_processamento, periodo_inicial, periodo_final, sequencia_arquivo, versao_layout) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection()) {
        
            try (PreparedStatement selectStmt = conn.prepareStatement(selectSql)) {
                selectStmt.setString(1, curso.getNome());
                selectStmt.setDate(2, new java.sql.Date(curso.getDataProcessamento().getTime()));
                ResultSet rs = selectStmt.executeQuery();
                if (rs.next()) {
                    int id = rs.getInt("id");
                    curso.setId(id);
                    return id;
                }
            }
            // Insert if not exists
            try (PreparedStatement pstmt = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, curso.getNome());
                pstmt.setDate(2, new java.sql.Date(curso.getDataProcessamento().getTime()));
                pstmt.setString(3, curso.getPeriodoInicial());
                pstmt.setString(4, curso.getPeriodoFinal());
                pstmt.setInt(5, curso.getSequenciaArquivo());
                pstmt.setString(6, curso.getVersaoLayout());

                int affectedRows = pstmt.executeUpdate();
                if (affectedRows == 0) {
                    throw new SQLException("Inserting curso failed, no rows affected.");
                }
                ResultSet generatedKeys = pstmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int id = generatedKeys.getInt(1);
                    curso.setId(id);
                    return id;
                } else {
                    throw new SQLException("Inserting curso failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public List<Curso> getAllCursos() {
        List<Curso> cursos = new ArrayList<>();
        String sql = "SELECT * FROM tb_cursos";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Curso curso = new Curso();
                curso.setId(rs.getInt("id"));
                curso.setNome(rs.getString("nome"));
                curso.setDataProcessamento(rs.getDate("data_processamento"));
                curso.setPeriodoInicial(rs.getString("periodo_inicial"));
                curso.setPeriodoFinal(rs.getString("periodo_final"));
                curso.setSequenciaArquivo(rs.getInt("sequencia_arquivo"));
                curso.setVersaoLayout(rs.getString("versao_layout"));
                cursos.add(curso);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cursos;
    }

    public void deleteCurso(int id) {
        String sql = "DELETE FROM tb_cursos WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
