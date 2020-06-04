package br.com.usinasantafe.pcomp.model.dao;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import br.com.usinasantafe.pcomp.InformacaoActivity;
import br.com.usinasantafe.pcomp.model.bean.estaticas.LeiraBean;
import br.com.usinasantafe.pcomp.model.bean.estaticas.OSBean;
import br.com.usinasantafe.pcomp.model.bean.variaveis.ConfigBean;
import br.com.usinasantafe.pcomp.util.VerifDadosServ;

public class LeiraDAO {

    public LeiraDAO() {
    }

    public boolean pesqLeiraExibir(){

//        PesqLeiraProdutoBean pesqBalancaProdTO = new PesqLeiraProdutoBean();
//        PesqLeiraCompostoBean pesqBalancaCompTO = new PesqLeiraCompostoBean();
//        if ((pesqBalancaProdTO.count() > 0) || (pesqBalancaCompTO.count() > 0)) {
            return true;
//        }
//        else {
//            return false;
//        }
    }


    public LeiraBean getLeira(Long idLeira){
        LeiraBean leiraBean = new LeiraBean();
        List leiraList = leiraBean.get("idLeira", idLeira);
        leiraBean = (LeiraBean) leiraList.get(0);
        leiraList.clear();
        return leiraBean;
    }

}
