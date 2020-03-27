package leitner.card.services.integration.containers;

import org.testcontainers.containers.GenericContainer;

public class MongoDbContainer extends GenericContainer<MongoDbContainer> {

    /**
     * This is the internal port on which MongoDB is running inside the container.
     * <p>
     * You can use this constant in case you want to map an explicit public port to it
     * instead of the default random port. This can be done using methods like
     * {@link #setPortBindings(java.util.List)}.
     */
    public static final int DEFAULT_MONGO_PORT = 27017;
    public static final String DEFAULT_IMAGE_AND_TAG = "mongo:latest";

    /**
     * Creates a new {@link MongoDbContainer} with the {@value DEFAULT_IMAGE_AND_TAG} image.
     */
    public MongoDbContainer() {
        this(DEFAULT_IMAGE_AND_TAG);
    }

    /**
     * Creates a new {@link MongoDbContainer} with the given {@code 'image'}.
     *
     * @param image the image (e.g. {@value DEFAULT_IMAGE_AND_TAG}) to use
     */
    public MongoDbContainer(String image) {
        super(image);
    }

    /**
     * Returns the actual public port of the internal MongoDB port ({@value DEFAULT_MONGO_PORT}).
     *
     * @return the public port of this container
     * @see #getMappedPort(int)
     */
    public Integer getPort() {
        return getMappedPort(DEFAULT_MONGO_PORT);
    }
}
