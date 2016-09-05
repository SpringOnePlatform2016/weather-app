package com.example.weather;

import io.netty.handler.codec.http.LastHttpContent;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.result.view.AbstractUrlBasedView;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;


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
//        exchange.getResponse().getHeaders().setConnection("keep-alive");
        try (BufferedOutputStream writer = new BufferedOutputStream(dataBuffer.asOutputStream())) {
            ClassPathResource resource = new ClassPathResource(getUrl());

            exchange.getResponse()
                    .getHeaders()
                    .setContentType(parseMediaType(exchange, resource.getFilename()));

            writer.write(IOUtils.toByteArray(resource.getInputStream()));
            writer.flush();
        } catch (IOException ex) {
            String message = "Could not load resource for URL [" + getUrl() + "]";
            return Mono.error(new IllegalStateException(message, ex));
        } catch (Throwable ex) {
            return Mono.error(ex);
        }

        return exchange.getResponse().writeWith(Flux.just(dataBuffer)).log("done");
    }

    private MediaType parseMediaType(ServerWebExchange exchange, String filename) {
        String extension = FilenameUtils.getExtension(getUrl());

        MediaType mediaType = exchange.getRequest().getHeaders().getAccept()
                .stream()
                .filter(mt -> mt.toString().contains(extension))
                .findFirst()
                .map(Optional::of)
                .orElse(Optional.ofNullable(URLConnection.guessContentTypeFromName(filename))
                        .map(MediaType::parseMediaType))
                .orElse(MediaType.TEXT_PLAIN);

//        if(logger.isDebugEnabled()) {
        logger.info(String.format("MediaTyp of %s : %s", filename, mediaType));
//        }

        return mediaType;
    }
}