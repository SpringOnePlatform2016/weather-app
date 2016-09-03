package com.example.weather;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.result.view.AbstractUrlBasedView;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;


public class StaticFileView extends AbstractUrlBasedView {

    public StaticFileView() {

    }

    public StaticFileView(String url) {
        this();

        setUrl(url);
    }

    @Override
    public boolean checkResourceExists(Locale locale) throws Exception {
        return true;
    }

    @Override
    protected Mono<Void> renderInternal(Map<String, Object> renderAttributes, ServerWebExchange exchange) {
        if (logger.isDebugEnabled()) {
            logger.debug("Rendering static resource [" + getUrl() + "].");
        }

        DataBuffer dataBuffer = exchange.getResponse().bufferFactory().allocateBuffer();

        try (BufferedOutputStream writer = new BufferedOutputStream(dataBuffer.asOutputStream())) {
            writer.write(IOUtils.toByteArray(new ClassPathResource(getUrl()).getInputStream()));
        } catch (IOException ex) {
            String message = "Could not load resource for URL [" + getUrl() + "]";
            return Mono.error(new IllegalStateException(message, ex));
        } catch (Throwable ex) {
            return Mono.error(ex);
        }

        return exchange.getResponse().writeWith(Mono.just(dataBuffer)).log("done");
    }
}