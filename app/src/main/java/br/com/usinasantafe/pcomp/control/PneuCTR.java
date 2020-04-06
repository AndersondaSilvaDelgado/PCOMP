package br.com.usinasantafe.pcomp.control;

import android.app.ProgressDialog;
import android.content.Context;

import java.util.List;

import br.com.usinasantafe.pcomp.model.bean.variaveis.ItemPneuBean;
import br.com.usinasantafe.pcomp.model.dao.CabecPneuDAO;
import br.com.usinasantafe.pcomp.model.dao.ItemPneuDAO;

public class PneuCTR {

    private ItemPneuBean itemPneuBean;

    public PneuCTR() {
    }

    public boolean verCalibAberto(){
        CabecPneuDAO cabecPneuDAO = new CabecPneuDAO();
        return cabecPneuDAO.verCabecPneuAberto();
    }

    public List getListItemCalibPneu(){
        CabecPneuDAO cabecPneuDAO = new CabecPneuDAO();
        ItemPneuDAO itemPneuDAO = new ItemPneuDAO();
        return itemPneuDAO.getListItemPneu(cabecPneuDAO.getIdCabecAberto());
    }

    public ItemPneuBean getItemPneuBean() {
        return itemPneuBean;
    }

    public void setItemPneuBean(Long idPosConfPneu) {
        CabecPneuDAO cabecPneuDAO = new CabecPneuDAO();
        this.itemPneuBean = new ItemPneuBean();
        this.itemPneuBean.setPosItemPneu(idPosConfPneu);
        this.itemPneuBean.setIdCabecItemPneu(cabecPneuDAO.getIdCabecAberto());
    }

    public void verPneu(String dado, Context telaAtual, Class telaProx, ProgressDialog progressDialog){
        ItemPneuDAO itemPneuDAO = new ItemPneuDAO();
        itemPneuDAO.verPneu(dado, telaAtual, telaProx, progressDialog);
    }

    public void salvarItem(){
        ItemPneuDAO itemPneuDAO = new ItemPneuDAO();
        itemPneuDAO.salvarItem(itemPneuBean);
    }

    public boolean verFechCabec(){
        CabecPneuDAO cabecPneuDAO = new CabecPneuDAO();
        ItemPneuDAO itemPneuDAO = new ItemPneuDAO();
        return cabecPneuDAO.verFechCabec(itemPneuDAO.getListItemPneu(cabecPneuDAO.getIdCabecAberto()));
    }

}
