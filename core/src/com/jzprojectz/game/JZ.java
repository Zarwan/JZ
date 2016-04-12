package com.jzprojectz.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class JZ extends ApplicationAdapter implements GestureDetector.GestureListener {
    public static final float UNIT = 32;
    private static final float X_ARROW_WIDTH = 150/UNIT;
    private static final float X_ARROW_HEIGHT = 113/UNIT;
    private static final float Y_ARROW_WIDTH = 113/UNIT;
    private static final float Y_ARROW_HEIGHT = 150/UNIT;
    private float width;
    private float height;
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

    @Override
    public void create() {

        player = new Player(UNIT);

        spriteBatch = new SpriteBatch();
        touchPoint = new Vector3();

        tiledMap = new TmxMapLoader().load("water.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 1/UNIT);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 30, 20);
        camera.update();

        width = camera.viewportWidth;
        height = camera.viewportHeight;

        guicam = new OrthographicCamera();
        guicam.setToOrtho(false, 30, 20);
        guicam.update();


        leftArrowKey = new Button(width - X_ARROW_WIDTH*2, Y_ARROW_HEIGHT, X_ARROW_WIDTH, X_ARROW_HEIGHT, "left_arrow.png");
        rightArrowKey = new Button(width - X_ARROW_WIDTH, Y_ARROW_HEIGHT, X_ARROW_WIDTH, X_ARROW_HEIGHT, "right_arrow.png");
        upArrowKey = new Button(width - X_ARROW_WIDTH - Y_ARROW_WIDTH/2, Y_ARROW_HEIGHT + X_ARROW_HEIGHT, Y_ARROW_WIDTH, Y_ARROW_HEIGHT, "up_arrow.png");
        downArrowKey = new Button(width - X_ARROW_WIDTH - Y_ARROW_WIDTH/2, 0, Y_ARROW_WIDTH, Y_ARROW_HEIGHT, "down_arrow.png");


        GestureDetector gestureDetector = new GestureDetector(this);
        Gdx.input.setInputProcessor(gestureDetector);
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
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        guicam.unproject(touchPoint.set(x, y, 0));

        if (leftArrowKey.clicked(touchPoint.x, touchPoint.y) && notOnLeftBorder()) {
            player.moveLeft();
            camera.position.x -= 1;
            camera.update();
        } else if (rightArrowKey.clicked(touchPoint.x, touchPoint.y)) {
            player.moveRight();
            camera.position.x += 1;
            camera.update();
        } else if (upArrowKey.clicked(touchPoint.x, touchPoint.y)) {
            player.moveUp();
            camera.position.y += 1;
            camera.update();
        } else if (downArrowKey.clicked(touchPoint.x, touchPoint.y) && notOnBottomBorder()) {
            player.moveDown();
            camera.position.y -= 1;
            camera.update();
        }
        return false;
    }

    private boolean notOnLeftBorder() {
        return (camera.position.x - width /2) > 0;
    }

    private boolean notOnBottomBorder() {
        return (camera.position.y - height /2) > 0;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }
}
