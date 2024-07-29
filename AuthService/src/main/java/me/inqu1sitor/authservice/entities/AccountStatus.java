package me.inqu1sitor.authservice.entities;

/**
 * An enumeration of an {@link AccountEntity account} statuses.
 *
 * @author Alexander Sankevich
 */
public enum AccountStatus {

    ACTIVE, DELETED, BLOCKED;

    @Override
    public String toString() {
        return name();
    }
}
