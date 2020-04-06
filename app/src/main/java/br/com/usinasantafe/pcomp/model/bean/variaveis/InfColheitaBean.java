package br.com.usinasantafe.pcomp.model.bean.variaveis;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import br.com.usinasantafe.pcomp.model.pst.Entidade;

@DatabaseTable(tableName="tbinforcolhvar")
public class InfColheitaBean extends Entidade {

    @DatabaseField(generatedId=true)
    private Long idPerda;
    @DatabaseField
    private String dthrPerda;
    @DatabaseField
    private Double toletePerda;
    @DatabaseField
    private Double lascaPerda;
    @DatabaseField
    private Double tocoPerda;
    @DatabaseField
    private Double ponteiroPerda;
    @DatabaseField
    private Double canaInteiraPerda;
    @DatabaseField
    private Double pedacoPerda;
    @DatabaseField
    private Double repiquePerda;
    @DatabaseField
    private Double soqueiraPerda;
    @DatabaseField
    private Double nroSoqueiraPerda;
    @DatabaseField
    private Double totalPerda;

    public InfColheitaBean() {
    }

    public Long getIdPerda() {
        return idPerda;
    }

    public void setIdPerda(Long idPerda) {
        this.idPerda = idPerda;
    }

    public String getDthrPerda() {
        return dthrPerda;
    }

    public void setDthrPerda(String dthrPerda) {
        this.dthrPerda = dthrPerda;
    }

    public Double getToletePerda() {
        return toletePerda;
    }

    public void setToletePerda(Double toletePerda) {
        this.toletePerda = toletePerda;
    }

    public Double getLascaPerda() {
        return lascaPerda;
    }

    public void setLascaPerda(Double lascaPerda) {
        this.lascaPerda = lascaPerda;
    }

    public Double getTocoPerda() {
        return tocoPerda;
    }

    public void setTocoPerda(Double tocoPerda) {
        this.tocoPerda = tocoPerda;
    }

    public Double getCanaInteiraPerda() {
        return canaInteiraPerda;
    }

    public void setCanaInteiraPerda(Double canaInteiraPerda) {
        this.canaInteiraPerda = canaInteiraPerda;
    }

    public Double getPedacoPerda() {
        return pedacoPerda;
    }

    public void setPedacoPerda(Double pedacoPerda) {
        this.pedacoPerda = pedacoPerda;
    }

    public Double getRepiquePerda() {
        return repiquePerda;
    }

    public void setRepiquePerda(Double repiquePerda) {
        this.repiquePerda = repiquePerda;
    }

    public Double getSoqueiraPerda() {
        return soqueiraPerda;
    }

    public void setSoqueiraPerda(Double soqueiraPerda) {
        this.soqueiraPerda = soqueiraPerda;
    }

    public Double getNroSoqueiraPerda() {
        return nroSoqueiraPerda;
    }

    public void setNroSoqueiraPerda(Double nroSoqueiraPerda) {
        this.nroSoqueiraPerda = nroSoqueiraPerda;
    }

    public Double getTotalPerda() {
        return totalPerda;
    }

    public void setTotalPerda(Double totalPerda) {
        this.totalPerda = totalPerda;
    }

    public Double getPonteiroPerda() {
        return ponteiroPerda;
    }

    public void setPonteiroPerda(Double ponteiroPerda) {
        this.ponteiroPerda = ponteiroPerda;
    }
}
