package br.com.usinasantafe.pcomp.to.tb.estaticas;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import br.com.usinasantafe.pcomp.pst.Entidade;

/**
 * Created by anderson on 11/11/2016.
 */
@DatabaseTable(tableName="tbmotoristaest")
public class MotoristaTO extends Entidade {

    private static final long serialVersionUID = 1L;

    @DatabaseField(id=true)
    private Long idMotorista;
    @DatabaseField
    private Long matricMotorista;
    @DatabaseField
    private String nomeMotorista;

    public MotoristaTO() {
    }

    public Long getIdMotorista() {
        return idMotorista;
    }

    public void setIdMotorista(Long idMotorista) {
        this.idMotorista = idMotorista;
    }

    public Long getMatricMotorista() {
        return matricMotorista;
    }

    public void setMatricMotorista(Long matricMotorista) {
        this.matricMotorista = matricMotorista;
    }

    public String getNomeMotorista() {
        return nomeMotorista;
    }

    public void setNomeMotorista(String nomeMotorista) {
        this.nomeMotorista = nomeMotorista;
    }

}
