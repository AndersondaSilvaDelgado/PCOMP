package br.com.usinasantafe.pcomp.model.bean.variaveis;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import br.com.usinasantafe.pcomp.model.pst.Entidade;

@DatabaseTable(tableName="tbitemmedpneuvar")
public class ItemPneuBean extends Entidade {

    private static final long serialVersionUID = 1L;

    @DatabaseField(generatedId=true)
    private Long idItemPneu;
    @DatabaseField
    private Long idCabecItemPneu;
    @DatabaseField
    private Long posItemPneu;
    @DatabaseField
    private String nroPneuItemPneu;
    @DatabaseField
    private Long pressaoEncItemPneu;
    @DatabaseField
    private Long pressaoColItemPneu;
    @DatabaseField
    private String dthrItemPneu;

    public ItemPneuBean() {
    }

    public Long getIdItemPneu() {
        return idItemPneu;
    }

    public void setIdItemPneu(Long idItemPneu) {
        this.idItemPneu = idItemPneu;
    }

    public Long getPosItemPneu() {
        return posItemPneu;
    }

    public void setPosItemPneu(Long posItemPneu) {
        this.posItemPneu = posItemPneu;
    }

    public String getNroPneuItemPneu() {
        return nroPneuItemPneu;
    }

    public void setNroPneuItemPneu(String nroPneuItemPneu) {
        this.nroPneuItemPneu = nroPneuItemPneu;
    }

    public Long getPressaoEncItemPneu() {
        return pressaoEncItemPneu;
    }

    public void setPressaoEncItemPneu(Long pressaoEncItemPneu) {
        this.pressaoEncItemPneu = pressaoEncItemPneu;
    }

    public Long getPressaoColItemPneu() {
        return pressaoColItemPneu;
    }

    public void setPressaoColItemPneu(Long pressaoColItemPneu) {
        this.pressaoColItemPneu = pressaoColItemPneu;
    }

    public Long getIdCabecItemPneu() {
        return idCabecItemPneu;
    }

    public void setIdCabecItemPneu(Long idCabecItemPneu) {
        this.idCabecItemPneu = idCabecItemPneu;
    }

    public String getDthrItemPneu() {
        return dthrItemPneu;
    }

    public void setDthrItemPneu(String dthrItemPneu) {
        this.dthrItemPneu = dthrItemPneu;
    }

}
