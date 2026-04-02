package com.rafgittools.webhook

/**
 * Basic in-process webhook handler.
 *
 * This validates trivial payload constraints and routes supported GitHub event
 * types to lightweight handlers, returning [Result.success] when processed.
 */
object WebhookHandler {

    private val supportedEvents = setOf(
        "ping",
        "push",
        "pull_request",
        "issues",
        "release"
    )

    fun handle(eventType: String, payload: String): Result<Unit> {
        val normalizedEvent = eventType.trim().lowercase()
        if (normalizedEvent.isBlank()) {
            return Result.failure(IllegalArgumentException("Webhook event type must not be blank"))
        }

        if (payload.isBlank()) {
            return Result.failure(IllegalArgumentException("Webhook payload must not be blank"))
        }

        if (normalizedEvent !in supportedEvents) {
            return Result.failure(
                UnsupportedOperationException("Unsupported webhook event: $eventType")
            )
        }

        routeEvent(normalizedEvent, payload)
        return Result.success(Unit)
    }

    private fun routeEvent(eventType: String, payload: String) {
        when (eventType) {
            "ping" -> onPing(payload)
            "push" -> onPush(payload)
            "pull_request" -> onPullRequest(payload)
            "issues" -> onIssue(payload)
            "release" -> onRelease(payload)
        }
    }

    private fun onPing(payload: String) {
        logReceipt("ping", payload)
    }

    private fun onPush(payload: String) {
        logReceipt("push", payload)
    }

    private fun onPullRequest(payload: String) {
        logReceipt("pull_request", payload)
    }

    private fun onIssue(payload: String) {
        logReceipt("issues", payload)
    }

    private fun onRelease(payload: String) {
        logReceipt("release", payload)
    }

    private fun logReceipt(eventType: String, payload: String) {
        println("Received webhook event: $eventType, payload size=${payload.length}")
    }
}
