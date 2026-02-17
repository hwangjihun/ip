package hunnie.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import hunnie.exception.HunnieException;

/**
 * Represents a deadline task with a description and a due date.
 */
public class Deadline extends Task {

    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy");
    protected LocalDate by;

    /**
     * Creates a new deadline task with the specified description and due date.
     *
     * @param description Description of the deadline task.
     * @param by Due date in yyyy-MM-dd format.
     * @throws HunnieException If the date format is invalid.
     */
    public Deadline(String description, String by) throws HunnieException {
        super(description);
        this.by = parseDate(by);
    }

    /**
     * Parses a date string into a LocalDate object.
     *
     * @param dateStr Date string in yyyy-MM-dd format.
     * @return LocalDate object representing the parsed date.
     * @throws HunnieException If the date format is invalid.
     */
    private LocalDate parseDate(String dateStr) throws HunnieException {
        assert dateStr != null : "Date input should not be null";
        try {
            return LocalDate.parse(dateStr, INPUT_FORMAT);
        } catch (DateTimeParseException e) {
            throw new HunnieException("Invalid date format. Use yyyy-MM-dd (e.g., 2019-10-15)");
        }
    }

    /**
     * Returns the due date of this deadline task.
     *
     * @return Due date as a LocalDate object.
     */
    public LocalDate getBy() {
        return by;
    }

    /**
     * Returns the due date as a formatted string.
     *
     * @return Due date in MMM dd yyyy format.
     */
    public String getByString() {
        return by.format(OUTPUT_FORMAT);
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + getByString() + ")";
    }
}
