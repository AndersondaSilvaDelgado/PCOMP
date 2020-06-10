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

}
