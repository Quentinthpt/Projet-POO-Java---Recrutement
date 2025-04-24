package DAO;

import Modele.DemandeurEmploi;

public interface DemandeurEmploiDAO {
    public DemandeurEmploi getDemandeurByEmail(String email);
}
