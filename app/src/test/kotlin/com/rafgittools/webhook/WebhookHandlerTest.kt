package com.rafgittools.webhook

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class WebhookHandlerTest {

    @Test
    fun handle_returnsFailure_whenEventTypeIsBlank() {
        val result = WebhookHandler.handle("   ", "{}")

        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()).isInstanceOf(IllegalArgumentException::class.java)
    }

    @Test
    fun handle_returnsFailure_whenPayloadIsBlank() {
        val result = WebhookHandler.handle("push", "  ")

        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()).isInstanceOf(IllegalArgumentException::class.java)
    }

    @Test
    fun handle_returnsFailure_whenEventIsUnsupported() {
        val result = WebhookHandler.handle("fork", "{\"ok\":true}")

        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()).isInstanceOf(UnsupportedOperationException::class.java)
    }

    @Test
    fun handle_returnsSuccess_forSupportedEvent() {
        val result = WebhookHandler.handle("pull_request", "{\"action\":\"opened\"}")

        assertThat(result.isSuccess).isTrue()
    }
}
