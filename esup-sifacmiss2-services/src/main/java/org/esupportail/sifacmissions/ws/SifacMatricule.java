package org.esupportail.sifacmissions.ws;

import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import org.esupportail.sifacmissions.ws.generated.ZWEB_SERVICE_MATRICULEServiceLocator;
import org.esupportail.sifacmissions.ws.generated.ZWEB_SERVICE_MATRICULESoapBindingStub;
import org.esupportail.sifacmissions.ws.generated.holders.TABLE_OF_ZZPORTSTT_BAPI_MISSIONHolder;

import org.apache.axis.client.Stub;

/**
 * Cette classe permet d'assurer une cohérence entre le code de l'application et
 * celui généré à partir des WSDL de Sifac. Il s'agit d'un proxy pour le web
 * service MATRICULE.
 *
 * @author Florent Cailhol (Anyware Services)
 */
public class SifacMatricule {

    private final String endpoint;
    private final String username;
    private final String password;

    private ZWEB_SERVICE_MATRICULESoapBindingStub stub;

    /**
     * Constructeur.
     *
     * @param endpoint Adresse du web service
     */
    public SifacMatricule(String endpoint) {
        this(endpoint, null, null);
    }

    /**
     * Constructeur à utiliser si le web service requiert une identification
     * HTTP de type BASIC.
     *
     * @param endpoint Adresse du web service
     * @param username
     * @param password
     */
    public SifacMatricule(String endpoint, String username, String password) {
        this.endpoint = endpoint;
        this.username = username;
        this.password = password;
    }

    /**
     * Proxy vers la méthode MATRICULE.
     *
     * @param login Paramètre LOGIN
     * @return Champ MATRICULE
     * @throws RemoteException
     * @throws ServiceException
     */
    public String getMatricule(String login) throws RemoteException, ServiceException {
        TABLE_OF_ZZPORTSTT_BAPI_MISSIONHolder ret = new TABLE_OF_ZZPORTSTT_BAPI_MISSIONHolder();
        getStub().z_ZPORTSMF_MATRICULE(login, ret);

        if (ret.value.getItem().length > 0) {
            return ret.value.getItem(0).getMATRICULE();
        }

        return null;
    }

    private ZWEB_SERVICE_MATRICULESoapBindingStub getStub() throws ServiceException {
        if (stub == null) {
            ZWEB_SERVICE_MATRICULEServiceLocator loc = new ZWEB_SERVICE_MATRICULEServiceLocator();
            loc.setZWEB_SERVICE_MATRICULESoapBindingEndpointAddress(endpoint);

            stub = (ZWEB_SERVICE_MATRICULESoapBindingStub) loc.getZWEB_SERVICE_MATRICULESoapBinding();

            if (username != null && password != null) {
                stub._setProperty(Stub.USERNAME_PROPERTY, username);
                stub._setProperty(Stub.PASSWORD_PROPERTY, password);
            }
        }

        return stub;
    }

}
