package dunaeva.polina.finalproject.entity;

import lombok. *;
import javax.persistence.*;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table

public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private CategoryType name;
}

