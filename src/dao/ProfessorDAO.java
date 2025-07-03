package dao;

import java.sql.*;
import java.util.*;
import util.ConnectionFactory;
import model.Professor;
import model.Disciplina;

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

    public List<Professor> getAllProfessores() {
        List<Professor> professores = new ArrayList<>();
        String sql = "SELECT * FROM tb_professores";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            DisciplinaDAO disciplinaDAO = new DisciplinaDAO();
            List<Disciplina> todasDisciplinas = disciplinaDAO.getAllDisciplinas();
            while (rs.next()) {
                Professor p = new Professor();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setTitulo_docente(rs.getInt("titulo_docente"));
                int disciplinaId = rs.getInt("disciplina_id");
                // Busca a disciplina correspondente e seta no objeto Professor
                Disciplina disciplina = null;
                for (Disciplina d : todasDisciplinas) {
                    if (d.getId() == disciplinaId) {
                        disciplina = d;
                        break;
                    }
                }
                p.setDisciplina(disciplina);
                professores.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return professores;
    }

    public void updateProfessor(Professor professor) {
        String sql = "UPDATE tb_professores SET disciplina_id = ?, nome = ?, titulo_docente = ? WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, professor.getDisciplina().getId());
            pstmt.setString(2, professor.getNome());
            pstmt.setInt(3, professor.getTitulo_docente());
            pstmt.setInt(4, professor.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
