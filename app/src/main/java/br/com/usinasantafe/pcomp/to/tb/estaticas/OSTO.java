package br.com.usinasantafe.pcomp.to.tb.estaticas;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import br.com.usinasantafe.pcomp.pst.Entidade;

/**
 * Created by anderson on 21/11/2016.
 */
@DatabaseTable(tableName="tbosest")
public class OSTO extends Entidade {

    private static final long serialVersionUID = 1L;

    @DatabaseField(id=true)
    private Long idOS;
    @DatabaseField
    private Long nroOS;
    @DatabaseField
    private Long tipoOS;
    @DatabaseField
    private Long ativOS;

    public OSTO() {
    }

    public Long getIdOS() {
        return idOS;
    }

    public void setIdOS(Long idOS) {
        this.idOS = idOS;
    }

    public Long getNroOS() {
        return nroOS;
    }

    public void setNroOS(Long nroOS) {
        this.nroOS = nroOS;
    }

    public Long getTipoOS() {
        return tipoOS;
    }

    public void setTipoOS(Long tipoOS) {
        this.tipoOS = tipoOS;
    }

    public Long getAtivOS() {
        return ativOS;
    }

    public void setAtivOS(Long ativOS) {
        this.ativOS = ativOS;
    }
}
