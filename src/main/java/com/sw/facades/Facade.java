package com.sw.facades;

import com.sw.classes.User;

/**
 * Facade générique
 */
public abstract class Facade {
    public static User currentUser;
    //On stocke le User lors de la connexion pour pouvoir l'utiliser dans les autres facades
}
