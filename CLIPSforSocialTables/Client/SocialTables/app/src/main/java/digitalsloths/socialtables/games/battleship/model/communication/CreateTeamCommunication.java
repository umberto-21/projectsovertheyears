package digitalsloths.socialtables.games.battleship.model.communication;

import digitalsloths.socialtables.basefunctions.types.Beacon;
import retrofit2.Callback;

/**
 * name CreateTeamCommunication.java
 * author Ugo Padoan
 * date 28/04/2016
 * brief Interfaccia che espone metodi per la gestione della comunicazione con il server per le funzionalità di creazione della squadra;
 * Utilizzata Utilizzata da CreateTeamPresenter per gestire la comunicazione con il server per le funzionalità di creazione della squadra;
 */
public interface CreateTeamCommunication {



    /**
     * @name createTeam
     * @desc Chiede al server di creare un team ritornando l'esito della creazione;
     * @param {String} name - nome del team che si sta per creare
     * @param {Beacon} beacon - Rappresenta il beacon associato all'utente.
     * @returns {boolean}
     * @memberOf Client.Games.Battleship.Model.Communication.CreateTeamCommunication
     */
    void createTeam(String teamName, Beacon beacon, Callback<Boolean> callback);


}
