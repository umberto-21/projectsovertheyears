package digitalsloths.socialtables.types;


import android.util.Log;

import com.google.gson.Gson;

/**
 *file ProfileImpl.java
 *author Andria Umberto
 *date 28/05/2016
 *brief
 *use
 */

public class ProfileImpl implements Profile {

    private static final String TAG = "ProfileImpl";
    private String _username;
    private Integer _id;

    /**
     * @name ProfileImpl
     * @desc costruttore
     * @param {String} username - _username
     * @memberOf Client.Types.ProfileImpl
     */
    public ProfileImpl (String username) {
       //Log.v(TAG, ".ProfileImpl(" + (new Gson()).toJson(username) + ")");
        _username =username;
       //Log.v(TAG, ".ProfileImpl(" + (new Gson()).toJson(username) + ")");
    }

    @Override
    public String getUsername() {
       //Log.v(TAG, ".getUsername()");
       //Log.v(TAG, ".getUsername() return: " + (new Gson()).toJson(_username));
        return _username;
    }
    @Override
    public void setUsername (String username){
       //Log.v(TAG, ".setUsername(" + (new Gson()).toJson(username) + ")");
        _username = username;
       //Log.v(TAG, ".setUsername(" + (new Gson()).toJson(username) + ")");
    }

    @Override
    public int getId() {
        return _id;
    }

    @Override
    public boolean equals(Profile p){
        return this._username == p.getUsername() && this._id == p.getId();
    }
}
