package org.esupportail.sifacmissions.services.matricule;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

/**
 * Service permettant de récupérer le matricule de l'utilisateur depuis un
 * tableau associatif de matricules. Ce composant ne doit pas être utilisé en
 * production.
 *
 * @author Florent Cailhol (Anyware Services)
 */
public class PropertiesMatriculeService implements MatriculeService, InitializingBean {

    private Map<String, String> data;

    /**
     * @param props Liste de matricules
     */
    public void setData(Map<String, String> data) {
        this.data = data;
    }

    /**
     * @param props Liste de matricules
     */
    public void setData(Properties props) {
        data = new HashMap<String, String>();

        Iterator<Entry<Object, Object>> it = props.entrySet().iterator();
        while (it.hasNext()) {
            Entry<Object, Object> entry = it.next();
            data.put(entry.getKey().toString(), entry.getValue().toString());
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(data, "data is required");
    }

    @Override
    public String getMatricule(String uid) {
        return data.get(uid);
    }

}
