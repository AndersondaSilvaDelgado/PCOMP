package br.com.usinasantafe.pcomp.to.tb.estaticas;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import br.com.usinasantafe.pcomp.pst.Entidade;

/**
 * Created by anderson on 11/11/2016.
 */
@DatabaseTable(tableName="tbequipamentoest")
public class EquipamentoTO extends Entidade {

    private static final long serialVersionUID = 1L;

    @DatabaseField(id=true)
    private Long idEquipamento;
    @DatabaseField
    private Long nroEquipamento;
    @DatabaseField
    private Long tipoEquipamento;
    @DatabaseField
    private Long classifEquipamento;

    public EquipamentoTO() {
    }

    public Long getIdEquipamento() {
        return idEquipamento;
    }

    public void setIdEquipamento(Long idEquipamento) {
        this.idEquipamento = idEquipamento;
    }

    public Long getNroEquipamento() {
        return nroEquipamento;
    }

    public void setNroEquipamento(Long nroEquipamento) {
        this.nroEquipamento = nroEquipamento;
    }

    public Long getTipoEquipamento() {
        return tipoEquipamento;
    }

    public void setTipoEquipamento(Long tipoEquipamento) {
        this.tipoEquipamento = tipoEquipamento;
    }

    public Long getClassifEquipamento() {
        return classifEquipamento;
    }

    public void setClassifEquipamento(Long classifEquipamento) {
        this.classifEquipamento = classifEquipamento;
    }

}
