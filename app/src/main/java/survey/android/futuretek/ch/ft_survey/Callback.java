package survey.android.futuretek.ch.ft_survey;

/**
 * Used as a callback.
 * Not sure why I'm not allowed to use Java 8's features. java.util.Function isn't available in my environment; probably using an old Android SDK or ide misconfigured
 */
public interface Callback<T> {
    void callback(T object);
}
