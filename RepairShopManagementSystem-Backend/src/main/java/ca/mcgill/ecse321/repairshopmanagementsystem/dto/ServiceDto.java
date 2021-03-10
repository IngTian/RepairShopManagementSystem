package ca.mcgill.ecse321.repairshopmanagementsystem.dto;

import java.util.Set;

public class ServiceDto {
    private String serviceType;
    private Set<AssistantDto> assistant;
    private AppointmentDto appointment;

    public ServiceDto() {
    }

    public ServiceDto(String serviceType, Set<AssistantDto> assistant, AppointmentDto appointment) {
        this.serviceType = serviceType;
        this.assistant = assistant;
        this.appointment = appointment;
    }

    public String getServiceType() {
        return this.serviceType;
    }

    public Set<AssistantDto> getAssistant() {
        return this.assistant;
    }

    public AppointmentDto getAppointment() {
        return this.appointment;
    }
}
