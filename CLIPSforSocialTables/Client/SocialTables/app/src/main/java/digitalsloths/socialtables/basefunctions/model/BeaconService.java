package digitalsloths.socialtables.basefunctions.model;

import digitalsloths.socialtables.basefunctions.types.Beacon;

/**
 * name Communication.java
 * author Ugo Padoan
 * date 06/06/2016
 * brief Interfaccia che gestisce le funzionalità legate alla lettura dei beacon;
 * Utilizzata da EnviromentServiceModelImpl per fornire i beacon;
 */
public interface BeaconService {
    /**
     * @name getNearestBeacon
     * @desc Ritorna il Beacon più vicino; (Null se non trova niente)
     * @returns {Client::BaseFunction::Types::Beacon}
     * @memberOf Client.BaseFunctions.Model.EnviromentServiceModel
     */
    public Beacon getNearestBeacon();
}
