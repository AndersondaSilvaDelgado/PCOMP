package br.com.usinasantafe.pcomp.model.bean.variaveis;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import br.com.usinasantafe.pcomp.model.pst.Entidade;

@DatabaseTable(tableName="tbinforplantiovar")
public class InfPlantioBean extends Entidade {

    @DatabaseField(generatedId=true)
    private Long idPlantio;
    @DatabaseField
    private String dthrPlantio;
    @DatabaseField
    private Double qtdeProdPlanej;
    @DatabaseField
    private Double qtdeProdReal;
    @DatabaseField
    private Double mediaProdPlanej;
    @DatabaseField
    private Double mediaProdReal;
    @DatabaseField
    private Double porcDispon;

    public InfPlantioBean() {
    }

    public Long getIdPlantio() {
        return idPlantio;
    }

    public void setIdPlantio(Long idPlantio) {
        this.idPlantio = idPlantio;
    }

    public String getDthrPlantio() {
        return dthrPlantio;
    }

    public void setDthrPlantio(String dthrPlantio) {
        this.dthrPlantio = dthrPlantio;
    }

    public Double getQtdeProdPlanej() {
        return qtdeProdPlanej;
    }

    public void setQtdeProdPlanej(Double qtdeProdPlanej) {
        this.qtdeProdPlanej = qtdeProdPlanej;
    }

    public Double getQtdeProdReal() {
        return qtdeProdReal;
    }

    public void setQtdeProdReal(Double qtdeProdReal) {
        this.qtdeProdReal = qtdeProdReal;
    }

    public Double getMediaProdPlanej() {
        return mediaProdPlanej;
    }

    public void setMediaProdPlanej(Double mediaProdPlanej) {
        this.mediaProdPlanej = mediaProdPlanej;
    }

    public Double getMediaProdReal() {
        return mediaProdReal;
    }

    public void setMediaProdReal(Double mediaProdReal) {
        this.mediaProdReal = mediaProdReal;
    }

    public Double getPorcDispon() {
        return porcDispon;
    }

    public void setPorcDispon(Double porcDispon) {
        this.porcDispon = porcDispon;
    }
}
