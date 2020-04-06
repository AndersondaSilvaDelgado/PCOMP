package br.com.usinasantafe.pcomp.model.bean.variaveis;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import br.com.usinasantafe.pcomp.model.pst.Entidade;

@DatabaseTable(tableName="tbcabecpneuvar")
public class CabecPneuBean extends Entidade {

    private static final long serialVersionUID = 1L;

    @DatabaseField(generatedId=true)
    private Long idCabecPneu;
    @DatabaseField
    private Long idApontCabecPneu;
    @DatabaseField
    private Long funcCabecPneu;
    @DatabaseField
    private Long equipCabecPneu;
    @DatabaseField
    private String dthrCabecPneu;
    @DatabaseField
    private Long statusCabecPneu;  //1 - Aberto; 2 - Encerrado

    public CabecPneuBean() {
    }

    public Long getIdCabecPneu() {
        return idCabecPneu;
    }

    public void setIdCabecPneu(Long idCabecPneu) {
        this.idCabecPneu = idCabecPneu;
    }

    public Long getFuncCabecPneu() {
        return funcCabecPneu;
    }

    public void setFuncCabecPneu(Long funcCabecPneu) {
        this.funcCabecPneu = funcCabecPneu;
    }

    public Long getEquipCabecPneu() {
        return equipCabecPneu;
    }

    public void setEquipCabecPneu(Long equipCabecPneu) {
        this.equipCabecPneu = equipCabecPneu;
    }

    public String getDthrCabecPneu() {
        return dthrCabecPneu;
    }

    public void setDthrCabecPneu(String dthrCabecPneu) {
        this.dthrCabecPneu = dthrCabecPneu;
    }

    public Long getStatusCabecPneu() {
        return statusCabecPneu;
    }

    public void setStatusCabecPneu(Long statusCabecPneu) {
        this.statusCabecPneu = statusCabecPneu;
    }

    public Long getIdApontCabecPneu() {
        return idApontCabecPneu;
    }

    public void setIdApontCabecPneu(Long idApontCabecPneu) {
        this.idApontCabecPneu = idApontCabecPneu;
    }

}
