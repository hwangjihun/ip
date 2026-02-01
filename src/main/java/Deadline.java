import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Deadline extends Task {

    protected LocalDate by;
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy");

    public Deadline(String description, String by) throws HunnieException{
        super(description);
        this.by = parseDate(by);
    }

    private LocalDate parseDate(String dateStr) throws HunnieException {
        try {
            return LocalDate.parse(dateStr, INPUT_FORMAT);
        } catch (DateTimeParseException e) {
            throw new HunnieException("Invalid date format. Use yyyy-MM-dd (e.g., 2019-10-15)");
        }
    }

    public LocalDate getBy() {
        return by;
    }

    public String getByString() {
        return by.format(OUTPUT_FORMAT);
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + getByString() + ")";
    }
}