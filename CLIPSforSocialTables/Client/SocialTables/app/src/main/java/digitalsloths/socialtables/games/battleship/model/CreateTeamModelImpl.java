package digitalsloths.socialtables.games.battleship.model;

import android.util.Log;

import com.google.gson.Gson;

import digitalsloths.socialtables.basefunctions.model.EnvironmentServiceModel;
import digitalsloths.socialtables.basefunctions.model.EnvironmentServiceModelImpl;
import digitalsloths.socialtables.basefunctions.types.Beacon;
import digitalsloths.socialtables.basefunctions.types.errors.Error;
import digitalsloths.socialtables.games.battleship.model.communication.CreateTeamCommunication;
import retrofit2.Callback;

/**
 * file: CreateTeamModelImpl.java
 * author: Andria Umberto
 * date: 5/29/16
 * brief: Implementa l'interfaccia che gestisce la business//Logic per la creazione della squadra;
 * use: Utilizzata da CreateTeamPresenter per la gestione della business//Logic per la creazione della squadra;
 */
public class CreateTeamModelImpl implements CreateTeamModel {

    private static final String TAG = "CreateTeamModelImpl";
    private CreateTeamCommunication _communication;
    private static EnvironmentServiceModel _serviceModel = EnvironmentServiceModelImpl.getIstance();

    /**
     * @name CreateTeamModelImpl
     * @desc costruttore
     * @param {CreateTeamCommunication} communication - parametro per l'inizializzazione di _communication;
     * @memberOf Client.Games.Battleship.Model.CreateTeamModelImpl
     */
    public CreateTeamModelImpl (CreateTeamCommunication communication) {
       //Log.v(TAG, ".createTeamModelImpl(...)");
        _communication =communication;
       //Log.v(TAG, ".createTeamModelImpl(...)");
    }

    /**
     * @name createTeam
     * @desc chiama il metodo createTeam (name : String) : Client.Types.Error sull'oggetto di tipo CreateTeamCommunication;
     * @param {String} name - nome della squadra
     * @returns {Client.Types.Error}
     * @memberOf Client.Games.Battleship.Model.CreateTeamModel
     */
    @Override
    public void createTeam(String name, Callback<Boolean> callback) {
       //Log.v(TAG, ".createTeam(" + (new Gson()).toJson(name) + ")");
        Beacon beacon =_serviceModel.getNearestBeacon();
        _communication.createTeam(name, beacon, callback);
       //Log.v(TAG, ".createTeam(" + (new Gson()).toJson(name) + ") return: void" );
    }
}
