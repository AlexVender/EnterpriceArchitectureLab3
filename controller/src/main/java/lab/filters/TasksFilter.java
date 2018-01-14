package lab.filters;

import lab.dao.AbstractDAO;
import lab.dao.exceptions.DAOException;
import lab.datatypes.Priority;
import lab.datatypes.Status;
import lab.entities.ProjectEntity;
import lab.entities.TaskEntity;
import lab.entities.UserEntity;
import lab.utils.HibernateSessionFactory;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.time.LocalDate;
import java.util.List;


public class TasksFilter {
    private final static Logger logger = Logger.getLogger(AbstractDAO.class);

    private String name;
    private Priority priority;
    private Status status;
    private LocalDate startDateFrom, startDateTo;
    private LocalDate dueDateFrom, dueDateTo;
    private UserEntity assignee;
    private ProjectEntity project;

    public TasksFilter setName(String name) {
        this.name = "%" + name + "%";
        return this;
    }

    public TasksFilter setPriority(Priority priority) {
        this.priority = priority;
        return this;
    }

    public TasksFilter setStatus(Status status) {
        this.status = status;
        return this;
    }

    public TasksFilter setStartDateFrom(LocalDate startDateFrom) {
        this.startDateFrom = startDateFrom;
        return this;
    }

    public TasksFilter setStartDateTo(LocalDate startDateTo) {
        this.startDateTo = startDateTo;
        return this;
    }

    public TasksFilter setDueDateFrom(LocalDate dueDateFrom) {
        this.dueDateFrom = dueDateFrom;
        return this;
    }

    public TasksFilter setDueDateTo(LocalDate dueDateTo) {
        this.dueDateTo = dueDateTo;
        return this;
    }

    public TasksFilter setAssignee(UserEntity assignee) {
        this.assignee = assignee;
        return this;
    }

    public TasksFilter setProject(ProjectEntity project) {
        this.project = project;
        return this;
    }


    public List<TaskEntity> find() throws DAOException {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {

            Class resultType = TaskEntity.class;
            Criteria cr = session.createCriteria(resultType);

            if (name != null && !name.isEmpty()) {
                cr.add(Restrictions.or(
                        Restrictions.ilike("description", name),
                        Restrictions.ilike("name", name))
                );
            }
            if (priority != null) {
                cr.add(Restrictions.eq("priority", priority));
            }
            if (status != null) {
                cr.add(Restrictions.eq("status", status));
            }
            if (startDateFrom != null) {
                cr.add(Restrictions.ge("startDate", startDateFrom));
            }
            if (startDateTo != null) {
                cr.add(Restrictions.le("startDate", startDateTo));
            }
            if (dueDateFrom != null) {
                cr.add(Restrictions.ge("dueDate", dueDateFrom));
            }
            if (dueDateTo != null) {
                cr.add(Restrictions.le("dueDate", dueDateTo));
            }
            if (project != null) {
                cr.add(Restrictions.eq("project", project));
            }
            if (assignee != null) {
                cr.add(Restrictions.eq("assignee", assignee));
            }

            return cr.list();
        } catch (HibernateException e) {
            logger.error(e.getStackTrace());
            throw new DAOException(e);
        }
    }
}
