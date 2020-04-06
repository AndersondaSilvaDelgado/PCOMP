package br.com.usinasantafe.pcomp.model.bean.variaveis;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import br.com.usinasantafe.pcomp.model.pst.Entidade;

@DatabaseTable(tableName="tbboletimvar")
public class BoletimMMBean extends Entidade {

	private static final long serialVersionUID = 1L;

	@DatabaseField(generatedId=true)
	private Long idBolMM;
	@DatabaseField
	private Long idExtBolMM;
	@DatabaseField
    private Long matricFuncBolMM;
	@DatabaseField
	private Long idEquipBolMM;
	@DatabaseField
    private Long idTurnoBolMM;
	@DatabaseField
    private Double hodometroInicialBolMM;
	@DatabaseField
	private Double hodometroFinalBolMM;
	@DatabaseField
	private Long osBolMM;
	@DatabaseField
	private Long ativPrincBolMM;
	@DatabaseField
	private String dthrInicialBolMM;
	@DatabaseField
	private String dthrFinalBolMM;
	@DatabaseField
	private Long statusBolMM;  //0 - Esta apontando os implementos; 1 - Aberto; 2 - Encerrado
	@DatabaseField
	private Long statusConBolMM;  //0 - OffLine; 1 - OnLine
	@DatabaseField
	private Long statusDtHrInicialBolMM;  //0 - Data Digitada; 1 - Data do Celular;
	@DatabaseField
	private Long statusDtHrFinalBolMM;  //0 - Data Digitada; 1 - Data do Celular;
	@DatabaseField
	private Long qtdeApontBolMM;
	@DatabaseField
	private Double longitudeBolMM;
	@DatabaseField
	private Double latitudeBolMM;

    public BoletimMMBean() {
	}

	public Long getIdBolMM() {
		return idBolMM;
	}

	public Long getIdExtBolMM() {
		return idExtBolMM;
	}

	public void setIdExtBolMM(Long idExtBolMM) {
		this.idExtBolMM = idExtBolMM;
	}

	public Long getMatricFuncBolMM() {
		return matricFuncBolMM;
	}

	public void setMatricFuncBolMM(Long matricFuncBolMM) {
		this.matricFuncBolMM = matricFuncBolMM;
	}

	public Long getIdEquipBolMM() {
		return idEquipBolMM;
	}

	public void setIdEquipBolMM(Long idEquipBolMM) {
		this.idEquipBolMM = idEquipBolMM;
	}

	public Long getIdTurnoBolMM() {
		return idTurnoBolMM;
	}

	public void setIdTurnoBolMM(Long idTurnoBolMM) {
		this.idTurnoBolMM = idTurnoBolMM;
	}

	public Double getHodometroInicialBolMM() {
		return hodometroInicialBolMM;
	}

	public void setHodometroInicialBolMM(Double hodometroInicialBolMM) {
		this.hodometroInicialBolMM = hodometroInicialBolMM;
	}

	public Double getHodometroFinalBolMM() {
		return hodometroFinalBolMM;
	}

	public void setHodometroFinalBolMM(Double hodometroFinalBolMM) {
		this.hodometroFinalBolMM = hodometroFinalBolMM;
	}

	public Long getOsBolMM() {
		return osBolMM;
	}

	public void setOsBolMM(Long osBolMM) {
		this.osBolMM = osBolMM;
	}

	public Long getAtivPrincBolMM() {
		return ativPrincBolMM;
	}

	public void setAtivPrincBolMM(Long ativPrincBolMM) {
		this.ativPrincBolMM = ativPrincBolMM;
	}

	public String getDthrInicialBolMM() {
		return dthrInicialBolMM;
	}

	public void setDthrInicialBolMM(String dthrInicialBolMM) {
		this.dthrInicialBolMM = dthrInicialBolMM;
	}

	public Long getStatusBolMM() {
		return statusBolMM;
	}

	public void setStatusBolMM(Long statusBolMM) {
		this.statusBolMM = statusBolMM;
	}

	public String getDthrFinalBolMM() {
		return dthrFinalBolMM;
	}

	public void setDthrFinalBolMM(String dthrFinalBolMM) {
		this.dthrFinalBolMM = dthrFinalBolMM;
	}

	public Long getStatusConBolMM() {
		return statusConBolMM;
	}

	public void setStatusConBolMM(Long statusConBolMM) {
		this.statusConBolMM = statusConBolMM;
	}

	public Long getQtdeApontBolMM() {
		return qtdeApontBolMM;
	}

	public void setQtdeApontBolMM(Long qtdeApontBolMM) {
		this.qtdeApontBolMM = qtdeApontBolMM;
	}

	public Double getLongitudeBolMM() {
		return longitudeBolMM;
	}

	public void setLongitudeBolMM(Double longitudeBolMM) {
		this.longitudeBolMM = longitudeBolMM;
	}

	public Double getLatitudeBolMM() {
		return latitudeBolMM;
	}

	public void setLatitudeBolMM(Double latitudeBolMM) {
		this.latitudeBolMM = latitudeBolMM;
	}

	public Long getStatusDtHrInicialBolMM() {
		return statusDtHrInicialBolMM;
	}

	public void setStatusDtHrInicialBolMM(Long statusDtHrInicialBolMM) {
		this.statusDtHrInicialBolMM = statusDtHrInicialBolMM;
	}

	public Long getStatusDtHrFinalBolMM() {
		return statusDtHrFinalBolMM;
	}

	public void setStatusDtHrFinalBolMM(Long statusDtHrFinalBolMM) {
		this.statusDtHrFinalBolMM = statusDtHrFinalBolMM;
	}
}
