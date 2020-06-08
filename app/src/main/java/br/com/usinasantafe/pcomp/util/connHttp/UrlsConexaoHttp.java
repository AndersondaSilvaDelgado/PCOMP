package br.com.usinasantafe.pcomp.util.connHttp;

import br.com.usinasantafe.pcomp.PCOMPContext;

public class UrlsConexaoHttp {

	public static String urlPrincipal = "http://www.usinasantafe.com.br/pmmdev/view/";
	public static String urlPrincEnvio = "http://www.usinasantafe.com.br/pmmdev/view/";

	public static String localPSTEstatica = "br.com.usinasantafe.pcomp.model.bean.estaticas.";
	public static String localUrl = "br.com.usinasantafe.pcomp.util.connHttp.UrlsConexaoHttp";

	public static String put = "?versao=" + PCOMPContext.versaoAplic.replace(".", "_");

	public static String AtividadeBean = urlPrincipal + "atividade.php" + put;
	public static String FuncionarioBean = urlPrincipal + "funcionario.php" + put;
	public static String LeiraBean = urlPrincipal + "leira.php" + put;
	public static String MotoMecBean = urlPrincipal + "motomec.php" + put;
	public static String ProdutoBean = urlPrincipal + "produto.php" + put;
	public static String TurnoBean = urlPrincipal + "turno.php" + put;
	
	public UrlsConexaoHttp() {
		// TODO Auto-generated constructor stub
	}

	public String getsInsertCarreg() {
		return urlPrincEnvio + "inserircarreg.php" + put;
	}

	public String getsInsertChecklist() {
		return urlPrincEnvio + "inserirchecklist.php" + put;
	}

	public String getsInsertApontaMM() {
		return urlPrincEnvio + "inserirapontmm.php" + put;
	}

	public String getsInsertBolAbertoMM() {
		return urlPrincEnvio + "inserirbolabertomm.php" + put;
	}

	public String getsInsertBolFechadoMM() {
		return urlPrincEnvio + "inserirbolfechadomm.php" + put;
	}

	public String urlVerifica(String classe){
		String retorno = "";
		if(classe.equals("LeiraComposto")){
			retorno = urlPrincipal + "pesqleiracomp.php" + put;
		} else if(classe.equals("LeiraCarregProd")){
			retorno = urlPrincipal + "retleiracarregprod.php" + put;
		} else if(classe.equals("CarregComposto")){
			retorno = urlPrincipal + "retcarregcomp.php" + put;
		} else if(classe.equals("Equip")){
			retorno = urlPrincipal + "equip.php" + put;
		} else if (classe.equals("Atualiza")) {
			retorno = urlPrincEnvio + "atualaplicpcomp.php" + put;
		} else if (classe.equals("OS")) {
			retorno = urlPrincipal + "os.php" + put;
		}
		return retorno;
	}

}
