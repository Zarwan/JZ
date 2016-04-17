package com.jzprojectz.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;

public class JZ extends ApplicationAdapter implements InputProcessor {
    public static final float UNIT = 32;
    public static final int NEUTRAL = 0;
    public static final int LEFT = 1;
    public static final int RIGHT = 2;
    public static final int UP = 3;
    public static final int DOWN = 4;
    private static final float X_ARROW_WIDTH = 150/UNIT;
    private static final float X_ARROW_HEIGHT = 113/UNIT;
    private static final float Y_ARROW_WIDTH = 113/UNIT;
    private static final float Y_ARROW_HEIGHT = 150/UNIT;
    private static final float SCREEN_WIDTH = 30;
    private static final float SCREEN_HEIGHT = 20;
    private TiledMap tiledMap;
    private OrthographicCamera mapCamera;
    private TiledMapRenderer tiledMapRenderer;
    private SpriteBatch spriteBatch;
    private SpriteBatch playerSpriteBatch;
    private OrthographicCamera staticCamera;
    private Vector3 touchPoint;
    private Button leftArrowKey;
    private Button rightArrowKey;
    private Button upArrowKey;
    private Button downArrowKey;
    private Player player;
    private int mapWidth;
    private int mapHeight;
    private int direction = NEUTRAL;

    @Override
    public void create() {

        player = new Player(this);

        spriteBatch = new SpriteBatch();
        playerSpriteBatch = new SpriteBatch();
        touchPoint = new Vector3();

        tiledMap = new TmxMapLoader().load("water.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 1/UNIT);

        MapProperties properties = tiledMap.getProperties();
        mapWidth = properties.get("width", Integer.class);
        mapHeight = properties.get("height", Integer.class);

        mapCamera = new OrthographicCamera();
        mapCamera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);
        mapCamera.update();

        staticCamera = new OrthographicCamera();
        staticCamera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);
        staticCamera.update();

        leftArrowKey = new Button(SCREEN_WIDTH - X_ARROW_WIDTH*2, Y_ARROW_HEIGHT, X_ARROW_WIDTH, X_ARROW_HEIGHT, "left_arrow.png");
        rightArrowKey = new Button(SCREEN_WIDTH - X_ARROW_WIDTH, Y_ARROW_HEIGHT, X_ARROW_WIDTH, X_ARROW_HEIGHT, "right_arrow.png");
        upArrowKey = new Button(SCREEN_WIDTH - X_ARROW_WIDTH - Y_ARROW_WIDTH/2, Y_ARROW_HEIGHT + X_ARROW_HEIGHT, Y_ARROW_WIDTH, Y_ARROW_HEIGHT, "up_arrow.png");
        downArrowKey = new Button(SCREEN_WIDTH - X_ARROW_WIDTH - Y_ARROW_WIDTH/2, 0, Y_ARROW_WIDTH, Y_ARROW_HEIGHT, "down_arrow.png");

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 1, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        mapCamera.update();
        tiledMapRenderer.setView(mapCamera);
        tiledMapRenderer.render();

        spriteBatch.setProjectionMatrix(staticCamera.combined);
        playerSpriteBatch.setProjectionMatrix(mapCamera.combined);

        playerSpriteBatch.begin();
        playerSpriteBatch.draw(player.getTexture(), player.getX(), player.getY(), player.getWidth(), player.getHeight());
        playerSpriteBatch.end();

        spriteBatch.begin();
        spriteBatch.draw(leftArrowKey.getTexture(), leftArrowKey.getXBound(), leftArrowKey.getYBound(), leftArrowKey.getWidth(), leftArrowKey.getHeight());
        spriteBatch.draw(rightArrowKey.getTexture(), rightArrowKey.getXBound(), rightArrowKey.getYBound(), rightArrowKey.getWidth(), rightArrowKey.getHeight());
        spriteBatch.draw(upArrowKey.getTexture(), upArrowKey.getXBound(), upArrowKey.getYBound(), upArrowKey.getWidth(), upArrowKey.getHeight());
        spriteBatch.draw(downArrowKey.getTexture(), downArrowKey.getXBound(), downArrowKey.getYBound(), downArrowKey.getWidth(), downArrowKey.getHeight());
        spriteBatch.end();

        switch (direction) {
            case NEUTRAL:
                break;

            case LEFT:
                if (!sideOfMapVisible(direction) && player.getX() + 1 == mapCamera.position.x) {
                    mapCamera.position.x -= 1;
                }
                player.move(direction);
                mapCamera.update();
                break;

            case RIGHT:
                if (!sideOfMapVisible(direction) && player.getX() + 1 == mapCamera.position.x) {
                    mapCamera.position.x += 1;
                }
                player.move(direction);
                mapCamera.update();
                break;

            case UP:
                if (!sideOfMapVisible(direction) && player.getY() + 1 == mapCamera.position.y) {
                    mapCamera.position.y += 1;
                }
                player.move(direction);
                mapCamera.update();
                break;

            case DOWN:
                if (!sideOfMapVisible(direction) && player.getY() + 1 == mapCamera.position.y) {
                    mapCamera.position.y -= 1;
                }
                player.move(direction);
                mapCamera.update();
                break;
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        staticCamera.unproject(touchPoint.set(screenX, screenY, 0));

        if (leftArrowKey.clicked(touchPoint.x, touchPoint.y)) {
            direction = LEFT;
        } else if (rightArrowKey.clicked(touchPoint.x, touchPoint.y)) {
            direction = RIGHT;
        } else if (upArrowKey.clicked(touchPoint.x, touchPoint.y)) {
            direction = UP;
        } else if (downArrowKey.clicked(touchPoint.x, touchPoint.y)) {
            direction = DOWN;
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        staticCamera.unproject(touchPoint.set(screenX, screenY, 0));

        if (leftArrowKey.clicked(touchPoint.x, touchPoint.y) ||
                rightArrowKey.clicked(touchPoint.x, touchPoint.y) ||
                upArrowKey.clicked(touchPoint.x, touchPoint.y) ||
                downArrowKey.clicked(touchPoint.x, touchPoint.y)) {

            direction = NEUTRAL;
        }
        return false;
    }

    public boolean sideOfMapVisible(int direction) {
        switch (direction) {
            case LEFT:
                return mapCamera.position.x - SCREEN_WIDTH/2 <= 0;
            case RIGHT:
                return mapCamera.position.x + SCREEN_WIDTH/2 >= mapWidth;
            case UP:
                return mapCamera.position.y + SCREEN_HEIGHT/2 >= mapHeight;
            case DOWN:
                return mapCamera.position.y - SCREEN_HEIGHT/2 <= 0;
        }
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    public float getMapWidth() {
        return mapWidth;
    }

    public float getMapHeight() {
        return mapHeight;
    }
}
