package digitalsloths.socialtables.profile.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import digitalsloths.socialtables.R;

/**
 * Created by SacconDaniele on 20/04/2016.
 */
public class ProfileViewImpl extends AppCompatActivity implements ProfileView{

    private String _name;
    private digitalsloths.socialtables.profile.presenter.ProfilePresenter _profilePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_view);
    }

    @Override
    public String getName(){
        return _name;
    }

    @Override
    public void setName(String name){
        _name = name;
    }

    @Override
    public void changeUsername(View view) {
       /* EditText edit = (EditText)view.findViewById(R.id.newUsernameText);
        final String username = edit.getText().toString();
        if (_profilePresenter.changeUsername(//username)){
            setName(username);
            //aggiornamento interfaccia
        }else{
            //errore e ritorno a fargli vedere l'username vecchio
        }*/

    }
}
