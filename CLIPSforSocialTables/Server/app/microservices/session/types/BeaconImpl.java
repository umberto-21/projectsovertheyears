package microservices.session.types;

/**
 * Created by pablos on 6/3/16.
 */
public class BeaconImpl implements Beacon {
    private String _id;
    @Override
    public String getId() {
        return _id;
    }
}
