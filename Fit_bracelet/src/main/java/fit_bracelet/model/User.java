package fit_bracelet.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String login;
    @NonNull
    private String firstName;
    private String surName;
    private Integer age;
    private Integer weight;
    private Integer height;
    private String sex;
    private String DateOfBirth;
    private int hashPassword;

    @OneToMany(mappedBy = "user")
    private List<Move> moveData;
    @OneToMany(mappedBy = "user")
    private List<Steps> stepsData;
}
