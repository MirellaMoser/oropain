package ch.zhaw.mosltech.NoPainIsGainBackend.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Value;


/**
 * Data Transfer Object (DTO) for representing graph data, including labels and corresponding values.
 * <p>
 * This DTO is typically used for transferring data necessary to render a chart or graph on the frontend. 
 * It encapsulates a series of labels (e.g., dates or categories) along with corresponding data points (e.g., 
 * measurements or counts), enabling the visualization of trends or distributions.
 * </p>
 */
@Value
public class GraphDataDTO {

    /**
     * A list of labels for the graph.
     * <p>
     * Each label corresponds to a point on the graph's x-axis. For example, in a time series graph, 
     * labels might represent successive dates or times.
     * </p>
     */
    private List<String> labels = new ArrayList<>();

    /**
     * A list of data points corresponding to the labels.
     * <p>
     * This list contains the values for each label and is plotted on the graph's y-axis. For instance, 
     * in a health tracking application, this could represent daily stress level scores.
     * </p>
     */
    private List<Double> data = new ArrayList<>();
}


