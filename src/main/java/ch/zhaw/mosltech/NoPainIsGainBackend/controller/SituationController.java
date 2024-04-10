package ch.zhaw.mosltech.NoPainIsGainBackend.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.zhaw.mosltech.NoPainIsGainBackend.dto.ElementSelectionDTO;
import ch.zhaw.mosltech.NoPainIsGainBackend.dto.InputDTO;
import ch.zhaw.mosltech.NoPainIsGainBackend.dto.StressorSelectionDTO;
import ch.zhaw.mosltech.NoPainIsGainBackend.entity.DailyRecord;
import ch.zhaw.mosltech.NoPainIsGainBackend.entity.DailyRecordRepository;
import ch.zhaw.mosltech.NoPainIsGainBackend.entity.ETimeOfDay;
import ch.zhaw.mosltech.NoPainIsGainBackend.entity.Situation;
import ch.zhaw.mosltech.NoPainIsGainBackend.entity.SituationRepository;
import ch.zhaw.mosltech.NoPainIsGainBackend.entity.Stressor;
import ch.zhaw.mosltech.NoPainIsGainBackend.entity.StressorRepository;
import ch.zhaw.mosltech.NoPainIsGainBackend.entity.Symptom;
import ch.zhaw.mosltech.NoPainIsGainBackend.entity.SymptomRepository;
import ch.zhaw.mosltech.NoPainIsGainBackend.entity.User;
import ch.zhaw.mosltech.NoPainIsGainBackend.entity.UserRepository;
import ch.zhaw.mosltech.NoPainIsGainBackend.exceptions.EntityNotFoundException;
import jakarta.transaction.Transactional;

/**
 * Service class for managing situations within daily health records.
 * It facilitates operations such as adding new situations, fetching current
 * situation selections,
 * and preparing DTOs for input forms.
 */
@Service
@Transactional
public class SituationController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DailyRecordRepository dailyRecordRepository;

    @Autowired
    private SituationRepository situationRepository;

    @Autowired
    private SymptomRepository symptomRepository;

    @Autowired
    private StressorRepository stressorRepository;

    @Autowired
    private RecordController recordController;

    /**
     * Prepares a blank {@link InputDTO} with default selections for symptoms and
     * stressors,
     * and available time slots for a new situation entry.
     * 
     * @param loginName The username of the user for whom the input DTO is being
     *                  prepared.
     * @return An {@link InputDTO} populated with default data.
     * @throws EntityNotFoundException If the user or daily record cannot be found.
     */
    public InputDTO populateBlankInputDTO(String loginName) throws EntityNotFoundException {
        // Fetch default selections for symptoms and stressors.
        List<ElementSelectionDTO> defaultSymptoms = getDefaultSymptomDTOs();
        List<StressorSelectionDTO> defaultStressors = getDefaultStressorDTOs();

        // Define available time slots for situation entries.
        List<ETimeOfDay> availableEntries = new ArrayList<>(
                List.of(ETimeOfDay.MORNING, ETimeOfDay.AFTERNOON, ETimeOfDay.EVENING));

        // Retrieve the current daily record for the user.
        DailyRecord dailyRecord = recordController.getCurrentDailyRecord(loginName);

        // Exclude time slots already used in today's situations from availableEntries.
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        if (sdf.format(dailyRecord.getDateTime()).equals(sdf.format(new Date()))) {
            dailyRecord.getSituations().forEach(situation -> availableEntries.remove(situation.getTimeOfDay()));
        }

        // Create and return the InputDTO with default values and available time slots.
        return new InputDTO(0, defaultSymptoms, 0, defaultStressors, new Date(), ETimeOfDay.MORNING, availableEntries);
    }

    /**
     * Retrieves the current selection of symptoms for the latest situation of a
     * user's daily record.
     * 
     * @param loginName The username of the user.
     * @return A list of {@link ElementSelectionDTO} representing the current
     *         symptom selections.
     * @throws EntityNotFoundException If the daily record cannot be found.
     */
    public List<ElementSelectionDTO> getCurrentSituationSymptomsSelection(String loginName)
            throws EntityNotFoundException {
        // Retrieve the current daily record for the user.
        DailyRecord dailyRecord = recordController.getCurrentDailyRecord(loginName);

        // Fetch default symptoms from the repository.
        List<Symptom> symptoms = symptomRepository.findByIsDefault(true);
        List<ElementSelectionDTO> selectedSymptoms = new ArrayList<>();

        // Check each symptom against those in the latest situation, marking as selected
        // if present.
        for (Symptom symptom : symptoms) {
            boolean isSelected = dailyRecord.getLatestSituation().getSymptoms().contains(symptom);
            selectedSymptoms.add(new ElementSelectionDTO(symptom.getName(), isSelected));
        }

        return selectedSymptoms;
    }

    /**
     * Adds a new situation to the user's current daily record based on the provided
     * {@link InputDTO}.
     * 
     * @param loginName    The username of the user.
     * @param newSituation An {@link InputDTO} containing the details of the new
     *                     situation to be added.
     * @throws EntityNotFoundException If the user cannot be found.
     */
    public void addSituation(String loginName, InputDTO newSituation) throws EntityNotFoundException {
        // Initialize a new Situation object from the InputDTO.
        Situation situation = new Situation();
        situation.setTimeOfDay(newSituation.getTimeOfDay());
        situation.setPainLevel(newSituation.getIntensity());
        situation.setStressLevel(newSituation.getStressLevel());

        // Add selected stressors to the situation.
        for (StressorSelectionDTO element : newSituation.getStressors()) {
            if (!element.isSelected())
                continue;
            Optional<Stressor> so = stressorRepository.findById(element.getName());
            if (so.isPresent()) {
                situation.getStressors().add(so.get());
            } else {
                Stressor s = new Stressor();
                s.setName(element.getName());
                s.setCategory("Custom");
                stressorRepository.save(s);
                situation.getStressors().add(s);
            }
        }

        // Add selected symptoms to the situation.
        for (ElementSelectionDTO element : newSituation.getSymptoms()) {
            if (!element.isSelected())
                continue;
            Optional<Symptom> so = symptomRepository.findById(element.getName());
            if (so.isPresent()) {
                situation.getSymptoms().add(so.get());
            } else {
                Symptom s = new Symptom();
                s.setName(element.getName());
                symptomRepository.save(s);
                situation.getSymptoms().add(s);
            }
        }

        // Save the newly created situation.
        situationRepository.save(situation);

        // Add the situation to the current or a new daily record based on the date.
        DailyRecord dailyRecord = recordController.getCurrentDailyRecord(loginName);
        Optional<User> userOptional = userRepository.findById(loginName);

        if (!userOptional.isPresent()) {
            throw new EntityNotFoundException();
        }
        User user = userOptional.get();

        // Create a new daily record if the date does not match today, else add to the
        // current daily record.
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        if (!sdf.format(dailyRecord.getDateTime()).equals(sdf.format(new Date()))) {
            DailyRecord newRecord = new DailyRecord();
            newRecord.setDateTime(new Date());
            newRecord.getSituations().add(situation);
            dailyRecordRepository.save(newRecord);
            user.getRecords().add(newRecord);
        } else {
            dailyRecord.getSituations().add(situation);
            dailyRecordRepository.save(dailyRecord);
        }
        userRepository.save(user);
    }

    /**
     * Fetches default symptom selections from the database.
     * 
     * @return A list of {@link ElementSelectionDTO} for default symptoms.
     */
    private List<ElementSelectionDTO> getDefaultSymptomDTOs() {
        List<ElementSelectionDTO> defaultSelection = new ArrayList<>();

        List<Symptom> defaultSymptoms = symptomRepository.findByIsDefault(true);
        for (Symptom symptom : defaultSymptoms) {
            defaultSelection.add(new ElementSelectionDTO(symptom.getName(), false));
        }

        return defaultSelection;
    }

    /**
     * Fetches default stressor selections from the database.
     * 
     * @return A list of {@link StressorSelectionDTO} for default stressors.
     */
    private List<StressorSelectionDTO> getDefaultStressorDTOs() {
        List<StressorSelectionDTO> defaultSelection = new ArrayList<>();

        List<Stressor> defaultStressors = stressorRepository.findByIsDefault(true);
        for (Stressor stressor : defaultStressors) {
            defaultSelection.add(new StressorSelectionDTO(stressor.getName(), stressor.getCategory(), false));
        }

        return defaultSelection;
    }

}