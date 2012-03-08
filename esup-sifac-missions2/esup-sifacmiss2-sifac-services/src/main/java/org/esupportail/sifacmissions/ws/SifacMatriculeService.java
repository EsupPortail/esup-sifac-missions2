package org.esupportail.sifacmissions.ws;

import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Stub;
import org.esupportail.sifacmissions.ws.holders.TABLE_OF_ZZPORTSTT_BAPI_MISSIONHolder;

public class SifacMatriculeService {

	private String endpoint;
	private String username;
	private String password;
	
	private ZWEB_SERVICE_MATRICULESoapBindingStub stub;
	
	public SifacMatriculeService(String endpoint, String username, String password)
	{
		this.endpoint = endpoint;
		this.username = username;
		this.password = password;
	}
	
	private ZWEB_SERVICE_MATRICULESoapBindingStub getStub() throws ServiceException
	{
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
	
	public String getMatricule(String id) throws RemoteException, ServiceException {
		TABLE_OF_ZZPORTSTT_BAPI_MISSIONHolder ret = new TABLE_OF_ZZPORTSTT_BAPI_MISSIONHolder();
		getStub().z_ZPORTSMF_MATRICULE(id, ret);
		
		if (ret.value.getItem().length > 0) {
			return ret.value.getItem(0).getMATRICULE();
		}
		
		return null;
	}

}
