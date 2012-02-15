/**
 * ESUP-Portail Blank Application - Copyright (c) 2010 ESUP-Portail consortium.
 */
package org.esupportail.sifacmissions.domain;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.util.List;

import javax.xml.rpc.ServiceException;

import org.esupportail.sifacmissions.domain.beans.Mission;
import org.esupportail.sifacmissions.domain.beans.User;

/**
 * @author Yves Deschamps (Universite de Lille 1) - 2010
 * 
 */
public interface DomainService extends Serializable
{
    /**
     * @param uid
     * @return a user.
     */
    User getUser(String uid);

    /**
     * @return the first year of SIFAC application.
     */
    Integer getFirstYear();

    /**
     * @param id
     * @return the name of user (without accent).
     */
    String getNom(String id);

    /**
     * @param id
     * @return the given name of user (without accent).
     */
    String getPrenom(String id);

    /**
     * @param matricule
     * @param nom
     * @param prenom
     * @param year
     * @return a list of frais missions.
     * @throws RemoteException
     * @throws ParseException
     * @throws ServiceException
     */
    List<Mission> getFraisMissions(String matricule, String nom, String prenom, Integer year) throws RemoteException, ParseException, ServiceException;

    /**
     * @param user
     * @return true if homonyme.
     */
    Boolean isHomonyme(User user);

    /**
     * @return the matricule service.
     */
    MatriculeService getMatriculeService();
}
