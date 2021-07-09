package digitalsloths.socialtables.games.battleship.model.communication;

import digitalsloths.socialtables.basefunctions.types.Beacon;
import digitalsloths.socialtables.types.Profile;

/**
 * Created by Ivan parise on 29/05/16.
 */
public interface ProfileCommunication {
    Profile getProfile(Beacon beacon);
}
