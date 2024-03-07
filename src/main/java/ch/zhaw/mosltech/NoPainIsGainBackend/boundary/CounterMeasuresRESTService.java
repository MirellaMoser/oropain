package ch.zhaw.mosltech.NoPainIsGainBackend.boundary;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.zhaw.mosltech.NoPainIsGainBackend.entity.CounterMeasure;
import ch.zhaw.mosltech.NoPainIsGainBackend.entity.CounterMeasureRepository;

@RestController
public class CounterMeasuresRESTService {
     
    @Autowired
    private CounterMeasureRepository counterMeasureRepository;

    @GetMapping("/countermeasures")
    public List<CounterMeasure> getAllDefaultCounterMeasures() {
        return counterMeasureRepository.findAllByIsDefault(true);
    }
    
}
