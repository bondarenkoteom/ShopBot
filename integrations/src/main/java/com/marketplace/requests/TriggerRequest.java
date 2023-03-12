package com.marketplace.requests;

import com.marketplace.constant.Trigger;
import lombok.Data;

@Data
public class TriggerRequest {
    Long userId;
    Trigger trigger;
}
