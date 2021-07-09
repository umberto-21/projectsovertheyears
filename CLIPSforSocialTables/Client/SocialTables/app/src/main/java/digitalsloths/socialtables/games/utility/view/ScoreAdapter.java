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
import digitalsloths.socialtables.games.utility.presenter.LeaderboardPresenter;
import digitalsloths.socialtables.games.utility.types.Score;

/**
 * name ScoreAdapter.java
 * author Casotto Federico
 * date 15/05/2016
 * brief Classe per adattare e personalizzare la lista di migliori punteggi.
 * use Utilizzata da LeaderboardPresenterImpl per personalizzare la visualizzazione della classifica.
 */
public class ScoreAdapter extends BaseAdapter {

    private static LayoutInflater _inflater = null;

    private final Context _context;
    private final List<Score> _bestScores;

    /**
     * @name ScoreAdapter
     * @desc Costruttore per personalizzare visualizzazione lista punteggi;
     * @param {LeaderboardPresenter} leaderboard - Presenter di visualizzazione classifica;
     * @memberOf Client.Games.Utility.View.ScoreAdapter
     */
    public ScoreAdapter(LeaderboardPresenter leaderboard) {

        this._context = leaderboard.getContext();
        this._bestScores = leaderboard.getScoresList();

        _inflater = (LayoutInflater)_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    /**
     * @name getCount
     * @desc Numero di righe del lista, ovvero numero membri;
     * @returns {int}
     * @memberOf Client.Games.Utility.View.ScoreAdapter
     */
    @Override
    public int getCount() {
        return _bestScores.size();
    }

    /**
     * @name getItem
     * @desc Oggetto della lista alla posizione richiesta;
     * @param {int} position - Posizione richiesta;
     * @returns {Object}
     * @memberOf Client.Games.Utility.View.ScoreAdapter
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
     * @memberOf Client.Games.Utility.View.ScoreAdapter
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
     * @memberOf Client.Games.Utility.View.ScoreAdapter
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = _inflater.inflate(R.layout.score_list_item_layout, null);
        ImageView icon = (ImageView) rowView.findViewById(R.id.scorePositionImage);
        TextView posit = (TextView) rowView.findViewById(R.id.positionScore);
        TextView name = (TextView) rowView.findViewById(R.id.teamText);
        TextView score = (TextView) rowView.findViewById(R.id.scoreText);
        int[] cupsIcon = {R.drawable.gold_cup,R.drawable.silver_cup,R.drawable.bronze_cup};
        if (position < 3) icon.setImageResource(cupsIcon[position]);
        posit.setText(String.valueOf(position+1));
        name.setText(_bestScores.get(position).getTeamName());
        score.setText(String.valueOf(_bestScores.get(position).getScore()));
        return rowView;
    }

}
