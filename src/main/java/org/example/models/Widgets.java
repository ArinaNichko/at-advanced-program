package org.example.models;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class Widgets {
    String widgetName;
    int widgetId;
    String widgetType;
    WidgetSize widgetSize;
    WidgetPosition widgetPosition;
    WidgetOptions widgetOptions;
}
