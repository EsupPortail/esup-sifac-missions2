package org.esupportail.sifacmissions.ws;

import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Stub;
import org.apache.commons.lang.StringUtils;
import org.esupportail.sifacmissions.domain.Categorizer;
import org.esupportail.sifacmissions.domain.beans.Mission;
import org.esupportail.sifacmissions.domain.beans.MissionDetails;
import org.esupportail.sifacmissions.domain.beans.Periode;
import org.esupportail.sifacmissions.ws.holders.TABLE_OF_BAPIRET2Holder;
import org.esupportail.sifacmissions.ws.holders.TABLE_OF_ZZPORTSTT_BAPI_MISSIONHolder;
import org.esupportail.sifacmissions.ws.holders.TABLE_OF_ZZPORTSTT_BAPI_MISSION_DETAILHolder;

public class SifacPortailService {

	private String endpoint;
	private String username;
	private String password;

	private Categorizer categorizer;
	private ZWEB_SERVICE_PORTAILSoapBindingStub stub;
	private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

	public SifacPortailService(String endpoint, String username, String password) {
		this.endpoint = endpoint;
		this.username = username;
		this.password = password;
	}
	
	public void setCategorizer(Categorizer categorizer) {
		this.categorizer = categorizer;
	}

	private ZWEB_SERVICE_PORTAILSoapBindingStub getStub()
			throws ServiceException {
		if (stub == null) {
			ZWEB_SERVICE_PORTAILServiceLocator loc = new ZWEB_SERVICE_PORTAILServiceLocator();
			loc.setZWEB_SERVICE_PORTAILSoapBindingEndpointAddress(endpoint);

			stub = (ZWEB_SERVICE_PORTAILSoapBindingStub) loc
					.getZWEB_SERVICE_PORTAILSoapBinding();

			if (username != null && password != null) {
				stub._setProperty(Stub.USERNAME_PROPERTY, username);
				stub._setProperty(Stub.PASSWORD_PROPERTY, password);
			}
		}

		return stub;
	}

	public List<Mission> getFraisMissions(String matricule, String nom,
			String prenom, String year) throws ServiceException,
			RemoteException, ParseException {
		List<Mission> missionsList = new ArrayList<Mission>();

		TABLE_OF_ZZPORTSTT_BAPI_MISSIONHolder missionsHolder = new TABLE_OF_ZZPORTSTT_BAPI_MISSIONHolder();
		TABLE_OF_BAPIRET2Holder ret = new TABLE_OF_BAPIRET2Holder();

		getStub().z_ZPORTSMF_MISSION_PORTAIL(year, matricule, missionsHolder, nom,
				prenom, ret);

		if (ret.value.getItem().length > 0) {
			ZZPORTSTT_BAPI_MISSION[] missionsArray = missionsHolder.value.getItem();

			for (int i = 0; i < missionsArray.length; i++) {
				Mission mission = new Mission();

				Periode periode = new Periode();
				periode.setDebut(formatter.parse(missionsArray[i].getDATE_DEBUT()));
				periode.setFin(formatter.parse(missionsArray[i].getDATE_FIN()));

				mission.setPeriode(periode);
				mission.setNumero(missionsArray[i].getNUMERO());
				mission.setMotif(missionsArray[i].getMOTIF());
				mission.setMontant(missionsArray[i].getMNT_TOT_MISSION());
				mission.setRemboursement(missionsArray[i].getMNT_REMB_MISSION());
				mission.setOrdre(Long.parseLong(missionsArray[i].getNUMERO()));
				
				mission.setDetails(getMissionDetails(matricule, mission.getNumero()));
				
				if (StringUtils.isNotBlank(missionsArray[i].getDATE_PRISE_EN_COMPTE_COMPTABLE())) {
					mission.setDate(formatter.parse(missionsArray[i].getDATE_PRISE_EN_COMPTE_COMPTABLE()));
				}

				missionsList.add(mission);
			}
		}

		return missionsList;
	}
	
	public List<MissionDetails> getMissionDetails(String matricule, String numeroMission) throws RemoteException, ServiceException
	{
		TABLE_OF_ZZPORTSTT_BAPI_MISSION_DETAILHolder detailsHolder = new TABLE_OF_ZZPORTSTT_BAPI_MISSION_DETAILHolder();
		TABLE_OF_BAPIRET2Holder ret = new TABLE_OF_BAPIRET2Holder();
		
		getStub().z_ZPORTSMF_MISSION_DETAIL(detailsHolder, matricule, numeroMission, ret);
		
		ZZPORTSTT_BAPI_MISSION_DETAIL[] detailsArray = detailsHolder.value.getItem();
		List<MissionDetails> detailsList = new ArrayList<MissionDetails>();
		
		if (detailsArray != null && detailsArray.length > 0) {
			for (int i = 0; i < detailsArray.length; i++) {
				MissionDetails details = new MissionDetails();
				
				details.setCategorie(categorizer.getCategory(detailsArray[i].getCAT_FRAIS_DEPL()));
				details.setDescription(detailsArray[i].getDES_FRAIS_DEPL());
				details.setMontant(detailsArray[i].getMONTANT());
				details.setPaid(detailsArray[i].getPAYE_SOCIETE().equals("X"));
				
				detailsList.add(details);
			}
		}
		
		return detailsList;
	}

}
