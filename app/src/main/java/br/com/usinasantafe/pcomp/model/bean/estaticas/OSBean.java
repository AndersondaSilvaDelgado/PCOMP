package br.com.usinasantafe.pcomp.model.bean.estaticas;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import br.com.usinasantafe.pcomp.model.pst.Entidade;

/**
 * Created by anderson on 21/11/2016.
 */
@DatabaseTable(tableName="tbosest")
public class OSBean extends Entidade {

    private static final long serialVersionUID = 1L;

    @DatabaseField(id=true)
    private Long idAtivOS;
    @DatabaseField
    private Long nroOS;
    @DatabaseField
    private Long idAtiv;

    public OSBean() {
    }

    public Long getIdAtivOS() {
        return idAtivOS;
    }

    public void setIdAtivOS(Long idAtivOS) {
        this.idAtivOS = idAtivOS;
    }

    public Long getNroOS() {
        return nroOS;
    }

    public void setNroOS(Long nroOS) {
        this.nroOS = nroOS;
    }

    public Long getIdAtiv() {
        return idAtiv;
    }

    public void setIdAtiv(Long idAtiv) {
        this.idAtiv = idAtiv;
    }
}
