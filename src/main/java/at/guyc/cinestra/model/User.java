package at.guyc.cinestra.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @Column(name = "user_id")
    private Integer userId;

    private String gender;
    private Integer age;
    private Integer occupation;

    @Column(name = "zip_code")
    private String zipCode;

}
