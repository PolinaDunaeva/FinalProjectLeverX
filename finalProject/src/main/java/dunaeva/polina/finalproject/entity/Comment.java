package dunaeva.polina.finalproject.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonFormat(pattern = "dd-MM-yyy", shape = JsonFormat.Shape.STRING)
    private LocalDate created_at;

    private Boolean approved;

    private String message;

    @Enumerated(EnumType.STRING)
    private RatingType rating;

    @ManyToOne
    @JoinColumn()
    private Trader trader;

    public Comment(LocalDate created_at, boolean approved, String message, RatingType rating, Trader trader) {
        this.created_at = created_at;
        this.approved = approved;
        this.message = message;
        this.rating = rating;
        this.trader = trader;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, created_at, approved, message, rating, trader);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return id.equals(comment.id) &&
                created_at.equals(comment.created_at)&&
                approved.equals(comment.approved)&&
                message.equals(comment.message)&&
                rating.equals(comment.rating);
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", created_at=" + created_at +
                ", approved=" + approved +
                ", message='" + message + '\'' +
                ", rating=" + rating +
                ", trader=" + trader.getFirstname() + " " + trader.getLastname() +
                '}';
    }

}

