package dunaeva.polina.finalproject.service;

import dunaeva.polina.finalproject.entity.Trader;
import dunaeva.polina.finalproject.payload.CommentRequest;
import dunaeva.polina.finalproject.entity.Comment;
import dunaeva.polina.finalproject.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    public void addComment(CommentRequest commentRequest, Trader trader) throws RuntimeException {

        Comment comment = new Comment(LocalDate.now(), false, commentRequest.getMessage(),
                commentRequest.getRating(), trader);
        commentRepository.save(comment);
    }

    public void saveComment(Comment comment) throws RuntimeException {
        commentRepository.save(comment);
    }

    public Collection<Comment> getAllCommentsByTraderId(Integer traderId) {
        return commentRepository.findAllByTraderId(traderId);
    }

    public Optional<Comment> getCommentById(int id) {
        return commentRepository.findById(id);
    }

    public boolean deleteComment(int id) {
        boolean deleted = false;
        Optional<Comment> comment = commentRepository.findById(id);
        if(comment.isPresent()) {
            commentRepository.delete(comment.get());
            deleted = true;
        }
        return deleted;
    }

    public Optional<Comment> updateComment(CommentRequest commentRequest, int commentId) {
        Optional<Comment> comment = commentRepository.findById(commentId);
        if(comment.isPresent()) {
            comment.get().setMessage(commentRequest.getMessage());
            comment.get().setRating(commentRequest.getRating());
            commentRepository.save(comment.get());
        }
        return comment;
    }

}
