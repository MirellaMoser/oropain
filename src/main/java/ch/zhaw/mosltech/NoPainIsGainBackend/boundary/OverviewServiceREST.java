package ch.zhaw.mosltech.NoPainIsGainBackend.boundary;

import java.security.Principal;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.zhaw.mosltech.NoPainIsGainBackend.controller.PlotController;
import ch.zhaw.mosltech.NoPainIsGainBackend.dto.GraphDataDTO;
import ch.zhaw.mosltech.NoPainIsGainBackend.exceptions.EntityNotFoundException;

@RestController
@RequestMapping("/api/plot")
public class OverviewServiceREST {

    @Autowired
    private PlotController plotController;

    private final Logger LOGGER = Logger.getLogger(this.getClass().getName());

    @GetMapping("/pain")
    private ResponseEntity<GraphDataDTO> getPainData(Principal principal) {
        String loginName = principal.getName();
        try {
            GraphDataDTO painData = plotController.getPainPlotData(loginName);
            return new ResponseEntity<>(painData, HttpStatus.OK);
        } catch (EntityNotFoundException enfe) {
            LOGGER.log(Level.SEVERE, enfe.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/stress")
    private ResponseEntity<GraphDataDTO> getStressData(Principal principal) {
        String loginName = principal.getName();
        try {
            GraphDataDTO stressData = plotController.getStressPlotData(loginName);
            return new ResponseEntity<>(stressData, HttpStatus.OK);
        } catch (EntityNotFoundException enfe) {
            LOGGER.log(Level.SEVERE, enfe.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }   

}
