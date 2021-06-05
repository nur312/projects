package validator.annotations;

public interface ValidationError {
    String getMessage();

    String getPath();

    Object getFailedValue();
}
