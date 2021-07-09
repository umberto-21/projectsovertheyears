package digitalsloths.socialtables.basefunctions.presenter;

import com.google.gson.Gson;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import digitalsloths.socialtables.R;
import digitalsloths.socialtables.basefunctions.types.errors.Error;
import digitalsloths.socialtables.basefunctions.types.errors.ErrorImpl;

/**
 * name ErrorPresenterImpl.java
 * author Pinton Federico
 * date 28/04/2016
 * brief La classe permette di visualizzare gli errori
 * use Viene utilittata per notificare all'utente gli errori riscontrati
 */
public class ErrorPresenterImpl extends Activity implements ErrorPresenter {
    private static final String TAG = "ErrorPresenterImpl";
    private Error _error;

    /**
     * @name onCreate
     * @desc Costruttore per l'activity di visualizzazione errori;
     * @param {Bundle} savedInstanceState - Oggetto per passare varie informazione utili all'Activity;
     * @returns {void}
     * @memberOf Client.BaseFunctions.Presenter.ErrorPresenterImpl
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.activity_error_view);

        TextView titleErrorView = (TextView)findViewById(R.id.titleErrorView);
        titleErrorView.setText("Environment Error");

        String jsonError = this.getIntent().getExtras().getString("error");
       //Log.v(TAG, "Errore da mostrare: " + jsonError);
        _error = (new Gson()).fromJson(jsonError, ErrorImpl.class);

        TextView contentErrorView = (TextView)findViewById(R.id.contentErrorView);
        contentErrorView.setText(_error.getError());
    }

    /**
     * @name close
     * @desc Metodo che gestisce la chiusura dei messaggi d'errore;
     * @param {View} view - Vista di riferimento nella quale far chiudere il messaggio d'errore;
     * @returns {void}
     * @memberOf Client.BaseFunctions.Presenter.ErrorPresenterImpl
     */
    @Override
    public void close(View view) {
        this.finish();
    }

    @Override
    public void onStop(){
        super.onStop();
        finish();
    }


    public static void show(Error error, Context context){
        //Log.i(TAG, ".show(Error)");
        Intent errorIntent = new Intent(context,ErrorPresenterImpl.class);
        Gson gson = new Gson();
        errorIntent.putExtra("error", gson.toJson(error));
        context.startActivity(errorIntent);
    }

}
