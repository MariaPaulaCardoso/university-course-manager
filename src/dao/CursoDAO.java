package dao;

import java.sql.*;

import model.*;
import util.ConnectionFactory;

public class CursoDAO {
    public int insertCurso(Curso curso) {
        String sql = "INSERT INTO cursos (nome, data_proc, periodo_inicial, periodo_final, sequencia, versao) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, curso.getNome());
            pstmt.setDate(2, new java.sql.Date(curso.getDataProcessamento().getTime()));
            pstmt.setString(3, curso.getPeriodoInicial());
            pstmt.setString(4, curso.getPeriodoFinal());
            pstmt.setInt(5, curso.getSequenciaArquivo());
            pstmt.setString(6, curso.getVersaoLayout());

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                curso.setId(id);
                return id;
            } 

        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } 

        return -1;
    }
}
