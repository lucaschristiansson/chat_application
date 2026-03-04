package dat055.group5.client;

import dat055.group5.export.NetworkPackage;

import java.util.UUID;
import java.util.concurrent.*;

/**
 * Manages asynchronus network requests.
 * Tracks requests with unique identifiers and awaits (non-blocking)
 * for confirmation from server
 */
public class RequestManager {
    private final ConcurrentHashMap<UUID, CompletableFuture<NetworkPackage>> pendingRequests = new ConcurrentHashMap<>();

    public CompletableFuture<NetworkPackage> registerRequest(UUID id) {
        CompletableFuture<NetworkPackage> future = new CompletableFuture<>();
        pendingRequests.put(id, future);
        return future;
    }

    /**
     * Checks for confirmation and removes it from the map if
     * successful
     * @param id
     * @param response
     * @return true or false depending on success
     */
    public boolean completeRequest(UUID id, NetworkPackage response) {
        if (id == null) return false;

        CompletableFuture<NetworkPackage> future = pendingRequests.remove(id);
        if (future != null) {
            future.complete(response);
            return true;
        }
        return false;
    }
}