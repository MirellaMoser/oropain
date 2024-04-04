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

    /**
     * Retrieves a graphical representation of pain levels over time for the
     * authenticated user.
     * <p>
     * This endpoint provides data suitable for plotting pain levels experienced by
     * the user over a series of dates.
     * The returned {@link GraphDataDTO} contains arrays of labels and corresponding
     * pain data points. Labels typically
     * represent dates, and the data points represent the pain levels recorded on
     * those dates.
     * </p>
     * 
     *
     * @param principal the {@link Principal} object representing the currently
     *                  authenticated user, used to identify the user's session.
     * @return a {@link ResponseEntity<GraphDataDTO>} containing the pain data for
     *         plotting, or a NOT_FOUND status if no data is available.
     *         <p>
     *         Example of JSON response structure:
     * 
     *         <pre>
     * {
     *   "labels": [
     *     "2024-03-30",
     *     "2024-03-31",
     *     "2024-04-01"
     *   ],
     *   "data": [2.5, 7, 2]
     * }
     *         </pre>
     * 
     *         This JSON format is designed for easy integration with charting
     *         libraries,
     *         enabling the visualization of pain trends over time.
     * 
     */
    @GetMapping("/pain")
    public ResponseEntity<GraphDataDTO> getPainData(Principal principal) {
        // Extract login name from the Principal object
        String loginName = principal.getName();
        try {
            // Retrieve pain data suitable for graph plotting
            GraphDataDTO painData = plotController.getPainPlotData(loginName);
            // Return the pain data with an OK status
            return new ResponseEntity<>(painData, HttpStatus.OK);
        } catch (EntityNotFoundException enfe) {
            // Log the exception if no pain data can be found
            LOGGER.log(Level.SEVERE, enfe.getMessage());
            // Return NOT_FOUND status if the pain data is unavailable
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Fetches data for visualizing stress levels over time for the authenticated
     * user.
     * <p>
     * This method returns data suitable for graphing stress levels, encapsulated in
     * a {@link GraphDataDTO} object.
     * The response includes an array of date labels and a corresponding array of
     * stress level data points. This setup
     * facilitates easy integration with visualization tools or libraries, allowing
     * users to track their stress levels
     * over a specified period.
     * </p>
     * 
     *
     * 
     * @param principal the {@link Principal} object representing the currently
     *                  authenticated user, used to identify the user's session.
     * @return a {@link ResponseEntity<GraphDataDTO>} containing the stress data for
     *         plotting, or a NOT_FOUND status if no data is available.
     *         <p>
     *         Example of JSON response structure:
     * 
     *         <pre>
     * {
     *   "labels": [
     *     "2024-03-30",
     *     "2024-03-31",
     *     "2024-04-01"
     *   ],
     *   "data": [1.5, 3, 1]
     * }
     *         </pre>
     * 
     *         This JSON format is designed for easy integration with charting
     *         libraries,
     *         enabling the visualization of stress trends over time.
     */
    @GetMapping("/stress")
    public ResponseEntity<GraphDataDTO> getStressData(Principal principal) {
        // Extract the login name from the Principal object
        String loginName = principal.getName();
        try {
            // Retrieve stress data suitable for graph plotting
            GraphDataDTO stressData = plotController.getStressPlotData(loginName);
            // Successfully retrieved the data; return it with an OK status
            return new ResponseEntity<>(stressData, HttpStatus.OK);
        } catch (EntityNotFoundException enfe) {
            // Log the exception if no stress data can be found
            LOGGER.log(Level.SEVERE, enfe.getMessage());
            // Return a NOT_FOUND status if the stress data is unavailable
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
