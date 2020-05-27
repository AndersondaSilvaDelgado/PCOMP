package br.com.usinasantafe.pcomp.control;

import android.content.Context;

import br.com.usinasantafe.pcomp.model.dao.CarregDAO;
import br.com.usinasantafe.pcomp.model.dao.LeiraDAO;
import br.com.usinasantafe.pcomp.model.dao.OSDAO;

public class CompostoCTR {

    public CompostoCTR() {
    }

    public boolean pesqLeiraExibir(){
        LeiraDAO leiraDAO = new LeiraDAO();
        return leiraDAO.pesqLeiraExibir();
    }

    public void pesqLeiraComposto(Context telaAtual){
        LeiraDAO leiraDAO = new LeiraDAO();
        ConfigCTR configCTR = new ConfigCTR();
        leiraDAO.pesqLeiraComposto(configCTR.getConfig(), telaAtual);
    }

    public void apontCarregComposto(Context context){
        MotoMecCTR motoMecCTR = new MotoMecCTR();
        Long matricFunc = motoMecCTR.getFunc();
        OSDAO osDAO = new OSDAO();
        Long idOSAgric = osDAO.getOS().getIdOSAgric();
        CarregDAO carregDAO = new CarregDAO();
        carregDAO.apontCarregComposto(idOSAgric, matricFunc, context);
    }

    public boolean verCarreg(){
        CarregDAO carregDAO = new CarregDAO();
        return carregDAO.verCarreg();
    }

    public String dadosEnvioCarreg(){
        CarregDAO carregDAO = new CarregDAO();
        return carregDAO.dadosEnvioCarreg();
    }

}
