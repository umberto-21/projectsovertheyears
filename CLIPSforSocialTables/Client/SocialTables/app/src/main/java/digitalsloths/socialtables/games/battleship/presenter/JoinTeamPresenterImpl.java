package digitalsloths.socialtables.games.battleship.presenter;

import com.google.gson.Gson;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import digitalsloths.socialtables.R;
import digitalsloths.socialtables.games.battleship.model.BattleshipModel;
import digitalsloths.socialtables.games.battleship.model.BattleshipModelImpl;
import digitalsloths.socialtables.games.battleship.model.JoinTeamModel;
import digitalsloths.socialtables.games.battleship.model.JoinTeamModelImpl;
import digitalsloths.socialtables.games.battleship.model.ProfileModel;
import digitalsloths.socialtables.games.battleship.model.ProfileModelImpl;
import digitalsloths.socialtables.games.battleship.model.communication.BattleshipCommunicationImpl;
import digitalsloths.socialtables.games.battleship.model.communication.JoinTeamCommunication;
import digitalsloths.socialtables.games.battleship.model.communication.JoinTeamCommunicationImpl;
import digitalsloths.socialtables.games.battleship.model.communication.ProfileCommunicationImpl;
import digitalsloths.socialtables.games.battleship.view.MembersAdapter;
import digitalsloths.socialtables.games.utility.types.Team;
import digitalsloths.socialtables.games.utility.types.TeamImpl;
import digitalsloths.socialtables.types.Profile;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ivan Parise
 * Created date: 25/05/2016
 * Description: Implementa l'interfaccia che gestisce l'application//Logic e la presentation//Logic per dare la possibilità all'utente di vedere com'è composta la squadra e eventualmente dissociarsi.
 * * * Use: Utilizzato da BattleshipManager per gestire la creazione del team da parte di un membro del team che non sia il capitano.
 */
public class JoinTeamPresenterImpl extends AppCompatActivity implements JoinTeamPresenter {
    private static final String TAG = "JoinTeamPresenterImpl";
    private JoinTeamModel _joinTeamModel;
    private BattleshipManager battleshipManager = BattleshipManagerImpl.getBattleshipManagerIstance();
    private BattleshipModel battleshipModel = new BattleshipModelImpl(new BattleshipCommunicationImpl());
    ProfileModel profileModel = new ProfileModelImpl(new ProfileCommunicationImpl());
    private Profile profile;

    /**
     * @name onCreate
     * @desc Metodo principale per creazione dell'Activity di composizione del team.
     * @param {Bundle} savedInstanceState - Informazione aggiuntive passate all'Activity.
     * @returns {void}
     * @memberOf Client.Games.Battleship.Presenter.JoinTeamPresenterImpl
     */
    @Override
    public void onCreate(Bundle savedInstanceState){
      //Log.i(TAG, "JoinTeamPresenterImpl.onCreate(Bundle)");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_team_view);

        battleshipManager.setJoinTeamActivity(this);

        _joinTeamModel = new JoinTeamModelImpl(new JoinTeamCommunicationImpl());

        _joinTeamModel.getTeam(new Callback<TeamImpl>() {

           @Override
           public void onResponse(Call<TeamImpl> call, Response<TeamImpl> response) {
               if(response.isSuccessful()){
                   Team team = response.body();
                   if(team != null){
                       //nome team
                       TextView nameT = (TextView) findViewById(R.id.teamName);
                       nameT.setText(team.getTeamName());
                   } else {
                       leaveTeam(null);
                   }
               }
           }

            @Override
            public void onFailure(Call<TeamImpl> call, Throwable t) {

            }
        });

        final ListView membersListView = (ListView) findViewById(R.id.membersList);
        membersListView.setAdapter(new MembersAdapter(this));

        final Button actionButton = (Button) this.findViewById(R.id.playButton);
        actionButton.setVisibility(View.INVISIBLE);


        _joinTeamModel.addTeamObserver(new Runnable() {
            @Override
            public void run() {

                updateTeam();
            }
        });

        battleshipModel.addStartSearchingOpponentObserver(new Runnable() {
            @Override
            public void run() {
               //Log.i(TAG, "is start searching opponent");
                battleshipManager.startSearching();
            }
        });


    }

    @Override
    public void onNewIntent(Intent intent){

       //Log.i(TAG, ".onNewIntent(...)");
        super.onNewIntent(intent);
        _joinTeamModel.addTeamObserver(new Runnable() {
            @Override
            public void run() {
                updateTeam();
            }
        });
        battleshipModel.addStartSearchingOpponentObserver(new Runnable() {
            @Override
            public void run() {
               //Log.i(TAG, "is start searching opponent");
                battleshipManager.startSearching();
            }
        });
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.isTracking()
                && !event.isCanceled()) {
            battleshipManager.leaveTeam();
        }
        return super.onKeyUp(keyCode, event);
    }

    /**
     * @name leaveTeam
     * @desc Permette di uscire da un team.
     * @param {Profile} profile - Stringa contenente il nome del membro del team da rimuovere;
     * @returns {void}
     * @memberOf Client.Games.Battleship.Presenter.JoinTeamPresenterImpl
     */
    @Override
    public void leaveTeam(View view) {
       //Log.i(TAG, ".leaveTeam(View)");
        battleshipManager.leaveTeam();
    }

    /**
     * @name getContext
     * @desc Contesto dell'Activity per presentare la lista dei membri del team.
     * @returns {Context}
     * @memberOf Client.Games.Battleship.Presenter.JoinTeamPresenterImpl
     */
    @Override
    public Context getContext() {
       //Log.i(TAG, ".getContext()");
        return this;
    }

    /**
     * @name removeMember
     * @desc Rimuove il componente del team indicato.
     * @returns {void}
     * @memberOf Client.Games.Battleship.Presenter.JoinTeamPresenterImpl
     */
    @Override
    public Team getTeam() {
      //Log.i(TAG, ".getTeam()");
        Future<Team> future = _joinTeamModel.getTeamFuture();
        Team team = null;
        try {
            team = future.get();
            if(team == null) {
               //Log.i(TAG, ".getTeam() return null");
                battleshipManager.kickOut();
                return null;
            }
            profile = profileModel.getProfile();
            if(team.getCaptain().getId() == profile.getId()){
                changeToCaptain();
                return team;
            }
            List<Profile> teammates = team.getTeammates();
            boolean find = false;
            for(Profile teammate : teammates){
                if(teammate.getId() == profile.getId()){
                    find = true;
                }
            }
            if(!find) battleshipManager.leaveTeam();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
       //Log.i(TAG, ".getTeam() return : " + (new Gson().toJson(team)));
        return team;
    }


        /**
     * @name updateTeam
     * @desc Aggiorna la lista di membri, notificando alla vista il cambiamento di dati da presentare;
     * @returns {void}
     * @memberOf Client.Games.Battleship.Presenter.TeamManagementPresenterImpl
     */
    @Override
    public void updateTeam() {
      //Log.i(TAG, ".updateTeam()");
//        changeToCaptain();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //notifico all'adapter il cambiamento nella lista
                final ListView membersListView = (ListView) findViewById(R.id.membersList);
                ((MembersAdapter)membersListView.getAdapter()).updateTeam();
            }
        });
    }

    @Override
    public void changeToCaptain(){
        battleshipManager.changeToCaptainTeamManagment();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        battleshipModel.removeStartSearchingOpponentObserver();
        _joinTeamModel.removeTeamObserver();
    }
}
