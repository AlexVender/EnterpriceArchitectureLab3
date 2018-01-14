package lab.entities;

import lab.datatypes.Priority;
import lab.datatypes.Status;
import lab.interfaces.EntityItem;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;


@Entity
@Table(name = "tasks", schema = "public", catalog = "postgres")
public class TaskEntity implements EntityItem {
    private Integer taskId;
    private String name;
    private String description;
    private Priority priority;
    private Status status;
    private LocalDate startDate;
    private LocalDate dueDate;
    private UserEntity assignee;
    private ProjectEntity project;
    
    public TaskEntity() {
        status = Status.OPEN;
    }
    
    public TaskEntity(String name, String description, Priority priority, Status status,
                      LocalDate startDate, LocalDate dueDate, UserEntity assignee, ProjectEntity project) {
        this.name = name;
        this.description = description;
        this.priority = priority;
        this.status = status;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.assignee = assignee;
        this.project = project;
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id", nullable = false)
    public Integer getId() {
        return taskId;
    }
    
    public void setId(Integer taskId) {
        this.taskId = taskId;
    }
    
    @Basic
    @Column(name = "name", nullable = false, length = 100)
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Basic
    @Column(name = "description", length = -1)
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    @Basic
    @Column(name = "priority", nullable = false)
    public Priority getPriority() {
        return priority;
    }
    
    public void setPriority(Priority priority) {
        this.priority = priority;
    }
    
    @Basic
    @Column(name = "status", nullable = false)
    public Status getStatus() {
        return status;
    }
    
    public void setStatus(Status status) {
        this.status = status;
    }
    
    @Basic
    @Column(name = "start_date", nullable = false)
    public LocalDate getStartDate() {
        return startDate;
    }
    
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    
    @Basic
    @Column(name = "due_date")
    public LocalDate getDueDate() {
        return dueDate;
    }
    
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
    
    @ManyToOne
    @JoinColumn(name = "assignee_id", referencedColumnName = "user_id", nullable = false)
    public UserEntity getAssignee() {
        return assignee;
    }
    
    public void setAssignee(UserEntity usersByAssigneeId) {
        this.assignee = usersByAssigneeId;
    }
    
    @ManyToOne
    @JoinColumn(name = "project_id", referencedColumnName = "project_id", nullable = false)
    public ProjectEntity getProject() {
        return project;
    }
    
    public void setProject(ProjectEntity projectsByProjectId) {
        this.project = projectsByProjectId;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskEntity that = (TaskEntity) o;
        return Objects.equals(taskId, that.taskId) &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description) &&
                Objects.equals(priority, that.priority) &&
                Objects.equals(status, that.status) &&
                Objects.equals(startDate, that.startDate) &&
                Objects.equals(dueDate, that.dueDate) &&
                Objects.equals(assignee.getId(), that.assignee.getId()) &&
                Objects.equals(project.getId(), that.project.getId());
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(taskId, name, description, priority, status,
                startDate, dueDate, assignee.getId(), project.getId());
    }
    
    @Override
    public Object clone() {
        try {
            TaskEntity result = (TaskEntity) super.clone();
            result.setAssignee((UserEntity) assignee.clone());
            result.setProject((ProjectEntity) project.clone());
            return result;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
