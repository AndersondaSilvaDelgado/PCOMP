package br.com.usinasantafe.pcomp.to.tb.variaveis;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import br.com.usinasantafe.pcomp.pst.Entidade;

/**
 * Created by anderson on 16/11/2016.
 */
@DatabaseTable(tableName="tbapontacarregvar")
public class ApontCarregTO extends Entidade {

    private static final long serialVersionUID = 1L;

    @DatabaseField(id=true)
    private Long idApontCarreg;
    @DatabaseField
    private Long equipApontCarreg;
    @DatabaseField
    private Long motoApontCarreg;
    @DatabaseField
    private Long prodApontCarreg;
    @DatabaseField
    private String dataApontCarreg;
    @DatabaseField
    private Long tipoApontCarreg;
    @DatabaseField
    private Long osApontCarreg;
    @DatabaseField
    private Long leiraApontCarreg;

    public ApontCarregTO() {
    }

    public Long getIdApontCarreg() {
        return idApontCarreg;
    }

    public void setIdApontCarreg(Long idApontCarreg) {
        this.idApontCarreg = idApontCarreg;
    }

    public Long getEquipApontCarreg() {
        return equipApontCarreg;
    }

    public void setEquipApontCarreg(Long equipApontCarreg) {
        this.equipApontCarreg = equipApontCarreg;
    }

    public Long getMotoApontCarreg() {
        return motoApontCarreg;
    }

    public void setMotoApontCarreg(Long motoApontCarreg) {
        this.motoApontCarreg = motoApontCarreg;
    }

    public Long getProdApontCarreg() {
        return prodApontCarreg;
    }

    public void setProdApontCarreg(Long prodApontCarreg) {
        this.prodApontCarreg = prodApontCarreg;
    }

    public String getDataApontCarreg() {
        return dataApontCarreg;
    }

    public void setDataApontCarreg(String dataApontCarreg) {
        this.dataApontCarreg = dataApontCarreg;
    }

    public Long getTipoApontCarreg() {
        return tipoApontCarreg;
    }

    public void setTipoApontCarreg(Long tipoApontCarreg) {
        this.tipoApontCarreg = tipoApontCarreg;
    }

    public Long getOsApontCarreg() {
        return osApontCarreg;
    }

    public void setOsApontCarreg(Long osApontCarreg) {
        this.osApontCarreg = osApontCarreg;
    }

    public Long getLeiraApontCarreg() {
        return leiraApontCarreg;
    }

    public void setLeiraApontCarreg(Long leiraApontCarreg) {
        this.leiraApontCarreg = leiraApontCarreg;
    }

}
