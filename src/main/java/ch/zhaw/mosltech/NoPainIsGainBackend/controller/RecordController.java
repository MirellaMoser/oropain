package ch.zhaw.mosltech.NoPainIsGainBackend.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.zhaw.mosltech.NoPainIsGainBackend.dto.ElementSelectionDTO;
import ch.zhaw.mosltech.NoPainIsGainBackend.dto.OverviewDTO;
import ch.zhaw.mosltech.NoPainIsGainBackend.entity.CounterMeasure;
import ch.zhaw.mosltech.NoPainIsGainBackend.entity.CounterMeasureRepository;
import ch.zhaw.mosltech.NoPainIsGainBackend.entity.DailyRecord;
import ch.zhaw.mosltech.NoPainIsGainBackend.entity.DailyRecordRepository;
import ch.zhaw.mosltech.NoPainIsGainBackend.entity.User;
import ch.zhaw.mosltech.NoPainIsGainBackend.entity.UserRepository;
import ch.zhaw.mosltech.NoPainIsGainBackend.exceptions.EntityNotFoundException;
import jakarta.transaction.Transactional;

/**
 * Service class for handling operations related to user records.
 * It facilitates fetching and updating of daily health records and countermeasure selections,
 * and also generating an overview of the daily health status.
 */
@Service
@Transactional
public class RecordController {

    @Autowired
    private CounterMeasureRepository counterMeasureRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DailyRecordRepository dailyRecordRepository;

    /**
     * Retrieves a list of countermeasure selections for the current record of a given user.
     * 
     * @param loginName The username of the user whose record is being queried.
     * @return A list of {@link ElementSelectionDTO} representing the selected countermeasures.
     * @throws EntityNotFoundException If the daily record or user cannot be found.
     */
    public List<ElementSelectionDTO> getCountermeasureSelectionForCurrentRecord(String loginName)
            throws EntityNotFoundException {
        // Fetch the current daily record for the given username.
        DailyRecord dailyRecord = getCurrentDailyRecord(loginName);

        // Retrieve all default countermeasures from the database.
        List<CounterMeasure> defaultCounterMeasures = counterMeasureRepository.findAllByIsDefault(true);
        List<ElementSelectionDTO> selectedCounterMeasures = new ArrayList<>();

        // Iterate over each default countermeasure and check if it is selected in the current daily record.
        for (CounterMeasure defaultCM : defaultCounterMeasures) {
            boolean isSelected = dailyRecord.getCounterMeasures().contains(defaultCM);
            selectedCounterMeasures.add(new ElementSelectionDTO(defaultCM.getName(), isSelected));
        }

        return selectedCounterMeasures;
    }

    /**
     * Updates the countermeasure selections for the current record of a given user.
     * 
     * @param loginName The username of the user whose record is to be updated.
     * @param updatedSelection A list of {@link ElementSelectionDTO} representing the updated selections.
     * @throws EntityNotFoundException If the daily record or user cannot be found.
     */
    public void setCountermeasureSelectionForCurrentRecord(String loginName, List<ElementSelectionDTO> updatedSelection)
            throws EntityNotFoundException {
        // Fetch the current daily record for the given username.
        DailyRecord dailyRecord = getCurrentDailyRecord(loginName);

        // Clear existing countermeasure selections.
        dailyRecord.getCounterMeasures().clear();

        // Iterate over the updated selections.
        for (ElementSelectionDTO selection : updatedSelection) {
            // Proceed only if the countermeasure is selected.
            if (selection.isSelected()) {
                // Check if the countermeasure already exists in the repository.
                Optional<CounterMeasure> cmOptional = counterMeasureRepository.findById(selection.getName());
                if (cmOptional.isPresent()) {
                    // Add the existing countermeasure to the daily record.
                    dailyRecord.getCounterMeasures().add(cmOptional.get());
                } else {
                    // Create and save a new countermeasure if it does not exist.
                    CounterMeasure newCM = new CounterMeasure();
                    newCM.setName(selection.getName());
                    newCM.setDefault(false);                    
                    counterMeasureRepository.save(newCM);
                    dailyRecord.getCounterMeasures().add(newCM);
                }
            }
        }

        // Save the updated daily record.
        dailyRecordRepository.save(dailyRecord);
    }

    /**
     * Generates an overview DTO for the latest daily record of a given user.
     * 
     * @param loginName The username of the user whose overview is to be generated.
     * @return An {@link OverviewDTO} containing a summary of the latest daily record.
     * @throws EntityNotFoundException If the daily record or user cannot be found.
     */
    public OverviewDTO getOverview(String loginName) throws EntityNotFoundException {
        // Fetch the current daily record for the given username.
        DailyRecord dailyRecord = getCurrentDailyRecord(loginName);

        // Generate and return an overview DTO from the daily record.
        return new OverviewDTO(dailyRecord);
    }

    /**
     * Retrieves the most recent DailyRecord for a given user, creating a new one if necessary.
     * 
     * @param loginName The username of the user.
     * @return The most recent {@link DailyRecord} for the user.
     * @throws EntityNotFoundException If the user cannot be found.
     */
    public DailyRecord getCurrentDailyRecord(String loginName) throws EntityNotFoundException {
        Optional<User> userOptional = userRepository.findById(loginName);
        if (!userOptional.isPresent()) {
            throw new EntityNotFoundException();
        }

        User user = userOptional.get();
        DailyRecord dailyRecord = dailyRecordRepository.findMostRecentSituation(user);

        // Create a new daily record if the user does not have any records yet.
        if (dailyRecord == null) {
            dailyRecord = new DailyRecord();
            user.getRecords().add(dailyRecord);
            dailyRecordRepository.save(dailyRecord);
            userRepository.save(user);
        }

        return dailyRecord;
    }

    /**
     * Retrieves all daily records for a given user, sorted in a specified order.
     * 
     * @param loginName The username of the user.
     * @return A list of all {@link DailyRecord}s for the user.
     * @throws EntityNotFoundException If the user cannot be found.
     */
    public List<DailyRecord> getAllRecordsOfPrincipal(String loginName) throws EntityNotFoundException {
        Optional<User> userOptional = userRepository.findById(loginName);
        if (!userOptional.isPresent()) {
            throw new EntityNotFoundException();
        }

        User user = userOptional.get();
        return dailyRecordRepository.findAllSiutationsOrdered(user);
    }

}
