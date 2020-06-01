package br.com.usinasantafe.pcomp.control;

import android.content.Context;

import br.com.usinasantafe.pcomp.model.bean.estaticas.ProdutoBean;
import br.com.usinasantafe.pcomp.model.dao.CarregDAO;
import br.com.usinasantafe.pcomp.model.dao.LeiraDAO;
import br.com.usinasantafe.pcomp.model.dao.OSDAO;
import br.com.usinasantafe.pcomp.model.dao.ProdutoDAO;

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

    public void apontCarreg(Context context){
        MotoMecCTR motoMecCTR = new MotoMecCTR();
        OSDAO osDAO = new OSDAO();
        ConfigCTR configCTR = new ConfigCTR();
        CarregDAO carregDAO = new CarregDAO();
        carregDAO.apontCarreg(osDAO.getOS().getIdOS(), motoMecCTR.getFunc(), configCTR.getEquip().getIdEquip(), context);
    }

    public void apontCarreg(Context context, ProdutoBean produtoBean){
        MotoMecCTR motoMecCTR = new MotoMecCTR();
        OSDAO osDAO = new OSDAO();
        ConfigCTR configCTR = new ConfigCTR();
        CarregDAO carregDAO = new CarregDAO();
        carregDAO.apontCarreg(osDAO.getOS().getIdOS(), motoMecCTR.getFunc(), configCTR.getEquip().getIdEquip(), produtoBean.getIdProduto(), context);
    }

    public boolean verCarreg(){
        CarregDAO carregDAO = new CarregDAO();
        return carregDAO.verCarreg();
    }

    public String dadosEnvioCarreg(){
        CarregDAO carregDAO = new CarregDAO();
        return carregDAO.dadosEnvioCarreg();
    }

    public boolean verProduto(String codProduto){
        ProdutoDAO produtoDAO = new ProdutoDAO();
        return produtoDAO.verProduto(codProduto);
    }

    public ProdutoBean getProduto(String codProduto){
        ProdutoDAO produtoDAO = new ProdutoDAO();
        return produtoDAO.getProduto(codProduto);
    }

    public void pesqOrdCarreg(){
        
    }

}
