package ai.polyakov.restaurantvoiting.util;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Component
public class TimeUtil {

    public Boolean isTimeBeforeControl(LocalTime time) {
        return time.isBefore(LocalDateTime.now().withHour(11).withMinute(0).withSecond(0).toLocalTime());
    }
}
