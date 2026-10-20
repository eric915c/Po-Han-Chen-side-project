package chatclient;

import java.util.List;

/**
 * Configuration and sample data for the local portfolio demo.
 *
 * When enabled, the client does not call the original course server.
 */
public final class DemoMode {
    public static final boolean ENABLED = true;

    public static final List<String> ROOMS = List.of(
            "Project Discussion",
            "Study Group",
            "Demo Room"
    );

    private DemoMode() {
        // Utility class; do not instantiate.
    }
}
