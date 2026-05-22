package tsp.itch;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import tsp.itch.model.*;
import jakarta.servlet.http.HttpServletResponse;

import java.awt.Color;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class GeneradorPDF {

    // ══════════════════════════════════════════════════════════════════
    //  PALETA INSTITUCIONAL
    // ══════════════════════════════════════════════════════════════════
    private static final Color AZUL_OSCURO   = new Color(0,  47,  108);   // Encabezados
    private static final Color AZUL_MEDIO    = new Color(0,  82,  165);   // Subencabezados
    private static final Color AZUL_CLARO    = new Color(220, 232, 248);  // Filas pares
    private static final Color GRIS_CLARO    = new Color(245, 247, 250);  // Filas impares
    private static final Color GRIS_TEXTO    = new Color(60,  60,   60);
    private static final Color VERDE_OK      = new Color(0,  120,  60);
    private static final Color ROJO_NOK      = new Color(180, 0,    0);
    private static final Color VERDE_BG      = new Color(212, 240, 220);
    private static final Color ROJO_BG       = new Color(255, 220, 220);

    // URL pública del logo TecNM (se descarga en tiempo de ejecución)
    private static final String URL_LOGO =
        "https://www.tecnm.mx/images/logo-tecnm.png";

    // ══════════════════════════════════════════════════════════════════
    //  ENCABEZADO INSTITUCIONAL UNIFICADO (con logo + texto)
    // ══════════════════════════════════════════════════════════════════
    private void agregarEncabezadoInstitucional(Document doc,
                                                 String tituloPrincipal,
                                                 String subtitulo) throws Exception {
        PdfPTable header = new PdfPTable(2);
        header.setWidthPercentage(100);
        header.setWidths(new float[]{1.5f, 6f});
        header.setSpacingAfter(14);

        // ── Celda del logo ──
        PdfPCell celdaLogo = new PdfPCell();
        celdaLogo.setBackgroundColor(AZUL_OSCURO);
        celdaLogo.setBorder(Rectangle.NO_BORDER);
        celdaLogo.setPadding(10);
        celdaLogo.setVerticalAlignment(Element.ALIGN_MIDDLE);
        celdaLogo.setHorizontalAlignment(Element.ALIGN_CENTER);
        try {
            Image logo = Image.getInstance(new URL(URL_LOGO));
            logo.scaleToFit(80, 60);
            celdaLogo.addElement(logo);
        } catch (Exception e) {
            // Si no hay internet, poner texto institucional en lugar del logo
            Font fLogo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9, Color.WHITE);
            Paragraph pLogo = new Paragraph("TecNM", fLogo);
            pLogo.setAlignment(Element.ALIGN_CENTER);
            celdaLogo.addElement(pLogo);
        }
        header.addCell(celdaLogo);

        // ── Celda del texto institucional ──
        PdfPCell celdaTexto = new PdfPCell();
        celdaTexto.setBackgroundColor(AZUL_OSCURO);
        celdaTexto.setBorder(Rectangle.NO_BORDER);
        celdaTexto.setPadding(12);
        celdaTexto.setVerticalAlignment(Element.ALIGN_MIDDLE);

        Font fInst   = FontFactory.getFont(FontFactory.HELVETICA, 9, new Color(200, 220, 255));
        Font fTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 15, Color.WHITE);
        Font fSub    = FontFactory.getFont(FontFactory.HELVETICA, 10, new Color(180, 210, 255));

        Paragraph pInst = new Paragraph("TECNOLÓGICO NACIONAL DE MÉXICO  |  Campus Chilpancingo", fInst);
        pInst.setAlignment(Element.ALIGN_LEFT);
        celdaTexto.addElement(pInst);

        Paragraph pTit = new Paragraph(tituloPrincipal, fTitulo);
        pTit.setAlignment(Element.ALIGN_LEFT);
        pTit.setSpacingBefore(4);
        celdaTexto.addElement(pTit);

        if (subtitulo != null && !subtitulo.isEmpty()) {
            Paragraph pSub = new Paragraph(subtitulo, fSub);
            pSub.setAlignment(Element.ALIGN_LEFT);
            celdaTexto.addElement(pSub);
        }

        header.addCell(celdaTexto);
        doc.add(header);
    }

    // ══════════════════════════════════════════════════════════════════
    //  LÍNEA DECORATIVA AZUL
    // ══════════════════════════════════════════════════════════════════
    private void agregarLineaDecorativa(Document doc) throws DocumentException {
        PdfPTable linea = new PdfPTable(3);
        linea.setWidthPercentage(100);
        linea.setWidths(new float[]{6f, 1f, 1.5f});
        linea.setSpacingAfter(10);

        PdfPCell c1 = celdaColor(AZUL_OSCURO, 5);
        PdfPCell c2 = celdaColor(AZUL_MEDIO, 5);
        PdfPCell c3 = celdaColor(new Color(0, 160, 220), 5);
        linea.addCell(c1);
        linea.addCell(c2);
        linea.addCell(c3);
        doc.add(linea);
    }

    private PdfPCell celdaColor(Color color, float altura) {
        PdfPCell c = new PdfPCell(new Paragraph(" "));
        c.setBackgroundColor(color);
        c.setFixedHeight(altura);
        c.setBorder(Rectangle.NO_BORDER);
        return c;
    }

    // ══════════════════════════════════════════════════════════════════
    //  SECCIÓN: Título de sección con fondo azul medio
    // ══════════════════════════════════════════════════════════════════
    private void agregarTituloSeccion(Document doc, String texto) throws DocumentException {
        PdfPTable t = new PdfPTable(1);
        t.setWidthPercentage(100);
        t.setSpacingBefore(8);
        t.setSpacingAfter(6);
        Font f = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, Color.WHITE);
        PdfPCell c = new PdfPCell(new Paragraph(texto.toUpperCase(), f));
        c.setBackgroundColor(AZUL_MEDIO);
        c.setPadding(7);
        c.setBorder(Rectangle.NO_BORDER);
        t.addCell(c);
        doc.add(t);
    }

    // ══════════════════════════════════════════════════════════════════
    //  CELDAS AUXILIARES
    // ══════════════════════════════════════════════════════════════════
    private PdfPCell crearCeldaEncabezado(String texto) {
        Font f = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9, Color.WHITE);
        PdfPCell c = new PdfPCell(new Paragraph(texto, f));
        c.setBackgroundColor(AZUL_OSCURO);
        c.setPadding(7);
        c.setHorizontalAlignment(Element.ALIGN_CENTER);
        c.setBorderColor(AZUL_MEDIO);
        return c;
    }

    private PdfPCell crearCeldaFila(String texto, Font fuente, Color bg) {
        PdfPCell c = new PdfPCell(new Paragraph(texto != null ? texto : "-", fuente));
        c.setBackgroundColor(bg);
        c.setPadding(6);
        c.setBorderColor(new Color(200, 210, 225));
        return c;
    }

    private PdfPCell crearCeldaLabel(String texto, Font fuente) {
        PdfPCell c = new PdfPCell(new Paragraph(texto, fuente));
        c.setBackgroundColor(AZUL_CLARO);
        c.setPadding(7);
        c.setBorderColor(new Color(180, 200, 230));
        return c;
    }

    private PdfPCell crearCeldaValor(String texto, Font fuente) {
        PdfPCell c = new PdfPCell(new Paragraph(texto != null ? texto : "-", fuente));
        c.setBackgroundColor(Color.WHITE);
        c.setPadding(7);
        c.setBorderColor(new Color(180, 200, 230));
        return c;
    }

    // ── Firmas ──
    private void agregarFirmas(Document doc, String firma1Texto, String firma2Texto)
            throws DocumentException {
        doc.add(new Paragraph("\n\n\n"));
        PdfPTable tablaFirmas = new PdfPTable(3);
        tablaFirmas.setWidthPercentage(85);
        tablaFirmas.setWidths(new float[]{4, 2, 4});
        tablaFirmas.setHorizontalAlignment(Element.ALIGN_CENTER);
        Font fFirma = FontFactory.getFont(FontFactory.HELVETICA, 9, GRIS_TEXTO);
        Font fCargo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9, AZUL_OSCURO);

        PdfPCell f1 = new PdfPCell();
        f1.setBorder(Rectangle.TOP);
        f1.setBorderColor(AZUL_OSCURO);
        f1.setPaddingTop(8);
        f1.setHorizontalAlignment(Element.ALIGN_CENTER);
        f1.addElement(new Paragraph(firma1Texto, fCargo));
        tablaFirmas.addCell(f1);

        PdfPCell esp = new PdfPCell(new Paragraph(" "));
        esp.setBorder(Rectangle.NO_BORDER);
        tablaFirmas.addCell(esp);

        PdfPCell f2 = new PdfPCell();
        f2.setBorder(Rectangle.TOP);
        f2.setBorderColor(AZUL_OSCURO);
        f2.setPaddingTop(8);
        f2.setHorizontalAlignment(Element.ALIGN_CENTER);
        f2.addElement(new Paragraph(firma2Texto, fCargo));
        tablaFirmas.addCell(f2);

        doc.add(tablaFirmas);
    }

    // ══════════════════════════════════════════════════════════════════
    //  1. PDF PAT GENERAL
    // ══════════════════════════════════════════════════════════════════
    public void generarPat(Pat pat, List<Actividad> actividades,
                           HttpServletResponse response) throws Exception {
        Document doc = new Document(PageSize.A4, 50, 50, 40, 40);
        PdfWriter writer = PdfWriter.getInstance(doc, response.getOutputStream());
        doc.open();

        agregarEncabezadoInstitucional(doc,
            "PLAN DE ACCIÓN TUTORIAL",
            "Departamento de Desarrollo Académico");
        agregarLineaDecorativa(doc);

        Font fLabel = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, AZUL_OSCURO);
        Font fValor = FontFactory.getFont(FontFactory.HELVETICA, 10, GRIS_TEXTO);

        // ── Título del PAT centrado con fondo ──
        PdfPTable tablaTitulo = new PdfPTable(1);
        tablaTitulo.setWidthPercentage(100);
        tablaTitulo.setSpacingAfter(10);
        Font fTit = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, AZUL_OSCURO);
        PdfPCell cTit = new PdfPCell(new Paragraph(
            pat.getTitulo() != null ? pat.getTitulo() : "-", fTit));
        cTit.setHorizontalAlignment(Element.ALIGN_CENTER);
        cTit.setBackgroundColor(GRIS_CLARO);
        cTit.setPadding(12);
        cTit.setBorderColor(AZUL_CLARO);
        tablaTitulo.addCell(cTit);
        doc.add(tablaTitulo);

        // ── Datos generales ──
        agregarTituloSeccion(doc, "Datos Generales");
        PdfPTable tablaDatos = new PdfPTable(2);
        tablaDatos.setWidthPercentage(100);
        tablaDatos.setWidths(new float[]{2f, 5f});
        tablaDatos.setSpacingAfter(10);
        tablaDatos.addCell(crearCeldaLabel("Semestre:", fLabel));
        tablaDatos.addCell(crearCeldaValor(
            pat.getSemestre() != null ? pat.getSemestre().getNombre() : "-", fValor));
        tablaDatos.addCell(crearCeldaLabel("Estatus:", fLabel));
        tablaDatos.addCell(crearCeldaValor(
            pat.getEstatus() != null && pat.getEstatus() == 1 ? "Activo" : "Inactivo", fValor));
        doc.add(tablaDatos);

        // ── Descripción ──
        agregarTituloSeccion(doc, "Descripción");
        PdfPTable tablaDesc = new PdfPTable(1);
        tablaDesc.setWidthPercentage(100);
        tablaDesc.setSpacingAfter(10);
        PdfPCell cDesc = new PdfPCell(new Paragraph(
            pat.getDescripcion() != null ? pat.getDescripcion() : "Sin descripción.", fValor));
        cDesc.setBackgroundColor(GRIS_CLARO);
        cDesc.setPadding(10);
        cDesc.setBorderColor(AZUL_CLARO);
        tablaDesc.addCell(cDesc);
        doc.add(tablaDesc);

        // ── Tabla de actividades ──
        agregarTituloSeccion(doc, "Actividades Programadas");
        doc.add(crearTablaActividades(actividades));

        // ── Pie ──
        doc.add(new Paragraph("\n"));
        agregarLineaDecorativa(doc);
        Font fPie = FontFactory.getFont(FontFactory.HELVETICA, 8, Color.GRAY);
        Paragraph pie = new Paragraph(
            "Documento generado por el Sistema de Gestión de Tutorías — ITCH", fPie);
        pie.setAlignment(Element.ALIGN_CENTER);
        doc.add(pie);

        doc.close();
    }

    // ══════════════════════════════════════════════════════════════════
    //  2. PDF PAT POR CARRERA
    // ══════════════════════════════════════════════════════════════════
    public void generarPatCarrera(PatCarrera pc, List<Actividad> actividades,
                                   HttpServletResponse response) throws Exception {
        Document doc = new Document(PageSize.A4, 50, 50, 40, 40);
        PdfWriter.getInstance(doc, response.getOutputStream());
        doc.open();

        agregarEncabezadoInstitucional(doc,
            "PLAN DE ACCIÓN TUTORIAL POR CARRERA",
            "Departamento de Desarrollo Académico");
        agregarLineaDecorativa(doc);

        Font fLabel = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, AZUL_OSCURO);
        Font fValor = FontFactory.getFont(FontFactory.HELVETICA, 10, GRIS_TEXTO);

        // ── Título del PAT ──
        PdfPTable tablaTitulo = new PdfPTable(1);
        tablaTitulo.setWidthPercentage(100);
        tablaTitulo.setSpacingAfter(10);
        Font fTit = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, AZUL_OSCURO);
        PdfPCell cTit = new PdfPCell(new Paragraph(
            pc.getPat() != null ? pc.getPat().getTitulo() : "-", fTit));
        cTit.setHorizontalAlignment(Element.ALIGN_CENTER);
        cTit.setBackgroundColor(GRIS_CLARO);
        cTit.setPadding(12);
        cTit.setBorderColor(AZUL_CLARO);
        tablaTitulo.addCell(cTit);
        doc.add(tablaTitulo);

        // ── Datos generales ──
        agregarTituloSeccion(doc, "Datos Generales");
        PdfPTable tablaDatos = new PdfPTable(2);
        tablaDatos.setWidthPercentage(100);
        tablaDatos.setWidths(new float[]{2f, 5f});
        tablaDatos.setSpacingAfter(10);

        tablaDatos.addCell(crearCeldaLabel("Carrera:", fLabel));
        tablaDatos.addCell(crearCeldaValor(
            pc.getCarrera() != null ? pc.getCarrera().getNombre() : "-", fValor));
        tablaDatos.addCell(crearCeldaLabel("Semestre:", fLabel));
        tablaDatos.addCell(crearCeldaValor(
            pc.getSemestre() != null ? pc.getSemestre().getNombre() : "-", fValor));
        tablaDatos.addCell(crearCeldaLabel("Estatus:", fLabel));
        tablaDatos.addCell(crearCeldaValor(
            pc.getEstatus() != null && pc.getEstatus() == 1 ? "Activo" : "Inactivo", fValor));
        doc.add(tablaDatos);

        // ── Descripción del PAT general ──
        agregarTituloSeccion(doc, "Descripción General");
        PdfPTable tablaDesc = new PdfPTable(1);
        tablaDesc.setWidthPercentage(100);
        tablaDesc.setSpacingAfter(10);
        PdfPCell cDesc = new PdfPCell(new Paragraph(
            pc.getPat() != null && pc.getPat().getDescripcion() != null
                ? pc.getPat().getDescripcion() : "Sin descripción.", fValor));
        cDesc.setBackgroundColor(GRIS_CLARO);
        cDesc.setPadding(10);
        cDesc.setBorderColor(AZUL_CLARO);
        tablaDesc.addCell(cDesc);
        doc.add(tablaDesc);

        // ── Actividades ──
        agregarTituloSeccion(doc, "Actividades Programadas");
        doc.add(crearTablaActividades(actividades));

        doc.add(new Paragraph("\n"));
        agregarLineaDecorativa(doc);
        Font fPie = FontFactory.getFont(FontFactory.HELVETICA, 8, Color.GRAY);
        Paragraph pie = new Paragraph(
            "Documento generado por el Sistema de Gestión de Tutorías — ITCH", fPie);
        pie.setAlignment(Element.ALIGN_CENTER);
        doc.add(pie);

        doc.close();
    }

    // ══════════════════════════════════════════════════════════════════
    //  3. PDF LISTA DE TUTORADOS POR TUTOR
    // ══════════════════════════════════════════════════════════════════
    public void generarListaTutorados(List<Asignacion> asignaciones,
                                       HttpServletResponse response) throws Exception {
        Document doc = new Document(PageSize.A4, 50, 50, 40, 40);
        PdfWriter.getInstance(doc, response.getOutputStream());
        doc.open();

        Font fLabel = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, AZUL_OSCURO);
        Font fValor = FontFactory.getFont(FontFactory.HELVETICA, 10, GRIS_TEXTO);

        boolean primero = true;
        for (Asignacion asig : asignaciones) {
            if (asig.getEstatus() == null || asig.getEstatus() == 0) continue;

            if (!primero) doc.newPage();
            primero = false;

            agregarEncabezadoInstitucional(doc,
                "LISTA DE TUTORADOS",
                "Programa Institucional de Tutorías");
            agregarLineaDecorativa(doc);

            // ── Datos del tutor ──
            agregarTituloSeccion(doc, "Datos de la Asignación");
            PdfPTable tablaDatos = new PdfPTable(2);
            tablaDatos.setWidthPercentage(100);
            tablaDatos.setWidths(new float[]{2f, 5f});
            tablaDatos.setSpacingAfter(10);

            String nombreTutor = asig.getTutor() != null
                ? asig.getTutor().getNombre() + " " + asig.getTutor().getApellido()
                : "Sin tutor";

            tablaDatos.addCell(crearCeldaLabel("Tutor asignado:", fLabel));
            tablaDatos.addCell(crearCeldaValor(nombreTutor, fValor));
            tablaDatos.addCell(crearCeldaLabel("Carrera:", fLabel));
            tablaDatos.addCell(crearCeldaValor(
                asig.getCarrera() != null ? asig.getCarrera().getNombre() : "-", fValor));
            tablaDatos.addCell(crearCeldaLabel("Semestre:", fLabel));
            tablaDatos.addCell(crearCeldaValor(
                asig.getSemestre() != null ? asig.getSemestre().getNombre() : "-", fValor));
            tablaDatos.addCell(crearCeldaLabel("Aula:", fLabel));
            tablaDatos.addCell(crearCeldaValor(asig.getAula() != null ? asig.getAula() : "-", fValor));
            tablaDatos.addCell(crearCeldaLabel("Horario:", fLabel));
            tablaDatos.addCell(crearCeldaValor(asig.getHorario() != null ? asig.getHorario() : "-", fValor));
            doc.add(tablaDatos);

            // ── Tabla de tutorados ──
            agregarTituloSeccion(doc, "Tutorados a Cargo");
            PdfPTable tablaTutorados = new PdfPTable(4);
            tablaTutorados.setWidthPercentage(100);
            tablaTutorados.setWidths(new float[]{0.8f, 2.5f, 2.5f, 4f});
            tablaTutorados.setSpacingAfter(20);

            tablaTutorados.addCell(crearCeldaEncabezado("No."));
            tablaTutorados.addCell(crearCeldaEncabezado("Nombre"));
            tablaTutorados.addCell(crearCeldaEncabezado("Apellido"));
            tablaTutorados.addCell(crearCeldaEncabezado("Correo"));

            Font fCelda = FontFactory.getFont(FontFactory.HELVETICA, 9, GRIS_TEXTO);
            if (asig.getTutorados() != null) {
                int fila = 0;
                for (AsignacionTutorado at : asig.getTutorados()) {
                    Tutorado t = at.getTutorado();
                    if (t == null) continue;
                    Color bg = (fila % 2 == 0) ? Color.WHITE : AZUL_CLARO;
                    tablaTutorados.addCell(crearCeldaFila(String.valueOf(fila + 1), fCelda, bg));
                    tablaTutorados.addCell(crearCeldaFila(t.getNombre(), fCelda, bg));
                    tablaTutorados.addCell(crearCeldaFila(t.getApellido(), fCelda, bg));
                    tablaTutorados.addCell(crearCeldaFila(t.getCorreo(), fCelda, bg));
                    fila++;
                }
            }
            doc.add(tablaTutorados);

            agregarFirmas(doc, "Coordinador de Tutoría por Carrera", "Jefe Académico");
            agregarLineaDecorativa(doc);
        }

        doc.close();
    }

    // ══════════════════════════════════════════════════════════════════
    //  4. PDF FORMATO ÚNICO DE ASISTENCIA
    // ══════════════════════════════════════════════════════════════════
    public void generarFormatoAsistencia(Asignacion asignacion,
                                          List<SesionTutoria> sesiones,
                                          List<AsignacionTutorado> tutorados,
                                          List<Asistencia> todasAsistencias,
                                          HttpServletResponse response) throws Exception {
        Document doc = new Document(PageSize.A4.rotate(), 40, 40, 36, 36);
        PdfWriter.getInstance(doc, response.getOutputStream());
        doc.open();

        agregarEncabezadoInstitucional(doc,
            "FORMATO ÚNICO DE ASISTENCIA",
            "Programa Institucional de Tutorías");
        agregarLineaDecorativa(doc);

        Font fLabel = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, AZUL_OSCURO);
        Font fValor = FontFactory.getFont(FontFactory.HELVETICA, 10, GRIS_TEXTO);

        // ── Datos de asignación ──
        agregarTituloSeccion(doc, "Datos de la Asignación");
        PdfPTable tablaDatos = new PdfPTable(4);
        tablaDatos.setWidthPercentage(100);
        tablaDatos.setWidths(new float[]{2f, 4f, 2f, 4f});
        tablaDatos.setSpacingAfter(10);

        String nombreTutor = asignacion.getTutor() != null
            ? asignacion.getTutor().getNombre() + " " + asignacion.getTutor().getApellido() : "-";
        tablaDatos.addCell(crearCeldaLabel("Tutor:", fLabel));
        tablaDatos.addCell(crearCeldaValor(nombreTutor, fValor));
        tablaDatos.addCell(crearCeldaLabel("Carrera:", fLabel));
        tablaDatos.addCell(crearCeldaValor(
            asignacion.getCarrera() != null ? asignacion.getCarrera().getNombre() : "-", fValor));
        tablaDatos.addCell(crearCeldaLabel("Semestre:", fLabel));
        tablaDatos.addCell(crearCeldaValor(
            asignacion.getSemestre() != null ? asignacion.getSemestre().getNombre() : "-", fValor));
        tablaDatos.addCell(crearCeldaLabel("Aula / Horario:", fLabel));
        tablaDatos.addCell(crearCeldaValor(
            (asignacion.getAula() != null ? asignacion.getAula() : "-") +
            "  /  " +
            (asignacion.getHorario() != null ? asignacion.getHorario() : "-"), fValor));
        tablaDatos.addCell(crearCeldaLabel("Total sesiones:", fLabel));
        tablaDatos.addCell(crearCeldaValor(String.valueOf(sesiones.size()), fValor));
        tablaDatos.addCell(crearCeldaLabel("Total tutorados:", fLabel));
        tablaDatos.addCell(crearCeldaValor(String.valueOf(tutorados.size()), fValor));
        doc.add(tablaDatos);

        // ── Tabla de asistencia ──
        agregarTituloSeccion(doc, "Registro de Asistencia");
        int numCols = 4 + sesiones.size() + 2;
        PdfPTable tablaAsist = new PdfPTable(numCols);
        tablaAsist.setWidthPercentage(100);
        tablaAsist.setSpacingAfter(20);

        float[] anchos = new float[numCols];
        anchos[0] = 0.6f;
        anchos[1] = 1.8f;
        anchos[2] = 2.2f;
        anchos[3] = 2.2f;
        for (int i = 0; i < sesiones.size(); i++) anchos[4 + i] = 1.1f;
        anchos[numCols - 2] = 1f;
        anchos[numCols - 1] = 1.1f;
        tablaAsist.setWidths(anchos);

        tablaAsist.addCell(crearCeldaEncabezado("#"));
        tablaAsist.addCell(crearCeldaEncabezado("No. Control"));
        tablaAsist.addCell(crearCeldaEncabezado("Nombre"));
        tablaAsist.addCell(crearCeldaEncabezado("Apellido"));

        for (SesionTutoria s : sesiones) {
            Font fSem = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 7, Color.WHITE);
            PdfPCell cSem = new PdfPCell(new Paragraph(
                "S" + s.getSemana() + "\n" +
                (s.getFecha() != null ? s.getFecha().toString() : "-"), fSem));
            cSem.setBackgroundColor(AZUL_MEDIO);
            cSem.setPadding(4);
            cSem.setHorizontalAlignment(Element.ALIGN_CENTER);
            cSem.setBorderColor(AZUL_OSCURO);
            tablaAsist.addCell(cSem);
        }
        tablaAsist.addCell(crearCeldaEncabezado("Total"));
        tablaAsist.addCell(crearCeldaEncabezado("%"));

        Font fCelda  = FontFactory.getFont(FontFactory.HELVETICA, 9, GRIS_TEXTO);
        Font fPresente = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9, VERDE_OK);
        Font fAusente  = FontFactory.getFont(FontFactory.HELVETICA, 9, new Color(180, 180, 180));

        int fila = 0;
        for (AsignacionTutorado at : tutorados) {
            Tutorado t = at.getTutorado();
            if (t == null) continue;
            Color bg = (fila % 2 == 0) ? Color.WHITE : GRIS_CLARO;

            tablaAsist.addCell(crearCeldaFila(String.valueOf(fila + 1), fCelda, bg));
            tablaAsist.addCell(crearCeldaFila(t.getNumeroControl(), fCelda, bg));
            tablaAsist.addCell(crearCeldaFila(t.getNombre(), fCelda, bg));
            tablaAsist.addCell(crearCeldaFila(t.getApellido(), fCelda, bg));

            int totalPresente = 0;
            for (SesionTutoria s : sesiones) {
                boolean asistio = todasAsistencias.stream().anyMatch(a ->
                    a.getTutorado().getIdTutorado().equals(t.getIdTutorado()) &&
                    a.getSesion().getIdSesion().equals(s.getIdSesion()) &&
                    a.getPresente() != null && a.getPresente() == 1);

                PdfPCell cAsist = new PdfPCell(new Paragraph(
                    asistio ? "✓" : "—", asistio ? fPresente : fAusente));
                cAsist.setBackgroundColor(asistio ? VERDE_BG : bg);
                cAsist.setPadding(4);
                cAsist.setHorizontalAlignment(Element.ALIGN_CENTER);
                cAsist.setBorderColor(new Color(200, 210, 225));
                tablaAsist.addCell(cAsist);
                if (asistio) totalPresente++;
            }

            double pct = sesiones.isEmpty() ? 0 :
                Math.round((totalPresente * 100.0 / sesiones.size()) * 10.0) / 10.0;
            Color bgPct = pct >= 80 ? VERDE_BG : ROJO_BG;
            Font fPct = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9,
                pct >= 80 ? VERDE_OK : ROJO_NOK);

            tablaAsist.addCell(crearCeldaFila(String.valueOf(totalPresente), fCelda, bg));
            PdfPCell cPct = new PdfPCell(new Paragraph(pct + "%", fPct));
            cPct.setBackgroundColor(bgPct);
            cPct.setPadding(4);
            cPct.setHorizontalAlignment(Element.ALIGN_CENTER);
            cPct.setBorderColor(new Color(200, 210, 225));
            tablaAsist.addCell(cPct);
            fila++;
        }
        doc.add(tablaAsist);

        agregarFirmas(doc, "Tutor", "Coordinador de Tutoría por Carrera");
        agregarLineaDecorativa(doc);
        doc.close();
    }

    // ══════════════════════════════════════════════════════════════════
    //  5. PDF CARNETS DE ESTUDIANTES
    // ══════════════════════════════════════════════════════════════════
    public void generarCarnets(Asignacion asignacion,
                                List<SesionTutoria> sesiones,
                                List<AsignacionTutorado> tutorados,
                                HttpServletResponse response) throws Exception {
        Document doc = new Document(PageSize.A4, 40, 40, 36, 36);
        PdfWriter.getInstance(doc, response.getOutputStream());
        doc.open();

        Font fLabel  = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8, AZUL_OSCURO);
        Font fValor  = FontFactory.getFont(FontFactory.HELVETICA, 8, GRIS_TEXTO);
        Font fSemana = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 7, Color.WHITE);
        Font fCargo  = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8, Color.WHITE);
        Font fPie    = FontFactory.getFont(FontFactory.HELVETICA, 7, Color.GRAY);

        String nombreTutor = asignacion.getTutor() != null
            ? asignacion.getTutor().getNombre() + " " + asignacion.getTutor().getApellido() : "-";
        String carrera  = asignacion.getCarrera() != null ? asignacion.getCarrera().getNombre() : "-";
        String semestre = asignacion.getSemestre() != null ? asignacion.getSemestre().getNombre() : "-";
        String aula     = asignacion.getAula() != null ? asignacion.getAula() : "-";
        String horario  = asignacion.getHorario() != null ? asignacion.getHorario() : "-";

        int total = tutorados.size();
        int index = 0;

        for (AsignacionTutorado at : tutorados) {
            Tutorado t = at.getTutorado();
            if (t == null) { index++; continue; }

            PdfPTable carnet = new PdfPTable(1);
            carnet.setWidthPercentage(95);
            carnet.setHorizontalAlignment(Element.ALIGN_CENTER);
            carnet.setSpacingBefore(8);
            carnet.setSpacingAfter(8);

            // ── Encabezado del carnet ──
            PdfPCell enc = new PdfPCell();
            enc.setBackgroundColor(AZUL_OSCURO);
            enc.setPadding(8);
            enc.setBorder(Rectangle.NO_BORDER);

            PdfPTable encTab = new PdfPTable(2);
            encTab.setWidths(new float[]{1.5f, 6f});
            encTab.setWidthPercentage(100);

            // mini-logo o texto
            PdfPCell miniLogo = new PdfPCell();
            miniLogo.setBackgroundColor(AZUL_OSCURO);
            miniLogo.setBorder(Rectangle.NO_BORDER);
            miniLogo.setVerticalAlignment(Element.ALIGN_MIDDLE);
            miniLogo.setPadding(4);
            try {
                Image logo = Image.getInstance(new URL(URL_LOGO));
                logo.scaleToFit(45, 35);
                miniLogo.addElement(logo);
            } catch (Exception e) {
                Font fMini = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8, Color.WHITE);
                miniLogo.addElement(new Paragraph("TecNM", fMini));
            }
            encTab.addCell(miniLogo);

            PdfPCell encTxt = new PdfPCell();
            encTxt.setBackgroundColor(AZUL_OSCURO);
            encTxt.setBorder(Rectangle.NO_BORDER);
            encTxt.setVerticalAlignment(Element.ALIGN_MIDDLE);
            encTxt.setPadding(4);
            Font fInstEnc = FontFactory.getFont(FontFactory.HELVETICA, 7, new Color(180, 210, 255));
            Font fTitEnc  = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9, Color.WHITE);
            encTxt.addElement(new Paragraph("TECNOLÓGICO NACIONAL DE MÉXICO  |  Campus Chilpancingo", fInstEnc));
            encTxt.addElement(new Paragraph("CARNET DE ASISTENCIA — PROGRAMA DE TUTORÍAS", fTitEnc));
            encTab.addCell(encTxt);
            enc.addElement(encTab);
            carnet.addCell(enc);

            // ── Datos del alumno ──
            PdfPCell datosCell = new PdfPCell();
            datosCell.setPadding(8);
            datosCell.setBorderColor(AZUL_MEDIO);
            datosCell.setBackgroundColor(GRIS_CLARO);

            PdfPTable tablaDatos = new PdfPTable(4);
            tablaDatos.setWidthPercentage(100);
            tablaDatos.setWidths(new float[]{2f, 4f, 2f, 4f});
            tablaDatos.addCell(crearCeldaLabel("Alumno:", fLabel));
            tablaDatos.addCell(crearCeldaValor(t.getNombre() + " " + t.getApellido(), fValor));
            tablaDatos.addCell(crearCeldaLabel("No. Control:", fLabel));
            tablaDatos.addCell(crearCeldaValor(t.getNumeroControl(), fValor));
            tablaDatos.addCell(crearCeldaLabel("Carrera:", fLabel));
            tablaDatos.addCell(crearCeldaValor(carrera, fValor));
            tablaDatos.addCell(crearCeldaLabel("Semestre:", fLabel));
            tablaDatos.addCell(crearCeldaValor(semestre, fValor));
            tablaDatos.addCell(crearCeldaLabel("Tutor:", fLabel));
            tablaDatos.addCell(crearCeldaValor(nombreTutor, fValor));
            tablaDatos.addCell(crearCeldaLabel("Aula / Horario:", fLabel));
            tablaDatos.addCell(crearCeldaValor(aula + " / " + horario, fValor));
            datosCell.addElement(tablaDatos);
            carnet.addCell(datosCell);

            // ── Tabla de sesiones ──
            PdfPCell sesCell = new PdfPCell();
            sesCell.setPadding(8);
            sesCell.setBorderColor(AZUL_MEDIO);

            Font fRegTit = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8, AZUL_OSCURO);
            Paragraph pReg = new Paragraph("REGISTRO DE ASISTENCIA — FIRMA DEL ALUMNO", fRegTit);
            pReg.setAlignment(Element.ALIGN_CENTER);
            pReg.setSpacingAfter(6);
            sesCell.addElement(pReg);

            int numSesiones = sesiones.isEmpty() ? 10 : sesiones.size();
            PdfPTable tablaSes = new PdfPTable(numSesiones);
            tablaSes.setWidthPercentage(100);

            for (int i = 0; i < numSesiones; i++) {
                String labelSem = (!sesiones.isEmpty() && i < sesiones.size())
                    ? "S" + sesiones.get(i).getSemana() + "\n" +
                      (sesiones.get(i).getFecha() != null ? sesiones.get(i).getFecha().toString() : "")
                    : "S" + (i + 1);
                PdfPCell cSem = new PdfPCell(new Paragraph(labelSem, fSemana));
                cSem.setBackgroundColor(AZUL_MEDIO);
                cSem.setPadding(4);
                cSem.setHorizontalAlignment(Element.ALIGN_CENTER);
                cSem.setBorderColor(AZUL_OSCURO);
                tablaSes.addCell(cSem);
            }
            for (int i = 0; i < numSesiones; i++) {
                PdfPCell cFirma = new PdfPCell(new Paragraph("\n\n"));
                cFirma.setPadding(4);
                cFirma.setMinimumHeight(36);
                cFirma.setBorderColor(new Color(200, 210, 225));
                tablaSes.addCell(cFirma);
            }
            sesCell.addElement(tablaSes);
            carnet.addCell(sesCell);

            // ── Pie del carnet ──
            PdfPCell pieCell = new PdfPCell();
            pieCell.setBackgroundColor(AZUL_CLARO);
            pieCell.setPadding(6);
            pieCell.setBorderColor(AZUL_MEDIO);
            Paragraph pPie = new Paragraph(
                "★  El alumno debe cumplir mínimo el 80% de asistencia (8 de 10 sesiones) " +
                "para acreditar el Programa de Tutorías.", fPie);
            pPie.setAlignment(Element.ALIGN_CENTER);
            pieCell.addElement(pPie);
            carnet.addCell(pieCell);

            doc.add(carnet);
            index++;
           // if (index % 2 == 0 && index < total) doc.newPage();
            
            index++;
            if (index < total) doc.newPage();
        }

        doc.close();
    }

    // ══════════════════════════════════════════════════════════════════
    //  6. PDF CONSTANCIA DEL ALUMNO
    // ══════════════════════════════════════════════════════════════════
    public void generarConstanciaAlumno(Constancia constancia,
                                         HttpServletResponse response) throws Exception {
        Document doc = new Document(PageSize.A4, 65, 65, 50, 50);
        PdfWriter.getInstance(doc, response.getOutputStream());
        doc.open();

        Font fInstBold  = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 13, AZUL_OSCURO);
        Font fInst      = FontFactory.getFont(FontFactory.HELVETICA, 10, GRIS_TEXTO);
        Font fTitulo    = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, AZUL_OSCURO);
        Font fBody      = FontFactory.getFont(FontFactory.HELVETICA, 12, GRIS_TEXTO);
        Font fBodyBold  = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, AZUL_OSCURO);

        // ── Logo + institución ──
        try {
            Image logo = Image.getInstance(new URL(URL_LOGO));
            logo.scaleToFit(100, 75);
            logo.setAlignment(Element.ALIGN_CENTER);
            doc.add(logo);
        } catch (Exception e) { /* sin logo */ }

        Paragraph p1 = new Paragraph("TECNOLÓGICO NACIONAL DE MÉXICO", fInstBold);
        p1.setAlignment(Element.ALIGN_CENTER);
        doc.add(p1);
        Paragraph p2 = new Paragraph("Campus Chilpancingo", fInst);
        p2.setAlignment(Element.ALIGN_CENTER);
        doc.add(p2);
        Paragraph p3 = new Paragraph("Departamento de Desarrollo Académico", fInst);
        p3.setAlignment(Element.ALIGN_CENTER);
        doc.add(p3);
        doc.add(new Paragraph("\n"));

        agregarLineaDecorativa(doc);
        doc.add(new Paragraph("\n"));

        Paragraph titulo = new Paragraph("CONSTANCIA DE PARTICIPACIÓN", fTitulo);
        titulo.setAlignment(Element.ALIGN_CENTER);
        doc.add(titulo);
        Font fSub = FontFactory.getFont(FontFactory.HELVETICA, 11, AZUL_MEDIO);
        Paragraph sub = new Paragraph("Programa Institucional de Tutorías", fSub);
        sub.setAlignment(Element.ALIGN_CENTER);
        doc.add(sub);
        doc.add(new Paragraph("\n\n"));

        // ── Cuerpo ──
        String nombreAlumno = constancia.getTutorado() != null
            ? constancia.getTutorado().getNombre() + " " + constancia.getTutorado().getApellido() : "-";
        String noControl = constancia.getTutorado() != null
            ? constancia.getTutorado().getNumeroControl() : "-";
        String carrera = constancia.getAsignacion() != null
            && constancia.getAsignacion().getCarrera() != null
            ? constancia.getAsignacion().getCarrera().getNombre() : "-";
        String semestre = constancia.getAsignacion() != null
            && constancia.getAsignacion().getSemestre() != null
            ? constancia.getAsignacion().getSemestre().getNombre() : "-";
        String nombreTutor = constancia.getAsignacion() != null
            && constancia.getAsignacion().getTutor() != null
            ? constancia.getAsignacion().getTutor().getNombre() + " "
              + constancia.getAsignacion().getTutor().getApellido() : "-";
        String fecha = constancia.getFechaEmision() != null
            ? constancia.getFechaEmision().toString() : "-";

        agregarParrafoConstancia(doc,
            "El Departamento de Desarrollo Académico del Tecnológico Nacional de México, " +
            "Campus Chilpancingo, hace constar que el alumno ",
            nombreAlumno + " (No. de Control: " + noControl + ")",
            " de la carrera de ",
            carrera,
            " participó satisfactoriamente en el Programa Institucional de Tutorías " +
            "correspondiente al semestre ",
            semestre + ".",
            fBody, fBodyBold);

        agregarParrafoConstancia(doc,
            "El alumno cumplió con el mínimo requerido del ",
            "80% de asistencia",
            " a las sesiones de tutoría impartidas por el docente tutor ",
            nombreTutor + ".",
            "", "", fBody, fBodyBold);

        agregarParrafoConstancia(doc,
            "La presente constancia se expide a petición del interesado el día ",
            fecha + ".",
            "", "", "", "", fBody, fBodyBold);

        doc.add(new Paragraph("\n\n\n\n"));
        agregarLineaDecorativa(doc);
        agregarFirmas(doc, "Coordinador(a) Institucional de Tutorías",
            "Jefe del Departamento de Desarrollo Académico");
        doc.close();
    }

    // ══════════════════════════════════════════════════════════════════
    //  7. PDF CONSTANCIA DEL TUTOR
    // ══════════════════════════════════════════════════════════════════
    public void generarConstanciaTutor(Asignacion asignacion,
                                        int totalTutorados,
                                        int tutoradosAcreditados,
                                        HttpServletResponse response) throws Exception {
        Document doc = new Document(PageSize.A4, 65, 65, 50, 50);
        PdfWriter.getInstance(doc, response.getOutputStream());
        doc.open();

        Font fInstBold = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 13, AZUL_OSCURO);
        Font fInst     = FontFactory.getFont(FontFactory.HELVETICA, 10, GRIS_TEXTO);
        Font fTitulo   = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, AZUL_OSCURO);
        Font fBody     = FontFactory.getFont(FontFactory.HELVETICA, 12, GRIS_TEXTO);
        Font fBodyBold = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, AZUL_OSCURO);

        try {
            Image logo = Image.getInstance(new URL(URL_LOGO));
            logo.scaleToFit(100, 75);
            logo.setAlignment(Element.ALIGN_CENTER);
            doc.add(logo);
        } catch (Exception e) { /* sin logo */ }

        Paragraph p1 = new Paragraph("TECNOLÓGICO NACIONAL DE MÉXICO", fInstBold);
        p1.setAlignment(Element.ALIGN_CENTER);
        doc.add(p1);
        Paragraph p2 = new Paragraph("Campus Chilpancingo", fInst);
        p2.setAlignment(Element.ALIGN_CENTER);
        doc.add(p2);
        Paragraph p3 = new Paragraph("Departamento de Desarrollo Académico", fInst);
        p3.setAlignment(Element.ALIGN_CENTER);
        doc.add(p3);
        doc.add(new Paragraph("\n"));

        agregarLineaDecorativa(doc);
        doc.add(new Paragraph("\n"));

        Paragraph titulo = new Paragraph("CONSTANCIA DE PARTICIPACIÓN", fTitulo);
        titulo.setAlignment(Element.ALIGN_CENTER);
        doc.add(titulo);
        Font fSub = FontFactory.getFont(FontFactory.HELVETICA, 11, AZUL_MEDIO);
        Paragraph sub = new Paragraph("Programa Institucional de Tutorías — Tutor", fSub);
        sub.setAlignment(Element.ALIGN_CENTER);
        doc.add(sub);
        doc.add(new Paragraph("\n\n"));

        String nombreTutor = asignacion.getTutor() != null
            ? asignacion.getTutor().getNombre() + " " + asignacion.getTutor().getApellido() : "-";
        String carrera  = asignacion.getCarrera() != null ? asignacion.getCarrera().getNombre() : "-";
        String semestre = asignacion.getSemestre() != null ? asignacion.getSemestre().getNombre() : "-";
        String aula     = asignacion.getAula() != null ? asignacion.getAula() : "-";
        String horario  = asignacion.getHorario() != null ? asignacion.getHorario() : "-";
        String fechaHoy = java.time.LocalDate.now().toString();

        agregarParrafoConstancia(doc,
            "El Departamento de Desarrollo Académico del Tecnológico Nacional de México, " +
            "Campus Chilpancingo, hace constar que el docente ",
            nombreTutor,
            " fungió como Tutor dentro del Programa Institucional de Tutorías " +
            "correspondiente al semestre ",
            semestre + ".",
            "", "", fBody, fBodyBold);

        agregarParrafoConstancia(doc,
            "El docente impartió ",
            "10 sesiones de tutoría",
            " en el aula ",
            aula,
            " en el horario ",
            horario + ", atendiendo a la carrera de " + carrera + ".",
            fBody, fBodyBold);

        agregarParrafoConstancia(doc,
            "De un total de ",
            String.valueOf(totalTutorados) + " tutorados asignados",
            ", ",
            String.valueOf(tutoradosAcreditados) + " acreditaron el programa",
            " con el mínimo del ",
            "80% de asistencia.",
            fBody, fBodyBold);

        agregarParrafoConstancia(doc,
            "La presente constancia se expide a petición del interesado el día ",
            fechaHoy + ".",
            "", "", "", "", fBody, fBodyBold);

        doc.add(new Paragraph("\n\n\n\n"));
        agregarLineaDecorativa(doc);
        agregarFirmas(doc, "Coordinador(a) Institucional de Tutorías",
            "Jefe del Departamento de Desarrollo Académico");
        doc.close();
    }

    // ══════════════════════════════════════════════════════════════════
    //  TABLA DE ACTIVIDADES (compartida)
    // ══════════════════════════════════════════════════════════════════
    private PdfPTable crearTablaActividades(List<Actividad> actividades)
            throws DocumentException {
        PdfPTable tabla = new PdfPTable(4);
        tabla.setWidthPercentage(100);
        tabla.setWidths(new float[]{3f, 4.5f, 1.5f, 2f});
        tabla.setSpacingAfter(12);

        tabla.addCell(crearCeldaEncabezado("Nombre"));
        tabla.addCell(crearCeldaEncabezado("Descripción"));
        tabla.addCell(crearCeldaEncabezado("Semana"));
        tabla.addCell(crearCeldaEncabezado("Fecha"));

        Font f = FontFactory.getFont(FontFactory.HELVETICA, 10, GRIS_TEXTO);
        int fila = 0;
        for (Actividad a : actividades) {
            Color bg = (fila % 2 == 0) ? Color.WHITE : AZUL_CLARO;
            tabla.addCell(crearCeldaFila(a.getNombre(), f, bg));
            tabla.addCell(crearCeldaFila(a.getDescripcion(), f, bg));
            tabla.addCell(crearCeldaFila(a.getSemana(), f, bg));
            tabla.addCell(crearCeldaFila(
                a.getFecha() != null ? a.getFecha().toString() : "-", f, bg));
            fila++;
        }
        return tabla;
    }

    // ══════════════════════════════════════════════════════════════════
    //  PÁRRAFO MIXTO PARA CONSTANCIAS (texto normal + negrita alternado)
    // ══════════════════════════════════════════════════════════════════
    private void agregarParrafoConstancia(Document doc,
                                           String t1, String b1,
                                           String t2, String b2,
                                           String t3, String b3,
                                           Font fNormal, Font fBold)
            throws DocumentException {
        Paragraph p = new Paragraph();
        p.setAlignment(Element.ALIGN_JUSTIFIED);
        p.setSpacingAfter(14);
        p.setLeading(18);
        if (t1 != null && !t1.isEmpty()) p.add(new Chunk(t1, fNormal));
        if (b1 != null && !b1.isEmpty()) p.add(new Chunk(b1, fBold));
        if (t2 != null && !t2.isEmpty()) p.add(new Chunk(t2, fNormal));
        if (b2 != null && !b2.isEmpty()) p.add(new Chunk(b2, fBold));
        if (t3 != null && !t3.isEmpty()) p.add(new Chunk(t3, fNormal));
        if (b3 != null && !b3.isEmpty()) p.add(new Chunk(b3, fBold));
        doc.add(p);
    }
    
 // ══════════════════════════════════════════════════════════════════
//  8. PDF PASE DE LISTA POR SESIÓN
// ══════════════════════════════════════════════════════════════════
public void generarPaseLista(Asignacion asignacion,
                              SesionTutoria sesion,
                              List<AsignacionTutorado> tutorados,
                              List<Asistencia> asistencias,
                              HttpServletResponse response) throws Exception {
    Document doc = new Document(PageSize.A4, 50, 50, 40, 40);
    PdfWriter.getInstance(doc, response.getOutputStream());
    doc.open();

    agregarEncabezadoInstitucional(doc,
        "PASE DE LISTA — SESIÓN " + sesion.getSemana(),
        "Programa Institucional de Tutorías");
    agregarLineaDecorativa(doc);

    Font fLabel = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, AZUL_OSCURO);
    Font fValor = FontFactory.getFont(FontFactory.HELVETICA, 10, GRIS_TEXTO);

    // ── Datos de la sesión ──
    agregarTituloSeccion(doc, "Datos de la Sesión");
    PdfPTable tablaDatos = new PdfPTable(2);
    tablaDatos.setWidthPercentage(100);
    tablaDatos.setWidths(new float[]{2f, 5f});
    tablaDatos.setSpacingAfter(10);

    String nombreTutor = asignacion.getTutor() != null
        ? asignacion.getTutor().getNombre() + " " + asignacion.getTutor().getApellido() : "-";
    tablaDatos.addCell(crearCeldaLabel("Tutor:", fLabel));
    tablaDatos.addCell(crearCeldaValor(nombreTutor, fValor));
    tablaDatos.addCell(crearCeldaLabel("Carrera:", fLabel));
    tablaDatos.addCell(crearCeldaValor(
        asignacion.getCarrera() != null ? asignacion.getCarrera().getNombre() : "-", fValor));
    tablaDatos.addCell(crearCeldaLabel("Semestre:", fLabel));
    tablaDatos.addCell(crearCeldaValor(
        asignacion.getSemestre() != null ? asignacion.getSemestre().getNombre() : "-", fValor));
    tablaDatos.addCell(crearCeldaLabel("Semana:", fLabel));
    tablaDatos.addCell(crearCeldaValor(
        sesion.getSemana() != null ? "Semana " + sesion.getSemana() : "-", fValor));
    tablaDatos.addCell(crearCeldaLabel("Fecha:", fLabel));
    tablaDatos.addCell(crearCeldaValor(
        sesion.getFecha() != null ? sesion.getFecha().toString() : "-", fValor));

    // ── Actividad del PAT ──
    if (sesion.getActividad() != null) {
        tablaDatos.addCell(crearCeldaLabel("Actividad PAT:", fLabel));
        tablaDatos.addCell(crearCeldaValor(sesion.getActividad().getNombre(), fValor));
        tablaDatos.addCell(crearCeldaLabel("Descripción:", fLabel));
        tablaDatos.addCell(crearCeldaValor(sesion.getActividad().getDescripcion(), fValor));
    }
    doc.add(tablaDatos);

    // ── Lista de asistencia ──
    agregarTituloSeccion(doc, "Lista de Asistencia");
    PdfPTable tablaAsist = new PdfPTable(5);
    tablaAsist.setWidthPercentage(100);
    tablaAsist.setWidths(new float[]{0.8f, 2f, 2.5f, 2.5f, 2f});
    tablaAsist.setSpacingAfter(20);

    tablaAsist.addCell(crearCeldaEncabezado("#"));
    tablaAsist.addCell(crearCeldaEncabezado("No. Control"));
    tablaAsist.addCell(crearCeldaEncabezado("Nombre"));
    tablaAsist.addCell(crearCeldaEncabezado("Apellido"));
    tablaAsist.addCell(crearCeldaEncabezado("Asistencia"));

    Font fCelda = FontFactory.getFont(FontFactory.HELVETICA, 9, GRIS_TEXTO);
    Font fPresente = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9, VERDE_OK);
    Font fAusente = FontFactory.getFont(FontFactory.HELVETICA, 9, ROJO_NOK);

    int fila = 0;
    for (AsignacionTutorado at : tutorados) {
        Tutorado t = at.getTutorado();
        if (t == null) continue;
        Color bg = (fila % 2 == 0) ? Color.WHITE : AZUL_CLARO;

        boolean asistio = asistencias.stream().anyMatch(a ->
            a.getTutorado().getIdTutorado().equals(t.getIdTutorado()) &&
            a.getPresente() != null && a.getPresente() == 1);

        tablaAsist.addCell(crearCeldaFila(String.valueOf(fila + 1), fCelda, bg));
        tablaAsist.addCell(crearCeldaFila(t.getNumeroControl(), fCelda, bg));
        tablaAsist.addCell(crearCeldaFila(t.getNombre(), fCelda, bg));
        tablaAsist.addCell(crearCeldaFila(t.getApellido(), fCelda, bg));

        PdfPCell cAsist = new PdfPCell(new Paragraph(
            asistio ? "✓ Presente" : "✗ Ausente", asistio ? fPresente : fAusente));
        cAsist.setBackgroundColor(asistio ? VERDE_BG : ROJO_BG);
        cAsist.setPadding(5);
        cAsist.setHorizontalAlignment(Element.ALIGN_CENTER);
        cAsist.setBorderColor(new Color(200, 210, 225));
        tablaAsist.addCell(cAsist);
        fila++;
    }
    doc.add(tablaAsist);

    agregarFirmas(doc, "Tutor", "Coordinador de Tutoría por Carrera");
    agregarLineaDecorativa(doc);
    doc.close();
    
    
}

public void generarCarnetIndividual(Asignacion asignacion,
        List<SesionTutoria> sesiones,
        AsignacionTutorado at,
        HttpServletResponse response) throws Exception {
List<AsignacionTutorado> uno = new java.util.ArrayList<>();
uno.add(at);
generarCarnets(asignacion, sesiones, uno, response);
}
}