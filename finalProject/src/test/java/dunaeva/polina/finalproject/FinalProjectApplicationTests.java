package dunaeva.polina.finalproject;

import org.junit.Assert;
import org.junit.Test;
import dunaeva.polina.finalproject.entity.Comment;
import dunaeva.polina.finalproject.entity.RatingType;
import dunaeva.polina.finalproject.entity.Trader;
import org.springframework.boot.test.context.SpringBootTest;


import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@SpringBootTest
public class FinalProjectApplicationTests {

    public Trader setUp() {
        Trader trader1 = new Trader("trader", "trader", false, LocalDate.now());
        Set<Comment> comments = new HashSet<>();
        trader1.setId(1);
        comments.add(new Comment(LocalDate.now(), false, "1", RatingType.ONE, trader1));
        comments.add(new Comment(LocalDate.now(), false, "2", RatingType.TWO, trader1));
        comments.add(new Comment(LocalDate.now(), false, "3", RatingType.THREE, trader1));
        comments.add(new Comment(LocalDate.now(), false, "4", RatingType.FOUR, trader1));
        trader1.setComments(comments);

        return trader1;
    }

    @Test
    public void averageRatingTest(){
        Trader trader = setUp();
        Assert.assertEquals(trader.countRating(), 2.5, 0.01);
    }

}
