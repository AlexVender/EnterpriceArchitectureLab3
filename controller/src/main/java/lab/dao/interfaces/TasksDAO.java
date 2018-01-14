package lab.dao.interfaces;

import lab.dao.exceptions.DAOException;
import lab.entities.TaskEntity;

import java.util.List;


public interface TasksDAO {
    Integer create(TaskEntity task) throws DAOException;
    
    TaskEntity read(Integer id) throws DAOException;
    
    List<TaskEntity> readAll(Integer limit, Integer offset) throws DAOException;
    
    void update(TaskEntity task) throws DAOException;
    
    void delete(TaskEntity taskEntity) throws DAOException;
}
