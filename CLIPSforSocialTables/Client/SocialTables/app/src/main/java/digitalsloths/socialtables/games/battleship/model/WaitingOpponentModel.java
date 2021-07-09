package digitalsloths.socialtables.games.battleship.model;

import retrofit2.Callback;

/**
 * name WaitingOpponentModel.java
 * author Umberto Andria
 * date 28/04/2016
 * brief Interfaccia che gestisce la business//Logic delle funzionalità di attesa avversario;
 * use Utilizzato da WaitingOpponentPresenter per gestire la business//Logic delle funzionalità di attesa avversario;
 */
public interface WaitingOpponentModel {


    /**
     * @name startSearchingOpponent
     * @desc Comunica al server la disponibilità di iniziare la partita; chiama il metodo startSearchingOpponent ()
     * : void sull'oggetto di tipo WaitingOpponentCommunication;
     * @returns {void}
     * @memberOf Client.Games.Battleship.Model.WaitingOpponentModel
     */
    public void startSearchingOpponent (Callback<Void> callback);

    /**
     * @name stopSearchingOpponent
     * @desc Comunica al server che l'utente esce dalla ricerca di un opponente;
     * @returns {boolean}
     * @memberOf Client.Games.Battleship.Model.WaitingOpponentModel
     */
    public void stopSearchingOpponent (Callback<Void> callback);
}
