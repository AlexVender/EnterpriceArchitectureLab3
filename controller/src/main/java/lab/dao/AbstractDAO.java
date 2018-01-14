package lab.dao;


import lab.dao.exceptions.DAOException;
import lab.interfaces.EntityItem;
import lab.utils.HibernateSessionFactory;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;


public abstract class AbstractDAO {
    private final static Logger logger = Logger.getLogger(AbstractDAO.class);

    protected Integer create(EntityItem entity) throws DAOException {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
            
            logger.info("Created " + entity.getClass() + " with id " + entity.getId());
            return entity.getId();
        } catch (HibernateException e) {
            logger.error(e.getStackTrace());
            throw new DAOException(e);
        }
    }

    protected static EntityItem read(Integer id, Class<? extends EntityItem> resultType) throws DAOException {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()){
            return session.get(resultType, id);
        } catch (HibernateException e) {
            logger.error(e.getStackTrace());
            throw new DAOException(e);
        }
    }

    protected static <T extends EntityItem> List<T> readAll(Integer limit, Integer offset, Class<T> resultType)
            throws DAOException {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            CriteriaQuery<T> cq = session.getCriteriaBuilder().createQuery(resultType);
            cq.select(cq.from(resultType));

            TypedQuery<T> typedQuery = session.createQuery(cq);
            typedQuery.setFirstResult(limit);
            typedQuery.setMaxResults(offset);

            return typedQuery.getResultList();
        } catch (HibernateException e) {
            logger.error(e.getStackTrace());
            throw new DAOException(e);
        }
    }

    protected void update(EntityItem entity) throws DAOException {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.update(entity);
            session.getTransaction().commit();
            logger.info("Updated " + entity.getClass() + " with id " + entity.getId());
        } catch (HibernateException e) {
            logger.error(e.getStackTrace());
            throw new DAOException(e);
        }
    }

    protected void delete(EntityItem entity) throws DAOException {
        logger.info("Deleting " + entity.getClass() + " with id  " + entity.getId());
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.delete(entity);
            session.getTransaction().commit();
            logger.info("Deleted " + entity.getClass() + " with id  " + entity.getId());
        } catch (HibernateException e) {
            logger.error(e.getStackTrace());
            throw new DAOException(e);
        }
    }
}
