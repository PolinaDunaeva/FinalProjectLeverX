package dunaeva.polina.finalproject.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.OptionalDouble;
import java.util.Set;

@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        uniqueConstraints = {
        @UniqueConstraint(columnNames = {"firstname", "lastname"})
        }
)
public class Trader implements Comparable<Trader> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    public Trader(@NotBlank @NonNull @Size(max = 50) String firstname, @NotBlank @NonNull @Size(max = 50) String lastname, @NonNull Boolean approved, @NonNull LocalDate created_at, Set<Category> categories, Set<Comment> comments) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.approved = approved;
        this.created_at = created_at;
        this.categories = categories;
        this.comments = comments;
    }

    @NotBlank
    @NonNull
    @Size(max = 50)
    private String firstname;

    @NotBlank
    @NonNull
    @Size(max = 50)
    private String lastname;

    @NonNull
    private Boolean approved;

    @NonNull
    @JsonFormat(pattern = "dd-MM-yyy", shape = JsonFormat.Shape.STRING)
    private LocalDate created_at;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinTable
    private Set<Category> categories = new HashSet<>();

    @Override
    public String toString() {
        return "Trader{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", approved=" + approved +
                ", created_at=" + created_at +
                ", categories=" + categories +
                '}';
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "trader")
    @JsonIgnore
    private Set<Comment> comments = new HashSet<>();

    @JsonProperty("rating")
    public Double countRating() {
        Set<Comment> comments = this.comments;
        OptionalDouble rating = comments.stream().filter(comment -> comment.getRating()!=null).
                mapToDouble((comment)->comment.getRating().
                        toInteger()).average();
        if(rating.isPresent()) {
            return rating.getAsDouble();
        }
        return 0.0;
    }

    @Override
    public int compareTo(Trader o) {
        return (int)(o.countRating()- this.countRating());
    }
}
