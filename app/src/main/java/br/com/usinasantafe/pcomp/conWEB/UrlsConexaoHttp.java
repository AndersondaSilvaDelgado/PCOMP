package br.com.usinasantafe.pcomp.conWEB;

public class UrlsConexaoHttp {

	private int tipoEnvio = 1;

	public static String datahorahttp = "http://www.usinasantafe.com.br/pcomp/datahora.php";

	public static String urlPrincipal = "http://www.usinasantafe.com.br/pcomp/";

	public static String urlPrincEnvio = "http://www.usinasantafe.com.br/pcomp/";
	//public static String urlPrincEnvio = "http://www.usinasantafe.com.br/pcompdesenv/";

	//public static String localPSTVariavel = "br.com.usinasantafe.pcomp.to.tb.variaveis.";
	public static String localPSTEstatica = "br.com.usinasantafe.pcomp.to.tb.estaticas.";
	public static String localUrl = "br.com.usinasantafe.pcomp.conWEB.UrlsConexaoHttp";
	
	public static String EquipamentoTO = urlPrincipal + "equipamento.php";
	public static String MotoristaTO = urlPrincipal + "motorista.php";
	public static String ProdutoTO = urlPrincipal + "produto.php";
	public static String OSTO = urlPrincipal + "os.php";
	public static String MotoMecTO = urlPrincipal + "motomec.php";
	public static String TurnoTO = urlPrincipal + "turno.php";
	
	public UrlsConexaoHttp() {
		// TODO Auto-generated constructor stub
	}

	public String getsInsertMotoMec() {
		if(tipoEnvio == 1)
			return urlPrincEnvio + "inserirmotomec.php";
		else
			return urlPrincEnvio + "inserirmotomec.php";
	}

	public String getsApontaCarreg() {
		if(tipoEnvio == 1)
			return urlPrincEnvio + "apontacarreg.php";
		else
			return urlPrincEnvio + "apontacarreg.php";
	}

	public String getsPesqBalProd() {
		if(tipoEnvio == 1)
			return urlPrincipal + "pesqbalancacomp.php";
		else
			return urlPrincipal + "pesqbalancacomp.php";
	}

	public String urlVerifica(String classe){
		String retorno = "";
		if(classe.equals("AtividadeOSTO")){
			retorno = urlPrincipal + "sVerifAtividadeOS";
		}
		else if(classe.equals("LiberacaoTO")){
			retorno = urlPrincipal + "sVerifLiberacao";
		}
		else if(classe.equals("PesqBalancaProdTO")){
			retorno = urlPrincipal + "pesqbalancaprod.php";
		}
		return retorno;
	}

}
