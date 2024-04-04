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
import ch.zhaw.mosltech.NoPainIsGainBackend.dto.ElementSelectionDTO;
import ch.zhaw.mosltech.NoPainIsGainBackend.dto.OverviewDTO;
import ch.zhaw.mosltech.NoPainIsGainBackend.entity.DailyRecord;
import ch.zhaw.mosltech.NoPainIsGainBackend.exceptions.EntityNotFoundException;

@RestController
@RequestMapping("/api/record")
public class RecordServiceREST {

    @Autowired
    private RecordController recordController;

    private final Logger LOGGER = Logger.getLogger(this.getClass().getName());

    /**
     * Retrieves a list of daily records for the authenticated user.
     * <p>
     * This method fetches all daily health records associated with the
     * authenticated user, identified by the Principal object.
     * Each record includes detailed information such as the date, recorded
     * situations throughout the day with pain and stress levels,
     * symptoms, stressors, countermeasures taken, and the average levels of pain
     * and stress.
     * </p>
     * 
     * @param principal the {@link Principal} object representing the currently
     *                  authenticated user, used to identify the user's session.
     * @return a {@link ResponseEntity} containing a list of {@link DailyRecord}
     *         objects for the authenticated user,
     *         or a NOT_FOUND status if no records are found.
     *         <p>
     *         An example of the JSON response structure provided by this endpoint:
     * 
     *         <pre>
     * [
     *     {
     *         "dateTime": "2024-03-30",
     *         "situations": [
     *             {
     *                 "timeOfDay": "AFTERNOON",
     *                 "painLevel": 2,
     *                 "stressLevel": 1,
     *                 "symptoms": [
     *                     {
     *                         "name": "Migräne",
     *                         "default": true
     *                     }
     *                 ],
     *                 "stressors": [
     *                     {
     *                         "name": "Schlafmangel",
     *                         "category": "Körperlich",
     *                         "default": true
     *                     }
     *                 ]
     *             }
     *         ],
     *         "counterMeasures": [
     *             {
     *                 "name": "Massage",
     *                 "default": true
     *             }
     *         ],
     *         "averagePainLevel": 2.5,
     *         "averageStressLevel": 1.5
     *     },
     *     ...
     * ]
     *         </pre>
     * 
     */
    @GetMapping
    public ResponseEntity<List<DailyRecord>> getDailyRecords(Principal principal) {
        // Extract login name from Principal object
        String loginName = principal.getName();
        try {
            // Retrieve all daily records for the user
            List<DailyRecord> allRecords = recordController.getAllRecordsOfPrincipal(loginName);
            // Return the records with an OK status
            return new ResponseEntity<>(allRecords, HttpStatus.OK);
        } catch (EntityNotFoundException enfe) {
            // Log the exception if no records are found
            LOGGER.log(Level.SEVERE, enfe.getMessage());
            // Return a NOT_FOUND response status
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Handles HTTP GET requests for fetching default countermeasures.
     * <p>
     * This controller method retrieves a list of default countermeasures for the
     * current record associated with the authenticated user.
     * It leverages the Principal object to identify the user making the request.
     * </p>
     * <p>
     * Upon successful retrieval, it returns a list of {@link ElementSelectionDTO}
     * objects within a {@link ResponseEntity}.
     * Each {@code ElementSelectionDTO} represents a default countermeasure, with
     * its selection status.
     * </p>
     * 
     * @param principal the {@link Principal} object representing the currently
     *                  authenticated user, used to identify the user's session
     * @return a {@link ResponseEntity} containing a list of
     *         {@link ElementSelectionDTO} objects representing the default
     *         countermeasures,
     *         or a NOT_FOUND status if the current record cannot be found.
     *         <p>
     *         Example of JSON response:
     * 
     *         <pre>
     * [
     *   {
     *     "name": "Entspannungsübungen",
     *     "selected": false
     *   },
     *  ...
     * ]
     *         </pre>
     * 
     */
    @GetMapping("/current/countermeasures")
    public ResponseEntity<List<ElementSelectionDTO>> getAllDefaultCounterMeasures(Principal principal) {
        // Extract login name from the Principal object representing the authenticated
        // user
        String loginName = principal.getName();
        try {
            // Attempt to retrieve the default countermeasures for the current record
            // associated with the user
            List<ElementSelectionDTO> contermeasures = recordController
                    .getCountermeasureSelectionForCurrentRecord(loginName);

            // Successfully retrieved the list; return it with an OK status
            return new ResponseEntity<>(contermeasures, HttpStatus.OK);
        } catch (EntityNotFoundException enfe) {
            // Log the exception if the current record cannot be found
            LOGGER.log(Level.SEVERE, enfe.getMessage());

            // Return a NOT_FOUND response status if the entity (record) is not found
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Updates the countermeasure selections for the current record of the
     * authenticated user.
     * <p>
     * This method allows the authenticated user to update their countermeasure
     * selections for their current health record.
     * The client submits a list of countermeasures with their selected statuses.
     * Each {@link ElementSelectionDTO} includes
     * the name of the countermeasure and whether it is selected.
     * </p> 
     *
     * @param principal        the {@link Principal} object representing the
     *                         currently authenticated user, used to identify the
     *                         user's session
     * @param currentSelection a list of {@link ElementSelectionDTO} objects
     *                         representing the countermeasures and their selection
     *                         status
     * @return a {@link ResponseEntity<Void>} with an OK status if the update is
     *         successful,
     *         or a NOT_FOUND status if the current record cannot be found
     * <p>
     * Example of JSON input for the request body:
     * 
     * <pre>
     * [
     *   {
     *     "name": "Entspannungsübungen",
     *     "selected": false
     *   },
     *   {
     *     "name": "Massage",
     *     "selected": false
     *   }
     * ]
     * </pre>
     * 
     * This input updates the selection status of specified countermeasures for the
     * user's current record.
     */
    @PostMapping("/current/countermeasures")
    public ResponseEntity<Void> updateCounterMeasures(Principal principal,
            @RequestBody List<ElementSelectionDTO> currentSelection) {
        // Extract login name from Principal object
        String loginName = principal.getName();
        try {
            // Update the countermeasure selections for the current record
            recordController.setCountermeasureSelectionForCurrentRecord(loginName, currentSelection);
            // Return OK status if the update is successful
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EntityNotFoundException enfe) {
            // Log the exception if the current record cannot be found
            LOGGER.log(Level.SEVERE, enfe.getMessage());
            // Return NOT_FOUND status if the update fails
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Fetches an overview of the current health record for the authenticated user.
     * <p>
     * This method retrieves a summary of the latest health record for the user,
     * identified by their login name.
     * The summary includes key details such as symptom intensity, identified
     * symptoms, stress level, primary stressors,
     * and the date and time of the entry. This overview provides a quick snapshot
     * of the user's current health status.
     * </p>
     *
     * @param principal the {@link Principal} object representing the currently
     *                  authenticated user, used to identify the user's session
     * @return a {@link ResponseEntity<OverviewDTO>} containing the overview data
     *         for the authenticated user,
     *         or a NOT_FOUND status if no current record can be found
     * <p>
     * Example of JSON response structure:
     * 
     * <pre>
     * {
     *   "intensity": 2,
     *   "symptoms": "Migräne",
     *   "stressLevel": "tief",
     *   "stressors": "Schlafmangel",
     *   "dateOfEntry": "2024-04-01",
     *   "timeOfEntry": "AFTERNOON"
     * }
     * </pre>
     * 
     * This JSON represents a concise overview of the user's current health record,
     * including the most recent symptoms and stressors.
     */
    @GetMapping("/current/overview")
    public ResponseEntity<OverviewDTO> getOverview(Principal principal) {
        // Extract login name from Principal object
        String loginName = principal.getName();
        try {
            // Retrieve the overview of the current record for the user
            OverviewDTO overview = recordController.getOverview(loginName);
            // Return the overview with an OK status
            return new ResponseEntity<>(overview, HttpStatus.OK);
        } catch (EntityNotFoundException enfe) {
            // Log the exception if the overview cannot be found
            LOGGER.log(Level.SEVERE, enfe.getMessage());
            // Return NOT_FOUND status if there is no current overview available
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
