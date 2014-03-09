package com.fincatto.osm.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fincatto.osm.classes.Point;
import com.fincatto.osm.classes.PointType;

import java.util.ArrayList;
import java.util.List;

public class DBPoint {

    private final DBHelper dbHelper;

    public DBPoint(final Context context) {
        this.dbHelper = new DBHelper(context);
    }

    private static Point parse(Cursor cursor) {
        Point point = new Point();
        point.setId(cursor.getLong(0));
        point.setLatitude(cursor.getDouble(1));
        point.setLongitude(cursor.getDouble(2));
        point.setType(PointType.valueOfCodigo(cursor.getInt(3)));
        point.setSpeed(cursor.getInt(4));
        return point;
    }

    public List<Point> findAll() {
        try (SQLiteDatabase database = dbHelper.getWritableDatabase()) {
            try (Cursor cursor = database.rawQuery("SELECT * FROM points ORDER BY id", null)) {
                final List<Point> points = new ArrayList<>();
                if (cursor.moveToFirst()) {
                    do {
                        points.add(parse(cursor));
                    } while (cursor.moveToNext());
                }
                return points;
            }
        }
    }


    public Point insert(Point point) {
        final ContentValues values = new ContentValues();
        values.put("latitude", point.getLatitude());
        values.put("longitude", point.getLongitude());
        values.put("type", point.getType().getCode());
        values.put("speed", point.getSpeed());

        try (SQLiteDatabase database = dbHelper.getWritableDatabase()) {
            final long insertId = database.insert("points", null, values);
            try (Cursor cursor = database.rawQuery("SELECT * FROM points WHERE id=?", new String[]{String.valueOf(insertId)})) {
                return cursor.moveToFirst() ? parse(cursor) : null;
            }
        }
    }
}
