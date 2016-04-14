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
    private static final float X_ARROW_WIDTH = 150/UNIT;
    private static final float X_ARROW_HEIGHT = 113/UNIT;
    private static final float Y_ARROW_WIDTH = 113/UNIT;
    private static final float Y_ARROW_HEIGHT = 150/UNIT;
    private static final float SCREEN_WIDTH = 30;
    private static final float SCREEN_HEIGHT = 20;
    private static final int NEUTRAL = 0;
    private static final int LEFT = 1;
    private static final int RIGHT = 2;
    private static final int UP = 3;
    private static final int DOWN = 4;
    private TiledMap tiledMap;
    private OrthographicCamera camera;
    private TiledMapRenderer tiledMapRenderer;
    private SpriteBatch spriteBatch;
    private OrthographicCamera guicam;
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

        player = new Player();

        spriteBatch = new SpriteBatch();
        touchPoint = new Vector3();

        tiledMap = new TmxMapLoader().load("water.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 1/UNIT);

        MapProperties properties = tiledMap.getProperties();
        mapWidth = properties.get("width", Integer.class);
        mapHeight = properties.get("height", Integer.class);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);
        camera.update();

        guicam = new OrthographicCamera();
        guicam.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);
        guicam.update();

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
        camera.update();
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();

        spriteBatch.setProjectionMatrix(guicam.combined);

        spriteBatch.begin();
        spriteBatch.draw(player.getTexture(), player.getX(), player.getY(), player.getWidth(), player.getHeight());
        spriteBatch.draw(leftArrowKey.getTexture(), leftArrowKey.getXBound(), leftArrowKey.getYBound(), leftArrowKey.getWidth(), leftArrowKey.getHeight());
        spriteBatch.draw(rightArrowKey.getTexture(), rightArrowKey.getXBound(), rightArrowKey.getYBound(), rightArrowKey.getWidth(), rightArrowKey.getHeight());
        spriteBatch.draw(upArrowKey.getTexture(), upArrowKey.getXBound(), upArrowKey.getYBound(), upArrowKey.getWidth(), upArrowKey.getHeight());
        spriteBatch.draw(downArrowKey.getTexture(), downArrowKey.getXBound(), downArrowKey.getYBound(), downArrowKey.getWidth(), downArrowKey.getHeight());
        spriteBatch.end();

        switch (direction) {
            case NEUTRAL:
                break;

            case LEFT:
                camera.position.x -= 1;
                player.moveLeft();
                camera.update();
                break;
            case RIGHT:

                camera.position.x += 1;
                player.moveRight();
                camera.update();
                break;

            case UP:
                camera.position.y += 1;
                player.moveUp();
                camera.update();
                break;

            case DOWN:
                camera.position.y -= 1;
                player.moveDown();
                camera.update();
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
        guicam.unproject(touchPoint.set(screenX, screenY, 0));

        if (leftArrowKey.clicked(touchPoint.x, touchPoint.y) && notOnLeftBorder()) {
            direction = LEFT;
        } else if (rightArrowKey.clicked(touchPoint.x, touchPoint.y) && notOnRightBorder()) {
            direction = RIGHT;
        } else if (upArrowKey.clicked(touchPoint.x, touchPoint.y) && notOnTopBorder()) {
            direction = UP;
        } else if (downArrowKey.clicked(touchPoint.x, touchPoint.y) && notOnBottomBorder()) {
            direction = DOWN;
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        guicam.unproject(touchPoint.set(screenX, screenY, 0));

        if (leftArrowKey.clicked(touchPoint.x, touchPoint.y) ||
                rightArrowKey.clicked(touchPoint.x, touchPoint.y) ||
                upArrowKey.clicked(touchPoint.x, touchPoint.y) ||
                downArrowKey.clicked(touchPoint.x, touchPoint.y)) {

            direction = NEUTRAL;
        }
        return false;
    }

    private boolean notOnLeftBorder() {
        return (camera.position.x - SCREEN_WIDTH/2) > 0;
    }

    private boolean notOnRightBorder() {
        return (camera.position.x + SCREEN_WIDTH/2) < mapWidth;
    }

    private boolean notOnBottomBorder() {
        return (camera.position.y - SCREEN_HEIGHT/2) > 0;
    }

    private boolean notOnTopBorder() {
        return (camera.position.y + SCREEN_HEIGHT/2) < mapHeight;
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
}
