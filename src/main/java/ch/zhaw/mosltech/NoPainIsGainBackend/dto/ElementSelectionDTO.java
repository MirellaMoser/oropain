package ch.zhaw.mosltech.NoPainIsGainBackend.dto;

import lombok.Value;


/**
 * Data Transfer Object (DTO) representing an element (such as a symptom, countermeasure, or stressor)
 * with a selection state.
 * <p>
 * This class is utilized to convey information about specific elements (e.g., symptoms in a health tracking application)
 * and their selection state (whether or not they have been chosen or are applicable in a given context).
 * </p>
 */
@Value
public class ElementSelectionDTO {

    /**
     * The name of the element.
     * <p>
     * This field stores the identifier or descriptive name of the element, such as the name of a symptom or countermeasure.
     * </p>
     */
    private String name;

    /**
     * The selection state of the element.
     * <p>
     * This boolean field indicates whether the element is selected (true) or not (false). The selection state
     * is used to represent the user's choice or applicability of the element in a given situation or record.
     * </p>
     */
    private boolean selected;
}
