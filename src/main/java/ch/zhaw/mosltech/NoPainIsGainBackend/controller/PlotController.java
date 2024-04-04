package ch.zhaw.mosltech.NoPainIsGainBackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.zhaw.mosltech.NoPainIsGainBackend.dto.GraphDataDTO;
import ch.zhaw.mosltech.NoPainIsGainBackend.entity.DailyRecord;
import ch.zhaw.mosltech.NoPainIsGainBackend.exceptions.EntityNotFoundException;

@Service
public class PlotController {

    @Autowired
    private RecordController recordController;

    public GraphDataDTO getPainPlotData(String loginName) throws EntityNotFoundException {
        GraphDataDTO res = new GraphDataDTO();       
        List<DailyRecord> dailyRecords = recordController.getAllRecordsOfPrincipal(loginName);

        for (DailyRecord dailyRecord : dailyRecords) {
            res.getLabels().add(dailyRecord.getDateTime().toString());
            res.getData().add(dailyRecord.getAveragePainLevel());
        }
        return res;
    }

    public GraphDataDTO getStressPlotData(String loginName) throws EntityNotFoundException  {
        GraphDataDTO res = new GraphDataDTO();        
        List<DailyRecord> dailyRecords = recordController.getAllRecordsOfPrincipal(loginName);

        for (DailyRecord dailyRecord : dailyRecords) {
            res.getLabels().add(dailyRecord.getDateTime().toString());
            res.getData().add(dailyRecord.getAverageStressLevel());
        }

        return res;
    }   
    
}
