package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EmployeurDAOImpl implements EmployeurDAO {
    private String dbUrl;
    private String dbUser;
    private String dbPassword;

    public EmployeurDAOImpl(String dbUrl, String dbUser, String dbPassword) {
        this.dbUrl = dbUrl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }

    public List<String> executeQuery(String query) throws SQLException {
        List<String> results = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Construire l'en-tête
            StringBuilder header = new StringBuilder();
            for (int i = 1; i <= columnCount; i++) {
                header.append(String.format("%-20s", metaData.getColumnName(i)));
            }
            results.add(header.toString());

            // Ajouter les données
            while (rs.next()) {
                StringBuilder row = new StringBuilder();
                for (int i = 1; i <= columnCount; i++) {
                    row.append(String.format("%-20s", rs.getString(i)));
                }
                results.add(row.toString());
            }
        }

        return results;
    }

    public String buildQuery(Map<String, Boolean> selectedFilters) {
        StringBuilder query = new StringBuilder("SELECT * FROM clients WHERE 1=1");

        for (Map.Entry<String, Boolean> entry : selectedFilters.entrySet()) {
            if (entry.getValue()) {
                query.append(" AND ").append(entry.getKey());
            }
        }

        return query.toString();
    }
}
