/*
 * Copyright MalchroSoft - Aymeric MALCHROWICZ. All right reserved.
 * The source code that contains this comment is an intellectual property
 * of MalchroSoft [Aymeric MALCHROWICZ]. Use is subject to licence terms.
 */

package com.malchrosoft.object;

import com.malchrosoft.debug.Log;

/**
 *
 * @author Aymeric Malchrowicz
 */
public class FileLoadingReport
{
    private int foundFileNumber;
    private int loadedFileNumber;
    private int rejectedFileNumber;
    private String indentText;

    /**
     * Creates a new file loading report object
     * @param fondFileNumber the number of found files
     * @param loadedFileNumber the number of loaded files
     * @param rejectedFileNumber the number of rejected files
     */
    public FileLoadingReport(int fondFileNumber, int loadedFileNumber,
        int rejectedFileNumber)
    {
        this.foundFileNumber = fondFileNumber;
        this.loadedFileNumber = loadedFileNumber;
        this.rejectedFileNumber = rejectedFileNumber;
        this.indentText = "";
    }

    /**
     * Creates a new file loading report object<br/>
     * The number of rejected file will compute
     * @param fondFileNumber the number of found files
     * @param loadedFileNumber the number of loaded files
     */
    public FileLoadingReport(int fondFileNumber, int loadedFileNumber)
    {
        this(fondFileNumber, loadedFileNumber, fondFileNumber-loadedFileNumber);
    }

    public int getFoundFileNumber()
    {
        return foundFileNumber;
    }

    public int getLoadedFileNumber()
    {
        return loadedFileNumber;
    }

    public int getRejectedFileNumber()
    {
        return rejectedFileNumber;
    }

    public String getIndentText()
    {
        return indentText;
    }

    public String getHTMLIndentText()
    {
        return indentText.replace(" ", "&nbsp;");
    }

    public void setIndentText(String indentText)
    {
        this.indentText = indentText;
    }
    
    public String getFoundFileNumberReportLine(String postNumber)
    {
        return this.getFoundFileNumber() + " " + postNumber;
    }

    public String getLoadedFileNumberReportLine(String postNumber)
    {
        return this.getLoadedFileNumber() + " " + postNumber;
    }

    public String getRejectedFileNumberReportLine(String postNumber)
    {
        return this.getRejectedFileNumber() + " " + postNumber;
    }

    public String getReport(String foundFilesText, String loadedFileText,
        String rejectedFileText)
    {
        return this.indentText + this.getFoundFileNumberReportLine(foundFilesText) + "\n" +
            this.indentText + this.getLoadedFileNumberReportLine(loadedFileText) + "\n" +
            this.indentText + this.getRejectedFileNumberReportLine(rejectedFileText);
    }

    public String getHTMLReport(String foundFilesText, String loadedFileText,
        String rejectedFileText)
    {
        return this.getHTMLIndentText() + this.getFoundFileNumberReportLine(foundFilesText) + "<br/>\n" +
            this.getHTMLIndentText() + this.getLoadedFileNumberReportLine(loadedFileText) + "<br/>\n" +
            this.getHTMLIndentText() + this.getRejectedFileNumberReportLine(rejectedFileText);
    }

    public String getHTMLReportWithHighlightedNumbers(String foundFilesText,
        String loadedFileText, String rejectedFileText)
    {
        String s = this.getHTMLReport(foundFilesText, loadedFileText,
            rejectedFileText);
        String newS = "";
        String[] plitS = s.split("\n");
        for (String cs : plitS) newS += this.boldFirstWord(cs) + "\n";
        return newS;
    }

    private String boldFirstWord(String s)
    {
        int pos = s.indexOf(" ", this.indentText.length());
        String firstWord = s.substring(0, pos);
        return "<b>" + firstWord + "</b>" + s.substring(pos);
    }

    public static void main(String argv[])
    {
        FileLoadingReport report = new FileLoadingReport(100, 65);
        Log.info(report.getReport("found",
            "loaded", "rejected"));
        Log.info(report.boldFirstWord("[bold] the first word..."));
        Log.info(report.getHTMLReportWithHighlightedNumbers("found",
            "loaded", "rejected"));
        System.exit(0);
    }

}
