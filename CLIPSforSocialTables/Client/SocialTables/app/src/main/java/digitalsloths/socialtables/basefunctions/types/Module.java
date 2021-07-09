package digitalsloths.socialtables.basefunctions.types;

import android.content.Context;

/**
 *
 * interface indicate the operation to complete
 *
 * @file Module.java
 * @author Federico Casotto
 * @date 24/05/2016
 * @use define the operation to process
 */
public interface Module {

    /*
     * undefined method that return the enum struct
     *
     * @return _module enum struct
     */
    public String getName();

    public void setName(String name);

    public void start(Context context);

    public boolean equals(Module m);
}
