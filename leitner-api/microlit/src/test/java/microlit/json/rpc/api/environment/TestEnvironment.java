package microlit.json.rpc.api.environment;

import microlit.json.rpc.api.body.request.notification.NotificationRequest;
import microlit.json.rpc.api.body.request.positional.PositionalRequest;

public class TestEnvironment {
    public static final String METHOD_NAME = "smoke";
    public static final String INCORRECT_METHOD_NAME = "incorrectMethodName";
    public static final Object[] ARGS = {1, "ihg"};
    public static final PositionalRequest POSITIONAL_REQUEST = PositionalRequest.createWithNumericId(1, METHOD_NAME,
            ARGS);
    public static final PositionalRequest POSITIONAL_REQUEST_INCORRECT_METHOD = PositionalRequest.createWithNumericId(1, INCORRECT_METHOD_NAME,
            ARGS);
    public static final PositionalRequest POSITIONAL_REQUEST_INCORRECT_ARGS = PositionalRequest.createWithNumericId(1, METHOD_NAME,
            new Object[]{});
    public static final NotificationRequest NOTIFICATION_REQUEST = NotificationRequest.create(METHOD_NAME,
            ARGS);
    public static final NotificationRequest INCORRECT_METHOD_NOTIFICATION = NotificationRequest.create(INCORRECT_METHOD_NAME,
            ARGS);
    public static final NotificationRequest INCORRECT_ARGS_NOTIFICATION = NotificationRequest.create(METHOD_NAME,
            new Object[]{});

    public interface TestApi {
        String smoke(Integer j, String string);
    }

    public static class TestApiImpl implements TestApi {

        @Override
        public String smoke(Integer i, String string) {
            return string + i;
        }
    }
}
