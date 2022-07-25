package fr.sulfuris.dev.vehicles.infrastructure.annotations;

/**
 * Warns that such an object/method is not finished yet and/or further usage is planned.
 */
public @interface ToDo {
    /**
     * Further information about the object/method and why it is marked as 'ToDo'.
     */
    String value();
}
