package br.com.usinasantafe.pcomp.control;

import br.com.usinasantafe.pcomp.model.dao.LeiraDAO;

public class LeiraCTR {

    public LeiraCTR() {
    }

    public boolean pesqLeiraExibir(){
        LeiraDAO leiraDAO = new LeiraDAO();
        return leiraDAO.pesqLeiraExibir();
    }

    public void pesqLeiraComposto(){
        LeiraDAO leiraDAO = new LeiraDAO();
        ConfigCTR configCTR = new ConfigCTR();
        leiraDAO.pesqLeiraComposto(configCTR.getConfig());
    }

}
