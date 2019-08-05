package br.com.usinasantafe.pcomp.to.tb.variaveis;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import br.com.usinasantafe.pcomp.pst.Entidade;

/**
 * Created by anderson on 11/11/2016.
 */
@DatabaseTable(tableName="tbconfigvar")
public class ConfiguracaoTO extends Entidade {

    private static final long serialVersionUID = 1L;

    @DatabaseField(id=true)
    private Long equipConfig;
    @DatabaseField
    private String senhaConfig;
    @DatabaseField
    private Long statusApontConfig;
    @DatabaseField
    private Long osConfig;

    public ConfiguracaoTO() {
        // TODO Auto-generated constructor stub
    }

    public Long getEquipConfig() {
        return equipConfig;
    }

    public void setEquipConfig(Long camconfig) {
        this.equipConfig = camconfig;
    }

    public String getSenhaConfig() {
        return senhaConfig;
    }

    public void setSenhaConfig(String senhaconfig) {
        this.senhaConfig = senhaconfig;
    }

    public Long getStatusApontConfig() {
        return statusApontConfig;
    }

    public void setStatusApontConfig(Long statusApontConfig) {
        this.statusApontConfig = statusApontConfig;
    }

    public Long getOsConfig() {
        return osConfig;
    }

    public void setOsConfig(Long osConfig) {
        this.osConfig = osConfig;
    }
}
