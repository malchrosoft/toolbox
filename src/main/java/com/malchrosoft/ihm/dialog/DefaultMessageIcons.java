/*
 * Copyright MalchroSoft - Aymeric MALCHROWICZ. All right reserved.
 * The source code that contains this comment is an intellectual property
 * of MalchroSoft [Aymeric MALCHROWICZ]. Use is subject to licence terms.
 */

package com.malchrosoft.ihm.dialog;

import com.malchrosoft.utils.net.URLTranformer;

/**
 *
 * @author Aymeric Malchrowicz
 */
public class DefaultMessageIcons
{
    private static String relativeCriticalIcon = "criticalIcon.im";
    private static String relativeMessageIcon = "messageIcon_2.im";
    private static String relativeWarningIcon = "warningIcon.im";
    private static String relativeQuestionIcon = "questionIcon.im";
    private static String relativeInfoIcon = "infoIcon.im";

    public static String getCriticalIconPath()
    {
        return buildDecodedPath(relativeCriticalIcon);
    }
    public static String getMessageIconPath()
    {
        return buildDecodedPath(relativeMessageIcon);
    }
    public static String getWarningIconPath()
    {
        return buildDecodedPath(relativeWarningIcon);
    }
    public static String getQuestionIconPath()
    {
        return buildDecodedPath(relativeQuestionIcon);
    }
    public static String getInfoIconPath()
    {
        return buildDecodedPath(relativeInfoIcon);
    }

    private static String buildDecodedPath(String iconFileName)
    {
        return URLTranformer.decodeReservedCharacters(
            "../RESOURCES/ICONS/default/" + iconFileName);
    }   
}
