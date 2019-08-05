package br.com.usinasantafe.pcomp;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;

import br.com.usinasantafe.pcomp.bo.ManipDadosReceb;
import br.com.usinasantafe.pcomp.to.tb.estaticas.TurnoTO;
import br.com.usinasantafe.pcomp.to.tb.variaveis.ApontCarregTO;
import br.com.usinasantafe.pcomp.to.tb.variaveis.ApontMotoMecTO;
import br.com.usinasantafe.pcomp.to.tb.variaveis.ConfiguracaoTO;
import br.com.usinasantafe.pcomp.to.tb.variaveis.TurnoVarTO;

/**
 * Created by anderson on 10/11/2016.
 */
public class PCOMPContext extends Application {

    private ApontCarregTO apontCarregTO;
    private TurnoVarTO turnoVarTO;
    private ApontMotoMecTO apontMotoMecTO;
    private boolean verOS;
    private boolean verTelaLeira;
    private boolean verTimer;
    private int tipoFuncao; //1 - CARREGAMENTO DE PRODUTO; 2 - CARREGAMENTO DE COMPOSTO;

    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
    }

    public ApontCarregTO getApontCarregTO() {
        if(apontCarregTO == null)
            apontCarregTO = new ApontCarregTO();
        return apontCarregTO;
    }

    public TurnoVarTO getTurnoVarTO() {
        if(turnoVarTO == null)
            turnoVarTO = new TurnoVarTO();
        return turnoVarTO;
    }

    public ApontMotoMecTO getApontMotoMecTO() {
        if(apontMotoMecTO == null)
            apontMotoMecTO = new ApontMotoMecTO();
        return apontMotoMecTO;
    }

    public boolean isVerOS() {
        return verOS;
    }

    public void setVerOS(boolean verOS) {
        this.verOS = verOS;
    }


    public boolean isVerTelaLeira() {
        return verTelaLeira;
    }

    public void setVerTelaLeira(boolean verTelaLeira) {
        this.verTelaLeira = verTelaLeira;
    }

    public boolean isVerTimer() {
        return verTimer;
    }

    public void setVerTimer(boolean verTimer) {
        this.verTimer = verTimer;
    }

    public int getTipoFuncao() {
        return tipoFuncao;
    }

    public void setTipoFuncao(int tipoFuncao) {
        this.tipoFuncao = tipoFuncao;
    }
}
