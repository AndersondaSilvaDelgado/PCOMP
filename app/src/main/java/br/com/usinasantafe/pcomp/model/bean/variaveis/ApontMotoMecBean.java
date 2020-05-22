package br.com.usinasantafe.pcomp.model.bean.variaveis;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import br.com.usinasantafe.pcomp.model.pst.Entidade;


@DatabaseTable(tableName="tbmotomecvar")
public class ApontMotoMecBean extends Entidade {

	private static final long serialVersionUID = 1L;
	
	@DatabaseField(id=true)
	private Long id;
	@DatabaseField
	private Long veic;
	@DatabaseField
	private Long motorista;
	@DatabaseField
	private Long opcor;
	@DatabaseField
	private String dihi;
	@DatabaseField
	private Long caux;

	
	public ApontMotoMecBean() {
		// TODO Auto-generated constructor stub
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getVeic() {
		return veic;
	}

	public void setVeic(Long veic) {
		this.veic = veic;
	}

	public Long getMotorista() {
		return motorista;
	}

	public void setMotorista(Long motorista) {
		this.motorista = motorista;
	}

	public Long getOpcor() {
		return opcor;
	}

	public void setOpcor(Long opcor) {
		this.opcor = opcor;
	}

	public String getDihi() {
		return dihi;
	}

	public void setDihi(String dihi) {
		this.dihi = dihi;
	}

	public Long getCaux() {
		return caux;
	}

	public void setCaux(Long caux) {
		this.caux = caux;
	}

}
