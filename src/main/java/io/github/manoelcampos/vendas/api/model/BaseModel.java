package io.github.manoelcampos.vendas.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;

/**
 * @author Manoel Campos
 */
public interface BaseModel extends Serializable {
    @Nullable
    Long getId();

    @JsonIgnore
    default boolean isEditing(){
        return !isInserting();
    }

    /**
     * {@return true ou false} se a entidade está sendo inserida no banco ou não.
     */
    @JsonIgnore
    default boolean isInserting(){
        return getId() == null || getId() == 0;
    }
}
