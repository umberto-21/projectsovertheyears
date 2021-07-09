package digitalsloths.socialtables.games.battleship.model;

import digitalsloths.socialtables.basefunctions.types.errors.Error;
import retrofit2.Callback;

/**
 * file: CreateTeamModel.java
 * author: Andria Umberto
 * date: 5/29/16
 * brief: 	Interfaccia che gestisce la business//Logic per la creazione della squadra;
 * use: Utilizzata da CreateTeamPresenter per la gestione della business//Logic per la creazione della squadra;
 */
public interface CreateTeamModel {

    /**
     * @name createTeam
     * @desc chiama il metodo createTeam (name : String) : Client.Types.Error sull'oggetto di tipo CreateTeamCommunication;
     * @param {String} name - nome della squadra
     * @returns {Client.Types.Error}
     * @memberOf Client.Games.Battleship.Model.CreateTeamModel
     */
    public void createTeam (String name, Callback<Boolean> callback); //da sostiutire bool con Error

}
