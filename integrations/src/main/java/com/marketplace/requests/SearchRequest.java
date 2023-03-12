package com.marketplace.requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.marketplace.constant.Category;
import lombok.Data;

import static com.fasterxml.jackson.annotation.JsonInclude.*;

@Data
public class SearchRequest {

    @JsonInclude(Include.NON_NULL)
    String query;

    @JsonInclude(Include.NON_NULL)
    Category category;

    @JsonInclude(Include.NON_NULL)
    Long ownerId;

}
