package br.com.usinasantafe.pcomp.model.bean.variaveis;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import br.com.usinasantafe.pcomp.model.pst.Entidade;

/**
 * Created by anderson on 16/11/2016.
 */
@DatabaseTable(tableName="tbpesqbalprodvar")
public class PesqBalancaProdBean extends Entidade {

    private static final long serialVersionUID = 1L;

    @DatabaseField(id=true)
    private Long id;
    @DatabaseField
    private Long equip;
    @DatabaseField
    private String leira;

    public PesqBalancaProdBean() {
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

    public String getLeira() {
        return leira;
    }

    public void setLeira(String leira) {
        this.leira = leira;
    }
}
