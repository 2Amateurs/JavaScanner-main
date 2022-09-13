package com.vault6936.javascanner.util;

import javafx.scene.Node;
import javafx.scene.Scene;

public class FXHelper<T extends Node> {
    private final Scene scene;
    public FXHelper(Scene scene){
        this.scene = scene;
    }
    public T getNode(String id) {
        return (T) scene.lookup(id);
    }
}
