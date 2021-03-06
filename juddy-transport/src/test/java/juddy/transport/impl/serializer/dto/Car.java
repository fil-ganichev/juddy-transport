package juddy.transport.impl.serializer.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Car {

    private String regNumber;
    private int year;
    private Model model;
}
