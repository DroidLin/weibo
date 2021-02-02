package com.open.core_base.permission;

import android.app.Activity;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;

import com.open.core_base.coroutine.CoroutineKt;
import com.open.core_base.interfaces.IContext;
import com.open.core_base.service.ServiceFacade;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Permission {

    public static void check(Activity activity, int requestCode) {
        String[] ungranted = permissionCheck(PermissionsObject.INSTANCE.getPermission());
        if (ungranted.length != 0) {
            ActivityCompat.requestPermissions(activity, ungranted, requestCode);
        }
    }


    public static String[] permissionCheck(String[] permissions) {
        List<String> notGrantedPermission = new ArrayList<>();
        IContext iContext = ServiceFacade.getInstance().get(IContext.class);
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(iContext.getContext(), permission) != PackageManager.PERMISSION_GRANTED
                    || PermissionChecker.checkSelfPermission(iContext.getContext(), permission) != PermissionChecker.PERMISSION_GRANTED) {
                notGrantedPermission.add(permission);
            }
        }
        return notGrantedPermission.toArray(new String[]{});
    }

    public static boolean permissionResultDispatch(Activity activity, String[] permission, int[] grantedResult) {
        List<String> retryPermission = new LinkedList<>();
        for (int i = 0; i < permission.length; i++) {
            if (grantedResult[i] == PackageManager.PERMISSION_DENIED) {
                retryPermission.add(permission[i]);
            }
        }
        if (retryPermission.size() == 0) {
            return true;
        } else {
            ActivityCompat.requestPermissions(activity, retryPermission.toArray(new String[]{}), 0x3);
            return false;
        }

    }
}
