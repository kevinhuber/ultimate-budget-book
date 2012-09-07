package de.g18.ubb.server.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.shiro.SecurityUtils;
import org.hibernate.Query;
import org.hibernate.Session;

import de.g18.ubb.common.domain.Auditable;
import de.g18.ubb.common.domain.Identifiable;
import de.g18.ubb.common.domain.User;
import de.g18.ubb.common.service.exception.NotFoundExcpetion;
import de.g18.ubb.common.util.EntityUtil;
import de.g18.ubb.server.service.local.UserServiceLocal;

/**
 * Abstrakte Oberklasse für Services.
 * Die Klasse stellt eine Datenbankverbindung und informationen über den "aufrufenden" User zu verfügung.
 *
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
abstract public class AbstractPersistanceBean<_Entity extends Identifiable> {

    /**
     * {@link EntityManager} für den Zugriff auf die Datenbank.
     */
    @PersistenceContext(unitName = "UBB")
    private EntityManager entityManager;

    /**
     * Hibernate {@link Session} für den Zugriff auf die Datenbank.
     */
    @PersistenceContext(unitName = "UBB")
    private Session session;

	@EJB
    private UserServiceLocal userService;


	/**
	 * Persitiert die übergebene Entität in der Datenbank und gibt die persistierte instanz zurück.
	 */
    public final _Entity saveAndLoad(_Entity aEntity) {
        prepareForSave(aEntity);
        return entityManager.merge(aEntity);
    }

    /**
     * Wird von {@link #saveAndLoad(Identifiable)} aufgerufen um Services die Möglichkeit zu geben vor dem Speichern
     * die Entität zu verändern / validieren.
     *
     * Klassen die diese Methode überschreiben sollten immer super{@link #prepareForSave(Identifiable)} aufrufen!
     */
    protected void prepareForSave(_Entity aEntity) {
        updateTypeDependendFields(aEntity);
    }

    /**
     * Wird normalerweise von {@link #prepareForSave(Identifiable)} aufgerufen, um beispielsweise bei einer {@link Auditable}
     * Entität die changeTime zu aktualisieren.
     */
    protected void updateTypeDependendFields(_Entity aEntity) {
        if (aEntity instanceof Auditable) {
            Auditable auditableEntity = (Auditable) aEntity;
            updateFields(auditableEntity);
        }
    }

    /**
     * Aktualisiert die Felder für {@link Auditable} Entitäten. (ChangeTime, ChangeUser, etc...)
     */
    protected final void updateFields(Auditable aEntity) {
        Date currentTime = Calendar.getInstance().getTime();
        if (EntityUtil.isPersistent(aEntity)) {
            aEntity.setChangeTime(currentTime);
            aEntity.setChangeUser(getCurrentUser());
        } else {
            aEntity.setCreateTime(currentTime);
            aEntity.setCreateUser(getCurrentUser());
        }
    }

    /**
     * Gibt den User zurück, der den Service aufgerufen hat.
     */
    protected final User getCurrentUser() {
        try {
            String userEmail = (String) SecurityUtils.getSubject().getPrincipal();
			return userService.loadByEMail(userEmail);
		} catch (NotFoundExcpetion e) {
			throw new IllegalStateException("Session is not authentificated! (EMail: " + SecurityUtils.getSubject().getPrincipal() + ")");
		}
    }

    /**
     * Lädt eine Entität anhand ihrer ID aus der Datenbank.
     */
    public final _Entity load(Long aId) throws NotFoundExcpetion {
        _Entity e = entityManager.find(getEntityClass(), aId);
        if (e == null) {
            throw new NotFoundExcpetion(getEntityClass(), aId);
        }
        return e;
    }

    /**
     * Ruft {@link Query#uniqueResult()} auf und schmeisst eine {@link NotFoundExcpetion} falls das Ergebniss null sein sollte.
     */
    protected final Object uniqueResult(Query aQuery) throws NotFoundExcpetion {
        Object result = aQuery.uniqueResult();
        if (result != null) {
            return result;
        }
        throw new NotFoundExcpetion(getEntityClass());
    }

    /**
     * Löscht eine Entität anhand der ID aus der Datenbank.
     * Falls keine Entität mit der ID existiert, wird eine {@link NotFoundExcpetion} geschmissen.
     */
    public final void removeById(Long aId) throws NotFoundExcpetion {
        remove(load(aId));
    }

    /**
     * Entfernd die übergebene Entität aus der Datenbank.
     */
    public final void remove(_Entity aEntity) {
        entityManager.remove(aEntity);
        aEntity.setId(null);
    }

    /**
     * Gibt alle in der Datenbank gespeicherten Entitäten vom Typ _Entity zurück.
     */
    @SuppressWarnings("unchecked")
    public final List<_Entity> getAll() {
        Query q = getHibernateSession()
            .createQuery("SELECT e FROM " + getEntityClass().getSimpleName() + " e ");
        return q.list();
    }

    /**
     * Gibt die aktuelle Hibernate {@link Session} zurück um mit der Datenbank arbeiten zu können.
     */
    protected final Session getHibernateSession() {
        return session;
    }

    /**
     * Gibt die Klasse der Entität zurück, mit der dieser Service arbeitet.
     */
    abstract protected Class<_Entity> getEntityClass();
}
