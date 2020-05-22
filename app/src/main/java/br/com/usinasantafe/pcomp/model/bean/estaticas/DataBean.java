package br.com.usinasantafe.pcomp.model.bean.estaticas;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import br.com.usinasantafe.pcomp.model.pst.Entidade;
/**
 * Created by anderson on 13/07/2017.
 */

@DatabaseTable(tableName="tbdata")
public class DataBean extends Entidade {

    private static final long serialVersionUID = 1L;

    @DatabaseField(id=true)
    private String data;

    public DataBean() {
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
