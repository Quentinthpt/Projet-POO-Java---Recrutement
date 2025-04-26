package DAO;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface EmployeurDAO {
    public List<String> executeQuery(String query) throws SQLException;
    public String buildQuery(Map<String, Boolean> selectedFilters);
}
