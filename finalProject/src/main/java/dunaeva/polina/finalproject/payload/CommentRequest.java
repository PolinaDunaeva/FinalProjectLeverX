package dunaeva.polina.finalproject.payload;

import dunaeva.polina.finalproject.entity.RatingType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequest {
    private String message;
    private RatingType rating;
}
