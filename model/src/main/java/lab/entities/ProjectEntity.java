package lab.entities;

import lab.interfaces.*;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.Objects;


@Entity
@Table(name = "projects", schema = "public", catalog = "postgres")
public class ProjectEntity implements EntityItem {
    private Integer projectId;
    private String name;
    
    public ProjectEntity() {}
    
    public ProjectEntity(String name) {
        this.name = name;
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id", nullable = false)
    public Integer getId() {
        return projectId;
    }
    
    public void setId(Integer projectId) {
        this.projectId = projectId;
    }
    
    @Basic
    @Column(name = "name", nullable = false, length = 50)
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectEntity that = (ProjectEntity) o;
        return Objects.equals(projectId, that.projectId) &&
                Objects.equals(name, that.name);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(projectId, name);
    }
    
    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
