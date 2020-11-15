package dunaeva.polina.finalproject.repository;

import dunaeva.polina.finalproject.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;


@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    Collection<Comment> findAllByTraderId(int traderId);
}
