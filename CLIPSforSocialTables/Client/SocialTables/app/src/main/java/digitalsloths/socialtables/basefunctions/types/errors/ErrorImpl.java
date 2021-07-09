package digitalsloths.socialtables.basefunctions.types.errors;

import android.util.Log;

/**
 * Created by pablos on 6/5/16.
 */
public class ErrorImpl implements Error {
    private static final String TAG = "ErrorImpl";
    private String _text;

    public ErrorImpl(String text) {
       //Log.v(TAG, ".ErrorImpl("+ text +")");
        _text = text;
    }
    @Override
    public String getError() {
       //Log.v(TAG, ".ErrorImpl("+ _text +")");
        return _text;
    }
}
