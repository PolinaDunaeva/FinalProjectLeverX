package dunaeva.polina.finalproject.payload;

import dunaeva.polina.finalproject.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TraderRequest {

    private String firstname;
    private String lastname;
    private Set<Category> categories;

}

