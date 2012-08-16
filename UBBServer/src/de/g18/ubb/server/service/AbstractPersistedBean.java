package de.g18.ubb.server.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import de.g18.ubb.common.domain.AbstractEntity;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
abstract public class AbstractPersistedBean<_Entity extends AbstractEntity> {

    @PersistenceContext(unitName = "UBB")
    private EntityManager em;


    public final _Entity saveAndLoad(_Entity aEntity) {
        prepareForSave(aEntity);
        save(aEntity);
        aEntity = loadById(aEntity.getId());
        return aEntity;
    }

    protected void prepareForSave(_Entity aEntity) {
    }

    public final void save(_Entity aEntity) {
        em.persist(aEntity);
    }

    @SuppressWarnings("unchecked")
    public final _Entity loadById(Long aId) {
        Query q = createQuery("SELECT e FROM " + getEntityClass().getSimpleName() + " e "
                            + " WHERE e.id = :id")
                  .setParameter("id", aId);
        return (_Entity) q.getSingleResult();
    }

    public final void removeById(Long aId) {
        remove(loadById(aId));
    }

    public final void remove(_Entity aEntity) {
        aEntity = em.merge(aEntity);
        em.remove(aEntity);
        aEntity.setId(null);
    }

    @SuppressWarnings("unchecked")
    public final List<_Entity> getAll() {
        Query q = createQuery("SELECT e FROM " + getEntityClass().getSimpleName() + " e ");
        return q.getResultList();
    }

    protected final Query createQuery(String aQuery) {
        return em.createQuery(aQuery);
    }

    abstract protected Class<_Entity> getEntityClass();
}
