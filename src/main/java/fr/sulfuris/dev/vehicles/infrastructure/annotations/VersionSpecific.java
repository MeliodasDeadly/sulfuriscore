package fr.sulfuris.dev.vehicles.infrastructure.annotations;

/**
 * Warns that such an object/method uses a {@link fr.sulfuris.dev.vehicles.infrastructure.enums.ServerVersion} getter.
 * <em>Usually, it must be edited when adding compatibility with a newer version (usually due to the usage of NMS).</em>
 *
 * @since 2.4.3
 */
public @interface VersionSpecific {
}
