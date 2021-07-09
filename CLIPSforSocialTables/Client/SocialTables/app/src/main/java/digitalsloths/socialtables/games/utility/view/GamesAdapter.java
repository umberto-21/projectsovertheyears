package digitalsloths.socialtables.games.utility.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import digitalsloths.socialtables.R;
import digitalsloths.socialtables.games.utility.presenter.GamesListPresenter;
import digitalsloths.socialtables.games.utility.types.Game;

/**
 * name GamesAdapter.java
 * author Casotto Federico
 * date 15/05/2016
 * brief Implementa l'interfaccia personalizzata della lista di giochi.
 * use Utilizzata da GamesListPresenter per presentare la lista di giochi personalizzata.
 */
public class GamesAdapter extends BaseAdapter {

    private static LayoutInflater _inflater = null;

    private final Context _context;
    private final List<Game> _games;

    /**
     * @name GamesAdapter
     * @desc Costruttore per personalizzare aggiunta membro team;
     * @param {GamesListPresenter} gamesListView - Presenter di gestione lista giochi;
     * @memberOf Client.Games.Utility.View.GamesAdapter
     */
    public GamesAdapter(GamesListPresenter gamesListView) {

        _context = gamesListView.getContext();
        _games = gamesListView.getGamesList();

        _inflater = (LayoutInflater)_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    /**
     * @name getCount
     * @desc Numero di righe del lista, ovvero numero membri;
     * @returns {int}
     * @memberOf Client.Games.Utility.View.GamesAdapter
     */
    @Override
    public int getCount() {
        return _games.size();
    }

    /**
     * @name getItem
     * @desc Oggetto della lista alla posizione richiesta;
     * @param {int} position - Posizione richiesta;
     * @returns {Object}
     * @memberOf Client.Games.Utility.View.GamesAdapter
     */
    @Override
    public Object getItem(int position) {
        return position;
    }

    /**
     * @name getItemId
     * @desc Id dell'oggetto alla posizione richiesta;
     * @param {int} position - Posizione richiesta;
     * @returns {long}
     * @memberOf Client.Games.Utility.View.GamesAdapter
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * @name getView
     * @desc Metodo principale per personalizzare righe della lista;
     * @param {int} position - Posizione riga corrente;
     * @param {View} convertView - Vista oggetto che si sta modificando;
     * @param {ViewGroup} parent - Padri della vista corrente;
     * @returns {View}
     * @memberOf Client.Games.Utility.View.GamesAdapter
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = _inflater.inflate(R.layout.game_list_item_layout, null);
        TextView text = (TextView) rowView.findViewById(R.id.itemText);
        ImageView image = (ImageView) rowView.findViewById(R.id.itemImage);
        text.setText(_games.get(position).getName());
        image.setImageResource(R.drawable.battleship);
        return rowView;
    }
}
