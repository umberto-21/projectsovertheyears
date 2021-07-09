package digitalsloths.socialtables.types;

import android.util.Log;

import com.google.gson.Gson;

/**
 *file SessionImpl.java
 *author Andria Umberto
 *date 28/05/2016
 *brief
 *use
 */
public class SessionImpl implements Session {

    private static final String TAG = "SessionImpl";
    private int _id;

    /**
     * @name SessionImpl
     * @desc costruttore;
     * @param {int} id - \_id
     * @memberOf Client.Types.SessionImpl
     */
    public SessionImpl (int id) {
       //Log.v(TAG, ".SessionImpl(" + (new Gson()).toJson(id) + ")");
        _id =id;
       //Log.v(TAG, ".SessionImpl(" + (new Gson()).toJson(id) + ")");
    }

    @Override
    public int getId() {
       //Log.v(TAG, ".getId()");
       //Log.v(TAG, ".getId() return: " + (new Gson()).toJson(_id));
        return _id;
    }

    @Override
    public boolean equals (Session s){
        return this._id == s.getId();
    }
}
