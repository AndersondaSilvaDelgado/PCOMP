package br.com.usinasantafe.pcomp.control;

import android.content.Context;

import java.util.List;

import br.com.usinasantafe.pcomp.model.bean.estaticas.LeiraBean;
import br.com.usinasantafe.pcomp.model.bean.estaticas.ProdutoBean;
import br.com.usinasantafe.pcomp.model.bean.variaveis.CarregBean;
import br.com.usinasantafe.pcomp.model.dao.CarregDAO;
import br.com.usinasantafe.pcomp.model.dao.LeiraDAO;
import br.com.usinasantafe.pcomp.model.dao.ProdutoDAO;

public class CompostoCTR {

    public CompostoCTR() {
    }

    public boolean pesqLeiraExibir(){
        CarregDAO carregDAO = new CarregDAO();
        return carregDAO.verLeiraExibir();
    }

    public CarregBean carregLeiraExibir(){
        CarregDAO carregDAO = new CarregDAO();
        return carregDAO.carregLeiraExibir();
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
        ConfigCTR configCTR = new ConfigCTR();
        CarregDAO carregDAO = new CarregDAO();
        carregDAO.apontCarreg(motoMecCTR.getFunc(), configCTR.getEquip().getIdEquip(), produtoBean.getIdProduto(), context);
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

    public CarregBean getRecebLeiraComp(){
        CarregDAO carregDAO = new CarregDAO();
        return carregDAO.getRecebLeiraComp();
    }

    public CarregBean getOrdCarreg(){
        CarregDAO carregDAO = new CarregDAO();
        return carregDAO.getOrdCarreg();
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

    public void pesqCarregProduto(Context telaAtual){
        CarregDAO carregDAO = new CarregDAO();
        ConfigCTR configCTR = new ConfigCTR();
        carregDAO.pesqCarregProduto(configCTR.getConfig(), telaAtual);
    }

    public void updateCarreg(String retorno) {
        CarregDAO carregDAO = new CarregDAO();
        carregDAO.updateCarreg(retorno);
    }

    public boolean verEnviaCarreg(){
        CarregDAO carregDAO = new CarregDAO();
        return carregDAO.verEnviaCarreg();
    }

    public List<LeiraBean> leiraList(){
        LeiraDAO leiraDAO = new LeiraDAO();
        return leiraDAO.leiraList();
    }

}
