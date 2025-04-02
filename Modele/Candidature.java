package Modele;
import java.util.*

public class Candidature {
    private int id;
    private Date dateCandidature;
    private String Statut;
    private String commentaire;
    private ArrayList<String> lienDocumentAnnexs;

    public int getId() {
        return id;
    }
    public Date getDate() {
        return dateCandidature;
    }
    public String getStatut() {
        return Statut;
    }
    public String getCommentaire() {
        return commentaire;
    }
    public ArrayList<String> getLienDocumentAnnexs() {
        return lienDocumentAnnexs;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setDateCandidature(Date dateCandidature) {
        this.dateCandidature = dateCandidature;
    }
    public void setStatut(String Statut) {
        this.Statut = Statut;
    }
    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }
    public void setLienDocumentAnnexs(ArrayList<String> lienDocumentAnnexs) {
        this.lienDocumentAnnexs = lienDocumentAnnexs;
    }

}
