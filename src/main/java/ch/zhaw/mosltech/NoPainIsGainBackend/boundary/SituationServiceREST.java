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

import ch.zhaw.mosltech.NoPainIsGainBackend.controller.SituationController;
import ch.zhaw.mosltech.NoPainIsGainBackend.dto.ElementSelectionDTO;
import ch.zhaw.mosltech.NoPainIsGainBackend.dto.InputDTO;
import ch.zhaw.mosltech.NoPainIsGainBackend.exceptions.EntityNotFoundException;

@RestController
@RequestMapping("/api/situation")
public class SituationServiceREST {

    @Autowired
    private SituationController situationController;

    private final Logger LOGGER = Logger.getLogger(this.getClass().getName());

    /**
     * Controller method to retrieve a list of symptoms for the current health
     * situation of the authenticated user.
     * <p>
     * This method communicates with the {@link SituationController} to fetch
     * symptoms associated with the user's most
     * recent situation record, providing insight into which symptoms were reported.
     * It serves as a mechanism to present
     * a snapshot of the user's current health status in terms of reported symptoms.
     * </p>
     *
     * @param principal The {@link Principal} object representing the currently
     *                  authenticated user, used to identify the user's session.
     * @return A {@link ResponseEntity} containing a list of
     *         {@link ElementSelectionDTO} representing the symptoms for the current
     *         situation,
     *         or a NOT_FOUND status if no current record can be found.
     *
     *         <p>
     *         Example of JSON response:
     * 
     *         <pre>
     * [
     *   {
     *     "name": "Kieferschmerzen",
     *     "selected": false
     *   },
     *   {
     *     "name": "Kiefergeräusche",
     *     "selected": true
     *   }
     * ]
     *         </pre>
     * 
     *         This example illustrates a typical JSON response, with each object
     *         representing a symptom and its selection state
     *         by the user for the current situation.
     */
    @GetMapping("/current/symptoms")
    public ResponseEntity<List<ElementSelectionDTO>> getAllSymptoms(Principal principal) {
        // Extract the login name from the Principal object to identify the user.
        String loginName = principal.getName();
        try {
            // Retrieve the current situation's symptoms selection for the logged-in user.
            List<ElementSelectionDTO> overview = situationController.getCurrentSituationSymptomsSelection(loginName);

            // Successfully retrieved the list; return it with an OK HTTP status.
            return new ResponseEntity<>(overview, HttpStatus.OK);
        } catch (EntityNotFoundException enfe) {
            // Log the exception if the current record cannot be found for the user.
            LOGGER.log(Level.SEVERE, enfe.getMessage());

            // Return a NOT_FOUND response status if the entity (record) is not found.
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Controller method to retrieve a blank situation template for the
     * authenticated user.
     * <p>
     * Fetches a template for recording a new health situation, populated with
     * default values and selections
     * to facilitate user input. This method is useful for initializing forms on the
     * client side with
     * appropriate default values and available options based on the user's current
     * context.
     * </p>
     *
     * @param principal The {@link Principal} object representing the currently
     *                  authenticated user, used to identify the user's session.
     * @return A {@link ResponseEntity} containing an {@link InputDTO} with
     *         pre-populated default values for a new health situation,
     *         or a NOT_FOUND status if the user's information could not be
     *         retrieved.
     *
     *         <p>
     *         Example of JSON result:
     * 
     *         <pre>
     * {
     *   "intensity": 7,
     *   "symptoms": [
     *   {
     *     "name": "Kieferschmerzen",
     *     "selected": true
     *   },
     *   {
     *     "name": "Kiefergeräusche",
     *     "selected": true
     *   }
     *  ],
     *   "stressLevel": 2,
     *   "stressors": [
     *     {
     *       "name": "Schlafmangel",
     *       "category": "Körperlich",
     *       "selected": true
     *     }
     *   ],
     *   "dateOfEntry": "2024-04-01",
     *   "timeOfDay": "MORNING",
     *   "availableEntries": ["MORNING", "AFTERNOON", "EVENING"]
     * }
     *         </pre>
     * 
     *         This example demonstrates how a blank input DTO might look, including
     *         default selections for symptoms and stressors,
     *         a default intensity and stress level of 0, and the current date.
     */
    @GetMapping("/empty")
    public ResponseEntity<InputDTO> getBlankSituation(Principal principal) {
        // Extract the login name from the Principal object to identify the user.
        String loginName = principal.getName();
        try {
            // Generate a blank InputDTO template for the user to fill out for a new
            // situation.
            InputDTO inputDTO = situationController.populateBlankInputDTO(loginName);

            // Successfully created the template; return it with an OK HTTP status.
            return new ResponseEntity<>(inputDTO, HttpStatus.OK);
        } catch (EntityNotFoundException enfe) {
            // Log the exception if the user's information could not be retrieved.
            LOGGER.log(Level.SEVERE, enfe.getMessage());

            // Return a NOT_FOUND response status if the entity (user) is not found.
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Controller method to handle the submission of a new health situation by the
     * authenticated user.
     * <p>
     * Processes a submitted InputDTO representing a new health situation recorded
     * by the user.
     * The method delegates the addition of the situation to the
     * {@link SituationController}, which handles
     * the business logic of creating and storing the situation in the database.
     * </p>
     *
     * @param principal The {@link Principal} object representing the currently
     *                  authenticated user, used to identify the user's session.
     * @param input     The {@link InputDTO} representing the details of the new
     *                  health situation submitted by the user.
     * @return A {@link ResponseEntity} with an empty body and an OK status if the
     *         situation was successfully added,
     *         or a NOT_FOUND status if the user's information could not be
     *         retrieved.
     * 
     *         <p>
     *         Example of JSON result:
     * 
     *         <pre>
     * {
     *   "intensity": 7,
     *   "symptoms": [
     *   {
     *     "name": "Kieferschmerzen",
     *     "selected": true
     *   },
     *   {
     *     "name": "Kiefergeräusche",
     *     "selected": true
     *   }
     *  ],
     *   "stressLevel": 2,
     *   "stressors": [
     *     {
     *       "name": "Schlafmangel",
     *       "category": "Körperlich",
     *       "selected": true
     *     }
     *   ],
     *   "dateOfEntry": "2024-04-01",
     *   "timeOfDay": "MORNING",
     *   "availableEntries": ["MORNING", "AFTERNOON", "EVENING"]
     * }
     *         </pre>
     * 
     *         This example demonstrates how a JSON request body might look when
     *         submitting a new health situation,
     *         including the user's selections for symptoms and stressors,
     *         intensity, stress level, and the date and time
     *         of the recorded situation.
     */
    @PostMapping
    public ResponseEntity<Void> postNewSituation(Principal principal, @RequestBody InputDTO input) {
        // Extract the login name from the Principal object to identify the user.
        String loginName = principal.getName();
        try {
            // Add the submitted situation to the database through the SituationController.
            situationController.addSituation(loginName, input);

            // Situation added successfully; return an OK response status.
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EntityNotFoundException enfe) {
            // Log the exception if the user's information could not be retrieved.
            LOGGER.log(Level.SEVERE, enfe.getMessage());

            // Return a NOT_FOUND response status if the entity (user) is not found.
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
