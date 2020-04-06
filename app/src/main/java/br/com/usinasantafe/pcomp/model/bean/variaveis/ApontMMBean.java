package br.com.usinasantafe.pcomp.model.bean.variaveis;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import br.com.usinasantafe.pcomp.model.pst.Entidade;

@DatabaseTable(tableName="tbapontmmvar")
public class ApontMMBean extends Entidade  {

	private static final long serialVersionUID = 1L;

	@DatabaseField(generatedId=true)
	private Long idApontMM;
	@DatabaseField
	private Long idBolApontMM;
	@DatabaseField
	private Long idExtBolApontMM;
	@DatabaseField
	private Long osApontMM;
	@DatabaseField
	private Long ativApontMM;
	@DatabaseField
	private Long paradaApontMM;
	@DatabaseField
	private Long transbApontMM;
	@DatabaseField
	private String dthrApontMM;
	@DatabaseField
	private Double latitudeApontMM;
	@DatabaseField
	private Double longitudeApontMM;
	@DatabaseField
	private Long statusConApontMM;  //0 - OffLine; 1 - OnLine
	@DatabaseField
	private Long statusApontMM;  //0 - Aberto; 1 - Enviar; 2 - Enviado
	@DatabaseField
	private Long statusDtHrApontMM;  //0 - Data Digitada; 1 - Data do Celular;

	public ApontMMBean() {
	}

	public Long getIdApontMM() {
		return idApontMM;
	}

	public Long getIdBolApontMM() {
		return idBolApontMM;
	}

	public void setIdBolApontMM(Long idBolApontMM) {
		this.idBolApontMM = idBolApontMM;
	}

	public Long getIdExtBolApontMM() {
		return idExtBolApontMM;
	}

	public void setIdExtBolApontMM(Long idExtBolApontMM) {
		this.idExtBolApontMM = idExtBolApontMM;
	}

	public Long getOsApontMM() {
		return osApontMM;
	}

	public void setOsApontMM(Long osApontMM) {
		this.osApontMM = osApontMM;
	}

	public Long getAtivApontMM() {
		return ativApontMM;
	}

	public void setAtivApontMM(Long ativApontMM) {
		this.ativApontMM = ativApontMM;
	}

	public Long getParadaApontMM() {
		return paradaApontMM;
	}

	public void setParadaApontMM(Long paradaApontMM) {
		this.paradaApontMM = paradaApontMM;
	}

	public String getDthrApontMM() {
		return dthrApontMM;
	}

	public void setDthrApontMM(String dthrApontMM) {
		this.dthrApontMM = dthrApontMM;
	}

	public Long getTransbApontMM() {
		return transbApontMM;
	}

	public void setTransbApontMM(Long transbApontMM) {
		this.transbApontMM = transbApontMM;
	}

	public Double getLatitudeApontMM() {
		return latitudeApontMM;
	}

	public void setLatitudeApontMM(Double latitudeApontMM) {
		this.latitudeApontMM = latitudeApontMM;
	}

	public Double getLongitudeApontMM() {
		return longitudeApontMM;
	}

	public void setLongitudeApontMM(Double longitudeApontMM) {
		this.longitudeApontMM = longitudeApontMM;
	}

	public Long getStatusConApontMM() {
		return statusConApontMM;
	}

	public void setStatusConApontMM(Long statusConApontMM) {
		this.statusConApontMM = statusConApontMM;
	}

	public Long getStatusApontMM() {
		return statusApontMM;
	}

	public void setStatusApontMM(Long statusApontMM) {
		this.statusApontMM = statusApontMM;
	}

	public Long getStatusDtHrApontMM() {
		return statusDtHrApontMM;
	}

	public void setStatusDtHrApontMM(Long statusDtHrApontMM) {
		this.statusDtHrApontMM = statusDtHrApontMM;
	}
}
