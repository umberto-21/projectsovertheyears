package microservices.profile.business;

import microservices.profile.types.Beacon;
import microservices.profile.types.Profile;
import microservices.profile.types.Session;

/**
 * Created by umberto il magnifico on 5/24/16.
 */
public interface ProfileBusiness {

    /**
     * @name changeProfile
     * @desc Cambia i dati del profilo indicato dal primo parametro con quelli indicati dal secondo parametro;
     * chiama il metodo changeProfile (userProfile : Profile, updateProfile : Profile) : boolean sull'istanza di
     * ProfileServices;
     * @param {Profile} userProfile - Rappresenta il profilo da aggiornare;
     * @param {Profile} updateProfile - Rappresenta i dati aggiornati da assegnare all'utente;
     * @returns {boolean}
     * @memberOf Server.Microservices.Profile.Business.ProfileBusiness
     */
    public boolean changeProfile (Profile userProfile, Profile updateProfile);

    /**
     * @name createNewProfile
     * @desc Ritorna un profilo nuovo associandolo alla sessione; \begin{itemize} \item{ottiene un'istanza del
     * singleton ProfileServices;} \item{chiama sull'istanza il metodo getNewProfile} \item{ritorna il profilo o
     * null nel caso in cui la creazione del profilo sia fallita} \end{itemize}
     * @param {Session} session - Rappresenta la sessione associata all'utente;
     * @param {Beacon} beacon - Rappresenta il beacon associato all'utente;
     * @returns {Profile}
     * @memberOf Server.Microservices.Profile.Business.ProfileBusiness
     */
    public Profile createNewProfile (Session session, Beacon beacon);

    /**
     * @name getProfile
     * @desc Ritorna il profilo associato alla sessione; \begin{itemize} \item{ottiene un'istanza del singleton
     * ProfileServices} \item{chiama sull'istanza il metodo getProfile} \item{ritorna il profilo o ritorna il
     * risultato di createNewProfile se il profilo è null} \end{itemize}
     * @param {Session} session - Rappresenta la sessione associata all'utente;
     * @returns {Profile}
     * @memberOf Server.Microservices.Profile.Business.ProfileBusiness
     */
    public Profile getProfile (Session session, Beacon beacon);


    /**
     * @name getProfile
     * @desc chiama sull'istanza dell'oggetto ProfileServices il metodo getProfile (session : Session) : Profile; ritorna il risultato;
     * @param {Session} session - riferimento all'oggetto di tipo Session;
     * @returns {Profile}
     * @memberOf Server.Microservices.Profile.Business.ProfileBusiness
     */
    public Profile getProfile (Session session);


    /**
     * @name updateLocation
     * @desc Aggiorna il Beacon associato all'utente; \begin{itemize} \item{ottiene un'istanza del singleton
     * ProfileServices} \item{chiama sull'istanza il metodo updateLocation (profile : Profile, beacon : Beacon) :
     * boolean} \end{itemize}
     * @param {Profile} profile - Rappresenta il profilo associato all'utente;
     * @param {Beacon} beacon - Rappresenta il beacon associato all'utente;
     * @returns {void}
     * @memberOf Server.Microservices.Profile.Business.ProfileBusiness
     */
    public void updateLocation (Profile profile, Beacon beacon);



}
