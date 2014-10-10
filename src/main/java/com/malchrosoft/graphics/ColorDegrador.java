/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.malchrosoft.graphics;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

/**
 *
 * @author Aymeric Malchrowicz
 */
public class ColorDegrador 
{

    private Graphics g;
    private int hauteur;
    private int largeur;
    
    /**
     * Crée un Dégradeur de couleur sur un objet graphique
     * @param g l'objet graphique
     */
    public ColorDegrador(Graphics g, int hauteur, int largeur)
    {
        this.g = g;
        this.hauteur = hauteur;
        this.largeur = largeur;
    }
    
    /**
     * Crée un Dégradeur de couleur sur un composent graphique
     * @param c Le composant
     */
    public ColorDegrador(Component c)
    {
        this(c.getGraphics(), c.getHeight(), c.getWidth());
    }
    
    public Graphics getGraphics()
    {
        return this.g;
    }
    
    /**
     * Crée un dégradé de haut en bas sur toute la surface du 
     * composent graphique
     * @param startColor couleur de départ
     * @param endColor couleur d'arrivée
     */
    public void degradeVertical(Color startColor, Color endColor)
    {
        int vred, vgreen, vblue;
        double vr2 = startColor.getRed() + 0.00;
        double vg2 = startColor.getGreen() + 0.00;
        double vb2 = startColor.getBlue() + 0.00;
        double vr1 = endColor.getRed() + 0.00;
        double vg1 = endColor.getGreen() + 0.00;
        double vb1 = endColor.getBlue() + 0.00;
        
        for (int i = 0; i < this.hauteur; i++)
        {
            vred = this.calculComposanteCouleur(vr1, vr2, i);
            vgreen = this.calculComposanteCouleur(vg1, vg2, i);
            vblue = this.calculComposanteCouleur(vb1, vb2, i);
            this.g.setColor(new Color(vred, vgreen, vblue));
            this.g.drawLine(0, i, this.largeur, i);
        }
    }
    
    /**
     * Crée un dégradé vertical de bas en haut sur toute la surface du 
     * composent graphique
     * @param startColor couleur de départ
     * @param endColor couleur d'arrivée
     */
    public void degradeVerticaleInverse(Color startColor, Color endColor)
    {
        this.degradeVertical(endColor, startColor);
    }
    
    public void degrade4CoinsArrondis(Color startColor, Color endColor, int rayon)
    {
        this.g.setColor(startColor);
        for (int i = 0; i < rayon; i++)
        {    
            this.g.drawLine(0 + i, 0, 0, rayon - i);
            this.g.drawLine(this.largeur - i, 0, this.largeur, rayon - i);
        }
        this.g.setColor(endColor);
        for (int i = 0; i < rayon; i++)
        {
            this.g.drawLine(0, this.hauteur - rayon + i, 0+i, this.hauteur);
            this.g.drawLine(this.largeur - i, this.hauteur, this.largeur, 
                this.hauteur - rayon + i);
        }
    }
    
    /**
     * Recalibre la valeur de la composante de couleur 
     * @param composanteCouleur la composante de couleur RGB
     * @return la valeur calibrée
     */
    private int calibreValeur(int composanteCouleur)
    {
        composanteCouleur = Math.abs(composanteCouleur);
        if (composanteCouleur < 0) 
        {
            composanteCouleur = 0;
        }
        else if (composanteCouleur > 255) 
        {
            composanteCouleur = 255;
        }
        return composanteCouleur;
    }
    
    /**
     * Calcul de la composante de couleur
     * @param cc1 compasante de départ
     * @param cc2 composante d'arrivée
     * @param niveau
     * @return
     */
    private int calculComposanteCouleur(double cc1, double cc2, int niveau)
    {
        if (cc1 <= cc2)
        {
            return this.calibreValeur((int) Math.round(
                cc2 + niveau*((cc1-cc2)/this.hauteur)));
        }
        else 
        {
            return this.calibreValeur((int) Math.round(
                cc2 - niveau*((cc1-cc2)/this.hauteur)));
        }
            
    }
    
    @Override
    public String toString()
    {
        return super.toString() + "\n   Hauteur : " + this.hauteur +
            ", Largeur : " + this.largeur + "\n       - Composant : " + 
            this.g.toString();
    }
    
    
    
}
