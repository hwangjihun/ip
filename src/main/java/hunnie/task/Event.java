package hunnie.task;

import hunnie.exception.HunnieException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents an event task with a description, start date, and end date.
 */
public class Event extends Task {

    protected LocalDate from;
    protected LocalDate to;
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy");

    /**
     * Creates a new event task with the specified description, start date, and end date.
     *
     * @param description Description of the event task.
     * @param from Start date in yyyy-MM-dd format.
     * @param to End date in yyyy-MM-dd format.
     * @throws HunnieException If either date format is invalid.
     */
    public Event(String description, String from, String to) throws HunnieException{
        super(description);
        this.from = parseDate(from);
        this.to = parseDate(to);
    }

    /**
     * Parses a date string into a LocalDate object.
     *
     * @param dateStr Date string in yyyy-MM-dd format.
     * @return LocalDate object representing the parsed date.
     * @throws HunnieException If the date format is invalid.
     */
    private LocalDate parseDate(String dateStr) throws HunnieException {
        try {
            return LocalDate.parse(dateStr, INPUT_FORMAT);
        } catch (DateTimeParseException e) {
            throw new HunnieException("Invalid date format. Use yyyy-MM-dd (e.g., 2019-10-15)");
        }
    }

    /**
     * Returns the start date of this event task.
     *
     * @return Start date as a LocalDate object.
     */
    public LocalDate getFrom() {
        return from;
    }

    /**
     * Returns the end date of this event task.
     *
     * @return End date as a LocalDate object.
     */
    public LocalDate getTo() {
        return to;
    }

    /**
     * Formats a date into a string representation.
     *
     * @param date Date to format.
     * @return Formatted date string in MMM dd yyyy format.
     */
    private String formatDate(LocalDate date) {
        return date.format(OUTPUT_FORMAT);
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + formatDate(this.from) + " to: " + formatDate(this.to) + ")";
    }
}
