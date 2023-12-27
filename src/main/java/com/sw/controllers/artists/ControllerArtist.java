package com.sw.controllers.artists;

import com.sw.controllers.Controller;
import com.sw.facades.FacadeArtist;

/**
 * Controller générique pour les pages artistes
 */
public abstract class ControllerArtist extends Controller{
    final FacadeArtist facadeArtist = FacadeArtist.getInstance();
}
