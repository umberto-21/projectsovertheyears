package digitalsloths.socialtables.basefunctions.types;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import digitalsloths.socialtables.profile.model.ProfileModel;
import digitalsloths.socialtables.profile.view.ProfileViewImpl;

/**
 * name ProfileModule.java
 * author Pinton Federico
 * date 25/05/2016
 * brief Gestisce le informazioni che rappresentano un profilo;
 * use Viene utilizzata per gestire le informazioni riguardanti un profilo;
 */
public class ProfileModule extends ModuleImpl {
    private static final String TAG = "ProfileModule";
    public ProfileModule(String name){
        super(name);
       //Log.v(TAG, ".ProfileModule()");
    }

    @Override
    public void start(Context _context) {
       //Log.v(TAG, ".start(Context)");
        Intent profileManager = new Intent(_context, ProfileViewImpl.class);
        profileManager.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        _context.startActivity(profileManager);
    }
}
