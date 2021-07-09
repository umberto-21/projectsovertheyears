package digitalsloths.socialtables.games.battleship.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import digitalsloths.socialtables.R;
import digitalsloths.socialtables.games.battleship.model.JoinTeamModel;
import digitalsloths.socialtables.games.battleship.model.TeamManagementModel;
import digitalsloths.socialtables.games.battleship.presenter.JoinTeamPresenter;
import digitalsloths.socialtables.games.battleship.presenter.JoinTeamPresenterImpl;
import digitalsloths.socialtables.games.battleship.presenter.TeamManagementPresenter;
import digitalsloths.socialtables.games.battleship.presenter.TeamManagementPresenterImpl;
import digitalsloths.socialtables.games.utility.types.Team;
import digitalsloths.socialtables.games.utility.types.TeamImpl;
import digitalsloths.socialtables.types.Profile;

/**
 * name BattleshipManager.java
 * author Casotto Federico
 * date 27/05/2016
 * brief Vista per la personalizzazione del lista dei membri di un team.
 * use Utilizzata da TeamManagementPresenter e JoinTeamPresenter per visualizzare i membri di un team.
 */
public class MembersAdapter extends BaseAdapter {

    private static String TAG = "MembersAdapter";
    private static LayoutInflater _inflater = null;

    private Context _context;

    private Team _teamMembers;
    private JoinTeamPresenter _joinTeamPresenter;

    /**
     * @name MembersAdapter
     * @desc Costruttore per personalizzare aggiunta membro team;
     * @param {JoinTeamPresenter} joinTeamPresenter - Presenter di gestionea ggiunta membro team;
     * @memberOf Client.Games.Battleship.Presenter.MembersAdapter
     */
    public MembersAdapter(JoinTeamPresenter joinTeamPresenter) {

        this._context = joinTeamPresenter.getContext();
        this._teamMembers = joinTeamPresenter.getTeam();
        if(_teamMembers == null ){
           //Log.v(TAG, ".MembersAdapter() team is null");
        } else {
            _teamMembers.getTeammates().add(0, _teamMembers.getCaptain());
        }
        this._joinTeamPresenter = joinTeamPresenter;

        _inflater = (LayoutInflater)_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    /**
     * @name getCount
     * @desc Numero di righe del lista, ovvero numero membri;
     * @returns {int}
     * @memberOf Client.Games.Battleship.Presenter.MembersAdapter
     */
    @Override
    public int getCount() {
        if (_teamMembers.getTeammates() == null){
            return 0;
        } else {
            return _teamMembers.getTeammates().size();
        }
    }

    /**
     * @name getItem
     * @desc Oggetto della lista alla posizione richiesta;
     * @param {int} position - Posizione richiesta;
     * @returns {Object}
     * @memberOf Client.Games.Battleship.Presenter.MembersAdapter
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
     * @memberOf Client.Games.Battleship.Presenter.MembersAdapter
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
     * @memberOf Client.Games.Battleship.Presenter.MembersAdapter
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rowView = _inflater.inflate(R.layout.team_list_item_layout, null);

        final Profile member = _teamMembers.getTeammates().get(position);

       //Log.i(TAG,"Numbero Mem: " + _teamMembers.getTeammates().size() + " postizione: " + position);

        final ImageButton removeMember = (ImageButton) rowView.findViewById(R.id.deleteMemberButton);

        ImageView head = (ImageView) rowView.findViewById(R.id.teamRoleImage);
        if(member.equals(_teamMembers.getCaptain())){
            head.setImageResource(R.drawable.captain_head);
        }
        else {
            head.setImageResource(R.drawable.sailor_head);

        }

        TextView name = (TextView) rowView.findViewById(R.id.memberText);
        name.setText(member.getUsername());

        if(TeamManagementPresenter.class.isInstance(_joinTeamPresenter)) {
           //Log.v("getView", ".sei capitano");

            if(member.getId() == _teamMembers.getCaptain().getId()){
                removeMember.setVisibility(View.INVISIBLE);
            }
            final TeamManagementPresenterImpl teamManagementPresenter = (TeamManagementPresenterImpl) _joinTeamPresenter;
            removeMember.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    teamManagementPresenter.removeMember(member); //rimozione del membro dalla squadra
                   //Log.i(TAG,"Membro rimosso: " + member.getUsername());
                    updateTeam();
                   //Log.i(TAG, " onClick");
                }
            });
        }
        else {
           //Log.v("getView", ".sei marinaio");
            removeMember.setVisibility(View.INVISIBLE);
        }

        return rowView;

    }

    public void updateTeam() {
        _teamMembers = _joinTeamPresenter.getTeam();
        if(_teamMembers == null ){
           //Log.v(TAG, ".MembersAdapter() team is null");
            return ;
        } else {
            _teamMembers.getTeammates().add(0, _teamMembers.getCaptain());
        }
        notifyDataSetChanged();
    }

}
