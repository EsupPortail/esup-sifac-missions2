package org.esupportail.sifacmissions.services.sifac;

/**
 * Levée par les services SIFAC.
 *
 * @author Florent Cailhol (Anyware Services)
 */
@SuppressWarnings("serial")
public class SifacException extends Exception {

    /**
     * Constructeur.
     *
     * @param cause Cause de l'exception
     */
    public SifacException(Throwable cause) {
        super(cause);
    }

}
