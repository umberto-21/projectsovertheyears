package digitalsloths.socialtables.games.battleship.model.communication;

import retrofit2.Callback;

/**
 * file: WaitingOpponentCommunication.java
 * author: Ugo Padoan
 * date: 6/6/16
 * brief: Interfaccia per la gestione della comunicazione con il server per le funzionalità di attesa avversario;
 * use: Utilizzata da WaitingOpponentPresenter per la gestione della comunicazione con il server per le funzionalità di attesa avversario;
 */
public interface WaitingOpponentCommunication {
    /**
     * @name startSearchingOpponent
     * @desc Comunica al server la disponibilità di iniziare la partita;
     * @returns {void}
     * @memberOf Client.Games.Battleship.Model.Communication.WaitingOpponentCommunication
     */
    void startSearchingOpponent(Callback<Void> callback);

    /**
     * @name stopSearchingOpponent
     * @desc comunica al server che l'utente é uscito dalla ricerca;
     * @returns {void}
     * @memberOf Client.Games.Battleship.Model.Communication.WaitingOpponentCommunication
     */
    void stopSearchingOpponent(Callback<Void> callback);

}
