package org.example.dao.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

@ToString
@Getter
@Setter
@Component
public class Metric {

    String nameMethod;
    String param;
    Long time;

}
