package br.com.usinasantafe.pcomp.control;

import android.content.Context;

import br.com.usinasantafe.pcomp.model.bean.estaticas.LeiraBean;
import br.com.usinasantafe.pcomp.model.bean.estaticas.ProdutoBean;
import br.com.usinasantafe.pcomp.model.bean.variaveis.CarregBean;
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
        CarregDAO carregDAO = new CarregDAO();
        ConfigCTR configCTR = new ConfigCTR();
        carregDAO.pesqLeiraComposto(configCTR.getConfig(), configCTR.getOS(), telaAtual);
    }

    public void apontCarreg(Context context){
        MotoMecCTR motoMecCTR = new MotoMecCTR();
        CarregDAO carregDAO = new CarregDAO();
        carregDAO.apontCarreg(motoMecCTR.getFunc(), context);
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

    public CarregBean getCarregAberto(){
        CarregDAO carregDAO = new CarregDAO();
        return carregDAO.getCarregAberto();
    }

    public LeiraBean getLeira(Long idLeira){
        LeiraDAO leiraDAO = new LeiraDAO();
        return leiraDAO.getLeira(idLeira);
    }

    public void pesqCarregComposto(Context telaAtual){
        CarregDAO carregDAO = new CarregDAO();
        ConfigCTR configCTR = new ConfigCTR();
        carregDAO.pesqCarregComposto(configCTR.getConfig(), telaAtual);
    }

    public void recCarregComposto(String result) {
        CarregDAO carregDAO = new CarregDAO();
        carregDAO.recCarregComposto(result);
    }

    public CarregBean getCarregFechado(){
        CarregDAO carregDAO = new CarregDAO();
        return carregDAO.getCarregFechado();
    }

    public void updateCarreg(String retorno) {
        CarregDAO carregDAO = new CarregDAO();
        carregDAO.updateCarreg(retorno);
    }

}
