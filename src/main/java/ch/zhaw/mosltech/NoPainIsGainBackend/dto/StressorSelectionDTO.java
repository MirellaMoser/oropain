package ch.zhaw.mosltech.NoPainIsGainBackend.dto;

import lombok.Value;

@Value
public class StressorSelectionDTO {
    private String name;
    private String category;
    private boolean selected;
}
