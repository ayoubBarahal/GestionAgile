package project.gestionprojet.Entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Status {
        ToDo,
        @JsonProperty("IN_PROGRESS")
        InProgress,
        Done

}
