package org.esupportail.sifacmissions.ws;

import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Stub;
import org.esupportail.sifacmissions.domain.beans.Mission;
import org.esupportail.sifacmissions.domain.beans.MissionDetails;
import org.esupportail.sifacmissions.domain.beans.Periode;
import org.esupportail.sifacmissions.utils.Categorizer;
import org.esupportail.sifacmissions.ws.generated.ZWEB_SERVICE_PORTAILServiceLocator;
import org.esupportail.sifacmissions.ws.generated.ZWEB_SERVICE_PORTAILSoapBindingStub;
import org.esupportail.sifacmissions.ws.generated.ZZPORTSTT_BAPI_MISSION;
import org.esupportail.sifacmissions.ws.generated.ZZPORTSTT_BAPI_MISSION_DETAIL;
import org.esupportail.sifacmissions.ws.generated.holders.TABLE_OF_BAPIRET2Holder;
import org.esupportail.sifacmissions.ws.generated.holders.TABLE_OF_ZZPORTSTT_BAPI_MISSIONHolder;
import org.esupportail.sifacmissions.ws.generated.holders.TABLE_OF_ZZPORTSTT_BAPI_MISSION_DETAILHolder;
import org.springframework.util.StringUtils;

/**
 * Cette classe permet d'assurer une cohérence entre le code de l'application et
 * celui généré à partir des WSDL de SIFAC. Il s'agit d'un proxy pour le web
 * service PORTAIL.
 * 
 * @author Florent Cailhol (Anyware Services)
 */
public class SifacPortailService {

	private final String endpoint;

	private final String username;

	private final String password;

	private Categorizer categorizer;

	private ZWEB_SERVICE_PORTAILSoapBindingStub stub;

	private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * Constructeur.
	 * 
	 * @param endpoint Adresse du web service
	 */
	public SifacPortailService(String endpoint) {
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
	public SifacPortailService(String endpoint, String username, String password) {
		this.endpoint = endpoint;
		this.username = username;
		this.password = password;
	}

	/**
	 * @param categorizer Service de détection des catégories
	 */
	public void setCategorizer(Categorizer categorizer) {
		this.categorizer = categorizer;
	}

	/**
	 * Proxy vers la méthode MISSION_PORTAIL.
	 * 
	 * @param matricule Paramètre MATRICULE
	 * @param nom Paramètre NOM
	 * @param prenom Paramètre PRENOM
	 * @param annee Paramètre ANNEE
	 * @return Liste de missions
	 * @throws ServiceException
	 * @throws RemoteException
	 * @throws ParseException
	 */
	public List<Mission> getFraisMissions(String matricule, String nom, String prenom, String annee) throws ServiceException, RemoteException, ParseException {
		List<Mission> missionsList = new ArrayList<Mission>();

		TABLE_OF_ZZPORTSTT_BAPI_MISSIONHolder missionsHolder = new TABLE_OF_ZZPORTSTT_BAPI_MISSIONHolder();
		TABLE_OF_BAPIRET2Holder ret = new TABLE_OF_BAPIRET2Holder();

		getStub().z_ZPORTSMF_MISSION_PORTAIL(annee, matricule, missionsHolder, nom, prenom, ret);

		if (ret.value.getItem() != null && ret.value.getItem().length > 0) {
			ZZPORTSTT_BAPI_MISSION[] missionsArray = missionsHolder.value.getItem();

			if (missionsArray != null && missionsArray.length > 0) {
				for (int i = 0; i < missionsArray.length; i++) {
					Mission mission = new Mission();

					Periode periode = new Periode();
					periode.setDebut(formatter.parse(missionsArray[i].getDATE_DEBUT()));
					periode.setFin(formatter.parse(missionsArray[i].getDATE_FIN()));

					mission.setPeriode(periode);
					mission.setNumero(StringUtils.trimWhitespace(missionsArray[i].getNUMERO()));
					mission.setMotif(StringUtils.trimWhitespace(missionsArray[i].getMOTIF()));
					mission.setMontant(missionsArray[i].getMNT_TOT_MISSION());
					mission.setRemboursement(missionsArray[i].getMNT_REMB_MISSION());
					mission.setOrdre(Long.parseLong(StringUtils.trimWhitespace(missionsArray[i].getNUMERO())));

					if (StringUtils.hasText(missionsArray[i].getDATE_PRISE_EN_COMPTE_COMPTABLE())) {
						mission.setDate(formatter.parse(missionsArray[i].getDATE_PRISE_EN_COMPTE_COMPTABLE()));
					}

					missionsList.add(mission);
				}
			}
		}

		return missionsList;
	}

	/**
	 * Proxy vers la méthode MISSION_DETAIL.
	 * 
	 * @param matricule Paramètre MATRICULE
	 * @param numeroMission Paramètre NO_MISSION
	 * @return Liste des frais
	 * @throws RemoteException
	 * @throws ServiceException
	 */
	public List<MissionDetails> getMissionDetails(String matricule, String numeroMission) throws RemoteException, ServiceException {
		TABLE_OF_ZZPORTSTT_BAPI_MISSION_DETAILHolder detailsHolder = new TABLE_OF_ZZPORTSTT_BAPI_MISSION_DETAILHolder();
		TABLE_OF_BAPIRET2Holder ret = new TABLE_OF_BAPIRET2Holder();

		getStub().z_ZPORTSMF_MISSION_DETAIL(detailsHolder, matricule, numeroMission, ret);

		ZZPORTSTT_BAPI_MISSION_DETAIL[] detailsArray = detailsHolder.value.getItem();
		List<MissionDetails> detailsList = new ArrayList<MissionDetails>();

		if (detailsArray != null && detailsArray.length > 0) {
			for (int i = 0; i < detailsArray.length; i++) {
				MissionDetails details = new MissionDetails();

				String catCode = detailsArray[i].getCAT_FRAIS_DEPL();
				String catDesc = detailsArray[i].getDES_FRAIS_DEPL();

				details.setCategorie(categorizer.getCategory(catCode, catDesc));
				details.setDescription(StringUtils.trimWhitespace(catDesc));
				details.setMontant(detailsArray[i].getMONTANT());
				details.setPaid(detailsArray[i].getPAYE_SOCIETE().equals("X"));

				detailsList.add(details);
			}
		}

		return detailsList;
	}
	
	private ZWEB_SERVICE_PORTAILSoapBindingStub getStub() throws ServiceException {
		if (stub == null) {
			ZWEB_SERVICE_PORTAILServiceLocator loc = new ZWEB_SERVICE_PORTAILServiceLocator();
			loc.setZWEB_SERVICE_PORTAILSoapBindingEndpointAddress(endpoint);

			stub = (ZWEB_SERVICE_PORTAILSoapBindingStub) loc.getZWEB_SERVICE_PORTAILSoapBinding();

			if (username != null && password != null) {
				stub._setProperty(Stub.USERNAME_PROPERTY, username);
				stub._setProperty(Stub.PASSWORD_PROPERTY, password);
			}
		}

		return stub;
	}

}
