package ch.zhaw.mosltech.NoPainIsGainBackend.dto;

import lombok.Value;

/**
 * Data Transfer Object (DTO) for capturing selections of stressors.
 * <p>
 * Represents a stressor identified by the user or application, including its name, category (e.g., environmental, psychological),
 * and whether it has been selected (true) or not (false). This class facilitates the transfer of stressor data, particularly
 * for user interfaces where users can select or deselect stressors that affect them.
 * </p>
 */
@Value
public class StressorSelectionDTO {

    /**
     * The name of the stressor.
     * <p>
     * This field holds the identifier or descriptive name of the stressor, providing a label for understanding what the stressor is.
     * </p>
     */
    private String name;

    /**
     * The category of the stressor.
     * <p>
     * Categorizes the stressor into broader groups to help with classification and analysis, such as 'Work-related', 'Personal', etc.
     * </p>
     */
    private String category;

    /**
     * Indicates whether the stressor is selected.
     * <p>
     * A boolean value representing the user's selection state of the stressor. True if the user has identified the stressor as affecting them,
     * false otherwise.
     * </p>
     */
    private boolean selected;
}
