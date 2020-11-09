package fish.payara.sample.debugging.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Application;

import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;

import fish.payara.sample.debugging.model.ServiceModel;
import fish.payara.sample.debugging.service.exception.BeanValidationExceptionMapper;
import fish.payara.sample.debugging.service.exception.IllegalArgumentExceptionMapper;
import fish.payara.sample.debugging.service.exception.WebApplicationExceptionMapper;

public abstract class ClientTest extends JerseyTest {

    protected Class<?>[] getClasses() {
        final Class<?>[] classes = {
            BeanValidationExceptionMapper.class,
            WebApplicationExceptionMapper.class,
            IllegalArgumentExceptionMapper.class,
            BookEndpoints.class,
            LibraryEndpoints.class,
        };
        return classes;
    }

    protected <T> T restClient(Class<T> apiClass, Class<? extends T> implClass) {

        String path = "";
        if (implClass.isAnnotationPresent(Path.class)) {
            path = implClass.getAnnotation(Path.class).value();
        }

        return RestClientBuilder.newBuilder()
                .baseUri(getBaseUri().resolve(path))
                .register(new LoggingFeature())
                .build(apiClass);
    }

    @Override
    protected Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        return new ResourceConfig(getClasses()).register(new AbstractBinder(){
            @Override
            protected void configure() {
                bind(new ServiceModel()).to(ServiceModel.class);
            }
        });
    }

    protected void assertFailStatus(int expectedStatus, Runnable task) {
        try {
            task.run();
            fail("API call shouln't succeed");
        } catch (WebApplicationException ex) {
            final int status = ex.getResponse().getStatus();
            assertEquals("Incorrect status code returned", expectedStatus, status);
        }
    }
    
}
