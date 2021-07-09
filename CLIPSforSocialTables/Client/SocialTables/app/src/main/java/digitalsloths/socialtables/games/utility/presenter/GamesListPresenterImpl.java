package digitalsloths.socialtables.games.utility.presenter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import digitalsloths.socialtables.R;
import digitalsloths.socialtables.games.utility.model.GamesListModel;
import digitalsloths.socialtables.games.utility.model.GamesListModelImpl;
import digitalsloths.socialtables.games.utility.types.Game;
import digitalsloths.socialtables.games.utility.view.GamesAdapter;

/**
 * name GamesListPresenter.java
 * author Pinton Federico
 * date 15/05/2016
 * brief Implementa l'interfaccia che gestisce la application//Logic\g{} e la presentation//Logic\g{} per fornire l'elenco dei giochi che l'applicazione mette a disposizione;
 * use Utilizzata da UtilityManager per gestire la selezione e l'avvio di un gioco.
 */
public class GamesListPresenterImpl extends AppCompatActivity implements GamesListPresenter {

    private GamesListModel _gamesListModel;
    //private List<Game> _listGame;

    /**
     * @name onCreate
     * @desc Costruttore di default dell'activity che visualizza la lista dei giochi disponibili;
     * @param {Bundle} savedInstanceState - Oggetto per passare varie informazione utili all'Activity.
     * @returns {void}
     * @memberOf Client.Games.Utility.Presenter.GamesListPresenterImpl
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games_list_view);

        _gamesListModel = new GamesListModelImpl();
        showGamesList();

    }

    /**
     * @name showGamesList
     * @desc Visualizza una lista di giochi e permette di selezionarne uno;
     * @returns {void}
     * @memberOf Client.Games.Utility.Presenter.GamesListPresenterImpl
     */
    @Override
    public void showGamesList() {

        final ListView gameListView = (ListView) findViewById(R.id.gamesList);
        gameListView.setAdapter(new GamesAdapter(this));

        gameListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long arg3) {
                loadGame(_gamesListModel.getGameList().get(position));
            }
        });

    }

    /**
     * @name loadGame
     * @desc Richiama il metodo loadGame dall'utility manager;
     * @returns {void}
     * @memberOf Client.Games.Utility.Presenter.GamesListPresenterImpl
     */
    @Override
    public void loadGame(Game game) {
        UtilityManager utilityManager = new UtilityManagerImpl(this,game);
        utilityManager.run();
    }

    /**
     * @name getContext
     * @desc Contesto dell'Activity di presentazione dei giochi
     * @returns {Context}
     * @memberOf Client.Games.Utility.Presenter.GamesListPresenter
     */
    @Override
    public Context getContext() {
        return this;
    }

    /**
     * @name getGamesList
     * @desc Lista di giochi disponibili
     * @returns {List<Game>}
     * @memberOf Client.Games.Utility.Presenter.GamesListPresenter
     */
    @Override
    public List<Game> getGamesList() {
        return _gamesListModel.getGameList();
    }

}
