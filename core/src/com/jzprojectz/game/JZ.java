package com.jzprojectz.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TideMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class JZ extends ApplicationAdapter implements GestureDetector.GestureListener {
    private static final int ARROW_WIDTH = 200;
    private static final int ARROW_HEIGHT = 151;
    private TiledMap tiledMap;
    private OrthographicCamera camera;
    private TiledMapRenderer tiledMapRenderer;
    private Texture texture;
    private SpriteBatch spriteBatch;
    private OrthographicCamera guicam;
    private Rectangle wleftBounds;
    private Rectangle wrightBounds;
    private Vector3 touchPoint;
    private Texture leftTexture;
    private Texture rightTexture;

    @Override
    public void create() {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        spriteBatch = new SpriteBatch();
        touchPoint = new Vector3();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, w, h);
        camera.update();

        tiledMap = new TmxMapLoader().load("water.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

        texture = new Texture(Gdx.files.internal("pikachu.png"));

        guicam = new OrthographicCamera(w, h);
        guicam.position.set(w/2f, h/2f, 0);
        guicam.update();

        wleftBounds = new Rectangle(w - ARROW_WIDTH*2, 0, ARROW_WIDTH, ARROW_HEIGHT);
        wrightBounds = new Rectangle(w - ARROW_WIDTH, 0, ARROW_WIDTH, ARROW_HEIGHT);
        leftTexture = new Texture(Gdx.files.internal("left_arrow.png"));
        rightTexture = new Texture(Gdx.files.internal("right_arrow.png"));

        GestureDetector gestureDetector = new GestureDetector(this);
        Gdx.input.setInputProcessor(gestureDetector);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();

        spriteBatch.begin();
        spriteBatch.draw(texture, 0, 0);
        spriteBatch.draw(leftTexture, wleftBounds.x, wleftBounds.y, wleftBounds.width, wleftBounds.height);
        spriteBatch.draw(rightTexture, wrightBounds.x, wrightBounds.y, wrightBounds.width, wrightBounds.height);
        spriteBatch.end();
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        guicam.unproject(touchPoint.set(x, y, 0));
        if (wleftBounds.contains(touchPoint.x, touchPoint.y)) {
            camera.position.x -= 300;
            camera.update();
        } else if ( wrightBounds.contains(touchPoint.x, touchPoint.y) ) {
            camera.position.x += 300;
            camera.update();
        }
        return false;
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
