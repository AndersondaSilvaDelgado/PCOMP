package br.com.usinasantafe.pcomp.model.dao;

import java.util.List;

import br.com.usinasantafe.pcomp.model.bean.estaticas.FuncBean;

public class FuncDAO {

    public FuncDAO() {
    }

    public boolean verFunc(Long matricFunc){
        FuncBean funcBean = new FuncBean();
        List<FuncBean> funcList = funcBean.get("matricFunc", matricFunc);
        boolean ret = (funcList.size() > 0);
        funcList.clear();
        return ret;
    }

    public boolean hasElements(){
        FuncBean funcBean = new FuncBean();
        return funcBean.hasElements();
    }

}
