package com.droid.treasures.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SPUtils {
    /**
     * 保存在手机里面的文件名
     */
    public static final String FILE_NAME = "im_share_data";
    /**
     * 不清除的文件
     */
    public static final String NO_CLEAR_FILE = "no_clear_file_share_data";

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     * Append methods of putting Double values by chen.
     *
     * @param context
     * @param key
     * @param object
     */
    protected static void put(Context context, String key, Object object) {
        put(context, FILE_NAME, key, object);
    }

    protected static void put(Context context, String fileName, String key, Object object) {
        if (object == null)
            return;
        SharedPreferences sp = context.getSharedPreferences(fileName,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else if (object instanceof Double) {
            editor.putLong(key, Double.doubleToRawLongBits((Double) object));
        }else if (object instanceof HashSet) {
            editor.putStringSet(key, (HashSet<String>) object);
        } else {
            editor.putString(key, object.toString());
        }
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     * Append methods of getting Double values by chen.
     *
     * @param context
     * @param key
     * @param defaultObject
     * @return
     */
    protected static Object get(Context context, String fileName, String key, Object defaultObject) {
        SharedPreferences sp = context.getSharedPreferences(fileName,
                Context.MODE_PRIVATE);

        if (defaultObject instanceof String) {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sp.getLong(key, (Long) defaultObject);
        } else if (defaultObject instanceof Double) {
            return Double.longBitsToDouble(sp.getLong(key,
                    Double.doubleToRawLongBits((Double) defaultObject)));
        } else if (defaultObject instanceof HashSet){
            return   sp.getStringSet(key, (Set<String>) defaultObject);
        }
        return null;
    }

    protected static Object get(Context context, String key, Object defaultObject) {
        return get(context, FILE_NAME, key, defaultObject);
    }


    /**
     * 移除某个key值已经对应的值
     *
     * @param context
     * @param key
     */
    protected static void remove(Context context, String fileName, String key) {
        SharedPreferences sp = context.getSharedPreferences(fileName,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        SharedPreferencesCompat.apply(editor);
    }

    protected static void remove(Context context, String key) {
        remove(context, FILE_NAME, key);
    }

    /**
     * 清除所有数据
     *
     * @param context
     */
    protected static void clear(Context context, String fileName) {
        SharedPreferences sp = context.getSharedPreferences(fileName,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        SharedPreferencesCompat.apply(editor);
    }

    protected static void clear(Context context) {
        clear(context, FILE_NAME);
    }

    /**
     * 查询某个key是否已经存在
     *
     * @param context
     * @param key
     * @return
     */
    protected static boolean contains(Context context, String fileName, String key) {
        SharedPreferences sp = context.getSharedPreferences(fileName,
                Context.MODE_PRIVATE);
        return sp.contains(key);
    }

    protected static boolean contains(Context context, String key) {
        return contains(context, FILE_NAME, key);
    }

    /**
     * 返回所有的键值对
     *
     * @param context
     * @return
     */
    protected static Map<String, ?> getAll(Context context,String fileName) {
        SharedPreferences sp = context.getSharedPreferences(fileName,
                Context.MODE_PRIVATE);
        return sp.getAll();
    }

    protected static Map<String, ?> getAll(Context context) {
        return getAll(context,FILE_NAME);
    }


        /**
         * 创建一个SharedPreferencesCompat解决apply方法的一个兼容类
         *
         * @author zhy
         */
        protected static class SharedPreferencesCompat {
        private static final Method sApplyMethod = findApplyMethod();

        /**
         * 反射查找apply的方法
         *
         * @return Method
         */
        protected static Method findApplyMethod() {
            try {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {
            }
            return null;
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         *
         * @param editor
         */
        protected static void apply(SharedPreferences.Editor editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (IllegalArgumentException e) {
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            }
            editor.commit();
        }
    }
}