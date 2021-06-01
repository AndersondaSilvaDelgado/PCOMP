package br.com.usinasantafe.pcomp.model.dao;

import java.util.List;

import br.com.usinasantafe.pcomp.model.bean.estaticas.TurnoBean;

public class TurnoDAO {

    public TurnoDAO() {
    }

    public List<TurnoBean> funcList(Long codTurno){
        TurnoBean turnoBean = new TurnoBean();
        return turnoBean.get("codTurno", codTurno);
    }

}
