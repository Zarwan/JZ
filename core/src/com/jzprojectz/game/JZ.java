package com.jzprojectz.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;
import java.util.List;

public class JZ extends ApplicationAdapter implements InputProcessor {
    public static final float UNIT = 32;
    public static final int NEUTRAL = 0;
    public static final int LEFT = 1;
    public static final int RIGHT = 2;
    public static final int UP = 3;
    public static final int DOWN = 4;
    private static final float ARROW_WIDTH = 3;
    private static final float ARROW_HEIGHT = 3;
    private static final float SHOOT_BUTTON_RADIUS = 3;
    private static final float SCREEN_WIDTH = 30;
    private static final float SCREEN_HEIGHT = 20;
    private TiledMap tiledMap;
    private OrthographicCamera mapCamera;
    private TiledMapRenderer tiledMapRenderer;
    private SpriteBatch staticSpriteBatch;
    private SpriteBatch dynamicSpriteBatch;
    private ShapeRenderer shapeRenderer;
    private OrthographicCamera staticCamera;
    private Vector3 touchPoint;
    private Button leftArrowKey;
    private Button rightArrowKey;
    private Button upArrowKey;
    private Button downArrowKey;
    private Button shootButton;
    private Player player;
    private int mapWidth;
    private int mapHeight;
    private int direction = NEUTRAL;
    private int directionFacing = RIGHT;
    private int moveCount = 0;
    private List<Bullet> bullets = new ArrayList<Bullet>();
    private List<Enemy> enemies = new ArrayList<Enemy>();

    @Override
    public void create() {

        player = new Player(this);
        enemies.add(new Squidward(this));

        staticSpriteBatch = new SpriteBatch();
        dynamicSpriteBatch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        touchPoint = new Vector3();

        tiledMap = new TmxMapLoader().load("map.tmx");
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

        leftArrowKey = new Button(SCREEN_WIDTH - ARROW_WIDTH*3, ARROW_HEIGHT, ARROW_WIDTH, ARROW_HEIGHT, "left_arrow.png");
        rightArrowKey = new Button(SCREEN_WIDTH - ARROW_WIDTH, ARROW_HEIGHT, ARROW_WIDTH, ARROW_HEIGHT, "right_arrow.png");
        upArrowKey = new Button(SCREEN_WIDTH - ARROW_WIDTH*2, ARROW_HEIGHT + ARROW_HEIGHT, ARROW_WIDTH, ARROW_HEIGHT, "up_arrow.png");
        downArrowKey = new Button(SCREEN_WIDTH - ARROW_WIDTH*2, 0, ARROW_WIDTH, ARROW_HEIGHT, "down_arrow.png");
        shootButton = new Button(SHOOT_BUTTON_RADIUS, SHOOT_BUTTON_RADIUS, SHOOT_BUTTON_RADIUS, SHOOT_BUTTON_RADIUS, "shooting_button.png");

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (player.isDead()) {
            return;
        }

        tiledMapRenderer.setView(mapCamera);
        tiledMapRenderer.render();

        staticSpriteBatch.setProjectionMatrix(staticCamera.combined);
        dynamicSpriteBatch.setProjectionMatrix(mapCamera.combined);
        shapeRenderer.setProjectionMatrix(mapCamera.combined);

        dynamicSpriteBatch.begin();
        dynamicSpriteBatch.draw(player.getTexture(), player.getX(), player.getY(), player.getWidth(), player.getHeight());

        for (Enemy enemy : enemies) {
            dynamicSpriteBatch.draw(enemy.getTexture(), enemy.getX(), enemy.getY(), enemy.getWidth(), enemy.getHeight());
        }
        dynamicSpriteBatch.end();

        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);

            if (bullet.isDead()) {
                bullets.remove(i);
                i--;
                continue;
            }

            shapeRenderer.circle(bullet.getX(), bullet.getY(), bullet.getRadius(), 12);

            for (int j = 0; j < enemies.size(); j++) {
                Enemy enemy = enemies.get(j);
                if (bullet.collision(enemy.getX(), enemy.getY(), enemy.getWidth(), enemy.getHeight())) {
                    enemy.shot();

                    if (enemy.isDead()) {
                        enemies.remove(j);
                        j--;
                    }
                    bullets.remove(i);
                    i--;
                }
            }
            if (bullets.contains(bullet)) {
                bullet.moveBullet();
            }
        }
        shapeRenderer.end();

        staticSpriteBatch.begin();
        staticSpriteBatch.draw(leftArrowKey.getTexture(), leftArrowKey.getXBound(), leftArrowKey.getYBound(), leftArrowKey.getWidth(), leftArrowKey.getHeight());
        staticSpriteBatch.draw(rightArrowKey.getTexture(), rightArrowKey.getXBound(), rightArrowKey.getYBound(), rightArrowKey.getWidth(), rightArrowKey.getHeight());
        staticSpriteBatch.draw(upArrowKey.getTexture(), upArrowKey.getXBound(), upArrowKey.getYBound(), upArrowKey.getWidth(), upArrowKey.getHeight());
        staticSpriteBatch.draw(downArrowKey.getTexture(), downArrowKey.getXBound(), downArrowKey.getYBound(), downArrowKey.getWidth(), downArrowKey.getHeight());
        staticSpriteBatch.draw(shootButton.getTexture(), shootButton.getXBound(), shootButton.getYBound(), shootButton.getWidth(), shootButton.getHeight());
        staticSpriteBatch.end();

        for (Enemy enemy : enemies) {
            enemy.followAndAttack(player);
        }

        if (direction != NEUTRAL) {
            directionFacing = direction;
        } else {
            return;
        }

        switch (direction) {
            case LEFT:
                if (!sideOfMapVisible(direction) && player.getX() + 1 <= mapCamera.position.x) {
                    mapCamera.position.x -= player.getDistance();
                }
                player.moveLeft();
                break;

            case RIGHT:
                if (!sideOfMapVisible(direction) && player.getX() + 1 >= mapCamera.position.x) {
                    mapCamera.position.x += player.getDistance();
                }
                player.moveRight();
                break;

            case UP:
                if (!sideOfMapVisible(direction) && player.getY() + 1 >= mapCamera.position.y) {
                    mapCamera.position.y += player.getDistance();
                }
                player.moveUp();
                break;

            case DOWN:
                if (!sideOfMapVisible(direction) && player.getY() + 1 <= mapCamera.position.y) {
                    mapCamera.position.y -= player.getDistance();
                }
                player.moveDown();
                break;
        }
        mapCamera.update();
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
        } else if (shootButton.clicked(touchPoint.x, touchPoint.y)) {
            bullets.add(new Bullet(player.getX(), player.getY(), directionFacing));
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
                return mapCamera.position.x - SCREEN_WIDTH/2 <= player.getDistance();
            case RIGHT:
                return mapCamera.position.x + SCREEN_WIDTH/2 >= mapWidth - player.getDistance();
            case UP:
                return mapCamera.position.y + SCREEN_HEIGHT/2 >= mapHeight - player.getDistance();
            case DOWN:
                return mapCamera.position.y - SCREEN_HEIGHT/2 <= player.getDistance();
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
