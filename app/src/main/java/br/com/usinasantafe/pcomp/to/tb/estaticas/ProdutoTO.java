package br.com.usinasantafe.pcomp.to.tb.estaticas;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import br.com.usinasantafe.pcomp.pst.Entidade;

/**
 * Created by anderson on 11/11/2016.
 */
@DatabaseTable(tableName="tbprodutoest")
public class ProdutoTO extends Entidade {

    private static final long serialVersionUID = 1L;

    @DatabaseField(id=true)
    private Long idProduto;
    @DatabaseField
    private String codProduto;
    @DatabaseField
    private String descProduto;

    public ProdutoTO() {
    }

    public Long getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Long idProduto) {
        this.idProduto = idProduto;
    }

    public String getCodProduto() {
        return codProduto;
    }

    public void setCodProduto(String codProduto) {
        this.codProduto = codProduto;
    }

    public String getDescProduto() {
        return descProduto;
    }

    public void setDescProduto(String descProduto) {
        this.descProduto = descProduto;
    }

}
