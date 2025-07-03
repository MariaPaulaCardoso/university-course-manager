package dao;

import java.sql.*;

public class ImportedFileDAO {
    public boolean insertImportedFile(String fileHash, String fileName, int totalRegistros) throws SQLException {
        String sql = "INSERT INTO tb_imported_files (file_hash, file_name, total_registros) VALUES (?, ?, ?)";
        try (Connection conn = util.ConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, fileHash);
            pstmt.setString(2, fileName);
            pstmt.setInt(3, totalRegistros);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) { // Unique violation
                return false;
            } else {
                throw e;
            }
        }
    }
}
