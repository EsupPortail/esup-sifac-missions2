package org.esupportail.sifacmissions.domain.beans;

/**
 * Valeurs possibles pour indiquer l'état du remboursement d'une mission.
 *
 * @author Florent Cailhol (Anyware Services)
 */
public interface MissionStatus {

    /**
     * Non remboursable.
     */
    public static final Integer NON_REMBOURSABLE = 0;

    /**
     * En attente de paiement.
     */
    public static final Integer EN_ATTENTE = 1;

    /**
     * Paiement validé.
     */
    public static final Integer VALIDEE = 2;

}
