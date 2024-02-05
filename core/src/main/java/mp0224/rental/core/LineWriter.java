package mp0224.rental.core;

/**
 * A generic printing interface, implementations of which, that as well as sending data to a conventional output (STDOUT),
 * could also write them to a file, a database, AWS S3, or any other kind of target.
 *
 */
public interface LineWriter {

    void println(String pText);
}
