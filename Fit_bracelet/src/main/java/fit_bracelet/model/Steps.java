package fit_bracelet.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Steps {

    @Id
    @GeneratedValue
    private Long stepsId;
    private LocalDate date;
    private Integer stepsCount;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

}
