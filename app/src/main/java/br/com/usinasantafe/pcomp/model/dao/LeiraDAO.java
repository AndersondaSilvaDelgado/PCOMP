package br.com.usinasantafe.pcomp.model.dao;

import java.util.List;

import br.com.usinasantafe.pcomp.model.bean.estaticas.LeiraBean;

public class LeiraDAO {

    public LeiraDAO() {
    }

    public LeiraBean getLeira(Long idLeira){
        LeiraBean leiraBean = new LeiraBean();
        List leiraList = leiraBean.get("idLeira", idLeira);
        leiraBean = (LeiraBean) leiraList.get(0);
        leiraList.clear();
        return leiraBean;
    }

    public List<LeiraBean> leiraList(){
        LeiraBean leiraBean = new LeiraBean();
        return leiraBean.orderBy("codLeira", true);
    }

}
