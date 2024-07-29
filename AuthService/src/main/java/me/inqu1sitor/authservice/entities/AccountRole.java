package me.inqu1sitor.authservice.entities;

/**
 * An enumeration of an {@link AccountEntity account} roles.
 *
 * @author Alexander Sankevich
 */
public enum AccountRole {

    USER, MODER, ADMIN;

    @Override
    public String toString() {
        return name();
    }
}
