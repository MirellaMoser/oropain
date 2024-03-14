package ch.zhaw.mosltech.NoPainIsGainBackend.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Value;

@Value
public class GraphDataDTO {
    private List<String> labels = new ArrayList<>();
    private List<Integer> data  = new ArrayList<>();

}


