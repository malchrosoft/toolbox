/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.malchrosoft.graphics;

import com.malchrosoft.debug.Log;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * Classe de gestion d'un fichier image
 * @author Aymeric Malchrowicz
 */
public class ImageFile 
{
    private String imageFilePath;
     
    /**
     * Construit à objet de fichier image.
     * @param imageFilePath
     */
    public ImageFile(String imageFilePath)
    {
         this.setImageFilePath(imageFilePath);
    }

    /**
     * Retourne le chemin du fichier image.
     * @return le chemin du fichier image
     */
    public String getImageFilePath()
    {
        return imageFilePath;
    }

    /**
     * Défini un nouveau chemin de fichier image et charge l'image 
     * correspondante.
     * @param imageFilePath
     */
    public void setImageFilePath(String imageFilePath)
    {
        this.imageFilePath = imageFilePath;
    }

    /**
     * Retourne l'image récupérer depuis le chemin ou null si erreur.
     * @return l'image
     */
    public Image getImage()
    {
        try
        {
            return ImageFile.buildImageFromPath(imageFilePath);
        } catch (IOException ex)
        {
            Log.error("Erreur de chargement d'image : " + new File(imageFilePath).getAbsolutePath());
            Logger.getLogger(ImageFile.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    /**
     * Retourne l'image récupérer depuis le chemin.
     * @return l'image
     * @throws IOException
     */
    public Image getBuiltImage() throws IOException
    {
        return ImageFile.buildImageFromPath(imageFilePath);
    }
    
    /**
     * Retourne un clone de l'image du fichier image.
     * @return un clone de l'image du fichier image
     */
//    public Image getImageClone()
//    {
//        try
//        {
//            return ImageFile.buildImageFromPath(this.getImageFilePath());
//        } catch (IOException ex)
//        {
//            ex.printStackTrace();
//        }
//        return null;
//    }
    
    public void reloadImage()
    {
        this.setImageFilePath(this.getImageFilePath());
    }
    
    /** 
     * Renvoie une image depuis son chemin dans le projet
     * @param imageClassPath le chemin de l'image dans le projet
     * @return l'image
     */
    public static Image buildImageFromPath(String imageClassPath) throws IOException
    {
        return ImageIO.read(new File(imageClassPath));
    }
}
