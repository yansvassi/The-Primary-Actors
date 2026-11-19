package service;

public class SlotUnavailableException extends RuntimeException {

    public SlotUnavailableException(String availabilityId) {
        super("No available slot with id " + availabilityId);
    }
}
