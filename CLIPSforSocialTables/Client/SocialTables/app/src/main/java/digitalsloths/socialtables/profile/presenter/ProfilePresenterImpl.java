package digitalsloths.socialtables.profile.presenter;


import com.google.gson.Gson;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import digitalsloths.socialtables.R;
import digitalsloths.socialtables.basefunctions.presenter.ErrorPresenterImpl;
import digitalsloths.socialtables.basefunctions.types.errors.Error;
import digitalsloths.socialtables.basefunctions.types.errors.ErrorImpl;
import digitalsloths.socialtables.basefunctions.types.errors.ServerErrorImpl;
import digitalsloths.socialtables.profile.model.ProfileModel;
import digitalsloths.socialtables.profile.model.ProfileModelImpl;
import digitalsloths.socialtables.profile.model.communication.ProfileCommunicationImpl;
import digitalsloths.socialtables.types.Profile;

/**
 * name ProfilePresenterImpl.java
 * author Casotto Federico
 * date 16/05/2016
 * brief Gestisce l'application//Logic di tutte le funzionalità legate al profilo
 * use Viene utilizzata per gestire l'application//Logic di tutte le funzionalità legate al profilo
 */
public class ProfilePresenterImpl extends AppCompatActivity implements  ProfilePresenter{

    /*
     * riferimento al model sottostante l'Activity, contiene info profilo utente
     */
    private ProfileModel _profileModel = new ProfileModelImpl(new ProfileCommunicationImpl());

    /**
     * @name onCreate
     * @desc Costruttore, costruisce il model Model che poi recupera il nome;
     * @param {Bundle} savedInstanceState - Oggetto per passare varie informazione utili all'Activity;
     * @returns {void}
     * @memberOf Client.Profile.Presenter.ProfilePresenterImpl
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_view);

        final TextView actualName = (TextView) findViewById(R.id.actualUsername);
        Profile profile = _profileModel.getProfile();
        if (profile != null)
            actualName.setText(profile.getUsername());
        else {
            Error error = new ErrorImpl("Error to create a new profile");
            show(error);
            this.finish();
        }


    }

    /**
     * @name show
     * @desc Metodo che visualizza un errore sulla gestione profilo;
     * @param {Error} error - Errore sul profilo da visualizzare;
     * @returns {void}
     * @memberOf Client.Profile.Presenter.ProfilePresenterImpl
     */
    private void show(Error error){
        Intent errorIntent = new Intent(this.getApplicationContext(),ErrorPresenterImpl.class);
        Gson gson = new Gson();
        errorIntent.putExtra("error", gson.toJson(error));
        startActivity(errorIntent);
    }


    /**
     * @name changeUsername
     * @desc Metodo che richiama il model per il cambio username e visualizza eventuali errori;
     * @param {View} view - Vista di riferimento sulla quale visualizzare gli errori;
     * @returns {void}
     * @memberOf Client.Profile.Presenter.ProfilePresenterImpl
     */
    @Override
    public void changeUsername(View view) {

        final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle("Profile Error");

        final EditText edit = (EditText) findViewById(R.id.newUsernameText);
        final String username = edit.getText().toString();

        final AlertDialog profile_alert = alertBuilder.create();

        if(!username.isEmpty()) {
            if(_profileModel.changeUsername(username)) {
                TextView actualName = (TextView) findViewById(R.id.actualUsername);
                actualName.setText(username);

            }else{
                profile_alert.setMessage("This username has already taken!!");
                profile_alert.show();
            }
        }else{
            profile_alert.setMessage("The input can't be empty!!");
            profile_alert.show();
        }

    }

}
