package digitalsloths.socialtables.games.battleship.presenter;

import android.content.Context;
import android.view.View;

import digitalsloths.socialtables.games.utility.types.Team;

/**
 * Created by Ivan Parise
 * Created date: 25/05/2016
 * Description: Interfaccia che gestisce l'application//Logic e la presentation//Logic per dare la possibilità all'utente di vedere com'è composta la squadra e eventualmente dissociarsi.
 * Use: Utilizzato da BattleshipManager per gestire la creazione del team da parte di un membro del team che non sia il capitano.
 */
public interface JoinTeamPresenter {

    /**
     * @name leaveTeam
     * @desc Permette di uscire da un team.
     * @param {View} view - Vista che ha ricevuto l'evento collegata al metodo;
     * @returns {void}
     * @memberOf Client.Games.Battleship.Presenter.JoinTeamPresenter
     */
    public void leaveTeam(View view);

    /**
     * @name getContext
     * @desc Contesto dell'Activity per presentare la lista dei membri del team.
     * @returns {Context}
     * @memberOf Client.Games.Battleship.Presenter.JoinTeamPresenter
     */
    public Context getContext();

    /**
     * @name getTeam
     * @desc Restituisce il team di cui l'utente fa parte;
     * @returns {Team}
     * @memberOf Client.Games.Battleship.Presenter.JoinTeamPresenter
     */
    public Team getTeam();

    public void updateTeam();

    void changeToCaptain();
}
