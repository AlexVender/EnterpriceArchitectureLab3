package lab.dao;

import lab.dao.exceptions.DAOException;
import lab.dao.interfaces.TasksDAO;
import lab.entities.TaskEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TasksDAOImpl extends AbstractDAO implements TasksDAO {
    @Override
    public Integer create(TaskEntity task) throws DAOException {
        return super.create(task);
    }

    @Override
    public TaskEntity read(Integer id) throws DAOException {
        return (TaskEntity) read(id, TaskEntity.class);
    }

    @Override
    public List<TaskEntity> readAll(Integer limit, Integer offset) throws DAOException {
        return readAll(limit, offset, TaskEntity.class);
    }

    @Override
    public void update(TaskEntity task) throws DAOException {
        super.update(task);
    }

    @Override
    public void delete(TaskEntity taskEntity) throws DAOException {
        super.delete(taskEntity);
    }
}
