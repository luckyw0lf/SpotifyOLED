package javaio;


import javaio.states.*;

public enum ViewState
{
    STARTING_WEBSERVER(new StartingWebserver()),
    AWAITING_AUTHENTICATION(new AwaitingAuthentication()),
    PLAYBACK(new Playback()),
    CLIMATE_CONTROL,
    MENU(new Menu());

    public State state;
    ViewState(State state){
        this.state = state;
    }

    ViewState(){}

    void show(){
        this.state.show();
    }
}
