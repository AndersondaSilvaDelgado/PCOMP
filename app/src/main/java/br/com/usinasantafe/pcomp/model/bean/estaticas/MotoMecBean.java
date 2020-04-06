/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.usinasantafe.pcomp.model.bean.estaticas;

/**
 *
 * @author anderson
 */

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import br.com.usinasantafe.pcomp.model.pst.Entidade;

@DatabaseTable(tableName="tbmotomecest")
public class MotoMecBean extends Entidade {
	
	private static final long serialVersionUID = 1L;
	
	@DatabaseField(id=true)
    private Long idMotoMec;
	@DatabaseField
	private Long idOperMotoMec;
	@DatabaseField
	private String descrOperMotoMec;
	@DatabaseField
	private Long codFuncaoOperMotoMec;
	@DatabaseField
	private Long posOperMotoMec;
	@DatabaseField
	private Long tipoOperMotoMec;
	@DatabaseField
	private Long aplicOperMotoMec;
	@DatabaseField
	private Long funcaoOperMotoMec; //1 - Atividade; 2 - Parada

    public MotoMecBean() {
    }

	public Long getIdMotoMec() {
		return idMotoMec;
	}

	public void setIdMotoMec(Long idMotoMec) {
		this.idMotoMec = idMotoMec;
	}

	public Long getIdOperMotoMec() {
		return idOperMotoMec;
	}

	public void setIdOperMotoMec(Long idOperMotoMec) {
		this.idOperMotoMec = idOperMotoMec;
	}

	public String getDescrOperMotoMec() {
		return descrOperMotoMec;
	}

	public void setDescrOperMotoMec(String descrOperMotoMec) {
		this.descrOperMotoMec = descrOperMotoMec;
	}

	public Long getCodFuncaoOperMotoMec() {
		return codFuncaoOperMotoMec;
	}

	public void setCodFuncaoOperMotoMec(Long codFuncaoOperMotoMec) {
		this.codFuncaoOperMotoMec = codFuncaoOperMotoMec;
	}

	public Long getPosOperMotoMec() {
		return posOperMotoMec;
	}

	public void setPosOperMotoMec(Long posOperMotoMec) {
		this.posOperMotoMec = posOperMotoMec;
	}

	public Long getTipoOperMotoMec() {
		return tipoOperMotoMec;
	}

	public void setTipoOperMotoMec(Long tipoOperMotoMec) {
		this.tipoOperMotoMec = tipoOperMotoMec;
	}

	public Long getAplicOperMotoMec() {
		return aplicOperMotoMec;
	}

	public void setAplicOperMotoMec(Long aplicOperMotoMec) {
		this.aplicOperMotoMec = aplicOperMotoMec;
	}

	public Long getFuncaoOperMotoMec() {
		return funcaoOperMotoMec;
	}

	public void setFuncaoOperMotoMec(Long funcaoOperMotoMec) {
		this.funcaoOperMotoMec = funcaoOperMotoMec;
	}
}
