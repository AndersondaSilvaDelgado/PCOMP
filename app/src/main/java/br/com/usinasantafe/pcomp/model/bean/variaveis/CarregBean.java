package br.com.usinasantafe.pcomp.model.bean.variaveis;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import br.com.usinasantafe.pcomp.model.pst.Entidade;


/**
 * Created by anderson on 16/11/2016.
 */
@DatabaseTable(tableName="tbapontacarregvar")
public class CarregBean extends Entidade {

    private static final long serialVersionUID = 1L;

    @DatabaseField(id=true)
    private Long idCarreg;
    @DatabaseField
    private Long equipCarreg;
    @DatabaseField
    private Long motoCarreg;
    @DatabaseField
    private Long prodCarreg;
    @DatabaseField
    private String dataCarreg;
    @DatabaseField
    private Long tipoCarreg; //1 - INSUMO; 2 - COMPOSTO;
    @DatabaseField
    private Long osCarreg;
    @DatabaseField
    private Long leiraCarreg;
    @DatabaseField
    private Long statusCarreg; //1 - Aberto; 2 - Fechado; 3 - Enviado

    public CarregBean() {
    }

    public Long getIdCarreg() {
        return idCarreg;
    }

    public void setIdCarreg(Long idCarreg) {
        this.idCarreg = idCarreg;
    }

    public Long getEquipCarreg() {
        return equipCarreg;
    }

    public void setEquipCarreg(Long equipCarreg) {
        this.equipCarreg = equipCarreg;
    }

    public Long getMotoCarreg() {
        return motoCarreg;
    }

    public void setMotoCarreg(Long motoCarreg) {
        this.motoCarreg = motoCarreg;
    }

    public Long getProdCarreg() {
        return prodCarreg;
    }

    public void setProdCarreg(Long prodCarreg) {
        this.prodCarreg = prodCarreg;
    }

    public String getDataCarreg() {
        return dataCarreg;
    }

    public void setDataCarreg(String dataCarreg) {
        this.dataCarreg = dataCarreg;
    }

    public Long getTipoCarreg() {
        return tipoCarreg;
    }

    public void setTipoCarreg(Long tipoCarreg) {
        this.tipoCarreg = tipoCarreg;
    }

    public Long getOsCarreg() {
        return osCarreg;
    }

    public void setOsCarreg(Long osCarreg) {
        this.osCarreg = osCarreg;
    }

    public Long getLeiraCarreg() {
        return leiraCarreg;
    }

    public void setLeiraCarreg(Long leiraCarreg) {
        this.leiraCarreg = leiraCarreg;
    }

    public Long getStatusCarreg() {
        return statusCarreg;
    }

    public void setStatusCarreg(Long statusCarreg) {
        this.statusCarreg = statusCarreg;
    }
}
