package org.example.models;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class WidgetSize {
    String width;
    String height;
}
