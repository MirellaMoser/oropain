package ch.zhaw.mosltech.NoPainIsGainBackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.zhaw.mosltech.NoPainIsGainBackend.dto.GraphDataDTO;
import ch.zhaw.mosltech.NoPainIsGainBackend.entity.DailyRecord;
import ch.zhaw.mosltech.NoPainIsGainBackend.exceptions.EntityNotFoundException;
import jakarta.transaction.Transactional;

/**
 * Service layer for generating plot data for pain and stress levels over time.
 * <p>
 * This controller leverages the {@link RecordController} to fetch user-specific records and aggregates them into
 * {@link GraphDataDTO} objects suitable for graphical representation. The primary purpose is to support visualizations
 * of health trends, specifically pain and stress levels, based on daily health records.
 * </p>
 */
@Service
@Transactional
public class PlotController {

    @Autowired
    private RecordController recordController; // Dependency on RecordController for fetching user records

    /**
     * Retrieves aggregated pain level data for a user, ready for plotting.
     * <p>
     * This method fetches all daily records for the specified user and aggregates the average pain levels
     * into a {@link GraphDataDTO}, which includes both the dates (as labels) and the corresponding pain levels (as data points).
     * </p>
     * @param loginName The username of the user for whom the data is being fetched.
     * @return A {@link GraphDataDTO} object containing the aggregated pain data suitable for charting.
     * @throws EntityNotFoundException If no records are found for the specified user.
     */
    public GraphDataDTO getPainPlotData(String loginName) throws EntityNotFoundException {
        GraphDataDTO res = new GraphDataDTO();       
        List<DailyRecord> dailyRecords = recordController.getAllRecordsOfPrincipal(loginName);

        for (DailyRecord dailyRecord : dailyRecords) {
            res.getLabels().add(dailyRecord.getDateTime().toString()); // Add date as label
            res.getData().add(dailyRecord.getAveragePainLevel()); // Add average pain level as data
        }
        return res;
    }

    /**
     * Retrieves aggregated stress level data for a user, ready for plotting.
     * <p>
     * Similar to {@link #getPainPlotData}, this method fetches all daily records for the specified user and
     * aggregates the average stress levels into a {@link GraphDataDTO}. The DTO then contains the dates (as labels)
     * and the corresponding stress levels (as data points), ready for graphical representation.
     * </p>
     * @param loginName The username of the user for whom the data is being fetched.
     * @return A {@link GraphDataDTO} object containing the aggregated stress data suitable for charting.
     * @throws EntityNotFoundException If no records are found for the specified user.
     */
    public GraphDataDTO getStressPlotData(String loginName) throws EntityNotFoundException  {
        GraphDataDTO res = new GraphDataDTO();        
        List<DailyRecord> dailyRecords = recordController.getAllRecordsOfPrincipal(loginName);

        for (DailyRecord dailyRecord : dailyRecords) {
            res.getLabels().add(dailyRecord.getDateTime().toString()); // Add date as label
            res.getData().add(dailyRecord.getAverageStressLevel()); // Add average stress level as data
        }

        return res;
    }   
    
}