package fit_bracelet.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Move {

    @Id
    @GeneratedValue
    private Long moveId;
    private LocalDate date;
    private Integer movesCount;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

}
