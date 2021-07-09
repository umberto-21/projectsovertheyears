package digitalsloths.socialtables.games.battleship.presenter;

import com.google.gson.Gson;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import digitalsloths.socialtables.R;
import digitalsloths.socialtables.games.battleship.model.BattleshipModel;
import digitalsloths.socialtables.games.battleship.model.BattleshipModelImpl;
import digitalsloths.socialtables.games.battleship.model.JoinTeamModel;
import digitalsloths.socialtables.games.battleship.model.JoinTeamModelImpl;
import digitalsloths.socialtables.games.battleship.model.TeamManagementModel;
import digitalsloths.socialtables.games.battleship.model.TeamManagementModelImpl;
import digitalsloths.socialtables.games.battleship.model.WaitingOpponentModel;
import digitalsloths.socialtables.games.battleship.model.WaitingOpponentModelImpl;
import digitalsloths.socialtables.games.battleship.model.communication.BattleshipCommunicationImpl;
import digitalsloths.socialtables.games.battleship.model.communication.JoinTeamCommunicationImpl;
import digitalsloths.socialtables.games.battleship.model.communication.TeamManagementCommunicationImpl;
import digitalsloths.socialtables.games.battleship.model.communication.WaitingOpponentCommunicationImpl;
import digitalsloths.socialtables.games.battleship.view.MembersAdapter;
import digitalsloths.socialtables.games.utility.types.Team;
import digitalsloths.socialtables.games.utility.types.TeamImpl;
import digitalsloths.socialtables.types.Profile;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by: Ivan Parise PC
 * Created on: 27/05/2016
 * Description: Implementa l'interfaccia che gestisce l'application//Logic e la presentation//Logic per dare la possibilità all'capitano di gestire la propria squadra.
 * Use: Utilizzato da BattleshipManager per gestire la gestione della squadra da parte del capitano.
 */
public class TeamManagementPresenterImpl extends JoinTeamPresenterImpl implements TeamManagementPresenter {

    private static final String TAG = "TeamManagementPresenter";
    private TeamManagementModel _teamModel;
    private BattleshipManager battleshipManager = BattleshipManagerImpl.getBattleshipManagerIstance();
    private BattleshipModel battleshipModel = new BattleshipModelImpl(new BattleshipCommunicationImpl());

    /**
     * @name onCreate
     * @desc Metodo per far iniziare l'Activity di gestione della squadra da parte del capitano.
     * @param {Bundle} savedInstanceState - Insieme di informazioni aggiuntive passate all'Activity;
     * @returns {void}
     * @memberOf Client.Games.Battleship.Presenter.TeamManagementPresenterImpl
     */
    @Override
    public void onCreate(Bundle savedInstanceState){
       //Log.i(TAG, "TeamManagementPresenterImpl.onCreate(Bundle)");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_team_view);

        _teamModel = new TeamManagementModelImpl(new TeamManagementCommunicationImpl());

        final ListView membersListView = (ListView) findViewById(R.id.membersList);
        membersListView.setAdapter(new MembersAdapter(this));

    }

    /**
     * @name removeMember
     * @desc Rimuove il componente del team indicato.
     * @param {Profile} profile - Stringa contenente il nome del membro del team da rimuovere;
     * @returns {void}
     * @memberOf Client.Games.Battleship.Presenter.TeamManagementPresenterImpl
     */
    @Override
    public void removeMember(Profile profile) {
       //Log.i(TAG, ".removeMember(Profile)");
        _teamModel.removeTeamMember(profile, new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    /**
     * @name startSearchOpponent
     * @desc Comunica al manager di iniziare la ricerca di un opponente.
     * @param {String} name - Stringa contenente il nome del team;
     * @returns {void}
     * @memberOf Client.Games.Battleship.Presenter.TeamManagementPresenterImpl
     */
    @Override
    public void startSearchOpponent(View view) {
       //Log.i(TAG, ".startSearchOpponent(View)");
        final WaitingOpponentModel waitingOpponentModel = new WaitingOpponentModelImpl(new WaitingOpponentCommunicationImpl());
        waitingOpponentModel.startSearchingOpponent(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    @Override
    public void changeToCaptain(){

    }

}
