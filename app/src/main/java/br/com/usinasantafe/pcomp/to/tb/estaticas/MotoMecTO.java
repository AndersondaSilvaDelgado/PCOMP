package br.com.usinasantafe.pcomp.to.tb.estaticas;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import br.com.usinasantafe.pcomp.pst.Entidade;

/**
 * Created by anderson on 17/11/2016.
 */
@DatabaseTable(tableName="tbmotomecest")
public class MotoMecTO extends Entidade {

    private static final long serialVersionUID = 1L;

    @DatabaseField(id=true)
    private Long codigoMotoMec;
    @DatabaseField
    private Long opcorMotoMec;
    @DatabaseField
    private String descMotoMec;
    @DatabaseField
    private Long posicaoMotoMec;
    @DatabaseField
    private Long tipoMotoMec;
    @DatabaseField
    private Long funcaoMotoMec;
    @DatabaseField
    private Long cargoMotoMec;

    public MotoMecTO() {
    }

    public Long getCodigoMotoMec() {
        return codigoMotoMec;
    }

    public void setCodigoMotoMec(Long codigoMotoMec) {
        this.codigoMotoMec = codigoMotoMec;
    }

    public Long getOpcorMotoMec() {
        return opcorMotoMec;
    }

    public void setOpcorMotoMec(Long opcorMotoMec) {
        this.opcorMotoMec = opcorMotoMec;
    }

    public String getDescMotoMec() {
        return descMotoMec;
    }

    public void setDescMotoMec(String descMotoMec) {
        this.descMotoMec = descMotoMec;
    }

    public Long getPosicaoMotoMec() {
        return posicaoMotoMec;
    }

    public void setPosicaoMotoMec(Long posicaoMotoMec) {
        this.posicaoMotoMec = posicaoMotoMec;
    }

    public Long getTipoMotoMec() {
        return tipoMotoMec;
    }

    public void setTipoMotoMec(Long tipoMotoMec) {
        this.tipoMotoMec = tipoMotoMec;
    }

    public Long getFuncaoMotoMec() {
        return funcaoMotoMec;
    }

    public void setFuncaoMotoMec(Long funcaoMotoMec) {
        this.funcaoMotoMec = funcaoMotoMec;
    }

    public Long getCargoMotoMec() {
        return cargoMotoMec;
    }

    public void setCargoMotoMec(Long cargoMotoMec) {
        this.cargoMotoMec = cargoMotoMec;
    }

}
