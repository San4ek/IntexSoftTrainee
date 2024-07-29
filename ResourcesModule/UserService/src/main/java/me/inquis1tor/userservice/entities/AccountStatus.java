package me.inquis1tor.userservice.entities;

/**
 * An enumeration of {@link AccountEntity account} status.
 *
 * @author Alexander Sankevich
 */
public enum AccountStatus {

    ACTIVE, BLOCKED, DELETED;

    @Override
    public String toString() {
        return name();
    }
}
