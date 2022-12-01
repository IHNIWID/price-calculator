package com.inpost.price.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ApiErrorResponse(@JsonProperty String errorMessage) {

}
