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

@Service
public class RecordController {

    @Autowired
    private CounterMeasureRepository counterMeasureRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DailyRecordRepository dailyRecordRepository;

    public List<ElementSelectionDTO> getCountermeasureSelectionForCurrentRecord(String loginanme)
            throws EntityNotFoundException {
        DailyRecord dailyRecord = getCurrentDailyRecord(loginanme);
        List<CounterMeasure> defaultCounterMeasures = counterMeasureRepository.findAllByIsDefault(true);
        List<ElementSelectionDTO> selectedCounterMeasures = new ArrayList<>();
        for (CounterMeasure defaultCM : defaultCounterMeasures) {
            if (dailyRecord.getCounterMeasures().contains(defaultCM)) {
                selectedCounterMeasures.add(new ElementSelectionDTO(defaultCM.getName(), true));
            } else {
                selectedCounterMeasures.add(new ElementSelectionDTO(defaultCM.getName(), false));
            }
        }
        return selectedCounterMeasures;
    }

    public void setCountermeasureSelectionForCurrentRecord(String loginName, List<ElementSelectionDTO> updatedSelection)
            throws EntityNotFoundException {
        DailyRecord dailyRecord = getCurrentDailyRecord(loginName);
        dailyRecord.getCounterMeasures().clear();
        for (ElementSelectionDTO elementSelectionDTO : updatedSelection) {
            if (elementSelectionDTO.isSelected()) {
                if (counterMeasureRepository.existsById(elementSelectionDTO.getName())) {
                    dailyRecord.getCounterMeasures()
                            .add(counterMeasureRepository.findById(elementSelectionDTO.getName()).get());
                } else {
                    CounterMeasure cm = new CounterMeasure();
                    cm.setName(elementSelectionDTO.getName());
                    cm.setDefault(false);
                    counterMeasureRepository.save(cm);
                    dailyRecord.getCounterMeasures().add(cm);
                }
            }
        }
        dailyRecordRepository.save(dailyRecord);
    }

    public OverviewDTO getOverview(String loginName) throws EntityNotFoundException {
        DailyRecord dailyRecord = getCurrentDailyRecord(loginName);
        return new OverviewDTO(dailyRecord);
    }

    public DailyRecord getCurrentDailyRecord(String loginName) throws EntityNotFoundException {
        Optional<User> userOptional = userRepository.findById(loginName);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            DailyRecord dailyRecord = dailyRecordRepository.findMostRecentSituation(user);
            // This is only true for new users. Otherwise a daily record exists.
            if (dailyRecord == null) {
                dailyRecord = new DailyRecord();
                user.getRecords().add(dailyRecord);
                dailyRecordRepository.save(dailyRecord);
                userRepository.save(user);
            }
            return dailyRecord;
        } else {
            throw new EntityNotFoundException();
        }
    }

}
