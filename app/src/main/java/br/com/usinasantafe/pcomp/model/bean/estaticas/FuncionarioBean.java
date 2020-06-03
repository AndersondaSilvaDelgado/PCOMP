package br.com.usinasantafe.pcomp.model.bean.estaticas;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import br.com.usinasantafe.pcomp.model.pst.Entidade;

/**
 * Created by anderson on 11/11/2016.
 */
@DatabaseTable(tableName="tbfuncionarioest")
public class FuncionarioBean extends Entidade {

    private static final long serialVersionUID = 1L;

    @DatabaseField(id=true)
    private Long matricFunc;
    @DatabaseField
    private String nomeFunc;

    public FuncionarioBean() {
    }

    public Long getMatricFunc() {
        return matricFunc;
    }

    public void setMatricFunc(Long matricFunc) {
        this.matricFunc = matricFunc;
    }

    public String getNomeFunc() {
        return nomeFunc;
    }

    public void setNomeFunc(String nomeFunc) {
        this.nomeFunc = nomeFunc;
    }
}
