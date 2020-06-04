package br.com.usinasantafe.pcomp.model.bean.estaticas;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import br.com.usinasantafe.pcomp.model.pst.Entidade;

@DatabaseTable(tableName="tbleiraest")
public class LeiraBean extends Entidade {

    private static final long serialVersionUID = 1L;

    @DatabaseField(id=true)
    private Long idLeira;
    @DatabaseField
    private Long codLeira;

    public LeiraBean() {
    }

    public Long getIdLeira() {
        return idLeira;
    }

    public void setIdLeira(Long idLeira) {
        this.idLeira = idLeira;
    }

    public Long getCodLeira() {
        return codLeira;
    }

    public void setCodLeira(Long codLeira) {
        this.codLeira = codLeira;
    }

}
