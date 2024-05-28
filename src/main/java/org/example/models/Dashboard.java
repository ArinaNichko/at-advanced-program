package org.example.models;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class Dashboard {
    String owner;
    String description;
    int id;
    String name;
    List<Widgets> widgets;
}
