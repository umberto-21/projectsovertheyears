package digitalsloths.socialtables.games.battleship.presenter;

/**
 * Created by Ivan Parise
 * Created date: 25/05/2016
 * Description:.Interfaccia che gestisce l'application//Logic e la presentation//Logic per dare la possibilità all'utente di creare una squadra;
 * Use: Utilizzato da BattleshipManager per gestire la fase di creazione del team.
 */
public interface CreateTeamPresenter {

    /**
     * @name createTeam
     * @desc richiama il metodo CreateTeam della classe BattleShipModelImpl; se restituisce un errore crea Client.BaseFunctions.Presenter.ErrorManager passandogli come parametro l'errore; altrimenti invoca su BattleshipManager il metodo showTeamManagement
     * @param {String} name - Stringa contenente il nome del team;
     * @returns {void}
     * @memberOf Client.Games.Battleship.Presenter.CreateTeamPresenter
     */
    public void createTeam (String name);

    /**
     * @name run
     * @desc Metodo che fa eseguire i comandi di creazione del nuovo team, altrimenti visualizza gli errori;
     * @returns {void}
     * @memberOf Client.Games.Battleship.Presenter.CreateTeamPresenter
     */
    public void run ();
}
