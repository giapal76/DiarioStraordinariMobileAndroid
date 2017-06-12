package com.example.andrea.diariostraordinari.Adapter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;
import android.print.pdf.PrintedPdfDocument;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Andrea on 11/04/17.
 */

/*** LEGENDA COMMENTI:
 *
 * /*** INIBITO *** = CODICE AUTOGENERATO NON RICHIESTO DALLE SPECIFICHE DEL PROGETTO
 *
 * /*** RIF N *** = RIFERIMENTO NUMERO N (SEGNALIBRO PER COMMENTI)
 *
 * /*** VEDI RIF N *** = LA PORZIONE DI PROGRAMMA IN BASSO E' LEGATA AL RIFERIMENTO N
 *
 * /*** DA IMPLEMENTARE *** = CODICE DA IMPLEMENTARE (VEDI COMMENTI IN BASSO)
 *
 * /*** TEST *** = CODICE MOMENTANEO DA MODIFICARE O ELIMINARE SUCCESSIVAMENTE
 *
 */

/*** COSE DA FARE
 *
 * *** DA MODIFICARE ***
 *
 * VEDI RIF. 1
 *
 */

/*** TEST ***/
/*** RIF. 1 ***/
/*** http://www.techotopia.com/index.php/An_Android_Custom_Document_Printing_Tutorial ***/
//Adapter per documenti di stampa
    //C'è parecchio da modificare perché al momento crea solo un file di prova da stampare
public class MyPrintDocumentAdapter extends PrintDocumentAdapter {

    Context context;
    private int pageHeight;
    private int pageWidth;
    public PdfDocument myPdfDocument;
    public int totalpages = 1;
    public String pdf_file_name = "test_print.pdf";

    public int k_external_margin = 200;
    public int horizontal_external_margin = 2970/k_external_margin;
    public int vertical_external_margin = 2100/k_external_margin;

    //Valori da stampare
    private int values;
    private String [] printableValues = new String[values];

    public MyPrintDocumentAdapter(Context context, int values, String [] printableValues)
    {
        this.context = context;
        this.values = values;
        this.printableValues = printableValues;
    }

    @Override
    public void onLayout(PrintAttributes oldAttributes,
                         PrintAttributes newAttributes,
                         CancellationSignal cancellationSignal,
                         LayoutResultCallback callback,
                         Bundle metadata) {

        myPdfDocument = new PrintedPdfDocument(context, newAttributes);

        //Formato A4 = 2100mmx2970mm

        pageHeight = 2100 - horizontal_external_margin;

        pageWidth = 2970 - vertical_external_margin;



        if (cancellationSignal.isCanceled() ) {
            callback.onLayoutCancelled();
            return;
        }

        if (totalpages > 0) {
            //Nome file pdf
            PrintDocumentInfo.Builder builder = new PrintDocumentInfo
                    .Builder(pdf_file_name)
                    .setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
                    .setPageCount(totalpages);

            PrintDocumentInfo info = builder.build();
            callback.onLayoutFinished(info, true);
        } else {
            callback.onLayoutFailed("Page count is zero.");
        }
    }


    @Override
    public void onWrite(final PageRange[] pageRanges,
                        final ParcelFileDescriptor destination,
                        final CancellationSignal cancellationSignal,
                        final WriteResultCallback callback) {

        for (int i = 0; i < totalpages; i++) {
            if (pageInRange(pageRanges, i))
            {
                //Crea il foglio
                PdfDocument.PageInfo newPage = new PdfDocument.PageInfo.Builder(pageWidth,
                        pageHeight, i).create();

                PdfDocument.Page page =
                        myPdfDocument.startPage(newPage);

                if (cancellationSignal.isCanceled()) {
                    callback.onWriteCancelled();
                    myPdfDocument.close();
                    myPdfDocument = null;
                    return;
                }
                //Disegna la pagina
                myDrawPage(page,i);
                myPdfDocument.finishPage(page);
            }
        }

        try {
            myPdfDocument.writeTo(new FileOutputStream(
                    destination.getFileDescriptor()));
        } catch (IOException e) {
            callback.onWriteFailed(e.toString());
            return;
        } finally {
            myPdfDocument.close();
            myPdfDocument = null;
        }

        callback.onWriteFinished(pageRanges);
    }

    private boolean pageInRange(PageRange[] pageRanges, int page)
    {
        for (int i = 0; i<pageRanges.length; i++)
        {
            if ((page >= pageRanges[i].getStart()) &&
                    (page <= pageRanges[i].getEnd()))
                return true;
        }
        return false;
    }

    private void myDrawPage(PdfDocument.Page page, int pagenumber) {

        Canvas canvas = page.getCanvas();

        int riga, colonne;
        int tot_righe = 18;
        int margin = 6;

        int primary_text_size = 7200/k_external_margin;
        int secondary_text_size = 6000/k_external_margin;
        int nome_operaio_text_size = 8000/k_external_margin;
        int tipo_straordinario_text_size = 5200/k_external_margin;

        String ZONA = printableValues[0];
        String UO = printableValues[1];
        String PRESTAZIONE_DI_LAVORO = "PRESTAZIONE DI LAVORO STRAORDINARIO";
        String EFFETTUATO = printableValues[2];
        String DATA = printableValues[3] + "/" + printableValues[4] + "/" + printableValues[5];
        String OPERAIO1 = printableValues[6];
        String DESCRIZIONE = printableValues[7];
        String COMUNE = printableValues[8];
        String TIPO_STRAORDINARIO = printableValues[9];
        String DALLE = printableValues[10] + ":" + printableValues[11];
        String ALLE = printableValues[12] + ":" + printableValues[13];
        String ORE = calcolaOre(DALLE, ALLE);

        pagenumber++;

        Paint paintable_border = new Paint();
        Paint paintable_text = new Paint();

        paintable_border.setColor(Color.BLACK);

        //Prima riga

        riga = 1;
        colonne = 3;

        setPaintableTextParams(paintable_text, Color.BLACK, secondary_text_size);

        drawCell(false, canvas, paintable_border, paintable_text, ZONA, margin, horizontal_external_margin, ((riga -1) * canvas.getHeight()/tot_righe) + vertical_external_margin, canvas.getWidth()/colonne, riga * canvas.getHeight()/tot_righe);
        drawCell(false, canvas, paintable_border, paintable_text, UO, margin, canvas.getWidth()/colonne, (riga -1) * canvas.getHeight()/tot_righe, (canvas.getWidth()) - horizontal_external_margin, riga * canvas.getHeight()/tot_righe);

        //Seconda riga

        riga = 2;
        colonne = 3;

        setPaintableTextParams(paintable_text, Color.BLACK, primary_text_size);

        drawCell(true, canvas, paintable_border, paintable_text, PRESTAZIONE_DI_LAVORO, margin, horizontal_external_margin, (riga -1) * canvas.getHeight()/tot_righe, canvas.getWidth()/colonne, riga * canvas.getHeight()/tot_righe);
        drawCell(true, canvas, paintable_border, paintable_text, EFFETTUATO, margin, canvas.getWidth()/colonne, (riga -1) * canvas.getHeight()/tot_righe, 2*canvas.getWidth()/colonne,  riga * canvas.getHeight()/tot_righe);
        drawCell(true, canvas, paintable_border, paintable_text, DATA, margin, 2*canvas.getWidth()/colonne, (riga -1) * canvas.getHeight()/tot_righe, (canvas.getWidth() - horizontal_external_margin),  riga * canvas.getHeight()/tot_righe);

        //Terza e quarta riga

        riga = 3;
        colonne = 5;

        setPaintableTextParams(paintable_text, Color.BLACK, primary_text_size);

        drawCell(true, canvas, paintable_border, paintable_text, "", margin, horizontal_external_margin, (riga -1) * canvas.getHeight()/tot_righe, canvas.getWidth()/colonne, (riga +1) * canvas.getHeight()/tot_righe);
            drawCell(false, canvas, paintable_border, paintable_text, "NOMINATIVO", 2*margin, horizontal_external_margin, (riga -1) * canvas.getHeight()/tot_righe, canvas.getWidth()/colonne, (riga) * canvas.getHeight()/tot_righe);
        drawCell(true, canvas, paintable_border, paintable_text, "", margin, canvas.getWidth()/colonne, (riga -1) * canvas.getHeight()/tot_righe, 2*canvas.getWidth()/colonne, (riga +1) * canvas.getHeight()/tot_righe);
            drawCell(false, canvas, paintable_border, paintable_text, "INDICAZIONE DEL MOTIVO", 2*margin, canvas.getWidth()/colonne, (riga -1) * canvas.getHeight()/tot_righe, 2*canvas.getWidth()/colonne,  (riga) * canvas.getHeight()/tot_righe);
            drawCell(false, canvas, paintable_border, paintable_text, "E DESCRIZIONE DEL LAVORO", 2*margin, canvas.getWidth()/colonne, (riga) * canvas.getHeight()/tot_righe, 2*canvas.getWidth()/colonne,  (riga +1) * canvas.getHeight()/tot_righe);
        drawCell(true, canvas, paintable_border, paintable_text, "", margin, 2*canvas.getWidth()/colonne, (riga -1) * canvas.getHeight()/tot_righe, 3*canvas.getWidth()/colonne,  (riga +1) * canvas.getHeight()/tot_righe);
            drawCell(false, canvas, paintable_border, paintable_text, "LOCALITA' DELLA PRESTAZIONE", 2*margin, 2*canvas.getWidth()/colonne, (riga -1) * canvas.getHeight()/tot_righe, 3*canvas.getWidth()/colonne,  (riga) * canvas.getHeight()/tot_righe);
        drawCell(true, canvas, paintable_border, paintable_text, "", margin, 3*canvas.getWidth()/colonne, (riga -1) * canvas.getHeight()/tot_righe, 4*canvas.getWidth()/colonne,  (riga +1) * canvas.getHeight()/tot_righe);
            drawCell(false, canvas, paintable_border, paintable_text, "TIPO: ( PGM - SEL - GST )", 2*margin, 3*canvas.getWidth()/colonne, (riga -1) * canvas.getHeight()/tot_righe, 4*canvas.getWidth()/colonne,  (riga) * canvas.getHeight()/tot_righe);
        drawCell(true, canvas, paintable_border, paintable_text, "", margin, 4*canvas.getWidth()/colonne, (riga -1) * canvas.getHeight()/tot_righe, (canvas.getWidth() - horizontal_external_margin),  (riga +1) * canvas.getHeight()/tot_righe);
            drawCell(false, canvas, paintable_border, paintable_text, "DURATA DELLA PRESTAZIONE", 2*margin, 4*canvas.getWidth()/colonne, (riga -1) * canvas.getHeight()/tot_righe, (canvas.getWidth() - horizontal_external_margin),  riga * canvas.getHeight()/tot_righe);
            drawCell(true, canvas, paintable_border, paintable_text, "DALLE", margin, 4*canvas.getWidth()/colonne, (riga) * canvas.getHeight()/tot_righe, (4*canvas.getWidth()/colonne)+((canvas.getWidth())-(4*canvas.getWidth()/colonne))/3,  (riga +1) * canvas.getHeight()/tot_righe);
            drawCell(true, canvas, paintable_border, paintable_text, "ALLE", margin, (4*canvas.getWidth()/colonne)+((canvas.getWidth())-(4*canvas.getWidth()/colonne))/3, (riga) * canvas.getHeight()/tot_righe, (4*canvas.getWidth()/colonne)+2*((canvas.getWidth())-(4*canvas.getWidth()/colonne))/3,  (riga +1) * canvas.getHeight()/tot_righe);
            drawCell(true, canvas, paintable_border, paintable_text, "N. ORE", margin,(4*canvas.getWidth()/colonne)+2*((canvas.getWidth())-(4*canvas.getWidth()/colonne))/3, (riga) * canvas.getHeight()/tot_righe, (canvas.getWidth() - horizontal_external_margin),  (riga +1) * canvas.getHeight()/tot_righe);

        //Dalla quinta alla tredicesima riga

        for(int i = 5; i < 15; i++){

            colonne = 5;

            if(i == 5) {

                setPaintableTextParams(paintable_text, Color.BLACK, nome_operaio_text_size);

                drawCell(true, canvas, paintable_border, paintable_text, OPERAIO1, margin, horizontal_external_margin, (i - 1) * canvas.getHeight() / tot_righe, canvas.getWidth() / colonne, i * canvas.getHeight() / tot_righe);

                setPaintableTextParams(paintable_text, Color.BLACK, primary_text_size);

                drawCell(true, canvas, paintable_border, paintable_text, DESCRIZIONE, margin, canvas.getWidth() / colonne, (i - 1) * canvas.getHeight() / tot_righe, 2 * canvas.getWidth() / colonne, i * canvas.getHeight() / tot_righe);
                drawCell(true, canvas, paintable_border, paintable_text, COMUNE, margin, 2 * canvas.getWidth() / colonne, (i - 1) * canvas.getHeight() / tot_righe, 3 * canvas.getWidth() / colonne, i * canvas.getHeight() / tot_righe);

                setPaintableTextParams(paintable_text, Color.BLACK, tipo_straordinario_text_size);

                drawCell(true, canvas, paintable_border, paintable_text, TIPO_STRAORDINARIO, margin, 3 * canvas.getWidth() / colonne, (i - 1) * canvas.getHeight() / tot_righe, 4 * canvas.getWidth() / colonne, i * canvas.getHeight() / tot_righe);

                setPaintableTextParams(paintable_text, Color.BLACK, primary_text_size);

                drawCell(true, canvas, paintable_border, paintable_text, DALLE, margin, 4 * canvas.getWidth() / colonne, (i - 1) * canvas.getHeight() / tot_righe, (4 * canvas.getWidth() / colonne) + ((canvas.getWidth()) - (4 * canvas.getWidth() / colonne)) / 3, i * canvas.getHeight() / tot_righe);
                drawCell(true, canvas, paintable_border, paintable_text, ALLE, margin, (4 * canvas.getWidth() / colonne) + ((canvas.getWidth()) - (4 * canvas.getWidth() / colonne)) / 3, (i - 1) * canvas.getHeight() / tot_righe, (4 * canvas.getWidth() / colonne) + 2 * ((canvas.getWidth()) - (4 * canvas.getWidth() / colonne)) / 3, i * canvas.getHeight() / tot_righe);

                setPaintableTextParams(paintable_text, Color.BLACK, secondary_text_size);
                drawCell(true, canvas, paintable_border, paintable_text, ORE, margin, (4 * canvas.getWidth() / colonne) + 2 * ((canvas.getWidth()) - (4 * canvas.getWidth() / colonne)) / 3, (i - 1) * canvas.getHeight() / tot_righe, (canvas.getWidth() - horizontal_external_margin), i * canvas.getHeight() / tot_righe);
                setPaintableTextParams(paintable_text, Color.BLACK, primary_text_size);

            }
            else {

                setPaintableTextParams(paintable_text, Color.BLACK, nome_operaio_text_size);

                drawCell(true, canvas, paintable_border, paintable_text, "", margin, horizontal_external_margin, (i - 1) * canvas.getHeight() / tot_righe, canvas.getWidth() / colonne, i * canvas.getHeight() / tot_righe);

                setPaintableTextParams(paintable_text, Color.BLACK, primary_text_size);

                drawCell(true, canvas, paintable_border, paintable_text, "", margin, canvas.getWidth() / colonne, (i - 1) * canvas.getHeight() / tot_righe, 2 * canvas.getWidth() / colonne, i * canvas.getHeight() / tot_righe);
                drawCell(true, canvas, paintable_border, paintable_text, "", margin, 2 * canvas.getWidth() / colonne, (i - 1) * canvas.getHeight() / tot_righe, 3 * canvas.getWidth() / colonne, i * canvas.getHeight() / tot_righe);

                setPaintableTextParams(paintable_text, Color.BLACK, tipo_straordinario_text_size);

                drawCell(true, canvas, paintable_border, paintable_text, "", margin, 3 * canvas.getWidth() / colonne, (i - 1) * canvas.getHeight() / tot_righe, 4 * canvas.getWidth() / colonne, i * canvas.getHeight() / tot_righe);

                setPaintableTextParams(paintable_text, Color.BLACK, primary_text_size);

                drawCell(true, canvas, paintable_border, paintable_text, "", margin, 4 * canvas.getWidth() / colonne, (i - 1) * canvas.getHeight() / tot_righe, (4 * canvas.getWidth() / colonne) + ((canvas.getWidth()) - (4 * canvas.getWidth() / colonne)) / 3, i * canvas.getHeight() / tot_righe);
                drawCell(true, canvas, paintable_border, paintable_text, "", margin, (4 * canvas.getWidth() / colonne) + ((canvas.getWidth()) - (4 * canvas.getWidth() / colonne)) / 3, (i - 1) * canvas.getHeight() / tot_righe, (4 * canvas.getWidth() / colonne) + 2 * ((canvas.getWidth()) - (4 * canvas.getWidth() / colonne)) / 3, i * canvas.getHeight() / tot_righe);
                drawCell(true, canvas, paintable_border, paintable_text, "", margin, (4 * canvas.getWidth() / colonne) + 2 * ((canvas.getWidth()) - (4 * canvas.getWidth() / colonne)) / 3, (i - 1) * canvas.getHeight() / tot_righe, (canvas.getWidth() - horizontal_external_margin), i * canvas.getHeight() / tot_righe);

            }

        }

        //Quartordicesima e quindicesima riga

        riga = 15;
        colonne = 8;

        setPaintableTextParams(paintable_text, Color.BLACK, secondary_text_size);

        drawCell(true, canvas, paintable_border, paintable_text, "", margin, horizontal_external_margin, (riga -1) * canvas.getHeight()/tot_righe, 3*canvas.getWidth()/colonne, ((riga +3) * canvas.getHeight()/tot_righe) - vertical_external_margin);
            drawCell(false, canvas, paintable_border, paintable_text, "NOTE:", 2*margin, horizontal_external_margin, (riga -1) * canvas.getHeight()/tot_righe, 3*canvas.getWidth()/colonne, (riga) * canvas.getHeight()/tot_righe);
        drawCell(true, canvas, paintable_border, paintable_text, "", margin, 3*canvas.getWidth()/colonne, (riga -1) * canvas.getHeight()/tot_righe, 4*canvas.getWidth()/colonne, (riga +1) * canvas.getHeight()/tot_righe);
            drawCell(false, canvas, paintable_border, paintable_text, "SEGNALATO DAL COE", 2*margin, 3*canvas.getWidth()/colonne, (riga -1) * canvas.getHeight()/tot_righe, 4*canvas.getWidth()/colonne,  (riga) * canvas.getHeight()/tot_righe);
            drawCell(false, canvas, paintable_border, paintable_text, "ORE ..............................", 2*margin, 3*canvas.getWidth()/colonne, (riga) * canvas.getHeight()/tot_righe, 4*canvas.getWidth()/colonne,  (riga +1) * canvas.getHeight()/tot_righe);
        drawCell(true, canvas, paintable_border, paintable_text, "", margin, 4*canvas.getWidth()/colonne, (riga -1) * canvas.getHeight()/tot_righe, 6*canvas.getWidth()/colonne,  (riga +1) * canvas.getHeight()/tot_righe);
            drawCell(false, canvas, paintable_border, paintable_text, "VISTO DEL CAPO SQUADRA", 2*margin, 4*canvas.getWidth()/colonne, (riga -1) * canvas.getHeight()/tot_righe, 6*canvas.getWidth()/colonne,  (riga) * canvas.getHeight()/tot_righe);
        drawCell(true, canvas, paintable_border, paintable_text, "", margin, 6*canvas.getWidth()/colonne, (riga -1) * canvas.getHeight()/tot_righe, (canvas.getWidth() - horizontal_external_margin),  (riga +1) * canvas.getHeight()/tot_righe);
            drawCell(false, canvas, paintable_border, paintable_text, "UNITA' RICHIEDENTE", 2*margin, 6*canvas.getWidth()/colonne, (riga -1) * canvas.getHeight()/tot_righe, (canvas.getWidth() - horizontal_external_margin),  (riga) * canvas.getHeight()/tot_righe);


        //Sedicesima e diciassettesima riga

        riga = 17;
        colonne = 8;

        setPaintableTextParams(paintable_text, Color.BLACK, secondary_text_size);

        drawCell(true, canvas, paintable_border, paintable_text, "", margin, 3*canvas.getWidth()/colonne, (riga -1) * canvas.getHeight()/tot_righe, 4*canvas.getWidth()/colonne, ((riga +1) * canvas.getHeight()/tot_righe) - vertical_external_margin);
            drawCell(false, canvas, paintable_border, paintable_text, "ORDINATO", 2*margin, 3*canvas.getWidth()/colonne, (riga -1) * canvas.getHeight()/tot_righe, 4*canvas.getWidth()/colonne,  (riga) * canvas.getHeight()/tot_righe);
            drawCell(false, canvas, paintable_border, paintable_text, "DA ..............................", 2*margin, 3*canvas.getWidth()/colonne, (riga) * canvas.getHeight()/tot_righe, 4*canvas.getWidth()/colonne,  ((riga +1) * canvas.getHeight()/tot_righe) - vertical_external_margin);
        drawCell(true, canvas, paintable_border, paintable_text, "", margin, 4*canvas.getWidth()/colonne, (riga -1) * canvas.getHeight()/tot_righe, 6*canvas.getWidth()/colonne,  ((riga +1) * canvas.getHeight()/tot_righe) - vertical_external_margin);
            drawCell(false, canvas, paintable_border, paintable_text, "VISTO DI CHI HA DATO L'ORDINE", 2*margin, 4*canvas.getWidth()/colonne, (riga -1) * canvas.getHeight()/tot_righe, 6*canvas.getWidth()/colonne,  (riga) * canvas.getHeight()/tot_righe);
        drawCell(true, canvas, paintable_border, paintable_text, "", margin, 6*canvas.getWidth()/colonne, (riga -1) * canvas.getHeight()/tot_righe, (canvas.getWidth() - horizontal_external_margin),  ((riga +1) * canvas.getHeight()/tot_righe) - vertical_external_margin);
            drawCell(false, canvas, paintable_border, paintable_text, "IL RESPONSABILE", 2*margin, 6*canvas.getWidth()/colonne, (riga -1) * canvas.getHeight()/tot_righe, (canvas.getWidth() - horizontal_external_margin),  ((riga) * canvas.getHeight()/tot_righe));


    }

    //Disegno una cella con bordo
    private void drawCell(Boolean is_borded, Canvas canvas, Paint paintable_border, Paint paintable_text, String text, int margin_border, int left, int top, int right, int bottom){

        Rect border_cell = new Rect(left, top, right, bottom);
        Rect cell = new Rect(left+margin_border, top+margin_border, right-margin_border, bottom-margin_border);
        float text_y = cell.centerY() + paintable_text.getTextSize()/2;
        float text_x = cell.left + margin_border;


        Paint white_canvas = new Paint();
        white_canvas.setColor(Color.WHITE);

        if (is_borded) canvas.drawRect(border_cell, paintable_border);
        canvas.drawRect(cell, white_canvas);
        canvas.drawText(text, text_x, text_y, paintable_text);


    }

    //Setto i parametri per i testi da aggiungere al pdf
    private void setPaintableTextParams(Paint paint, int color, int text_size){

        paint.setColor(color);
        paint.setTextSize(text_size);

    }

    private String calcolaOre(String start, String finish){

        try {
            int start_h = Integer.parseInt(start.substring(0, 2));
            int start_m = (start_h * 60) + Integer.parseInt(start.substring(3, 5));

            int finish_h = Integer.parseInt(finish.substring(0, 2));
            int finish_m = (finish_h * 60) + Integer.parseInt(finish.substring(3, 5));

            int time = saltoDiData(start_m, finish_m);

            int time_h = time / 60;
            int time_m = time % 60;

            return formattaOrario(time_h) + "h e " + formattaOrario(time_m) + "m";
        }catch (Exception e){
            return "00h e 00m";
        }

    }

    private String formattaOrario(int n){

        if(n < 10)
            return "0" + Integer.toString(n);
        else
            return Integer.toString(n);

    }

    private int saltoDiData(int start_m, int finish_m){

        int ris = finish_m - start_m;;

        if(ris < 0)

            ris = ((24 * 60) - start_m) + finish_m;

        return ris;
    }


}






















