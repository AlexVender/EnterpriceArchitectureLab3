package lab.dao;

import lab.dao.exceptions.DAOException;
import lab.dao.interfaces.UsersDAO;
import lab.entities.UserEntity;
import lab.utils.HibernateSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UsersDAOImpl extends AbstractDAO implements UsersDAO {
    
    @Autowired
    public UsersDAOImpl(HibernateSessionFactory hibernateSessionFactory) {
        super(hibernateSessionFactory);
    }
    
    @Override
    public Integer create(UserEntity user) throws DAOException {
        return super.create(user);
    }

    @Override
    public UserEntity read(Integer id) throws DAOException {
        return (UserEntity) read(id, UserEntity.class);
    }

    @Override
    public List<UserEntity> readAll(Integer limit, Integer offset) throws DAOException {
        return readAll(limit, offset, UserEntity.class);
    }

    @Override
    public void update(UserEntity user) throws DAOException {
        super.update(user);
    }

    @Override
    public void delete(UserEntity userEntity) throws DAOException {
        super.delete(userEntity);
    }
}
