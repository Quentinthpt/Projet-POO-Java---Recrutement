package DAO;

import java.util.List;
import java.util.Map;

public interface OffreEmploiDAO {
    List<Map<String, Object>> findTopOffres();

    int countByAgence(int idAgence);
}
