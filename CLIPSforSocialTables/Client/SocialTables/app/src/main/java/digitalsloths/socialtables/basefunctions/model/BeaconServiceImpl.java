package digitalsloths.socialtables.basefunctions.model;

import com.google.gson.Gson;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import digitalsloths.socialtables.basefunctions.types.Beacon;
import digitalsloths.socialtables.basefunctions.types.BeaconImpl;

/**
 * name Communication.java
 * author Ugo Padoan
 * date 06/06/2016
 * brief Classe che implementa l'nterfaccia che gestisce le funzionalità legate alla lettura dei beacon;
 * Utilizzata da EnviromentServiceModelImpl per fornire i beacon;
 */
public class BeaconServiceImpl extends Service implements BeaconService , BeaconConsumer {
    private static final String TAG = "BeaconServiceImpl";

    /*
     * contesto nella quale agisce il Service
     */
    private Context _context = this;

    /*
     * classe principale per la lettura dei Beacon
     */
    private BeaconManager _beaconManager;

    /*
     * Beacon più vicino individuato
     */
    private Beacon _nearestBeacon; // = new BeaconImpl("0xf7826da64fa24e988024bc5b71e0893a", 6, 52); // TODO: 6/15/16 se la lettura beacon non va gli do un tavolo di default

    /*
     * classe creata per effettuare il bind ad una Activity
     */
    private IBinder _binder = new BeaconServiceBinder();

    //(k) ======================================
    private static final int _READING = 5;

    //Beacon letti ====================================
    private List<org.altbeacon.beacon.Beacon> _readingStash;

    /*
     * classe interna per fornire il bind
    */
    public class BeaconServiceBinder extends Binder {
        public BeaconService getService(){
            return BeaconServiceImpl.this;
        }
    }

    /*
     * regione della quale fanno parte i Beacon del gruppo
     */
    private Region _region = new Region("myRangingUniqueId", null, null, null);

    /*
    * class per fare il bind con una activity
    */
    @Override
    public IBinder onBind(Intent intent) {
       //Log.v(TAG, ".onBind(...)");
        startOperation();
       //Log.v(TAG, ".onBind(...) return");
        return _binder;
    }


    /*
     * metodo per avvia il monitoraggio dei Beacon all'avvio del Service
     */
//    public int onStartCommand(Intent intent, int flaps, int startId){
    private void startOperation(){
       //Log.v(TAG,".startOperation()");

        _readingStash = new ArrayList<>(); //inzializzo array ====================================

        _beaconManager = BeaconManager.getInstanceForApplication(_context);
        _beaconManager.getBeaconParsers()
                .add(new BeaconParser()
                        .setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25"));
        _beaconManager.bind(this);


       //Log.v(TAG,".startOperation() return");
    }

    /*
     * fermo il monitoraggio nel caso avvenga un chiusura del Service
     */
    @Override
    public void onDestroy() {
       //Log.v(TAG,".onDestroy()");
        super.onDestroy();
        try {
           //Log.v(TAG,"stopRangingBeaconsInRegion");
            _beaconManager.stopRangingBeaconsInRegion(_region);
           //Log.v(TAG,"stopMonitoringBeaconsInRegion");
            _beaconManager.stopMonitoringBeaconsInRegion(_region);
        } catch(RemoteException e) {
           //Log.v(TAG,"RemoteException e");
            e.printStackTrace();
        }
        _beaconManager.unbind(this);
       //Log.v(TAG,"onDestroy() return");
    }


    /*
     * metodo che si occupa di rilevare i Beacon nella regione
     */
    @Override
    public void onBeaconServiceConnect() {

       //Log.v(TAG,"onBeaconServiceConnect()");

        _beaconManager.setRangeNotifier(new RangeNotifier() {

            @Override
            public void didRangeBeaconsInRegion(Collection<org.altbeacon.beacon.Beacon> beacons, Region region) {
               //Log.v(TAG, "didRangeBeaconsInRegion beacon letti: " + beacons.size());
                if (beacons.size() > 0) {
                    saveState(beacons.iterator().next());
                   //Log.v(TAG, "inizializzo _nearestbeacon");
                }
            }



        });

        try {
           //Log.v(TAG,"start ranging");
            _beaconManager.startRangingBeaconsInRegion(_region);
        } catch (RemoteException e) {
           //Log.v(TAG,"RemoteException e");
            e.printStackTrace();
        }
    }
    //=========================================================================//
    private void saveState(org.altbeacon.beacon.Beacon beacon) {

        if(_readingStash.size() >= _READING) {

            Map<org.altbeacon.beacon.Beacon,Double> distances = new HashMap<>(); //mappa distanze
            Map<org.altbeacon.beacon.Beacon,Integer> times = new HashMap<>(); //mappa numero di volte letto stesso Beacon
            for(org.altbeacon.beacon.Beacon b:_readingStash) { //numero di Beacon letti
                if(!distances.containsKey(b)) { //se distanze non lo contiene lo salvo
                    distances.put(b, b.getDistance());
                    times.put(b, 1);
                }else { //altrimenti incremento contatore distanza e numero di volte letto
                    distances.put(b, distances.get(b) + b.getDistance());
                    times.put(b, times.get(b) + 1);
                }
            }

            Map<org.altbeacon.beacon.Beacon,Double> avarages = new HashMap<>(); //mappa per ottenere media distanze
            for(org.altbeacon.beacon.Beacon b:distances.keySet())
                avarages.put(b,distances.get(b) / times.get(b)); //calcolo le medie per Beacon letto

            org.altbeacon.beacon.Beacon nearest = null; //beacon più vicino per distanza media
            double min_distance = Double.MAX_VALUE; //distanza media
            for (org.altbeacon.beacon.Beacon b:avarages.keySet()) {
                if(min_distance > avarages.get(b)) { //se distanza media più grande della distanza del Beacon salvo la nuova distanza
                    nearest = b;
                    min_distance = avarages.get(b);
                }
            }

            _nearestBeacon = new BeaconImpl(nearest);
            _readingStash.clear(); //cancello tutti i valori salvati

        }else {
            if (_nearestBeacon == null) _nearestBeacon = new BeaconImpl(beacon); //se prima lettura salvo semplicemente
            _readingStash.add(beacon); //poi aggiungo all'array di letture
           //Log.i(TAG,"Lettura numero: " + _readingStash.size());
        }

    }

    @Override
    public Context getApplicationContext() {
        return _context;
    }

    /**
     * @name getNearestBeacon
     * @desc Ritorna il Beacon più vicino; (Null se non trova niente)
     * @returns {Client::BaseFunction::Types::Beacon}
     * @memberOf Client.BaseFunctions.Model.EnviromentServiceModel
     */
    @Override
    public Beacon getNearestBeacon() {
       //Log.v(TAG,"getNearestBeacon()");
       //Log.v(TAG,"getNearestBeacon() return: " + (new Gson()).toJson(_nearestBeacon));
        return _nearestBeacon;
    }

}