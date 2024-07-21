package es.chiteroman.playintegrityfix;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import org.json.JSONObject;
import org.lsposed.hiddenapibypass.HiddenApiBypass;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.KeyStore;
import java.security.KeyStoreSpi;
import java.security.Provider;
import java.security.Security;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class EntryPoint {
    public static final String TAG = "PIF";
    private static final Map<Field, String> map = new HashMap<>();
    private static final String SIGNATURE_DATA = "MIIFyTCCA7GgAwIBAgIVALyxxl+zDS9SL68SzOr48309eAZyMA0GCSqGSIb3DQEBCwUAMHQxCzAJBgNVBAYTAlVTMRMwEQYDVQQIEwpDYWxpZm9ybmlhMRYwFAYDVQQHEw1Nb3VudGFpbiBWaWV3MRQwEgYDVQQKEwtHb29nbGUgSW5jLjEQMA4GA1UECxMHQW5kcm9pZDEQMA4GA1UEAxMHQW5kcm9pZDAgFw0yMjExMDExODExMzVaGA8yMDUyMTEwMTE4MTEzNVowdDELMAkGA1UEBhMCVVMxEzARBgNVBAgTCkNhbGlmb3JuaWExFjAUBgNVBAcTDU1vdW50YWluIFZpZXcxFDASBgNVBAoTC0dvb2dsZSBJbmMuMRAwDgYDVQQLEwdBbmRyb2lkMRAwDgYDVQQDEwdBbmRyb2lkMIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAsqtalIy/nctKlrhd1UVoDffFGnDf9GLi0QQhsVoJkfF16vDDydZJOycG7/kQziRZhFdcoMrIYZzzw0ppBjsSe1AiWMuKXwTBaEtxN99S1xsJiW4/QMI6N6kMunydWRMsbJ6aAxi1llVq0bxSwr8Sg/8u9HGVivfdG8OpUM+qjuV5gey5xttNLK3BZDrAlco8RkJZryAD40flmJZrWXJmcr2HhJJUnqG4Z3MSziEgW1u1JnnY3f/BFdgYsA54SgdUGdQP3aqzSjIpGK01/vjrXvifHazSANjvl0AUE5i6AarMw2biEKB2ySUDp8idC5w12GpqDrhZ/QkW8yBSa87KbkMYXuRA2Gq1fYbQx3YJraw0UgZ4M3fFKpt6raxxM5j0sWHlULD7dAZMERvNESVrKG3tQ7B39WAD8QLGYc45DFEGOhKv5Fv8510h5sXK502IvGpI4FDwz2rbtAgJ0j+16db5wCSW5ThvNPhCheyciajc8dU1B5tJzZN/ksBpzne4Xf9gOLZ9ZU0+3Z5gHVvTS/YpxBFwiFpmL7dvGxew0cXGSsG5UTBlgr7i0SX0WhY4Djjo8IfPwrvvA0QaCFamdYXKqBsSHgEyXS9zgGIFPt2jWdhaS+sAa//5SXcWro0OdiKPuwEzLgj759ke1sHRnvO735dYn5whVbzlGyLBh3L0CAwEAAaNQME4wDAYDVR0TBAUwAwEB/zAdBgNVHQ4EFgQUU1eXQ7NoYKjvOQlh5V8jHQMoxA8wHwYDVR0jBBgwFoAUU1eXQ7NoYKjvOQlh5V8jHQMoxA8wDQYJKoZIhvcNAQELBQADggIBAHFIazRLs3itnZKllPnboSd6sHbzeJURKehx8GJPvIC+xWlwWyFO5+GHmgc3yh/SVd3Xja/k8Ud59WEYTjyJJWTw0Jygx37rHW7VGn2HDuy/x0D+els+S8HeLD1toPFMepjIXJn7nHLhtmzTPlDWDrhiaYsls/k5Izf89xYnI4euuOY2+1gsweJqFGfbznqyqy8xLyzoZ6bvBJtgeY+G3i/9Be14HseSNa4FvI1Oze/l2gUu1IXzN6DGWR/lxEyt+TncJfBGKbjafYrfSh3zsE4N3TU7BeOL5INirOMjre/jVgB1YQG5qLVaPoz6mdn75AbBBm5a5ahApLiKqzy/hP+1rWgw8Ikb7vbUqov/bnY3IlIU6XcPJTCDb9aRZQkStvYpQd82XTyxD/T0GgRLnUj5Uv6iZlikFx1KNj0YNS2T3gyvL++J9B0Y6gAkiG0EtNplz7Pomsv5pVdmHVdKMjqWw5/6zYzVmu5cXFtR384Ti1qwML1xkD6TC3VIv88rKIEjrkY2c+v1frh9fRJ2OmzXmML9NgHTjEiJR2Ib2iNrMKxkuTIs9oxKZgrJtJKvdU9qJJKM5PnZuNuHhGs6A/9gt9OccetYeQvVSqeEmQluWfcunQn9C9Vwi2BJIiVJh4IdWZf5/e2PlSSQ9CJjz2bKI17pzdxOmjQfE0JSF7Xt";

    static {
        try {
            KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
            Field keyStoreSpi = keyStore.getClass().getDeclaredField("keyStoreSpi");

            keyStoreSpi.setAccessible(true);

            CustomKeyStoreSpi.keyStoreSpi = (KeyStoreSpi) keyStoreSpi.get(keyStore);

        } catch (Throwable t) {
            Log.e(TAG, "Couldn't get keyStoreSpi field!", t);
        }

        Provider provider = Security.getProvider("AndroidKeyStore");

        Provider customProvider = new CustomProvider(provider);

        Security.removeProvider("AndroidKeyStore");
        Security.insertProviderAt(customProvider, 1);

        Signature spoofedSignature = new Signature(Base64.decode(SIGNATURE_DATA, Base64.DEFAULT));
        Parcelable.Creator<PackageInfo> originalCreator = PackageInfo.CREATOR;
        Parcelable.Creator<PackageInfo> customCreator = new CustomPackageInfoCreator(originalCreator, spoofedSignature);

        try {
            Field creatorField = findField(PackageInfo.class, "CREATOR");
            creatorField.setAccessible(true);
            creatorField.set(null, customCreator);
        } catch (Throwable t) {
            Log.e(TAG, "Couldn't replace PackageInfoCreator", t);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            HiddenApiBypass.addHiddenApiExemptions("Landroid/os/Parcel;", "Landroid/content/pm", "Landroid/app");
        }

        try {
            Field cacheField = findField(PackageManager.class, "sPackageInfoCache");
            cacheField.setAccessible(true);
            Object cache = cacheField.get(null);
            Method clearMethod = Objects.requireNonNull(cache).getClass().getMethod("clear");
            clearMethod.invoke(cache);
        } catch (Throwable t) {
            Log.e(TAG, "Couldn't clear PackageInfoCache", t);
        }

        try {
            Field creatorsField = findField(Parcel.class, "mCreators");
            creatorsField.setAccessible(true);
            Map<?, ?> mCreators = (Map<?, ?>) creatorsField.get(null);
            Objects.requireNonNull(mCreators).clear();
        } catch (Throwable t) {
            Log.e(TAG, "Couldn't clear Parcel mCreators", t);
        }

        try {
            Field creatorsField = findField(Parcel.class, "sPairedCreators");
            creatorsField.setAccessible(true);
            Map<?, ?> sPairedCreators = (Map<?, ?>) creatorsField.get(null);
            Objects.requireNonNull(sPairedCreators).clear();
        } catch (Throwable t) {
            Log.e(TAG, "Couldn't clear Parcel sPairedCreators", t);
        }
    }

    private static Field findField(Class<?> currentClass, String fieldName) {
        while (currentClass != null && !currentClass.equals(Object.class)) {
            try {
                return currentClass.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                currentClass = currentClass.getSuperclass();
            }
        }
        return null;
    }

    public static void init(String json) {

        if (TextUtils.isEmpty(json)) {
            Log.e(TAG, "JSON is empty!");
        } else {
            try {
                JSONObject jsonObject = new JSONObject(json);

                jsonObject.keys().forEachRemaining(s -> {
                    try {
                        String value = jsonObject.getString(s);

                        if (TextUtils.isEmpty(value)) return;

                        Field field = getFieldByName(s);

                        if (field == null) return;

                        map.put(field, value);

                    } catch (Throwable t) {
                        Log.e(TAG, "Error parsing JSON", t);
                    }
                });
            } catch (Throwable t) {
                Log.e(TAG, "Error parsing JSON", t);
            }
        }

        Log.i(TAG, "Fields ready to spoof: " + map.size());

        spoofFields();
    }

    static void spoofFields() {
        map.forEach((field, s) -> {
            try {
                if (s.equals(field.get(null))) return;
                field.setAccessible(true);
                String oldValue = String.valueOf(field.get(null));
                field.set(null, s);
                Log.d(TAG, String.format("""
                        ---------------------------------------
                        [%s]
                        OLD: '%s'
                        NEW: '%s'
                        ---------------------------------------
                        """, field.getName(), oldValue, field.get(null)));
            } catch (Throwable t) {
                Log.e(TAG, "Error modifying field", t);
            }
        });
    }

    private static Field getFieldByName(String name) {
        Field field;
        try {
            field = Build.class.getDeclaredField(name);
        } catch (NoSuchFieldException e) {
            try {
                field = Build.VERSION.class.getDeclaredField(name);
            } catch (NoSuchFieldException ex) {
                return null;
            }
        }
        field.setAccessible(true);
        return field;
    }
}
