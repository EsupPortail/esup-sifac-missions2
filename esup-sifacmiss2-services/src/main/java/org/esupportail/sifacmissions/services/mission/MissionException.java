package org.esupportail.sifacmissions.services.mission;

/**
 * Levée par les services SIFAC.
 *
 * @author Florent Cailhol (Anyware Services)
 */
@SuppressWarnings("serial")
public class MissionException extends Exception {

    /**
     * Constructeur.
     *
     * @param cause Cause de l'exception
     */
    public MissionException(Throwable cause) {
        super(cause);
    }

}
