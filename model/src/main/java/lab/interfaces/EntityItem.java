package lab.interfaces;

import java.io.Serializable;


public interface EntityItem extends Serializable, Cloneable {
    Integer getId();
    
    void setId(Integer id);
}
