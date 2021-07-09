package digitalsloths.socialtables.games.battleship.presenter;

import android.content.Context;
import android.view.View;

import digitalsloths.socialtables.games.utility.types.Team;
import digitalsloths.socialtables.types.Profile;

/**
 * Created by: Ivan Parise PC
 * Created on: 27/05/2016
 * Description: Interfaccia che gestisce l'application//Logic e la presentation//Logic per dare la possibilità all'capitano di gestire la propria squadra.
 * Use: Utilizzato da BattleshipManager per gestire la gestione della squadra da parte del capitano.
 */
public interface TeamManagementPresenter {

    /**
     * @name getContext
     * @desc  Restituisce il contesto dell'Activity, necessaria alla View Adapter per presentare la lista di membri personalizzata;
     * @returns {Context}
     * @memberOf Client.Games.Battleship.Presenter.TeamManagementPresenter
     */
//    public Context getContext();

    /**
     * @name getTeam
     * @desc Restituisce la lista di membri della squadra prelevandoli dal model sottostante;
     * @returns {Team}
     * @memberOf Client.Games.Battleship.Presenter.TeamManagementPresenter
     */
//    public Team getTeam();

    /**
     * @name updateTeam
     * @desc Aggiorna la lista di membri, notificando alla vista il cambiamento di dati da presentare;
     * @returns {void}
     * @memberOf Client.Games.Battleship.Presenter.TeamManagementPresenter
     */
//    public void updateTeam();

    /**
     * @name removeMember
     * @desc Rimuove il componente del team indicato.
     * @param {Profile} profile - Stringa contenente il nome del membro del team da rimuovere;
     * @returns {void}
     * @memberOf Client.Games.Battleship.Presenter.TeamManagementPresenter
     */
    public void removeMember(Profile profile);

    /**
     * @name startSearchOpponent
     * @desc Comunica al manager di iniziare la ricerca di un opponente.
     * @param {String} name - Stringa contenente il nome del team;
     * @returns {void}
     * @memberOf Client.Games.Battleship.Presenter.TeamManagementPresenter
     */
    public void startSearchOpponent(View view);


}