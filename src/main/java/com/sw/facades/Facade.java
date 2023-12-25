package com.sw.facades;

import com.sw.classes.User;
import com.sw.classes.Proposal;

/**
 * Facade générique
 */
public abstract class Facade {
    public static User currentUser;
    //On stocke le User lors de la connexion pour pouvoir l'utiliser dans les autres facades

}
