package com.caobugs.gis.util.updateapp;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2016/7/4
 */
public class ApiClient
{
    public static Update checkVersion(AppContext appContext) throws Exception {
        try {
            return Update.parse(HtmlRegexpUtil
                    .GetInputStreamByUrl("http://files.cnblogs.com/luomingui/MobileAppVersion.xml"));
        } catch (Exception e) {
            throw e;
        }
    }
}
