package hevs.labo.projetandroid.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Nullable;
import javax.inject.Named;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * WARNING: This generated code is intended as a sample or starting point for using a
 * Google Cloud Endpoints RESTful API with an Objectify entity. It provides no data access
 * restrictions and no data validation.
 * <p>
 * DO NOT deploy this code unchanged as part of a real application to real users.
 */
@Api(
        name = "artworkApi",
        version = "v1",
        resource = "artwork",
        namespace = @ApiNamespace(
                ownerDomain = "backend.projetandroid.labo.hevs",
                ownerName = "backend.projetandroid.labo.hevs",
                packagePath = ""
        )
)
public class ArtworkEndpoint {

    private static final Logger logger = Logger.getLogger(ArtworkEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(Artwork.class);
    }

    /**
     * Returns the {@link Artwork} with the corresponding ID.
     *
     * @param id the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code Artwork} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "artwork/{id}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public Artwork get(@Named("id") Long id) throws NotFoundException {
        logger.info("Getting Artwork with ID: " + id);
        Artwork artwork = ofy().load().type(Artwork.class).id(id).now();
        if (artwork == null) {
            throw new NotFoundException("Could not find Artwork with ID: " + id);
        }
        return artwork;
    }

    /**
     * Inserts a new {@code Artwork}.
     */
    @ApiMethod(
            name = "insert",
            path = "artwork",
            httpMethod = ApiMethod.HttpMethod.POST)
    public Artwork insert(Artwork artwork) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that artwork.id has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        ofy().save().entity(artwork).now();
        logger.info("Created Artwork with ID: " + artwork.getId());

        return ofy().load().entity(artwork).now();
    }

    /**
     * Updates an existing {@code Artwork}.
     *
     * @param id      the ID of the entity to be updated
     * @param artwork the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code Artwork}
     */
    @ApiMethod(
            name = "update",
            path = "artwork/{id}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public Artwork update(@Named("id") Long id, Artwork artwork) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(id);
        ofy().save().entity(artwork).now();
        logger.info("Updated Artwork: " + artwork);
        return ofy().load().entity(artwork).now();
    }

    /**
     * Deletes the specified {@code Artwork}.
     *
     * @param id the ID of the entity to delete
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code Artwork}
     */
    @ApiMethod(
            name = "remove",
            path = "artwork/{id}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("id") Long id) throws NotFoundException {
        checkExists(id);
        ofy().delete().type(Artwork.class).id(id).now();
        logger.info("Deleted Artwork with ID: " + id);
    }

    /**
     * List all entities.
     *
     * @param cursor used for pagination to determine which page to return
     * @param limit  the maximum number of entries to return
     * @return a response that encapsulates the result list and the next page token/cursor
     */
    @ApiMethod(
            name = "list",
            path = "artwork",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<Artwork> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<Artwork> query = ofy().load().type(Artwork.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<Artwork> queryIterator = query.iterator();
        List<Artwork> artworkList = new ArrayList<Artwork>(limit);
        while (queryIterator.hasNext()) {
            artworkList.add(queryIterator.next());
        }
        return CollectionResponse.<Artwork>builder().setItems(artworkList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(Long id) throws NotFoundException {
        try {
            ofy().load().type(Artwork.class).id(id).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find Artwork with ID: " + id);
        }
    }
}