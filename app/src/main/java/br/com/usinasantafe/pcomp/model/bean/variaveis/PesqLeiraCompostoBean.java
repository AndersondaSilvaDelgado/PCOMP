package br.com.usinasantafe.pcomp.model.bean.variaveis;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import br.com.usinasantafe.pcomp.model.pst.Entidade;


/**
 * Created by anderson on 18/01/2017.
 */
@DatabaseTable(tableName="tbpesqbalcompvar")
public class PesqLeiraCompostoBean extends Entidade {

    private static final long serialVersionUID = 1L;

    @DatabaseField(id=true)
    private Long id;
    @DatabaseField
    private Long equip;
    @DatabaseField
    private Long os;
    @DatabaseField
    private Long idLeira;
    @DatabaseField
    private String cdLeira;

    public PesqLeiraCompostoBean() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEquip() {
        return equip;
    }

    public void setEquip(Long equip) {
        this.equip = equip;
    }

    public Long getOs() {
        return os;
    }

    public void setOs(Long os) {
        this.os = os;
    }

    public Long getIdLeira() {
        return idLeira;
    }

    public void setIdLeira(Long idLeira) {
        this.idLeira = idLeira;
    }

    public String getCdLeira() {
        return cdLeira;
    }

    public void setCdLeira(String cdLeira) {
        this.cdLeira = cdLeira;
    }

}
