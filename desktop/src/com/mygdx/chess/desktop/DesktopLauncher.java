package com.mygdx.chess.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.chess.Chess;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.addIcon("icon.png", Files.FileType.Internal);
		config.width = 800;
		config.height = 600;
		config.backgroundFPS = 15;
		config.foregroundFPS = 144;
		config.resizable = false;
		new LwjglApplication(new Chess(), config);
	}
}
