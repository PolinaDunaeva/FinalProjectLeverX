package dunaeva.polina.finalproject.controller;

import dunaeva.polina.finalproject.entity.Trader;
import dunaeva.polina.finalproject.payload.TraderRequest;
import dunaeva.polina.finalproject.service.TraderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.annotation.security.RolesAllowed;
import java.util.Optional;

@RestController
public class TraderController {
    @Autowired
    TraderService traderService;

    @GetMapping("/traders")
    public ResponseEntity<?> getAllTraders() {
        final Iterable<?> traders = traderService.getAllTraders();

        return traders.iterator().hasNext() ?
                new ResponseEntity<>(traders, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value ="/traders/{id}")
    public ResponseEntity<?> getTrader(@PathVariable(name = "id") int id) {
        final Optional<Trader> trader = traderService.getTraderById(id);

        return trader.map(value ->
                new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() ->
                new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RolesAllowed("ROLE_ADMIN")
    @GetMapping(value ="/traders/{id}/approve")
    public ResponseEntity<?> approve(@PathVariable(name = "id") int id) {
        final Optional<Trader> trader = traderService.getTraderById(id);
        if (trader.isPresent()) {
            trader.get().setApproved(true);
            traderService.saveTrader(trader.get());
        }
        return trader.map(value ->
                new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() ->
                new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(value ="/traders/{id}/rating")
    public ResponseEntity<?> getTraderRating(@PathVariable(name = "id") int id) {
        final Optional<Trader> trader = traderService.getTraderById(id);

        return trader.map(value ->
                new ResponseEntity<>(value.countRating(), HttpStatus.OK)).orElseGet(() ->
                new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(value ="/traders/rating")
    public ResponseEntity<?> getTradersTop(@RequestParam String category) {
        final Iterable<?> traders = traderService.getTradersTop(category);

        return traders.iterator().hasNext() ?
                new ResponseEntity<>(traders, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PostMapping("/traders")
    public ResponseEntity<?> addTrader(@RequestBody TraderRequest traderRequest) {
        try {
            traderService.addTrader(traderRequest);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        //sendMailService.sendEmail(mailMessageBuilder.convert(bookingDto));
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
