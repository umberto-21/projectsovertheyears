package digitalsloths.socialtables.basefunctions.types;

/**
 * Created by Federico on 24/05/2016.
 */
public abstract class ModuleImpl implements Module {

    private String _name;

    public ModuleImpl(String name) {
        _name=name;
    }

    /*
     * method that return the String value associates with struct
     *
     * @return the value of the module name
    */
    @Override
    public String getName() {
        return _name;
    }

    /*
     * undefined method that set the name defines operations
     *
     * @param _module String name of operations to set
     */
    public void setName(String name){
        _name=name;
    }

    public boolean equals(Module m){
        return _name==m.getName();
    }
}
