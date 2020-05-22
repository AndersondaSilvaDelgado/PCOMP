package br.com.usinasantafe.pcomp.model.bean.variaveis;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import br.com.usinasantafe.pcomp.pst.Entidade;

@DatabaseTable(tableName="tbleiraordcarregvar")
public class LeiraOrdCarregBean extends Entidade {

    private static final long serialVersionUID = 1L;

    @DatabaseField(id=true)
    private Long idLeiraOrdCarreg;
    @DatabaseField
    private Long equipLeiraOrdCarreg;
    @DatabaseField
    private Long osLeiraOrdCarreg;
    @DatabaseField
    private Long idLeira;
    @DatabaseField
    private String codLeira;
    @DatabaseField
    private Long idOrdCarreg;
    @DatabaseField
    private Long pesoEntradaOrdCarreg;
    @DatabaseField
    private Long pesoSaidaOrdCarreg;
    @DatabaseField
    private Long pesoLiquidoOrdCarreg;

    public LeiraOrdCarregBean() {
    }

    public Long getIdLeiraOrdCarreg() {
        return idLeiraOrdCarreg;
    }

    public void setIdLeiraOrdCarreg(Long idLeiraOrdCarreg) {
        this.idLeiraOrdCarreg = idLeiraOrdCarreg;
    }

    public Long getEquipLeiraOrdCarreg() {
        return equipLeiraOrdCarreg;
    }

    public void setEquipLeiraOrdCarreg(Long equipLeiraOrdCarreg) {
        this.equipLeiraOrdCarreg = equipLeiraOrdCarreg;
    }

    public Long getOsLeiraOrdCarreg() {
        return osLeiraOrdCarreg;
    }

    public void setOsLeiraOrdCarreg(Long osLeiraOrdCarreg) {
        this.osLeiraOrdCarreg = osLeiraOrdCarreg;
    }

    public Long getIdLeira() {
        return idLeira;
    }

    public void setIdLeira(Long idLeira) {
        this.idLeira = idLeira;
    }

    public String getCodLeira() {
        return codLeira;
    }

    public void setCodLeira(String codLeira) {
        this.codLeira = codLeira;
    }

    public Long getIdOrdCarreg() {
        return idOrdCarreg;
    }

    public void setIdOrdCarreg(Long idOrdCarreg) {
        this.idOrdCarreg = idOrdCarreg;
    }

    public Long getPesoEntradaOrdCarreg() {
        return pesoEntradaOrdCarreg;
    }

    public void setPesoEntradaOrdCarreg(Long pesoEntradaOrdCarreg) {
        this.pesoEntradaOrdCarreg = pesoEntradaOrdCarreg;
    }

    public Long getPesoSaidaOrdCarreg() {
        return pesoSaidaOrdCarreg;
    }

    public void setPesoSaidaOrdCarreg(Long pesoSaidaOrdCarreg) {
        this.pesoSaidaOrdCarreg = pesoSaidaOrdCarreg;
    }

    public Long getPesoLiquidoOrdCarreg() {
        return pesoLiquidoOrdCarreg;
    }

    public void setPesoLiquidoOrdCarreg(Long pesoLiquidoOrdCarreg) {
        this.pesoLiquidoOrdCarreg = pesoLiquidoOrdCarreg;
    }
}
