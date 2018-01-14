package lab.dao;

import lab.dao.exceptions.DAOException;
import lab.dao.interfaces.UsersDAO;
import lab.entities.UserEntity;

import java.util.List;


public class UsersDAOImpl extends AbstractDAO implements UsersDAO {
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
