package digitalsloths.socialtables.games.battleship.presenter;

import com.google.gson.Gson;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import digitalsloths.socialtables.R;
import digitalsloths.socialtables.basefunctions.presenter.MainMenuPresenter;
import digitalsloths.socialtables.basefunctions.presenter.MainMenuPresenterImpl;
import digitalsloths.socialtables.games.battleship.model.BattleshipModel;
import digitalsloths.socialtables.games.battleship.model.ShowFinalScoreModel;
import digitalsloths.socialtables.games.battleship.model.ShowFinalScoreModelImpl;
import digitalsloths.socialtables.games.battleship.model.communication.ShowFinalScoreCommunication;
import digitalsloths.socialtables.games.battleship.model.communication.ShowFinalScoreCommunicationImpl;
import digitalsloths.socialtables.games.utility.types.Score;
import digitalsloths.socialtables.games.utility.types.ScoreImpl;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
* name FinalScorePresenter.java
* author Andria Umberto
* date 10/05/2016
* brief Implementa l'interfaccia che gestisce l'application//Logic\g{} e la presentation//Logic\g{}
* per dare la possibilità all'utente di visualizzare il punteggio finale.
* use Utilizzato da BattleshipManager per gestire la visualizzazione del punteggio finale.
*/
public class FinalScorePresenterImpl extends AppCompatActivity implements FinalScorePresenter {
    private ShowFinalScoreModel showFinalScoreModel;
    private BattleshipManager battleshipManager = BattleshipManagerImpl.getBattleshipManagerIstance();
    private static final String TAG = "FinalScorePresenterImpl";

    private static String VICTORY_MESSAGE = "YOUR TEAM WON!!";
    private static String DEFEAT_MESSAGE = "YOUR TEAM LOST!!";

    /**
     * @name onCreate
     * @desc Costruttore dell'activity per la visualizzazione del risultato finale;
     * @param {Bundle} savedInstanceState - Oggetto per passare varie informazioni utili all'Activity;
     * @returns {void}
     * @memberOf Client.Games.Battleship.Presenter.FinalScorePresenterImpl
     */
    protected void onCreate(Bundle savedInstanceState) {
       //Log.i(TAG, "FinalScorePresenterImpl.onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_score_view);

        battleshipManager.setShowFinalScoreActivity(this);

        showFinalScoreModel = new ShowFinalScoreModelImpl(new ShowFinalScoreCommunicationImpl());
        showFinalScoreModel.getFinalScore(new Callback<List<Score>>() {
            @Override
            public void onResponse(Call<List<Score>> call, Response<List<Score>> response) {
                if(response.isSuccessful()){
                    List<Score> scoreList = response.body();
                   //Log.i(TAG, "getFinalScore.onResponse() " + (new Gson()).toJson(scoreList));
                    if (scoreList != null && scoreList.size()>=2){
                        showFinalScore(scoreList.get(0), scoreList.get(1));
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Score>> call, Throwable t) {

            }
        });


    }

    /**
     * @name back
     * @desc Metodo che ritorna alla schermata principale dell'applicazione;
     * @param {View} view - Vista di riferimento per l'associazione al pulsante da premere;
     * @returns {void}
     * @memberOf Client.Games.Battleship.Presenter.FinalScorePresenterImpl
     */
    @Override
    public void back(View view) {
       //Log.i(TAG, ".back(View)");
//        Intent mainMenuIntent = new Intent(this,MainMenuPresenterImpl.class);
//        this.startActivity(mainMenuIntent);
        battleshipManager.exit();
    }

    public void rematch(View view) {
        //Log.i(TAG, ".rematch(View)");
        battleshipManager.startPlay();
    }

    /**
     * @name showFinalScore
     * @desc Metodo che visualizza il punteggio finale della partita;
     * @param {Score} teamScore - Punteggio raggiunto dalla squadra;
     * @param {Score} enemyScore - Punteggio raggiunto dalla squadra avversaria;
     * @returns {void}
     * @memberOf Client.Games.Battleship.Presenter.FinalScorePresenterImpl
     */
    @Override
    public void showFinalScore(Score teamScore, Score enemyScore) {
       //Log.i(TAG, ".showFinalScore(Score,Score)");

        TextView yourScore = (TextView) this.findViewById(R.id.scoreEndMatch);
        TextView teamName = (TextView) this.findViewById(R.id.teamNameAlert);
        yourScore.setText(String.valueOf(teamScore.getScore()));
        teamName.setText(teamScore.getTeamName());

        TextView opponentScore = (TextView) this.findViewById(R.id.scoreOpponentEndMatch);
        TextView opponentName = (TextView) this.findViewById(R.id.opponentTeamAlert);
        opponentScore.setText(String.valueOf(enemyScore.getScore()));
        opponentName.setText(enemyScore.getTeamName());

        if( teamScore.getScore() >= enemyScore.getScore()) {
            ((ImageView)findViewById(R.id.endMatchImage)).setImageResource(R.drawable.victory_ship);
            ((TextView)findViewById(R.id.matchResultAlert)).setText(VICTORY_MESSAGE);
        }else {
            ((ImageView)findViewById(R.id.endMatchImage)).setImageResource(R.drawable.sunk_ship);
            ((TextView)findViewById(R.id.matchResultAlert)).setText(DEFEAT_MESSAGE);
        }

    }
}
