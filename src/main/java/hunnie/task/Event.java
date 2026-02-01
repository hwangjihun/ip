package hunnie.task;

import hunnie.exception.HunnieException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Event extends Task {

    protected LocalDate from;
    protected LocalDate to;
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy");

    public Event(String description, String from, String to) throws HunnieException{
        super(description);
        this.from = parseDate(from);
        this.to = parseDate(to);
    }

    private LocalDate parseDate(String dateStr) throws HunnieException {
        try {
            return LocalDate.parse(dateStr, INPUT_FORMAT);
        } catch (DateTimeParseException e) {
            throw new HunnieException("Invalid date format. Use yyyy-MM-dd (e.g., 2019-10-15)");
        }
    }

    public LocalDate getFrom() {
        return from;
    }

    public LocalDate getTo() {
        return to;
    }

    private String formatDate(LocalDate date) {
        return date.format(OUTPUT_FORMAT);
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + formatDate(this.from) + " to: " + formatDate(this.to) + ")";
    }
}
