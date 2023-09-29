package com.marketplace.models;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Map;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Info {
    String userId;
    String userName;
    String status;
    String supportLink;
    Map<String, String> rules;
}
