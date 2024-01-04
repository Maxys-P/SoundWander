package com.sw.controllers.publicPlaylist;

import com.sw.controllers.Controller;
import com.sw.facades.FacadePlaylist;
import com.sw.facades.FacadePublicPlaylist;

public class ControllerPlaylist extends Controller {
    private FacadePlaylist playlistFacade;

    public ControllerPlaylist() {
        super();
        playlistFacade = FacadePlaylist.getInstance();
    }
}
