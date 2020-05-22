package br.com.usinasantafe.pcomp.util.connHttp;

import br.com.usinasantafe.pcomp.PCOMPContext;

public class UrlsConexaoHttp {

	public static String urlPrincipal = "http://www.usinasantafe.com.br/pcompdev/view/";
	public static String urlPrincEnvio = "http://www.usinasantafe.com.br/pcompdev/view/";

	public static String localPSTEstatica = "br.com.usinasantafe.pcomp.to.tb.estaticas.";
	public static String localUrl = "br.com.usinasantafe.pcomp.conWEB.UrlsConexaoHttp";

	public static String put = "?versao=" + PCOMPContext.versaoAplic.replace(".", "_");

	public static String MotoristaTO = urlPrincipal + "motorista.php" + put;
	public static String ProdutoTO = urlPrincipal + "produto.php" + put;
	public static String OSTO = urlPrincipal + "os.php" + put;
	public static String OperMotoMecTO = urlPrincipal + "opermotomec.php" + put;
	public static String TurnoTO = urlPrincipal + "turno.php" + put;
	
	public UrlsConexaoHttp() {
		// TODO Auto-generated constructor stub
	}

	public String getsInsertMotoMec() {
		return urlPrincEnvio + "inserirmotomec.php" + put;
	}

	public String getsInserirCarreg() {
		return urlPrincEnvio + "inserircarreg.php" + put;
	}

	public String getsPesqLeiraProd() {
		return urlPrincipal + "pesqleiracomp.php" + put;
	}

	public String urlVerifica(String classe){
		String retorno = "";
		if(classe.equals("AtividadeOSTO")){
			retorno = urlPrincipal + "sVerifAtividadeOS";
		}
		else if(classe.equals("PesqBalancaProdBean")){
			retorno = urlPrincipal + "pesqleiraprod.php" + put;
		}
		else if(classe.equals("Equip")){
			retorno = urlPrincipal + "equip.php" + put;
		}
		return retorno;
	}

}
