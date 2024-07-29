package me.inquis1tor.userservice.entities;

/**
 * An enumeration of {@link AccountEntity account} role.
 *
 * @author Alexander Sankevich
 */
public enum AccountRole {

    USER, ADMIN, MODER;

    @Override
    public String toString() {
        return name();
    }
}
