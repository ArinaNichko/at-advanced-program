package org.example.models;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class WidgetOptions {
    boolean zoom;
    boolean latest;
    boolean includeMethods;
    String timeline;
    String viewMode;
    String launchNameFilter;
}
