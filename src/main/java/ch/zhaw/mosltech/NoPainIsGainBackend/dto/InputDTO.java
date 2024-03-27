package ch.zhaw.mosltech.NoPainIsGainBackend.dto;

import java.util.Date;
import java.util.List;

import ch.zhaw.mosltech.NoPainIsGainBackend.entity.ETimeOfDay;
import lombok.Value;

@Value
public class InputDTO {
 private int intensity;
    private List<ElementSelectionDTO> symptoms;
    private int stressLevel;
    private List<StressorSelectionDTO> stressors;
    private Date dateOfEntry;
    private ETimeOfDay timeOfDay;
}
