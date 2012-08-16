package de.g18.ubb.server.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Query;
import org.hibernate.Session;

import de.g18.ubb.common.domain.Auditable;
import de.g18.ubb.common.domain.Identifiable;
import de.g18.ubb.common.domain.User;
import de.g18.ubb.common.service.LoadService;
import de.g18.ubb.common.service.RemoveService;
import de.g18.ubb.common.service.StoreService;
import de.g18.ubb.common.service.exception.NotFoundExcpetion;
import de.g18.ubb.common.util.EntityUtil;
import de.g18.ubb.server.service.local.UserServiceLocal;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
abstract public class AbstractPersistanceBean<_Entity extends Identifiable> implements RemoveService<_Entity>,
                                                                                       LoadService<_Entity>,
                                                                                       StoreService<_Entity> {

    @PersistenceContext(unitName = "UBB")
    private EntityManager entityManager;

    @PersistenceContext(unitName = "UBB")
    private Session session;

    @EJB
    private UserServiceLocal userService;


    @Override
    public final _Entity saveAndLoad(_Entity aEntity) {
        prepareForSave(aEntity);
        return entityManager.merge(aEntity);
    }

    protected void prepareForSave(_Entity aEntity) {
        updateTypeDependendFields(aEntity);
    }

    protected void updateTypeDependendFields(_Entity aEntity) {
        if (aEntity instanceof Auditable) {
            Auditable auditableEntity = (Auditable) aEntity;
            updateFields(auditableEntity);
        }
    }

    protected final void updateFields(Auditable aEntity) {
        Date currentTime = Calendar.getInstance().getTime();
        if (EntityUtil.isPersistent(aEntity)) {
            aEntity.setChangeTime(currentTime);
            aEntity.setChangeUser(getTestUser()); // TODO (huber): Implementieren!!!
        } else {
            aEntity.setCreateTime(currentTime);
            aEntity.setCreateUser(getTestUser()); // TODO (huber): Implementieren!!!
        }
    }

    public User getTestUser() {
        List<User> users = userService.getAll();
        if (!users.isEmpty()) {
            return users.get(0);
        }
        return createNewPersistedUser();
    }

    private User createNewPersistedUser() {
        User user = new User();
        return userService.saveAndLoad(user);
    }

    @Override
    public final _Entity loadById(Long aId) throws NotFoundExcpetion {
        _Entity e = entityManager.find(getEntityClass(), aId);
        if (e == null) {
            throw new NotFoundExcpetion(getEntityClass(), aId);
        }
        return e;
    }

    protected final _Entity uniqueResult(Query aQuery) throws NotFoundExcpetion {
        @SuppressWarnings("unchecked")
        _Entity result = (_Entity) aQuery.uniqueResult();
        if (result != null) {
            return result;
        }
        throw new NotFoundExcpetion(getEntityClass());
    }

    @Override
    public final void removeById(Long aId) throws NotFoundExcpetion {
        remove(loadById(aId));
    }

    @Override
    public final void remove(_Entity aEntity) {
        entityManager.remove(aEntity);
        aEntity.setId(null);
    }

    @SuppressWarnings("unchecked")
    public final List<_Entity> getAll() {
        Query q = getHibernateSession()
            .createQuery("SELECT e FROM " + getEntityClass().getSimpleName() + " e ");
        return q.list();
    }

    protected final Session getHibernateSession() {
        return session;
    }

    abstract protected Class<_Entity> getEntityClass();
}
