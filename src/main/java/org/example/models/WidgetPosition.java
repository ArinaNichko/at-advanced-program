package org.example.models;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class WidgetPosition {
    String positionX;
    String positionY;
}
