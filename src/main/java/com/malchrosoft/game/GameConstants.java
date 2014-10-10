package com.malchrosoft.game;

import java.io.File;

/*
 * Copyright MalchroSoft - Aymeric MALCHROWICZ. All right reserved.
 * The source code that contains this comment is an intellectual property
 * of MalchroSoft [Aymeric MALCHROWICZ]. Use is subject to licence terms.
 */


/**
 *
 * @author Aymeric Malchrowicz
 */
public class GameConstants
{
	/**
	 * Vitesse de rafraichissement du plateau de jeu en millisecondes.
	 */
	public static final int VITESSE_RAFRAICHISSEMENT = 25; //20

	public static final int GRAVITY = 10;


	/**
	 * DIRECTORIES
	 */
	public static final String SOUND_DIRECTORY = "SOUNDS" + File.separator;
	public static final String MUSIC_DIRECTORY = "MUSICS" + File.separator;
	public static final String IMAGE_DIRECTORY = "IMAGES" + File.separator;
}
