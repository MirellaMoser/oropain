package ch.zhaw.mosltech.NoPainIsGainBackend.boundary;

import java.security.Principal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.zhaw.mosltech.NoPainIsGainBackend.controller.RecordController;
import ch.zhaw.mosltech.NoPainIsGainBackend.controller.SituationController;
import ch.zhaw.mosltech.NoPainIsGainBackend.dto.ElementSelectionDTO;
import ch.zhaw.mosltech.NoPainIsGainBackend.dto.InputDTO;
import ch.zhaw.mosltech.NoPainIsGainBackend.dto.OverviewDTO;
import ch.zhaw.mosltech.NoPainIsGainBackend.exceptions.EntityNotFoundException;

@RestController
@RequestMapping("/api/situation")
public class SituationServiceREST {

    @Autowired
    private SituationController situationController;

    @Autowired
    private RecordController recordController;

    private final Logger LOGGER = Logger.getLogger(this.getClass().getName());

    @GetMapping("/current/countermeasures")
    public ResponseEntity<List<ElementSelectionDTO>> getAllDefaultCounterMeasures(Principal principal) {
        String loginName = principal.getName();
        try {
            List<ElementSelectionDTO> overview = recordController
                    .getCountermeasureSelectionForCurrentRecord(loginName);
            return new ResponseEntity<>(overview, HttpStatus.OK);
        } catch (EntityNotFoundException enfe) {
            LOGGER.log(Level.SEVERE, enfe.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/current/countermeasures")
    public ResponseEntity<Void> updateCounterMeasures(Principal principal,
            @RequestBody List<ElementSelectionDTO> currentSelection) {
        String loginName = principal.getName();
        try {
            recordController.setCountermeasureSelectionForCurrentRecord(loginName, currentSelection);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EntityNotFoundException enfe) {
            LOGGER.log(Level.SEVERE, enfe.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/current/overview")
    public ResponseEntity<OverviewDTO> getOverview(Principal principal) {
        String loginName = principal.getName();
        try {
            OverviewDTO overview = recordController.getOverview(loginName);
            return new ResponseEntity<>(overview, HttpStatus.OK);
        } catch (EntityNotFoundException enfe) {
            LOGGER.log(Level.SEVERE, enfe.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/current/symptoms")
    public ResponseEntity<List<ElementSelectionDTO>> getAllSymptoms(Principal principal) {
        String loginName = principal.getName();
        try {
            List<ElementSelectionDTO> overview = situationController
                    .getCurrentSituationSymptomsSelection(loginName);
            return new ResponseEntity<>(overview, HttpStatus.OK);
        } catch (EntityNotFoundException enfe) {
            LOGGER.log(Level.SEVERE, enfe.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/new/empty")
    public ResponseEntity<InputDTO> getNewEntrySituation(Principal principal) {
        String loginName = principal.getName();
        try {
            InputDTO inputDTO = situationController.populateBlankInputDTO(loginName);
            return new ResponseEntity<>(inputDTO, HttpStatus.OK);
        } catch (EntityNotFoundException enfe) {
            LOGGER.log(Level.SEVERE, enfe.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Void> postNewSituation(Principal principal, @RequestBody InputDTO input) {
         String loginName = principal.getName();
        try {
            situationController.addSituation(loginName, input);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EntityNotFoundException enfe) {
            LOGGER.log(Level.SEVERE, enfe.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
    }

}
