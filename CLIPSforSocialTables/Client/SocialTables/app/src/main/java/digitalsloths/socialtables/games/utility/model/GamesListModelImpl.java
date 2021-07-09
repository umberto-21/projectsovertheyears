package digitalsloths.socialtables.games.utility.model;

import java.util.ArrayList;
import java.util.List;

import digitalsloths.socialtables.games.utility.types.Game;
import digitalsloths.socialtables.games.utility.types.GameImpl;

/**
 * name GamesListModelImpl.java
 * author Casotto Federico
 * date 15/05/2016
 * brief Implementa l'interfaccia che gestisce la bussines//Logic legata alla gestione dei giochi avviabili
 * use Utilizzata da GamesListPresenter per gestire la bussines//Logic legata alla gestione dei giochi avviabili.
 */
public class GamesListModelImpl implements GamesListModel {

    private static List<Game> _gameList;

    public GamesListModelImpl(){

        Game battleship=new GameImpl(1,"Battleship");
        Game tetris=new GameImpl(2,"Tetris");
        Game memory=new GameImpl(3,"Memory");

        //inizializzazione array
        _gameList= new ArrayList<Game>();

        _gameList.add(battleship);
//        _gameList.add(tetris);
//        _gameList.add(memory);


        //riempimento array fittizio, da implementare attraverso comunicazione con Server
        /*String[] games_names = {"Battleship","Tetris","Memory"};
        for(int i=0; i < games_names.length; i++)
            _gameList.add(new GameImpl(i,games_names[i]));*/

    }

    @Override
    public List<Game> getGameList() {
        return _gameList;
    }

    @Override
    public void addGame(Game game){
        _gameList.add(game);
    }
}
