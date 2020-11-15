package dunaeva.polina.finalproject.controller;

import dunaeva.polina.finalproject.entity.Comment;
import dunaeva.polina.finalproject.entity.Trader;
import dunaeva.polina.finalproject.payload.CommentRequest;
import dunaeva.polina.finalproject.service.CommentService;
import dunaeva.polina.finalproject.service.TraderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.Optional;

@RestController
public class CommentController {
    @Autowired
    CommentService commentService;

    @Autowired
    TraderService traderService;

    @GetMapping("/traders/{id}/comments")
    public ResponseEntity<?> getAllComments(@PathVariable(name = "id") int id) {
        final Iterable<?> comments = commentService.getAllCommentsByTraderId(id);

        return comments.iterator().hasNext() ?
                new ResponseEntity<>(comments, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value ="/traders/{traderId}/comments/{id}")
    public ResponseEntity<?> getTrader(@PathVariable(name = "id") int id) {
        final Optional<?> comment = commentService.getCommentById(id);

        return comment.map(value ->
                new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() ->
                new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/traders/{id}/comments")
    public ResponseEntity<?> addComment(@PathVariable(name = "id") int id,
                                        @RequestBody CommentRequest commentRequest) {
        try {
            Optional<Trader> trader = traderService.getTraderById(id);
            commentService.addComment(commentRequest, trader.get());
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PutMapping(value ="/traders/{traderId}/comments/{id}")
    public ResponseEntity<?> updateComment(@PathVariable(name = "id") int id,
                                           @RequestBody CommentRequest commentRequest) {
            Optional<?> comment = commentService.updateComment(commentRequest, id);
        return comment.isPresent()
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value ="/traders/{traderId}/comments/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable(name = "id") int id) {

        return commentService.deleteComment(id)
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @RolesAllowed("ROLE_ADMIN")
    @GetMapping(value ="/traders/{tradersId}/comments/{id}/approve")
    public ResponseEntity<?> approve(@PathVariable(name = "id") int id) {
        final Optional<Comment> comment = commentService.getCommentById(id);
        if (comment.isPresent()) {
            comment.get().setApproved(true);
            commentService.saveComment(comment.get());
        }

        return comment.map(value ->
                new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() ->
                new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
