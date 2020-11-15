package dunaeva.polina.finalproject.service;

import dunaeva.polina.finalproject.entity.CategoryType;
import dunaeva.polina.finalproject.payload.TraderRequest;
import dunaeva.polina.finalproject.entity.Trader;
import dunaeva.polina.finalproject.repository.TraderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TraderService {
    @Autowired
    TraderRepository traderRepository;

    public void addTrader(TraderRequest traderRequest) throws RuntimeException {

        Trader trader = new Trader(traderRequest.getFirstname(), traderRequest.getLastname(),
                false, LocalDate.now(), traderRequest.getCategories(), new HashSet<>());
        traderRepository.save(trader);
    }

    public void saveTrader(Trader trader) throws RuntimeException {
        traderRepository.save(trader);
    }

    public Iterable<?> getAllTraders() {
        return traderRepository.findAll();
    }

    public Iterable<?> getTradersTop(String category) {

        return traderRepository.findAll().stream().
                filter(t->t.getCategories().iterator().next().
                        getName().equals(CategoryType.valueOf(category)))
                .sorted().collect(Collectors.toList());
    }

    public Optional<Trader> getTraderById(int id) {
        Trader trader = traderRepository.findById(id).get();
        return traderRepository.findById(id);
    }

}
