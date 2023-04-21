package ro.itschool.entity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Reply extends Post {

    private Boolean isPublic;

}
